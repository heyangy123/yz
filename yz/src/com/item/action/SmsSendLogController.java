package com.item.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.base.util.DateUtil;
import com.base.util.JSONUtils;
import com.base.util.Sendsms;
import com.item.dao.model.SmsSendLog;
import com.item.dao.model.SmsSendLogExample;
import com.item.daoEx.model.SmsSendLogEx;
import com.item.service.SmsSendLogService;

@RequestMapping("smsSendLog")
@Controller
public class SmsSendLogController extends CoreController{

    @Autowired
    private SmsSendLogService smsSendLogService;
    
    private static Map<String, String> smsError;
    
    @RequestMapping("/list")
	@ResponseBody 
    public String list(Integer page, Integer rows, String phone, String device, String ip, String code, String time) throws Exception{
    	PaginationSupport.byPage(page, rows);
    	SmsSendLogExample example = new SmsSendLogExample();
    	example.setOrderByClause("create_time desc");
    	SmsSendLogExample.Criteria criteria = example.createCriteria();
    	if(StringUtils.isNotBlank(phone)){
    		criteria.andPhoneLike(phone);
    	}
    	if (StringUtils.isNotBlank(device)){
    		criteria.andDeviceIdLike(device);
    	}
    	if (StringUtils.isNotBlank(ip)){
    		criteria.andIpLike(ip);
    	}
    	if (StringUtils.isNotBlank(code)){
    		criteria.andCodeEqualTo(code);
    	}
    	if (StringUtils.isNotBlank(time)){
    		Date date = DateUtil.strToDate(time, "yyyy-MM-dd");
    		criteria.andCreateTimeGreaterThan(date).andCreateTimeLessThan(DateUtil.add(date, 1));
    	}
    	List<SmsSendLog> list = smsSendLogService.selectByExample(example);
    	
    	if (smsError == null){
    		init();
    	}
    	
    	List<SmsSendLogEx> exs = new ArrayList<SmsSendLogEx>();
    	for (SmsSendLog log : list){
    		SmsSendLogEx ex = new SmsSendLogEx();
    		BeanUtils.copyProperties(ex, log);
    		ex.setErrorName(smsError.get(ex.getCode()));
    		exs.add(ex);
    	}
    	
      	return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), exs));
    }
    
    private void init(){
    	smsError = new HashMap<String, String>();
    	for (Sendsms sendsms : Sendsms.values()){
    		smsError.put(sendsms.getCode(),sendsms.getMsg());
    	}
    }
    
    @RequestMapping("/save")
	@ResponseBody 
    public String save(SmsSendLog smsSendLog){
    	if (StringUtils.isBlank(smsSendLog.getId())){
    		smsSendLogService.insert(smsSendLog);
    	}else{
    		smsSendLogService.updateByPrimaryKeySelective(smsSendLog);
    	}
       	return JSONUtils.serialize(new Ret(0));
    }
    
    @RequestMapping("/findById")
	@ResponseBody 
    public String find(String id){
    	SmsSendLog smsSendLog = smsSendLogService.selectByPrimaryKey(id);
       	return JSONUtils.serialize(smsSendLog);
    }
    
    @RequestMapping("/del")
	@ResponseBody 
    public String del(String id){
    	String[] ids = id.split(",");
    	for (String str : ids){
    		smsSendLogService.deleteByPrimaryKey(str);
    	}
       	return JSONUtils.serialize(new Ret(0));
    }


}
