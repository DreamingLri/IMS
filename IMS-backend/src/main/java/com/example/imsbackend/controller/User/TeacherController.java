package com.example.imsbackend.controller.User;

import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
@Validated
public class TeacherController {
    private final UserService userService;

    @GetMapping("/listTeacher")
    public List<AuthUserInfoVO> studentList(String username){
        return userService.listTeacher(username);
    }

    //查询
    @GetMapping("/getUserById/{id}")
    public AuthUserInfoVO getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }
    //新增
    @PostMapping("/insertUser")
    public boolean insertTeacher(@Valid @RequestBody InsertUserDTO insertUserDTO){
        return userService.insertTeacher(insertUserDTO);
    }
    //更新
    @PostMapping("/updateUserById")
    public boolean updateUserById(@Valid @RequestBody UpdateUserDTO updateUserDTO){
        return userService.updateUserById(updateUserDTO);
    }
    //删除
    @DeleteMapping("/deleteUserById/{id}")
    public boolean deleteUserById(@PathVariable Integer id){
        return userService.deleteUserById(id);
    }
}
