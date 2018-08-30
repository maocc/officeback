package cn.feezu.maint.vehicle.service;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.feezu.maint.webservice.vehicle.service.CarService;
import cn.feezu.maint.webservice.ws.vehicle.service.CarDTOArray;
import cn.feezu.maint.webservice.ws.vehicle.service.CarObdDTO;
import cn.feezu.maint.webservice.ws.vehicle.service.Result;

/**
 * @author maocc
 * @description
 * @date: 2018/3/30 16:31
 */
@Service
public class CarControlServiceImpl implements CarControlService {
	private final static Logger logger = LoggerFactory.getLogger(CarControlServiceImpl.class);

	@Value("${webservice.host}")
	private String host;
	/**
	 *
	 * @param controlType 控制类型
	 * @param license 车牌号
	 * @return
	 */
	@Override
	public Result carControl(String controlType, String license) {
		
		Result result = new Result();
		result.setSuccess(false);
		result.setErrors(Arrays.asList("调用失败"));
		
		cn.feezu.maint.webservice.ws.vehicle.service.CarService carService = null;
		try {
			carService = new CarService(host).getCarServiceImplPort();
		} catch (MalformedURLException e) {
			logger.error("carControl MalformedURLException",e);
		}
		if(carService==null){
			return result;
		}
		
		logger.info("license:{}",license);
		CarDTOArray carDTOArray = carService.findCarInfoByLicense(license);
		if(carDTOArray.getItem() != null && carDTOArray.getItem().size() > 0){
			logger.info("控制车辆命令- license:{},controlType:{},carId:{}",license,controlType,carDTOArray.getItem().get(0).getCarId());
			result = carService.controlCarByManager(controlType,carDTOArray.getItem().get(0).getCarId());
		}
		
		return result;
	}
	
	/**
	 * 获取obd详情
	 * @param license 车牌号
	 * @return
	 */
	@Override
	public Optional<CarObdDTO> getCarObdInfoById(String license) {
		cn.feezu.maint.webservice.ws.vehicle.service.CarService carService = null;
		try {
			carService = new CarService(host).getCarServiceImplPort();
		} catch (MalformedURLException e) {
			logger.error("carControl MalformedURLException",e);
		}
		if(carService==null){
			return Optional.empty();
		}
		CarDTOArray carDTOArray = carService.findCarInfoByLicense(license);
		
		if(CollectionUtils.isEmpty(carDTOArray.getItem())){
			return Optional.empty();
		}
		
		return Optional.of(carService.getCarObdInfoById(carDTOArray.getItem().get(0).getCarId()));
	}
}
