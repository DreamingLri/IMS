package com.example.imsbackend.controller;

import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.service.ScoreService;
import com.example.imsbackend.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
@Validated
public class ScoreController {
    private final ScoreService scoreService;
    @PostMapping("/addScore")
    public boolean addScore(@RequestBody UserCourse userCourse){
        return scoreService.addScore(userCourse);
    }
}
