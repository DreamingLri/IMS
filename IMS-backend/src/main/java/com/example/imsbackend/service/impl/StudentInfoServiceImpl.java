package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.StudentInfo;
import com.example.imsbackend.mapper.StudentInfoMapper;
import com.example.imsbackend.service.StudentInfoService;
import org.springframework.stereotype.Service;

/**
 * (StudentInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-12-06 20:58:50
 */
@Service("studentInfoService")
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

}
