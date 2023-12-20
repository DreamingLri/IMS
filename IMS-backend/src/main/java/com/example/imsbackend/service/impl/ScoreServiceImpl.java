package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.*;
import com.example.imsbackend.entity.vo.EvaluationCourseVO;
import com.example.imsbackend.entity.vo.EvaluationVO;
import com.example.imsbackend.entity.vo.StudentScoreVo;
import com.example.imsbackend.entity.vo.TeacherScoreVO;
import com.example.imsbackend.mapper.*;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.ScoreService;
import com.example.imsbackend.service.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.imsbackend.constants.OtherConstants.STUDENT_ID;

@Service("scoreService")
@AllArgsConstructor
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    private final UserCourseMapper userCourseMapper;
    private final UserLevelMapper userLevelMapper;
    private final UserMapper userMapper;
    private final CoursesMapper coursesMapper;
    @Override
    public boolean addScore(Score score) {
        double totalScore;
        double gradePoints;
        if(score.getScoreFunction() == 1){
            totalScore = score.getStudyScore()*0.4 + score.getExamScore()*0.6;
            gradePoints = getGradePoints(totalScore);
        }

        else if (score.getScoreFunction() == 2){
            totalScore = score.getStudyScore()*0.5 + score.getExamScore()*0.5;
            gradePoints = getGradePoints(totalScore);
        }
        else{
            totalScore = score.getStudyScore();
            gradePoints = getGradePoints(totalScore);
        }

        score.setTotalScore(totalScore);
        score.setGradePoint(gradePoints);

        Score checkScore = baseMapper.selectOne(new LambdaQueryWrapper<Score>()
                .eq(Score::getCourseId, score.getCourseId())
                .eq(Score::getUserId, score.getUserId())
                .isNull(Score::getEvaluationScore));
        if(ObjectUtil.isEmpty(checkScore)){
            //学分自增
            {
                User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                        .eq(User::getId, score.getUserId()));
                Courses course = coursesMapper.selectOne(new LambdaQueryWrapper<Courses>()
                        .eq(Courses::getId, score.getCourseId()));
                if(user.getEarnedCredit() == null){
                    user.setEarnedCredit(0.0);
                }
                user.setEarnedCredit(user.getEarnedCredit()+course.getCredit());
            }
            return baseMapper.insert(score) == 1;
        } else {
            score.setId(checkScore.getId());
            score.setTotalScore(totalScore);
            return baseMapper.updateById(score) == 1;
        }
    }

    //绩点计算
    private Double getGradePoints(double totalScore){
        double gradePoints;
        if(totalScore >= 60){
            gradePoints = (totalScore-50)/10;
        } else {
            gradePoints = 0;
        }
        return gradePoints;
    }

    @Override
    public boolean addEvaluation(Score score) {
        Score checkScore = baseMapper.selectOne(new LambdaQueryWrapper<Score>()
                .eq(Score::getUserId, score.getUserId())
                .eq(Score::getCourseId, score.getCourseId())
                .isNull(Score::getTotalScore));
        if(ObjectUtil.isEmpty(checkScore)){
            return baseMapper.insert(score) == 1;
        } else {
            score.setId(checkScore.getId());
            return baseMapper.updateById(score) == 1;
        }
    }

    @Override
    public List<StudentScoreVo> listScoreByUserId(Integer userId) {
        List<StudentScoreVo> list = new ArrayList<>();
        baseMapper.selectList(new LambdaQueryWrapper<Score>()
                .eq(Score::getUserId, userId))
                .forEach(score -> {
                    StudentScoreVo studentScoreVo = new StudentScoreVo();
                    if(score.getCourseId() != null
                            && score.getStudyScore() != null
                            && score.getExamScore() != null
                            && score.getTotalScore() != null){
                        studentScoreVo.setName(coursesMapper.selectOne(new LambdaQueryWrapper<Courses>()
                                        .eq(Courses::getId, score.getCourseId()))
                                .getName());
                        studentScoreVo.setStudyScore(score.getStudyScore());
                        studentScoreVo.setExamScore(score.getExamScore());
                        studentScoreVo.setTotalScore(score.getTotalScore());
                        studentScoreVo.setGradePoint(score.getGradePoint());
                        list.add(studentScoreVo);
                    }
                });
        return list;
    }

    @Override
    public List<Integer> getScoreCountByUserId(Integer userId) {
            List<Integer> list = new ArrayList<>();
            List<Score> scores = baseMapper.selectList(new LambdaQueryWrapper<Score>()
                    .eq(Score::getUserId, userId));
            int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
            for (Score score : scores) {
                if(score.getTotalScore() != null){
                    if(score.getTotalScore() >=90 && score.getTotalScore() <=100)
                        count1++;
                    if(score.getTotalScore() >=80 && score.getTotalScore() <90)
                        count2++;
                    if(score.getTotalScore() >=70 && score.getTotalScore() <80)
                        count3++;
                    if(score.getTotalScore() >=60 && score.getTotalScore() <70)
                        count4++;
                    if(score.getTotalScore() < 60)
                        count5++;
                }
            }
            Collections.addAll(list, count1, count2, count3, count4, count5);
            return list;
    }

    //获得平均绩点
    public Double getGradePointByUserId(Integer userId) {
        double gradePoints = 0;
        int count = 0;
        List<Score> scores = baseMapper.selectList(new LambdaQueryWrapper<Score>()
                .eq(Score::getUserId, userId));
        for (Score score : scores) {
            if(score.getGradePoint() != null){
                gradePoints += score.getGradePoint();
                count++;
            }
        }
        if(count != 0){
            double result = new BigDecimal(gradePoints / count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        }
        return null;
    }

    //根据UserId(老师)展示评教内容
    @Override
    public List<JSONObject> getTeacherEvaluationByUserId(Integer userId) {

        List<JSONObject> list = new ArrayList<>();
        List<UserCourse> userCourses = userCourseMapper.selectList(new LambdaQueryWrapper<UserCourse>()
                .eq(UserCourse::getUserId, userId));
        for(UserCourse userCourse : userCourses){
            Courses course = coursesMapper.selectOne(new LambdaQueryWrapper<Courses>()
                    .eq(Courses::getId, userCourse.getCourseId()));
            List<EvaluationVO> evaluationVOList = new ArrayList<>();
            List<Score> scores = baseMapper.selectList(new LambdaQueryWrapper<Score>()
                    .eq(Score::getCourseId, userCourse.getCourseId())
                    .isNull(Score::getTotalScore));
            for (Score score : scores) {
                EvaluationVO evaluationVO = BeanCopyUtil.INSTANCE.toEvaluationVO(score);
                evaluationVOList.add(evaluationVO);
            }
            EvaluationCourseVO evaluationCourseVO = BeanCopyUtil.INSTANCE.toEvaluationCourseVO(course);
            evaluationCourseVO.setEvaluationList(evaluationVOList);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("course", evaluationCourseVO);
            list.add(jsonObject);
        }
        return list;
    }


    @Override
    public Score getScoreByUserIdAndCourseId(Integer userId, Integer courseId) {
        Score score = baseMapper.selectOne(new LambdaQueryWrapper<Score>()
                .eq(Score::getUserId, userId)
                .eq(Score::getCourseId, courseId));
        if(ObjectUtil.isEmpty(score)){
            return null;
        } else {
            return score;
        }
    }

    @Override
    public List<StudentScoreVo> listStudentWithScoreByCourseId(Integer courseId) {
        List<StudentScoreVo> list = new ArrayList<>();
            List<UserCourse> userCourses = userCourseMapper.selectList(new LambdaQueryWrapper<UserCourse>()
                .eq(UserCourse::getCourseId, courseId));
            userCourses.stream()
                .filter(userCourse -> {
                    UserLevel userLevel = userLevelMapper.selectOne(new LambdaQueryWrapper<UserLevel>()
                        .eq(UserLevel::getUserId, userCourse.getUserId()));
                    return userLevel.getLevelId().equals(STUDENT_ID);
                })
                .forEach(userCourse -> {
                    Score score = baseMapper.selectOne(new LambdaQueryWrapper<Score>()
                        .eq(Score::getUserId, userCourse.getUserId())
                        .eq(Score::getCourseId, userCourse.getCourseId())
                            .isNull(Score::getEvaluationScore)
                            .isNull(Score::getEvaluationSuggestion));
                    User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                        .eq(User::getId, userCourse.getUserId()));

                    StudentScoreVo studentScoreVo = new StudentScoreVo();
                    studentScoreVo.setName(user.getUsername());
                    studentScoreVo.setNetId(user.getNetId());
                    studentScoreVo.setCode(user.getCode());
                    studentScoreVo.setId(user.getId());
                    if(!ObjectUtil.isEmpty(score)){
                        if(score.getStudyScore() != null
                                && score.getExamScore() != null
                                && score.getTotalScore() != null
                                && score.getScoreFunction() != null){
                            studentScoreVo.setStudyScore(score.getStudyScore());
                            studentScoreVo.setExamScore(score.getExamScore());
                            studentScoreVo.setTotalScore(score.getTotalScore());
                            studentScoreVo.setScoreFunction(score.getScoreFunction());
                        }
                    }
                    list.add(studentScoreVo);
                });
        return list;
    }

    @Override
    public List<TeacherScoreVO> listTeacherWithScoreByUserId(Integer userId) {
        List<TeacherScoreVO> list = new ArrayList<>();
            List<UserCourse> userCourses = userCourseMapper.selectList(new LambdaQueryWrapper<UserCourse>().eq(UserCourse::getUserId, userId));
            userCourses.forEach(userCourse -> {
                Courses courses = coursesMapper.selectOne(new LambdaQueryWrapper<Courses>()
                        .eq(Courses::getId, userCourse.getCourseId()));
                Score score = baseMapper.selectOne(new LambdaQueryWrapper<Score>()
                        .eq(Score::getUserId, userId)
                        .eq(Score::getCourseId, userCourse.getCourseId())
                        .isNull(Score::getTotalScore));
                TeacherScoreVO teacherScoreVO = getTeacherScoreVO(courses, score);
                list.add(teacherScoreVO);
            });
        return list;
    }

    private static TeacherScoreVO getTeacherScoreVO(Courses courses, Score score) {
        TeacherScoreVO teacherScoreVO = new TeacherScoreVO();
        teacherScoreVO.setTeacherName(courses.getTeacher());
        teacherScoreVO.setCourseName(courses.getName());
        teacherScoreVO.setId(courses.getId());

        if(!ObjectUtil.isEmpty(score)){
            if(score.getEvaluationScore()!= null)
                teacherScoreVO.setEvaluationScore(score.getEvaluationScore());
            if(score.getEvaluationSuggestion() != null)
                teacherScoreVO.setEvaluationSuggestion(score.getEvaluationSuggestion());
        }
        return teacherScoreVO;
    }
}
