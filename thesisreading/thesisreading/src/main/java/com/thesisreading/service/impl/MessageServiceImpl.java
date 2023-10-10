package com.thesisreading.service.impl;

import com.thesisreading.mapper.MessageMapper;
import com.thesisreading.model.Message;
import com.thesisreading.model.MessageExample;
import com.thesisreading.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Override
    public long countByExample(MessageExample example) {
        return messageMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(MessageExample example) {
        return messageMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer messageId) {
        return messageMapper.deleteByPrimaryKey(messageId);
    }

    @Override
    public int insert(Message record) { return messageMapper.insert(record); }

    @Override
    public int insertSelective(Message record) {
        return messageMapper.insertSelective(record);
    }

    @Override
    public List<Message> selectByExample(MessageExample example) {
        return messageMapper.selectByExample(example);
    }

    @Override
    public Message selectByPrimaryKey(Integer messageId) {
        return messageMapper.selectByPrimaryKey(messageId);
    }

    @Override
    public int updateByExampleSelective(Message record,MessageExample example) {
        return messageMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Message record,MessageExample example) {
        return messageMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Message record) {
        return messageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Message record) {
        return messageMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Message> list) {
        return messageMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Message> list) {
        return messageMapper.batchInsert(list);
    }

    @Override
    public List<Message> selectByPIdByPage(Integer paperId, Integer pageIndex, Integer pageSize) {
        Integer pageIndexStart = (pageIndex - 1) * pageSize;
        return messageMapper.selectPageByPId(paperId, pageIndexStart, pageSize);
    }

}
