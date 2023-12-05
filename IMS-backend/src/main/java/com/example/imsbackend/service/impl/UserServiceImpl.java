package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.User;
import com.example.imsbackend.entity.UserLevel;
import com.example.imsbackend.entity.dto.InsertUserDTO;
import com.example.imsbackend.entity.dto.UpdateUserDTO;
import com.example.imsbackend.entity.vo.AuthUserInfoVO;
import com.example.imsbackend.handler.exception.DeleteStudentException;
import com.example.imsbackend.handler.exception.InsertStudentException;
import com.example.imsbackend.mapper.UserLevelMapper;
import com.example.imsbackend.mapper.UserMapper;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.imsbackend.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.example.imsbackend.constants.OtherConstants.STUDENT_ID;

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

    @Override
    public List<AuthUserInfoVO> listUser(String username) {
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
    public AuthUserInfoVO getUserById(Integer id) {
        return BeanCopyUtil.INSTANCE.toAuthUserInfo(baseMapper.selectById(id));
    }

    @Override
    public boolean insertUser(InsertUserDTO insertUserDTO) {
        User user =  BeanCopyUtil.INSTANCE.toUser(insertUserDTO);
        if(baseMapper.insert(user) == 0){
            throw new InsertStudentException();
        }
        if(userLevelMapper.insert(new UserLevel(user.getId(), STUDENT_ID)) == 0){
            throw new InsertStudentException();
        }
        return true;

//        return baseMapper.insert(user) == 1;
    }

    @Override
    public boolean updateUserById(UpdateUserDTO updateUserDTO) {
        User user = BeanCopyUtil.INSTANCE.toUser(updateUserDTO);
        return baseMapper.updateById(user) == 1;
    }

    @Override
    @Transactional
    public boolean deleteUserById(Integer id) {
        if(baseMapper.deleteById(id) == 0){
            throw new DeleteStudentException();
        }
        if(userLevelMapper.delete(new LambdaQueryWrapper<UserLevel>().eq(UserLevel::getUserId, id)) == 0){
            throw new DeleteStudentException();
        }
        return true;
    }
}

