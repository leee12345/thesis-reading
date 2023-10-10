package com.thesisreading.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageJumpUtils {

    /**
     * 跳转到教师注册的页面
     * @return
     */
    @RequestMapping(value = "/teacher/signup")
    public String toTeacherSignup(){
        return "teacher/signup";
    }

    /**
     * 跳转到学生注册的页面
     * @return
     */
    @RequestMapping(value = "/student/signup")
    public String toStudentSignup(){
        return "student/signup";
    }

    /**
     * 跳转到学生的主页
     * @return
     */
    @RequestMapping(value = "/student/index")
    public String toStudentIndex(){
        return "student/index";
    }


    @RequestMapping(value = "/teacher/index")
    public String toTeacherIndex(){
        return "teacher/index";
    }


}
