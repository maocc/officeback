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
 * 接到调度工单后X分钟后未处理完成进入到超时未完成工单列表。
 * @author zhangfx
 *
 */
public class OrderOperateTimeoutSyncTimer extends QuartzJobBean {

	final static Logger logger = LoggerFactory.getLogger(OrderOperateTimeoutSyncTimer.class);
	@Autowired
	private OrderOperateManager orderOperateManager;
	
	@Autowired
	private OrderManager orderManager;
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("OrderOperateTimeoutSyncTimer.load.....");

		//获取任务详情内的数据集合
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //获取商品编号
      
        Long orderId = dataMap.getLong("orderId");
        System.out.println("+++++++++++++++++++"+orderId);
        logger.info("开始处理工单消息，将工单设为超时未完成.",orderId);
        
        orderManager.findById(orderId).map(order->{
        	
        	if(Constants.Order.Status.ACCPEPTED==order.getStatus()){
        		orderOperateManager.chageOrderStatus(orderId, Constants.Order.Status.COMPLATE_TIMEOUT);
        	}
        	return null;
        });
        
      
		logger.info("OrderOperateTimeoutSyncTimer.end.....");
	}

}

	

