package cn.feezu.maint.assign.converter;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.Settings;
import cn.feezu.maint.assign.manager.SettingsManager;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.manager.MemberManager;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.util.DictUtils;

@Component
public class OrderConverter {

	private static final String pattern = "yyyy-MM-dd HH:mm";

	@Autowired
	private MemberManager memberManager;

	@Autowired
	private SettingsManager settingsManager;
	
	public Order convertToEntity(Order entity) {
		// private String orderTypeName;//订单类型名称
		Map<String, String> orderType = DictUtils.getCode(Constants.Order.ORDER_TYPE);
		if (orderType.containsKey(String.valueOf(entity.getOrderType()))) {

			entity.setOrderTypeName(orderType.get(String.valueOf(entity.getOrderType())));
		}
		DateFormatUtils.format(new Date(), pattern);

		//
		// private String sendTimeString;//派单时间字符
		if (entity.getSendTime() != null) {
			entity.setSendTimeString(DateFormatUtils.format(entity.getSendTime(), pattern));
		}
		if (entity.getCreateTime() != null) {
			entity.setCreateTimeString(DateFormatUtils.format(entity.getCreateTime(), pattern));
		}
		// private String acceptTimeString;//接单时间字符
		if (entity.getAcceptTime() != null) {
			entity.setAcceptTimeString(DateFormatUtils.format(entity.getAcceptTime(), pattern));
		}
		// 接单时长
		if (entity.getAcceptTime() != null && entity.getSendTime() != null) {
			entity.setSendToacceptTime(getMin(entity.getAcceptTime(), entity.getSendTime()));
		}

		// private String operateTimeString;//结单时间字符
		if (entity.getOperate() != null) {
			entity.setOperateTimeString(DateFormatUtils.format(entity.getOperate(), pattern));
		}
		// private String acceptUserName;//运维人员
		if (entity.getAcceptUserId() != null) {
			Member member = memberManager.selectByPrimaryKey(entity.getAcceptUserId());
			if (member != null) {
				entity.setAcceptUserName(member.getName());
			}
		}
		// private String statusName;//订单状态
		Map<Short, String> statusMap = Constants.Order.Status.titles;
		if (statusMap.containsKey(entity.getStatus())) {
			entity.setStatusName(statusMap.get(entity.getStatus()));
		}

		
		if(Constants.Order.Status.ACCEPT_TIMEOUT == entity.getStatus() ||
				Constants.Order.Status.COMPLATE_TIMEOUT == entity.getStatus()
				){
			
			Map<String, String> settings = getSettings(entity.getCompanyId());
			
			if (Constants.Order.Status.ACCEPT_TIMEOUT == entity.getStatus()) {
				// 超时时长设置
				int min =0;
				if(settings!=null){
					min=Integer.valueOf(settings.get("code1"));
				}
				entity.setTimeout(getDatePoor(new Date(), DateUtils.addMinutes(entity.getCreateTime(), min)));
				entity.setStatusName(statusMap.get(Constants.Order.Status.SENDING));
			} else if (Constants.Order.Status.COMPLATE_TIMEOUT == entity.getStatus()) {
				// 超时时长设置
				int min =0;
				if(settings!=null){
					
					 String code=null;
					 if(Constants.Order.OrderType.CLEAN == entity.getOrderType()){
						 code="code3";
					 }else if(Constants.Order.OrderType.CHARGING == entity.getOrderType()){
						 code="code2";
					 }else if(Constants.Order.OrderType.DISPATCH == entity.getOrderType()){
						 code="code4";
					 }else{
						 code="code5";
					 }
					
					min=Integer.valueOf(settings.get(code));
				}
				entity.setTimeout(getDatePoor(new Date(), entity.getAcceptTime()==null?null:DateUtils.addMinutes(entity.getAcceptTime(),min)));
				entity.setStatusName(statusMap.get(Constants.Order.Status.ACCPEPTED));
			}
		}
		
		if (Constants.Order.Status.ACCPEPTED == entity.getStatus()) {

			entity.setOperateTime(getMin(new Date(),entity.getAcceptTime()));

		} else if (Constants.Order.Status.CONFIRM == entity.getStatus()
				|| Constants.Order.Status.COMPLATE == entity.getStatus()
				|| Constants.Order.Status.EVALUATE == entity.getStatus()) {
			if (entity.getAcceptTime() != null && entity.getOperate() != null) {
				entity.setOperateTime(getMin(entity.getOperate(), entity.getAcceptTime()));
			}
		}
		return entity;
	}

	private Map<String, String> getSettings(String companyId){
		//获取设置时间
		Optional<Settings> setting = settingsManager.findByCompanyId(companyId);
		
		if(setting.isPresent()){
			 
			Settings settings = setting.get();
			@SuppressWarnings("unchecked")
			Map<String,Map<String, String>> _content=JSON.parseObject(settings.getContent(), Map.class);
			if(_content.containsKey(Constants.Order.OVER_TIME)){
				return _content.get(Constants.Order.OVER_TIME);
				 
			}
		}
		return null;
	}
	public static String getDatePoor(Date endDate, Date nowDate) {
		if (endDate == null || nowDate == null) {
			return null;
		}
		long nd = 1000 * 24 * 60 * 60L;
		long nh = 1000 * 60 * 60L;
		long nm = 1000 * 60L;
		 long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = DateUtils.setSeconds(endDate, 0).getTime() - DateUtils.setSeconds(nowDate, 0).getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		 long sec = diff % nd % nh % nm / ns;
		 if(sec>0){
			 min++;
		 }
		return day + "天" + hour + "小时" + min + "分钟";
	}

	public static String getMin(Date endDate, Date nowDate) {
		if (endDate == null || nowDate == null) {
			return null;
		}
		long nm = 1000 * 60L;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = DateUtils.setSeconds(endDate, 0).getTime() - DateUtils.setSeconds(nowDate, 0).getTime();
		// 计算差多少分钟
		long day = diff / nm;
		
		// 计算差多少秒//输出结果
		long sec = diff % nm;
		if(sec>0){
			day++;
		}
		return day + "分钟";
	}
}
