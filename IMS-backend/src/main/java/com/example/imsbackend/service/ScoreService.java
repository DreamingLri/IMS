package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.Score;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.entity.vo.StudentScoreVo;

import java.util.List;


public interface ScoreService extends IService<Score> {

    boolean addScore(Score score);

    Score getScoreByUserIdAndCourseId(Integer userId, Integer courseId);

    List<StudentScoreVo> listStudentWithScoreByCourseId(Integer courseId);
}
