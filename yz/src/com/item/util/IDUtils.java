package com.item.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主键ID生成工具类
 * @author cailin66@163.com
 * 
 */
public class IDUtils {
	private static final AtomicInteger increase = new AtomicInteger();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/**
	 * 生成String类型的ID编号
	 * @return yyyyMMddHHmmssSS + 序列号
	 */
	public static String next() {
		StringBuilder builder = new StringBuilder();
		builder.append(DATE_FORMAT.format(new Date()));
		//毫秒SSS取前两位(00~99)
		builder.deleteCharAt(16);
		//循环0000~9999之间的序列数
		builder.append(String.format("%03d", Math.abs(increase.incrementAndGet() % 1000)));
		return builder.toString();
	}
	
	/**
	 * 生成long类型的ID编号
	 * @return yyyyMMddHHmmssSS + 序列号
	 */
	public static long nextId() {
		return Long.parseLong(next());
	}
	
	
//	public static void main (String[] args){
//		System.out.println(IDUtils.next());
//		System.out.println(IDUtils.nextId());
//	}
	
}
