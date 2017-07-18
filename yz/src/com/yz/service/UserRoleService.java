package com.yz.service;

import com.yz.dao.UserRoleMapper;
import com.yz.dao.model.UserRole;
import com.yz.dao.model.UserRoleExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    private static final Logger logger = Logger.getLogger(UserRoleService.class);

    public int countByExample(UserRoleExample example) {
        return this.userRoleMapper.countByExample(example);
    }

    public UserRole selectByPrimaryKey(String id) {
        return this.userRoleMapper.selectByPrimaryKey(id);
    }

    public List<UserRole> selectByExample(UserRoleExample example) {
        return this.userRoleMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.userRoleMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(UserRole record) {
        return this.userRoleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserRole record) {
        return this.userRoleMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(UserRoleExample example) {
        return this.userRoleMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(UserRole record, UserRoleExample example) {
        return this.userRoleMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(UserRole record, UserRoleExample example) {
        return this.userRoleMapper.updateByExample(record, example);
    }

    public int insert(UserRole record) {
        return this.userRoleMapper.insert(record);
    }

    public int insertSelective(UserRole record) {
        return this.userRoleMapper.insertSelective(record);
    }
}