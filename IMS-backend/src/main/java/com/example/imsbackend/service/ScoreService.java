package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.Score;
import com.example.imsbackend.entity.UserCourse;


public interface ScoreService extends IService<Score> {

    boolean addScore(UserCourse userCourse);
}
