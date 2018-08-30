package cn.feezu.maintweb.web.rest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.primitives.Bytes;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.converter.RepairConverter;
import cn.feezu.maint.assign.entity.Repair;
import cn.feezu.maint.assign.manager.RepairManager;
import cn.feezu.maint.vehicle.entity.MaintUserDTO;
import cn.feezu.maintweb.config.ApplicationProperties;
import cn.feezu.maintweb.util.FileResponse;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.PaginationUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/web-api/repair")
@Api(tags = "报修管理")
public class RepairResource extends CoreResource {

	@Autowired
	private RepairManager repairManager;
	@Autowired
	private RepairConverter repairConverter;
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@ApiOperation("待办报修单")
	@GetMapping("/list/false")
	ResponseEntity<List<Repair>> queryAll(String plantNo, Short status, Date startTime, Date endTime, Long repairUserId,
			Pageable page) {

		Page<Repair> entities = repairManager.queryAll(getGroupIds(), plantNo, false, startTime, endTime, repairUserId, page);
		
		entities.forEach(repairConverter::convertToEntity);
		 
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/web-api/repair/list/false");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);

	}

	//TODO 需要优化
	@ApiOperation("导出待办报修单")
	@GetMapping("/list/false/repairing_record.csv")
	ResponseEntity<InputStreamResource> downLoadQueryAll(String plantNo, Short status, Date startTime, Date endTime, Long repairUserId) throws IOException {

		Page<Repair> entities = repairManager.queryAll(getGroupIds(),plantNo, false, startTime, endTime, repairUserId,new PageRequest(0, 10000));
		
		String fileName = applicationProperties.getTempFlePath()+"repairing_"+System.currentTimeMillis()+".csv";
		
		FileSystemResource file = new FileSystemResource(fileName);
		String line = "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s"
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s";
		
		StringBuffer sb = new StringBuffer("序号" 
				+ Constants.DEFAULT_DELIMITER + "车牌号"
				+ Constants.DEFAULT_DELIMITER + "位置" 
				+ Constants.DEFAULT_DELIMITER + "报障人员"
				+ Constants.DEFAULT_DELIMITER + "报修时间"
				+ Constants.DEFAULT_DELIMITER + "报修位置"
				+ Constants.DEFAULT_DELIMITER + ""
				+ Constants.DEFAULT_DELIMITER + "状态").append(Constants.DEFAULT_END);
		int start=1;
		for (Repair entity : entities.getContent()) {
			
			entity = repairConverter.convertToEntity(entity);
			
			sb.append(String.format(line, start++,
					entity.getPlateNo(),
					convertCsvDisplay(entity.getAddress()), 
					convertCsvDisplay(entity.getReportUserName()),
					convertCsvDisplay(entity.getCreateTimeString()),
					entity.getLatitude()+","+entity.getLongitude(), 
					convertCsvDisplay(entity.getStatusName()))).append(
							Constants.DEFAULT_END);
		}
		sb.append(Constants.CSV_DESC_APPEND);
		Files.write(Bytes.concat(Constants.commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())),
				new File(fileName));
		
		return FileResponse.back(file);

	}
	//TODO 需要优化
	@ApiOperation("导出已办报修单")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "String", required = false, value = "车牌号", defaultValue = "京A123456"),
			@ApiImplicitParam(paramType = "query", name = "status", dataType = "integer", required = false, value = "工单状态"),
			@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "报修开始时间"),
			@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "报修结束时间") })
	@GetMapping("/list/true/completed_record.csv")
	ResponseEntity<InputStreamResource> downloadQueryAll(String plantNo, Short status, Date startTime, Date endTime) throws IOException {

		Page<Repair> entities = repairManager.queryAll(getGroupIds(),plantNo, true, startTime, endTime, null, new PageRequest(0, 10000));

		String fileName = applicationProperties.getTempFlePath()+"completed_repair_"+System.currentTimeMillis()+".csv";
		
		FileSystemResource file = new FileSystemResource(fileName);  
		
		String line = "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s"
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s";
		
		StringBuffer sb = new StringBuffer("序号" 
				+ Constants.DEFAULT_DELIMITER + "车牌号" 
				+ Constants.DEFAULT_DELIMITER + "位置" 
				+ Constants.DEFAULT_DELIMITER + "报修人员" 
				+ Constants.DEFAULT_DELIMITER + "报修时间"
				+ Constants.DEFAULT_DELIMITER + "工单编号" 
				+ Constants.DEFAULT_DELIMITER + "状态").append(Constants.DEFAULT_END);;
		int start=1;
		for (Repair entity : entities.getContent()) {
			entity = repairConverter.convertToEntity(entity);
			sb.append(String.format(line, start++,
					convertCsvDisplay(entity.getPlateNo()), 
					/*entity.getLatitude(),entity.getLongitude(),*/
					convertCsvDisplay(entity.getAddress()), 
					convertCsvDisplay(entity.getReportUserName()),
					convertCsvDisplay(entity.getCreateTimeString()), 
					entity.getOrderId()==null?"":entity.getOrderId(), 
					convertCsvDisplay(entity.getStatusName()))).append(Constants.DEFAULT_END);
		}
		sb.append(Constants.CSV_DESC_APPEND);
		Files.write(Bytes.concat(Constants.commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())),
				new File(fileName));
		
		return FileResponse.back(file);

	}

	@ApiOperation("已办报修单")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "String", required = false, value = "车牌号", defaultValue = "京A123456"),
			@ApiImplicitParam(paramType = "query", name = "status", dataType = "integer", required = false, value = "工单状态"),
			@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "报修开始时间"),
			@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "报修结束时间") })
	@GetMapping("/list/true")
	ResponseEntity<List<Repair>> queryAll(String plantNo, Short status, Date startTime, Date endTime, Pageable page) {

		Page<Repair> entities = repairManager.queryAll(getGroupIds(),plantNo, true, startTime, endTime, null, page);
		entities.forEach(repairConverter::convertToEntity);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/web-api/repair/list/true");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);

	}
	
	@ApiOperation("报修单详情")
	@GetMapping("/{id:.+}")
	ResponseEntity<Repair> queryById(@PathVariable Long id) {
		return ResponseUtil.wrapOrNotFound(repairManager.findById(id).map(repairConverter::convertToEntity));

	}

	@ApiOperation("关闭问题")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "reason", dataType = "String", required = true, value = "原因", defaultValue = "说明原因") })
	@PostMapping("/{id:.+}/close")
	ResponseEntity<Map<String, Object>> closeById(@PathVariable Long id, String reason) {

		return repairManager.findById(id).map(entity -> {
			MaintUserDTO user =getUserDTO();
			boolean result = repairManager.close(entity, reason, user.getUserId(),user.getUserName());
			if (!result) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(results("result", result));
			}
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("关闭成功", "success"))
					.body(results("result", result));
		}).orElse(returnNotFound(HeaderUtil.createFailureAlert("repair", "message", "条目不存在")));

	}

/*	@ApiOperation("派单")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "reason", dataType = "String", required = true, value = "原因", defaultValue = "说明原因") })
	@PostMapping("/{id:.+}/order/create")
	ResponseEntity<Map<String, Object>> createOrder(@PathVariable Long id, @RequestBody OrderDTO entityDTO) {

		return repairManager.findById(id).map(entity -> {
			MaintUserDTO user =getUserDTO();
			boolean result = repairManager.createOrder(entity, entityDTO,user.getUserId(),user.getUserName());
			if (!result) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(results("result", result));
			}
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("派单成功", "success"))
					.body(results("result", result));
		}).orElse(returnNotFound(HeaderUtil.createFailureAlert("repair", "message", "条目不存在")));

	}*/
	

	@ApiOperation("报修处理")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "reason", dataType = "String", required = false, value = "原因" ) ,
			@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "String", required = false, value = "工单编号" ) ,
			
	})	
	@PostMapping("/{id:.+}/operate")
	ResponseEntity<Map<String, Object>> createOrder(@PathVariable Long id,short type,@RequestParam(required=false) String reason, @RequestParam(required=false) Long orderNo) {

		return repairManager.findById(id).map(entity -> {
			MaintUserDTO user =getUserDTO();
			boolean result = false;
			if(Constants.Repair.CLOSED == type){
				result = repairManager.close(entity, reason, user.getUserId(),user.getUserName());
			}else if(Constants.Repair.CREATE_ORDER == type){
				
				result =repairManager.operate(entity.getId(),type,orderNo,reason,user.getUserId(),user.getUserName());
			}
			if (!result) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(results("result", result));
			}
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "success"))
					.body(results("result", result));
		}).orElse(returnNotFound(HeaderUtil.createFailureAlert("repair", "message", "条目不存在")));

	}
	
	private String convertCsvDisplay(String value) {
		if(StringUtils.isBlank(value)) {
			return "";
		}
		return value.trim();
	}
}
