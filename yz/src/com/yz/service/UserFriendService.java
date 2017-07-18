package com.yz.service;

import com.yz.dao.UserFriendMapper;
import com.yz.dao.model.UserFriend;
import com.yz.dao.model.UserFriendExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFriendService {
    @Autowired
    private UserFriendMapper userFriendMapper;

    private static final Logger logger = Logger.getLogger(UserFriendService.class);

    public int countByExample(UserFriendExample example) {
        return this.userFriendMapper.countByExample(example);
    }

    public UserFriend selectByPrimaryKey(String id) {
        return this.userFriendMapper.selectByPrimaryKey(id);
    }

    public List<UserFriend> selectByExample(UserFriendExample example) {
        return this.userFriendMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.userFriendMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(UserFriend record) {
        return this.userFriendMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserFriend record) {
        return this.userFriendMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(UserFriendExample example) {
        return this.userFriendMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(UserFriend record, UserFriendExample example) {
        return this.userFriendMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(UserFriend record, UserFriendExample example) {
        return this.userFriendMapper.updateByExample(record, example);
    }

    public int insert(UserFriend record) {
        return this.userFriendMapper.insert(record);
    }

    public int insertSelective(UserFriend record) {
        return this.userFriendMapper.insertSelective(record);
    }
}