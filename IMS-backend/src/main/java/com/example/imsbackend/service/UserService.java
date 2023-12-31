package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.entity.vo.TeacherNameVO;

import java.util.List;

public interface UserService extends IService<User> {

    List<AuthUserInfoVO> listStudent(String username);
    List<AuthUserInfoVO> listTeacher(String username);

    AuthUserInfoVO getUserById(Integer id);

    boolean insertStudent(InsertUserDTO insertUserDTO);
    boolean insertTeacher(InsertUserDTO insertUserDTO);
    boolean insertAdmin(InsertUserDTO insertUserDTO);
    boolean insertUser(InsertUserDTO insertUserDTO);

    boolean updateUserById(UpdateUserDTO updateUserDTO);

    boolean deleteUserById(Integer id);

    List<AuthUserInfoVO> listAll(String username);

    List<TeacherNameVO> listTeacherName();

    List<AuthUserInfoVO> listStudentByCourseId(Integer courseId);
}

