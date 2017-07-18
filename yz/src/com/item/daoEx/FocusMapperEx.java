package com.item.daoEx;

import java.util.List;
import java.util.Map;

import com.item.daoEx.model.FocusEx;

public interface FocusMapperEx {

	List<FocusEx> selectList(Map<String, Object> map);

}
