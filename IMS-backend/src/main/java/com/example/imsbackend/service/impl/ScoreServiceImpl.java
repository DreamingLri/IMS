package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.Score;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.entity.UserLevel;
import com.example.imsbackend.entity.vo.StudentScoreVo;
import com.example.imsbackend.mapper.ScoreMapper;
import com.example.imsbackend.mapper.UserCourseMapper;
import com.example.imsbackend.mapper.UserLevelMapper;
import com.example.imsbackend.mapper.UserMapper;
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
            baseMapper.insert(score);
        } else {
            score.setId(checkScore.getId());
            score.setStudyScore(score.getStudyScore());
            score.setExamScore(score.getExamScore());
            score.setTotalScore(totalScore);
            score.setScoreFunction(score.getScoreFunction());
            baseMapper.updateById(score);
        }
        return true;
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
            if(!ObjectUtil.isEmpty(score)){
                studentScoreVo.setId(score.getId());
                studentScoreVo.setStudyScore(score.getStudyScore());
                studentScoreVo.setExamScore(score.getExamScore());
                studentScoreVo.setTotalScore(score.getTotalScore());
            }
            list.add(studentScoreVo);
        });
        return list;
    }
}
