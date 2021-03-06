package com.item.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item.dao.FeedbackMapper;
import com.item.dao.model.Feedback;
import com.item.dao.model.FeedbackExample;


@Service
public class FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    private static final Logger logger = Logger.getLogger(FeedbackService.class);

    public int countByExample(FeedbackExample example) {
        return this.feedbackMapper.countByExample(example);
    }

    public Feedback selectByPrimaryKey(String id) {
        return this.feedbackMapper.selectByPrimaryKey(id);
    }

    public List<Feedback> selectByExample(FeedbackExample example) {
        return this.feedbackMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.feedbackMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Feedback record) {
        return this.feedbackMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Feedback record) {
        return this.feedbackMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(FeedbackExample example) {
        return this.feedbackMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Feedback record, FeedbackExample example) {
        return this.feedbackMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Feedback record, FeedbackExample example) {
        return this.feedbackMapper.updateByExample(record, example);
    }

    public int insert(Feedback record) {
        return this.feedbackMapper.insert(record);
    }

    public int insertSelective(Feedback record) {
        return this.feedbackMapper.insertSelective(record);
    }
}