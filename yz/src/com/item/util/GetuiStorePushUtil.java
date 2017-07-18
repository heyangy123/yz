package com.item.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.base.CoreConstants;
import com.base.util.JSONUtils;
import com.base.util.PattenUtil;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * 个推推送帮助类
 * @author LC
 *
 */
public class GetuiStorePushUtil {

	private static final Logger logger = Logger.getLogger(GetuiStorePushUtil.class);
	
	private static String appId = CoreConstants.getProperty("getui.store.appId");
	private static String appkey = CoreConstants.getProperty("getui.store.appkey");
	private static String master = CoreConstants.getProperty("getui.store.master");
	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	private static IGtPush push = null;
	
	static{
		push = new IGtPush(host, appkey, master);
		try {
			push.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 推送给所有人，推送给tag
	 * @param params 透传内容
	 * @param iosMsg ios-APNS 消息
	 * @param tags tags列表 逗号隔开
	 * @param  phoneType 手机类型 IOS/ANDROID/null(全部)
	 * @return
	 */
	public static String pushMessageToApp(Map<String,Object> params,String iosMsg,String tags,String phoneType){
		try {
			//透传模板
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(appId);
			template.setAppkey(appkey);
			template.setTransmissionType(2);
			if(params != null && !params.isEmpty()){
				template.setTransmissionContent(JSONUtils.serialize(params));
			}else{
				template.setTransmissionContent(iosMsg);
			}
			
			Map<String, Object> ios = new HashMap<String, Object>();
			iosMsg = filterMsg(params, ios, iosMsg);
			
			//ios APNS 信息
			template.setPushInfo("", 0, iosMsg, "", JSONUtils.serialize(ios),"", "", "");
			
			//推送消息体
			AppMessage message = new AppMessage();
			message.setData(template);
			message.setOffline(true);
			message.setOfflineExpireTime(1 * 1000 * 3600);
	
			//appid
			List<String> appIdList = new ArrayList<String>();
			appIdList.add(appId);
			
			//手机类型
			List<String> phoneTypeList = new ArrayList<String>();
			if(phoneType == null){
				phoneTypeList.add("IOS");
				phoneTypeList.add("ANDROID");
			}else{
				phoneTypeList.add(phoneType);
			}
			message.setAppIdList(appIdList);
			message.setPhoneTypeList(phoneTypeList);
			
			//标签
			if(tags!=null && tags.trim().length()>0){
				String[] tagArray = tags.split(",");
				message.setTagList(Arrays.asList(tagArray));
			}
			
			IPushResult ret = push.pushMessageToApp(message,"");
			logger.info(ret.getResponse().toString());
			System.out.println(ret.getResponse().toString());
			return ret.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 推送给单个人
	 * @param params 透传内容
	 * @param iosMsg ios-APNS 消息
	 * @param alias
	 * @return
	 */
	public static String pushToSingle(Map<String,Object> params,String iosMsg,String alias){
		if(StringUtils.isBlank(alias))return null;
		alias = PattenUtil.matchAlias(alias);
		try {
			//透传模板
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(appId);
			template.setAppkey(appkey);
			template.setTransmissionType(2);
			if(params != null && !params.isEmpty()){
				template.setTransmissionContent(JSONUtils.serialize(params));
			}else{
				template.setTransmissionContent(iosMsg);
			}
			
			Map<String, Object> ios = new HashMap<String, Object>();
			iosMsg = filterMsg(params, ios, iosMsg);
			
			template.setPushInfo("", 0, iosMsg, "", JSONUtils.serialize(ios),"", "", "");
			
			//推送消息体
			SingleMessage message = new SingleMessage();
			message.setData(template);
			message.setOffline(true);
			message.setOfflineExpireTime(1 * 1000 * 3600);
	
			Target target = new Target();
			target.setAppId(appId);
			target.setAlias(alias);
			
			IPushResult ret = push.pushMessageToSingle(message, target);
			logger.debug(ret.getResponse().toString()+"<<<>>>别名:"+alias);
			return ret.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 推送给用户列表
	 * @param params 透传内容
	 * @param iosMsg ios-APNS 消息
	 * @param aliasList 别名列表
	 * @return
	 */
	public static String pushToList(Map<String,Object> params,String iosMsg,List<String> aliasList){
		if(aliasList == null || aliasList.isEmpty())return null;
		try {
			//透传模板
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(appId);
			template.setAppkey(appkey);
			template.setTransmissionType(2);
			if(params != null && !params.isEmpty()){
				template.setTransmissionContent(JSONUtils.serialize(params));
			}else{
				template.setTransmissionContent(iosMsg);
			}
			
			Map<String, Object> ios = new HashMap<String, Object>();
			iosMsg = filterMsg(params, ios, iosMsg);
			
			//ios APNS 信息
			template.setPushInfo("", 0, iosMsg, "", JSONUtils.serialize(ios),"", "", "");
			
			//推送消息体
			ListMessage message = new ListMessage();
			message.setData(template);
			message.setOffline(true);
			message.setOfflineExpireTime(1 * 1000 * 3600);
	
			List<Target> targets = new ArrayList<Target>();
			for (String alias : aliasList) {
				Target target = new Target();
				target.setAlias(PattenUtil.matchAlias(alias));
				target.setAppId(appId);
				targets.add(target);
			}
			
			String taskId = push.getContentId(message, "toList_Alias_Push");
			IPushResult ret = push.pushMessageToList(taskId, targets);
			logger.debug(ret.getResponse().toString());
			return ret.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 单个cid绑定
	 * @param alias 别名
	 * @param cid	cid
	 * @return
	 */
	public static boolean bindAlias(String alias, String cid) {
		alias = PattenUtil.matchAlias(alias);
		IAliasResult bindSCid = push.bindAlias(appId, alias, cid);
		logger.debug("绑定结果：" + bindSCid.getResult() + "错误码:" + bindSCid.getErrorMsg());
		return bindSCid.getResult();
	}
	
	/**
	 * 一个别名绑定多个cid
	 * @param cids cid列表
	 * @param alias	 别名
	 * @return
	 */
	public static boolean bindAliasList(List<String> cids, String alias){
		alias = PattenUtil.matchAlias(alias);
		List<Target> Lcids = new ArrayList<Target>();
		for (String str : cids){
			Target target = new Target();
			target.setAlias(alias);
			target.setClientId(str);
			Lcids.add(target);
		}
		IAliasResult bindLCid = push.bindAlias(appId, Lcids);
		logger.debug("绑定结果：" + bindLCid.getResult() + "错误码:" + bindLCid.getErrorMsg());
		return bindLCid.getResult();
	}
	
	/**
	 * 解除别名与cid的绑定
	 * @param alias 别名
	 * @param cid	cid
	 * @return
	 */
	public static boolean unBindAlias(String alias, String cid){
		alias = PattenUtil.matchAlias(alias);
		IAliasResult AliasUnBind = push.unBindAlias(appId, alias, cid);
		logger.debug("解除绑定结果:" + AliasUnBind.getResult()+"错误码:"+AliasUnBind.getErrorMsg());
		return AliasUnBind.getResult();
	}
	
	/**
	 * 绑定别名的所有ClientID解绑
	 * @param alias 别名
	 * @return
	 */
	public static boolean unBindAliasAll(String alias){
		alias = PattenUtil.matchAlias(alias);
		IAliasResult AliasUnBindAll = push.unBindAliasAll(appId, alias);
		logger.debug("绑定结果：" + AliasUnBindAll.getResult() + "错误码:" + AliasUnBindAll.getErrorMsg());
		return AliasUnBindAll.getResult();
	}
	
	/**绑定标签tag列表
	 * @param tagList 标签列表
	 * @param cid	clientId
	 * @return
	 */
	public static String setClientTag(List<String> tagList, String cid){
		
		IQueryResult result = push.setClientTag(appId, cid, tagList);
		logger.debug(result.toString());
		return result.toString();
	}
	
	
	
	/**
	 * 绑定单个标签
	 * @param tag 标签名
	 * @param cid clientId
	 * @return
	 */
	public static String setSingleCilentTag(String tag, String cid){
		
		List<String> tagList = new ArrayList<String>();
		tagList.add(tag);
		return setClientTag(tagList, cid);
	}
	
	/**
	 * 
	 * @param params
	 * @param iosMsg
	 * @return
	 * @throws Exception
	 */
	public static String filterMsg(Map<String, Object> params, Map<String, Object> ios, String iosMsg) throws Exception{
		//ios APNS 信息
		Iterator<String> key = params.keySet().iterator();
		while(key.hasNext()){
			String str = key.next();
			if (!str.contains("_android")){
				ios.put(str, params.get(str));
			}
		}		
		
		String jsonString = ios.isEmpty()?"":JSONUtils.serialize(ios);
		Integer len = 152;
		if (jsonString.getBytes("GBK").length > len){
			logger.error("params数据太长");
			throw new Exception("params数据太长s");
		}else {
			len -= jsonString.getBytes("GBK").length;
			iosMsg = substring(iosMsg,len);
			return iosMsg;
		}
	}
	
	public static boolean isChineseChar(char c)   
            throws UnsupportedEncodingException {   
        // 如果字节数大于1，是汉字   
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了   
        return String.valueOf(c).getBytes("GBK").length > 1;   
    } 
  
    /**  
     * 按字节截取字符串  
     *   
     * @param orignal  
     *            原始字符串  
     * @param count  
     *            截取位数  
     * @return 截取后的字符串  
     * @throws UnsupportedEncodingException  
     *             使用了JAVA不支持的编码格式  
     */  
    public static String substring(String orignal, int count)   
            throws UnsupportedEncodingException {   
        // 原始字符不为null，也不是空字符串   
        if (orignal != null && !"".equals(orignal)) {   
            // 将原始字符串转换为GBK编码格式   
            orignal = new String(orignal.getBytes(), "GBK");   
            // 要截取的字节数大于0，且小于原始字符串的字节数   
            if (count > 0 && count < orignal.getBytes("GBK").length) {   
                StringBuffer buff = new StringBuffer();   
                char c;   
                for (int i = 0; i < count; i++) {   
                    c = orignal.charAt(i);   
                    buff.append(c);   
                    if (isChineseChar(c)) {   
                        // 遇到中文汉字，截取字节总数减1   
                        --count;   
                    }   
                }   
                return buff.toString();   
            }   
        }   
        return orignal;   
    }   
}
