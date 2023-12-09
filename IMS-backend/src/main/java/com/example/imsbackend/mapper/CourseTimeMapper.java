package com.example.imsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.imsbackend.entity.CourseTime;
import org.apache.ibatis.annotations.Mapper;

/**
 * (CourseTime)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-08 10:21:43
 */
@Mapper
public interface CourseTimeMapper extends BaseMapper<CourseTime> {

}

