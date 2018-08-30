package cn.feezu.maint.dubbo;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wzc.common.Result;
import com.wzc.manage.meta.ws.eomsManage.service.MaintDispatchOrderService;

@Component
public class OrderService {

	@Reference
	public MaintDispatchOrderService maintDispatchOrderService;
	
	public String notityOrderComplete(String orderNo) {
		Result result = maintDispatchOrderService.maintDoneOrder(Long.parseLong(orderNo));
		if(result.isSuccess()) {
			return "";
		}
		
		List<String> errors = result.getErrors();
		if(CollectionUtils.isNotEmpty(errors)) {
			return errors.get(0);
		}
		
		return "系统错误";
	}
}
