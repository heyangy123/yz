package com.item.daoEx;

import java.util.List;
import java.util.Map;

import com.item.daoEx.model.UserNotifyEx;


public interface UserNotifyMapperEx{
	List<UserNotifyEx> selectAppList(Map<String, Object> map);
}