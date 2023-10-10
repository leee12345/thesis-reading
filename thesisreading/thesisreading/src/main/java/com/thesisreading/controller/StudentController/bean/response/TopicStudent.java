package com.thesisreading.controller.StudentController.bean.response;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TopicStudent {
    /**
     * 学号
     */
    @Expose
    private String studentId;

    /**
     * 姓名
     */
    @Expose
    private String studentName;

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
    private String className;

    /**
     * 选题时间
     */
    @Expose
    private Date selectionTime;
}
