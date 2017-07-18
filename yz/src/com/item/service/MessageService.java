package com.item.service;

import com.item.dao.MessageMapper;
import com.item.dao.model.Message;
import com.item.dao.model.MessageExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    private static final Logger logger = Logger.getLogger(MessageService.class);

    public int countByExample(MessageExample example) {
        return this.messageMapper.countByExample(example);
    }

    public Message selectByPrimaryKey(String id) {
        return this.messageMapper.selectByPrimaryKey(id);
    }

    public List<Message> selectByExample(MessageExample example) {
        return this.messageMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.messageMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Message record) {
        return this.messageMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Message record) {
        return this.messageMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(MessageExample example) {
        return this.messageMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Message record, MessageExample example) {
        return this.messageMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Message record, MessageExample example) {
        return this.messageMapper.updateByExample(record, example);
    }

    public int insert(Message record) {
        return this.messageMapper.insert(record);
    }

    public int insertSelective(Message record) {
        return this.messageMapper.insertSelective(record);
    }
}