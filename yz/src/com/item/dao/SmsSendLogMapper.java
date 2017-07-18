package com.item.dao;

import com.item.dao.model.SmsSendLog;
import com.item.dao.model.SmsSendLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsSendLogMapper {
    int countByExample(SmsSendLogExample example);

    int deleteByExample(SmsSendLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(SmsSendLog record);

    int insertSelective(SmsSendLog record);

    List<SmsSendLog> selectByExample(SmsSendLogExample example);

    SmsSendLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SmsSendLog record, @Param("example") SmsSendLogExample example);

    int updateByExample(@Param("record") SmsSendLog record, @Param("example") SmsSendLogExample example);

    int updateByPrimaryKeySelective(SmsSendLog record);

    int updateByPrimaryKey(SmsSendLog record);
}