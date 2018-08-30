package cn.feezu.maint.assign.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderOperate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单详细信息
 * 
 * @author zhangfx
 *
 */
@Data
public class OrderDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836838414845307052L;
	
	@ApiModelProperty(value="工单详情")
	private Order order;
	/**
	 * Map<分类,Map<条目,Map<text/value,对应内容>>>
	 */
	@ApiModelProperty(value="工单检查项")
	private Map<String ,Map<String,SettingContent>> details=null;
	/**
	 * 工单流转信息
	 */
	@ApiModelProperty(value="工单流转信息")
	private List<OrderOperate> operates;
	
}
