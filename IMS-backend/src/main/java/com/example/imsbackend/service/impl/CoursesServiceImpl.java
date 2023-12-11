package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.Exams;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.entity.vo.CourseVO;
import com.example.imsbackend.handler.exception.InsertCourseException;
import com.example.imsbackend.handler.exception.UpdateCourseException;
import com.example.imsbackend.mapper.CoursesMapper;
import com.example.imsbackend.mapper.ExamsMapper;
import com.example.imsbackend.mapper.UserCourseMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (Courses)表服务实现类
 *
 * @author makejava
 * @since 2023-12-05 17:02:30
 */
@Service("coursesService")
@RequiredArgsConstructor
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    private final UserCourseMapper userCourseMapper;
    private final ExamsMapper examsMapper;
    @Override
    public List<Courses> listCourse(String name) {
        LambdaQueryWrapper<Courses> like = new LambdaQueryWrapper<Courses>()
                .like(StringUtils.hasText(name), Courses::getName, name);
        return baseMapper.selectList(like);
    }

    @Override
    public boolean insertCourse(Courses courses) {
        if(baseMapper.insert(courses) == 0){
            throw new InsertCourseException();
        }
        if(Objects.equals(courses.getCourseAssessment(), "闭卷考试") || Objects.equals(courses.getCourseAssessment(), "开卷考试")){
            Exams exam = new Exams();
            exam.setName(courses.getName()+"考试");
            exam.setCourseId(courses.getId());
            examsMapper.insert(exam);
        }
        return true;
    }

    @Override
    public boolean updateCourseById(Courses courses) {
        int id = courses.getId();
        String oldAssessment = baseMapper.selectById(id).getCourseAssessment();
        String newAssessment = courses.getCourseAssessment();
        if(oldAssessment == null){
            if((newAssessment.equals("闭卷考试") || newAssessment.equals("开卷考试"))){
                Exams exam = new Exams();
                exam.setName(courses.getName()+"考试");
                exam.setCourseId(courses.getId());
                examsMapper.insert(exam);
                if(baseMapper.updateById(courses) == 0){
                    throw new UpdateCourseException();
                }
            } else {
                if(baseMapper.updateById(courses) == 0){
                    throw new UpdateCourseException();
                }
            }
        } else {
            if((oldAssessment.equals("闭卷考试") || oldAssessment.equals("开卷考试")) && ((newAssessment.equals("闭卷考试") || newAssessment.equals("开卷考试")))){
                if(baseMapper.updateById(courses) == 0){
                    throw new UpdateCourseException();
                }
            } else if ((!oldAssessment.equals("闭卷考试") && !oldAssessment.equals("开卷考试")) && (newAssessment.equals("闭卷考试") || newAssessment.equals("开卷考试"))) {
                Exams exam = new Exams();
                exam.setName(courses.getName()+"考试");
                exam.setCourseId(courses.getId());
                examsMapper.insert(exam);
                if(baseMapper.updateById(courses) == 0){
                    throw new UpdateCourseException();
                }
            } else if ((oldAssessment.equals("闭卷考试") || oldAssessment.equals("开卷考试")) && (newAssessment.equals("论文") || newAssessment.equals("其它"))) {
                if(examsMapper.delete(new LambdaQueryWrapper<Exams>().eq(Exams::getCourseId, id)) == 0){
                    throw new UpdateCourseException();
                }
                if(baseMapper.updateById(courses) == 0){
                    throw new UpdateCourseException();
                }
            } else {
                if(baseMapper.updateById(courses) == 0){
                    throw new UpdateCourseException();
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteCourseById(Integer id) {
        return baseMapper.deleteById(id) == 1;
    }

    @Override
    public List<CourseVO> selectCourseById(Integer id) {
        List<CourseVO> courseVOS = new ArrayList<>();
        baseMapper.selectList(new LambdaQueryWrapper<>())
                .forEach(courses -> {
                    CourseVO courseVO = BeanCopyUtil.INSTANCE.toCourseVO(courses);
                    courseVO.setSelected(false);
                    List<UserCourse> list = userCourseMapper.selectList(new LambdaQueryWrapper<UserCourse>().eq(UserCourse::getCourseId, courseVO.getId()));
                    for (UserCourse i : list){
                        if(Objects.equals(i.getUserId(), id)){
                            courseVO.setSelected(true);
                        }
                    }
                    courseVOS.add(courseVO);
                });
        return courseVOS;
    }

    @Override
    public Courses listCourseById(Integer CourseId) {
        return baseMapper.selectById(CourseId);
    }
}

