package com.thesisreading.controller.AdminController.bean.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherTcInfoAddRequest {

    /**
     * 无tcid
     */

    /**
     * 课程ID
     */
    @Expose
    private String courseId;

    /**
     * 教师ID
     */
    @Expose
    private String teacherId;

    /**
     * 身份（主讲教师或者团队教师）
     */
    @Expose
    private String teaIdentity;

    /**
     * 授课学时
     */
    @Expose
    private Integer teachHour;
}
