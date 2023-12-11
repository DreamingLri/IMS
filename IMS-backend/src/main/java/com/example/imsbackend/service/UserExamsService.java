package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserExams;

import java.util.List;


/**
 * (UserExams)表服务接口
 *
 * @author makejava
 * @since 2023-12-07 15:49:31
 */
public interface UserExamsService extends IService<UserExams> {

    boolean selectExam(UserExams userExams);

    List<User> listStudentByExamId(Integer studentId);

    boolean withdrawExam(UserExams userExams);
}
