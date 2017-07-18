package com.yz.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yz.dao.SocialReplyMapper;
import com.yz.dao.model.SocialReply;
import com.yz.dao.model.SocialReplyExample;
import com.yz.daoEx.SocialReplyMapperEx;
import com.yz.daoEx.model.SocialReplyEx;

@Service
public class SocialReplyService {
    @Autowired
    private SocialReplyMapper socialReplyMapper;
    
    @Autowired
    private SocialReplyMapperEx socialReplyMapperEx;

    private static final Logger logger = Logger.getLogger(SocialReplyService.class);

    public int countByExample(SocialReplyExample example) {
        return this.socialReplyMapper.countByExample(example);
    }

    public SocialReply selectByPrimaryKey(String id) {
        return this.socialReplyMapper.selectByPrimaryKey(id);
    }

    public List<SocialReply> selectByExample(SocialReplyExample example) {
        return this.socialReplyMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.socialReplyMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SocialReply record) {
        return this.socialReplyMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(SocialReply record) {
        return this.socialReplyMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(SocialReplyExample example) {
        return this.socialReplyMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(SocialReply record, SocialReplyExample example) {
        return this.socialReplyMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(SocialReply record, SocialReplyExample example) {
        return this.socialReplyMapper.updateByExample(record, example);
    }

    public int insert(SocialReply record) {
        return this.socialReplyMapper.insert(record);
    }

    public int insertSelective(SocialReply record) {
        return this.socialReplyMapper.insertSelective(record);
    }
    
    public List<SocialReplyEx> selectAppList(String id){
    	 return this.socialReplyMapperEx.selectAppList(id);
    }
}