package com.thesisreading.service;

import java.util.List;
import com.thesisreading.model.Stutopic;
import com.thesisreading.model.StutopicExample;
public interface StutopicService{


    long countByExample(StutopicExample example);

    int deleteByExample(StutopicExample example);

    int deleteByPrimaryKey(String stutopicId);

    int insert(Stutopic record);

    int insertSelective(Stutopic record);

    List<Stutopic> selectByExample(StutopicExample example);

    Stutopic selectByPrimaryKey(String stutopicId);

    int updateByExampleSelective(Stutopic record,StutopicExample example);

    int updateByExample(Stutopic record,StutopicExample example);

    int updateByPrimaryKeySelective(Stutopic record);

    int updateByPrimaryKey(Stutopic record);

    int updateBatch(List<Stutopic> list);

    int batchInsert(List<Stutopic> list);

    /**
     * 根据学号查找该学生的选题记录
     * @param studentId
     * @return
     */
    List<Stutopic> selectByStudentId(String studentId);

    Stutopic selectByStuIdAndTopicId(String studentId, String topicId);

    List<Stutopic> selectByStuIdAndCourseId(String studentId, String courseId);

    List<Stutopic> selectByTopicId(String topicId);

}
