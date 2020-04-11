package com.zzw.spring.boot.base.dao;

import com.zzw.spring.boot.base.domain.Student;

/**
 * 学生dao
 *
 * @author zhaozhiwei
 * @date 2020/2/23 21:53
 */
public interface StudentDAO {

    void insertStudent(Student student);

    void updateStudent(Student student);
}
