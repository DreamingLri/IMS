package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;

import java.util.List;

public interface UserService extends IService<User> {

    List<AuthUserInfoVO> listUser(String username);

    AuthUserInfoVO getUserById(Integer id);

    boolean insertUser(InsertUserDTO insertUserDTO);

    boolean updateUserById(UpdateUserDTO updateUserDTO);

    boolean deleteUserById(Integer id);
}

