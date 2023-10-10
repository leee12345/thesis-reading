package com.thesisreading.service;

import com.thesisreading.model.Tc;
import java.util.List;
import com.thesisreading.model.TcExample;
public interface TcService{


    long countByExample(TcExample example);

    int deleteByExample(TcExample example);

    int deleteByPrimaryKey(String tcId);

    int insert(Tc record);

    int insertSelective(Tc record);

    List<Tc> selectByExample(TcExample example);

    Tc selectByPrimaryKey(String tcId);

    int updateByExampleSelective(Tc record,TcExample example);

    int updateByExample(Tc record,TcExample example);

    int updateByPrimaryKeySelective(Tc record);

    int updateByPrimaryKey(Tc record);

    int updateBatch(List<Tc> list);

    int batchInsert(List<Tc> list);

    /**
     * 按照教师号查找tc表的记录
     * @param teacherid
     * @return
     */
    List<Tc> selectByTeacherId(String teacherid);


    /**
     * 按照课程号查找tc表的记录
     * @param courseid
     * @return
     */
    List<Tc> selectByCourseId(String courseid);


    /**
     * 查找courseid当前的teachhour的总和
     */
    Integer getNowTeachHourByCourseId(String courseid);

    /**
     * 通过课程号找到带有主讲教师的对应记录
     *
     * @param courseId
     * @return
     */
    Tc selectContainMajorTeacherByCourseId(String courseId);

    /**
     * 通过课程号找到带有团队教师的对应记录
     *
     * @param courseId
     * @return list
     */
    List<Tc> selectContainTeamTeacherByCourseId(String courseId);

    /**
     * 对于courseId 找到tc记录 进行分页
     * @param courseId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Tc> selectByCIdByPage(String courseId, int pageIndex, int pageSize);

}
