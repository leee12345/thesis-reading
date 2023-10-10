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
public class TopicTeaName {

    /**
     * 主题ID
     */
    @Expose
    private String topicId;

    /**
     * 出题教师 name
     */
    @Expose
    private String teacherName;

    /**
     * 主题名
     */
    @Expose
    private String topicName;

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
