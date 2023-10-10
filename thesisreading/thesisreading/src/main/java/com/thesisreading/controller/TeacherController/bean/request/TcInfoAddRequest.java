package com.thesisreading.controller.TeacherController.bean.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcInfoAddRequest {

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
     * 授课学时
     */
    @Expose
    private Integer teachHour;
}
