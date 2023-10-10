package com.thesisreading.service;

import java.util.List;
import com.thesisreading.model.PaperExample;
import com.thesisreading.model.Paper;
import com.thesisreading.model.Topic;
import org.springframework.web.multipart.MultipartFile;

public interface PaperService{


    long countByExample(PaperExample example);

    int deleteByExample(PaperExample example);

    int deleteByPrimaryKey(Integer paperId);

    int insert(Paper record);

    int insertSelective(Paper record);

    List<Paper> selectByExample(PaperExample example);

    List<Paper> selectAll();

    Paper selectByPrimaryKey(Integer paperId);

    int updateByExampleSelective(Paper record,PaperExample example);

    int updateByExample(Paper record,PaperExample example);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    int updateBatch(List<Paper> list);

    int batchInsert(List<Paper> list);

    /**
     * 上传excel文件
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    boolean batchImport(String fileName, MultipartFile file, String topicId) throws Exception;

    /**
     * 分页
     * @param pageIndex
     * @param pageSize
     * @return
     */

    List<Paper> selectByPage( int pageIndex, int pageSize);

}
