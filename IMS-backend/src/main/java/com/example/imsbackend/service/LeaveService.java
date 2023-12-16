package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.Leave;
import com.example.imsbackend.entity.vo.LeaveVO;

import java.util.List;


public interface LeaveService extends IService<Leave> {
    List<Leave> getLeaveListByUserId(Integer userId);

    boolean addLeave(Leave leave);

    List<LeaveVO> getAllListByAdminId(Integer userId);

    boolean updateLeave(Leave leave);
}
