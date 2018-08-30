package cn.feezu.maintapp.web.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7919963537484063284L;
	@ApiModelProperty(value="供电状态")
	private String chargeStatus;
	@ApiModelProperty(value="车门状态")
	private String doorStatus;
	@ApiModelProperty(value="剩余电量")
	private String power;
	@ApiModelProperty(value="续航里程")
	private String batteryKm;
	
}