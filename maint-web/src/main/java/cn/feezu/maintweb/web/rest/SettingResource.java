package cn.feezu.maintweb.web.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.Settings;
import cn.feezu.maint.assign.manager.SettingsManager;
import cn.feezu.maint.util.DictUtils;
import cn.feezu.maintweb.web.dto.ResultDTO;
import cn.feezu.maintweb.web.dto.SettingContent;
import cn.feezu.maintweb.web.dto.SettingValue;
import cn.feezu.maintweb.web.dto.SettingsDTO;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/web-api/settings")
@Api(tags = "相关设置")
public class SettingResource  extends CoreResource{

	@Autowired
	private SettingsManager settingsManager;
	
	@ApiOperation("超时工单设置列表")
	@GetMapping("/order")
	ResponseEntity<SettingsDTO> getOrderSet() {

		Map<String, SettingContent> value=new HashMap<>();
		DictUtils.getCode(Constants.ORDER_OVER_TIME).forEach((k,v)->{
			value.put(k, new SettingContent(v, ""));
		});
		 
		String companyId=getCompanyId();//公司ID
		Optional<Settings> entity = settingsManager.findByCompanyId(companyId);
		
		
		if(entity.isPresent()){
			 
			Settings settings = entity.get();
			@SuppressWarnings("unchecked")
			Map<String,Map<String, String>> _content=JSON.parseObject(settings.getContent(), Map.class);
			if(_content.containsKey(Constants.Order.OVER_TIME)){
				Map<String, String> kv = _content.get(Constants.Order.OVER_TIME);
				kv.forEach((k,v)->value.get(k).setValue(v));
			}
		}
		 
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("请求成功", "success")).body(new SettingsDTO(value));
	}
//	{
//		  "paramSet": {
//		    "code4": "5",
//		    "code3": "4",
//		    "code2": "3",
//		    "code1": "2",
//		    "code5": "6"
//		  }
//		}
	/**
	 * 
	 * @param content
	 * @return
	 */
	@ApiOperation("超时工单设置保存")
	@PostMapping("/order")
	ResponseEntity<ResultDTO> postOrderSet(@RequestBody SettingValue content) {
 
		String companyId=getCompanyId();//公司ID
		if(content==null || content.getParamSet()==null ||  content.getParamSet().isEmpty()){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("settings", "message", "保存失败")).body(new ResultDTO(false));
		}
		
		//带有超时设置
		Map<String, String> results = content.getParamSet();
		
		if(!settingsManager.config(companyId,Constants.Order.OVER_TIME,JSON.toJSONString(results/*results(Constants.Order.OVER_TIME, results)*/))){
			//保存失败
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("settings", "message", "保存失败")).body(new ResultDTO(false));
		}
		
		//TODO 保存成功
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("请求成功", "success")).body(new ResultDTO(true));
	}
	
}
