package com.thesisreading.mapper;

import com.thesisreading.model.Tc;
import com.thesisreading.model.TcExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TcMapper {
    long countByExample(TcExample example);

    int deleteByExample(TcExample example);

    int deleteByPrimaryKey(String tcId);

    int insert(Tc record);

    int insertSelective(Tc record);

    List<Tc> selectByExample(TcExample example);

    Tc selectByPrimaryKey(String tcId);

    int updateByExampleSelective(@Param("record") Tc record, @Param("example") TcExample example);

    int updateByExample(@Param("record") Tc record, @Param("example") TcExample example);

    int updateByPrimaryKeySelective(Tc record);

    int updateByPrimaryKey(Tc record);

    int updateBatch(List<Tc> list);

    int batchInsert(@Param("list") List<Tc> list);

    /**
     * 通过课程号找到带有主讲教师的对应记录
     *
     * @param courseId
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select("select distinct * from readingsystem.tc where tc.course_id = #{courseId} and tc.tea_identity like '%主讲%'")
    Tc selectContainMajorTeacherByCourseId(String courseId);


    /**
     * 通过课程号找到带有团队教师的对应记录
     *
     * @param courseId
     * @return list
     */
    @ResultMap("BaseResultMap")
    @Select("select * from readingsystem.tc where tc.course_id = #{courseId} and tc.tea_identity like '%团队%'")
    List<Tc> selectContainTeamTeacherByCourseId(String courseId);

    /**
     * 授课表 分页 by courseId
     * @param courseId
     * @param pageIndexStart
     * @param pageSize
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.tc " +
            "where tc.course_id = #{courseId, jdbcType=VARCHAR}" +
            "limit #{pageIndexStart, jdbcType=INTEGER}, #{pageSize, jdbcType=INTEGER}")
    List<Tc> selectPageByCId(String courseId, Integer pageIndexStart, Integer pageSize);


}
