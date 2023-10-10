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
public class TopicInfoResponse {
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

    /**
     * 课程名
     */
    @Expose
    private String courseName;

    /**
     * 出题教师 ID
     */
    @Expose
    private String teacherId;

    /**
     * 教师姓名
     */
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
