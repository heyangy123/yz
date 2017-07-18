package com.item.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item.dao.AdminMapper;
import com.item.dao.model.Admin;
import com.item.dao.model.AdminExample;
import com.item.daoEx.AdminMapperEx;
import com.item.daoEx.model.AdminEx;
import com.mdx.mobile.commons.verfy.Md5;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminMapperEx adminMapperEx;
    
    private static final Logger logger = Logger.getLogger(AdminService.class);

    public int countByExample(AdminExample example) {
        return this.adminMapper.countByExample(example);
    }

    public Admin selectByPrimaryKey(String id) {
        return this.adminMapper.selectByPrimaryKey(id);
    }

    public List<Admin> selectByExample(AdminExample example) {
        return this.adminMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.adminMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Admin record) {
        return this.adminMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Admin record) {
        return this.adminMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(AdminExample example) {
        return this.adminMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Admin record, AdminExample example) {
        return this.adminMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Admin record, AdminExample example) {
        return this.adminMapper.updateByExample(record, example);
    }

    public int insert(Admin record) {
        return this.adminMapper.insert(record);
    }

    public int insertSelective(Admin record) {
        return this.adminMapper.insertSelective(record);
    }
    
    public List<AdminEx> selectAdmins(String groupId, String roleId,String areaCode){
    	return adminMapperEx.selectAdmins(groupId, roleId,areaCode);
    }
    
    public Admin login(String account, String password)throws Exception{
    	AdminExample example = new AdminExample();
    	example.createCriteria().andAccountEqualTo(account).andPasswordEqualTo(Md5.md5(password));
    	List<Admin> list = adminMapper.selectByExample(example);
    	if (list == null || list.size() == 0){
    		return null;
    	}else {
			return list.get(0);
		}
    }
    
    public int changePwd(String id, String password) throws Exception {
    	Admin admin = new Admin();
    	admin.setId(id);
    	admin.setPassword(Md5.md5(password));
    	return adminMapper.updateByPrimaryKeySelective(admin);
    }
    public int changePwdByAccount(String account, String password) throws Exception {
    	Admin admin = new Admin();
    	admin.setPassword(Md5.md5(password));
    	AdminExample example = new AdminExample();
    	example.createCriteria().andAccountEqualTo(account);
		return adminMapper.updateByExampleSelective(admin, example);
    }
}