package com.yz.service;

import com.yz.dao.SocialMapper;
import com.yz.dao.model.Social;
import com.yz.dao.model.SocialExample;
import com.yz.daoEx.SocialMapperEx;
import com.yz.daoEx.model.SocialEx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialService {
    @Autowired
    private SocialMapper socialMapper;
    
    @Autowired
    private SocialMapperEx socialMapperEx;

    private static final Logger logger = Logger.getLogger(SocialService.class);

    public int countByExample(SocialExample example) {
        return this.socialMapper.countByExample(example);
    }

    public Social selectByPrimaryKey(String id) {
        return this.socialMapper.selectByPrimaryKey(id);
    }

    public List<Social> selectByExample(SocialExample example) {
        return this.socialMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.socialMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Social record) {
        return this.socialMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Social record) {
        return this.socialMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(SocialExample example) {
        return this.socialMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Social record, SocialExample example) {
        return this.socialMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Social record, SocialExample example) {
        return this.socialMapper.updateByExample(record, example);
    }

    public int insert(Social record) {
        return this.socialMapper.insert(record);
    }

    public int insertSelective(Social record) {
        return this.socialMapper.insertSelective(record);
    }
    
    public List<SocialEx> selectAppList(Map<String,Object> map){
    	return this.socialMapperEx.selectAppList(map);
    }
}