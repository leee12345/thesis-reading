package com.thesisreading.service;

import java.util.List;
import com.thesisreading.model.Topicpaper;
import com.thesisreading.model.TopicpaperExample;
public interface TopicpaperService{


    long countByExample(TopicpaperExample example);

    int deleteByExample(TopicpaperExample example);

    int deleteByPrimaryKey(Integer topicpaperId);

    int insert(Topicpaper record);

    int insertSelective(Topicpaper record);

    List<Topicpaper> selectByExample(TopicpaperExample example);

    Topicpaper selectByPrimaryKey(Integer topicpaperId);

    int updateByExampleSelective(Topicpaper record,TopicpaperExample example);

    int updateByExample(Topicpaper record,TopicpaperExample example);

    int updateByPrimaryKeySelective(Topicpaper record);

    int updateByPrimaryKey(Topicpaper record);

    int updateBatch(List<Topicpaper> list);

    int batchInsert(List<Topicpaper> list);

    List<Topicpaper> selectByStutopicId(String stutopicId);
}
