package com.item.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item.dao.CodeMapper;
import com.item.dao.model.Code;
import com.item.dao.model.CodeExample;

@Service
public class CodeService {
    @Autowired
    private CodeMapper codeMapper;
    private static Map<String,Code> map;

    private static final Logger logger = Logger.getLogger(CodeService.class);
    
    private static final String CODE_FOR_ADD_TOPIC_CREDIT= "1004";

    public int countByExample(CodeExample example) {
        return this.codeMapper.countByExample(example);
    }

    public Code selectByPrimaryKey(String code) {
        return this.codeMapper.selectByPrimaryKey(code);
    }

    public List<Code> selectByExample(CodeExample example) {
        return this.codeMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String code) {
    	int i = codeMapper.deleteByPrimaryKey(code);
    	fleshMap();
        return i;
    }

    public int updateByPrimaryKeySelective(Code record) {
    	int i = codeMapper.updateByPrimaryKeySelective(record);
    	fleshMap();
        return i;
    }
    
    public int updateByPrimaryKey(Code record) {
    	int i = codeMapper.updateByPrimaryKey(record);
    	fleshMap();
        return i;
    }

    public int deleteByExample(CodeExample example) {
    	int i = codeMapper.deleteByExample(example);
    	fleshMap();
        return i;
    }

    public int updateByExampleSelective(Code record, CodeExample example) {
    	int i = codeMapper.updateByExampleSelective(record, example);
    	fleshMap();
        return i;
    }

    public int updateByExample(Code record, CodeExample example) {
    	int i = codeMapper.updateByExample(record, example);
    	fleshMap();
        return i;
    }

    public int insert(Code record) {
    	int i = codeMapper.insert(record);
    	fleshMap();
        return i;
    }

    public int insertSelective(Code record) {
    	int i = codeMapper.insertSelective(record);
    	fleshMap();
        return i;
    }
    
    public String getCode(String code){
    	if(map == null){
    		fleshMap();
    	}
    	Code c = map.get(code);
    	return c == null ? "" : c.getValue();
    }
    
    public Code get(String code){
    	if(map == null){
    		fleshMap();
    	}
    	return map.get(code);
    }
    
    private void fleshMap(){
    	if(map !=null){
    		map.clear();
    	}else{
    		map=new HashMap<String, Code>();
    	}
		List<Code> list = codeMapper.selectByExample(new CodeExample());
		for (Code code : list) {
			map.put(code.getCode(), code);
		}
    }
    
    public String getCODE_FOR_ADD_TOPIC_CREDIT(){
    	return getCode(CODE_FOR_ADD_TOPIC_CREDIT);
    }
}