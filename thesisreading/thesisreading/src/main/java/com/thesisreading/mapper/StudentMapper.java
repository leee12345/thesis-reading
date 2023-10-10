package com.thesisreading.mapper;

import com.thesisreading.model.Student;
import com.thesisreading.model.StudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {
    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(String studentId);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(String studentId);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    int updateBatch(List<Student> list);

    int batchInsert(@Param("list") List<Student> list);

    /**
     * 学号降序 分页
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.student " +
            "order by student.student_id" +
            "limit #{pageIndexStart, jdbcType=INTEGER}, #{pageSize, jdbcType=INTEGER}")
    List<Student> selectPage(Integer pageIndexStart, Integer pageSize);

    /**
     * 选出所有的学生
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select("select * from readingsystem.student")
    List<Student> selectAll();
}
