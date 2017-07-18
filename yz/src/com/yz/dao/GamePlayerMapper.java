package com.yz.dao;

import com.yz.dao.model.GamePlayer;
import com.yz.dao.model.GamePlayerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GamePlayerMapper {
    int countByExample(GamePlayerExample example);

    int deleteByExample(GamePlayerExample example);

    int deleteByPrimaryKey(String id);

    int insert(GamePlayer record);

    int insertSelective(GamePlayer record);

    List<GamePlayer> selectByExample(GamePlayerExample example);

    GamePlayer selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GamePlayer record, @Param("example") GamePlayerExample example);

    int updateByExample(@Param("record") GamePlayer record, @Param("example") GamePlayerExample example);

    int updateByPrimaryKeySelective(GamePlayer record);

    int updateByPrimaryKey(GamePlayer record);
    
}