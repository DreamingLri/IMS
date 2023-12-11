package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.CourseTime;
import com.example.imsbackend.mapper.CourseTimeMapper;
import com.example.imsbackend.service.CourseTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (CourseTime)表服务实现类
 *
 * @author makejava
 * @since 2023-12-08 10:21:43
 */
@Service("courseTimeService")
@RequiredArgsConstructor
public class CourseTimeServiceImpl extends ServiceImpl<CourseTimeMapper, CourseTime> implements CourseTimeService {

    @Override
    public List<CourseTime> getCourseTimeById(int id) {
        return baseMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .filter(courseTime -> courseTime.getCourseId() == id)
                .toList();
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
                .eq(CourseTime::getWeekday, courseTime.getWeekday()));
        if(!ObjectUtil.isEmpty(courseTimes))
            return false;
        else {
            return baseMapper.insert(courseTime) == 1;
        }
    }

}
