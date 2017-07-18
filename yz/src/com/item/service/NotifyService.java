package com.item.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.model.Ret;
import com.item.dao.NotifyMapper;
import com.item.dao.UserMapper;
import com.item.dao.UserNotifyMapper;
import com.item.dao.model.Notify;
import com.item.dao.model.NotifyExample;
import com.item.dao.model.User;
import com.item.dao.model.UserExample;
import com.item.dao.model.UserNotify;
import com.item.dao.model.UserNotifyExample;
import com.item.daoEx.NotifyMapperEx;
import com.item.daoEx.model.NotifyEx;

@Service
public class NotifyService {
    @Autowired
    private NotifyMapper notifyMapper;
    @Autowired
    private NotifyMapperEx notifyMapperEx;
    @Autowired
    private UserNotifyMapper userNotifyMapper;
    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = Logger.getLogger(NotifyService.class);

    public int countByExample(NotifyExample example) {
        return this.notifyMapper.countByExample(example);
    }

    public Notify selectByPrimaryKey(String id) {
        return this.notifyMapper.selectByPrimaryKey(id);
    }

    public List<Notify> selectByExample(NotifyExample example) {
        return this.notifyMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.notifyMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Notify record) {
        return this.notifyMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Notify record) {
        return this.notifyMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(NotifyExample example) {
        return this.notifyMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Notify record, NotifyExample example) {
        return this.notifyMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Notify record, NotifyExample example) {
        return this.notifyMapper.updateByExample(record, example);
    }

    public int insert(Notify record) {
        return this.notifyMapper.insert(record);
    }

    public int insertSelective(Notify record) {
        return this.notifyMapper.insertSelective(record);
    }

	public List<NotifyEx> selectList(Map<String, Object> map) {
		return notifyMapperEx.selectList(map);
	}

	public void deleteEx(String id) {
		UserNotifyExample example = new UserNotifyExample();
		example.createCriteria().andNidEqualTo(id);
		userNotifyMapper.deleteByExample(example);
		notifyMapper.deleteByPrimaryKey(id);
	}

	public Ret sendNotify(String id) {
		Notify notify = notifyMapper.selectByPrimaryKey(id);
		if(notify!=null){
			UserNotify record = new UserNotify();
			record.setType(1);
			record.setContent(notify.getContent());
			record.setCreateTime(new Date());
			record.setNid(notify.getId());
			record.setIsRead(0);
			record.setTitle(notify.getTitle());
			record.setRedirectType(notify.getRedirectType());
			record.setRedirectContent(notify.getRedirectContent());
			if(StringUtils.isNotBlank(notify.getUserId())){
				User user = userMapper.selectByPrimaryKey(notify.getUserId());
				if(user == null)return new Ret(1,"用户不存在");
				record.setUserId(notify.getUserId());
				userNotifyMapper.insert(record);
			}else{
//				UserExample example = new UserExample();
//				List<User> list = userMapper.selectByExample(example);
//				for (User user : list) {
//					record.setId(null);
//					record.setUserId(user.getId());
//					userNotifyMapper.insert(record);
//				}
//				notify.setNum(list.size());
			}
			
			notify.setState(2);
			notifyMapper.updateByPrimaryKey(notify);
			return new Ret(0);
		}
		return new Ret(0);
	}

	public void updateIsRead(String notifyId) {
		notifyMapperEx.updateIsRead(notifyId);
	}
}