package com.yz.daoEx;

import java.util.List;
import java.util.Map;

import com.yz.daoEx.model.GameEx;


public interface GameMapperEx{

	List<GameEx> selectAppList(Map<String,Object> map);

	GameEx selectGameDetail (Map<String, Object> map);


	List<GameEx> selectList(Map<String, Object> map);

	GameEx selectShare(Map<String, Object> map);

	List<GameEx> selectAppMyGameList(Map<String, Object> map);
	
}