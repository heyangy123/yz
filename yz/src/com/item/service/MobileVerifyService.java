package com.item.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.mobile.MobileInfo;
import com.item.dao.MobileVerifyMapper;
import com.item.dao.model.MobileVerify;
import com.item.dao.model.MobileVerifyExample;

@Service
public class MobileVerifyService {
    @Autowired
    private MobileVerifyMapper mobileVerifyMapper;

    private static final Logger logger = Logger.getLogger(MobileVerifyService.class);

    public int countByExample(MobileVerifyExample example) {
        return this.mobileVerifyMapper.countByExample(example);
    }

    public MobileVerify selectByPrimaryKey(String deviceId) {
        return this.mobileVerifyMapper.selectByPrimaryKey(deviceId);
    }

    public List<MobileVerify> selectByExample(MobileVerifyExample example) {
        return this.mobileVerifyMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String deviceId) {
        return this.mobileVerifyMapper.deleteByPrimaryKey(deviceId);
    }

    public int updateByPrimaryKeySelective(MobileVerify record) {
        return this.mobileVerifyMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MobileVerify record) {
        return this.mobileVerifyMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(MobileVerifyExample example) {
        return this.mobileVerifyMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(MobileVerify record, MobileVerifyExample example) {
        return this.mobileVerifyMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(MobileVerify record, MobileVerifyExample example) {
        return this.mobileVerifyMapper.updateByExample(record, example);
    }

    public int insert(MobileVerify record) {
        return this.mobileVerifyMapper.insert(record);
    }

    public int insertSelective(MobileVerify record) {
        return this.mobileVerifyMapper.insertSelective(record);
    }
    
    /**
     * 更新登录信息
     * @param mobileInfo 
     * @param deviceType 设备类型 ios or android
     * @param cid 个推cid
     * @param mutli 是否支持多终端登录
     * @return verify
     */
    public String updateMobileVerify(MobileInfo mobileInfo,String deviceType,String cid,boolean mutli){
    	if(!mutli){//单设备登录删除其他设备的登录信息
    		MobileVerifyExample example = new MobileVerifyExample();
    		example.createCriteria().andUserIdEqualTo(mobileInfo.getUserid()).andDeviceIdNotEqualTo(mobileInfo.getDeviceid());
			mobileVerifyMapper.deleteByExample(example);
    	}
    	
    	//更新当前设备登录信息
		MobileVerify mobileVerify = new MobileVerify();
		mobileVerify.setVerify(UUID.randomUUID().toString());
		mobileVerify.setDeviceId(mobileInfo.getDeviceid());
		mobileVerify.setUserId(mobileInfo.getUserid());
		mobileVerify.setDeviceType(deviceType);
		mobileVerify.setDeviceBadge(0);
		if(StringUtils.isNotBlank(cid))mobileVerify.setCid(cid);
		int count = mobileVerifyMapper.updateByPrimaryKeySelective(mobileVerify);
		
		if(count == 0){//未更新则新增
			mobileVerify.setCreateTime(new Date());
			mobileVerifyMapper.insert(mobileVerify);
		}
		
		return mobileVerify.getVerify();
	}
    
    /**
     * 注销
     * @param mobileInfo
     */
    public void logout(MobileInfo mobileInfo){
    	MobileVerify mobileVerify = mobileVerifyMapper.selectByPrimaryKey(mobileInfo.getDeviceid());
    	if(mobileVerify == null)return;
    	
    	mobileVerifyMapper.deleteByPrimaryKey(mobileInfo.getDeviceid());
    }

	public void deleteOther(String userid,String deviceid) {
		MobileVerifyExample example = new MobileVerifyExample();
		example.createCriteria().andUserIdEqualTo(userid).andDeviceIdNotEqualTo(deviceid);
		mobileVerifyMapper.deleteByExample(example);
	}
	
	public String getCidById(String userId){
		MobileVerifyExample example = new MobileVerifyExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<MobileVerify> list = this.mobileVerifyMapper.selectByExample(example);
		if (list.size() == 0) return null;
		return list.get(0).getCid();
	}
	
	public List<String> getCidsById(String userId){
		List<String> list = new ArrayList<String>(1);
		list.add(userId);
		return getCidByIds(list);
	}
	
	public List<String> getCidByIds(List<String> userId){
		MobileVerifyExample example = new MobileVerifyExample();
		example.createCriteria().andUserIdIn(userId);
		Set<String> set = new HashSet<String>();
		List<MobileVerify> list = this.mobileVerifyMapper.selectByExample(example);
		for (MobileVerify verify : list){
			if (StringUtils.isBlank(verify.getCid())) continue;
			set.add(verify.getCid());
		}
		return new ArrayList<String>(set);
	}
}