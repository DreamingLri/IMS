package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.Exams;
import com.example.imsbackend.entity.UserExams;
import com.example.imsbackend.entity.dto.ExamAddStudentDTO;
import com.example.imsbackend.handler.exception.DeleteExamException;
import com.example.imsbackend.handler.exception.InsertExamException;
import com.example.imsbackend.handler.exception.UpdateExamException;
import com.example.imsbackend.mapper.CoursesMapper;
import com.example.imsbackend.mapper.ExamsMapper;
import com.example.imsbackend.mapper.UserExamsMapper;
import com.example.imsbackend.service.ExamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * (Exams)表服务实现类
 *
 * @author makejava
 * @since 2023-12-07 15:49:19
 */
@Service("examsService")
@RequiredArgsConstructor
public class ExamsServiceImpl extends ServiceImpl<ExamsMapper, Exams> implements ExamsService {

    private final UserExamsMapper userExamsMapper;
    private final CoursesMapper coursesMapper;
    @Override
    public List<Exams> listExam(String name) {
        LambdaQueryWrapper<Exams> like = new LambdaQueryWrapper<Exams>()
                .like(StringUtils.hasText(name), Exams::getName, name);
        return baseMapper.selectList(like);
    }

    @Override
    public List<Exams> listExamByUserId(Integer id) {
        return baseMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .filter(exams -> {
                    LambdaQueryWrapper<UserExams> userExamsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userExamsLambdaQueryWrapper.eq(UserExams::getExamId, exams.getId());
                    UserExams userExams = userExamsMapper.selectOne(userExamsLambdaQueryWrapper);
                    if(userExams == null){
                        return false;
                    }
                    return Objects.equals(userExams.getUserId(), id);
                })
                .toList();
    }

    @Override
    public boolean insertExam(Exams exams) {
        if(baseMapper.insert(exams) == 0)
            throw new InsertExamException();
        return true;
    }

    @Override
    public boolean updateExam(Exams exams) {
        if(baseMapper.updateById(exams) == 0)
            throw new UpdateExamException();
        return true;
    }

    @Override
    public boolean deleteExamById(Integer id) {
        Exams exams = baseMapper.selectOne(new LambdaQueryWrapper<Exams>().eq(Exams::getId, id));
        if(!ObjectUtil.isEmpty(exams)){
            if(exams.getCourseId() != null){
                int courseId = exams.getCourseId();
                Courses courses = coursesMapper.selectOne(new LambdaQueryWrapper<Courses>().eq(Courses::getId, courseId));
                if(!ObjectUtil.isEmpty(courses)){
                    throw new DeleteExamException("此为自动创建考试，不能在此页面删除");
                }
            }
        }
        if(baseMapper.deleteById(id) == 0)
            throw new DeleteExamException("删除失败");
        else {
            return true;
        }
    }

    @Override
    public boolean addStudent(ExamAddStudentDTO examAddStudentDTO) {
        int examId = examAddStudentDTO.getId();
        String[] students = examAddStudentDTO.getStudent().split(",");
        for(String student : students){
            int studentId = Integer.parseInt(student);
            userExamsMapper.insert(new UserExams(studentId, examId));
        }
        return true;
    }
}
