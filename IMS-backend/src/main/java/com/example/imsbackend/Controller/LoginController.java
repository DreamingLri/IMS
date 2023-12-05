package com.example.imsbackend.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.vo.LoginVO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.handler.exception.UsernamePasswordException;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
class LoginController {
    private final UserMapper userMapper;
    @PostMapping("/login")
    public AuthUserInfoVO login(@RequestBody LoginVO loginVO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginVO.getUsername());
        User dbUser = userMapper.selectOne(wrapper);
        if(dbUser == null || !Objects.equals(dbUser.getPassword(), loginVO.getPassword())){
            throw new UsernamePasswordException();
        }
        return BeanCopyUtil.INSTANCE.toAuthUserInfo(dbUser);
    }
}
