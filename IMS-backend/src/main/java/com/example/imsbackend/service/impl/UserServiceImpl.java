package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import org.springframework.stereotype.Service;
import com.example.imsbackend.service.UserService;

import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-12-03 10:25:06
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<AuthUserInfoVO> listUser() {
        return baseMapper.selectList(null)
                .stream()
                .map(BeanCopyUtil.INSTANCE::toAuthUserInfo)
                .toList();
    }

    @Override
    public AuthUserInfoVO getUserById(Integer id) {
        return BeanCopyUtil.INSTANCE.toAuthUserInfo(baseMapper.selectById(id));
    }

    @Override
    public boolean insertUser(InsertUserDTO insertUserDTO) {
        User user =  BeanCopyUtil.INSTANCE.toUser(insertUserDTO);
        return baseMapper.insert(user) == 1;
    }

    @Override
    public boolean updateUserById(UpdateUserDTO updateUserDTO) {
        User user =  BeanCopyUtil.INSTANCE.toUser(updateUserDTO);
        return baseMapper.updateById(user) == 1;
    }

    @Override
    public boolean deleteUserById(Integer id) {
        return baseMapper.deleteById(id) == 1;
    }
}

