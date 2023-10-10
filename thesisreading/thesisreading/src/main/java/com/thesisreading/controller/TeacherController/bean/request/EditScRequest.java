package com.thesisreading.controller.TeacherController.bean.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditScRequest {

    @Expose
    private String scId;

    @Expose
    private Integer score;
}
