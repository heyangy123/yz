package com.item.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.CoreConstants;
import com.base.mobile.IMError;
import com.base.mobile.MFrameEnumError;
import com.base.pay.wx.util.MD5Util;
import com.base.web.annotation.LoginMethod;
import com.item.dao.MobileVerifyMapper;
import com.item.dao.SignMapper;
import com.item.dao.UserMapper;
import com.item.dao.model.MobileVerifyExample;
import com.item.dao.model.Sign;
import com.item.dao.model.User;
import com.item.dao.model.UserExample;
import com.item.daoEx.UserMapperEx;
import com.item.daoEx.model.UserEx;
import com.item.util.QueryParams;
import com.mdx.mobile.commons.verfy.Md5;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserMapperEx userMapperEx;
	@Autowired
	private MobileVerifyMapper verifyMapper;
	@Autowired
	private SignMapper signMapper;

	private static final Logger logger = Logger.getLogger(UserService.class);

	public int countByExample(UserExample example) {
		return this.userMapper.countByExample(example);
	}

	public User selectByPrimaryKey(String id) {
		return this.userMapper.selectByPrimaryKey(id);
	}

	public List<User> selectByExample(UserExample example) {
		return this.userMapper.selectByExample(example);
	}

	public int deleteByPrimaryKey(String id) {
		return this.userMapper.deleteByPrimaryKey(id);
	}

	
	public int updateByPrimaryKeySelective(User record) {
		return this.userMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(User record) {
		return this.userMapper.updateByPrimaryKey(record);
	}

	public int deleteByExample(UserExample example) {
		return this.userMapper.deleteByExample(example);
	}

	public int updateByExampleSelective(User record, UserExample example) {
		return this.userMapper.updateByExampleSelective(record, example);
	}

	public int updateByExample(User record, UserExample example) {
		return this.userMapper.updateByExample(record, example);
	}

	public int insert(User record) {
		return this.userMapper.insert(record);
	}

	public int insertSelective(User record) {
		return this.userMapper.insertSelective(record);
	}
	
	public List<UserEx> selectPalyer (Map<String, Object> map){
    	return this.userMapperEx.selectPalyer(map);
    }
    
    public List<UserEx> selectFriend (Map<String, Object> map){
    	return this.userMapperEx.selectFriend(map);
    }
    
    public UserEx selectUserDetail(Map<String, Object> map){
    	return this.userMapperEx.selectUserDetail(map);
    }

	/**
	 * 手机用户登录验证方法
	 * 
	 * @param appid
	 * @param userid
	 * @param verify
	 * @param deviceId
	 * @return
	 */
	public IMError mobileUserVerify(String appid, String userid, String verify,
			String deviceid) {
//		Boolean ret = CacheSupport.get("mobileVerifyCache", Md5.mD5(userid+verify+deviceid), Boolean.class);
//    	if(ret!=null)return ret;
		User user = this.userMapper.selectByPrimaryKey(userid);
		if(user!=null && user.getState() == 1 && user.getIsVerify() == 1){
			MobileVerifyExample example = new MobileVerifyExample();
			example.createCriteria().andDeviceIdEqualTo(deviceid).andUserIdEqualTo(userid).andVerifyEqualTo(verify);
			int cnt = verifyMapper.countByExample(example);
			if(cnt>0){
				//CacheSupport.put("mobileVerifyCache", Md5.mD5(userid+verify+deviceid), true);
				return null;
			}
		}
//		CacheSupport.put("mobileVerifyCache", Md5.mD5(userid+verify+deviceid), false);
    	return MFrameEnumError.LOGIN_FAIL_ERROR;
	}

	public List<UserEx> selectList(Map<String, Object> map) {
		return userMapperEx.selectList(map);
	}

    public boolean autoH5Login(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		Cookie c = null;
		String account = "";
		String pwd = "";
		for (int i = 0; cookies!=null && i < cookies.length; i++){
			c = cookies[i];
			if (c.getName().equals("account")){
				account = c.getValue();
			}else if(c.getName().equals("pwd")){
				pwd = c.getValue();
			}
		}
		if(account.length()>0 && pwd.length()>0){
			UserExample example = new UserExample();
			example.createCriteria().andAccountEqualTo(account).andPasswordEqualTo(pwd).andStateEqualTo(1);
			List<User> list = this.selectByExample(example);
			if(list.size()>0){
				User user = list.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId());
				map.put("account", user.getAccount());
				request.getSession().setAttribute(LoginMethod.PHONE.getName(), map);
				return true;
			}
		}
    	return false;
    }
    
    /**
	 * 签名认证
	 * @param paramMap
	 * @return
	 */
	public boolean authVerify(Map<String,Object> paramMap){
		String sign = (String) paramMap.get(CoreConstants.SIGN);
		if (StringUtils.isBlank(sign)) return false;
		paramMap.put("key", CoreConstants.getProperty("mobile.authKey"));
		String verifySign = createSign(new TreeMap<String, Object>(paramMap));
		Sign temp = signMapper.selectByPrimaryKey(verifySign);
		if (temp != null) return false;
		else {
			temp = new Sign();
			temp.setId(verifySign);
			signMapper.insert(temp);
		}
		//查询重复
		return sign.equals(verifySign);
	}
	
	@SuppressWarnings("rawtypes")
    public static String createSign(SortedMap<String, Object> params){
    	StringBuffer sb = new StringBuffer();
		Set es = params.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)&& !"device".equals(k)&& !"deviceid".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + params.get("key"));
		
		String sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
		
		return sign;
    }
    
    public String getViewId(String idf,String ids){
    	String temp = null;
    	if (idf.compareTo(ids) > 0){
    		temp = idf+"-"+ids;
    	}else {
			temp = ids+"-"+idf;
		}
    	return com.base.mobile.base.Md5.mD5(temp);
    }
    
    public User login(String account, String password)throws Exception{
    	UserExample example = new UserExample();
    	example.createCriteria().andAccountEqualTo(account).andPasswordEqualTo(Md5.md5(password));
    	List<User> list = userMapper.selectByExample(example);
    	if (list == null || list.size() == 0){
    		return null;
    	}else {
			return list.get(0);
		}
    }
    
    public int changePwd(String id, String password) throws Exception {
    	User admin = new User();
    	admin.setId(id);
    	admin.setPassword(Md5.md5(password));
    	return userMapper.updateByPrimaryKeySelective(admin);
    }
    
    
    public int updateScorePlus(User user){
    	return userMapperEx.updateScorePlus(user);
    }

}