package cn.feezu.maintapp.queue;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.feezu.maint.assign.entity.OrderDetail;
import cn.feezu.maint.assign.manager.OrderOperateManager;
import cn.feezu.maint.order.entity.OrderOperate;

/**
 * 抢单队列
 * 
 * @author zhangfx
 *
 */
@Component
public class OrderQueue {

	final static Logger log = LoggerFactory.getLogger(OrderQueue.class);

	private static BlockingQueue<QueueTask<OrderAccept>> queue = new LinkedBlockingQueue<>(1000);

	private static BlockingQueue<QueueTask<OrderAccept>> complate = new LinkedBlockingQueue<>(1000);
	@Autowired
	private OrderOperateManager orderOperateManager;
	
	static private final String ORDER_NOT_FOUND="工单不存在，请选择其他车辆";
	/**
	 * 产生抢单
	 * 
	 * @param oa
	 * @throws InterruptedException
	 */
	public boolean produce(QueueTask<OrderAccept> oa) {

		try {
			if (!queue.offer(oa, 2, TimeUnit.SECONDS)) {
				log.error("放入数据失败：" + oa);
				return false;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// queue.put(oa);
		return true;
	}

	/**
	 * 消费抢单
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public QueueTask<OrderAccept> consume() throws InterruptedException {
		return complate.take();
	}

	/*
	 * 处理任务
	 */
	public void excute() {
		new Thread(() -> {
			try {

				while (true) {
					// 消费
					log.info("消费者准备消费: " + System.currentTimeMillis());
					QueueTask<OrderAccept> oa = queue.take();
					
					Optional<OrderDetail> orderDetail =orderOperateManager.findById(oa.getMessage().getOrderId());
					
					if(!orderDetail.isPresent()){
						oa.setError(true);
						oa.setErrorMessage(ORDER_NOT_FOUND);
						complate.put(oa);
						log.info("消费者消费完毕: " + oa);
						 continue;
					}
				/*	Optional<Order> order = orderManager.findByOrderNo(orderDetail.get().getOrder().getOrderNo());
					
					if(!order.isPresent()){
						oa.setError(true);
						oa.setErrorMessage(ORDER_NOT_FOUND);
						complate.put(oa);
						log.info("消费者消费完毕: " + oa);
						 continue;
					}*/
					
					//用户是否已有订单
					boolean exists =orderOperateManager.findExistOrderByUserId(oa.getMessage().getUserId()).isPresent();
					if(exists){
						oa.setError(true);
						oa.setErrorMessage("您还有在处理中的工单，请先处理完再进行抢单");
						complate.put(oa);
						log.info("消费者消费完毕: " + oa);
						 continue;
					}
					
					try{
						Optional<OrderOperate> oo =orderOperateManager.accept(oa.getMessage().getUserId(), orderDetail.get().getOrder());
						
						if(oo.isPresent()){
							oa.setError(false);
							oa.setErrorMessage("接单成功");
							complate.put(oa);
							log.info("消费者消费完毕: " + oa);
						}else{
							oa.setError(true);
							oa.setErrorMessage("您还有在处理中的工单，请先处理完再进行抢单");
							complate.put(oa);
							log.info("消费者消费完毕: " + oa);
						}
						continue;
					}catch(Exception e){
						
						oa.setError(true);
						oa.setErrorMessage(e.getMessage());
						complate.put(oa);
						log.info("消费者消费完毕: " + oa);
					}
					
					if(oa.isTimeOut()){
						
						log.info("任务超时，中断操作");
						continue;
					}
					
					complate.put(oa);
					log.info("消费者消费完毕: " + oa);
				}

			} catch (InterruptedException ex) {

			}

		}).start();
	}

	public OrderQueue(){
		excute();//开启任务处理
	}
}
