package com.item.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Ret;
import com.base.util.ClassUtils;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;
import com.base.web.annotation.LoginMethod;
import com.item.dao.model.Role;
import com.item.dao.model.UserGroup;
import com.item.util.RoleUtil;


@Controller
@RequestMapping("manager")
public class LoginController extends CoreController{
	@Autowired
	private RoleUtil roleUtil;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String defalut(HttpSession session,HttpServletRequest request) throws Exception {
		String sys = getSessionParameter("sys", session);
		if(!sys.equals("")){
			return "redirect:manager/"+sys;
		}
		String temp = "admin";
		Cookie[] cookies = request.getCookies();
		Cookie c = null;
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			c = cookies[i];
			if (c.getName().equals("sys")) {
				temp = c.getValue();
				break;
			}
		}
		return "redirect:manager/"+temp;
	}

	@RequestMapping("{system}")
	public String index(@PathVariable("system")String system ,HttpSession session, String role, ModelMap model) throws Exception {
		if (!RoleUtil.isGroup(system)){
			return "common/404";
		}else {
			model.put("sys", system);
			model.put("sysName", RoleUtil.getGroupName(system));
		}
		Object obj = session.getAttribute(LoginMethod.ADMIN.getName());
		if (obj != null && system.equals(getSessionParameter("groupCode", session))) {
			return "sys/main";
		} else {
			session.invalidate();
		}
		return "sys/login";
	}
	
	@RequestMapping("{system}/login")
	@ResponseBody
	public String login(@PathVariable("system")String system ,String account, String pwd, HttpSession session) throws Exception {
		
		UserGroup group = RoleUtil.getGroupByCode(system);
		
		if (group == null){
			return JSONUtils.serialize(new Ret(1, "登录错误L10000,联系管理员"));
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		params.put("password", pwd);
		
		Object object = null;
		try {
			String className = group.getClassName();
			object = ClassUtils.invokeMethod(group.getClassName(), "login", params);
		} catch (Exception e) {
			logger.error(group.getClassName()+"中没有定义login(account,password)方法");
			return JSONUtils.serialize(new Ret(1, "登录错误L10001,联系管理员"));
		}

		if (object == null){
			return JSONUtils.serialize(new Ret(1, "用户名密码不正确"));
		}
		
		String id = null;
		String roleCode = null;
		String typeCode = null;
		try {
			id = BeanUtils.getProperty(object, "id");
			roleCode = BeanUtils.getProperty(object, "roleCode");
		} catch (Exception e) {
			logger.error("对象没有定义id或者roleCode字段");
			return JSONUtils.serialize(new Ret(1, "登录错误L10002,联系管理员"));
		}

		if (!RoleUtil.isGroupRole(roleCode, system)){
			logger.error("登录失败（不属于该用户组）");
			return JSONUtils.serialize(new Ret(1, "用户名密码不正确"));
		}
		
		Role role = RoleUtil.getRoleByCode(roleCode);
		
		if (role == null){
			return JSONUtils.serialize(new Ret(1, "登录错误L10003,联系管理员"));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("account", account);
		map.put("sys", group.getCode());
		map.put("sysname", group.getInfo());
		map.put("groupId", group.getId());
		map.put("groupWelcome", StringUtils.isBlank(group.getUrl())?"/base/jsp/welcome.jsp":group.getUrl());
		map.put("groupCode", group.getCode());
		map.put("roleId", role.getId());
		map.put("roleCode", role.getCode());
		map.put("rolename", role.getName());
		map.put("typeCode", typeCode);
		map.put("menu", JSONUtils.serialize(RoleUtil.initMenus(RoleUtil.getRightsByCode(group.getCode(),roleCode))));
		session.setAttribute(LoginMethod.ADMIN.getName(), map);
//		System.out.println(map.toString());
		return JSONUtils.serialize(new Ret(0,group.getUrl()));
	}
	
	@RequestMapping("/changePwd")
	@ResponseBody
	@LoginFilter
	public String changePwd(String oldpwd, String newpwd, HttpSession session) throws Exception {
		String account = getSessionParameter("account", session);
		String groupCode = getSessionParameter("groupCode", session);
		UserGroup group = RoleUtil.getGroupByCode(groupCode);
		String sys = getSessionParameter("sys", session);
		String roleCode = getSessionParameter("roleCode", session);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		params.put("password", oldpwd);
		
		Object object = null;
		
		try {
			object = ClassUtils.invokeMethod(group.getClassName(), "login", params);
		} catch (Exception e) {
			logger.error(group.getClassName()+"中没有定义login(account,password)方法");
			return JSONUtils.serialize(new Ret(1, "错误C10001,联系管理员"));
		}
		

		if (object != null){
			String id = null;
			try {
				id = BeanUtils.getProperty(object, "id");
			} catch (Exception e) {
				logger.error("对象没有定义id字段");
				return JSONUtils.serialize(new Ret(1, "登录错误C10002,联系管理员"));
			}

			params.clear();
			params.put("id", id);
			params.put("password", newpwd);
			
			try {
				if(sys.equals("agent") && !roleCode.equals("agent")){
					params.put("account", account);
					ClassUtils.invokeMethod("AdminService", "changePwdByAccount", params);
				}else{
					ClassUtils.invokeMethod(group.getClassName(), "changePwd", params);
				}
			} catch (Exception e) {
				logger.error(group.getClassName()+"中没有定义changePwd(id,password)方法");
				return JSONUtils.serialize(new Ret(1, "错误C10003,联系管理员"));
			}
			
			return JSONUtils.serialize(new Ret(0));
		}
		return  JSONUtils.serialize(new Ret(1,"密码错误"));
	}
	
	
	// 注销
	@RequestMapping("/logout")
	@LoginFilter
	public String logout(HttpSession session) {
		String sys = getSessionParameter("sys", session);
		if (StringUtils.isBlank(sys))
			return "error/error";
		session.invalidate();
		return "redirect:/manager/"+sys;
	}
	
	
}
