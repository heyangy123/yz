package autoCode.daoEx;

import autoCode.daoEx.code.JavaCodeImpl;
import autoCode.daoEx.code.XMLCodeImpl;

public class AutoCodeRun {
	public static void main(String[] args) throws Exception {
		/* 配置项——begin  */
		String beanName = "Footprint"; // 实体类名称不带Ex
		String projectName = "website";// 项目名称 com.xx
		/* 配置项——end  */
		
		
		
		
		
		String mapperName = beanName + "Mapper";
		{
			XMLCodeImpl xai = new XMLCodeImpl(projectName, beanName, mapperName);
			xai.save();
		}
		{
			JavaCodeImpl cai = new JavaCodeImpl(projectName, beanName, true);
			cai.save();
		}
		{
			JavaCodeImpl cai = new JavaCodeImpl(projectName, mapperName, false);
			cai.save();
		}
	}
}
