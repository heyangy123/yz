package com.yz.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yz.dao.GameMapper;
import com.yz.dao.model.Game;
import com.yz.dao.model.GameExample;
import com.yz.daoEx.GameMapperEx;
import com.yz.daoEx.model.GameEx;

@Service
public class GameService {
    @Autowired
    private GameMapper gameMapper;
    
    
    @Autowired
    private GameMapperEx gameMapperEx;

    private static final Logger logger = Logger.getLogger(GameService.class);

    public int countByExample(GameExample example) {
        return this.gameMapper.countByExample(example);
    }

    public Game selectByPrimaryKey(String id) {
        return this.gameMapper.selectByPrimaryKey(id);
    }

    public List<Game> selectByExample(GameExample example) {
        return this.gameMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.gameMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Game record) {
        return this.gameMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Game record) {
        return this.gameMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(GameExample example) {
        return this.gameMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Game record, GameExample example) {
        return this.gameMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Game record, GameExample example) {
        return this.gameMapper.updateByExample(record, example);
    }

    public int insert(Game record) {
        return this.gameMapper.insert(record);
    }

    public int insertSelective(Game record) {
        return this.gameMapper.insertSelective(record);
    }
    
    public List<GameEx> selectAppList(Map<String,Object> map){
    	return this.gameMapperEx.selectAppList(map);
    }
    
    public GameEx selectGameDetail (Map<String, Object> map){
    	return this.gameMapperEx.selectGameDetail(map);
    }
    
    public GameEx selectShare (Map<String, Object> map){
    	return this.gameMapperEx.selectShare(map);
    }

	public List<GameEx> selectAppMyGameList(Map<String, Object> map) {
		return this.gameMapperEx.selectAppMyGameList(map);
	}
}