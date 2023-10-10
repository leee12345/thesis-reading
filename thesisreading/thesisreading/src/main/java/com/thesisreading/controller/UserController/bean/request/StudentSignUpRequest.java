package com.thesisreading.controller.UserController.bean.request;

import com.thesisreading.model.Student;
import com.thesisreading.model.User;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentSignUpRequest {
    /**
     * 用户ID
     */
    @Expose
    private String userid;

    /**
     * 姓名
     */
    @Expose
    private String studentname;

    /**
     * 用户密码
     */
    @Expose
    private String password;

    /**
     * 用户类型，2代表学生
     */
    @Expose
    private Integer role;

    /**
     * 性别
     */
    @Expose
    private String sex;

    /**
     * 专业
     */
    @Expose
    private String major;

    /**
     * 班级
     */
    @Expose
    private String classname;
}
