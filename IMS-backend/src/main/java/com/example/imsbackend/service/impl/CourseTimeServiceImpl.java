package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.CourseTime;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.handler.exception.InsertCourseTimeException;
import com.example.imsbackend.mapper.CourseTimeMapper;
import com.example.imsbackend.mapper.UserCourseMapper;
import com.example.imsbackend.service.CourseTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (CourseTime)表服务实现类
 *
 * @author makejava
 * @since 2023-12-08 10:21:43
 */
@Service("courseTimeService")
@RequiredArgsConstructor
public class CourseTimeServiceImpl extends ServiceImpl<CourseTimeMapper, CourseTime> implements CourseTimeService {

    private final UserCourseMapper userCourseMapper;
    @Override
    public List<CourseTime> getCourseTimeById(int id) {
        return baseMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .filter(courseTime -> courseTime.getCourseId() == id)
                .toList();
    }

    @Override
    public List<CourseTime> getCourseTimeByUserId(int id) {
        List<CourseTime> list = new ArrayList<>();
        List<UserCourse> userCourses = userCourseMapper.selectList(new LambdaQueryWrapper<UserCourse>()
                .eq(UserCourse::getUserId, id));
        for(UserCourse i : userCourses){
            List<CourseTime> courseTimes = baseMapper.selectList(new LambdaQueryWrapper<>())
                    .stream()
                    .filter(courseTime -> Objects.equals(courseTime.getCourseId(), i.getCourseId()))
                    .toList();
            list.addAll(courseTimes);
        }
        return list;
    }

    @Override
    public boolean updateCourseTimeById(CourseTime courseTime) {
        return baseMapper.updateById(courseTime) == 1;
    }

    @Override
    public boolean deleteCourseTimeById(Integer id) {
        return baseMapper.deleteById(id) == 1;
    }

    @Override
    public boolean insertCourseTime(CourseTime courseTime) {
        List<CourseTime> courseTimes = baseMapper.selectList(new LambdaQueryWrapper<CourseTime>()
                .eq(CourseTime::getSession, courseTime.getSession())
                .eq(CourseTime::getWeekday, courseTime.getWeekday())
                .eq(CourseTime::getCourseId, courseTime.getId()));
        if(!ObjectUtil.isEmpty(courseTimes))
            throw new InsertCourseTimeException("课程时间重复");
        else {
            return baseMapper.insert(courseTime) == 1;
        }
    }

}
