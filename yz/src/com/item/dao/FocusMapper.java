package com.item.dao;

import com.item.dao.model.Focus;
import com.item.dao.model.FocusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FocusMapper {
    int countByExample(FocusExample example);

    int deleteByExample(FocusExample example);

    int deleteByPrimaryKey(String id);

    int insert(Focus record);

    int insertSelective(Focus record);

    List<Focus> selectByExampleWithBLOBs(FocusExample example);

    List<Focus> selectByExample(FocusExample example);

    Focus selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Focus record, @Param("example") FocusExample example);

    int updateByExampleWithBLOBs(@Param("record") Focus record, @Param("example") FocusExample example);

    int updateByExample(@Param("record") Focus record, @Param("example") FocusExample example);

    int updateByPrimaryKeySelective(Focus record);

    int updateByPrimaryKeyWithBLOBs(Focus record);

    int updateByPrimaryKey(Focus record);
}