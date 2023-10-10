package com.thesisreading.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.thesisreading.model.TeacherExample;
import java.util.List;
import com.thesisreading.mapper.TeacherMapper;
import com.thesisreading.model.Teacher;
import com.thesisreading.service.TeacherService;
@Service
public class TeacherServiceImpl implements TeacherService{

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public long countByExample(TeacherExample example) {
        return teacherMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(TeacherExample example) {
        return teacherMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String teacherId) {
        return teacherMapper.deleteByPrimaryKey(teacherId);
    }

    @Override
    public int insert(Teacher record) {
        return teacherMapper.insert(record);
    }

    @Override
    public int insertSelective(Teacher record) {
        return teacherMapper.insertSelective(record);
    }

    @Override
    public List<Teacher> selectByExample(TeacherExample example) {
        return teacherMapper.selectByExample(example);
    }

    @Override
    public Teacher selectByPrimaryKey(String teacherId) {
        return teacherMapper.selectByPrimaryKey(teacherId);
    }

    @Override
    public int updateByExampleSelective(Teacher record,TeacherExample example) {
        return teacherMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Teacher record,TeacherExample example) {
        return teacherMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Teacher record) {
        return teacherMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Teacher record) {
        return teacherMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Teacher> list) {
        return teacherMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Teacher> list) {
        return teacherMapper.batchInsert(list);
    }

    @Override
    public List<Teacher> selectByPage(Integer pageIndex, Integer pageSize) {
        Integer pageIndexStart = (pageIndex - 1) * pageSize;
        return teacherMapper.selectPage(pageIndexStart, pageSize);
    }

    @Override
    public List<Teacher> selectAll() {
        return teacherMapper.selectAll();
    }


}
