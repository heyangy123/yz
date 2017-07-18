package com.base.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseUtils {
	private static Log logger = LogFactory.getLog(ResponseUtils.class);

	public static void renderJson(HttpServletResponse response, Object object) {
		System.out.println("转换啦");
		render(response, JSONUtils.serialize(object), "application/json;charset=UTF-8");
	}

	public static void renderJsonp(HttpServletResponse response, Object object) {
		render(response, "jsonpcallback(" + JSONUtils.serialize(object) + ")", "text/plain;charset=UTF-8");
	}

	public static void render(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void renderText(HttpServletResponse response, String text) {
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static String getHostURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	}

	public static String getHostURLWithContextPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	}
}