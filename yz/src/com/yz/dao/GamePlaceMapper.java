package com.yz.dao;

import com.yz.dao.model.GamePlace;
import com.yz.dao.model.GamePlaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GamePlaceMapper {
    int countByExample(GamePlaceExample example);

    int deleteByExample(GamePlaceExample example);

    int deleteByPrimaryKey(String id);

    int insert(GamePlace record);

    int insertSelective(GamePlace record);

    List<GamePlace> selectByExample(GamePlaceExample example);

    GamePlace selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GamePlace record, @Param("example") GamePlaceExample example);

    int updateByExample(@Param("record") GamePlace record, @Param("example") GamePlaceExample example);

    int updateByPrimaryKeySelective(GamePlace record);

    int updateByPrimaryKey(GamePlace record);
}