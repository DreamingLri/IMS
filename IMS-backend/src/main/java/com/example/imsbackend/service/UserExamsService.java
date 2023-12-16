package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserExams;
import com.example.imsbackend.entity.vo.UserExamVO;

import java.util.List;


public interface UserExamsService extends IService<UserExams> {

    boolean selectExam(UserExams userExams);

    List<User> listStudentByExamId(Integer studentId);

    boolean withdrawExam(UserExams userExams);

    List<UserExamVO> listUserSelectedExam(Integer examId);
}
