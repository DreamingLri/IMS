package com.example.imsbackend.controller;

import com.example.imsbackend.entity.Score;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.entity.vo.StudentScoreVo;
import com.example.imsbackend.service.ScoreService;
import com.example.imsbackend.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
@Validated
public class ScoreController {
    private final ScoreService scoreService;
    @PostMapping("/addScore")
    public boolean addScore(@RequestBody Score score){
        return scoreService.addScore(score);
    }

    @GetMapping("/getScoreByUserIdAndCourseId")
    public Score getScoreByUserIdAndCourseId(Integer userId, Integer courseId){
        return scoreService.getScoreByUserIdAndCourseId(userId, courseId);
    }

    @GetMapping("/listStudentWithScoreByCourseId")
    public List<StudentScoreVo> listStudentWithScoreByCourseId(Integer courseId){
        return scoreService.listStudentWithScoreByCourseId(courseId);
    }
}
