package com.yz.service;

import com.yz.dao.BannerMapper;
import com.yz.dao.model.Banner;
import com.yz.dao.model.BannerExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    private static final Logger logger = Logger.getLogger(BannerService.class);

    public int countByExample(BannerExample example) {
        return this.bannerMapper.countByExample(example);
    }

    public Banner selectByPrimaryKey(String id) {
        return this.bannerMapper.selectByPrimaryKey(id);
    }

    public List<Banner> selectByExample(BannerExample example) {
        return this.bannerMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.bannerMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Banner record) {
        return this.bannerMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Banner record) {
        return this.bannerMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(BannerExample example) {
        return this.bannerMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Banner record, BannerExample example) {
        return this.bannerMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Banner record, BannerExample example) {
        return this.bannerMapper.updateByExample(record, example);
    }

    public int insert(Banner record) {
        return this.bannerMapper.insert(record);
    }

    public int insertSelective(Banner record) {
        return this.bannerMapper.insertSelective(record);
    }
}