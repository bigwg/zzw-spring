package com.zzw.spring.boot.data.mapper;

import com.zzw.spring.boot.data.domain.model.User;

/**
 * @author zhaozhiwei
 */
public interface UserMapper {
    int insertUser(User object);
    int updateUser(User object);
}