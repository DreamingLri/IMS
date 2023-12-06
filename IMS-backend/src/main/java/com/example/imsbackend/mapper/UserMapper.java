package com.example.imsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.vo.TeacherNameVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select user.id, user.username from user left join user_level ul on user.id = ul.user_id where ul.level_id = 2 or ul.laevl_id = 4")
    List<TeacherNameVO> listTeacherName();
}
