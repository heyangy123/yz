package com.item.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Ret;
import com.item.dao.model.SinglePage;
import com.item.service.SinglePageService;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;

@Controller
@LoginFilter
@RequestMapping(value = "sp")
public class SinglePageController extends CoreController{
	@Autowired
	SinglePageService singlePageService;
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public String save(SinglePage news)throws Exception{
		singlePageService.updateByPrimaryKeySelective(news);
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/findByCode")
	@ResponseBody
	public String findByCode(String code){
		SinglePage singlePage = singlePageService.selectByPrimaryKey(code);
		return JSONUtils.serialize(singlePage);
	}
}
