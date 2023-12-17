package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.School;
import com.example.imsbackend.entity.UserSchool;
import com.example.imsbackend.handler.exception.SchoolDeleteException;
import com.example.imsbackend.mapper.SchoolMapper;
import com.example.imsbackend.mapper.UserSchoolMapper;
import com.example.imsbackend.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("schoolService")
@RequiredArgsConstructor
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
    private final UserSchoolMapper userSchoolMapper;
    @Override
    public List<School> getAllSchool() {
        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public boolean insertSchool(School school) {
        return baseMapper.insert(school) == 1;
    }

    @Override
    public boolean updateSchool(School school) {
        return baseMapper.updateById(school) == 1;
    }

    @Override
    public boolean deleteSchoolById(Integer id) {
        UserSchool userSchool = userSchoolMapper.selectOne(new LambdaQueryWrapper<UserSchool>()
                .eq(UserSchool::getSchoolId, id));
        if(ObjectUtil.isEmpty(userSchool)){
            return baseMapper.deleteById(id) == 1;
        } else {
            throw new SchoolDeleteException();
        }
    }
}
