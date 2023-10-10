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
public class CourseInfoRequest {

    // basic info

    /**
     * 课程ID
     */
    @Expose
    private String courseId;

    /**
     * 课程名
     */
    @Expose
    private String courseName;

    /**
     * 开设学期
     */
    @Expose
    private String term;

    /**
     * 课时数
     */
    @Expose
    private Integer courseHour;

}
