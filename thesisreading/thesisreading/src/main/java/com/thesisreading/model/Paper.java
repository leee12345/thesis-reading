package com.thesisreading.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

@Builder
public class Paper implements Serializable {

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

    private static final long serialVersionUID = 1L;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getDocLocation() {
        return docLocation;
    }

    public void setDocLocation(String docLocation) {
        this.docLocation = docLocation;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getUploadingTime() {
        return uploadingTime;
    }

    public void setUploadingTime(Date uploadingTime) {
        this.uploadingTime = uploadingTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paperId=").append(paperId);
        sb.append(", title=").append(title);
        sb.append(", author=").append(author);
        sb.append(", source=").append(source);
        sb.append(", keyword=").append(keyword);
        sb.append(", abstractText=").append(abstractText);
        sb.append(", docLocation=").append(docLocation);
        sb.append(", studentId=").append(studentId);
        sb.append(", uploadingTime=").append(uploadingTime);
        sb.append("]");
        return sb.toString();
    }
}
