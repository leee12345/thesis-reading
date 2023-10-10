package com.thesisreading.mapper;

import com.thesisreading.model.Student;
import com.thesisreading.model.Topic;
import com.thesisreading.model.TopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TopicMapper {
    long countByExample(TopicExample example);

    int deleteByExample(TopicExample example);

    int deleteByPrimaryKey(String topicId);

    int insert(Topic record);

    int insertSelective(Topic record);

    List<Topic> selectByExample(TopicExample example);

    Topic selectByPrimaryKey(String topicId);

    int updateByExampleSelective(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByExample(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKey(Topic record);

    int updateBatch(List<Topic> list);

    int batchInsert(@Param("list") List<Topic> list);

    /**
     * 主题表 分页 by courseId
     * @param courseId
     * @param pageIndexStart
     * @param pageSize
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.topic " +
            "where topic.course_id = #{courseId, jdbcType=VARCHAR}" +
            "order by topic.topic_id " +
            "limit #{pageIndexStart, jdbcType=INTEGER}, #{pageSize, jdbcType=INTEGER}")
     //limit第一个参数：返回值的偏移量，第二个参数：返回值的最大数目
    List<Topic> selectPageByCId(String courseId, Integer pageIndexStart, Integer pageSize);

}
