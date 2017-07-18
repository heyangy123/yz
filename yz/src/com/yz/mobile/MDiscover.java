package com.yz.mobile;

import com.base.dialect.PaginationSupport;
import com.base.mobile.MobException;
import com.base.mobile.MobileInfo;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.util.DateUtil;
import com.eone.common.proto.MNearby.Mvedio;
import com.eone.common.proto.MNearby.MvedioList;
import com.eone.common.proto.MUserAccount.MUser;
import com.eone.common.proto.MUserAccount.MUserList;
import com.item.dao.model.User;
import com.item.dao.model.UserExample;
import com.item.service.UserService;
import com.yz.dao.model.Vedio;
import com.yz.dao.model.VedioExample;
import com.yz.service.VedioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mobile
@Component
public class MDiscover {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VedioService vedioService;
	
	  
	/**
	@api MCreditTop MCreditTop 	//积分排名前五  (需要分页)
	@param areaCode 城市code
	@return MUserList
	*/
	@MobileMethod(methodno = "MCreditTop", isLogin = false,isPage = true)
	public MUserList.Builder CreditTop (MobileInfo mobileInfo,String areaCode,Integer page,Integer limit) throws Exception{
		if(areaCode==null){
			throw new MobException("areaCode");	
		}
		
		UserExample example = new UserExample();
		example.setOrderByClause("total_score desc");
		example.createCriteria().andCityCodeIsNotNull().andCityCodeEqualTo(Integer.valueOf(areaCode));
		PaginationSupport.byPage(page, limit,false);
		List<User> list = userService.selectByExample(example);
		MUserList.Builder ret = MUserList.newBuilder();
		if(!list.isEmpty()){
			for(int i =0;i<list.size();i++){
				MUser.Builder m = MUser.newBuilder();
				m.setName(list.get(i).getName());
				m.setImg(list.get(i).getImg());
				m.setScore(list.get(i).getTotalScore());
				ret.addUser(m);
			}
		}
		return ret;
	}
	
	/**
	@api MvedioList MvedioList 	//视频列表
	@return MvedioList
	*/
	@MobileMethod(methodno = "MvedioList", isLogin = false,isPage = true)
	public MvedioList.Builder vedioList (MobileInfo mobileInfo,Integer page ,Integer limit){
		
		VedioExample example = new VedioExample();
		example.setOrderByClause("create_time desc");
		PaginationSupport.byPage(page, limit,false);
		List<Vedio> list = vedioService.selectByExample(example);
		MvedioList.Builder ret = MvedioList.newBuilder();
		for (Vedio vedio : list) {
			Mvedio.Builder m = Mvedio.newBuilder();
			m.setImg(vedio.getImg());
			m.setTime(DateUtil.dateToStr(vedio.getCreateTime()));
			m.setTitle(vedio.getTitle());
			m.setRemark(vedio.getRemark());
			m.setId(vedio.getId());
			m.setVedioTime(vedio.getTime());
			ret.addList(m);
		}	
		
		return ret;
		
	}	
	
	/**
	@api MVedioDeatail MVedioDeatail 	//视频详情
	@param id 视频id
	@return Mvedio
	*/
	@MobileMethod(methodno = "MVedioDeatail")
	public Mvedio.Builder VedioDeatail (MobileInfo mobileInfo,String id) throws Exception{
		
		if(StringUtils.isBlank(id)){
			throw new MobException("id");	
		}
		
		Vedio vedio = vedioService.selectByPrimaryKey(id);
		
		Mvedio.Builder ret = Mvedio.newBuilder();
		
		if(vedio!=null){
			ret.setImg(vedio.getImg());
			ret.setTime(DateUtil.dateToStr(vedio.getCreateTime()));
			ret.setTitle(vedio.getTitle());
			ret.setRemark(vedio.getRemark());
			ret.setUrl(vedio.getUrl());
		}

		return ret;
		
	}	
	
}
