package com.item.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.base.CoreConstants;
import com.base.util.HttpClientUtils;
import com.mdx.mobile.commons.verfy.Md5;

/**
 * 短信通道
 */
@Service
public class WxSendsmsUtil {
	private static final Logger logger = LoggerFactory.getLogger(WxSendsmsUtil.class);
	private static final String account = CoreConstants.SENDSMS_ACCOUNT;
	private static final String password = CoreConstants.SENDSMS_PWD;
	private static final String url = "http://api.smsbao.com/sms";
	private static final Header header = new Header() {
		public String getValue() {
			return "application/x-www-form-urlencoded;charset=UTF-8";
		}
		public String getName() {
			return "ContentType";
		}
		public HeaderElement[] getElements() throws ParseException {
			return null;
		}
	};
	private static Map<String, WxSendsms> smsError;
	
	public static String push(String mobile,String content){
		Map<String, String> params = new HashMap<String, String>();
		params.put("u", account);
		params.put("p", Md5.mD5(password));
		params.put("m", mobile);
		params.put("c", content);
		String code = HttpClientUtils.post(url, params, "UTF-8", "UTF-8",new Header[]{header});
		logger.info("短信通道反馈：["+mobile+"]["+ code + "][" + content + "]");
		if(code.equals("0"))code = "2";
		return code;
	}
	
	private static void init(){
		smsError = new HashMap<String, WxSendsms>();
		WxSendsms[] arr = WxSendsms.values();
		for (WxSendsms sendsms : arr){
			smsError.put(sendsms.getCode(), sendsms);
		}
	}
	
	public static String getSmsError(String code) throws Exception {
		if (smsError == null) init();
		if(smsError.get(code) != null)
			return smsError.get(code).getMsg();
		else
			return null;
	}
	
}
