package com.thesisreading.mapper;

import com.thesisreading.model.Teacher;
import com.thesisreading.model.TeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
public interface TeacherMapper {
    long countByExample(TeacherExample example);

    int deleteByExample(TeacherExample example);

    int deleteByPrimaryKey(String teacherId);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    List<Teacher> selectByExample(TeacherExample example);

    Teacher selectByPrimaryKey(String teacherId);

    int updateByExampleSelective(@Param("record") Teacher record, @Param("example") TeacherExample example);

    int updateByExample(@Param("record") Teacher record, @Param("example") TeacherExample example);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    int updateBatch(List<Teacher> list);

    int batchInsert(@Param("list") List<Teacher> list);

    /**
     * 教师号 分页
     * @param pageIndexStart
     * @param pageSize
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.teacher " +
            "order by teacher.teacher_id " +
            "limit #{pageIndexStart, jdbcType=INTEGER}, #{pageSize, jdbcType=INTEGER}")
    List<Teacher> selectPage(Integer pageIndexStart, Integer pageSize);

    /**
     * 所有教师的列表
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.teacher")
    List<Teacher> selectAll();

}
