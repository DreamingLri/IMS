package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.Leave;
import com.example.imsbackend.mapper.LeaveMapper;
import com.example.imsbackend.service.LeaveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("leaveService")
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {

    @Override
    public List<Leave> getLeaveListByUserId(Integer userId) {
        return baseMapper.selectList(new LambdaQueryWrapper<Leave>().eq(Leave::getUserId, userId));
    }

    @Override
    public boolean addLeave(Leave leave) {
            Leave addLeave = new Leave();
            addLeave.setUserId(leave.getUserId());
            addLeave.setLeaveBegin(leave.getLeaveBegin());
            addLeave.setLeaveEnd(leave.getLeaveEnd());
            addLeave.setLeaveReason(leave.getLeaveReason());
            addLeave.setLeaveType(leave.getLeaveType());
            int LeaveTime = (int) ((leave.getLeaveEnd().getTime() - leave.getLeaveBegin().getTime()) / (1000*3600*24));
            addLeave.setLeaveTime(LeaveTime);
            return baseMapper.insert(addLeave) == 1;
    }
}
