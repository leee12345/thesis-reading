package com.thesisreading.mapper;

import com.thesisreading.model.Paper;
import com.thesisreading.model.PaperExample;
import java.util.List;

import com.thesisreading.model.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaperMapper {
    long countByExample(PaperExample example);

    int deleteByExample(PaperExample example);

    int deleteByPrimaryKey(Integer paperId);

    int insert(Paper record);

    int insertSelective(Paper record);

    List<Paper> selectByExample(PaperExample example);

    List<Paper> selectAll();

    Paper selectByPrimaryKey(Integer paperId);


    int updateByExampleSelective(@Param("record") Paper record, @Param("example") PaperExample example);

    int updateByExample(@Param("record") Paper record, @Param("example") PaperExample example);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    int updateBatch(List<Paper> list);

    int batchInsert(@Param("list") List<Paper> list);
    /**
     *  分页
     * @param pageIndexStart
     * @param pageSize
     * @return
     */
    @ResultMap("BaseResultMap")
    @Select(value = "select * from readingsystem.paper " +
            "order by paper.paper_id " +
            "limit #{pageIndexStart, jdbcType=INTEGER}, #{pageSize, jdbcType=INTEGER}")
    //limit第一个参数：返回值的偏移量，第二个参数：返回值的最大数目
    List<Paper> selectByPage( Integer pageIndexStart, Integer pageSize);
}
