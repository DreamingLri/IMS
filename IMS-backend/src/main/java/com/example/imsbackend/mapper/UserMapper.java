package com.example.imsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.imsbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
