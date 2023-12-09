package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.handler.exception.InsertCourseException;
import com.example.imsbackend.handler.exception.InsertStudentException;
import com.example.imsbackend.mapper.CoursesMapper;
import com.example.imsbackend.mapper.UserCourseMapper;
import com.example.imsbackend.mapper.UserLevelMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * (Courses)表服务实现类
 *
 * @author makejava
 * @since 2023-12-05 17:02:30
 */
@Service("coursesService")
@RequiredArgsConstructor
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    private final UserCourseMapper userCourseMapper;
    @Override
    public List<Courses> listCourse(String name) {
        LambdaQueryWrapper<Courses> like = new LambdaQueryWrapper<Courses>()
                .like(StringUtils.hasText(name), Courses::getName, name);
        return baseMapper.selectList(like);
    }

    @Override
    public boolean insertCourse(Courses courses) {
        if(baseMapper.insert(courses) == 0){
            throw new InsertCourseException();
        }
        return true;
    }

    @Override
    public boolean updateCourseById(Courses courses) {
        return baseMapper.updateById(courses) == 1;
    }

    @Override
    public boolean deleteCourseById(Integer id) {
        return baseMapper.deleteById(id) == 1;
    }

    @Override
    public List<Courses> selectCourseById(Integer id) {
        return baseMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .filter(courses -> {
                    LambdaQueryWrapper<UserCourse> userCourseLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userCourseLambdaQueryWrapper.eq(UserCourse::getCourseId, courses.getId());
                    List<UserCourse> userCourses = userCourseMapper.selectList(userCourseLambdaQueryWrapper).stream().toList();
                    for(UserCourse i : userCourses){
                        if(Objects.equals(i.getUserId(), id))
                            return true;
                    }
                    return false;
                })
                .toList();
    }

    @Override
    public Courses listCourseById(Integer CourseId) {
        return baseMapper.selectById(CourseId);
    }
}

