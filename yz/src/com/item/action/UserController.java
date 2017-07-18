package com.item.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;
import com.item.dao.model.User;
import com.item.daoEx.model.UserEx;
import com.item.service.UserService;
import com.mdx.mobile.commons.verfy.Md5;

@RequestMapping("user")
@Controller
public class UserController extends CoreController{
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	@ResponseBody
	@LoginFilter
	public String selectUserList(String name,Integer page,Integer rows) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("key", name);
		PaginationSupport.byPage(page, rows);
		List<UserEx> list= userService.selectList(map);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(User user){
		if(!StringUtils.isBlank(user.getPassword()))
		    user.setPassword(Md5.mD5(user.getPassword()));
		this.userService.updateByPrimaryKeySelective(user);
		return JSONUtils.serialize(new Ret(0));
	}
}
