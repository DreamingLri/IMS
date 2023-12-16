package com.example.imsbackend.controller;


import com.example.imsbackend.entity.School;
import com.example.imsbackend.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController{
    private final SchoolService schoolService;

    @GetMapping("/getAllSchool")
    public List<School> getAllSchool(){
        return schoolService.getAllSchool();
    }

    @PostMapping("/insertSchool")
    public boolean insertSchool(@RequestBody School school){
        return schoolService.insertSchool(school);
    }

    @PostMapping("/updateSchool")
    public boolean updateSchool(@RequestBody School school){
        return schoolService.updateSchool(school);
    }

    @DeleteMapping("/deleteSchoolById/{id}")
    public boolean deleteSchoolById(@PathVariable Integer id){
        return schoolService.deleteSchoolById(id);
    }
}

