package com.thesisreading.controller.StudentController.bean.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageRequest {

    /**
     * paperID
     */
    @Expose
    private Integer paperId;

    /**
     * 评论内容
     */
    @Expose
    private String content;


}
