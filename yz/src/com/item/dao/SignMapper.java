package com.item.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.item.dao.model.Sign;
import com.item.dao.model.SignExample;

public interface SignMapper {
	int countByExample(SignExample example);

	int deleteByExample(SignExample example);

	int deleteByPrimaryKey(String id);

	int insert(Sign record);

	int insertSelective(Sign record);

	List<Sign> selectByExample(SignExample example);

	Sign selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Sign record,@Param("example") SignExample example);

	int updateByExample(@Param("record") Sign record,@Param("example") SignExample example);

	int updateByPrimaryKeySelective(Sign record);

	int updateByPrimaryKey(Sign record);

}
