package com.thesisreading.service;

import com.thesisreading.model.TopicExample;
import java.util.List;
import com.thesisreading.model.Topic;
import org.springframework.web.multipart.MultipartFile;

public interface TopicService{


    long countByExample(TopicExample example);

    int deleteByExample(TopicExample example);

    int deleteByPrimaryKey(String topicId);

    int insert(Topic record);

    int insertSelective(Topic record);

    List<Topic> selectByExample(TopicExample example);

    Topic selectByPrimaryKey(String topicId);

    int updateByExampleSelective(Topic record,TopicExample example);

    int updateByExample(Topic record,TopicExample example);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKey(Topic record);

    int updateBatch(List<Topic> list);

    int batchInsert(List<Topic> list);

    /**
     * 主题分页 by courseId
     * @param courseId
     * @param pageIndex
     * @param pageSize
     * @return
     */

    List<Topic> selectByCIdByPage(String courseId, int pageIndex, int pageSize);

}
