package com.item.service;

import com.item.dao.AreasMapper;
import com.item.dao.model.Areas;
import com.item.dao.model.AreasExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreasService {
    @Autowired
    private AreasMapper areasMapper;

    private static final Logger logger = Logger.getLogger(AreasService.class);

    public int countByExample(AreasExample example) {
        return this.areasMapper.countByExample(example);
    }

    public Areas selectByPrimaryKey(Integer id) {
        return this.areasMapper.selectByPrimaryKey(id);
    }

    public List<Areas> selectByExample(AreasExample example) {
        return this.areasMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.areasMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Areas record) {
        return this.areasMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Areas record) {
        return this.areasMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(AreasExample example) {
        return this.areasMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Areas record, AreasExample example) {
        return this.areasMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Areas record, AreasExample example) {
        return this.areasMapper.updateByExample(record, example);
    }

    public int insert(Areas record) {
        return this.areasMapper.insert(record);
    }

    public int insertSelective(Areas record) {
        return this.areasMapper.insertSelective(record);
    }
}