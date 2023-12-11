package com.example.imsbackend.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.imsbackend.entity.Exams;
import com.example.imsbackend.entity.dto.ExamAddStudentDTO;
import com.example.imsbackend.entity.vo.ExamsVO;

import java.util.List;


/**
 * (Exams)表服务接口
 *
 * @author makejava
 * @since 2023-12-07 15:49:19
 */
public interface ExamsService extends IService<Exams> {

    List<Exams> listExam(String name);

    List<Exams> listExamByUserId(Integer id);

    boolean insertExam(Exams exams);

    boolean updateExam(Exams exams);

    boolean deleteExamById(Integer id);

    boolean addStudent(ExamAddStudentDTO examAddStudentDTO);
}
