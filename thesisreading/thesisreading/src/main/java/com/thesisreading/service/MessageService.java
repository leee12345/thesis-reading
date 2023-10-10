package com.thesisreading.service;

import com.thesisreading.model.Message;
import com.thesisreading.model.MessageExample;
import com.thesisreading.model.Paper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {


    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Integer messageId);

    int updateByExampleSelective(Message record,MessageExample example);

    int updateByExample(Message record,MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    int updateBatch(List<Message> list);

    int batchInsert(List<Message> list);

    /**
     * 主题分页 by paperId
     * @param paperId
     * @param pageIndex
     * @param pageSize
     * @return
     */

    List<Message> selectByPIdByPage(Integer paperId, Integer pageIndex, Integer pageSize);

}
