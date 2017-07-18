package com.item.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class SmsSendUtil {

	private static final String USERNAME = "jfwl";
	private static String PASSWORD = "jackal";
	private static String url = "http://js10088.com:18001/?Action=SendSms&UserName=USERNAME&Password=PASSWORD&Mobile=MOBILE&Message=MESSAGE";
	
	static{
		PASSWORD = DigestUtils.md5Hex(PASSWORD);
		url = url.replaceAll("USERNAME", USERNAME).replaceAll("PASSWORD", PASSWORD);
	}
	
	public static String push(String mobile,String content){
		try {
			content = URLEncoder.encode(content, "utf-8");
			String sendUrl = url.replace("MOBILE", mobile).replace("MESSAGE", content);
			HttpClient client = new HttpClient();
	        HttpMethod method = new GetMethod( sendUrl );
	        // 这里设置字符编码，避免乱码
	        method.setRequestHeader("Content-Type", "text/html;charset=utf-8");
	 
	        client.executeMethod(method);
	        // 打印服务器返回的状态
	        System.out.println(method.getStatusLine());
	 
	        // 获取返回的html页面
	        byte[] body = method.getResponseBody();
	        // 打印返回的信息
	        String result = new String(body, "utf-8");
	        if(result.startsWith("0:")){
	        	System.out.println("发送成功！");
	        	return "2";
	        }
	        else
	        	System.out.println("发送失败！");
	        System.out.println(result);
	        // 释放连接
	        method.releaseConnection();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";
	}
}
