package com.thesisreading.controller.TeacherController.bean.response;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcInfoResponse {

    @Expose
    private String tcId;

    @Expose
    private String courseId;


    @Expose
    private String courseName;

    @Expose
    private String teacherId;

    @Expose
    private String teacherName;

    /**
     * 身份（主讲教师或者团队教师）
     */
    @Expose
    private String teaIdentity;

    @Expose
    private Integer teachHour;
}
