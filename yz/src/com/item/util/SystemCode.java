package com.item.util;

import com.base.CoreConstants;
import com.base.support.PropertySupport;

public class SystemCode extends CoreConstants{
	public static final String SELF_URL=PropertySupport.getProperty("self.url");
	public static final String IMG_URL=PropertySupport.getProperty("self.url")+"/download.do?id=";
	
	public static String getImgURL(String id,Integer w,Integer h){
		String ret = IMG_URL+id;
		if(w!=null) ret+="&w="+w;
		if(h!=null) ret+="&h="+h;
		return ret;
	}
}
