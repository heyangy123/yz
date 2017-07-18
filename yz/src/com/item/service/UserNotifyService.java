package com.item.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item.dao.UserNotifyMapper;
import com.item.dao.model.UserNotify;
import com.item.dao.model.UserNotifyExample;
import com.item.daoEx.UserNotifyMapperEx;
import com.item.daoEx.model.UserNotifyEx;

@Service
public class UserNotifyService {
    @Autowired
    private UserNotifyMapper userNotifyMapper;
    @Autowired
    private UserNotifyMapperEx userNotifyMapperEx;

    private static final Logger logger = Logger.getLogger(UserNotifyService.class);

    public int countByExample(UserNotifyExample example) {
        return this.userNotifyMapper.countByExample(example);
    }

    public UserNotify selectByPrimaryKey(String id) {
        return this.userNotifyMapper.selectByPrimaryKey(id);
    }

    public List<UserNotify> selectByExample(UserNotifyExample example) {
        return this.userNotifyMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.userNotifyMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(UserNotify record) {
        return this.userNotifyMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserNotify record) {
        return this.userNotifyMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(UserNotifyExample example) {
        return this.userNotifyMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(UserNotify record, UserNotifyExample example) {
        return this.userNotifyMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(UserNotify record, UserNotifyExample example) {
        return this.userNotifyMapper.updateByExample(record, example);
    }

    public int insert(UserNotify record) {
        return this.userNotifyMapper.insert(record);
    }

    public int insertSelective(UserNotify record) {
        return this.userNotifyMapper.insertSelective(record);
    }
    public List<UserNotifyEx> selectAppList(Map<String, Object> map){
    	return userNotifyMapperEx.selectAppList(map);
    }
}