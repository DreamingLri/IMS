package com.example.imsbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserLevel;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.SignUpDTO;
import com.example.imsbackend.entity.vo.LoginVO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.handler.exception.UsernamePasswordException;
import com.example.imsbackend.mapper.UserLevelMapper;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.example.imsbackend.constants.OtherConstants.*;

@RestController
@RequiredArgsConstructor
class LoginController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserLevelMapper userLevelMapper;
    @PostMapping("/login")
    public AuthUserInfoVO login(@RequestBody LoginVO loginVO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNetId, loginVO.getUsername());
        User dbUser = userMapper.selectOne(wrapper);
        if(dbUser == null || !Objects.equals(dbUser.getPassword(), loginVO.getPassword())){
            throw new UsernamePasswordException();
        }
        int level = userLevelMapper.selectOne(new LambdaQueryWrapper<UserLevel>()
                .eq(UserLevel::getUserId, dbUser.getId())).getLevelId();
        AuthUserInfoVO returnUser = BeanCopyUtil.INSTANCE.toAuthUserInfo(dbUser);
        returnUser.setLevel(level);
        return returnUser;
    }

    @PostMapping("/signUp")
    public boolean signUp(@RequestBody SignUpDTO signUpDTO){
        try{
            int level = signUpDTO.getLevel();
            InsertUserDTO insertUserDTO = BeanCopyUtil.INSTANCE.toInsertUserDTO(signUpDTO);
            if(level == ADMIN_ID){
                return userService.insertAdmin(insertUserDTO);
            } else if (level == TEACHER_ID) {
                return userService.insertTeacher(insertUserDTO);
            } else if (level == STUDENT_ID){
                return userService.insertStudent(insertUserDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
