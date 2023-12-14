package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.dto.InsertCourseDTO;
import com.example.imsbackend.entity.vo.CourseVO;

import java.util.List;

/**
 * (Courses)表服务接口
 *
 * @author makejava
 * @since 2023-12-05 17:02:30
 */
public interface CoursesService extends IService<Courses> {

    List<Courses> listCourse(String name);

    boolean insertCourse(Courses courses);

    boolean updateCourseById(Courses courses);

    boolean deleteCourseById(Integer id);

    List<CourseVO> selectCourseById(Integer id);

    Courses listCourseById(Integer CourseId);

    List<Courses> listCourseByUserId(Integer userId);

    boolean insertCourseByUserId(InsertCourseDTO courses);
}

