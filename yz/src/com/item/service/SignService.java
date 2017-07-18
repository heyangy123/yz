package com.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item.dao.SignMapper;
import com.item.dao.model.Sign;
import com.item.dao.model.SignExample;

@Service
public class SignService {
	@Autowired
	private SignMapper signMapper;

	public int countByExample(SignExample example) {
		return this.signMapper.countByExample(example);
	}

	public Sign selectByPrimaryKey(String id) {
		return this.signMapper.selectByPrimaryKey(id);
	}

	public List<Sign> selectByExample(SignExample example) {
		return this.signMapper.selectByExample(example);
	}

	public int deleteByPrimaryKey(String id) {
		return this.signMapper.deleteByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Sign record) {
		return this.signMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Sign record) {
		return this.signMapper.updateByPrimaryKey(record);
	}

	public int deleteByExample(SignExample example) {
		return this.signMapper.deleteByExample(example);
	}

	public int updateByExampleSelective(Sign record, SignExample example) {
		return this.signMapper.updateByExampleSelective(record, example);
	}

	public int updateByExample(Sign record, SignExample example) {
		return this.signMapper.updateByExample(record, example);
	}

	public int insert(Sign record) {
		return this.signMapper.insert(record);
	}

	public int insertSelective(Sign record) {
		return this.signMapper.insertSelective(record);
	}

}
