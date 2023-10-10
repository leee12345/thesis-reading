package com.thesisreading.controller.TeacherController.bean.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewTopicRequest {

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
     * 人数上限
     */
    @Expose
    private Integer maxNum;

}
