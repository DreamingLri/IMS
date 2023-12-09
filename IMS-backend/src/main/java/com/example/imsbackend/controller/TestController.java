package com.example.imsbackend.controller;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.service.CoursesService;
import com.example.imsbackend.service.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TestController {
    private final CoursesService coursesService;
    private final UserCourseService userCourseService;

    @GetMapping("/test/{id}")
    public Courses selectCourse(@PathVariable Integer id){
        return coursesService.listCourseById(id);
    }
}
