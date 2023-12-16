package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.Leave;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserSchool;
import com.example.imsbackend.entity.vo.LeaveVO;
import com.example.imsbackend.mapper.LeaveMapper;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.mapper.UserSchoolMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("leaveService")
@RequiredArgsConstructor
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {
    private final UserSchoolMapper userSchoolMapper;
    private final LeaveMapper leaveMapper;
    private final UserMapper userMapper;

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

    @Override
    public List<LeaveVO> getAllListByAdminId(Integer userId) {
        List<LeaveVO> list = new ArrayList<>();
        int schoolId =  userSchoolMapper.selectOne(new LambdaQueryWrapper<UserSchool>()
                .eq(UserSchool::getUserId, userId)).getSchoolId();
        userSchoolMapper.selectList(new LambdaQueryWrapper<UserSchool>()
                .eq(UserSchool::getSchoolId, schoolId)
                .ne(UserSchool::getUserId, userId))
                .forEach(userSchool -> {
                    leaveMapper.selectList(new LambdaQueryWrapper<Leave>()
                            .eq(Leave::getUserId, userSchool.getUserId()))
                            .forEach(leave -> {
                                LeaveVO leaveVO = BeanCopyUtil.INSTANCE.toLeaveVO(leave);
                                leaveVO.setName(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, leaveVO.getUserId())).getUsername());
                                list.add(leaveVO);
                            });
                });
        return list;
    }

    @Override
    public boolean updateLeave(Leave leave) {
        return baseMapper.updateById(leave) == 1;
    }
}
