package com.yz.dao;

import com.yz.dao.model.SocialUpvote;
import com.yz.dao.model.SocialUpvoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SocialUpvoteMapper {
    int countByExample(SocialUpvoteExample example);

    int deleteByExample(SocialUpvoteExample example);

    int deleteByPrimaryKey(String id);

    int insert(SocialUpvote record);

    int insertSelective(SocialUpvote record);

    List<SocialUpvote> selectByExample(SocialUpvoteExample example);

    SocialUpvote selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SocialUpvote record, @Param("example") SocialUpvoteExample example);

    int updateByExample(@Param("record") SocialUpvote record, @Param("example") SocialUpvoteExample example);

    int updateByPrimaryKeySelective(SocialUpvote record);

    int updateByPrimaryKey(SocialUpvote record);
}