package cn.feezu.maint.assign.converter;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.manager.MemberManager;
import cn.feezu.maint.order.entity.OrderOperate;
@Component
public class OrderOperateConverter {
	private static final String pattern = "yyyy-MM-dd HH:mm";
	
	@Autowired
	private MemberManager memberManager;

	public OrderOperate convertToEntity(OrderOperate entity) {
		
			//操作人员
			if(Constants.OrderOperateType.ACCEPT ==entity.getOperateType()||
					Constants.OrderOperateType.SUBMIT ==entity.getOperateType()){
				//接单报单是成员
				
				if(NumberUtils.isCreatable(entity.getOperateUserId() )){
					Member member = memberManager.selectByPrimaryKey(Long.parseLong(entity.getOperateUserId()));
					if (member != null) {
						entity.setOperateUserName(member.getName());
					}
				}
				
			}else{
				//其他是系统管理员
			}
			//操作时间
			if (entity.getOperateTime() != null) {
				entity.setOperateTimeString(DateFormatUtils.format(entity.getOperateTime(), pattern));
			}
		 
		 
		return entity;
	}
}
