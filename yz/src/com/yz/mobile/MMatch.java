package com.yz.mobile;

import com.alibaba.fastjson.JSONObject;
import com.base.dialect.PaginationSupport;
import com.base.mobile.MobException;
import com.base.mobile.MobileInfo;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.util.DateUtil;
import com.eone.common.proto.MCommon.MArea;
import com.eone.common.proto.MCommon.MAreaList;
import com.eone.common.proto.MCommon.MRet;
import com.eone.common.proto.MMatch.*;
import com.fx.service.RandomCodeService;
import com.item.dao.model.*;
import com.item.daoEx.model.UserEx;
import com.item.service.AreasService;
import com.item.service.CitiesService;
import com.item.service.ProvincesService;
import com.item.service.UserService;
import com.yz.dao.model.*;
import com.yz.daoEx.model.GameEx;
import com.yz.service.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Mobile
@Component
public class MMatch {
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private ProvincesService provincesService;
	
	@Autowired
	private CitiesService citiesService;
	
	@Autowired
	private AreasService areasService;
	
	@Autowired
	private GameTypeService gameTypeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RandomCodeService randomCodeService;
	
	@Autowired
	private GamePlayerService gamePlayerService;
	
	@Autowired
	private GameDetailService gameDetailService;

	@Autowired
	private GamePlaceService gamePlaceService;
	
	@Autowired
	private UserRoleService userRoleService;

	/**
	 @api MGamePlaceList MGamePlaceList 		//比赛地址列表
	 @param required areaCode; //  地区
	 @return MGameAddressList
	 */

	@MobileMethod(methodno = "MGamePlaceList", isLogin = false,isPage = true)
	public MGameAddressList.Builder MGamePlaceList(MobileInfo mobileInfo,String areaCode,Integer page, Integer limit) throws Exception{
		if(StringUtils.isBlank(areaCode)){
			throw new MobException("areaCode");
		}
		GamePlaceExample gamePlaceExample = new GamePlaceExample();
		gamePlaceExample.createCriteria().andCityCodeEqualTo(Integer.parseInt(areaCode));
		PaginationSupport.byPage(page, limit,false);
		List<GamePlace> list = gamePlaceService.selectByExample(gamePlaceExample);
		MGameAddressList.Builder ret = MGameAddressList.newBuilder();
		for (GamePlace gamePlace : list) {
			MGameAddress.Builder m = MGameAddress.newBuilder();
			m.setName(gamePlace.getPlace());
			m.setId(gamePlace.getId());
			m.setAddress(gamePlace.getAddress());
			ret.addGameAddress(m);
		}

		return ret;
	}
	
	/**
	@api MBannerList MBannerList 		//轮播图列表
	@param required type; //  轮播图类型（1：约战  2:发现 3:篮球圈  4:商城）
	@return MBannerList
	*/
	
	@MobileMethod(methodno = "MBannerList", isLogin = false)
	public MBannerList.Builder BannerList (MobileInfo mobileInfo,Integer type) throws Exception{
		if(type==null) throw new MobException("type");
		BannerExample example = new BannerExample();
		example.setOrderByClause("rank desc");
		example.createCriteria().andTypeEqualTo(type).andStateEqualTo(1);
		List<Banner> list = bannerService.selectByExample(example);
		MBannerList.Builder ret = MBannerList.newBuilder();
		for (Banner banner : list) {
			MBanner.Builder m = MBanner.newBuilder();
			m.setImg(banner.getImg());
			m.setJumpUrl(banner.getJumpUrl());
			m.setTitle(banner.getTitle());
			m.setType(type);
			ret.addBanner(m);
		}
		return ret;
	}
	
	/**
	@api MUserRoleList MUserRoleList 		// 球员位置列表
	@return MUserRoleList
	*/
	@MobileMethod(methodno = "MUserRoleList", isLogin = false)
	public JSONObject MUserRoleList (MobileInfo mobileInfo) throws Exception{
		UserRoleExample example = new UserRoleExample();
		example.createCriteria().andStateEqualTo(1);
		List<UserRole> list = userRoleService.selectByExample(example);
		JSONObject j = new JSONObject();
		j.put("list", list);
		return j;
		
	}
	
	/**
	@api MGameList MGameList //对战列表
	@param areaCode 城市 id
	@param state 比赛状态 0:待审核 1:待应战 2:约战中 3:已近约战
	@param type 比赛类型 1:1v1 3:3v3 4:4v4 5:5v5
	@param time 按照时间筛选 1:一周内 2:三周内 3:一个月内 4:三个月内
	@param name //比赛主题 比赛地点 比赛场地
	@return MGameList
	*/
	@MobileMethod(methodno = "MGameList", isLogin = false, isPage = true)
	public MGameList.Builder GameList (MobileInfo mobileInfo,Integer page,Integer limit,String areaCode,String state,String type,String time,String name) throws Exception{

		if(StringUtils.isBlank(areaCode)){
			throw new MobException("areaCode");	
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("areaCode", areaCode);
		
		if(StringUtils.isNotBlank(state)){
			map.put("state", state);
		}
		
		if(StringUtils.isNotBlank(type)){
			map.put("type", type);
		}
		
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateNow = new Date();
		Date next = DateUtil.getNextDay(dateNow);
		Calendar cl = Calendar.getInstance();
		cl.setTime(next);
		String beginTime = "";
		String endTime = sdf.format(next);
		if(StringUtils.isNotBlank(time)){
			if(time.equals("1")){
				cl.add(Calendar.WEEK_OF_YEAR, -1);	//一周内
			}else if(time.equals("2")){
				cl.add(Calendar.WEEK_OF_YEAR, -3);	//三周内
			}else if(time.equals("3")){
				cl.add(Calendar.MONTH, -1);			//一个月内
			}else if(time.equals("4")){
				cl.add(Calendar.MONTH, -3);			//三个月内
			}
			Date dateFrom = cl.getTime();
			beginTime = sdf.format(dateFrom);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			//System.out.println(sdf.format(dateFrom)+"到"+sdf.format(next));
		}
		if(StringUtils.isNotBlank(name)){
			map.put("name", name);
		}
		
		PaginationSupport.byPage(page, limit,false);
		List<GameEx> list = gameService.selectAppList(map);
		MGameList.Builder ret = MGameList.newBuilder();
		for (GameEx gameEx : list) {
			MGame.Builder m = MGame.newBuilder();
			m.setAddress(gameEx.getAddress());
			m.setCreateTime(DateUtil.dateToStr(gameEx.getPlayTime()));;
			m.setName(gameEx.getTheme());
			m.setOrganizer(gameEx.getNickName());
			m.setImg(gameEx.getImg());
			m.setId(gameEx.getId());
			m.setState(gameEx.getState());
			ret.addGame(m);
		}
		return ret;
	}
	
	/**
	@api MUserStartGame MUserStartGame //我发起的约战
	@param type // 0:约战方 1:应战方
	@return MGameList
	*/
	@MobileMethod(methodno = "MUsertGame", isLogin = true, isPage = true)
	public MGameList.Builder MUsertGame (MobileInfo mobileInfo,Integer page,Integer limit,String type) throws Exception{
		if(StringUtils.isBlank(type)){
			throw new MobException("type");	
		}
		Map<String,Object> map = new HashMap<String,Object>();
		PaginationSupport.byPage(page, limit,false);
		map.put("userId", mobileInfo.getUserid());
		map.put("type", type);
		List<GameEx> list = gameService.selectAppMyGameList(map);
		MGameList.Builder ret = MGameList.newBuilder();
		for (GameEx gameEx : list) {
			MGame.Builder m = MGame.newBuilder();
			m.setAddress(gameEx.getAddress());
			m.setCreateTime(DateUtil.dateToStr(gameEx.getPlayTime()));;
			m.setName(gameEx.getTheme());
			m.setOrganizer(gameEx.getNickName());
			m.setImg(gameEx.getImg());
			m.setId(gameEx.getId());
			ret.addGame(m);
		}
		return ret;
	}
	
	
	/**
	@api MCityList MCityList //城市列表
	@param type  1 省   2市  3区    默认不传   type和code 为 省
	@param code  代码
	@return MAreaList
	*/
	@MobileMethod(methodno = "MCityList", isLogin = false)
	public MAreaList.Builder CityList (MobileInfo mobileInfo,String code,String type) throws Exception{
		
		MAreaList.Builder ret = MAreaList.newBuilder();
		
		if(StringUtils.isBlank(code)&&StringUtils.isBlank(type)){
			ProvincesExample example = new ProvincesExample();
			List<Provinces> list = provincesService.selectByExample(example);
			for (Provinces provinces : list) {
				MArea.Builder m = MArea.newBuilder();
				m.setCode(provinces.getProvinceCode());
				m.setName(provinces.getProvince());
				ret.addList(m);
			}
		}
		
		if("1".equals(type)){
			ProvincesExample example = new ProvincesExample();
			List<Provinces> list = provincesService.selectByExample(example);
			for (Provinces provinces : list) {
				MArea.Builder m = MArea.newBuilder();
				m.setCode(provinces.getProvinceCode());
				m.setName(provinces.getProvince());
				ret.addList(m);
			}
		}
		
		if("2".equals(type)){
			CitiesExample example = new CitiesExample();
			example.createCriteria().andProvinceCodeEqualTo(code);
			List<Cities> list = citiesService.selectByExample(example);
			for (Cities cities : list) {
				MArea.Builder m = MArea.newBuilder();
				m.setCode(cities.getCityCode());
				m.setName(cities.getCity());
				ret.addList(m);
			}
		}
		
		if("3".equals(type)){
			AreasExample example = new AreasExample();
			example.createCriteria().andCityCodeEqualTo(code);
			List<Areas> list = areasService.selectByExample(example);
			for (Areas areas : list) {
				MArea.Builder m = MArea.newBuilder();
				m.setCode(areas.getAreaCode());
				m.setName(areas.getArea());
				ret.addList(m);
			}
		}
		
		return ret;
	}
	
	
	
	/**
	@api MGameDetail MGameDetail //对战详情      (需要登录)
	@param required string id //比赛id
	@return MGame
	*/

	@MobileMethod(methodno = "MGameDetail", isLogin = true)
	public MGameDetail.Builder GameDetail (MobileInfo mobileInfo,String gameId) throws Exception{
		if(StringUtils.isBlank(gameId)){
			throw new MobException("gameId");	
		}
		String organizerImg = "";
		String acceptImg = "";
		String organizerId = "";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("gameId", gameId);
		GameEx game = gameService.selectGameDetail(map);
		List<UserEx> gamePlayer = userService.selectPalyer(map);
		
		MMemberList.Builder mRet = MMemberList.newBuilder();
		
		for (UserEx userEx : gamePlayer) {
			if(userEx.getType() == 0 && userEx.getIsCaptain() == 1){
				organizerImg = userEx.getImg();
				organizerId = userEx.getId();
			}
			if(userEx.getType() == 1 && userEx.getIsCaptain() == 1){
				acceptImg = userEx.getImg();
			}
			MMember.Builder player = MMember.newBuilder();
			player.setId(userEx.getId());
			player.setImg(userEx.getImg());
			player.setName(userEx.getName());
			player.setType(userEx.getType()); //参赛人员类型
			mRet.addMember(player);
		}
		
		MGameDetail.Builder ret = MGameDetail.newBuilder();
		
		if(game!=null){
			ret.setList(mRet);
			ret.setCreateTime(DateUtil.dateToStr(game.getCreateTime(), "YYYY年-MM月-dd日 HH:mm"));
			ret.setAddress(game.getAddress());
			ret.setPlace(game.getPlace());
			ret.setType(game.getName());
			ret.setNumber(game.getNumber());
			ret.setThem(game.getTheme());
			ret.setRemark(game.getRemark());
			ret.setAmount(game.getAmount());
			ret.setId(gameId);
			ret.setOrganizerImg(organizerImg);
			ret.setAcceptImg(acceptImg);
			ret.setOrganizerId(organizerId);
		}
		
		return ret;
	}
	
	
	
	/**
	@api MGameTypeList MGameTypeList //比赛类型列表  
	@return MGameTypeList
	*/
	@MobileMethod(methodno = "MGameTypeList", isLogin = false)
	public MGameTypeList.Builder GameTypeList (MobileInfo mobileInfo) throws Exception{
		GameTypeExample example = new GameTypeExample();
		example.setOrderByClause("rank desc");
		example.createCriteria().andStateEqualTo(1);
		List<GameType> list = gameTypeService.selectByExample(example);
		MGameTypeList.Builder ret = MGameTypeList.newBuilder();
		for (GameType gameType : list) {
			MGameType.Builder m = MGameType.newBuilder();
			m.setId(gameType.getId());
			m.setName(gameType.getName());
			m.setNum(gameType.getNumber());
			m.setAmount(gameType.getAmount().toString());
			ret.addGameType(m);
		}
		return ret;
	}
	
	/**
	@api MFriendList MFriendList // 好友列表
	@return MFriendList 
	*/
	@MobileMethod(methodno = "MFriendList", isLogin = true,isPage = true)
	public MFriendList.Builder FriendList (MobileInfo mobileInfo,Integer page,Integer limit) throws Exception{
		
		PaginationSupport.byPage(page, limit,false);
		String userId = mobileInfo.getUserid();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<UserEx> list = userService.selectFriend(map);
		
		MFriendList.Builder ret = MFriendList.newBuilder();
		
		for (UserEx userEx : list) {
			MFriend.Builder m = MFriend.newBuilder();
			m.setId(userEx.getId());
			m.setImg(userEx.getImg());
			m.setSex(userEx.getGender());
			m.setName(userEx.getName());
			ret.addList(m);
		}
		
		return ret;
	}

	
	/**
	@api MSearchUser MSearchUser  // 搜索好友
	@param required string word; //名字或者id 必须传一个
	@return MFriend 
	*/
	@MobileMethod(methodno = "MSearchUser", isLogin = false)
	public MFriendList.Builder SearchUser (MobileInfo mobileInfo,String word) throws Exception{
		
		if(StringUtils.isBlank(word)) throw new MobException("word");
		
		UserExample example = new UserExample();
		example.createCriteria().andNameLike("%"+word+"%");
		List<User> list = userService.selectByExample(example);
		if(list.isEmpty()){
			example.clear();
			example.createCriteria().andIdEqualTo(word);
			list = userService.selectByExample(example);
		}
		
		MFriendList.Builder ret = MFriendList.newBuilder();
		MFriend.Builder f = MFriend.newBuilder();
		if(!list.isEmpty()){
			for(User u:list){
				f.setId(u.getId());
				f.setImg(u.getImg());
				f.setSex(u.getGender());
				f.setName(u.getName());
				ret.addList(f);
			}
			
		}
		return ret;
	}

	/**
	@api MAddGame MAddGame  //发布约战
	@param userId   组织者id
	@param playerId   参赛人员 逗号拼接  
	@param tel   电话
	@param theme  主题
	@param typeId   比赛类型id  需要自行判断是否
	@param time   比赛时间
	@param placeId   比赛场地
	@param remark   比赛介绍
	@return MRet 
	*/
	@MobileMethod(methodno = "MAddGame", isLogin = true)
	public MRet.Builder MAddGame (MobileInfo mobileInfo,String playerId,String tel,String theme,String typeId,
			String time,String placeId,String remark,String amount) throws Exception{
		
		if(StringUtils.isBlank(tel)){
			throw new MobException("tel");
		}
		if(StringUtils.isBlank(theme)){
			throw new MobException("theme");
		}
		if(StringUtils.isBlank(typeId)){
			throw new MobException("typeId");
		}
		if(StringUtils.isBlank(placeId)){
			throw new MobException("placeId");
		}
		if(StringUtils.isBlank(time)){
			throw new MobException("time");
		}
		if(StringUtils.isBlank(remark)){
			throw new MobException("remark");
		}
		if(amount == null){
			throw new MobException("amount");
		}
		
		String gameId = randomCodeService.getRandom();
		
		Game game = new Game();
		game.setId(gameId);
		game.setUserId(mobileInfo.getUserid());
		game.setTel(tel);
		game.setTheme(theme);
		game.setGameTypeId(typeId);
		game.setPlayTime(DateUtil.strToDate(time+":00"));
		game.setGamePlaceId(placeId);
		game.setRemark(remark);
		game.setState(0);
		
		gameService.insert(game);
		
		
	
//		int len = ids.length; //总共几个人
		if(StringUtils.isNotBlank(playerId)){
			String ids [] = playerId.split(",");
			
			for (String userId : ids) {
				GamePlayer player = new GamePlayer();
				player.setUserId(userId);
				player.setGameId(gameId);
				player.setIsCaptain(0);
				player.setType(0); //约战方
				player.setCreateTime(new Date());
				gamePlayerService.insert(player);
			}
		}
		
		GamePlayer player = new GamePlayer(); //队长
		player.setUserId(mobileInfo.getUserid());
		player.setGameId(gameId);
		player.setIsCaptain(1); // 队长为1
		player.setType(0); 
		player.setCreateTime(new Date());
		
		gamePlayerService.insert(player);
		
		GameDetail gameDetail = new GameDetail();
		gameDetail.setAmount(new BigDecimal(amount));
		gameDetail.setType(0); //约战方
		gameDetail.setUserId(mobileInfo.getUserid());
		gameDetail.setGameId(gameId);
		gameDetail.setPayState(0);
		gameDetail.setCreateTime(new Date());
		gameDetailService.insert(gameDetail);
		
		//支付
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		ret.setMsg(gameDetail.getId());
		return ret;
	}
	
	/**
	@api MAcceptGame MAcceptGame // 应战
	@param string gameId  //比赛id
	@param string playerId // 参赛人员 逗号拼接  
	@return MRet 
	*/
	@MobileMethod(methodno = "MAcceptGame", isLogin = true)
	public synchronized MRet.Builder AcceptGame (MobileInfo mobileInfo,String gameId,String playerId,String amount) throws Exception{
		if(StringUtils.isBlank(gameId)){
			throw new MobException("gameId");
		}
		
		if(StringUtils.isBlank(amount)){
			throw new MobException("amount");
		}
	
		
		//删除之前可能没给钱就插入的数据
		GamePlayerExample gamePlayerExample = new GamePlayerExample();
		gamePlayerExample.createCriteria().andGameIdEqualTo(gameId).andTypeEqualTo(1);
		gamePlayerService.deleteByExample(gamePlayerExample);

		GameDetailExample gameDetailExample = new GameDetailExample();
		gameDetailExample.createCriteria().andGameIdEqualTo(gameId).andTypeEqualTo(1);
		gameDetailService.deleteByExample(gameDetailExample);
		
		
		if(StringUtils.isNotBlank(playerId)){
			String ids [] = playerId.split(",");
			
			for (String userId : ids) {
				GamePlayer player = new GamePlayer();
				player.setUserId(userId);
				player.setGameId(gameId);
				player.setIsCaptain(0);
				player.setType(1); //应战方
				player.setCreateTime(new Date());
				gamePlayerService.insert(player);
			}
		}
		
		GamePlayer player = new GamePlayer(); //队长
		player.setUserId(mobileInfo.getUserid());
		player.setGameId(gameId);
		player.setIsCaptain(1); // 队长为1
		player.setType(1);  //应战方
		player.setCreateTime(new Date());
		
		gamePlayerService.insert(player);
		
		
		GameDetail gameDetail = new GameDetail();
		gameDetail.setAmount(new BigDecimal(amount));
		gameDetail.setType(1); //应战方
		gameDetail.setUserId(mobileInfo.getUserid());
		gameDetail.setGameId(gameId);
		gameDetail.setPayState(0);
		gameDetail.setCreateTime(new Date());
		gameDetailService.insert(gameDetail);
		
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		ret.setMsg(gameDetail.getId());
		
		/*//支付
		Game game = gameService.selectByPrimaryKey(gameId);
		game.setState(3);
		gameService.updateByPrimaryKey(game);*/
		return ret;
		
	}
	

}
