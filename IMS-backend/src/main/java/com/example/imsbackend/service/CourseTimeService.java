package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.CourseTime;

import java.util.List;


/**
 * (CourseTime)表服务接口
 *
 * @author makejava
 * @since 2023-12-08 10:21:43
 */
public interface CourseTimeService extends IService<CourseTime> {

    List<CourseTime> getCourseTimeById(int id);

    boolean updateCourseTimeById(CourseTime courseTime);

    boolean deleteCourseTimeById(Integer id);

    boolean insertCourseTime(CourseTime courseTime);
}
