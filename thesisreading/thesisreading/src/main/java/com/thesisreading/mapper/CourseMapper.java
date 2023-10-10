package com.thesisreading.mapper;

import com.thesisreading.model.Course;
import com.thesisreading.model.CourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseMapper {
    long countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(String courseId);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(String courseId);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    int updateBatch(List<Course> list);

    int batchInsert(@Param("list") List<Course> list);

    /**
     * 课程号顺序 分页
     * @param pageIndexStart
     * @param pageSize
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.course " +
            "order by course.course_id " +
            "limit #{pageIndexStart, jdbcType=INTEGER}, #{pageSize, jdbcType=INTEGER}")
    List<Course> selectPage(Integer pageIndexStart, Integer pageSize);

    /**
     * 所有课程的列表
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.course")
    List<Course> selectAll();
}
