package com.bicsoft.sy.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.util.DateTimeUtil;

/**
 * @流水号每天0点重置任务
 * @Author    Gaohua
 * @Version   2015/09/05
 */

@Component
public class UpdateServialNumTask {
	private static final Logger log = LoggerFactory.getLogger(UpdateServialNumTask.class);
	
	@Autowired
	private ServialNumService servialNumService;
	
	@Scheduled(cron="0 0 0 * * ?")
	public void resetServialNum(){    
      log.info(DateTimeUtil.getCurrentDateTime() + " 开始重置流水号...");
      servialNumService.updateServialNum();
      log.info(DateTimeUtil.getCurrentDateTime() + " 重置流水号完毕！");
	}    
}
