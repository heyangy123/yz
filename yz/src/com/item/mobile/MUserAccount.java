package com.item.mobile;

import com.alibaba.fastjson.JSONObject;
import com.base.cache.CacheSupport;
import com.base.mobile.MobException;
import com.base.mobile.MobileInfo;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.oauthLogin.OauthLoginUtil;
import com.base.oauthLogin.model.OauthUserInfo;
import com.base.service.BFileService;
import com.base.support.PropertySupport;
import com.base.util.BaiduSocialUtil;
import com.base.util.ClassUtils;
import com.base.util.DateUtil;
import com.base.util.FileType;
import com.eone.common.proto.MCommon.MRet;
import com.eone.common.proto.MUserAccount.MAccount;
import com.eone.common.proto.MUserAccount.MUser;
import com.fx.service.RandomCodeService;
import com.item.dao.model.*;
import com.item.daoEx.model.UserEx;
import com.item.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executors;

/*import sun.misc.BASE64Encoder;*/

@Mobile
@Component
public class MUserAccount {
	//是否为多终端登录模式
	private static final boolean MULTI_LOGIN = true;
	
	@Autowired
	private UserService userService;
	@Autowired
	private MobileVerifyService verifyService;
	@Autowired
	private UserOauthService oauthService;
	@Autowired
	private RandomCodeService randomCodeService;
	@Autowired
	private BFileService fileService;
	@Autowired
	private UserCreditLogService creditLogService;
	
	@Autowired
	private SmsSendLogService smsSendLogService;
	
	@Autowired
	private NotifyService notifyService;

	
	/**
	 * 获取验证码
	 * @param phone 手机号
	 * @param type 1:注册,绑定手机  2:忘记密码 3:团购订单
	 * @url /mobile?methodno=MGetMobileMsg&debug=1&deviceid=test&phone=&type=
	 */
	@MobileMethod(methodno = "MGetMobileMsg", isLogin = false)
	public MRet.Builder getMobileMsg(MobileInfo mobileInfo, String phone, Integer type) throws Exception {
		// 参数校验
		if (StringUtils.isBlank(phone)) {
			throw new MobException("phone");
		}
		if (type == null) {
			throw new MobException("type");
		}

		// 查询
		int count = 0;

		UserExample example = new UserExample();
		example.createCriteria().andAccountEqualTo(phone);
		count = userService.countByExample(example);


		if (type == 1) {// 注册
			if (count > 0) {
				throw new MobException(MEnumError.PHONE_EXISTS_ERROR);
			}
		} else if(type==2){
			if (count == 0) {
				throw new MobException(MEnumError.PHONE_NOTEXISTS_ERROR);
			}
		}

		// 发送短信
		int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
		String content = PropertySupport.getProperty("sms.type1_model", "您的验证码是：{1}。请不要把验证码泄露给其他人。");
		content = content.replace("{1}", mobileCode+"");
		System.out.println(content);

		// 返回
		MRet.Builder ret = MRet.newBuilder();

		CacheSupport.put("mobileCodeCache", phone, mobileCode + "");
		ret.setCode(0);
		ret.setMsg(mobileCode+"");

		String code = smsSendLogService.sendIdentifySms(phone, mobileInfo.getDeviceid(), "", content);
		if(!code.equals("2")){
			CacheSupport.remove("mobileCodeCache", phone);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", code);
			String msg = (String)ClassUtils.invokeMethod(PropertySupport.getProperty("sms.class", "SendsmsUtil"), "getSmsError", params);
			if(msg!=null){
				throw new MobException(10101,msg);
			}else{
				throw new MobException(MEnumError.SMS_ERROR);
			}
		}
		return ret;
	}

	/**
	 * 验证验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @url /mobile?methodno=MCheckMobileMsg&debug=1&deviceid=test&phone=&code=
	 */
	@MobileMethod(methodno = "MCheckMobileMsg", isLogin = false)
	public MRet.Builder checkMobileMsg(MobileInfo mobileInfo, String phone, String code) throws Exception {
		// 参数校验
		if (StringUtils.isBlank(phone)) {
			throw new MobException("phone");
		}
		if (StringUtils.isBlank(code)) {
			throw new MobException("code");
		}

		String value = CacheSupport.get("mobileCodeCache", phone, String.class);
		// 返回
		MRet.Builder ret = MRet.newBuilder();
		if (code.equals(value)) {
			ret.setCode(0);
		} else {
			ret.setCode(1);
		}
		return ret;
	}

	/**
	 * 注册
	 * param  nickName //昵称
	 * @param phone 手机号
	 * @param code 验证码
	 * @param password 密码(需要加密)
	 * @param device //设备类型  android或ios
	 * @param cid //个推cid
	 * @param address // 所在地
	 * @param sex // 性别
	 * @url /mobile?methodno=MRegist&debug=1&deviceid=test&phone=&code=&password=&device=&recommedCode=
	 */
	@MobileMethod(methodno = "MRegist", isLogin = false)
	public MAccount.Builder regist(MobileInfo mobileInfo, String nickName,String phone, 
			String code,String address,Integer sex, String password,String recommedCode, String device,String cid) throws Exception {
		// 参数校验
		if (StringUtils.isBlank(phone)) {
			throw new MobException("phone");
		}
		if (StringUtils.isBlank(code)) {
			throw new MobException("code");
		}
		if (StringUtils.isBlank(password)) {
			throw new MobException("password");
		}
		if (StringUtils.isBlank(device)) {
			throw new MobException("device");
		}
		if (StringUtils.isBlank(address)) {
			throw new MobException("address");
		}
		if (StringUtils.isBlank(nickName)) {
			throw new MobException("nickName");
		}
	

		//验证码验证
		String value = CacheSupport.get("mobileCodeCache", phone, String.class);
		if(!code.equals(value)){
			throw new MobException(MEnumError.MOBILE_CODE_ERROR);
		}
		
		UserExample example = new UserExample();
		example.createCriteria().andAccountEqualTo(phone);
		int i = userService.countByExample(example);
		if(i>0){
			throw new MobException(MEnumError.PHONE_EXISTS_ERROR);
		}
		
		//添加数据
		Date date = new Date();
		User record = new User();
		record.setId(randomCodeService.getRandom());
		record.setAccount(phone);
		record.setCreateTime(date);
		record.setState(1);
		record.setAddress(address);
		record.setPassword(password);
		record.setName(nickName);
		record.setAssists(0);
		record.setHeight("0");
		record.setWeight("0");
		record.setGender(2);
		record.setGoal(0);
		record.setRebounds(0);
		record.setSteals(0);
		record.setBlock(0);
		record.setTotalScore(0);
		record.setImg(" ");
		record.setIsVerify(1);
		record.setRoleId("");
		record.setScore(0);

		if(sex!=null){
			record.setGender(sex);
		}
		
		//保存
		int cnt = userService.insert(record);
		if(cnt == 0){
			throw new MobException(MEnumError.CREATE_ACCOUNT_ERROR);
		}
		
		mobileInfo.setUserid(record.getId());
		String verify = verifyService.updateMobileVerify(mobileInfo, device, cid, MULTI_LOGIN);
		
		// 返回
		MAccount.Builder ret = MAccount.newBuilder();
		ret.setId(record.getId());
		ret.setVerify(verify);
		return ret;
	}

	/**
	* @api 登录
	* @param phone 手机号
	* @param password 密码(需要加密)
	* @param cid //个推cid
	* @param device 设备类型  android或ios
	* @url /mobile?methodno=MLogin&debug=1&deviceid=test&phone=&password=&device=
	*/
	@MobileMethod(methodno = "MLogin", isLogin = false)
	public MAccount.Builder login(MobileInfo mobileInfo,String phone, String password, String device, String cid) throws Exception {
		// 参数校验
		if (StringUtils.isBlank(phone)) {
			throw new MobException("phone");
		}
		if (StringUtils.isBlank(password)) {
			throw new MobException("password");
		}
		if (StringUtils.isBlank(device)) {
			throw new MobException("device");
		}

		//校验登录
		UserExample example = new UserExample();
		example.createCriteria().andAccountEqualTo(phone).andPasswordEqualTo(password);
		List<User> list = userService.selectByExample(example);
		
		if(list.size() == 0){
			throw new MobException(MEnumError.LOGIN_FAILURE_ERROR);
		}
		
		User user = list.get(0);
		
		//禁用
		if(user.getState() == 0){
			throw new MobException(MEnumError.ACCOUNT_STOP_ERROR);
		}
		
		/*if(user.getIsVerify()!=1){
			throw new MobException(MEnumError.ACCOUNT_VERIFY_LOAD);
		}*/
		
		mobileInfo.setUserid(user.getId());
		String verify = verifyService.updateMobileVerify(mobileInfo, device, cid, MULTI_LOGIN);
		
		// 返回
		MAccount.Builder ret = MAccount.newBuilder();
		ret.setId(user.getId());
		ret.setVerify(verify);
//		ret.setType(user.getType());
		return ret;
	}

	/**
	* @api 第三方登录
	* @param type 1:qq 2:sina
	* @param openid openid
	* @param accessToken accessToken
	* @param device 设备类型  android或ios
	* @param cid //个推cid
	* @url /mobile?methodno=MOauthLogin&debug=1&deviceid=test&type=&openid=&accessToken=&device=
	*/
	@MobileMethod(methodno = "MOauthLogin", isLogin = false)
	public MAccount.Builder oauthLogin(MobileInfo mobileInfo,final Integer type,final String openid,final String accessToken, String device,String cid) throws Exception {
		// 参数校验
		if (type==null) {
			throw new MobException("type");
		}
		if (StringUtils.isBlank(openid)) {
			throw new MobException("openid");
		}
		if (StringUtils.isBlank(accessToken)) {
			throw new MobException("accessToken");
		}
		if (StringUtils.isBlank(device)) {
			throw new MobException("device");
		}
		
		UserOauthExample example = new UserOauthExample();
		example.createCriteria().andTypeEqualTo(type).andOpenIdEqualTo(openid);
		List<UserOauth> list = oauthService.selectByExample(example);
		
		User user = null;
		boolean insert = false;//是否需要添加
		
		if(list.size()>0){
			UserOauth oauth = list.get(0);
			user = userService.selectByPrimaryKey(oauth.getUserId());
			if(user == null){
				oauthService.deleteByPrimaryKey(oauth.getId());
				insert = true;
			}else if(user.getState() == 0){
				throw new MobException(MEnumError.ACCOUNT_STOP_ERROR);
			}
		}else{
			insert = true;
		}
		
		//插入
		if(insert){
			//添加数据
			Date date = new Date();
			String accout = DateUtil.dateToStr(date, "yyyyMMddHHmmss");
			if(type == 1){
				accout = "qq_"+accout;
			}else if (type == 2){
				accout = "sina_"+accout;
			}else if (type == 3){
				accout = "wx_"+accout;
			}
			user = new User();
			user.setId(randomCodeService.getRandom());
			user.setCreateTime(date);
			user.setState(1);
			user.setName(accout);
			user.setGender(2);
			user.setAccount(accout);
			user.setPassword("");
			user.setIsVerify(1);
			user.setTotalScore(0);
			user.setAssists(0);
			user.setHeight("0");
			user.setWeight("0");
			user.setGoal(0);
			user.setRebounds(0);
			user.setSteals(0);
			user.setBlock(0);
			user.setRoleId("");
			user.setScore(0);
			user.setImg("");
			//保存
			int cnt = userService.insert(user);
			if(cnt == 0){
				throw new MobException(MEnumError.CREATE_ACCOUNT_ERROR);
			}
			
			final String userId = user.getId();
			
			//第三方登录
			Executors.newSingleThreadExecutor().execute(new Runnable() {
				public void run() {
					OauthUserInfo oauthUserInfo = OauthLoginUtil.getOauthUserInfo(type, accessToken, openid);
					if (oauthUserInfo!=null){
						User user = new User();
						user.setId(userId);
						user.setName(oauthUserInfo.getNickName());
						user.setGender(oauthUserInfo.getSex());
						if (oauthUserInfo.getHeadImg() != null){
							user.setImg(uploadHeadImg(oauthUserInfo.getHeadImg()));
						}
						if (StringUtils.isNotBlank(oauthUserInfo.getNickName()))
//							user.setIsInitNickName(1);
						userService.updateByPrimaryKeySelective(user);
					}else {
						
					}
				}
			});
			
			//保存第三方登录信息
			UserOauth oauth = new UserOauth();
			oauth.setAccessToken(accessToken);
			oauth.setCreateTime(new Date());
			oauth.setOpenId(openid);
			oauth.setType(type);
			oauth.setUserId(user.getId());
			cnt = oauthService.insert(oauth);
			if(cnt == 0){
				throw new MobException(MEnumError.CREATE_ACCOUNT_ERROR);
			}
		}
		
		//更新登录验证
		mobileInfo.setUserid(user.getId());
		String verify = verifyService.updateMobileVerify(mobileInfo, device, cid, MULTI_LOGIN);
		
		//返回
		MAccount.Builder ret = MAccount.newBuilder();
		ret.setId(user.getId());
		ret.setVerify(verify);
		return ret;
	}
	
	/**
	 * 第三方登录获取头像
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String uploadHeadImg(String url){
		try {
			String savePath = UUID.randomUUID().toString();
			byte[] bytes = BaiduSocialUtil.download(url);
			
			FileType type = BaiduSocialUtil.getType(Arrays.copyOf(bytes, 28));
			if (type != null)
				savePath += "."+type.name().toLowerCase();
			
			String img = fileService.uploadFile(bytes, savePath, null);
			return img;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 完善资料
	 * @param nickName 昵称
	 * @param headImg 头像ID
	 * @param sex 性别
	 * @url /mobile?methodno=MUpdateUser&debug=1&userid=1&verify=1&deviceid=test&name=&headImg=
	 */
	@MobileMethod(methodno = "MUpdateUser", isLogin = true)
	public JSONObject updateUser1(MobileInfo mobileInfo, String nickName, String headImg,String sex,String tel,String weixin,String birthday,
			Integer gender,String height,String weight,String roleId,Integer cityCode,String address,String fullAddress) throws Exception {
		//更新数据
		User record = new User();
		record.setId(mobileInfo.getUserid());
		record.setName(nickName);
		record.setImg(headImg);
		record.setTel(tel);
		record.setWeixin(weixin);
		record.setBirthday(birthday);
		record.setGender(gender);
		record.setHeight(height);
		record.setWeight(weight);
		record.setRoleId(roleId);
		record.setCityCode(cityCode);
		record.setAddress(address);
		record.setFullAddress(fullAddress);
		if(StringUtils.isNotBlank(sex))
			record.setGender(Integer.parseInt(sex));
		userService.updateByPrimaryKeySelective(record);
		
		// 返回
		User user = userService.selectByPrimaryKey(mobileInfo.getUserid());
		JSONObject j = new JSONObject();
		j.put("user", user);
		return j;
	}

	/**
	 * 忘记密码
	 * @param phone 手机号
	 * @param code 验证码
	 * @param password 密码(需要加密)
	 * @param device //设备类型  android或ios
	 * @param cid //个推cid
	 * @url /mobile?methodno=MForgetPassword&debug=1&deviceid=test&phone=&code=&password=&pushId=&device=
	 */
	@MobileMethod(methodno = "MForgetPassword", isLogin = false)
	public MAccount.Builder forgetPassword(MobileInfo mobileInfo, String phone, 
			String code, String password, String device, String cid) throws Exception {
		// 参数校验
		if (StringUtils.isBlank(phone)) {
			throw new MobException("phone");
		}
		if (StringUtils.isBlank(code)) {
			throw new MobException("code");
		}
		if (StringUtils.isBlank(password)) {
			throw new MobException("password");
		}
		if (StringUtils.isBlank(device)) {
			throw new MobException("device");
		}

		//验证码验证
		String value = CacheSupport.get("mobileCodeCache", phone, String.class);
		if(!code.equals(value)){
			throw new MobException(MEnumError.MOBILE_CODE_ERROR);
		}
		
		//修改数据
		UserExample uexample = new UserExample();
		uexample.createCriteria().andAccountEqualTo(phone);
		List<User> list = userService.selectByExample(uexample);
		
		if(list.size() == 0){
			throw new MobException(MEnumError.PHONE_NOTEXISTS_ERROR);
		}
		
		User record = list.get(0);
		record.setPassword(password);
		int cnt = userService.updateByPrimaryKey(record);
		
		if(cnt == 0){
			throw new MobException(MEnumError.UPDATE_ACCOUNT_ERROR);
		}
		
		mobileInfo.setUserid(record.getId());
		String verify = verifyService.updateMobileVerify(mobileInfo, device, cid, false);
		
		// 返回
		MAccount.Builder ret = MAccount.newBuilder();
		ret.setId(record.getId());
		ret.setVerify(verify);
		return ret;
	}

	/**
	 * 修改密码
	 * @param oldPwd 旧密码(需要加密)
	 * @param newPwd 新密码(需要加密)
	 * @url /mobile?methodno=MChangePassword&debug=1&userid=1&verify=1&deviceid=test&oldPwd=&newPwd=
	 */
	@MobileMethod(methodno = "MChangePassword", isLogin = true)
	public MRet.Builder changePassword(MobileInfo mobileInfo, String oldPwd, String newPwd) throws Exception {
		// 参数校验
		if (StringUtils.isBlank(newPwd)) {
			throw new MobException("newPwd");
		}
		if (StringUtils.isBlank(oldPwd)) {
			throw new MobException("oldPwd");
		}
		
		User user = userService.selectByPrimaryKey(mobileInfo.getUserid());
		if(!oldPwd.equals(user.getPassword())){
			throw new MobException(MEnumError.PASSWORD_FALSE_ERROR);
		}
		
		//删除其他设备的登录验证信息
		verifyService.deleteOther(mobileInfo.getUserid(),mobileInfo.getDeviceid());
		
		//更新数据
		User record = new User();
		record.setId(mobileInfo.getUserid());
		record.setPassword(newPwd);
		userService.updateByPrimaryKeySelective(record);
		
		// 返回
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		return ret;
	}


	/**
	 * 注销
	 * @url /mobile?methodno=MLogout&debug=1&userid=1&verify=1&deviceid=test&oldPwd=&newPwd=
	 */
	@MobileMethod(methodno = "MLogout", isLogin = true)
	public MRet.Builder logout(MobileInfo mobileInfo) throws Exception {
		verifyService.logout(mobileInfo);
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		return ret;
	}

	/**
	 * 获取用户信息
	 *
	 * @param
	 * @url /mobile?methodno=MGetUserInfo&debug=1&deviceid=test&appid=1&content=
	 */
	@MobileMethod(methodno = "MGetUserInfo", isLogin = false)
	public JSONObject getUserInfo(MobileInfo mobileInfo) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", mobileInfo.getUserid());

		UserEx user = userService.selectUserDetail(map);
		if (user == null)
			throw new MobException(MEnumError.USER_NOEXIST_ERROR);
		MUser.Builder ret = MUser.newBuilder();
		UserExample example = new UserExample();
		example.createCriteria().andTotalScoreGreaterThan(user.getTotalScore());
		int rank = userService.countByExample(example);

		NotifyExample notifyExample = new NotifyExample();
		notifyExample.createCriteria().andUserIdEqualTo(mobileInfo.getUserid()).andIsReadEqualTo(0);
		List<Notify> list = notifyService.selectByExample(notifyExample);
		JSONObject j = new JSONObject();
		if(list.size() > 0){
			user.setNewMsg(String.valueOf(1));
		}else{
			user.setNewMsg(String.valueOf(0));
		}
		user.setRank(rank+1);
		if(user.getPosition() == null){
			user.setPosition("");
		}
		j.put("user", user);
		
		
		return j;
	}

	/**
	* @api 绑定手机
	* @param phone 手机号
	* @param password 密码
	* @param code 短信验证码
	* @url /mobile?methodno=MBindPhone&debug=1&deviceid=test&userid=&verify=&phone=&code=
	*/
	@MobileMethod(methodno = "MBindPhone", isLogin = true)
	public MRet.Builder bindPhone(MobileInfo mobileInfo,String phone,String password,String code) throws Exception {
		// 参数校验
		if (StringUtils.isBlank(phone)) {
			throw new MobException("phone");
		}
		if (StringUtils.isBlank(code)) {
			throw new MobException("code");
		}
		
		User user = userService.selectByPrimaryKey(mobileInfo.getUserid());
		
		//验证码验证
		String value = CacheSupport.get("mobileCodeCache", phone, String.class);
		if(!code.equals(value)){
			throw new MobException(MEnumError.MOBILE_CODE_ERROR);
		}
		
		user.setAccount(phone);
		user.setPassword(password);
		userService.updateByPrimaryKeySelective(user);
		
		MRet.Builder ret = MRet.newBuilder();
		ret.setCode(0);
		return ret;
	}

	/*public static String SendSms(String Phone)throws Exception{
	    String message = URLEncoder.encode("网信通接口短信测试", "utf-8");
	    int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
        String content = new String("您的验证码是：" + mobileCode + "。请不要把验证码泄露给其他人。");
        content = URLEncoder.encode(content, "utf-8");
        System.out.println(content);
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String str = "jackal";
        String newstr="5da520440785b95df2c6016087c315ae".toUpperCase();
        
        String url = "http://112.124.112.88:9090/?Action=SendSms&UserName=jfwl&Password="+newstr+"&Mobile="+Phone+"&Message=" + content;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod( url );
 
        // 这里设置字符编码，避免乱码
        method.setRequestHeader("Content-Type", "text/html;charset=utf-8");
 
        client.executeMethod(method);
        // 打印服务器返回的状态
        System.out.println(method.getStatusLine());
 
        // 获取返回的html页面
        byte[] body = method.getResponseBody();
        // 打印返回的信息
        String result = new String(body, "utf-8");
        if(result.startsWith("0:"))
            System.out.println("发送成功！");
        else
            System.out.println("发送失败！");
        System.out.println(result);
        // 释放连接
        method.releaseConnection();
        return result;
	    
	}*/
	
	/*public static void main(String[] args) throws Exception {
	    String phone = "13401533776";
	    SendSms(phone);
    }*/
}

