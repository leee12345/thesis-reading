package com.thesisreading.controller.TeacherController.bean.response;

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
public class TcPaperInfo {
    @Expose
    private Integer paperId;//论文ID


    @Expose
    private String title;//论文题目


    @Expose
    private String author;//论文作者


    @Expose
    private String source; //出处


    @Expose
    private String keyword;//关键词


    @Expose
    private String abstractText;//摘要


    @Expose
    private String docLocation;//文件物理位置


    @Expose
    private String studentId;//学号


    @Expose
    private Date uploadingTime;//上传时间

    @Expose
    private String link;//超链接

}
