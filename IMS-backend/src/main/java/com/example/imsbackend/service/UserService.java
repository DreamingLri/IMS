package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.User;

import java.util.List;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-12-03 10:25:03
 */
public interface UserService extends IService<User> {

    List<User> listUser();

    User getUserById(Integer id);

    boolean insertUser(User user);

    boolean updateUserById(User user);

    boolean deleteUserById(Integer id);
}

