package cn.feezu.maint.job.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.Settings;
import cn.feezu.maint.assign.manager.SettingsManager;
import cn.feezu.maint.job.LoginErrorCountDeleteSyncTimer;
import cn.feezu.maint.job.OrderOperateTimeoutSyncTimer;
import cn.feezu.maint.job.OrderSendTimeoutSyncTimer;
import cn.feezu.maint.job.OrderSendToGroupSyncTimer;
import cn.feezu.wzc.scheduler.SchedulerHelper;

/**
 * 同步服务
 * 
 * @author zhangfx
 *
 */
@Service
public class SyncService {
	
	final static Logger logger = LoggerFactory.getLogger(SyncService.class);
	
	@Autowired
	private SchedulerHelper schedulerHelper;
	@Autowired
	private SettingsManager settingsManager;

	/**
	 * 删除用户的登录次数限制，每天23:59点执行
	 * @throws Exception 
	 */
	public void buidDeleteUserLoginErrorCount() throws Exception{
		String name = LoginErrorCountDeleteSyncTimer.class.getName();
		//设置每5秒执行一次
    	String cron = "0 59 23 * * ?";
    	 //任务名称
        
    	schedulerHelper.build(LoginErrorCountDeleteSyncTimer.class,name, cron);		
	}
	/**
	 * 将订单分配到组
	 * @param companyId
	 * @param orderId
	 */
	public void buildOrderSendToGroupSyncTimer(String companyId, Long orderId) {

		// 任务名称
		String name = OrderSendToGroupSyncTimer.class.getName() + UUID.randomUUID().toString();

		// 秒杀开始时间(1 秒后)
		long startTime = System.currentTimeMillis() + 1000 * 1;

		Map<String, Object> params = new HashMap<>();

		params.put("orderId", orderId);
		params.put("companyId", companyId);
		try {
			schedulerHelper.build(OrderSendToGroupSyncTimer.class, name, startTime, params);
		} catch (Exception e) {
			logger.error("工单创建后分配到车辆所在组出错",e);
		}
		
		logger.info("工单创建后分配到车辆所在组完成");
	}
	
	/**
	 * 工单派发X分钟后未接单进入到超时未接单工单列表。
	 * @param companyId 公司Id
	 * @param orderId 工单ID
	 */
	public void buildOrderSendTimeoutSyncTimer(String companyId, Long orderId) {

		getSettings(companyId, "code1").map(time -> {

			// 任务名称
			String name = OrderSendTimeoutSyncTimer.class.getName() + UUID.randomUUID().toString();

			// 秒杀开始时间(X 分钟后)
			long startTime = System.currentTimeMillis() + 1000 * 60 * Long.parseLong(time);

			Map<String, Object> params = new HashMap<>();

			params.put("orderId", orderId);

			try {
				schedulerHelper.build(OrderSendTimeoutSyncTimer.class, name, startTime, params);
			} catch (Exception e) {
				logger.error("工单派发X分钟后未接单进入到超时未接单工单列表--出错",e);
			}
			return null;
		});
	}

	/**
	 * 接到调度工单后X分钟后未处理完成进入到超时未完成工单列表。
	 * @param companyId 公司Id
	 * @param orderId 工单ID
	 * @param code 字典表记录得工单类型对应得超时设置code
	 */
	public void buildOrderOperateTimeoutSyncTimer(String companyId, Long orderId, String code) {

		getSettings(companyId, code).map(time -> {

			// 任务名称
			String name = OrderOperateTimeoutSyncTimer.class.getName() + UUID.randomUUID().toString();

			// 秒杀开始时间(X 分钟后)
			long startTime = System.currentTimeMillis() + 1000 * 60 * Long.parseLong(time);

			Map<String, Object> params = new HashMap<>();

			params.put("orderId", orderId);

			try {
				schedulerHelper.build(OrderOperateTimeoutSyncTimer.class, name, startTime, params);
			} catch (Exception e) {
				logger.error("接到调度工单后X分钟后未处理完成进入到超时未完成工单列表。",e);
			}
			return null;
		});
	}

	private Optional<String> getSettings(String companyId, String code) {

		Optional<Settings> settings = settingsManager.findByCompanyId(companyId);
		Optional<Map<String, String>> maps = settings.map(setting -> {
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> content = JSON.parseObject(setting.getContent(), Map.class);
			return content.get(Constants.Order.OVER_TIME);
		});

		return maps.map(map -> {
			if (map.containsKey(code)) {
				return map.get(code);
			}
			if("code1".equals(code)){//默认值
				return "10";
			}else{
				return "120";
			}
		});

	}
}
