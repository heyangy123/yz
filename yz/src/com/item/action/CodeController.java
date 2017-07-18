package com.item.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.item.dao.model.Code;
import com.item.dao.model.CodeExample;
import com.item.dao.model.CodeExample.Criteria;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.item.service.CodeService;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;

@RequestMapping("code")
@Controller
public class CodeController extends CoreController{
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/list")
	@ResponseBody
	@LoginFilter
	public String list(Integer page,Integer rows,String code){
		PaginationSupport.byPage(page, rows);
		CodeExample example = new CodeExample();
		Criteria criteria = example.createCriteria().andCodeNotLike("%@sys");//去除单独维护的参数
		if(!StringUtils.isBlank(code)){
			criteria.andCodeEqualTo(code);
		}
		List<Code> list = codeService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}
	@RequestMapping("findById")
	@ResponseBody
	public String findById(String code){
		Code codes = codeService.get(code);
		return JSONUtils.serialize(codes);
	}
	@RequestMapping("save")
	@ResponseBody
	@LoginFilter
	public String save(Code code){
		this.codeService.updateByPrimaryKeySelective(code);
		return JSONUtils.serialize(new Ret(0));
	}
	@RequestMapping("/del")
	@ResponseBody
	@LoginFilter
	public String del(String code)throws Exception{
		String[] ids = code.split(",");
		for (String c : ids) {
			codeService.deleteByPrimaryKey(c);
		}
		return "{\"success\":true}";
	}
}
