package com.yz.service;

import com.yz.dao.GameTypeMapper;
import com.yz.dao.model.GameType;
import com.yz.dao.model.GameTypeExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameTypeService {
    @Autowired
    private GameTypeMapper gameTypeMapper;

    private static final Logger logger = Logger.getLogger(GameTypeService.class);

    public int countByExample(GameTypeExample example) {
        return this.gameTypeMapper.countByExample(example);
    }

    public GameType selectByPrimaryKey(String id) {
        return this.gameTypeMapper.selectByPrimaryKey(id);
    }

    public List<GameType> selectByExample(GameTypeExample example) {
        return this.gameTypeMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.gameTypeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(GameType record) {
        return this.gameTypeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(GameType record) {
        return this.gameTypeMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(GameTypeExample example) {
        return this.gameTypeMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(GameType record, GameTypeExample example) {
        return this.gameTypeMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(GameType record, GameTypeExample example) {
        return this.gameTypeMapper.updateByExample(record, example);
    }

    public int insert(GameType record) {
        return this.gameTypeMapper.insert(record);
    }

    public int insertSelective(GameType record) {
        return this.gameTypeMapper.insertSelective(record);
    }
}