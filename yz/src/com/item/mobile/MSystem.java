package com.item.mobile;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.base.dialect.PaginationSupport;
import com.base.mobile.MobException;
import com.base.mobile.MobileInfo;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.service.BFileService;
import com.base.util.DateUtil;
import com.base.util.StringUtil;
import com.eone.common.proto.MCommon.MAbout;
import com.eone.common.proto.MCommon.MFet;
import com.eone.common.proto.MCommon.MFile;
import com.eone.common.proto.MCommon.MFileList;
import com.eone.common.proto.MCommon.MNotify;
import com.eone.common.proto.MCommon.MNotifyList;
import com.eone.common.proto.MCommon.MRet;
import com.eone.common.proto.MCommon.MSysParams;
import com.fx.service.RandomCodeService;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.item.ConstantsCode;
import com.item.dao.model.Feedback;
import com.item.dao.model.Notify;
import com.item.dao.model.NotifyExample;
import com.item.dao.model.SinglePage;
import com.item.dao.model.SinglePageExample;
import com.item.dao.model.User;
import com.item.service.CodeService;
import com.item.service.FeedbackService;
import com.item.service.NotifyService;
import com.item.service.SinglePageService;
import com.item.service.UserCreditLogService;
import com.item.service.UserNotifyService;
import com.item.service.UserService;
import com.yz.dao.model.UserFriend;
import com.yz.dao.model.UserFriendExample;
import com.yz.service.UserFriendService;

@Mobile
@Component
public class MSystem {
	
	@Autowired
	private SinglePageService singlePageService;
	@Autowired
	private BFileService fileService;
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserNotifyService userNotifyService;
	@Autowired
	private UserCreditLogService userCreditLogService;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private RandomCodeService randomCodeService;

	/**
	 * 上传文件
	 * @param requestProto
	 * @url /mobile?methodno=MUploadFile&debug=1&deviceid=test&appid=1&userid=&verify=
	 */
	@MobileMethod(methodno = "MUploadFile", isLogin = true, reqClz = MFileList.class)
	public MRet.Builder uploadFiles(MobileInfo mobileInfo, MFileList.Builder requestProto) throws Exception {
		if (requestProto == null) throw new MobException("files");
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(1);
		List<MFile.Builder> list = requestProto.getFileBuilderList();
		
		int max = list.size() - 1;

		if (max == -1){
			throw new MobException("files");
		}
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; ; i++){
			String temp = fileService.uploadFile(list.get(i).getFile().toByteArray(), list.get(i).getFileName(), mobileInfo.getUserid());
			sb.append(temp);
			if (i == max){
				ret.setMsg(sb.toString());
				return ret;
			}
			sb.append(",");
		}

	}
	
	
	/**
	 * 用户反馈
	 * @param content 内容
	 * @url /mobile?methodno=MFeedback&debug=1&deviceid=test&appid=1&content=
	 */
	@MobileMethod(methodno = "MFeedback", isLogin = false)
	public MRet.Builder feedback(MobileInfo mobileInfo, String content,String img) throws Exception{
		// 参数校验
		if (StringUtils.isBlank(content)) {
			throw new MobException("content");
		}
		
		Feedback record = new Feedback();
		record.setCreateTime(new Date());
		record.setInfo(content);
		record.setUserId(mobileInfo.getUserid());
		record.setImg(img);
		record.setTel("110");
		feedbackService.insert(record);
		
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		return ret;
	}
	
	/**
	 * 获取系统常量
	 * @url /mobile?methodno=MSysParams&debug=1&deviceid=1
	 */
	@MobileMethod(methodno = "MSysParams", isLogin = false)
	public MSysParams.Builder sysParams(MobileInfo mobileInfo) throws Exception{
		MSysParams.Builder ret = MSysParams.newBuilder();
		ret.setParam1(codeService.getCode(ConstantsCode.WELCOME_IMG));//欢迎页
		return ret;
	}
	
	/**
	 * @api MSinglePage MSinglePage	//系统页面
	 * @param code	//关于我们 10001
	 * @return MAboutList
	*/
	@MobileMethod(methodno = "MSinglePage", isLogin = false)
	public MAbout.Builder MSinglePage(MobileInfo mobileInfo,String code) throws Exception{
		if (StringUtils.isBlank(code)) {
			throw new MobException("code");
		}
		
		MAbout.Builder ret = MAbout.newBuilder();
		
		SinglePageExample example = new SinglePageExample();
		example.createCriteria().andCodeEqualTo(code);
		List<SinglePage> list = singlePageService.selectByExample(example);
		if(!list.isEmpty()){
			SinglePage singlePage = list.get(0);
			ret.setContent(singlePage.getContent());
			ret.setRemark(singlePage.getRemark());
		}
		
		return ret;
	}
	
	/**
	 * 我的消息(需要登录,分页)
	 * @url /mobile?methodno=MNotifyList&debug=1&appid=1&userid=&verify=&deviceid=&begin=
	 */
	@MobileMethod(methodno = "MNotifyList",isLogin=true,isPage=true)
	public MNotifyList.Builder notifyList(MobileInfo mobileInfo,Integer page,Integer limit,String userid) throws Exception {
		mobileInfo.setUserid(userid);
		MNotifyList.Builder ret = MNotifyList.newBuilder();
		if(StringUtils.isNotBlank(mobileInfo.getUserid())){
			NotifyExample example = new NotifyExample();
			example.setOrderByClause("create_time desc");
			example.createCriteria().andUserIdEqualTo(mobileInfo.getUserid());
			PaginationSupport.byPage(page, limit,false);
			List<Notify> list = notifyService.selectByExample(example);
			for (Notify notify : list) {
				MNotify.Builder n = MNotify.newBuilder();
				n.setId(notify.getId());
				n.setTitle(StringUtil.emptyIfBlank(notify.getTitle()));
				n.setContent(notify.getContent());
				n.setIsRead(notify.getIsRead());
				n.setCreateTime(DateUtil.getLnow(notify.getCreateTime()));
				n.setRedirectType(notify.getRedirectType());
				n.setRedirectContent(StringUtil.emptyIfBlank(notify.getRedirectContent()));
				n.setFriendId(notify.getFriendId());
				n.setState(notify.getState());
				ret.addNotify(n);
			}
		}
		//String userId = mobileInfo.getUserid();
		//notifyService.updateIsRead(userId);
		return ret;
	}

	/**
	 * 删除消息
	 * @param
	 * @param  //类型 1:系统通知
	 * @url /mobile?methodno=MDelNotify&debug=1&userid=&verify=&deviceid=&id=
	 */
	@MobileMethod(methodno = "MDelNotify",isLogin=true)
	public MRet.Builder delNotify(MobileInfo mobileInfo,String notifyId) throws Exception {
		if (notifyId == null) {
			throw new MobException("notifyId");
		}
		NotifyExample example = new NotifyExample();
		NotifyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(mobileInfo.getUserid());
		if(StringUtils.isNotBlank(notifyId)){
			criteria.andIdEqualTo(notifyId);
		}
		notifyService.deleteByExample(example);
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		return ret;
	}
	
	/**
	 * 消息设为已读
	 * @param id
	 * @url /mobile?methodno=MReadNotify&debug=1&userid=&verify=&deviceid=1&id=
	 */
	@MobileMethod(methodno = "MReadNotify",isLogin=true)
	public MRet.Builder readNotify(MobileInfo mobileInfo,String notifyId) throws Exception {
		if (StringUtils.isBlank(notifyId)) {
			throw new MobException("notifyId");
		}
		Notify record = notifyService.selectByPrimaryKey(notifyId);
		record.setIsRead(1);
		notifyService.updateByPrimaryKeySelective(record);
		
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		return ret;
	}

	/**
	 * 消息详情
	 * @param id
	 * @url /mobile?methodno=MNotify&debug=1&userid=&verify=&deviceid=1&id=
	 */
	@MobileMethod(methodno = "MNotify",isLogin=true)
	public MNotify.Builder notify(MobileInfo mobileInfo,String notifyId) throws Exception {
		if (StringUtils.isBlank(notifyId)) {
			throw new MobException("notifyId");
		}
		Notify notify = notifyService.selectByPrimaryKey(notifyId);
		MNotify.Builder ret = MNotify.newBuilder();
		if(notify!=null){
				ret.setId(notify.getId());
				ret.setTitle(StringUtil.emptyIfBlank(notify.getTitle()));
				ret.setContent(StringUtil.emptyIfBlank(notify.getContent()));
				ret.setIsRead(notify.getIsRead());
				ret.setCreateTime(DateUtil.asHtml(notify.getCreateTime()));
				ret.setRedirectType(notify.getRedirectType());
				ret.setRedirectContent("");
				ret.setState(notify.getState());

				if(notify.getRedirectType().equals("0")){
					notifyService.updateIsRead(notifyId);
				}else{
					notify.setIsRead(1);
					notifyService.updateByPrimaryKey(notify);
				}
		}
		return ret;
	}
	
	/**
	 * 请求加好友
	 */
	@MobileMethod(methodno = "MAddFriend",isLogin=true)
	public MFet.Builder addFriend(MobileInfo mobileInfo,String friendId) throws Exception{
		MFet.Builder fet = MFet.newBuilder();
		if(StringUtils.isBlank(friendId)){
			throw new MobException("friendId");
		}
		User us = userService.selectByPrimaryKey(friendId);
		if(us == null){
			fet.setCode(1);
			fet.setMsg("friendId有误");
			return fet;
		}
		
		User u = userService.selectByPrimaryKey(mobileInfo.getUserid());
		UserFriendExample example = new UserFriendExample();
		example.createCriteria().andUserIdEqualTo(mobileInfo.getUserid()).andFriendEqualTo(friendId);
		List<UserFriend> list = userFriendService.selectByExample(example);
		if(list.size() > 0){
			fet.setCode(-1);
			fet.setMsg("你们已经是好友了");
			return fet;
		}
		
		Notify notify = new Notify();
		notify.setContent("'" + u.getName()+"'请求添加您为好友");
		notify.setCreateTime(new Date()); 
		notify.setIsRead(0);
		notify.setUserId(friendId);
		notify.setTitle("添加好友请求");
		notify.setRedirectType(0);
		notify.setRedirectContent("");
		notify.setState(0);
		notify.setFriendId(mobileInfo.getUserid());
		notifyService.insert(notify);
		fet.setCode(0);
		fet.setFriendId(mobileInfo.getUserid());
		return fet;
	}
	
	/**
	 * 同意/拒绝添加好友
	 */
	@MobileMethod(methodno = "MAcceptFriend",isLogin=true)
	public MRet.Builder acceptFridend(MobileInfo mobileInfo,String friendId,String state,String notifyId) throws Exception{
		MRet.Builder ret = MRet.newBuilder();
		if(StringUtils.isBlank(friendId)){
			throw new MobException("friendId");
		}
		if(StringUtils.isBlank(state)){
			throw new MobException("state");
		}
		if(StringUtils.isBlank(notifyId)){
			throw new MobException("notifyId");
		}
		User us = userService.selectByPrimaryKey(friendId);
		if(us == null){
			ret.setCode(1);
			ret.setMsg("friendId有误");
			return ret;
		}
		if(state.equals("1")){
			UserFriendExample example = new UserFriendExample();
			example.createCriteria().andUserIdEqualTo(mobileInfo.getUserid()).andFriendEqualTo(friendId);
			List<UserFriend> list = userFriendService.selectByExample(example);
			if(list.size() > 0){
				ret.setCode(-1);
				ret.setMsg("你们已经是好友了");
				return ret;
			}
			UserFriend userFriend = new UserFriend();
			userFriend.setId(randomCodeService.getRandom());
			userFriend.setCreateTime(new Date());
			userFriend.setFriend(friendId);
			userFriend.setUserId(mobileInfo.getUserid());
			userFriendService.insert(userFriend);
			UserFriend uf = new UserFriend();
			uf = userFriend;
			uf.setId(randomCodeService.getRandom());
			uf.setUserId(friendId);
			uf.setFriend(mobileInfo.getUserid());
			userFriendService.insert(uf);
			Notify notify = notifyService.selectByPrimaryKey(notifyId);
			notify.setState(1);
			notifyService.updateByPrimaryKey(notify);
			ret.setCode(0);
		}else if(state.equals("2")){
			Notify notify = notifyService.selectByPrimaryKey(notifyId);
			notify.setState(2);
			notifyService.updateByPrimaryKey(notify);
			ret.setCode(0);
		}else{
			ret.setCode(1);
			ret.setMsg("state为1或2");
		}
		return ret;
	}
	
	/**
	 * 删除好友
	 */
	
	@MobileMethod(methodno = "MDelFriend",isLogin=true)
	public MRet.Builder delFriend(MobileInfo mobileInfo,String friendId) throws Exception{
		if(StringUtils.isBlank(friendId)){
			throw new MobException("friendId");
		}
		MRet.Builder ret = MRet.newBuilder();
		UserFriendExample example = new UserFriendExample();
		example.createCriteria().andUserIdEqualTo(mobileInfo.getUserid()).andFriendEqualTo(friendId);
		int x = userFriendService.deleteByExample(example);
		if(x == 1){
			example.clear();
			example.createCriteria().andUserIdEqualTo(friendId).andFriendEqualTo(mobileInfo.getUserid());
			int y = userFriendService.deleteByExample(example);
			if(y  == 1){
				ret.setCode(0);
			}
		}
		return ret;
	}
}
