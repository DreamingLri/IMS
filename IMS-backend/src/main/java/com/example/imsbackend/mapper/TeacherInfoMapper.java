package com.example.imsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.imsbackend.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (TeacherInfo)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-06 20:59:03
 */
@Mapper
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {

}

