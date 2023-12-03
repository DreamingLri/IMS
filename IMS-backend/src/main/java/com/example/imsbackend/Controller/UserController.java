package com.example.imsbackend.Controller;

import com.example.imsbackend.entity.User;
import com.example.imsbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/listUser")
    public List<User> userList(){
        return userService.listUser();
    }

    //查询
    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }
    //新增
    @PostMapping("/insertUser")
    public boolean insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }
    //更新
    @PostMapping("/updateUserById")
    public boolean updateUserById(@RequestBody User user){
        return userService.updateUserById(user);
    }
    //删除
    @DeleteMapping("/deleteUserById/{id}")
    public boolean deleteUserById(@PathVariable Integer id){
        return userService.deleteUserById(id);
    }
}
