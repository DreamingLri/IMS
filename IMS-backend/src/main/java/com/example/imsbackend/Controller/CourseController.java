package com.example.imsbackend.Controller;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.service.CoursesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Validated
public class CourseController {

    private final CoursesService coursesService;

    @GetMapping("/listCourse")
    public List<Courses> listCourse(String name){
        return coursesService.listCourse(name);
    }

    @PostMapping("/insertCourse")
    public boolean insertCourse(@Valid @RequestBody Courses courses){
        return coursesService.insertCourse(courses);
    }

    @PostMapping("/updateCourseById")
    public boolean updateCourseById(@Valid @RequestBody Courses courses){
        return coursesService.updateCourseById(courses);
    }

    @DeleteMapping("deleteCourseById/{id}")
    public boolean deleteCourseById(@PathVariable Integer id){
        return coursesService.deleteCourseById(id);
    }
}
