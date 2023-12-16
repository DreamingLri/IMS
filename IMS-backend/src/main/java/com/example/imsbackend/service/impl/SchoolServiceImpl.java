package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.School;
import com.example.imsbackend.mapper.SchoolMapper;
import com.example.imsbackend.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("schoolService")
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {

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
        return baseMapper.deleteById(id) == 1;
    }
}
