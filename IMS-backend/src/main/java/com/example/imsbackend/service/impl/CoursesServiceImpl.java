package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.mapper.CoursesMapper;
import com.example.imsbackend.service.CoursesService;
import org.springframework.stereotype.Service;

/**
 * (Courses)表服务实现类
 *
 * @author makejava
 * @since 2023-12-05 17:02:30
 */
@Service("coursesService")
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

}

