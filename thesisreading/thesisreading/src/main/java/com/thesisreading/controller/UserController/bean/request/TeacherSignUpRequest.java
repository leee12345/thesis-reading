package com.thesisreading.controller.UserController.bean.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherSignUpRequest {
    /**
     * 用户ID
     */
    @Expose
    private String userid;

    /**
     * 姓名
     */
    @Expose
    private String teachername;

    /**
     * 用户密码
     */
    @Expose
    private String password;

    /**
     * 用户类型，0代表管理员，1代表教师
     */
    @Expose
    private Integer role;

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
