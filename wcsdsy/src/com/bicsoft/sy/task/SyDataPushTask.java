package com.bicsoft.sy.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bicsoft.sy.util.DateTimeUtil;

/**
 * @数据推送定时任务
 * @Author    Gaohua
 * @Version   2015/09/05
 */

@Component
public class SyDataPushTask {
	private static final Logger log = LoggerFactory.getLogger(SyDataPushTask.class);
	
	//每天晚上3点开始进行数据同步
	@Scheduled(cron="0 0 3  * * ? ")
	public void myTest(){    
		log.info(DateTimeUtil.getCurrentDateTime() + " 开始数据推送...");
		
		//数据同步代码,现在代码在RestApiController中定义，等联调完毕后拷过来即可
		
		log.info(DateTimeUtil.getCurrentDateTime() + " 数据推送完毕！");
	}    

}
