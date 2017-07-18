package com.item.action;

import java.util.Date;
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
import com.item.dao.model.Notify;
import com.item.daoEx.model.NotifyEx;
import com.item.service.NotifyService;

@RequestMapping("notify")
@Controller
@LoginFilter
public class NotifyController extends CoreController{
	@Autowired
	private NotifyService notifyService;
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(Integer page,Integer rows){
		PaginationSupport.byPage(page, rows);
		Map<String,Object> map = new HashMap<String,Object>();
		List<NotifyEx> list = notifyService.selectList(map);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(Notify notify){
		if(StringUtils.isBlank(notify.getId())){//添加
			notify.setState(1);
			notify.setCreateTime(new Date());
			notifyService.insert(notify);
		}else{//update
			Notify temp = notifyService.selectByPrimaryKey(notify.getId());
			if(temp == null || (temp.getState() == 2 && StringUtils.isNotBlank(temp.getUserId()))){
				return JSONUtils.serialize(new Ret(1,"已发送的记录无法修改"));
			}
			notifyService.updateByPrimaryKeySelective(notify);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/send")
	@ResponseBody
	public String send(String id){
		Ret ret = notifyService.sendNotify(id);
		return JSONUtils.serialize(ret);
	}

	@RequestMapping("del")
	@ResponseBody
	public String del(String id){
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			notifyService.deleteEx(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(String id) throws Exception{
		Notify notify = notifyService.selectByPrimaryKey(id);
		return JSONUtils.serialize(notify);
	}
}
