package com.zzw.spring.boot.base.service;

import com.zzw.spring.boot.base.dao.StudentDao;
import com.zzw.spring.boot.base.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StudentService
 *
 * @author zhaozhiwei
 * @since 2020/4/11 11:53
 */
@Slf4j
@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public Student get(Integer id){
        return this.getStudent(id);
    }

    @Transactional
    public Student getStudent(Integer id) {
        return studentDao.getStudent(id);
    }

    @Transactional
    public void updateStudent(Integer id) {
        Student student = studentDao.getStudent(id);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result = studentDao.updateStudentAge(student.getId(), student.getAge(), 18);
        log.info("updateStudentAge result: {}", result);
    }
}
