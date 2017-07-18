package com.yz.daoEx;

import java.util.List;
import java.util.Map;

import com.yz.daoEx.model.SocialUpvoteEx;


public interface SocialUpvoteMapperEx{
	List<SocialUpvoteEx> selectList (String id);
}