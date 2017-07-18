package com.yz.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.item.util.IDUtils;
import com.yz.dao.model.Vedio;
import com.yz.dao.model.VedioExample;
import com.yz.service.VedioService;


@Controller
@RequestMapping("/video")
public class VideoController extends CoreController{
	
	@Autowired
	private VedioService videoService;
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public String list(Vedio video , Integer page,Integer rows, Model model) {
		PaginationSupport.byPage(page, rows);
		VedioExample example = new VedioExample();
		example.setOrderByClause("create_time desc");
		//List<Vedio> list = videoService.selectVideoByExample(example);
		List<Vedio> list = videoService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Vedio video) {
		if(StringUtils.isBlank(video.getId())){//添加
			video.setId(IDUtils.next());
			video.setCreateTime(new Date());
			videoService.insert(video);
		}else{
			//update
			videoService.updateByPrimaryKeySelective(video);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(String id) {
		String[] ids =id.split(",");
		for (int i = 0; i < ids.length; i++) {
			videoService.deleteByPrimaryKey(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	@ResponseBody
	public String findById(String id) throws Exception{
		Vedio video = videoService.selectByPrimaryKey(id);
		return JSONUtils.serialize(video);
	}
}
