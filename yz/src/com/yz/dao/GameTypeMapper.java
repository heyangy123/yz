package com.yz.dao;

import com.yz.dao.model.GameType;
import com.yz.dao.model.GameTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameTypeMapper {
    int countByExample(GameTypeExample example);

    int deleteByExample(GameTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(GameType record);

    int insertSelective(GameType record);

    List<GameType> selectByExample(GameTypeExample example);

    GameType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GameType record, @Param("example") GameTypeExample example);

    int updateByExample(@Param("record") GameType record, @Param("example") GameTypeExample example);

    int updateByPrimaryKeySelective(GameType record);

    int updateByPrimaryKey(GameType record);
}