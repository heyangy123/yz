package com.yz.daoEx;

import java.util.List;
import java.util.Map;

import com.yz.daoEx.model.SocialEx;


public interface SocialMapperEx{
	List<SocialEx> selectAppList(Map<String,Object> map);
}