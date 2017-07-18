package com.yz.website;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.dao.model.Ret;
import com.base.util.JSONUtils;
import com.yz.dao.model.GamePlayerExample;
import com.yz.daoEx.model.GameEx;
import com.yz.daoEx.model.GamePlayerEx;
import com.yz.service.GamePlayerService;
import com.yz.service.GameService;


@RequestMapping("share")
@Controller
public class WebShareController{
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GamePlayerService gamePlayerService;
	
	@RequestMapping("/{id}")
    public String detail(@PathVariable("id")String id,ModelMap map){
		map.put("id", id);
    	return "share/sharePhoto";
	}
	
	
	@RequestMapping("/getContent")
	@ResponseBody
	public String getContent(String id){
		
		if(StringUtils.isBlank(id)){
			return JSONUtils.serialize(new Ret(1, "id缺少"));
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("id", id);
		
		GameEx gameEx = gameService.selectShare(map); //比赛信息
		
		map.clear();
		map.put("gameId", id);
		map.put("type", 0); //发起方
		
		GamePlayerEx organizer = gamePlayerService.selectCaptain(map); //发起
		
		map.clear();
		map.put("gameId", id);
		map.put("type", 1);
		
		GamePlayerEx accpeter = gamePlayerService.selectCaptain(map); //应战
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("game", gameEx);
		data.put("organizer", organizer);
		data.put("accpeter", accpeter);
		
		return JSONUtils.serialize(data);
	}
}
