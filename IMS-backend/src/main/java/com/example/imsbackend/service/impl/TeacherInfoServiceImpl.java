package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.TeacherInfo;
import com.example.imsbackend.mapper.TeacherInfoMapper;
import com.example.imsbackend.service.TeacherInfoService;
import org.springframework.stereotype.Service;

/**
 * (TeacherInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-12-06 20:59:03
 */
@Service("teacherInfoService")
public class TeacherInfoServiceImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements TeacherInfoService {

}
