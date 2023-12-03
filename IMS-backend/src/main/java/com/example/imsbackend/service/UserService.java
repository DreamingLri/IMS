package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;

import java.util.List;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-12-03 10:25:03
 */
public interface UserService extends IService<User> {

    List<AuthUserInfoVO> listUser();

    AuthUserInfoVO getUserById(Integer id);

    boolean insertUser(InsertUserDTO insertUserDTO);

    boolean updateUserById(UpdateUserDTO updateUserDTO);

    boolean deleteUserById(Integer id);
}

