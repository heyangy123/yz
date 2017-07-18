package com.yz.service;

import com.yz.dao.GamePlaceMapper;
import com.yz.dao.model.GamePlace;
import com.yz.dao.model.GamePlaceExample;
import com.yz.daoEx.GamePlaceMapperEx;
import com.yz.daoEx.model.GamePlaceEx;
import com.yz.daoEx.model.GamePlayerEx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamePlaceService {
    @Autowired
    private GamePlaceMapper gamePlaceMapper;
    @Autowired
    private GamePlaceMapperEx gamePlaceMapperEx;

    private static final Logger logger = Logger.getLogger(GamePlaceService.class);

    public int countByExample(GamePlaceExample example) {
        return this.gamePlaceMapper.countByExample(example);
    }

    public GamePlace selectByPrimaryKey(String id) {
        return this.gamePlaceMapper.selectByPrimaryKey(id);
    }

    public List<GamePlace> selectByExample(GamePlaceExample example) {
        return this.gamePlaceMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.gamePlaceMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(GamePlace record) {
        return this.gamePlaceMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(GamePlace record) {
        return this.gamePlaceMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(GamePlaceExample example) {
        return this.gamePlaceMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(GamePlace record, GamePlaceExample example) {
        return this.gamePlaceMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(GamePlace record, GamePlaceExample example) {
        return this.gamePlaceMapper.updateByExample(record, example);
    }

    public int insert(GamePlace record) {
        return this.gamePlaceMapper.insert(record);
    }

    public int insertSelective(GamePlace record) {
        return this.gamePlaceMapper.insertSelective(record);
    }
    
    public List<GamePlaceEx> selectList(){
    	return gamePlaceMapperEx.selectList();
    }
    public GamePlaceEx selectByPlaceId(String id){
    	return gamePlaceMapperEx.selectByPlaceId(id);
    }
    
}