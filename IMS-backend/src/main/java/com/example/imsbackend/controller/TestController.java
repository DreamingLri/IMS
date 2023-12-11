package com.example.imsbackend.controller;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.service.CoursesService;
import com.example.imsbackend.service.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TestController {
    private final CoursesService coursesService;

    @GetMapping("/test/{id}")
    public Courses selectCourse(@PathVariable Integer id){
        return coursesService.listCourseById(id);
    }
}
