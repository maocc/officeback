package cn.feezu.maint.assign.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 派单实体
 * @author zhangfx
 *
 */
@Data
public class OrderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7675590865828525592L;
	private String plantNo;//车牌号
	private Long groupId;//运维组
	private Long memberId;//运维组成员
	private String orderType;//工单类型
	
	private  String latitude;//调度等位置
	private String longitude;//调度等位置
	private String meno;//派单备注
}
