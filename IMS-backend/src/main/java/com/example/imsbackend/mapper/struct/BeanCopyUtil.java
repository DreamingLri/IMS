package com.example.imsbackend.mapper.struct;

import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanCopyUtil {
    BeanCopyUtil INSTANCE = Mappers.getMapper(BeanCopyUtil.class);

    AuthUserInfoVO toAuthUserInfo(User user);
    User toUser(InsertUserDTO insertUserDTO);

    User toUser(UpdateUserDTO updateUserDTO);
}