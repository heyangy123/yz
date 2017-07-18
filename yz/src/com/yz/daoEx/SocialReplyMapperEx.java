package com.yz.daoEx;

import java.util.List;
import java.util.Map;

import com.yz.daoEx.model.SocialReplyEx;


public interface SocialReplyMapperEx{
	List<SocialReplyEx> selectAppList(String id);
}