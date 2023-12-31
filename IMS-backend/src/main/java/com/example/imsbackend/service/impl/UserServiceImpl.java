package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.*;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.entity.vo.TeacherNameVO;
import com.example.imsbackend.handler.exception.DeleteStudentException;
import com.example.imsbackend.handler.exception.InsertStudentException;
import com.example.imsbackend.mapper.*;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.imsbackend.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.imsbackend.constants.OtherConstants.*;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-12-03 10:25:06
 */
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserLevelMapper userLevelMapper;
    private final UserCourseMapper userCourseMapper;
    private final SchoolMapper schoolMapper;
    private final UserSchoolMapper userSchoolMapper;

    @Override
    @Transactional
    public List<AuthUserInfoVO> listAll(String username) {
        LambdaQueryWrapper<User> like = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(username), User::getUsername, username);
        return baseMapper.selectList(like)
                .stream()
                .map(BeanCopyUtil.INSTANCE::toAuthUserInfo)
                .toList();
    }

    @Override
    public List<TeacherNameVO> listTeacherName() {
        return baseMapper.selectList(new LambdaQueryWrapper<User>())
                .stream()
                .filter(user -> {
                    LambdaQueryWrapper<UserLevel> userLevelLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userLevelLambdaQueryWrapper.eq(UserLevel::getUserId, user.getId());
                    UserLevel userLevel = userLevelMapper.selectOne(userLevelLambdaQueryWrapper);
                    if(userLevel == null)
                        return false;
                    return Objects.equals(userLevel.getLevelId(), TEACHER_ID);
                })
                .map(BeanCopyUtil.INSTANCE::toTeacherNameVO)
                .toList();
    }

    @Override
    public List<AuthUserInfoVO> listStudentByCourseId(Integer courseId) {
        LambdaQueryWrapper<UserCourse> userCourseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userCourseLambdaQueryWrapper.eq(UserCourse::getCourseId, courseId);
        List<UserCourse> userCourses = userCourseMapper.selectList(userCourseLambdaQueryWrapper);
        List<AuthUserInfoVO> list = new ArrayList<>();
        for(UserCourse i : userCourses){
            User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getCode, i.getUserId()));
            UserLevel userLevel = userLevelMapper.selectOne(new LambdaQueryWrapper<UserLevel>().eq(UserLevel::getUserId, user.getId()));
            if(Objects.equals(userLevel.getLevelId(), STUDENT_ID)){
                list.add(BeanCopyUtil.INSTANCE.toAuthUserInfo(user));
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<AuthUserInfoVO> listStudent(String username) {
        LambdaQueryWrapper<User> like = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(username), User::getUsername, username);
        return baseMapper.selectList(like)
                .stream()
                .filter(user -> {
                    LambdaQueryWrapper<UserLevel> userLevelLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userLevelLambdaQueryWrapper.eq(UserLevel::getUserId, user.getId());
                    UserLevel userLevel = userLevelMapper.selectOne(userLevelLambdaQueryWrapper);
                    if(userLevel == null)
                        return false;
                    return Objects.equals(userLevel.getLevelId(), STUDENT_ID);
                })
                .map(BeanCopyUtil.INSTANCE::toAuthUserInfo)
                .toList();
    }

    @Override
    @Transactional
    public List<AuthUserInfoVO> listTeacher(String username) {
        LambdaQueryWrapper<User> like = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(username), User::getUsername, username);
        return baseMapper.selectList(like)
                .stream()
                .filter(user -> {
                    LambdaQueryWrapper<UserLevel> userLevelLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userLevelLambdaQueryWrapper.eq(UserLevel::getUserId, user.getId());
                    UserLevel userLevel = userLevelMapper.selectOne(userLevelLambdaQueryWrapper);
                    if(userLevel == null)
                        return false;
                    return Objects.equals(userLevel.getLevelId(), TEACHER_ID);
                })
                .map(BeanCopyUtil.INSTANCE::toAuthUserInfo)
                .toList();
    }

    @Override
    public AuthUserInfoVO getUserById(Integer id) {
        return BeanCopyUtil.INSTANCE.toAuthUserInfo(baseMapper.selectById(id));
    }

    @Override
    public boolean insertStudent(InsertUserDTO insertUserDTO) {
        User user = BeanCopyUtil.INSTANCE.toUser(insertUserDTO);
        user.setEarnedCredit(0.0);
        user.setGradePoints(0.0);
        if(baseMapper.insert(user) == 0){
            throw new InsertStudentException("创建学生失败，学号重复或NetId重复");
        }
        if(userLevelMapper.insert(new UserLevel(user.getId(), STUDENT_ID)) == 0){
            throw new InsertStudentException(null);
        }
        String school = insertUserDTO.getAffiliatedSchool();
        int schoolId =  schoolMapper.selectOne(new LambdaQueryWrapper<School>()
                .eq(School::getName, school)).getId();
        userSchoolMapper.insert(new UserSchool(user.getId(), schoolId));
        return true;
    }

    @Override
    public boolean insertTeacher(InsertUserDTO insertUserDTO) {
        User user = BeanCopyUtil.INSTANCE.toUser(insertUserDTO);
        if(baseMapper.insert(user) == 0){
            throw new InsertStudentException("创建教师失败，学号重复或NetId重复");
        }
        if(userLevelMapper.insert(new UserLevel(user.getId(), TEACHER_ID)) == 0){
            throw new InsertStudentException(null);
        }
        String school = insertUserDTO.getAffiliatedSchool();
        int schoolId =  schoolMapper.selectOne(new LambdaQueryWrapper<School>()
                .eq(School::getName, school)).getId();
        userSchoolMapper.insert(new UserSchool(user.getId(), schoolId));
        return true;
    }

    @Override
    public boolean insertAdmin(InsertUserDTO insertUserDTO) {
        User user = BeanCopyUtil.INSTANCE.toUser(insertUserDTO);
        if(baseMapper.insert(user) == 0){
            throw new InsertStudentException("创建管理员失败，学号重复或NetId重复");
        }
        if(userLevelMapper.insert(new UserLevel(user.getId(), ADMIN_ID)) == 0){
            throw new InsertStudentException(null);
        }
        String school = insertUserDTO.getAffiliatedSchool();
        int schoolId =  schoolMapper.selectOne(new LambdaQueryWrapper<School>()
                .eq(School::getName, school)).getId();
        userSchoolMapper.insert(new UserSchool(user.getId(), schoolId));
        return true;
    }

    @Override
    @Transactional
    public boolean insertUser(InsertUserDTO insertUserDTO) {
        User user = BeanCopyUtil.INSTANCE.toUser(insertUserDTO);
        if(baseMapper.insert(user) == 0){
            throw new InsertStudentException(null);
        }
        return true;

//        return baseMapper.insert(user) == 1;
    }

    @Override
    public boolean updateUserById(UpdateUserDTO updateUserDTO) {
        User user = BeanCopyUtil.INSTANCE.toUser(updateUserDTO);
        UserSchool old_school = userSchoolMapper.selectOne(new LambdaQueryWrapper<UserSchool>()
                .eq(UserSchool::getUserId, user.getId()));
        School school = schoolMapper.selectOne(new LambdaQueryWrapper<School>()
                .eq(School::getName, user.getAffiliatedSchool()));
        if(!ObjectUtil.isEmpty(old_school) && !ObjectUtil.isEmpty(school)){
            if(old_school.getSchoolId() != school.getId()){
                userSchoolMapper.delete(new LambdaQueryWrapper<UserSchool>()
                        .eq(UserSchool::getUserId, user.getId())
                        .eq(UserSchool::getSchoolId, old_school.getSchoolId()));
                userSchoolMapper.insert(new UserSchool(user.getId(), school.getId()));
            }
        } else {
            userSchoolMapper.insert(new UserSchool(user.getId(), school.getId()));
        }
        return baseMapper.updateById(user) == 1;
    }

    @Override
    @Transactional
    public boolean deleteUserById(Integer id) {
        if(baseMapper.deleteById(id) == 0){
            throw new DeleteStudentException();
        }
        UserLevel userLevel = userLevelMapper.selectOne(new LambdaQueryWrapper<UserLevel>().eq(UserLevel::getUserId, id));
        if(!ObjectUtil.isEmpty(userLevel)){
            if(userLevelMapper.delete(new LambdaQueryWrapper<UserLevel>().eq(UserLevel::getUserId, id)) == 0){
                throw new DeleteStudentException();
            }
        }
        UserSchool userSchool = userSchoolMapper.selectOne(new LambdaQueryWrapper<UserSchool>().eq(UserSchool::getUserId, id));
        if(!ObjectUtil.isEmpty(userSchool)){
            if(userSchoolMapper.delete(new LambdaQueryWrapper<UserSchool>().eq(UserSchool::getUserId, id)) == 0){
                throw new DeleteStudentException();
            }
        }
        return true;
    }
}

