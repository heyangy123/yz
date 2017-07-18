package com.item.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;
import com.item.dao.model.Feedback;
import com.item.dao.model.FeedbackExample;
import com.item.service.FeedbackService;


@RequestMapping("feedback")
@Controller
@LoginFilter
public class FeedBackController extends CoreController {
	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping("/list")
	@ResponseBody
	public String findlist(HttpSession session, Integer page, Integer rows) {
		PaginationSupport.byPage(page, rows);
		FeedbackExample example = new FeedbackExample();
		example.setOrderByClause("create_time desc");
		List<Feedback> list = this.feedbackService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}

	@RequestMapping("/del")
	@ResponseBody
	public String del(String id) {
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			this.feedbackService.deleteByPrimaryKey(ids[i]);
		}
		return "{\"success\":true}";
	}

}
