package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.UserSchool;
import com.example.imsbackend.mapper.UserSchoolMapper;
import com.example.imsbackend.service.UserSchoolService;
import org.springframework.stereotype.Service;
@Service("userSchoolService")
public class UserSchoolServiceImpl extends ServiceImpl<UserSchoolMapper, UserSchool> implements UserSchoolService {

}
