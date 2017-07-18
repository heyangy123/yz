package com.item.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.CoreConstants;
import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dao.model.Task;
import com.base.dao.model.TaskExample;
import com.base.dialect.PaginationSupport;
import com.base.service.TaskService;
import com.base.task.QuartzManager;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;

@RequestMapping("task")
@Controller
@LoginFilter
public class TaskController extends CoreController{
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("list")
	@ResponseBody
	public String selectUserList(String name,Integer page,Integer rows) throws Exception{
		PaginationSupport.byPage(page, rows);
		TaskExample example = new TaskExample();
		example.setOrderByClause("job_id");
		List<Task> list= taskService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}
	
	
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		String[] ids =id.split(",");
		for (int i = 0; i < ids.length; i++) {
			taskService.deleteByPrimaryKey(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String saveIndexFocus(Task task){
		if(StringUtils.isBlank(task.getId())){//添加
			task.setState(0);
			taskService.insert(task);
		}else{//update
			taskService.updateByPrimaryKeySelective(task);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(String id) throws Exception{
		Task task = taskService.selectByPrimaryKey(id);
		return JSONUtils.serialize(task);
	}
	
	@RequestMapping("/checkJobId")
	@ResponseBody
	public String checkJobId(String value) throws Exception {
		TaskExample example = new TaskExample();
		example.createCriteria().andJobIdEqualTo(value);
		int cnt = taskService.countByExample(example);
		if (cnt > 0) return JSONUtils.serialize(new Ret(1));
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/reflushJob")
	@ResponseBody
	public String reflushJob() throws Exception {
		if (!CoreConstants.TASK_ENABLE.booleanValue()){
		      return JSONUtils.serialize(new Ret(1,"系统未开启调度任务,如需开启,请设置project.properties中参数:task.enable"));
		}
		QuartzManager.scheduleJob();
		return JSONUtils.serialize(new Ret(0));
	}
}
