package com.example.imsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.imsbackend.entity.StudentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (StudentInfo)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-06 20:58:50
 */
@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

}

