package com.yz.service;

import com.yz.dao.ProductMapper;
import com.yz.dao.model.Product;
import com.yz.dao.model.ProductExample;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    private static final Logger logger = Logger.getLogger(ProductService.class);

    public int countByExample(ProductExample example) {
        return this.productMapper.countByExample(example);
    }

    public Product selectByPrimaryKey(String id) {
        return this.productMapper.selectByPrimaryKey(id);
    }

    public List<Product> selectByExample(ProductExample example) {
        return this.productMapper.selectByExampleWithBLOBs(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.productMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Product record) {
        return this.productMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Product record) {
        return this.productMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int deleteByExample(ProductExample example) {
        return this.productMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Product record, ProductExample example) {
        return this.productMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Product record, ProductExample example) {
        return this.productMapper.updateByExampleWithBLOBs(record, example);
    }

    public int insert(Product record) {
        return this.productMapper.insert(record);
    }

    public int insertSelective(Product record) {
        return this.productMapper.insertSelective(record);
    }
}