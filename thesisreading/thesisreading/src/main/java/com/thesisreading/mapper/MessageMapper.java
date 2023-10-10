package com.thesisreading.mapper;

import com.thesisreading.model.Message;
import com.thesisreading.model.MessageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Integer messageId);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    int updateBatch(List<Message> list);

    int batchInsert(@Param("list") List<Message> list);

    /**
     *  分页 by paperId
     * @param paperId
     * @param pageIndexStart
     * @param pageSize
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.message " +
            "where message.paper_id = #{paperId, jdbcType=INTEGER} " +
            "order by message.message_id " +
            "limit #{pageIndexStart, jdbcType=INTEGER}, #{pageSize, jdbcType=INTEGER}")
     //limit第一个参数：返回值的偏移量，第二个参数：返回值的最大数目
    List<Message> selectPageByPId(Integer paperId, Integer pageIndexStart, Integer pageSize);

}
