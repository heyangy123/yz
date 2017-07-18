package com.yz.dao;

import com.yz.dao.model.Vedio;
import com.yz.dao.model.VedioExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VedioMapper {
    int countByExample(VedioExample example);

    int deleteByExample(VedioExample example);

    int deleteByPrimaryKey(String id);

    int insert(Vedio record);

    int insertSelective(Vedio record);

    List<Vedio> selectByExample(VedioExample example);

    Vedio selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Vedio record, @Param("example") VedioExample example);

    int updateByExample(@Param("record") Vedio record, @Param("example") VedioExample example);

    int updateByPrimaryKeySelective(Vedio record);

    int updateByPrimaryKey(Vedio record);
    
    List<Vedio> selectVideoByExample(VedioExample example);
    
}