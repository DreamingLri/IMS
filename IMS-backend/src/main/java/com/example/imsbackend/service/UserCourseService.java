package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.UserCourse;

public interface UserCourseService extends IService<UserCourse> {
    boolean selectCourse(UserCourse userCourse);

    boolean withdrawCourse(UserCourse userCourse);

    boolean addScore(UserCourse userCourse);
}
