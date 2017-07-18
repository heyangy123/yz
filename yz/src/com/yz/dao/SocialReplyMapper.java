package com.yz.dao;

import com.yz.dao.model.SocialReply;
import com.yz.dao.model.SocialReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SocialReplyMapper {
    int countByExample(SocialReplyExample example);

    int deleteByExample(SocialReplyExample example);

    int deleteByPrimaryKey(String id);

    int insert(SocialReply record);

    int insertSelective(SocialReply record);

    List<SocialReply> selectByExample(SocialReplyExample example);

    SocialReply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SocialReply record, @Param("example") SocialReplyExample example);

    int updateByExample(@Param("record") SocialReply record, @Param("example") SocialReplyExample example);

    int updateByPrimaryKeySelective(SocialReply record);

    int updateByPrimaryKey(SocialReply record);
}