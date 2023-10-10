package com.thesisreading.service;

import java.util.List;
import com.thesisreading.model.Sc;
import com.thesisreading.model.ScExample;
public interface ScService{


    long countByExample(ScExample example);

    int deleteByExample(ScExample example);

    int deleteByPrimaryKey(String scId);

    int insert(Sc record);

    int insertSelective(Sc record);

    List<Sc> selectByExample(ScExample example);

    Sc selectByPrimaryKey(String scId);

    int updateByExampleSelective(Sc record,ScExample example);

    int updateByExample(Sc record,ScExample example);

    int updateByPrimaryKeySelective(Sc record);

    int updateByPrimaryKey(Sc record);

    int updateBatch(List<Sc> list);

    int batchInsert(List<Sc> list);

    /**
     * 按照courseId找到相应的学生选课记录
     * @param courseId
     * @return
     */
    List<Sc> selectByCourseId(String courseId);

    Sc selectByStuIdAndCourseId(String studentId, String courseId);

    List<Sc> selectByStudentId(String studentId);
}
