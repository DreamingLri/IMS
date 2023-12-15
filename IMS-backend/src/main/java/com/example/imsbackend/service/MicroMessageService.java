package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.MicroMessage;

import java.util.List;


public interface MicroMessageService extends IService<MicroMessage> {

    List<MicroMessage> getMessageByUserId(Integer userId);

    boolean sendMessage(MicroMessage message);
}
