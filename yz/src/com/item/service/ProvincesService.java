package com.item.service;

import com.item.dao.ProvincesMapper;
import com.item.dao.model.Provinces;
import com.item.dao.model.ProvincesExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvincesService {
    @Autowired
    private ProvincesMapper provincesMapper;

    private static final Logger logger = Logger.getLogger(ProvincesService.class);

    public int countByExample(ProvincesExample example) {
        return this.provincesMapper.countByExample(example);
    }

    public Provinces selectByPrimaryKey(Integer id) {
        return this.provincesMapper.selectByPrimaryKey(id);
    }

    public List<Provinces> selectByExample(ProvincesExample example) {
        return this.provincesMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.provincesMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Provinces record) {
        return this.provincesMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Provinces record) {
        return this.provincesMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(ProvincesExample example) {
        return this.provincesMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Provinces record, ProvincesExample example) {
        return this.provincesMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Provinces record, ProvincesExample example) {
        return this.provincesMapper.updateByExample(record, example);
    }

    public int insert(Provinces record) {
        return this.provincesMapper.insert(record);
    }

    public int insertSelective(Provinces record) {
        return this.provincesMapper.insertSelective(record);
    }
}