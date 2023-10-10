package com.thesisreading.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.thesisreading.model.Tc;
import java.util.List;
import com.thesisreading.model.TcExample;
import com.thesisreading.mapper.TcMapper;
import com.thesisreading.service.TcService;
@Service
public class TcServiceImpl implements TcService{

    @Resource
    private TcMapper tcMapper;

    @Override
    public long countByExample(TcExample example) {
        return tcMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(TcExample example) {
        return tcMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String tcId) {
        return tcMapper.deleteByPrimaryKey(tcId);
    }

    @Override
    public int insert(Tc record) {
        return tcMapper.insert(record);
    }

    @Override
    public int insertSelective(Tc record) {
        return tcMapper.insertSelective(record);
    }

    @Override
    public List<Tc> selectByExample(TcExample example) {
        return tcMapper.selectByExample(example);
    }

    @Override
    public Tc selectByPrimaryKey(String tcId) {
        return tcMapper.selectByPrimaryKey(tcId);
    }

    @Override
    public int updateByExampleSelective(Tc record,TcExample example) {
        return tcMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Tc record,TcExample example) {
        return tcMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Tc record) {
        return tcMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Tc record) {
        return tcMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Tc> list) {
        return tcMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Tc> list) {
        return tcMapper.batchInsert(list);
    }

    @Override
    public List<Tc> selectByTeacherId(String teacherid) {
        TcExample example = new TcExample();
        example.createCriteria().andTeacherIdEqualTo(teacherid);
        return selectByExample(example);
    }

    @Override
    public List<Tc> selectByCourseId(String courseid) {
        TcExample example = new TcExample();
        example.createCriteria().andCourseIdEqualTo(courseid);
        return selectByExample(example);
    }

    @Override
    public Integer getNowTeachHourByCourseId(String courseid){
        List<Tc> tcList = selectByCourseId(courseid);
        int nowTeachHour = 0;
        for (Tc item : tcList) {
            nowTeachHour += item.getTeachHour();
        }
        return nowTeachHour;
    }

    @Override
    public Tc selectContainMajorTeacherByCourseId(String courseId) {
        return tcMapper.selectContainMajorTeacherByCourseId(courseId);
    }

    @Override
    public List<Tc> selectContainTeamTeacherByCourseId(String courseId) {
        return tcMapper.selectContainTeamTeacherByCourseId(courseId);
    }

    @Override
    public List<Tc> selectByCIdByPage(String courseId, int pageIndex, int pageSize) {
        Integer pageIndexStart = (pageIndex - 1) * pageSize;
        return tcMapper.selectPageByCId(courseId, pageIndexStart, pageSize);
    }
}
