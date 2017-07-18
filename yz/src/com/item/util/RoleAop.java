package com.item.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.base.web.annotation.LoginMethod;

@Aspect
@Component
public class RoleAop {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**  
     * go方法  
     */  
    private final static String el = "execution(* com.base.action.SysController.go(..))";  
  
    @SuppressWarnings("unchecked")
	@Around(el)  
    public Object around(ProceedingJoinPoint p) {  
    	HttpServletRequest request = (HttpServletRequest) p.getArgs()[0];
        Object ob = null;  
        try {  
        	HttpSession session = request.getSession();
        	if (session == null){
        		ob = p.proceed();  
    		}else {
    			String path = request.getParameter("path");
    			Map<String, Object> params = (Map<String, Object>) session.getAttribute(LoginMethod.ADMIN.getName());
    			if (params != null){
    				String role =  (String) params.get("roleId");
        			if (StringUtils.isNotBlank(path) && RoleUtil.isUrl(path)){
        				if (RoleUtil.isMenu(role, path)){
        					ob = p.proceed();
        				}
        			}else {
        				ob = p.proceed();  
    				}
    			}else {
					ob = p.proceed();
				}
			}
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
  
        return ob;  
    }  
      
    @AfterThrowing(value = el, throwing="e")  
    public void throwing(Exception e){  
        System.out.println("出异常了:"+e);  
    }  
}
