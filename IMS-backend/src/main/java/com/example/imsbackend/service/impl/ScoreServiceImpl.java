package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.*;
import com.example.imsbackend.entity.vo.StudentScoreVo;
import com.example.imsbackend.entity.vo.TeacherScoreVO;
import com.example.imsbackend.mapper.*;
import com.example.imsbackend.service.ScoreService;
import com.example.imsbackend.service.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(score.getScoreFunction() == 1)
            totalScore = score.getStudyScore()*0.4 + score.getExamScore()*0.6;
        else if (score.getScoreFunction() == 2)
            totalScore = score.getStudyScore()*0.5 + score.getExamScore()*0.5;
        else
            totalScore = score.getStudyScore();
        score.setTotalScore(totalScore);

        Score checkScore = baseMapper.selectOne(new LambdaQueryWrapper<Score>()
                .eq(Score::getCourseId, score.getCourseId())
                .eq(Score::getUserId, score.getUserId()));
        if(ObjectUtil.isEmpty(checkScore)){
            return baseMapper.insert(score) == 1;
        } else {
            score.setId(checkScore.getId());
            score.setTotalScore(totalScore);
            return baseMapper.updateById(score) == 1;
        }
    }

    @Override
    public boolean addEvaluation(Score score) {
        Score checkScore = baseMapper.selectOne(new LambdaQueryWrapper<Score>()
                .eq(Score::getUserId, score.getUserId())
                .eq(Score::getCourseId, score.getCourseId()));
        if(ObjectUtil.isEmpty(checkScore)){
            return baseMapper.insert(score) == 1;
        } else {
            score.setId(checkScore.getId());
            return baseMapper.updateById(score) == 1;
        }
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
                    .eq(Score::getCourseId, userCourse.getCourseId()));
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getId, userCourse.getUserId()));

            StudentScoreVo studentScoreVo = new StudentScoreVo();
            studentScoreVo.setName(user.getUsername());
            studentScoreVo.setNetId(user.getNetId());
            studentScoreVo.setCode(user.getCode());
            studentScoreVo.setId(user.getId());

            if(!ObjectUtil.isEmpty(score)){
                studentScoreVo.setStudyScore(score.getStudyScore());
                studentScoreVo.setExamScore(score.getExamScore());
                studentScoreVo.setTotalScore(score.getTotalScore());
                studentScoreVo.setScoreFunction(score.getScoreFunction());
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
                    .eq(Score::getCourseId, userCourse.getCourseId()));

            TeacherScoreVO teacherScoreVO = new TeacherScoreVO();
            teacherScoreVO.setTeacherName(courses.getTeacher());
            teacherScoreVO.setCourseName(courses.getName());
            teacherScoreVO.setId(courses.getId());

            if(!ObjectUtil.isEmpty(score)){
                teacherScoreVO.setEvaluationScore(score.getEvaluationScore());
                teacherScoreVO.setEvaluationSuggestion(score.getEvaluationSuggestion());
            }
            list.add(teacherScoreVO);
        });
        return list;
    }
}
