package com.base.mobile.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.CoreConstants;
import com.base.exception.SystemException;
import com.base.mobile.MFrameEnumError;
import com.base.mobile.MobException;
import com.base.mobile.MobileData;
import com.base.mobile.MobileDataUtil;
import com.base.mobile.MobileInfo;
import com.base.mobile.base.DES;
import com.base.mobile.base.MRequest;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.util.ClassUtils;
import com.base.util.JSONUtils;
import com.base.util.ProtobufUtils;
import com.base.util.SpringContextUtil;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;

public class MobileJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(MobileJsonServlet.class);

	//保存接口方法注解
	private Map<String, MobileMethod> mobileMethodAnnoMap = new HashMap();
	//保存接口方法
	private Map<String, Method> mobileMethodMap = new HashMap();

	public void init() throws ServletException {
		try {
			// 获取所有的手机接口类
			Map mobileClz = SpringContextUtil.getBeansWithAnnotation(Mobile.class);
			if (mobileClz != null) {
				//遍历所有接口类
				for (Iterator localIterator = mobileClz.values().iterator(); localIterator.hasNext();) {
					Object clzObj = localIterator.next();
					//获取类所有接口方法
					List<Method> methodList = ClassUtils.getClassMethodByAnnotation(clzObj.getClass(), MobileMethod.class);

					//遍历所有接口方法
					for (Method method : methodList) {
						MobileMethod methodAnno = (MobileMethod) method.getAnnotation(MobileMethod.class);

						String methodName = methodAnno.methodno();
						if (StringUtils.isBlank(methodName)) {
							continue;
						}

						if (this.mobileMethodAnnoMap.containsKey(methodName)) {
							throw new SystemException("手机接口:" + methodName + "有重复定义,请检查代码!");
						}
						this.mobileMethodMap.put(methodName, method);
						this.mobileMethodAnnoMap.put(methodName, methodAnno);
						MobileData.putMobileMethod("mobile", methodName, methodAnno, method);
					}
				}
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  //获取请求参数
		Map paramMap;
		try {
			paramMap = getParam(req);
			String methodName = (String)paramMap.get("methodno");
			paramMap.remove("methodno");
		    paramMap.remove("debug");
		    
		    MobileMethod mobileMethod = (MobileMethod)this.mobileMethodAnnoMap.get(methodName);
		    
			if (mobileMethod == null) {
				throw new SystemException("请求的接口:" + methodName + "在服务器端没有定义,请检查!");
			}
			
			Method method = (Method) this.mobileMethodMap.get(methodName);
			
			MobileDataUtil.data(req, resp, mobileMethod, method, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	public void destroy() {
		this.mobileMethodMap.clear();
		this.mobileMethodAnnoMap.clear();
	}

	private Map<String, Object> getParam(HttpServletRequest request) throws Exception {
		Map paramMap = new HashMap();
		String methodName = null;
		for (Object paramName : request.getParameterMap().keySet()) {
			if (!StringUtils.isNotBlank((String) paramName)){
				continue;
			}
			String paramValue = request.getParameter((String) paramName);

			if ("methodno".equals(paramName)) {
				methodName = paramValue;
			}

			paramMap.put(paramName, paramValue);
		}

		byte[] protobuf = StreamUtils.copyToByteArray(request.getInputStream());

		MRequest.Msg_Request.Builder requestProtobuf = (MRequest.Msg_Request.Builder) ProtobufUtils.deserialize(
				protobuf, MRequest.Msg_Request.class);

		for (int i = 0; i < requestProtobuf.getPostsCount(); i++) {
			paramMap.put(requestProtobuf.getPosts(i).getName(), requestProtobuf.getPosts(i).getValue());
		}

		if ((requestProtobuf.getRequestMessage() != null) && (requestProtobuf.getRequestMessage().size() > 0)) {
			MobileMethod mobileMethod = (MobileMethod) this.mobileMethodAnnoMap.get(methodName);

			if (mobileMethod != null) {
				Class requestClz = mobileMethod.reqClz();
				if (requestClz != null){
					//paramMap.put("requestProto", JSONUtils.jsonToPb(requestClz, req))
				}
				paramMap.put("requestProto", ProtobufUtils.deserialize(requestProtobuf.getRequestMessage(), requestClz));
			}

		}
		
		MobileInfo mobileInfo = new MobileInfo((String) paramMap.get("userid"), (String) paramMap.get("deviceid"),
				(String) paramMap.get("appid"),(String) paramMap.get("areaCode"));
		paramMap.put("mobileInfo", mobileInfo);
		return paramMap;
	}
	
	private Object requestMethod(String methodName, MobileMethod mobileMethod, Map<String, Object> paramMap)
			throws Exception {
		Method method = (Method) this.mobileMethodMap.get(methodName);
		Object methodResult = ClassUtils.invokeMethod(method.getDeclaringClass().getSimpleName(), method.getName(), paramMap);
		return methodResult;
	}
}
