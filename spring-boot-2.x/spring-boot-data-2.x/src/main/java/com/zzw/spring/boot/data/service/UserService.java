package com.zzw.spring.boot.data.service;

import com.zzw.spring.boot.data.domain.model.User;
import com.zzw.spring.boot.data.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户服务
 *
 * @author zhaozhiwei
 * @date 2019/5/6 14:49
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user){
        userMapper.insertUser(user);
    }
}
