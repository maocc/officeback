package cn.feezu.maint.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.manager.OrderOperateManager;
import cn.feezu.maint.service.OrderManager;

/**
 * 
 * 
 * 工单派发X分钟后未接单进入到超时未接单工单列表。
 * 
 * 
 * @author zhangfx
 *
 */
public class OrderSendTimeoutSyncTimer extends QuartzJobBean {

	final static Logger logger = LoggerFactory.getLogger(OrderSendTimeoutSyncTimer.class);

	@Autowired
	private OrderOperateManager orderOperateManager;
	@Autowired
	private OrderManager orderManager;
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("OrderSendTimeoutSyncTimer.load.....");

		//获取任务详情内的数据集合
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //获取商品编号
      
        Long orderId = dataMap.getLong("orderId");
        logger.info("开始处理工单消息.设为超时未接工单",orderId);
        
        orderManager.findById(orderId).map(order->{
        	
        	if(Constants.Order.Status.SENDED==order.getStatus()){
        		 orderOperateManager.chageOrderStatus(orderId, Constants.Order.Status.ACCEPT_TIMEOUT);
        		
        	}
        	return null;
        });
        
       
		logger.info("OrderSendTimeoutSyncTimer.end.....");
	}

}
