package com.thesisreading.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.thesisreading.mapper.StutopicMapper;
import com.thesisreading.model.Stutopic;
import com.thesisreading.model.StutopicExample;
import com.thesisreading.service.StutopicService;
@Service
public class StutopicServiceImpl implements StutopicService{

    @Resource
    private StutopicMapper stutopicMapper;

    @Override
    public long countByExample(StutopicExample example) {
        return stutopicMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(StutopicExample example) {
        return stutopicMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String stutopicId) {
        return stutopicMapper.deleteByPrimaryKey(stutopicId);
    }

    @Override
    public int insert(Stutopic record) {
        return stutopicMapper.insert(record);
    }

    @Override
    public int insertSelective(Stutopic record) {
        return stutopicMapper.insertSelective(record);
    }

    @Override
    public List<Stutopic> selectByExample(StutopicExample example) {
        return stutopicMapper.selectByExample(example);
    }

    @Override
    public Stutopic selectByPrimaryKey(String stutopicId) {
        return stutopicMapper.selectByPrimaryKey(stutopicId);
    }

    @Override
    public int updateByExampleSelective(Stutopic record,StutopicExample example) {
        return stutopicMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Stutopic record,StutopicExample example) {
        return stutopicMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Stutopic record) {
        return stutopicMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Stutopic record) {
        return stutopicMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Stutopic> list) {
        return stutopicMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Stutopic> list) {
        return stutopicMapper.batchInsert(list);
    }

    @Override
    public List<Stutopic> selectByStudentId(String studentId) {
        StutopicExample example = new StutopicExample();
        example.createCriteria().andStudentIdEqualTo(studentId);
        return selectByExample(example);
    }

    @Override
    public Stutopic selectByStuIdAndTopicId(String studentId, String topicId) {
        StutopicExample example = new StutopicExample();
        example.createCriteria().andStudentIdEqualTo(studentId).andTopicIdEqualTo(topicId);
        List<Stutopic> list = selectByExample(example);
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Stutopic> selectByStuIdAndCourseId(String studentId, String courseId){
        return stutopicMapper.selectByStuIdAndCourseId(studentId,courseId);
    }
    @Override
    public List<Stutopic> selectByTopicId(String topicId) {
        StutopicExample example = new StutopicExample();
        example.createCriteria().andTopicIdEqualTo(topicId);
        return selectByExample(example);
    }

}
