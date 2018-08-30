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

import cn.feezu.common.constant.CommonConstant;
import cn.feezu.common.util.Constants;
import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.assign.converter.OrderConverter;
import cn.feezu.maint.assign.entity.OrderDetail;
import cn.feezu.maint.assign.manager.OrderOperateManager;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.service.OrderManager;
import cn.feezu.maint.vehicle.entity.MaintUserDTO;
import cn.feezu.maint.vehicle.service.CarControlService;
import cn.feezu.maint.webservice.ws.vehicle.service.Result;
import cn.feezu.maintweb.config.ApplicationProperties;
import cn.feezu.maintweb.util.FileResponse;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.PaginationUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import cn.feezu.wzc.common.map.Coords;
import cn.feezu.wzc.common.map.Position;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/web-api/order")
@Api(tags = "工单服务")
public class OrderReousrce extends CoreResource {

	@Autowired
	private OrderOperateManager orderOperateManager;
	@Autowired
	private ApplicationProperties applicationProperties;
	@Autowired
	private OrderConverter orderConverter;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private CarControlService carControlService;
	
	@ApiOperation("工单创建")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "type", dataType = "double", required = true, 
				value = "工单类型"
						+" \t\n 充电工单:1"
						+ "\t\n 清洁工单:2"
						+" \t\n 调度工单:3"),
		@ApiImplicitParam(paramType = "query", name = "plateNo", dataType = "string", required = true, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "companyId", dataType = "string", required = true, value = "公司Id"),
		@ApiImplicitParam(paramType = "query", name = "source", dataType = "double", required = true, value = "工单来源（0:微租车平台）"),
		@ApiImplicitParam(paramType = "query", name = "createUserId", dataType = "string", required = true, value = "创建人ID（创建工单的用户ID）"),
		@ApiImplicitParam(paramType = "query", name = "createUserName", dataType = "string", required = true, value = "创建人名称（创建工单的用户名称）"),
		@ApiImplicitParam(paramType = "query", name = "latitude", dataType = "double", required = true, value = "车辆当前位置latitude"),
		@ApiImplicitParam(paramType = "query", name = "longitude", dataType = "double", required = true, value = "车辆当前位置longitude"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType = "string", required = true, 
				value = "工单内容（工单内容，电量，目标位置）"
						+" \t\n 充电工单:{\"power\":\"20%\"}"
						+ "\t\n 清洁工单:"
						+" \t\n 调度工单:{\"address\":\"北京市海淀区软件园中科大洋大厦\",\"latitude\":\"40.057482\",\"longitude\":\"116.305671\"}"),
	})
	
	@PostMapping("/create")
	ResponseEntity<Map<String, Object>> create(@RequestParam("plateNo") String plateNo,@RequestParam("companyId") String companyId, @RequestParam("type") Short type,
			@RequestParam("source") Short source, @RequestParam("createUserId") String createUserId, @RequestParam("createUserName") String createUserName,
			@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude,
			@RequestParam(value="remark", required=false) String remark, @RequestParam(value="description", required=false) String description) {
		if(StringUtils.isBlank(plateNo)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("order", "message", "车牌号不能为空")).body(null);
		}
		if(longitude==0 ||latitude==0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("order", "message", "车辆定位不正确")).body(null);
		}
		if(type<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("order", "message", "工单类型错误")).body(null);
		}
		
		if(source<0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("order", "message", "工单来源错误")).body(null);
		}
		
		if(StringUtils.isBlank(createUserId) || StringUtils.isBlank(createUserName)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("order", "message", "创建人信息不能为空")).body(null);
		}
		
		Order order = new Order();
		order.setId(IDUtils.getId());
		order.setMemo(remark);
		order.setOrderNo(IDUtils.getId()+"");
		order.setOrderType(type);
		order.setSource(source);
		order.setPlantNo(plateNo);
		order.setCreateTime(new Date());
		order.setCreateId(createUserId);
		order.setCreateName(createUserName);
		order.setLatitude(String.valueOf(latitude));
		order.setLongitude(String.valueOf(longitude));
		order.setCompanyId(companyId);
		Coords coords =new Coords();
		coords.setLongitude(String.valueOf(longitude));
		coords.setLatitude(String.valueOf(latitude));
		
		order.setAddress(Position.baiduCoordsToAddress(coords));
		
		order.setStatus(Constants.Order.Status.CREATE);
		order.setContent(description.replaceAll("&quot;", "\""));
		
		boolean result = orderManager.createOrder(order);
		
		if(!result){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert("order", "message", "创建失败")).body(null);
		}
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("创建成功", "success")).body(results("result", order.getOrderNo()));
	}

	
	@ApiOperation("工单详情")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id",  dataType = "long", required = false, value = "工单id"),
		})
	@GetMapping("/{id:.+}")
	ResponseEntity<OrderDetail> queryById(@PathVariable Long id) {
		return ResponseUtil.wrapOrNotFound(orderOperateManager.findById(id));

	}
	
	
	@ApiOperation("工单取消")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id",  dataType = "string", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType = "string", required = false, value = "备注"),
		})
	@PostMapping("/{id:.+}/cancel")
	ResponseEntity<Map<String, Object>> cancel(@PathVariable String id , @RequestParam(value="description", required=false) String description) {
		 
		
		return orderOperateManager.findByOrderNo(id).map(orderOperate -> {
			
			Order order = orderOperate.getOrder();
			if(  order.getStatus() > Constants.Order.Status.ACCPEPTED && order.getStatus() < Constants.Order.Status.ACCEPT_TIMEOUT){
				return badResponse(HeaderUtil.createFailureAlert("order", "message", "取消失败"), HttpStatus.BAD_REQUEST);
			}
			//MaintUserDTO user =getUserDTO();
			orderOperateManager.cancel(order.getCreateId(),order.getCreateName(), order, description).map(oo->{
				return ResponseEntity.ok().headers(HeaderUtil.createAlert("取消成功", "order")).body(results("result", true));
			}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "取消失败"), HttpStatus.BAD_REQUEST));
			
			
			
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("工单取消成功", "success")).body(results("result", true));
		}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "工单不存在"), HttpStatus.BAD_REQUEST));
		 
	}
	
	@ApiOperation("工单转派")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id",  dataType = "long", required = false, value = "工单id"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType = "string", required = false, value = "备注"),
		})
	@PostMapping("/{id:.+}/recycle")
	ResponseEntity<Map<String, Object>> recycle(@PathVariable Long id , @RequestParam(value="description", required=false) String description) {
		 
		return orderOperateManager.findById(id).map(orderOperate -> {
			
			Order order = orderOperate.getOrder();
			if(Constants.Order.Status.ACCPEPTED!= order.getStatus()){
				return badResponse(HeaderUtil.createFailureAlert("order", "message", "转派失败"), HttpStatus.BAD_REQUEST);
			}
			MaintUserDTO user =getUserDTO();
			orderOperateManager.recycle(user.getUserId(),user.getUserName(), order, description).map(oo->{
				return ResponseEntity.ok().headers(HeaderUtil.createAlert("转派成功", "order")).body(results("result", true));
			}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "报单失败"), HttpStatus.BAD_REQUEST));
			
			
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("工单转派", "success")).body(results("result", true));
		}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "工单不存在"), HttpStatus.BAD_REQUEST));
		 
	}
	
	@ApiOperation("工单确认（待确认的改为确认）")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id",  dataType = "long", required = false, value = "工单id"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType = "string", required = false, value = "备注"),
		})
	@PostMapping("/{id:.+}/confirm")
	ResponseEntity<Map<String, Object>> confirm(@PathVariable Long id , @RequestParam(value="description", required=false) String description) {
		 
		return orderOperateManager.findById(id).map(orderOperate -> {
			
			Order order = orderOperate.getOrder();
			if(Constants.Order.Status.CONFIRM!= order.getStatus()){
				return badResponse(HeaderUtil.createFailureAlert("order", "message", "工单状态不正确"), HttpStatus.BAD_REQUEST);
			}
			
			// 参数待补充
			Result result = carControlService.carControl(CommonConstant.CAR_CONTROL_POWER_RESET, order.getPlantNo());
			if (!result.isSuccess()) {
			 
				String errorMsg = result.getErrors().get(0);
				
				return badResponse(HeaderUtil.createFailureAlert("order", "message", "初始化车辆失败:"+errorMsg), HttpStatus.BAD_REQUEST);
			}
			MaintUserDTO user =getUserDTO();
			orderOperateManager.confirm(user.getUserId(),user.getUserName(), order, description).map(oo->{
				return ResponseEntity.ok().headers(HeaderUtil.createAlert("确认成功", "order")).body(results("result", true));
			}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "确认失败"), HttpStatus.BAD_REQUEST));
			
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("确认成功", "success")).body(results("result", true));
		}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "工单不存在"), HttpStatus.BAD_REQUEST));
		 
	}
	
	@ApiOperation("工单评价（已确认的进行评价）")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id",  dataType = "long", required = false, value = "工单id"),
		@ApiImplicitParam(paramType = "query", name = "star", dataType = "string", required = false, value = "评星",allowableValues="range[1, 5]"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType = "string", required = false, value = "备注"),
		})
	@PostMapping("/{id:.+}/evaluate")
	ResponseEntity<Map<String, Object>> evaluate(@PathVariable Long id , @RequestParam(value="star", required=true) String star, @RequestParam(value="description", required=false) String description) {

		return orderOperateManager.findById(id).map(orderOperate -> {
			
			Order order = orderOperate.getOrder();
			if(Constants.Order.Status.COMPLATE!= order.getStatus()){
				return badResponse(HeaderUtil.createFailureAlert("order", "message", "工单状态不正确"), HttpStatus.BAD_REQUEST);
			}
			MaintUserDTO user =getUserDTO();
			orderOperateManager.evaluate(user.getUserId(),user.getUserName(), order,star, description).map(oo->{
				return ResponseEntity.ok().headers(HeaderUtil.createAlert("评价成功", "order")).body(results("result", true));
			}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "评价失败"), HttpStatus.BAD_REQUEST));
			
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("评价成功", "success")).body(results("result", true));
		}).orElse(badResponse(HeaderUtil.createFailureAlert("order", "message", "工单不存在"), HttpStatus.BAD_REQUEST));
		 
	}
	/**
	 * 
	 * @param orderNo 公单号
	 * @param operateUserId 运维人员 
	 * @param plantNo 车牌号
	 * @param startTime 派单开始时间
	 * @param endTime 派单结束时间
	 * @param orderType 工单类型
	 * @param orderStatus 工单状态
	 * @param page
	 * @return
	 */
	@ApiOperation("超时未接工单列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "string", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "double", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "short", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "short", required = false, value = "派单方式"),
		})
	@GetMapping("/list/false/timeout/accept")
	ResponseEntity<List<Order>>  queryAcceptTimeoutAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus,Short createType,Pageable page) {
		Page<Order> entities = orderOperateManager.queryAcceptTimeoutAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/web-api/order/list/false/timeout/accept");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);
	}
	
	
	@ApiOperation("导出超时未接工单列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "string", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "double", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "short", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "short", required = false, value = "派单方式"),
		})
	@GetMapping("/list/false/timeout/accept/order_timeout_accept.csv")
	ResponseEntity<InputStreamResource>  downLoadAcceptTimeoutAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus,Short createType,Pageable page) throws IOException  {
		Page<Order> entities = orderOperateManager.queryAcceptTimeoutAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		
		 String fileName = applicationProperties.getTempFlePath()+"order_timeout_accept"+System.currentTimeMillis()+".csv";
			
			FileSystemResource file = new FileSystemResource(fileName);
			String line = "%s" 
					/*+ Constants.DEFAULT_DELIMITER + "%s" */
					+ Constants.DEFAULT_DELIMITER + "%s"
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s"
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s";
			
			StringBuffer sb = new StringBuffer(/*"序号" 
					+ Constants.DEFAULT_DELIMITER + */"工单号" 
					+ Constants.DEFAULT_DELIMITER + "工单类型" 
					+ Constants.DEFAULT_DELIMITER + "车牌号" 
					+ Constants.DEFAULT_DELIMITER + "派单时间"
					+ Constants.DEFAULT_DELIMITER + "接单时长" 
					+ Constants.DEFAULT_DELIMITER + "开始时间" 
					+ Constants.DEFAULT_DELIMITER + "超时时长"
					+ Constants.DEFAULT_DELIMITER + "运维人员"
					+ Constants.DEFAULT_DELIMITER + "状态").append(Constants.DEFAULT_END);
			 
			/*int start=1;*/
			for (Order entity : entities.getContent()) {
				entity = orderConverter.convertToEntity(entity);
				
				sb.append(String.format(line, /*start++,*/
						entity.getOrderNo(),
						convertCsvDisplay(entity.getOrderTypeName()), 
						entity.getPlantNo(),
						convertCsvDisplay(entity.getSendTimeString()),
						convertCsvDisplay(entity.getSendToacceptTime()),
						convertCsvDisplay(entity.getAcceptTimeString()),
						convertCsvDisplay(entity.getTimeout()),
						convertCsvDisplay(entity.getAcceptUserName()),
						convertCsvDisplay(entity.getStatusName()))).append(
								Constants.DEFAULT_END);
			}
			sb.append(Constants.CSV_DESC_APPEND);
			Files.write(Bytes.concat(Constants.commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())),
					new File(fileName));
			
			return FileResponse.back(file);

	}
	
	@ApiOperation("超时未完成工单列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "String", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "double", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "double", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "double", required = false, value = "派单方式"),
		})
	@GetMapping("/list/false/timeout/complate")
	ResponseEntity<List<Order>>  queryComplateTimeoutAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus,Short createType,Pageable page) {
		Page<Order> entities = orderOperateManager.queryComplateTimeoutAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/web-api/order/list/false/timeout/complate");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);
	}
	
	//序号、工单号、工单类型、车牌号、派单时间、接单时长、开始时间、超时时长、运维人员、状态。
	
	
	@ApiOperation("导出超时未完成工单列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "String", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "double", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "double", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "double", required = false, value = "派单方式"),
		})
	@GetMapping("/list/false/timeout/complate/order_timeout_complate.csv")
	ResponseEntity<InputStreamResource>  downloadComplateTimeoutAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus,Short createType,Pageable page) throws IOException {
		Page<Order> entities = orderOperateManager.queryComplateTimeoutAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		 String fileName = applicationProperties.getTempFlePath()+"order_operateing_record"+System.currentTimeMillis()+".csv";
			
			FileSystemResource file = new FileSystemResource(fileName);
			String line = "%s" 
					/*+ Constants.DEFAULT_DELIMITER + "%s" */
					+ Constants.DEFAULT_DELIMITER + "%s"
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s"
					+ Constants.DEFAULT_DELIMITER + "%s" 
					+ Constants.DEFAULT_DELIMITER + "%s";
			
			StringBuffer sb = new StringBuffer(/*"序号" 
					+ Constants.DEFAULT_DELIMITER +*/ "工单号" 
					+ Constants.DEFAULT_DELIMITER + "工单类型" 
					+ Constants.DEFAULT_DELIMITER + "车牌号" 
					+ Constants.DEFAULT_DELIMITER + "派单时间"
					+ Constants.DEFAULT_DELIMITER + "接单时长" 
					+ Constants.DEFAULT_DELIMITER + "开始时间" 
					+ Constants.DEFAULT_DELIMITER + "超时时长"
					+ Constants.DEFAULT_DELIMITER + "运维人员"
					+ Constants.DEFAULT_DELIMITER + "状态").append(Constants.DEFAULT_END);
			 
			/*int start=1;*/
			for (Order entity : entities.getContent()) {
				entity = orderConverter.convertToEntity(entity);

				sb.append(String.format(line,/* start++,*/
						" "+entity.getOrderNo(),
						convertCsvDisplay(entity.getOrderTypeName()), 
						convertCsvDisplay(entity.getPlantNo()),
						convertCsvDisplay(entity.getSendTimeString()),
						convertCsvDisplay(entity.getSendToacceptTime()),
						convertCsvDisplay(entity.getAcceptTimeString()),
						convertCsvDisplay(entity.getTimeout()),
						convertCsvDisplay(entity.getAcceptUserName()),
						convertCsvDisplay(entity.getStatusName()))).append(
								Constants.DEFAULT_END);
			}
			sb.append(Constants.CSV_DESC_APPEND);
			Files.write(Bytes.concat(Constants.commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())),
					new File(fileName));
			
			return FileResponse.back(file);

	}
	
	/**
	 * 
	 * @param orderNo 工单号
	 * @param operateUserId 运维人员
	 * @param plantNo 车牌号
	 * @param startTime 派单开始时间
	 * @param endTime 派单结束时间
	 * @param orderType 工单类型
	 * @param orderStatus 工单状态
	 * @param createType 派单方式
	 * @param page
	 * @return
	 */
	@ApiOperation("待办全部工单列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "String", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "short", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "short", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "short", required = false, value = "派单方式"),
		})
	@GetMapping("/list/false")
	ResponseEntity<List<Order>>  queryOperatIngAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus, Short createType, Pageable page) {
		Page<Order> entities = orderOperateManager.queryOperatIngAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, orderStatus, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/web-api/order/list/false");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);
	}
	//TODO 需要优化
	@ApiOperation("导出待办全部工单")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "String", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "short", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "short", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "short", required = false, value = "派单方式"),
		})
	@GetMapping("/list/false/order_operateing_record.csv")
	ResponseEntity<InputStreamResource> downLoadOperatIngAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus, Short createType) throws IOException {
		
		Page<Order> entities = orderOperateManager.queryOperatIngAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, orderStatus, new PageRequest(0, 10000));
		
		String fileName = applicationProperties.getTempFlePath()+"order_operateing_record"+System.currentTimeMillis()+".csv";
		
		FileSystemResource file = new FileSystemResource(fileName);
		String line = "%s" 
				/*+ Constants.DEFAULT_DELIMITER + "%s" */
				+ Constants.DEFAULT_DELIMITER + "%s"
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s"
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s"
				/*+ Constants.DEFAULT_DELIMITER + "%s"*/;
		
		StringBuffer sb = new StringBuffer(/*"序号" 
				+ Constants.DEFAULT_DELIMITER +*/ "工单号" 
				+ Constants.DEFAULT_DELIMITER + "工单类型" 
				+ Constants.DEFAULT_DELIMITER + "车牌号" 
				+ Constants.DEFAULT_DELIMITER + "派单时间"
				+ Constants.DEFAULT_DELIMITER + "接单时长" 
				+ Constants.DEFAULT_DELIMITER + "开始时间" 
				+ Constants.DEFAULT_DELIMITER + "结束时间"
				+ Constants.DEFAULT_DELIMITER + "运维时长"
				+ Constants.DEFAULT_DELIMITER + "运维人员"
				+ Constants.DEFAULT_DELIMITER + "状态"
				/*+ Constants.DEFAULT_DELIMITER + "位置"*/).append(Constants.DEFAULT_END);
		 
		for (Order entity : entities.getContent()) {
			entity = orderConverter.convertToEntity(entity);
			
			sb.append(String.format(line, /*start++,*/
					entity.getOrderNo(),
					convertCsvDisplay(entity.getOrderTypeName()), 
					entity.getPlantNo(),
					convertCsvDisplay(entity.getSendTimeString()),
					convertCsvDisplay(entity.getSendToacceptTime()),
					convertCsvDisplay(entity.getAcceptTimeString()),
					convertCsvDisplay(entity.getOperateTimeString()),
					convertCsvDisplay(entity.getOperateTime()),
					convertCsvDisplay(entity.getAcceptUserName()),
					convertCsvDisplay(entity.getStatusName())/*,
					convertCsvDisplay(entity.getAddress())*/)).append(
							Constants.DEFAULT_END);
		}
		sb.append(Constants.CSV_DESC_APPEND);
		Files.write(Bytes.concat(Constants.commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())),
				new File(fileName));
		
		return FileResponse.back(file);

	}

	@ApiOperation("已办工单列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "String", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "short", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "short", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "short", required = false, value = "派单方式"),
		})
	@GetMapping("/list/true")
	ResponseEntity<List<Order>>  queryComplateAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus, Short createType,Pageable page) {
		Page<Order> entities = orderOperateManager.queryComplateAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, orderStatus, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/web-api/order/list/true");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);
	}
	
	//TODO 需要优化
	@ApiOperation("导出已办工单")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderNo", dataType = "String", required = false, value = "工单号"),
		@ApiImplicitParam(paramType = "query", name = "operateUserId", dataType = "long", required = false, value = "运维人员"),
		@ApiImplicitParam(paramType = "query", name = "plantNo", dataType = "string", required = false, value = "车牌号"),
		@ApiImplicitParam(paramType = "query", name = "orderStatus", dataType = "short", required = false, value = "工单状态"),
		@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "string", required = false, value = "派单开始时间"),
		@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "string", required = false, value = "派单结束时间"),
		@ApiImplicitParam(paramType = "query", name = "orderType", dataType = "short", required = false, value = "工单类型"),
		@ApiImplicitParam(paramType = "query", name = "createType", dataType = "short", required = false, value = "派单方式"),
		})
	@GetMapping("/list/true/order_complate_record.csv")
	ResponseEntity<InputStreamResource> downLoadQueryComplateAll(String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus, Short createType) throws IOException {
		
		Page<Order> entities = orderOperateManager.queryComplateAll(getCompanyId(),null,orderNo, operateUserId, plantNo, startTime, endTime, orderType, orderStatus, new PageRequest(0, 10000));
		
		String fileName = applicationProperties.getTempFlePath()+"order_complate_record"+System.currentTimeMillis()+".csv";
		
		FileSystemResource file = new FileSystemResource(fileName);
		String line = "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s"
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s"
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s" 
				+ Constants.DEFAULT_DELIMITER + "%s";
		
		StringBuffer sb = new StringBuffer("序号" 
				+ Constants.DEFAULT_DELIMITER + "工单号" 
				+ Constants.DEFAULT_DELIMITER + "工单类型" 
				+ Constants.DEFAULT_DELIMITER + "车牌号" 
				+ Constants.DEFAULT_DELIMITER + "派单时间"
				+ Constants.DEFAULT_DELIMITER + "接单时长" 
				+ Constants.DEFAULT_DELIMITER + "开始时间" 
				+ Constants.DEFAULT_DELIMITER + "结束时间"
				+ Constants.DEFAULT_DELIMITER + "运维时长"
				+ Constants.DEFAULT_DELIMITER + "运维人员"
				+ Constants.DEFAULT_DELIMITER + "状态").append(Constants.DEFAULT_END);
		 
		int start=1;
		for (Order entity : entities.getContent()) {
			entity = orderConverter.convertToEntity(entity);
			
			sb.append(String.format(line, start++, 
					entity.getOrderNo(),
					convertCsvDisplay(entity.getOrderTypeName()),
					entity.getPlantNo(),
					convertCsvDisplay(entity.getSendTimeString()),
					convertCsvDisplay(entity.getSendToacceptTime()),
					convertCsvDisplay(entity.getAcceptTimeString()),
					convertCsvDisplay(entity.getOperateTimeString()),
					convertCsvDisplay(entity.getOperateTime()),
					convertCsvDisplay(entity.getAcceptUserName()),
					convertCsvDisplay(entity.getStatusName()))).append(
							Constants.DEFAULT_END);
		}

		sb.append(Constants.CSV_DESC_APPEND);
		Files.write(Bytes.concat(Constants.commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())),
				new File(fileName));
		
		return FileResponse.back(file);

	}

	private String convertCsvDisplay(String value) {
		if(StringUtils.isBlank(value)) {
			return "";
		}
		return value.trim();
	}
}
