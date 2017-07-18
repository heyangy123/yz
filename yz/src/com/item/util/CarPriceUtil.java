package com.item.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class CarPriceUtil {
	public static String getCarPrice(BigDecimal price){
		if(price.floatValue()<10000){
			return price.floatValue()+"";
		}else if(price.floatValue()<1000000){
			return price.divide(new BigDecimal(10000),2, RoundingMode.HALF_UP).floatValue()+"万";
		}else{
			return price.divide(new BigDecimal(1000000),2, RoundingMode.HALF_UP).floatValue()+"百万";
		}
		
	}
	
	public static String getCarPrice(Long minPrice,Long maxPrice){
		BigDecimal min = new BigDecimal(minPrice);
		BigDecimal max = new BigDecimal(maxPrice);
		if(max.floatValue()<1000000){
			return min.divide(new BigDecimal(10000),2, RoundingMode.HALF_UP).floatValue()+"-"+max.divide(new BigDecimal(10000),2, RoundingMode.HALF_UP).floatValue()+"万";
		}else{
			return min.divide(new BigDecimal(1000000),2, RoundingMode.HALF_UP).floatValue()+"-"+max.divide(new BigDecimal(1000000),2, RoundingMode.HALF_UP).floatValue()+"百万";
		}
	}
	
	public static String getCarMile(BigDecimal mile){
		if(mile.floatValue()<10000){
			return mile+"";
		}else if(mile.floatValue()<1000000){
			return mile.divide(new BigDecimal(10000),2, RoundingMode.HALF_UP).floatValue()+"万";
		}else{
			return mile.divide(new BigDecimal(1000000),2, RoundingMode.HALF_UP).floatValue()+"百万";
		}
	}
	
	public static String getOrderCode(int length){
    	Long n = System.currentTimeMillis();
    	String s = n.toString();
    	if(length<=3) {
    		return s.substring(s.length()-length,s.length());
    	}
    	s = s.substring(s.length()-length+3,s.length());
    	s+=getrandomString(3);
    	return s;
    }
	
	public static String getrandomString(int strLength) {        
        Random rm = new Random();
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        String fixLenthString = String.valueOf(pross);
        return fixLenthString.substring(1, strLength + 1);
    }
}
