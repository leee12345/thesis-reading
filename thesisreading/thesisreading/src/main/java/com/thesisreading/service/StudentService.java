package com.thesisreading.service;

import java.util.List;
import com.thesisreading.model.StudentExample;
import com.thesisreading.model.Student;
import com.thesisreading.model.Teacher;

public interface StudentService{


    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(String studentId);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(String studentId);

    int updateByExampleSelective(Student record,StudentExample example);

    int updateByExample(Student record,StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    int updateBatch(List<Student> list);

    int batchInsert(List<Student> list);

    /**
     * 分页
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Student> selectByPage(Integer pageIndex, Integer pageSize);


    List<Student> selectAll();
}
