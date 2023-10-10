package com.thesisreading.controller.AdminController.bean.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherTcInfoRequest {

    /**
     * TCID
     */
    @Expose
    private String tcId;

    /**
     * 身份（主讲教师或者团队教师）
     */
    @Expose
    private String teaIdentity;

    /**
     * 授课学时
     */
    @Expose
    private Integer teachHour;

}
