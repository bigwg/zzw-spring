package com.zzw.spring.boot.base.dao;

import com.zzw.spring.boot.base.domain.Student;
import org.apache.ibatis.annotations.Param;

/**
 * StudentDao
 *
 * @author zhaozhiwei
 * @since 2020/4/11 11:53
 */
public interface StudentDao {

    int insertStudent(Student object);

    int updateStudent(Student object);

    Student getStudent(@Param("id") Integer id);

    int updateStudentAge(@Param("id") Integer id, @Param("expectAge") Integer expectAge, @Param("updateAge") Integer updateAge);
}
