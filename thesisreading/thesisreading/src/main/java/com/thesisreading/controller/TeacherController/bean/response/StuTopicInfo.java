package com.thesisreading.controller.TeacherController.bean.response;


import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StuTopicInfo {

    /**
     * StuTopicID
     */
    @Expose
    private String stutopicId;


    /**
     * 主题ID
     */
    @Expose
    private String topicId;

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
     * 出题教师 ID   ----  指导教师
     */
    @Expose
    private String teacherId;

    @Expose
    private String teacherName;

}
