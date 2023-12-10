package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserExams;
import com.example.imsbackend.mapper.UserExamsMapper;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.service.UserExamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (UserExams)表服务实现类
 *
 * @author makejava
 * @since 2023-12-07 15:49:31
 */
@Service("userExamsService")
@RequiredArgsConstructor
public class UserExamsServiceImpl extends ServiceImpl<UserExamsMapper, UserExams> implements UserExamsService {

    private final UserMapper userMapper;
    @Override
    public List<User> listStudentByExamId(Integer examId) {
        return userMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .filter(userExams -> {
                    UserExams userExam = baseMapper.selectOne(new LambdaQueryWrapper<UserExams>().eq(UserExams::getUserId, userExams.getId()));
                    return !ObjectUtil.isEmpty(userExam);
                })
                .toList();
    }
}
