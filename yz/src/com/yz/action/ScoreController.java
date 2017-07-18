package com.yz.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.item.dao.model.User;
import com.item.dao.model.UserExample;
import com.item.service.UserService;

@Controller
@RequestMapping("/score")
public class ScoreController extends CoreController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	@ResponseBody
	public String list(User user, Integer page,Integer rows, Model model) {
		PaginationSupport.byPage(page, rows);
		UserExample example = new UserExample();
		example.setOrderByClause("score");
		List<User> list = userService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}
	
	
}
