package cn.feezu.maint.job;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.feezu.maint.service.CacheService;

/**
 * 定时删除登录次数限制
 * 
 * @author zhangfx
 *
 */
public class LoginErrorCountDeleteSyncTimer extends QuartzJobBean {

	final static Logger logger = LoggerFactory.getLogger(LoginErrorCountDeleteSyncTimer.class);
	@Resource
	private CacheService testService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		testService.deleteAll();
		logger.debug("清除緩存完成");
	}

}
