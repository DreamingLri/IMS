package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserLevel;
import com.example.imsbackend.entity.vo.UserWithLevelVO;
import com.example.imsbackend.mapper.UserLevelMapper;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.UserLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service("userLevelService")
@RequiredArgsConstructor
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper, UserLevel> implements UserLevelService {
    private final UserMapper userMapper;
    private final UserLevelMapper userLevelMapper;
    @Override
    public List<UserWithLevelVO> getUserWithLevel(String username, Integer userId) {
        List<UserWithLevelVO> list = new ArrayList<>();
        LambdaQueryWrapper<User> like = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(username), User::getUsername, username)
                        .ne(User::getId, userId);
        userMapper.selectList(like)
                .forEach(user -> {
                    UserWithLevelVO userWithLevelVO = new UserWithLevelVO();
                    int level = userLevelMapper.selectOne(new LambdaQueryWrapper<UserLevel>()
                            .eq(UserLevel::getUserId, user.getId())).getLevelId();
                    userWithLevelVO = BeanCopyUtil.INSTANCE.toUserWithLevelVO(user);
                    userWithLevelVO.setLevel(level);
                    list.add(userWithLevelVO);
                });
        return list;
    }

    @Override
    public boolean updateLevelByUserId(UserLevel userLevel) {
        UserLevel user = baseMapper.selectOne(new LambdaQueryWrapper<UserLevel>()
                .eq(UserLevel::getUserId, userLevel.getUserId()));
        if(!ObjectUtil.isEmpty(user)){
            return baseMapper.update(userLevel, new LambdaQueryWrapper<UserLevel>()
                    .eq(UserLevel::getUserId, user.getUserId())) == 1;
        } else {
            return baseMapper.insert(new UserLevel(userLevel.getUserId(), userLevel.getLevelId())) == 1;
        }
    }
}
