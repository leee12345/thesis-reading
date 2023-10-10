package com.thesisreading.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.thesisreading.model.Sc;
import com.thesisreading.model.ScExample;
import com.thesisreading.mapper.ScMapper;
import com.thesisreading.service.ScService;
@Service
public class ScServiceImpl implements ScService{

    @Resource
    private ScMapper scMapper;

    @Override
    public long countByExample(ScExample example) {
        return scMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ScExample example) {
        return scMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String scId) {
        return scMapper.deleteByPrimaryKey(scId);
    }

    @Override
    public int insert(Sc record) {
        return scMapper.insert(record);
    }

    @Override
    public int insertSelective(Sc record) {
        return scMapper.insertSelective(record);
    }

    @Override
    public List<Sc> selectByExample(ScExample example) {
        return scMapper.selectByExample(example);
    }

    @Override
    public Sc selectByPrimaryKey(String scId) {
        return scMapper.selectByPrimaryKey(scId);
    }

    @Override
    public int updateByExampleSelective(Sc record,ScExample example) {
        return scMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Sc record,ScExample example) {
        return scMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Sc record) {
        return scMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Sc record) {
        return scMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Sc> list) {
        return scMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Sc> list) {
        return scMapper.batchInsert(list);
    }

    @Override
    public List<Sc> selectByCourseId(String courseId) {
        ScExample example = new ScExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        return selectByExample(example);
    }

    @Override
    public Sc selectByStuIdAndCourseId(String studentId, String courseId) {
        ScExample example = new ScExample();
        example.createCriteria().andCourseIdEqualTo(courseId).andStudentIdEqualTo(studentId);
        List<Sc> list = selectByExample(example);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public List<Sc> selectByStudentId(String studentId) {
        ScExample example = new ScExample();
        example.createCriteria().andStudentIdEqualTo(studentId);
        return selectByExample(example);
    }

}
