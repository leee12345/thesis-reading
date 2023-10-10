package com.thesisreading.controller.TeacherController.bean.response;


import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TopicInfo {

    /**
     * 主题ID
     */
    @Expose
    private String topicId;

    /**
     * 课程ID
     */
    @Expose
    private String courseId;

    @Expose
    private String courseName;

    /**
     * 出题教师 ID
     */
    @Expose
    private String teacherId;

    @Expose
    private String teacherName;

    /**
     * 主题名
     */
    @Expose
    private String topic;

    /**
     * 关键词
     */
    @Expose
    private String keyword;

    /**
     * 创建时间
     */
    @Expose
    private Date creationTime;

    /**
     * 已选学生人数
     */
    @Expose
    private Integer studentNum;

    /**
     * 人数上限
     */
    @Expose
    private Integer maxNum;
}
