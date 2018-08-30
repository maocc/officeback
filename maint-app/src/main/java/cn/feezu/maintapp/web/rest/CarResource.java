package cn.feezu.maintapp.web.rest;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.feezu.common.constant.CommonConstant;
import cn.feezu.common.util.Constants;
import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.assign.entity.Repair;
import cn.feezu.maint.assign.manager.RepairManager;
import cn.feezu.maint.vehicle.service.CarControlService;
import cn.feezu.maint.webservice.ws.vehicle.service.Result;
import cn.feezu.maintapp.web.dto.CarInfoDTO;
import cn.feezu.maintapp.web.dto.ResultDTO;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/car")
@Api(tags = "车辆服务")
public class CarResource extends CoreResource {
	private static String ENTITY_NAME = "CarResource";
	static private final Logger logger = LoggerFactory.getLogger(CarResource.class);
	
	@Autowired
	private RepairManager repairManager;
	@Autowired
	private CarControlService carControlService;

	@ApiOperation("车辆报修")
	@PostMapping("/report-repair")
	public ResponseEntity<ResultDTO> reportRepair(@RequestParam("plateNo") String plateNo,
			@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude,
			@RequestParam("address") String address, @RequestParam(name="description",required=false) String description,
			@RequestParam("carPics") String[] carPics) {
		// 参数待补充

		Repair entity = new Repair.Builder().id(IDUtils.getId()).plateNo(plateNo).latitude(String.valueOf(latitude))
				.longitude(String.valueOf(longitude)).address(address).content(description)
				.images(StringUtils.join(carPics, ":")).createTime(new Date()).status(Constants.Repair.CREATE).groupId(getGroupId())
				.reportUserId(gerUserInfo().getId()).build();
		repairManager.save(entity);

		return ResponseEntity.ok().headers(HeaderUtil.createAlert("上报成功", "success")).body(new ResultDTO(true));

	}
	

	@ApiOperation("车辆控制")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "controlType", dataType = "String", required = true, 
				value = "操作类型"
				+ "\t\n 鸣笛:"+CommonConstant.CAR_CONTROL_WHISTLE
				+" \t\n 开门:"+CommonConstant.CAR_CONTROL_OPEN_DOOR
				+" \t\n 关门:"+CommonConstant.CAR_CONTROL_CLOSE_DOOR
				+" \t\n 断电:"+CommonConstant.CAR_CONTROL_CUT_POWER
				+" \t\n 供电:"+CommonConstant.CAR_CONTROL_POWER_SUPPLY
				+" \t\n 初始化:"+CommonConstant.CAR_CONTROL_POWER_RESET
				),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "String", required = true, value = "车牌号")})
	@PostMapping("/control")
	public ResponseEntity<ResultDTO> control(@RequestParam("controlType") String controlType, @RequestParam("plantNo") String plateNo) {
		// 参数待补充
		Result result = carControlService.carControl(controlType, plateNo);
		if (result.isSuccess()) {
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("命令执行成功", "success")).body(new ResultDTO(true));
		} else {
			String errorMsg = result.getErrors().get(0);
			logger.error("control errorMsg={}", errorMsg);
			
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", errorMsg),HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("车辆设备信息（续航，电量、车门等状态）")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "String", required = true, value = "车牌号")})
	@GetMapping("/info")
	public ResponseEntity<CarInfoDTO> findByPlateNo(@RequestParam("plantNo") String plateNo) {
 
		Optional<CarInfoDTO> result = carControlService.getCarObdInfoById(plateNo).map(obd->{
			
			//充电状态 0无效 1未充电 2充电中 3充电完成 4充电故障
			String chargeStatus="--";
			if(obd.getChargeStatus()!=null){
				if(obd.getChargeStatus()==0){
					chargeStatus="--";//无效
				}else if(obd.getChargeStatus()==1){
					chargeStatus="未充电";
				}else if(obd.getChargeStatus()==2){
					chargeStatus="充电中";
				}else if(obd.getChargeStatus()==3){
					chargeStatus="充电完成";
				}else if(obd.getChargeStatus()==4){
					chargeStatus="充电故障";
				}
			}
			
			//车门状态  （0无效 1表示关 2未关 3预留） 左前 左右 左后 右后 后备箱
			String doorStatus="--";
			if(obd.getDoorStatus()!=null){
				if(obd.getDoorStatus().contains("2")){
					doorStatus="已开启";
				}else if(obd.getDoorStatus().contains("11111")){
					doorStatus="已关闭";
				}
			}
			//private String soc; //电量百分比
			//private Float fuelPercentage; //油量百分比 0- 100 比如56.6 表示56.6%
			//private Double batteryKm;//续航里程
			 
			return new CarInfoDTO(chargeStatus, doorStatus, obd.getSoc()!=null?obd.getSoc():(obd.getFuelPercentage()==null?"--":obd.getFuelPercentage()+"%"), obd.getBatteryKm()==null?"--":obd.getBatteryKm()+"KM");
		});

		if(!result.isPresent()){
			
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "查询失败"), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "success")).body(result.get());
		 

	}
}
