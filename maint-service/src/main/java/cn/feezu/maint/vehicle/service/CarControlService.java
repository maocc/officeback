package cn.feezu.maint.vehicle.service;

import java.util.Optional;

import cn.feezu.common.constant.CommonConstant;
import cn.feezu.maint.webservice.ws.vehicle.service.CarObdDTO;
import cn.feezu.maint.webservice.ws.vehicle.service.Result;

/**
 * @author maocc
 * @description
 * @date: 2018/3/30 16:12
 */
public interface CarControlService {

	/**
	 * 车辆控制
	 * @param controlType 控制类型
	 * @see  CommonConstant 控制具体命令
	 * @param license 车牌号
	 * @return
	 */
	public Result carControl(String controlType,String license);
	/**
	 * 获取obd详情
	 * @param license 车牌号
	 * @return
	 */
	Optional<CarObdDTO> getCarObdInfoById(String license);
}
