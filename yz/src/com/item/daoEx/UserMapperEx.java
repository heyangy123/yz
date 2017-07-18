package com.item.daoEx;

import java.util.List;
import java.util.Map;

import com.item.dao.model.User;
import com.item.daoEx.model.UserEx;


public interface UserMapperEx{
	List<UserEx> selectList(Map<String, Object> map);
	List<UserEx> selectPalyer(Map<String, Object> map);
	List<UserEx> selectFriend(Map<String, Object> map);
	UserEx selectUserDetail(Map<String, Object> map);
	int updateScorePlus(User user);
	
 }