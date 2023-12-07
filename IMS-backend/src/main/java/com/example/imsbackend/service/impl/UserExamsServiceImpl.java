package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.UserExams;
import com.example.imsbackend.mapper.UserExamsMapper;
import com.example.imsbackend.service.UserExamsService;
import org.springframework.stereotype.Service;

/**
 * (UserExams)表服务实现类
 *
 * @author makejava
 * @since 2023-12-07 15:49:31
 */
@Service("userExamsService")
public class UserExamsServiceImpl extends ServiceImpl<UserExamsMapper, UserExams> implements UserExamsService {

}
