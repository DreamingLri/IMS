package com.example.imsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.School;

import java.util.List;


public interface SchoolService extends IService<School> {

    List<School> getAllSchool();

    boolean insertSchool(School school);

    boolean updateSchool(School school);

    boolean deleteSchoolById(Integer id);
}
