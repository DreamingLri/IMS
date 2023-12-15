package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.MicroMessage;
import com.example.imsbackend.mapper.MicroMessageMapper;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.service.MicroMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("microMessageService")
@RequiredArgsConstructor
public class MicroMessageServiceImpl extends ServiceImpl<MicroMessageMapper, MicroMessage> implements MicroMessageService {

    private final UserMapper userMapper;
    @Override
    public List<MicroMessage> getMessageByUserId(Integer userId) {
        return baseMapper.selectList(new LambdaQueryWrapper<MicroMessage>()
                .eq(MicroMessage::getToUserId, userId));
    }

    @Override
    public boolean sendMessage(MicroMessage message) {
        if(ObjectUtil.isEmpty(message.getToUserName())){
            message.setToUserName(userMapper.selectById(message.getToUserId()).getUsername());
        }
        return baseMapper.insert(message) == 1;
    }
}
