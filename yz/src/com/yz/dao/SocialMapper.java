package com.yz.dao;

import com.yz.dao.model.Social;
import com.yz.dao.model.SocialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SocialMapper {
    int countByExample(SocialExample example);

    int deleteByExample(SocialExample example);

    int deleteByPrimaryKey(String id);

    int insert(Social record);

    int insertSelective(Social record);

    List<Social> selectByExample(SocialExample example);

    Social selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Social record, @Param("example") SocialExample example);

    int updateByExample(@Param("record") Social record, @Param("example") SocialExample example);

    int updateByPrimaryKeySelective(Social record);

    int updateByPrimaryKey(Social record);
}