package cn.feezu.maintapp.web.rest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import cn.feezu.common.util.CommonUtils;
import cn.feezu.common.util.Constants;
import cn.feezu.common.util.DistanceUtil;
import cn.feezu.maint.assign.converter.OrderConverter;
import cn.feezu.maint.assign.entity.OrderDetail;
import cn.feezu.maint.assign.manager.OrderOperateManager;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.manager.MemberManager;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderExample;
import cn.feezu.maint.service.OrderManager;
import cn.feezu.maintapp.queue.OrderAccept;
import cn.feezu.maintapp.queue.OrderQueue;
import cn.feezu.maintapp.queue.QueueTask;
import cn.feezu.maintapp.web.dto.ResultDTO;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.PaginationUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/order")
@Api(tags = "工单服务", description = "提供工单查询，操作等")
public class OrderResource extends CoreResource{
	@Autowired
	private OrderOperateManager orderOperateManager;
	@Autowired
	private OrderConverter orderConverter;
	
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private OrderQueue orderQueue;
	
	static private final String ORDER_NOT_FOUND="工单不存在，请选择其他车辆";
	
	/**
	 * 接口适用于地图找车、工单待处理列表
	 * @param longitude
	 * @param latitude
	 * @param status
	 * @return
	 */
	@ApiOperation("工单列表")
	@GetMapping("/list")
	public ResponseEntity<List<Order>> list(@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude, Pageable pageable) {
		Long groupId = getGroupId();
		OrderExample example = new OrderExample();
		example.createCriteria().andGroupIdEqualTo(groupId).andStatusIn(Arrays.asList(Constants.Order.Status.SENDED,Constants.Order.Status.ACCEPT_TIMEOUT));
		
		List<Order> orders = orderManager.selectByExample(example);
		if(CollectionUtils.isNotEmpty(orders)) {
			
			orders.stream()
			.map(orderConverter::convertToEntity)
			.forEach(order->order.setDistance(DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(order.getLatitude()), Double.parseDouble(order.getLongitude()))));
			//会议上讨论的是把所有的待处理工单查处理啊，然后内存排序返回
		
			orders = orders.stream().filter(order->order.getDistance()<=2000).collect(Collectors.toList());
			
			Collections.sort(orders, (o1,o2)->o1.getDistance()<=o2.getDistance()?-1:1);
		}
		orders =CommonUtils.subList(orders, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(new PageImpl<>(orders,pageable,orders.size()), "/order/list");
		return new ResponseEntity<>(orders, headers, HttpStatus.OK);
	}
	
	/*@ApiOperation("运维工单-待处理")
	@GetMapping("/list/opreate")
	ResponseEntity<List<Order>>  queryOperatIngAll(@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude, @RequestParam(value="status", required=false) String status, 
			@RequestParam(value="plateNo", required=false) String plateNo,Pageable page) {
		Page<Order> entities = orderOperateManager.queryOperatIngAll(null,getGroupId(),null, null, plateNo, null, null, null, null, page);
		
		 entities.getContent().stream().map(orderConverter::convertToEntity);
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/order/list/opreate");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);
	}*/
	
	@ApiOperation("运维工单-已完成")
	@GetMapping("/list/complate")
	ResponseEntity<List<Order>>  queryComplatedAll(@RequestParam(value="plateNo", required=false) String plateNo,Pageable page) {
		Page<Order> entities = orderOperateManager.queryComplatedAll(gerUserInfo().getId(),plateNo, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/order/list/complate");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);
	}
	
	@ApiOperation("运维工单-处理中")
	@GetMapping("/list/opreateing")
	ResponseEntity<List<Order>>  queryAcceptedAll(@RequestParam(value="plateNo", required=false) String plateNo,Pageable page) {
		Page<Order> entities = orderOperateManager.queryAcceptdAll(gerUserInfo().getId(),plateNo, page);
		
		 entities.getContent().stream().forEach(orderConverter::convertToEntity);
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(entities, "/order/list/opreateing");
		return new ResponseEntity<>(entities.getContent(), headers, HttpStatus.OK);
	}
	@ApiOperation("工单详情")
	@GetMapping("/{id:.+}")
	ResponseEntity<OrderDetail> queryById(@PathVariable Long id) {
		return ResponseUtil.wrapOrNotFound(orderOperateManager.findById(id));

	}
	
	@ApiOperation("立即接单")
	@PostMapping("/{id:.+}/accept")
	public DeferredResult<ResponseEntity<ResultDTO>> maps(@PathVariable Long id) {
		
		DeferredResult<ResponseEntity<ResultDTO>> result =new DeferredResult<ResponseEntity<ResultDTO>>(5000L/*毫秒*/);
		
		Optional<OrderDetail> orderDetail =orderOperateManager.findById(id);
		if(!orderDetail.isPresent()){
			result.setErrorResult( badResponse(HeaderUtil.createFailureAlert("order", "message", ORDER_NOT_FOUND), HttpStatus.BAD_REQUEST));
			return result;
		}
 
		OrderAccept oa = new OrderAccept(gerUserInfo().getId(), orderDetail.get().getOrder().getId());
		QueueTask<OrderAccept> task = new QueueTask<OrderAccept>(result,oa,false);
		
		result.onTimeout(()->{
			task.setTimeOut(true);
			result.setErrorResult(badResponse(HeaderUtil.createFailureAlert("order", "message", "接单超时，请重试"), HttpStatus.BAD_REQUEST));
			
		});
		
		orderQueue.produce(task);
		
		return result;
		

	}
	
	@ApiOperation("报单")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id",  dataType = "long", required = false, value = "工单id"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType = "string", required = false, value = "备注"),
		})
	@PostMapping("/{id:.+}/submit")
	public ResponseEntity<ResultDTO> submit(@PathVariable Long id,String description, @RequestBody Map<String,Map<String, String>> content) {
		
		Optional<OrderDetail> orderDetail =orderOperateManager.findById(id);
		
		if(!orderDetail.isPresent()){
			return badResponse(HeaderUtil.createFailureAlert("order", "message", ORDER_NOT_FOUND), HttpStatus.BAD_REQUEST);
		}
		Optional<Order> order = orderManager.findByOrderNo(orderDetail.get().getOrder().getOrderNo());
		
		if(!order.isPresent()){
			return badResponse(HeaderUtil.createFailureAlert("order", "message", ORDER_NOT_FOUND), HttpStatus.BAD_REQUEST);
		}
		
		if (!(Constants.Order.Status.ACCPEPTED == order.get().getStatus() || Constants.Order.Status.COMPLATE_TIMEOUT == order.get().getStatus())) {

			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert("order", "message", "工单状态不正确"), HttpStatus.BAD_REQUEST);
		}

		return orderOperateManager.submit(gerUserInfo().getId(),description, order.get(),content).map(orderOperate->{
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("报单成功", "order")).body(new ResultDTO(true));
		}).orElse(ResponseUtil.badResponse(HeaderUtil.createFailureAlert("order", "message", "报单失败"), HttpStatus.BAD_REQUEST));
		
	}
	
	@ApiOperation("工单转派")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id",  dataType = "long", required = false, value = "工单id"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType = "string", required = false, value = "备注"),
		})
	@PostMapping("/{id:.+}/recycle")
	ResponseEntity<ResultDTO> recycle(@PathVariable Long id , @RequestParam(value="description", required=false) String description) {
		 
		
		Optional<OrderDetail> orderDetail =orderOperateManager.findById(id);
		
		if(!orderDetail.isPresent()){
			return badResponse(HeaderUtil.createFailureAlert("order", "message", ORDER_NOT_FOUND), HttpStatus.BAD_REQUEST);
		}
		
		Order order = orderDetail.get().getOrder();
		if(!(Constants.Order.Status.ACCPEPTED== order.getStatus() || Constants.Order.Status.COMPLATE_TIMEOUT == order.getStatus())){
			return badResponse(HeaderUtil.createFailureAlert("order", "message", "转派失败"), HttpStatus.BAD_REQUEST);
		}
		if(StringUtils.isBlank(description)){
			description="用户"+gerUserInfo().getId()+"转派工单";
		}
		Member member = memberManager.selectByPrimaryKey(gerUserInfo().getId());
		return orderOperateManager.recycle(String.valueOf(gerUserInfo().getId()),member.getName(), order, description).map(oo->{
			
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("转派成功", "order")).body(new ResultDTO(true));
		}).orElse(ResponseUtil.badResponse(HeaderUtil.createFailureAlert("order", "message", "报单失败"), HttpStatus.BAD_REQUEST));
	}
}
