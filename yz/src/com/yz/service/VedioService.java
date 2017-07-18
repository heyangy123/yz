package com.yz.service;

import com.yz.dao.VedioMapper;
import com.yz.dao.model.Vedio;
import com.yz.dao.model.VedioExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VedioService {
    @Autowired
    private VedioMapper vedioMapper;

    private static final Logger logger = Logger.getLogger(VedioService.class);

    public int countByExample(VedioExample example) {
        return this.vedioMapper.countByExample(example);
    }

    public Vedio selectByPrimaryKey(String id) {
        return this.vedioMapper.selectByPrimaryKey(id);
    }

    public List<Vedio> selectByExample(VedioExample example) {
        return this.vedioMapper.selectByExample(example);
    }
    public List<Vedio> selectVideoByExample(VedioExample example) {
    	return this.vedioMapper.selectVideoByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.vedioMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Vedio record) {
        return this.vedioMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Vedio record) {
        return this.vedioMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(VedioExample example) {
        return this.vedioMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Vedio record, VedioExample example) {
        return this.vedioMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Vedio record, VedioExample example) {
        return this.vedioMapper.updateByExample(record, example);
    }

    public int insert(Vedio record) {
        return this.vedioMapper.insert(record);
    }

    public int insertSelective(Vedio record) {
        return this.vedioMapper.insertSelective(record);
    }
}