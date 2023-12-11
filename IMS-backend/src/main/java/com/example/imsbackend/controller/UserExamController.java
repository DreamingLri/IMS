package com.example.imsbackend.controller;

import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.vo.UserExamVO;
import com.example.imsbackend.service.UserExamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-exam")
@RequiredArgsConstructor
public class UserExamController {
    private final UserExamsService userExamsService;

    @GetMapping("/listUserSelectedExam")
    public List<UserExamVO> listUserSelectedExam(Integer examId){
        return userExamsService.listUserSelectedExam(examId);
    }
}
