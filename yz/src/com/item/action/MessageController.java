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
import com.base.support.PropertySupport;
import com.base.util.GetuiPushUtil;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;
import com.item.dao.model.Message;
import com.item.dao.model.MessageExample;
import com.item.service.MessageService;

@RequestMapping("msg")
@Controller
@LoginFilter
public class MessageController extends CoreController{

    @Autowired
    private MessageService messageService;
    
    @RequestMapping("/list")
	@ResponseBody 
    public String list(Integer page, Integer rows){
    	PaginationSupport.byPage(page, rows);
    	MessageExample example = new MessageExample();
    	
    	List<Message> list = messageService.selectByExample(example);
      	return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
    }
    
    @RequestMapping("/save")
	@ResponseBody 
    public String save(Message message){
    	if (StringUtils.isBlank(message.getId())){
    		message.setCreateTime(new Date());
    		message.setState(1);
    		messageService.insert(message);
    	}else{
    		Message temp = messageService.selectByPrimaryKey(message.getId());
			if(temp == null || temp.getState() == 2){
				return JSONUtils.serialize(new Ret(1,"已推送的记录无法修改"));
			}
			message.setState(1);
			message.setCreateTime(temp.getCreateTime());
    		messageService.updateByPrimaryKeySelective(message);
    	}
       	return JSONUtils.serialize(new Ret(0));
    }
    
    @RequestMapping("/findById")
	@ResponseBody 
    public String find(String id){
    	Message message = messageService.selectByPrimaryKey(id);
       	return JSONUtils.serialize(message);
    }
    
    @RequestMapping("/del")
	@ResponseBody 
    public String del(String id){
    	String[] ids = id.split(",");
    	for (String str : ids){
    		messageService.deleteByPrimaryKey(str);
    	}
       	return JSONUtils.serialize(new Ret(0));
    }

	
	@RequestMapping("/send")
	@ResponseBody
	public String send(String id){
		if(StringUtils.isBlank(PropertySupport.getProperty("getui.appId"))){
			return JSONUtils.serialize(new Ret(1,"系统尚未开启推送服务！"));
		}
		
		Message message = messageService.selectByPrimaryKey(id);
		if(message==null){
			return JSONUtils.serialize(new Ret(1,"消息不存在"));
		}
		
		if(message.getState() == 2){
			return JSONUtils.serialize(new Ret(1,"消息已被推送过了~"));
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("redirectType", 1);
		params.put("msg_android", message.getContent());
		String ret = "";
		if(message.getState() == 1){
			ret = GetuiPushUtil.pushMessageToApp(params, message.getContent(), null, null);
		}else if(message.getState() == 2){
			//ret = GetuiPushUtil.pushToSingle(params, message.getContent(), message.getTarget());
		}
		
		if(StringUtils.isBlank(ret)){
			JSONUtils.serialize(new Ret(1,"推送失败，请联系开发人员"));
		}
		
		if(ret.contains("result=ok")){
			message.setState(2);
			messageService.updateByPrimaryKey(message);
		}else{
			JSONUtils.serialize(new Ret(1,"推送失败,返回值："+ret));
		}
		
		return JSONUtils.serialize(new Ret(0));
	}
}
