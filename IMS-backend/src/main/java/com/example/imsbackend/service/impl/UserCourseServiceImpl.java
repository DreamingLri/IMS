package com.example.imsbackend.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.Score;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.entity.vo.UserCourseVO;
import com.example.imsbackend.handler.exception.SelectCourseException;
import com.example.imsbackend.handler.exception.WithdrawCourseException;
import com.example.imsbackend.mapper.CoursesMapper;
import com.example.imsbackend.mapper.ScoreMapper;
import com.example.imsbackend.mapper.UserCourseMapper;
import com.example.imsbackend.service.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("userCourseService")
@AllArgsConstructor
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements UserCourseService {
    private final CoursesMapper coursesMapper;
    private final ScoreMapper scoreMapper;
    private final UserCourseMapper userCourseMapper;

    @Override
    public boolean selectCourse(UserCourse userCourse) {
        if(baseMapper.insert(userCourse) == 0){
            throw new SelectCourseException();
        }
        return true;
    }

    @Override
    public boolean withdrawCourse(UserCourse userCourse) {
        Score checkScore = scoreMapper.selectOne(new LambdaQueryWrapper<Score>()
                .eq(Score::getUserId, userCourse.getUserId())
                .eq(Score::getCourseId, userCourse.getCourseId()));
        if(ObjectUtil.isEmpty(checkScore)){
            if(baseMapper.delete(new LambdaQueryWrapper<UserCourse>()
                    .eq(UserCourse::getCourseId, userCourse.getCourseId())
                    .eq(UserCourse::getUserId, userCourse.getUserId()))
                    == 0){
                throw new WithdrawCourseException();
            }
            return true;
        } else {
            if(checkScore.getTotalScore() == null && checkScore.getExamScore() == null && checkScore.getStudyScore() == null){
                if(baseMapper.delete(new LambdaQueryWrapper<UserCourse>()
                        .eq(UserCourse::getCourseId, userCourse.getCourseId())
                        .eq(UserCourse::getUserId, userCourse.getUserId()))
                        == 0){
                    throw new WithdrawCourseException();
                }
                return true;
            }
            throw new WithdrawCourseException();
        }
    }

    @Override
    public List<UserCourseVO> listNumberOfStudentSelectCourse() {
        List<UserCourseVO> list = new ArrayList<>();
        List<Courses> courses = coursesMapper.selectList(new LambdaQueryWrapper<>());
        for (Courses course : courses) {
            UserCourseVO userCourseVO = new UserCourseVO();
            userCourseVO.setId(course.getId());
            userCourseVO.setCourseName(course.getName());
            userCourseVO.setTeacher(course.getTeacher());
            userCourseVO.setPlace(course.getPlace());
            if(course.getStudentNumber() != null)
                userCourseVO.setStudentNumber(course.getStudentNumber());
            List<UserCourse> userCourses = baseMapper.selectList(new LambdaQueryWrapper<UserCourse>()
                    .eq(UserCourse::getCourseId, course.getId()));
            userCourseVO.setSelectedNumber(userCourses.size());
            list.add(userCourseVO);
        }
        return list;
    }

    @Override
    public List<UserCourseVO> listNumberOfStudentSelectCourseByUserId(Integer userId) {
        List<UserCourseVO> list = new ArrayList<>();
        List<Courses> courses = coursesMapper.selectList(new LambdaQueryWrapper<>());
        for (Courses course : courses) {
            UserCourse userCourse = userCourseMapper.selectOne(new LambdaQueryWrapper<UserCourse>()
                    .eq(UserCourse::getCourseId, course.getId())
                    .eq(UserCourse::getUserId, userId));
            if(!ObjectUtil.isEmpty(userCourse)){
                UserCourseVO userCourseVO = new UserCourseVO();
                userCourseVO.setId(course.getId());
                userCourseVO.setCourseName(course.getName());
                userCourseVO.setTeacher(course.getTeacher());
                userCourseVO.setPlace(course.getPlace());
                if(course.getStudentNumber() != null)
                    userCourseVO.setStudentNumber(course.getStudentNumber());
                List<UserCourse> userCourses = baseMapper.selectList(new LambdaQueryWrapper<UserCourse>()
                        .eq(UserCourse::getCourseId, course.getId())
                        .ne(UserCourse::getUserId, userId));
                userCourseVO.setSelectedNumber(userCourses.size());

                list.add(userCourseVO);
            }
        }
        return list;
    }
}
