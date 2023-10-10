package com.thesisreading.controller.AdminController.bean.response;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CourseTeachInfo {
    @Expose
    String teacherId;
    @Expose
    String teacherName;
    @Expose
    String teaIdentity;
    @Expose
    Integer teachHour;
}


