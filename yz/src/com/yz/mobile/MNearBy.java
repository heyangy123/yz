package com.yz.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.dao.model.BFile;
import com.base.service.BFileService;
import com.fx.service.RandomCodeService;
import com.yz.daoEx.model.ImgView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.base.dialect.PaginationSupport;
import com.base.mobile.MobException;
import com.base.mobile.MobileInfo;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.util.DateUtil;
import com.eone.common.proto.MCommon.MRet;
import com.eone.common.proto.MNearby.MComment;
import com.eone.common.proto.MNearby.MCommentList;
import com.eone.common.proto.MNearby.MTalk;
import com.eone.common.proto.MNearby.MTalkList;
import com.eone.common.proto.MNearby.MText;
import com.eone.common.proto.MNearby.MUpvote;
import com.eone.common.proto.MNearby.MUpvoteList;
import com.yz.dao.model.Social;
import com.yz.dao.model.SocialReply;
import com.yz.dao.model.SocialUpvote;
import com.yz.daoEx.model.SocialEx;
import com.yz.daoEx.model.SocialReplyEx;
import com.yz.daoEx.model.SocialUpvoteEx;
import com.yz.service.SocialReplyService;
import com.yz.service.SocialService;
import com.yz.service.SocialUpvoteService;


@Mobile
@Component
public class MNearBy {
	@Autowired
	private SocialReplyService socialReplyService;
	
	@Autowired
	private SocialService socialService;
	
	@Autowired
	private SocialUpvoteService socialUpvoteService;

	@Autowired
	private BFileService fileService;

	@Autowired
	private RandomCodeService randomCodeService;
	
	/**
	@api CommentReplys CommentReplys //朋友圈 回复 和 赞 列表
	@param string id //评论id
	@return MCommentList
	*/
	@MobileMethod(methodno = "MCommentReplys", isLogin = false ,isPage = true)
	public MText.Builder CommentReplys (MobileInfo mobileInfo,Integer page,Integer limit,String id) throws Exception{
		
		if(StringUtils.isBlank(id)){
			throw new MobException("id");
		}
		
		PaginationSupport.byPage(page, limit,false);
		
		MText.Builder ret = MText.newBuilder();
		MCommentList.Builder comment = MCommentList.newBuilder();
		MUpvoteList.Builder vote = MUpvoteList.newBuilder();
		
		List<SocialReplyEx> commentList = socialReplyService.selectAppList(id);
		List<SocialUpvoteEx> voteList = socialUpvoteService.selectList(id);
		
		for (SocialUpvoteEx socialUpvoteEx : voteList) {
			MUpvote.Builder m = MUpvote.newBuilder();
			m.setName(socialUpvoteEx.getNickName());
			m.setId(socialUpvoteEx.getId());
			vote.addList(m);
		}
		
		for (SocialReplyEx socialReplyEx : commentList) {
			MComment.Builder m = MComment.newBuilder();
			m.setContent(socialReplyEx.getContent());
			m.setCritic(socialReplyEx.getCritic());
			//m.setName(socialReplyEx.getName());
			//m.setReplayName(socialReplyEx.getReplyName());
			m.setTarget(socialReplyEx.getTarget());
			m.setId(socialReplyEx.getId());
			comment.addList(m);
		}
		
		ret.setMCommentList(comment);
		ret.setMUpvoteList(vote);
		
		return ret;
	}
	
//	/**
//	@api MTalkList MTalkList //朋友圈列表
//	@param type 1全部 2个人
//	@return MTalkList
//	*/
//	@MobileMethod(methodno = "MTalkList", isLogin = false ,isPage = true)
//	public MTalkList.Builder TalkList (MobileInfo mobileInfo,Integer page,Integer limit,String type) throws Exception{
//		
//		PaginationSupport.byPage(page, limit,false);
//		MTalkList.Builder ret = MTalkList.newBuilder();
//		
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("type", type);
//		
//		List<SocialEx> list = socialService.selectAppList(map);
//		for (SocialEx socialEx : list) {
//			MTalk.Builder m = MTalk.newBuilder();
//			m.setId(socialEx.getId());
//			m.setContent(socialEx.getContent());
//			m.setImg(socialEx.getImg());
//			m.setTime(DateUtil.dateAsQQ(socialEx.getCreateTime()));
//			m.setUserName(socialEx.getNickName());
//			m.setLogo(socialEx.getLogo());
//			ret.addList(m);
//		}
//		return ret;
//	}
	
	/**
	@api MTalkList MTalkList //朋友圈列表
	@param type 1全部 2个人
	@return MTalkList
	*/
	@MobileMethod(methodno = "MSocialList", isLogin = false ,isPage = true)
	public JSONObject SocialList (MobileInfo mobileInfo,Integer page,Integer limit,String type) throws Exception{
		
		PaginationSupport.byPage(page, limit,false);
//		MSocial.Builder ret = MSocial.newBuilder();
//		MTalkList.Builder talkList = MTalkList.newBuilder();
//		MCommentLists.Builder commentLists = MCommentLists.newBuilder();
//		MUpvoteLists.Builder upvoteLists = MUpvoteLists.newBuilder();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		if(type.equals("2")){
			if(StringUtils.isBlank(mobileInfo.getUserid())){
				throw new MobException("userid");
			}
			map.put("userId", mobileInfo.getUserid());
		}
		JSONObject j = new JSONObject();
		List<Object> list1 = new ArrayList<Object>();
		List<Object> list2 = new ArrayList<Object>();
		//动态列表
		List<SocialEx> sList = socialService.selectAppList(map);
		List<SocialEx> sList2 = new ArrayList<SocialEx>();
		for (SocialEx socialEx : sList) {
			if(StringUtils.isNotBlank(socialEx.getImg())){
				String str [] = socialEx.getImg().split(",");
				List<ImgView> imgList = new ArrayList<ImgView>();
				for (String s : str) {
					BFile file = fileService.selectByPrimaryKey(s);
					ImgView imgView = new ImgView();
					imgView.setImg(s);
					String hw [] = file.getFileCreater().split("x");
					imgView.setWeight(hw[0]);
					imgView.setHeight(hw[1]);
					imgList.add(imgView);
					socialEx.setImgViewList(imgList);
				}
				
//				System.out.println(socialEx.getId());
			}
			sList2.add(socialEx);
			
		}
		/*for (SocialEx socialEx : sList) {
			MTalk.Builder m = MTalk.newBuilder();
			m.setId(socialEx.getId());
			m.setContent(socialEx.getContent());
			m.setImg(socialEx.getImg());
			m.setTime(DateUtil.dateAsQQ(socialEx.getCreateTime()));
			m.setUserName(socialEx.getNickName());
			m.setLogo(socialEx.getLogo());
			talkList.addList(m);
		}*/
		j.put("talkList", sList2);
		//评论列表
		for (SocialEx socialEx : sList) {
			List<SocialReplyEx> list = socialReplyService.selectAppList(socialEx.getId());
			/*MCommentList.Builder commentList = MCommentList.newBuilder();
			for (SocialReplyEx socialReplyEx : list) {
				MComment.Builder m = MComment.newBuilder();
				m.setContent(socialReplyEx.getContent());
				m.setCritic(socialReplyEx.getCritic());
				m.setName(socialReplyEx.getName());
				m.setReplayName(socialReplyEx.getReplyName());
				m.setTarget(socialReplyEx.getTarget());
				m.setId(socialReplyEx.getId());
				commentList.addList(m);
			}*/
			list1.add(list);
		}
		j.put("commentList", list1);
		//点赞列表 
		for (SocialEx socialEx : sList) {
			List<SocialUpvoteEx> voteList = socialUpvoteService.selectList(socialEx.getId());
			/*MUpvoteList.Builder vote = MUpvoteList.newBuilder();
			for (SocialUpvoteEx socialUpvoteEx : voteList) {
				MUpvote.Builder m = MUpvote.newBuilder();
				m.setName(socialUpvoteEx.getNickName());
				m.setId(socialUpvoteEx.getId());
				vote.addList(m);
			}*/
			list2.add(voteList);
		}
		j.put("voteList", list2);
		return j;
	}
	
	/**
	@api MAddComment MAddComment //添加朋友圈
	@param string content
	@param string img
	@return MRet
	*/
	@MobileMethod(methodno = "MAddComment", isLogin = true ,isPage = false)
	public MRet.Builder AddComment (MobileInfo mobileInfo,String content,String img) throws Exception{
		
		if(StringUtils.isBlank(content)){
			throw new MobException("content");
		}
		
		Social s = new Social();
		s.setContent(content);
		s.setImg(img);
		s.setUserId(mobileInfo.getUserid());
		s.setCreateTime(new Date());
		int i = socialService.insert(s);
		
		MRet.Builder ret = MRet.newBuilder();
		
		ret.setCode(i);
		ret.setMsg("发布成功");
		
		return ret;
		
	}
	

	/**
	@api MAddReplay MAddReplay //添加评论
	@param string id 帖子id
	@param string content
	@param string targetId 对象id
	@return MRet
	*/
	@MobileMethod(methodno = "MAddReplay", isLogin = true ,isPage = false)
	public JSONObject AddReplay (MobileInfo mobileInfo,String content,String id,String targetId) throws Exception{
		
		if(StringUtils.isBlank(content)){
			throw new MobException("content");
		}
		if(StringUtils.isBlank(id)){
			throw new MobException("id");
		}
		if(StringUtils.isBlank(targetId)){
			throw new MobException("targetId");
		}
		
		SocialReply s = new SocialReply();
		String r_id = randomCodeService.getRandom();
		s.setId(r_id);
		s.setContent(content);
		s.setCreateTime(new Date());
		s.setCritic(mobileInfo.getUserid());
		s.setTarget(targetId);
		s.setSocialId(id);
		
		int i = socialReplyService.insert(s);

		
		JSONObject j = new JSONObject();
		j.put("id", r_id);
		
		return j;
	} 
	
	/**
	@api MAddUpvote MAddUpvote //添加赞
	@param string id 帖子id
	@param string userId 用户id
	@return MRet
	*/
	@MobileMethod(methodno = "MAddUpvote", isLogin = true ,isPage = false)
	public JSONObject AddUpvote (MobileInfo mobileInfo,String id) throws Exception{

		if(StringUtils.isBlank(id)){
			throw new MobException("id");
		}
		
		SocialUpvote s = new SocialUpvote();
		String r_id = randomCodeService.getRandom();
		s.setId(r_id);
		s.setCreateTime(new Date());
		s.setSocialId(id);
		s.setUserId(mobileInfo.getUserid());
		
		int i = socialUpvoteService.insert(s);
			
		JSONObject j = new JSONObject();
		j.put("id", r_id);
		
		return j;
	}
	
	/**
	@api MEditComment MEditComment // 删除评论 和 赞
	@param id
	@para type 1评论 2赞 
	@return MRet
	*/
	@MobileMethod(methodno = "MEditComment", isLogin = true )
	public MRet.Builder EditComment (MobileInfo mobileInfo,String id,String type) throws Exception{
		
		if(StringUtils.isBlank(id)){
			throw new MobException("id");
		}
		
		if(StringUtils.isBlank(type)){
			throw new MobException("type");
		}
		
		if("1".equals(type)){ 
			SocialReply s = socialReplyService.selectByPrimaryKey(id);
			if(s!=null){
				socialReplyService.deleteByPrimaryKey(id);
			}
		}else{
			SocialUpvote s = socialUpvoteService.selectByPrimaryKey(id);
			if(s!=null){
				socialUpvoteService.deleteByPrimaryKey(id);
			}
		}
		
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		ret.setMsg("删除成功");
		
		return ret;
		
	}
}
