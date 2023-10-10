package com.thesisreading.service;

import com.thesisreading.model.CourseExample;
import java.util.List;
import com.thesisreading.model.Course;
public interface CourseService{


    long countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(String courseId);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(String courseId);

    int updateByExampleSelective(Course record,CourseExample example);

    int updateByExample(Course record,CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    int updateBatch(List<Course> list);

    int batchInsert(List<Course> list);

    List<Course> selectByPage(int pageIndex, int pageSize);

    List<Course> selectAll();
}
