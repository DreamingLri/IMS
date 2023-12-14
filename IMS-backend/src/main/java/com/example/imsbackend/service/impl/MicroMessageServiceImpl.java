package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.MicroMessage;
import com.example.imsbackend.mapper.MicroMessageMapper;
import com.example.imsbackend.service.MicroMessageService;
import org.springframework.stereotype.Service;

/**
 * (MicroMessage)表服务实现类
 *
 * @author makejava
 * @since 2023-12-14 21:45:19
 */
@Service("microMessageService")
public class MicroMessageServiceImpl extends ServiceImpl<MicroMessageMapper, MicroMessage> implements MicroMessageService {

}
