package com.zzw.spring.boot.base.contorller;

import com.zzw.spring.boot.base.domain.Student;
import com.zzw.spring.boot.base.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * StudentController
 *
 * @author zhaozhiwei
 * @since 2020/4/11 12:00
 */
@Slf4j
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("getStudent")
    public Student getStudent(@RequestParam("id") Integer id){
        return studentService.getStudent(id);
    }

    @GetMapping("updateStudentAge")
    public String updateStudentAge(@RequestParam("id") Integer id){
        studentService.updateStudent(id);
        return "ok";
    }
}
