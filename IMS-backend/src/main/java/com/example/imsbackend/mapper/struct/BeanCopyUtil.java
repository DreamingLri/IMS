package com.example.imsbackend.mapper.struct;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.Exams;
import com.example.imsbackend.entity.Score;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.dto.InsertCourseDTO;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.SignUpDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.*;
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
    EvaluationCourseVO toEvaluationCourseVO(Courses courses);

    Courses toCourses(InsertCourseDTO courseDTO);

    EvaluationVO toEvaluationVO(Score score);

    User toUser(UpdateUserDTO updateUserDTO);
    InsertUserDTO toInsertUserDTO(SignUpDTO signUpDTO);
}
