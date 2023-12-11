package com.example.imsbackend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.handler.exception.SelectCourseException;
import com.example.imsbackend.handler.exception.WithdrawCourseException;
import com.example.imsbackend.mapper.UserCourseMapper;
import com.example.imsbackend.service.UserCourseService;
import org.springframework.stereotype.Service;


@Service("userCourseService")
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements UserCourseService {
    @Override
    public boolean selectCourse(UserCourse userCourse) {
        if(baseMapper.insert(userCourse) == 0){
            throw new SelectCourseException();
        }
        return true;
    }

    @Override
    public boolean withdrawCourse(UserCourse userCourse) {
//        if(baseMapper.deleteById(userCourse) == 0){
//            throw new WithdrawCourseException();
//        }
//        return true;
        if(baseMapper.delete(new LambdaQueryWrapper<UserCourse>()
                .eq(UserCourse::getCourseId, userCourse.getCourseId())
                .eq(UserCourse::getUserId, userCourse.getUserId()))
                == 0){
            throw new WithdrawCourseException();
        }
        return true;
    }
}
