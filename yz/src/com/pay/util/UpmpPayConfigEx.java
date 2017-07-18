package com.pay.util;

import com.base.pay.PayPropertySupport;
import com.pay.acp.UpmpConfig;

public class UpmpPayConfigEx extends UpmpConfig{
	public static String MER_BACK_END_URL_TG;
	public static String MER_FRONT_END_URL_TG;
    public static String MER_FRONT_WEB_URL_TG;
	
	static {
	    try {
	    	MER_BACK_END_URL_TG = PayPropertySupport.getProperty("mer.activity.back.end.url");
	    	MER_FRONT_END_URL_TG = PayPropertySupport.getProperty("mer.activity.front.end.url");
	    	MER_FRONT_WEB_URL_TG = PayPropertySupport.getProperty("mer.activity.frontWeb.end.url");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
