package com.yz.daoEx;

import java.util.List;

import com.yz.daoEx.model.GamePlaceEx;


public interface GamePlaceMapperEx{
	
	List<GamePlaceEx> selectList();
	
	GamePlaceEx selectByPlaceId(String id);
	
}