package com.example.imsbackend.mapper.struct;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.Exams;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.entity.vo.CourseVO;
import com.example.imsbackend.entity.vo.TeacherNameVO;
import com.example.imsbackend.entity.vo.UserExamVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanCopyUtil {
    BeanCopyUtil INSTANCE = Mappers.getMapper(BeanCopyUtil.class);

    AuthUserInfoVO toAuthUserInfo(User user);
    TeacherNameVO toTeacherNameVO(User user);
    User toUser(InsertUserDTO insertUserDTO);

    UserExamVO toUserExamVO(User user);
    CourseVO toCourseVO(Courses courses);

    User toUser(UpdateUserDTO updateUserDTO);
}
