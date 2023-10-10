package com.thesisreading.service;

import com.thesisreading.model.TeacherExample;
import java.util.List;
import com.thesisreading.model.Teacher;
public interface TeacherService{


    long countByExample(TeacherExample example);

    int deleteByExample(TeacherExample example);

    int deleteByPrimaryKey(String teacherId);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    List<Teacher> selectByExample(TeacherExample example);

    Teacher selectByPrimaryKey(String teacherId);

    int updateByExampleSelective(Teacher record,TeacherExample example);

    int updateByExample(Teacher record,TeacherExample example);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    int updateBatch(List<Teacher> list);

    int batchInsert(List<Teacher> list);

    List<Teacher> selectByPage(Integer pageIndex, Integer pageSize);

    List<Teacher> selectAll();
}
