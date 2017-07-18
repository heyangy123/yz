package com.yz.daoEx;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yz.dao.model.GamePlayer;
import com.yz.daoEx.model.GamePlayerEx;


public interface GamePlayerMapperEx{
	GamePlayerEx selectCaptain(Map<String,Object> map);

	List<GamePlayerEx> selectByGameId(@Param("gameId") String gameId, @Param("type") Integer type);
}