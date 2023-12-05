package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.mapper.CoursesMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.CoursesService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * (Courses)表服务实现类
 *
 * @author makejava
 * @since 2023-12-05 17:02:30
 */
@Service("coursesService")
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    @Override
    public List<Courses> listCourse(String name) {
        LambdaQueryWrapper<Courses> like = new LambdaQueryWrapper<Courses>()
                .like(StringUtils.hasText(name), Courses::getName, name);
        List<Courses> list = baseMapper.selectList(like);
        return list;
    }

    @Override
    public boolean insertCourse(Courses courses) {
        return baseMapper.insert(courses) == 1;
    }

    @Override
    public boolean updateCourseById(Courses courses) {
        return baseMapper.updateById(courses) == 1;
    }

    @Override
    public boolean deleteCourseById(Integer id) {
        return baseMapper.deleteById(id) == 1;
    }
}

