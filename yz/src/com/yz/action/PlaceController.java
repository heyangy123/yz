package com.yz.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.item.util.IDUtils;
import com.yz.dao.model.GamePlace;
import com.yz.dao.model.GamePlaceExample;
import com.yz.daoEx.model.GamePlaceEx;
import com.yz.service.GamePlaceService;

/**
 * 比赛地址管理
 * @author Cailin
 *
 */
@Controller
@RequestMapping("/place")
public class PlaceController extends CoreController {
	@Autowired
	private GamePlaceService gamePlaceService;
	
	@RequestMapping("list")
	@ResponseBody
	public String list(GamePlace place , Integer page,Integer rows, Model model) {
		PaginationSupport.byPage(page, rows);
		List<GamePlaceEx> list = gamePlaceService.selectList();
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}
	
	@ResponseBody
	@RequestMapping("save")
	public String save(GamePlace place) {
		if(StringUtils.isBlank(place.getId())){//添加
			place.setId(IDUtils.next());
			place.setCreateTime(new Date());
			gamePlaceService.insert(place);
		}else{//update
			gamePlaceService.updateByPrimaryKeySelective(place);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String id) {
		String[] ids =id.split(",");
		for (int i = 0; i < ids.length; i++) {
			gamePlaceService.deleteByPrimaryKey(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@ResponseBody
	@RequestMapping("/findById")
	public String findById(String id) throws Exception{
		GamePlaceEx place = gamePlaceService.selectByPlaceId(id);
		return JSONUtils.serialize(place);
	}
	
	
}
