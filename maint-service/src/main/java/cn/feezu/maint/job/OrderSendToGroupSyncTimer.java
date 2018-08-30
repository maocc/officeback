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

public class OrderSendToGroupSyncTimer extends QuartzJobBean {

	final static Logger logger = LoggerFactory.getLogger(OrderSendToGroupSyncTimer.class);

	@Autowired
	private OrderOperateManager orderOperateManager;
	@Autowired
	private OrderManager orderManager;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("OrderSendToGroupSyncTimer.load.....");

		//获取任务详情内的数据集合
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //获取商品编号
      
        Long orderId = dataMap.getLong("orderId");
        String companyId = dataMap.getString("companyId");
        logger.info("开始处理工单消息.设为超时未接工单,orderId:{},companyId:{}",orderId,companyId);
        
        orderManager.findById(orderId).map(order->{
        	
        	if(Constants.Order.Status.CREATE==order.getStatus()){
        		  //新创建的工单，进行分配
        		orderOperateManager.allocate(0L, order);
        	}
        	return null;
        });
       
		logger.info("OrderSendToGroupSyncTimer.end.....");
	}

}
