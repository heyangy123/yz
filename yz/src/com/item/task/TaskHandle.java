package com.item.task;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.base.task.ScheduleJob;

@Component
public class TaskHandle{
	private static final Logger logger = Logger.getLogger(TaskHandle.class);
	
    public void execute(ScheduleJob scheduleJob){
    	System.out.println("进入处理类,taskid:"+scheduleJob.getJobId());
    	
//    	if(scheduleJob.getJobId().equals("1001")){
//    		taskService.updateOrderEnd();//订单超时自动完成
//    	}else if(scheduleJob.getJobId().equals("1002")){
//    		taskService.updateGoodsMode2();//提前将抢购商品置为抢购状态
//    	}else if(scheduleJob.getJobId().equals("1003")){
//    		taskService.updateGoodsMode1();//将上一轮的商品模式置为正常
//    	}else if(scheduleJob.getJobId().equals("1004")){
//    		taskService.updateStoreVisitReport();//每天统计商家访问量
//    	}
    }
}