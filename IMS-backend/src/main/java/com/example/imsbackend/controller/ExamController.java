package com.example.imsbackend.controller;

import com.example.imsbackend.entity.Exams;
import com.example.imsbackend.service.ExamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
@Validated
public class ExamController {
    private final ExamsService examsService;

    @GetMapping("/listExam")
    public List<Exams> listExam(String name){
        return examsService.listExam(name);
    }

    @GetMapping("/listExamByStudentId")
    public List<Exams> listExamByUserId(Integer id){
        return examsService.listExamByUserId(id);
    }

    @PostMapping("/insertExam")
    public boolean insertExam(@RequestBody Exams exams){
        return examsService.insertExam(exams);
    }

    @PostMapping("/updateExam")
    public boolean updateExam(@RequestBody Exams exams){
        return examsService.updateExam(exams);
    }

    @DeleteMapping("deleteExamById")
    public boolean deleteExamById(@PathVariable Integer id){
        return examsService.deleteExamById(id);
    }
}
