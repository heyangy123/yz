package com.item.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class InitOnLoad implements BeanPostProcessor{

	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		if (arg0 instanceof RoleUtil){
			RoleUtil roleUtil = (RoleUtil) arg0;
			if(!RoleUtil.inited)
				roleUtil.loadData();
		}
		return arg0;
	}

	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}

	


	
	

	
	
	
}
