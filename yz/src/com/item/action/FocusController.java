package com.item.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.item.dao.model.Focus;
import com.item.dao.model.FocusExample;
import com.item.daoEx.model.FocusEx;
import com.item.service.FocusService;


@Controller
@RequestMapping("focus")
@LoginFilter
public class FocusController extends CoreController{
	@Autowired
	private FocusService focusService;
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(Integer page,Integer rows,Integer location,String flag,HttpSession session){
		String areaCode = getSessionParameter("areaCode", session);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("location", location);
		map.put("areaCode", areaCode);
		map.put("flag", flag);
		if(StringUtils.isNotBlank(flag)){
			List<FocusEx> list = focusService.selectList(map);
			return JSONUtils.serialize(list);
		}else{
			PaginationSupport.byPage(page, rows);
			List<FocusEx> list = focusService.selectList(map);
			return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
		}
		
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		String[] ids =id.split(",");
		for (int i = 0; i < ids.length; i++) {
			focusService.deleteByPrimaryKey(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String saveIndexFocus(Focus focus,HttpSession session){
		String areaCode = getSessionParameter("areaCode", session);
		if(StringUtils.isBlank(focus.getId())){//添加
			focus.setCreateTime(new Date());
			if(StringUtils.isBlank(areaCode)){
				 areaCode = "0";
			}
			if(StringUtils.isBlank(focus.getAreaCode())){
				focus.setAreaCode(areaCode);
			}
			if(StringUtils.isNotBlank(focus.getContent())){
				focus.setContent(StringUtils.trim(focus.getContent()));
			}
			this.focusService.insert(focus);
		}else{//update
			if(StringUtils.isNotBlank(focus.getContent())){
				focus.setContent(StringUtils.trim(focus.getContent()));
			}
			this.focusService.updateByPrimaryKeySelective(focus);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(String id) throws Exception{
		Focus focus = this.focusService.selectByPrimaryKey(id);
		return JSONUtils.serialize(focus);
	}
	
	@RequestMapping("/findByAreaCode")
	@ResponseBody
	public String findByAreaCode(String areaCode, Integer location){
		
		FocusExample example = new FocusExample();
		FocusExample.Criteria criteria = example.createCriteria();
		criteria.andLocationEqualTo(location);
		
		if(location!=4 && location!=6 && location!=8 && location!=12){
			criteria.andAreaCodeEqualTo(areaCode);
		}
			
		List<Focus> list = this.focusService.selectByExample(example);
		
		Focus focus = null;
		if(list!=null && list.size()>0){
			focus = list.get(0);
		}
		
		return JSONUtils.serialize(focus);
	}
	@RequestMapping("/findByStore")	
	@ResponseBody
	public String findByStore(HttpSession session, Integer location){
		String id = getSessionParameter("id", session);
		FocusExample example = new FocusExample();
		FocusExample.Criteria criteria = example.createCriteria();
		criteria.andLocationEqualTo(location).andRestraintEqualTo(id);
		
		
		List<Focus> list = this.focusService.selectByExample(example);
		
		Focus focus = null;
		if(list!=null && list.size()>0){
			focus = list.get(0);
		}
		
		return JSONUtils.serialize(focus);
	}
}
