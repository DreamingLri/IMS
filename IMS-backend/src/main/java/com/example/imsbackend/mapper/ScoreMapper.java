package com.example.imsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.imsbackend.entity.Score;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Score)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-11 22:45:50
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

}

