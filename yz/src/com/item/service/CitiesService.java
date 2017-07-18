package com.item.service;

import com.item.dao.CitiesMapper;
import com.item.dao.model.Cities;
import com.item.dao.model.CitiesExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitiesService {
    @Autowired
    private CitiesMapper citiesMapper;

    private static final Logger logger = Logger.getLogger(CitiesService.class);

    public int countByExample(CitiesExample example) {
        return this.citiesMapper.countByExample(example);
    }

    public Cities selectByPrimaryKey(Integer id) {
        return this.citiesMapper.selectByPrimaryKey(id);
    }

    public List<Cities> selectByExample(CitiesExample example) {
        return this.citiesMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.citiesMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Cities record) {
        return this.citiesMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Cities record) {
        return this.citiesMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(CitiesExample example) {
        return this.citiesMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Cities record, CitiesExample example) {
        return this.citiesMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Cities record, CitiesExample example) {
        return this.citiesMapper.updateByExample(record, example);
    }

    public int insert(Cities record) {
        return this.citiesMapper.insert(record);
    }

    public int insertSelective(Cities record) {
        return this.citiesMapper.insertSelective(record);
    }
}