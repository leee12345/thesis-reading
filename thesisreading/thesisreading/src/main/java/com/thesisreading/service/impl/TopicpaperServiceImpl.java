package com.thesisreading.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.thesisreading.model.Topicpaper;
import com.thesisreading.mapper.TopicpaperMapper;
import com.thesisreading.model.TopicpaperExample;
import com.thesisreading.service.TopicpaperService;
@Service
public class TopicpaperServiceImpl implements TopicpaperService{

    @Resource
    private TopicpaperMapper topicpaperMapper;

    @Override
    public long countByExample(TopicpaperExample example) {
        return topicpaperMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(TopicpaperExample example) {
        return topicpaperMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer topicpaperId) {
        return topicpaperMapper.deleteByPrimaryKey(topicpaperId);
    }

    @Override
    public int insert(Topicpaper record) {
        return topicpaperMapper.insert(record);
    }

    @Override
    public int insertSelective(Topicpaper record) {
        return topicpaperMapper.insertSelective(record);
    }

    @Override
    public List<Topicpaper> selectByExample(TopicpaperExample example) {
        return topicpaperMapper.selectByExample(example);
    }

    @Override
    public Topicpaper selectByPrimaryKey(Integer topicpaperId) {
        return topicpaperMapper.selectByPrimaryKey(topicpaperId);
    }

    @Override
    public int updateByExampleSelective(Topicpaper record,TopicpaperExample example) {
        return topicpaperMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Topicpaper record,TopicpaperExample example) {
        return topicpaperMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Topicpaper record) {
        return topicpaperMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Topicpaper record) {
        return topicpaperMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Topicpaper> list) {
        return topicpaperMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Topicpaper> list) {
        return topicpaperMapper.batchInsert(list);
    }

    @Override
    public List<Topicpaper> selectByStutopicId(String stutopicId) {
        TopicpaperExample example = new TopicpaperExample();
        example.createCriteria().andStutopicIdEqualTo(stutopicId);
        return selectByExample(example);
    }

}
