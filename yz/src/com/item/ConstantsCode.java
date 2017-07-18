package com.item;

import com.base.support.PropertySupport;

public class ConstantsCode {

	public static final String WELCOME_IMG = "welcome@sys";//欢迎页
	public static final String OAUTH_LOGIN = "oauthLogin@sys";//第三方登录开关
	public static final String SERVER_URL = PropertySupport.getProperty("server.url");
	public static final String ICON_URL = SERVER_URL + "download?id=";
}
