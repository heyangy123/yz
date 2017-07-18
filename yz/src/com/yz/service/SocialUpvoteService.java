package com.yz.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yz.dao.SocialUpvoteMapper;
import com.yz.dao.model.SocialUpvote;
import com.yz.dao.model.SocialUpvoteExample;
import com.yz.daoEx.SocialUpvoteMapperEx;
import com.yz.daoEx.model.SocialUpvoteEx;

@Service
public class SocialUpvoteService {
    @Autowired
    private SocialUpvoteMapper socialUpvoteMapper;
    
    @Autowired
    private SocialUpvoteMapperEx socialUpvoteMapperEx;

    private static final Logger logger = Logger.getLogger(SocialUpvoteService.class);

    public int countByExample(SocialUpvoteExample example) {
        return this.socialUpvoteMapper.countByExample(example);
    }

    public SocialUpvote selectByPrimaryKey(String id) {
        return this.socialUpvoteMapper.selectByPrimaryKey(id);
    }

    public List<SocialUpvote> selectByExample(SocialUpvoteExample example) {
        return this.socialUpvoteMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.socialUpvoteMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SocialUpvote record) {
        return this.socialUpvoteMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SocialUpvote record) {
        return this.socialUpvoteMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(SocialUpvoteExample example) {
        return this.socialUpvoteMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(SocialUpvote record, SocialUpvoteExample example) {
        return this.socialUpvoteMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(SocialUpvote record, SocialUpvoteExample example) {
        return this.socialUpvoteMapper.updateByExample(record, example);
    }

    public int insert(SocialUpvote record) {
        return this.socialUpvoteMapper.insert(record);
    }

    public int insertSelective(SocialUpvote record) {
        return this.socialUpvoteMapper.insertSelective(record);
    }
    
    public List<SocialUpvoteEx> selectList (String id){
    	return this.socialUpvoteMapperEx.selectList(id);
    }
}