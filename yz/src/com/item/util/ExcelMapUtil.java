package com.item.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExcelMapUtil {
	private static  Map<String, String>  map ;
	static{
		map = new HashMap<String, String>();
		map.put("title", "name");
		map.put("price", "price");
		map.put("num", "num");
		map.put("description", "content");
		map.put("picture", "imgs");
	}
	
	public static boolean isKeyContains(String key){
		Set<String> keys = map.keySet();
		return keys.contains(key);
	}
	public static String get(String key){
		return map.get(key);
	}
	

}
