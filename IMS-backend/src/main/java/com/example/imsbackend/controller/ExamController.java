package com.example.imsbackend.controller;

import com.example.imsbackend.entity.Exams;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserExams;
import com.example.imsbackend.entity.dto.ExamAddStudentDTO;
import com.example.imsbackend.service.ExamsService;
import com.example.imsbackend.service.UserExamsService;
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
    private final UserExamsService userExamsService;

    @GetMapping("/listExam")
    public List<Exams> listExam(String name){
        return examsService.listExam(name);
    }

    @GetMapping("/listExamByUserId")
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

    @DeleteMapping("/deleteExamById/{id}")
    public boolean deleteExamById(@PathVariable Integer id){
        return examsService.deleteExamById(id);
    }

    @PostMapping("/addStudent")
    public boolean addStudent(@RequestBody ExamAddStudentDTO examAddStudentDTO){
        return examsService.addStudent(examAddStudentDTO);
    }

    @GetMapping("/listStudentByExamId")
    public List<User> listStudentByExamId(Integer examId){
        return userExamsService.listStudentByExamId(examId);
    }

    @PostMapping("/selectExam")
    public boolean selectExam(@RequestBody UserExams userExams){
        return userExamsService.selectExam(userExams);
    }

    @PostMapping("/withdrawExam")
    public boolean withdrawExam(@RequestBody UserExams userExams){
        return userExamsService.withdrawExam(userExams);
    }
}
