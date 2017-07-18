package com.yz.service;

import com.yz.dao.GamePlayerMapper;
import com.yz.dao.model.GamePlayer;
import com.yz.dao.model.GamePlayerExample;
import com.yz.daoEx.GamePlayerMapperEx;
import com.yz.daoEx.model.GamePlayerEx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamePlayerService {
    @Autowired
    private GamePlayerMapper gamePlayerMapper;
    
    @Autowired
    private GamePlayerMapperEx gamePlayerMapperEx;

    private static final Logger logger = Logger.getLogger(GamePlayerService.class);

    public int countByExample(GamePlayerExample example) {
        return this.gamePlayerMapper.countByExample(example);
    }

    public GamePlayer selectByPrimaryKey(String id) {
        return this.gamePlayerMapper.selectByPrimaryKey(id);
    }

    public List<GamePlayer> selectByExample(GamePlayerExample example) {
        return this.gamePlayerMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.gamePlayerMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(GamePlayer record) {
        return this.gamePlayerMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(GamePlayer record) {
        return this.gamePlayerMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(GamePlayerExample example) {
        return this.gamePlayerMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(GamePlayer record, GamePlayerExample example) {
        return this.gamePlayerMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(GamePlayer record, GamePlayerExample example) {
        return this.gamePlayerMapper.updateByExample(record, example);
    }

    public int insert(GamePlayer record) {
        return this.gamePlayerMapper.insert(record);
    }

    public int insertSelective(GamePlayer record) {
        return this.gamePlayerMapper.insertSelective(record);
    }
    
    public List<GamePlayerEx> selectByGameId(String gameId, Integer type){
    	return this.gamePlayerMapperEx.selectByGameId(gameId, type);
    }
    
    public GamePlayerEx selectCaptain(Map<String,Object> map){
    	return this.gamePlayerMapperEx.selectCaptain(map);
    }
}