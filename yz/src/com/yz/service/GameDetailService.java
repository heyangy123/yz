package com.yz.service;

import com.yz.dao.GameDetailMapper;
import com.yz.dao.model.GameDetail;
import com.yz.dao.model.GameDetailExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameDetailService {
    @Autowired
    private GameDetailMapper gameDetailMapper;

    private static final Logger logger = Logger.getLogger(GameDetailService.class);

    public int countByExample(GameDetailExample example) {
        return this.gameDetailMapper.countByExample(example);
    }

    public GameDetail selectByPrimaryKey(String id) {
        return this.gameDetailMapper.selectByPrimaryKey(id);
    }

    public List<GameDetail> selectByExample(GameDetailExample example) {
        return this.gameDetailMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.gameDetailMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(GameDetail record) {
        return this.gameDetailMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(GameDetail record) {
        return this.gameDetailMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(GameDetailExample example) {
        return this.gameDetailMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(GameDetail record, GameDetailExample example) {
        return this.gameDetailMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(GameDetail record, GameDetailExample example) {
        return this.gameDetailMapper.updateByExample(record, example);
    }

    public int insert(GameDetail record) {
        return this.gameDetailMapper.insert(record);
    }

    public int insertSelective(GameDetail record) {
        return this.gameDetailMapper.insertSelective(record);
    }
}