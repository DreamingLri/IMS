package com.example.imsbackend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserExams;
import com.example.imsbackend.entity.UserLevel;
import com.example.imsbackend.entity.vo.UserExamVO;
import com.example.imsbackend.handler.exception.SelectExamException;
import com.example.imsbackend.handler.exception.WithdrawExamException;
import com.example.imsbackend.mapper.UserExamsMapper;
import com.example.imsbackend.mapper.UserLevelMapper;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.UserExamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.imsbackend.constants.OtherConstants.STUDENT_ID;
import static com.example.imsbackend.constants.OtherConstants.TEACHER_ID;

/**
 * (UserExams)表服务实现类
 *
 * @author makejava
 * @since 2023-12-07 15:49:31
 */
@Service("userExamsService")
@RequiredArgsConstructor
public class UserExamsServiceImpl extends ServiceImpl<UserExamsMapper, UserExams> implements UserExamsService {

    private final UserMapper userMapper;
    private final UserLevelMapper userLevelMapper;

    @Override
    public List<User> listStudentByExamId(Integer examId) {
        return userMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .filter(userExams -> {
                    UserExams userExam = baseMapper.selectOne(new LambdaQueryWrapper<UserExams>().eq(UserExams::getUserId, userExams.getId()));
                    return !ObjectUtil.isEmpty(userExam);
                })
                .toList();
    }

    @Override
    public List<UserExamVO> listUserSelectedExam(Integer examId) {
        List<UserExamVO> userExamVOs = new ArrayList<>();
        userMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .filter(user -> {
                    LambdaQueryWrapper<UserLevel> userLevelLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userLevelLambdaQueryWrapper.eq(UserLevel::getUserId, user.getId());
                    UserLevel userLevel = userLevelMapper.selectOne(userLevelLambdaQueryWrapper);
                    if(userLevel == null)
                        return false;
                    return Objects.equals(userLevel.getLevelId(), STUDENT_ID);
                })
                .forEach(user -> {
            UserExamVO userExamVO = BeanCopyUtil.INSTANCE.toUserExamVO(user);
            LambdaQueryWrapper<UserExams> userExamsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userExamsLambdaQueryWrapper.eq(UserExams::getExamId, examId).eq(UserExams::getUserId, userExamVO.getId());
            UserExams userExams = baseMapper.selectOne(userExamsLambdaQueryWrapper);
            if(ObjectUtil.isEmpty(userExams)){
                userExamVO.setSelected(false);
                userExamVOs.add(userExamVO);
            } else {
                userExamVO.setSelected(true);
                userExamVOs.add(userExamVO);
            }
        });
        return userExamVOs;
    }

    //选考退考
    @Override
    public boolean selectExam(UserExams userExams) {
        if(baseMapper.insert(userExams) == 0)
            throw new SelectExamException();
        return true;
    }

    @Override
    public boolean withdrawExam(UserExams userExams) {
        if(baseMapper.delete(new LambdaQueryWrapper<UserExams>()
                .eq(UserExams::getUserId, userExams.getUserId())
                .eq(UserExams::getExamId, userExams.getExamId()))
                == 0){
            throw new WithdrawExamException();
        }
        return true;
    }
}
