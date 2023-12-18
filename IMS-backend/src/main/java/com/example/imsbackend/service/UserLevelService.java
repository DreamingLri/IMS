package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.UserLevel;
import com.example.imsbackend.entity.vo.UserWithLevelVO;

import java.util.List;

public interface UserLevelService extends IService<UserLevel> {

    List<UserWithLevelVO> getUserWithLevel(String username, Integer userId);

    boolean updateLevelByUserId(UserLevel userLevel);
}
