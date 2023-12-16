package com.example.imsbackend.controller;

import com.example.imsbackend.entity.Leave;
import com.example.imsbackend.entity.vo.LeaveVO;
import com.example.imsbackend.mapper.LeaveMapper;
import com.example.imsbackend.service.LeaveService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;
    @GetMapping("/getLeaveListByUserId")
    public List<Leave> getLeaveListByUserId(Integer userId){
        return leaveService.getLeaveListByUserId(userId);
    }

    @PostMapping("/addLeave")
    public boolean addLeave(@RequestBody Leave leave){
        return leaveService.addLeave(leave);
    }

    @GetMapping("/getAllListByAdminId")
    public List<LeaveVO> getAllListByAdminId(Integer userId){
        return leaveService.getAllListByAdminId(userId);
    }

    @PostMapping("/updateLeave")
    public boolean updateLeave(@RequestBody Leave leave){
        return leaveService.updateLeave(leave);
    }
}
