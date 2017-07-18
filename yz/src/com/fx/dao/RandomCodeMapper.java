package com.fx.dao;

import com.fx.dao.model.RandomCode;
import com.fx.dao.model.RandomCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RandomCodeMapper {
    int countByExample(RandomCodeExample example);

    int deleteByExample(RandomCodeExample example);

    int deleteByPrimaryKey(String id);

    int insert(RandomCode record);

    int insertSelective(RandomCode record);

    List<RandomCode> selectByExample(RandomCodeExample example);

    RandomCode selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RandomCode record, @Param("example") RandomCodeExample example);

    int updateByExample(@Param("record") RandomCode record, @Param("example") RandomCodeExample example);

    int updateByPrimaryKeySelective(RandomCode record);

    int updateByPrimaryKey(RandomCode record);
}