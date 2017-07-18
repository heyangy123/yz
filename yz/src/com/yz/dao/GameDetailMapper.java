package com.yz.dao;

import com.yz.dao.model.GameDetail;
import com.yz.dao.model.GameDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameDetailMapper {
    int countByExample(GameDetailExample example);

    int deleteByExample(GameDetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(GameDetail record);

    int insertSelective(GameDetail record);

    List<GameDetail> selectByExample(GameDetailExample example);

    GameDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GameDetail record, @Param("example") GameDetailExample example);

    int updateByExample(@Param("record") GameDetail record, @Param("example") GameDetailExample example);

    int updateByPrimaryKeySelective(GameDetail record);

    int updateByPrimaryKey(GameDetail record);
}