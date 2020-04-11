package com.zzw.spring.boot.base.service;

import com.zzw.spring.boot.base.dao.StudentDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 学生服务类
 *
 * @author zhaozhiwei
 * @date 2020/2/23 23:22
 */
@Service
public class StudentService {

    @Resource
    private StudentDAO studentDAO;



}
