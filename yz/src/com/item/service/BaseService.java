//package com.item.service;
//
//import java.math.BigDecimal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.cxs.daoEx.BaseMapperEx;
//
//@Service
//public class BaseService {
//	@Autowired
//	private BaseMapperEx baseMapperEx;
//
//	/**
//	 * 更新余额积分等(防止因并发导致的数据错误)
//	 * @param tableName 表名
//	 * @param colum 需要更新的数据库字段名称
//	 * @param num BigDecimal or int
//	 * @param id ID
//	 * @return
//	 */
//	public int updateNumById(String tableName, String colum, Object num, String id){
//    	return this.updateNumById(tableName, colum, num, id, "id");
//    }
//
//	/**
//	 * 更新余额积分等(防止因并发导致的数据错误)
//	 * @param tableName 表名
//	 * @param colum 需要更新的数据库字段名称
//	 * @param num BigDecimal or int
//	 * @param id ID
//	 * @param idColum 需要更新的主键名称
//	 * @return
//	 */
//	public int updateNumById(String tableName, String colum, Object num, String id, String idColum) {
//		BigDecimal n = null;
//		if(num instanceof BigDecimal){
//			n = (BigDecimal)num;
//		}else if(num instanceof Integer){
//			n = BigDecimal.valueOf((Integer)num);
//		}else{
//			return 0;
//		}
//		return baseMapperEx.updateNumById(tableName, colum, idColum, id, n);
//	}
//
//}