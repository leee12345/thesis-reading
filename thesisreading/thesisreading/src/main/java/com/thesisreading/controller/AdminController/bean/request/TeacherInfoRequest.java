package com.thesisreading.controller.AdminController.bean.request;

import com.thesisreading.model.Tc;
import com.thesisreading.model.Teacher;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfoRequest {

    // basic info

    /**
     * 教师ID
     */
    @Expose
    private String teacherId;

    /**
     * 姓名
     */
    @Expose
    private String name;

    /**
     * 性别
     */
    @Expose
    private String sex;

    /**
     * 教师职称
     */
    @Expose
    private String protitle;

    /**
     * 手机号
     */
    @Expose
    private String phone;

}
