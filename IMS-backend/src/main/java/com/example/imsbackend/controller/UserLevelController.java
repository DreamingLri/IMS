package com.example.imsbackend.controller;

import com.example.imsbackend.entity.UserLevel;
import com.example.imsbackend.entity.vo.UserWithLevelVO;
import com.example.imsbackend.mapper.UserLevelMapper;
import com.example.imsbackend.service.UserLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-level")
@RequiredArgsConstructor
public class UserLevelController {
    private final UserLevelService userLevelService;
    @GetMapping("/getUserWithLevel")
    public List<UserWithLevelVO> getUserWithLevel(String username){
        return userLevelService.getUserWithLevel(username);
    }

    @PostMapping("/updateLevelByUserId")
    public boolean updateLevelByUserId(@RequestBody UserLevel userLevel){
        return userLevelService.updateLevelByUserId(userLevel);
    }
}
