package com.fx.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dialect.PaginationSupport;
import com.base.util.StringUtil;
import com.fx.dao.RandomCodeMapper;
import com.fx.dao.model.RandomCode;
import com.fx.dao.model.RandomCodeExample;

@Service
public class RandomCodeService {
    @Autowired
    private RandomCodeMapper randomCodeMapper;

    private static final Logger logger = Logger.getLogger(RandomCodeService.class);

    public int countByExample(RandomCodeExample example) {
        return this.randomCodeMapper.countByExample(example);
    }

    public RandomCode selectByPrimaryKey(String id) {
        return this.randomCodeMapper.selectByPrimaryKey(id);
    }

    public List<RandomCode> selectByExample(RandomCodeExample example) {
        return this.randomCodeMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.randomCodeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(RandomCode record) {
        return this.randomCodeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(RandomCode record) {
        return this.randomCodeMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(RandomCodeExample example) {
        return this.randomCodeMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(RandomCode record, RandomCodeExample example) {
        return this.randomCodeMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(RandomCode record, RandomCodeExample example) {
        return this.randomCodeMapper.updateByExample(record, example);
    }

    public int insert(RandomCode record) {
        return this.randomCodeMapper.insert(record);
    }

    public int insertSelective(RandomCode record) {
        return this.randomCodeMapper.insertSelective(record);
    }
    
	public void insertBatch(){
		RandomCode code = null;
		int count = 100;
		while(count > 0){
			code = new RandomCode();
			code.setId(StringUtil.getRandomNum(8));
			code.setState(1);
			try {
				randomCodeMapper.insert(code);
				count--;
			} catch (Exception e) {
			}
		}
	}
	
	public String getRandom(){
		RandomCodeExample example = new RandomCodeExample();
		example.createCriteria().andStateEqualTo(1);
		example.setOrderByClause("rand()");
		PaginationSupport.byPage(1, 1, false);
		List<RandomCode> list = randomCodeMapper.selectByExample(example);
		if(list.size() == 0){
			insertBatch();
			return getRandom();
		}else{
			RandomCode code = list.get(0);
			code.setState(0);
			randomCodeMapper.updateByPrimaryKey(code);
			return code.getId();
		}
	}
}