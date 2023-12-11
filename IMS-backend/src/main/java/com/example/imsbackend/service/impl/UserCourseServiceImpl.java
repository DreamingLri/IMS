package com.example.imsbackend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.Score;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.handler.exception.SelectCourseException;
import com.example.imsbackend.handler.exception.WithdrawCourseException;
import com.example.imsbackend.mapper.ScoreMapper;
import com.example.imsbackend.mapper.UserCourseMapper;
import com.example.imsbackend.service.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service("userCourseService")
@AllArgsConstructor
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements UserCourseService {
    private final ScoreMapper scoreMapper;
    @Override
    public boolean selectCourse(UserCourse userCourse) {
        if(baseMapper.insert(userCourse) == 0){
            throw new SelectCourseException();
        }
        return true;
    }

    @Override
    public boolean withdrawCourse(UserCourse userCourse) {
//        if(baseMapper.deleteById(userCourse) == 0){
//            throw new WithdrawCourseException();
//        }
//        return true;
        if(baseMapper.delete(new LambdaQueryWrapper<UserCourse>()
                .eq(UserCourse::getCourseId, userCourse.getCourseId())
                .eq(UserCourse::getUserId, userCourse.getUserId()))
                == 0){
            throw new WithdrawCourseException();
        }
        return true;
    }

    @Override
    public boolean addScore(UserCourse userCourse) {
        double totalScore;
        int function = userCourse.getScoreFunction();
        if(function == 1){
            totalScore = userCourse.getExamScore()*0.6 + userCourse.getStudyScore()*0.4;
        } else if (function == 2) {
            totalScore = userCourse.getExamScore()*0.5 + userCourse.getStudyScore()*0.5;
        } else {
            totalScore = userCourse.getStudyScore();
        }
        userCourse.setTotalScore(totalScore);
        Score score = new Score();
        score.setCourseId(userCourse.getCourseId());

        scoreMapper.insert()

        return true;
    }
}
