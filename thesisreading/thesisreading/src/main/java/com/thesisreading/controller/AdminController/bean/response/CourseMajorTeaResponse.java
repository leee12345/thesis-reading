package com.thesisreading.controller.AdminController.bean.response;

import com.thesisreading.model.Course;
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
public class CourseMajorTeaResponse {

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

    /**
     * 主讲教师id
     */
    @Expose
    private String mteacherId;

    /**
     * 主讲教师name
     */
    @Expose
    private String mteacherName;

}
