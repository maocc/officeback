package cn.feezu.maintapp.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import cn.feezu.maintapp.web.dto.ResultDTO;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private OrderQueue queue;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new Thread(() -> {
			try {
				while (true) {
					// 消费
					log.info("消费者准备消费: " + System.currentTimeMillis());
					QueueTask<OrderAccept> oa = queue.consume();
					
					if(oa.isTimeOut()){
						oa.getResult().setErrorResult(ResponseUtil.badResponse(HeaderUtil.createFailureAlert("order", "message", "接单超时，请重试"), HttpStatus.BAD_REQUEST));
					}
					else{
						
						if(oa.isError()){
							oa.getResult().setErrorResult(ResponseUtil.badResponse(HeaderUtil.createFailureAlert("order", "message", oa.getErrorMessage()), HttpStatus.BAD_REQUEST));
						}else{
							oa.getResult().setResult(ResponseEntity.ok().headers(HeaderUtil.createAlert("接单成功", "order")).body(new ResultDTO(true)));
						}
					}
					log.info("消费者消费完毕: " + oa + "," + System.currentTimeMillis());
				}
			} catch (InterruptedException ex) {
				log.error("接单失败", ex);
			}

		}).start();
		;
	}

}
