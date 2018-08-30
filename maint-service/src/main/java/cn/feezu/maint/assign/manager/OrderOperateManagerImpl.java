package cn.feezu.maint.assign.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.assign.converter.OrderConverter;
import cn.feezu.maint.assign.converter.OrderOperateConverter;
import cn.feezu.maint.assign.entity.CheckList;
import cn.feezu.maint.assign.entity.OrderDetail;
import cn.feezu.maint.assign.entity.SettingContent;
import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.YwAreaGraph;
import cn.feezu.maint.authority.manager.GroupManager;
import cn.feezu.maint.authority.manager.YwAreaGraphManager;
import cn.feezu.maint.dubbo.OrderService;
import cn.feezu.maint.job.scheduler.SyncService;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderExample;
import cn.feezu.maint.order.entity.OrderExample.Criteria;
import cn.feezu.maint.order.entity.OrderOperate;
import cn.feezu.maint.order.entity.OrderOperateExample;
import cn.feezu.maint.order.mapper.OrderOperateMapper;
import cn.feezu.maint.service.OrderManager;
import cn.feezu.maint.util.AreaUtil;
import cn.feezu.maint.util.DictUtils;

@Service
public class OrderOperateManagerImpl implements OrderOperateManager {

	private static final Logger log = LoggerFactory.getLogger(OrderOperateManagerImpl.class);
	@Autowired
	private OrderManager orderManager;
 
	@Autowired
	private CheckListService checkListService;
	@Autowired
	private OrderOperateMapper orderOperateMapper;
	@Autowired
	private OrderConverter orderConverter;
	@Autowired
	private OrderOperateConverter orderOperateConverter;
	@Autowired
	private SyncService syncService;
	@Autowired
	private GroupManager groupManager;
	
	@Autowired
	private YwAreaGraphManager ywAreaGraphManager;
	@Autowired
	private OrderService orderService;
	
	
	@Override
	public Optional<OrderDetail> findById(Long id) {


		Optional<Order> entity = orderManager.findById(id);
	
		return findByOrder(entity);
	}

	@Override
	public Optional<OrderDetail> findByOrderNo(String orderNo){
		Optional<Order> entity = orderManager.findByOrderNo(orderNo);
		return findByOrder(entity);
	}
	
	@SuppressWarnings("unchecked")
	private Optional<OrderDetail> findByOrder(Optional<Order> entity){
		OrderDetail od = new OrderDetail();
		return entity.map(order -> {
			order = orderConverter.convertToEntity(order);
			List<OrderOperate> orderOperate = findOrderOperateById(order.getOrderNo());

			// 设置工单流转信息
			orderOperate.forEach(orderOperateConverter::convertToEntity);
			od.setOperates(orderOperate);

			Map<String, Map<String, SettingContent>> checkList1 =new HashMap<>();
			for (OrderOperate op : orderOperate) {
				if (Constants.OrderOperateType.SUBMIT == op.getOperateType()||
						Constants.OrderOperateType.COMPALATE == op.getOperateType()||
						Constants.OrderOperateType.EVALUATE == op.getOperateType()
						) {
					// 检修信息及评价
					Map<String, Map<String, SettingContent>> checkList=	checkListService
					.findByOrderIdAndOpId(order.getId(), op.getId());
					 
					if(checkList!=null){
						
						checkList.forEach((k,v)->checkList1.put(k, v));
					}
					
				}
				
				//补充结单单姓名
				if(Constants.OrderOperateType.COMPALATE==op.getOperateType() || Constants.OrderOperateType.CANCEL==op.getOperateType()) {
					order.setFinishUserName(op.getOperateUserName());
					order.setFinishTimeString(DateFormatUtils.format(op.getOperateTime(), "yyyy-MM-dd HH:mm"));
				}
			}
			od.setDetails(checkList1);

			if(od.getDetails()==null|| od.getDetails().isEmpty()){
				
				Map<String, Map<String, SettingContent>> checkList = new HashMap<>();
				
				//车况检查
				Map<String, SettingContent> s1= new HashMap<>();
				DictUtils.getCode(Constants.CheckList.VEHICLE_CONDITION).forEach((k,v)->s1.put(k, new SettingContent(v,"")));
				checkList.put(Constants.CheckList.VEHICLE_CONDITION, s1);
				
				//清洁检查
				Map<String, SettingContent> s2= new HashMap<>();
				DictUtils.getCode(Constants.CheckList.CLEAR).forEach((k,v)->s2.put(k, new SettingContent(v,"")));
				checkList.put(Constants.CheckList.CLEAR, s2);
				
				if(order.getOrderType()==Constants.Order.OrderType.CHARGING){
					//充电检查
					Map<String, SettingContent> s3= new HashMap<>();
					DictUtils.getCode(Constants.CheckList.CHARGING_CONDITION).forEach((k,v)->s3.put(k, new SettingContent(v,"")));
					checkList.put(Constants.CheckList.CHARGING_CONDITION, s3);
				}
				
				od.setDetails(checkList);
			}
			
			if(StringUtils.isNotBlank(order.getContent())){
				try{
					order.setExtendContent(JSON.parseObject(order.getContent(), Map.class));
				}catch(Exception e){
					log.error("扩展信息转换map错误："+order.getContent(),e);
				}
			}
			// 设置工单信息
			od.setOrder(order);
			Optional<OrderDetail> detail = Optional.of(od);
			return detail;
		}).orElse(Optional.empty());
	}
	@Override
	public Optional<List<Order>> findExistOrderByUserId(Long userId){
		OrderExample example =new OrderExample();
		example.createCriteria().andAcceptUserIdEqualTo(userId).andStatusEqualTo(Constants.Order.Status.ACCPEPTED);
		List<Order> orders = orderManager.selectByExample(example);
		if(CollectionUtils.isEmpty(orders)){
			return Optional.empty();
		}
		return Optional.of(orders);
	}
	
	private List<OrderOperate> findOrderOperateById(String orderNo) {
		OrderOperateExample example = new OrderOperateExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		example.setOrderByClause("id desc");
		return orderOperateMapper.selectByExample(example);

	}

	@Override
	public Page<Order> queryComplateAll(String companyId, Long groupId, String orderNo, Long operateUserId,
			String plantNo, Date startTime, Date endTime, Short orderType, Short orderStatus, Pageable page) {

		OrderExample example = new OrderExample(page);
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNoneBlank(companyId)) {
			criteria.andCompanyIdEqualTo(companyId);
		}
		if (groupId != null) {
			criteria.andGroupIdEqualTo(groupId);
		}
		if (StringUtils.isNoneBlank(orderNo)) {
			criteria.andOrderNoLike("%"+orderNo+"%");
		}
		if (operateUserId != null) {
			criteria.andAcceptUserIdEqualTo(operateUserId);
		}
		if (StringUtils.isNoneBlank(plantNo)) {
			criteria.andPlantNoLike("%"+plantNo+"%");
		}
		
		if (startTime != null) {
//			startTime = DateUtils.setHours(startTime, 0);
//			startTime = DateUtils.setMinutes(startTime, 0);
//			startTime = DateUtils.setSeconds(startTime, 0);
			criteria.andSendTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
//			endTime = DateUtils.setHours(endTime, 0);
//			endTime = DateUtils.setMinutes(endTime, 0);
//			endTime = DateUtils.setSeconds(endTime, 0);
//			endTime= DateUtils.addDays(endTime, 1);
			criteria.andSendTimeLessThanOrEqualTo(endTime);
		}
		
		if (orderType != null) {
			criteria.andOrderTypeEqualTo(orderType);
		}
		if (orderStatus != null) {
			criteria.andStatusEqualTo(orderStatus);
		}
		//已评价和已取消都属于已完成工单
		List<Short> statusList= new ArrayList<>();
		statusList.add(Constants.Order.Status.CANCEL);
		statusList.add(Constants.Order.Status.EVALUATE);
		criteria.andStatusIn(statusList);
		
		//criteria.andOperateIsNotNull();
		return orderManager.findByExample(example);
	}

	@Override
	public Page<Order> queryOperatIngAll(String companyId, Long groupId, String orderNo, Long operateUserId,
			String plantNo, Date startTime, Date endTime, Short orderType, Short orderStatus, Pageable page) {
		OrderExample example = new OrderExample(page);
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(companyId)) {
			criteria.andCompanyIdEqualTo(companyId);
		}
		if (groupId != null) {
			criteria.andGroupIdEqualTo(groupId);
		}
		if (StringUtils.isNotBlank(orderNo)) {
			criteria.andOrderNoLike("%"+orderNo+"%");
		}
		if (operateUserId != null) {
			criteria.andAcceptUserIdEqualTo(operateUserId);
		}
		if (StringUtils.isNotBlank(plantNo)) {
			criteria.andPlantNoLike("%"+plantNo+"%");
		}
		
		if (startTime != null) {
//			startTime = DateUtils.setHours(startTime, 0);
//			startTime = DateUtils.setMinutes(startTime, 0);
//			startTime = DateUtils.setSeconds(startTime, 0);
			criteria.andSendTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
//			endTime = DateUtils.setHours(endTime, 0);
//			endTime = DateUtils.setMinutes(endTime, 0);
//			endTime = DateUtils.setSeconds(endTime, 0);
//			endTime= DateUtils.addDays(endTime, 1);
			criteria.andSendTimeLessThanOrEqualTo(endTime);
		}
		if (orderType != null) {
			criteria.andOrderTypeEqualTo(orderType);
		}
		if (orderStatus != null) {
			if(orderStatus <= Constants.Order.Status.SENDED){
				List<Short> statusList=new ArrayList<>();
				statusList.add(Constants.Order.Status.CREATE);
				statusList.add(Constants.Order.Status.SENDING);
				statusList.add(Constants.Order.Status.SENDED);
				statusList.add(Constants.Order.Status.ACCEPT_TIMEOUT);
				criteria.andStatusIn(statusList);
			}else if(orderStatus == Constants.Order.Status.ACCPEPTED){
				List<Short> statusList=new ArrayList<>();
				statusList.add(Constants.Order.Status.ACCPEPTED);
				statusList.add(Constants.Order.Status.COMPLATE_TIMEOUT);
				criteria.andStatusIn(statusList);
			}
			
			else{
				
				criteria.andStatusEqualTo(orderStatus);
			}
		}else{
			List<Short> statusList=new ArrayList<>();
			statusList.add(Constants.Order.Status.CREATE);
			statusList.add(Constants.Order.Status.SENDING);
			statusList.add(Constants.Order.Status.SENDED);
			statusList.add(Constants.Order.Status.ACCPEPTED);
			statusList.add(Constants.Order.Status.CONFIRM);
			statusList.add(Constants.Order.Status.COMPLATE);
			statusList.add(Constants.Order.Status.ACCEPT_TIMEOUT);
			statusList.add(Constants.Order.Status.COMPLATE_TIMEOUT);
			criteria.andStatusIn(statusList);
		}
		
		//criteria.andOperateIsNull();
//		example.or(example.createCriteria().andStatusLessThan(Constants.Order.Status.COMPLATE).andStatusGreaterThanOrEqualTo(Constants.Order.Status.ACCEPT_TIMEOUT));
		return orderManager.findByExample(example);
	}
	@Override
	public Page<Order> queryAcceptTimeoutAll(String companyId, Long groupId,String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Pageable page){
		OrderExample example = new OrderExample(page);
		Criteria criteria = example.createCriteria().andStatusEqualTo(Constants.Order.Status.ACCEPT_TIMEOUT);
		if (StringUtils.isNoneBlank(companyId)) {
			criteria.andCompanyIdEqualTo(companyId);
		}
		if (groupId != null) {
			criteria.andGroupIdEqualTo(groupId);
		}
		if (StringUtils.isNoneBlank(orderNo)) {
			criteria.andOrderNoEqualTo(orderNo);
		}
		if (operateUserId != null) {
			criteria.andAcceptUserIdEqualTo(operateUserId);
		}
		if (StringUtils.isNoneBlank(plantNo)) {
			criteria.andPlantNoEqualTo(plantNo);
		}
		if (startTime != null) {
//			startTime = DateUtils.setHours(startTime, 0);
//			startTime = DateUtils.setMinutes(startTime, 0);
//			startTime = DateUtils.setSeconds(startTime, 0);
			criteria.andSendTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
//			endTime = DateUtils.setHours(endTime, 0);
//			endTime = DateUtils.setMinutes(endTime, 0);
//			endTime = DateUtils.setSeconds(endTime, 0);
//			endTime= DateUtils.addDays(endTime, 1);
			criteria.andSendTimeLessThanOrEqualTo(endTime);
		}
		if (orderType != null) {
			criteria.andOrderTypeEqualTo(orderType);
		}
		return orderManager.findByExample(example);
	}
	@Override
	public Page<Order> queryComplateTimeoutAll(String companyId, Long groupId,String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Pageable page){
		OrderExample example = new OrderExample(page);
		Criteria criteria = example.createCriteria().andStatusEqualTo(Constants.Order.Status.COMPLATE_TIMEOUT);
		if (StringUtils.isNoneBlank(companyId)) {
			criteria.andCompanyIdEqualTo(companyId);
		}
		if (groupId != null) {
			criteria.andGroupIdEqualTo(groupId);
		}
		if (StringUtils.isNoneBlank(orderNo)) {
			criteria.andOrderNoEqualTo(orderNo);
		}
		if (operateUserId != null) {
			criteria.andAcceptUserIdEqualTo(operateUserId);
		}
		if (StringUtils.isNoneBlank(plantNo)) {
			criteria.andPlantNoEqualTo(plantNo);
		}
		if (startTime != null) {
//			startTime = DateUtils.setHours(startTime, 0);
//			startTime = DateUtils.setMinutes(startTime, 0);
//			startTime = DateUtils.setSeconds(startTime, 0);
			criteria.andSendTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
//			endTime = DateUtils.setHours(endTime, 0);
//			endTime = DateUtils.setMinutes(endTime, 0);
//			endTime = DateUtils.setSeconds(endTime, 0);
//			endTime= DateUtils.addDays(endTime, 1);
			criteria.andSendTimeLessThanOrEqualTo(endTime);
		}
		if (orderType != null) {
			criteria.andOrderTypeEqualTo(orderType);
		}
		return orderManager.findByExample(example);
	}
	@Override
	public Page<Order> queryComplatedAll(Long userId, String plantNo, Pageable page) {
		OrderExample example = new OrderExample(page);
		
		example.setOrderByClause("status asc");
		
		Criteria criteria = example.createCriteria().andAcceptUserIdEqualTo(userId);
		if (plantNo != null) {
			criteria.andPlantNoEqualTo(plantNo);
		}
		criteria.andOperateIsNotNull();
		return orderManager.findByExample(example);
	}

	@Override
	public Page<Order> queryAcceptdAll(Long userId, String plantNo, Pageable page) {
		OrderExample example = new OrderExample(page);
		Criteria criteria = example.createCriteria().andAcceptUserIdEqualTo(userId);
		if (plantNo != null) {
			criteria.andPlantNoEqualTo(plantNo);
		}
		criteria.andStatusIn(Arrays.asList(Constants.Order.Status.ACCPEPTED, Constants.Order.Status.COMPLATE_TIMEOUT));
//		criteria.andStatusEqualTo(Constants.Order.Status.ACCPEPTED);
		return orderManager.findByExample(example);
	}
	@Override
	public Optional<Order> chageOrderStatus(Long orderId,Short status){
		Optional<Order> entity = orderManager.findById(orderId);
		
		return entity.map(order->{
			
			order.setStatus(status);
			
			orderManager.update(order);
			
			return order;
		});
		 
	}
	
	@Override
	public Optional<Order> allocate(Long userId, Order order) {
		
		if(order==null){
			return Optional.empty();
		}
		log.info("开始分配");
		//获取公司的组
		Optional<List<Group>> groups = groupManager.findByCompanyId(order.getCompanyId());
		
		 groups.map(_groups->{
			
			_groups.forEach(group->{
				//TODO 各种判断后
				boolean flag=true;
				if(flag){
					List<YwAreaGraph> graphs =ywAreaGraphManager.findByGroupIdAndAreaId(group.getId(),null);
					if(!CollectionUtils.isEmpty(graphs)){
						for(YwAreaGraph graph:graphs){
							boolean result = AreaUtil.in(graph.getGraphType(), graph.getPoints(), order.getLatitude(), order.getLongitude());
							if(result){
								log.info("找到了所属分组");
								//TODO 如果有合适的
								order.setGroupId(group.getId());
								
								Order entity =new Order();
								entity.setId(order.getId());
								entity.setGroupId(group.getId());
								entity.setStatus(Constants.Order.Status.SENDED);
								entity.setSendTime(new Date());
								entity.setSendUserId(0L);
								boolean updateResult = orderManager.update(entity);
								if(!updateResult){
									log.error("更新分组任务失败");
									
								}else{
									//分配完成后，创建工单超时未接的任务
									 syncService.buildOrderSendTimeoutSyncTimer(order.getCompanyId(), order.getId());
									 flag=true;
									 break;
								}
								
							}else{
								log.error("未找到所属分组");
							}
						}
					}
				}
				
			});
			return null;
		});
		
		 return Optional.of(order);
	}
	
	@Override
	@Transactional
	public synchronized Optional<OrderOperate> accept(Long userId, Order order) {
		 
		order = orderManager.findById(order.getId()).get();
		if(!(Constants.Order.Status.SENDED == order.getStatus() || Constants.Order.Status.ACCEPT_TIMEOUT == order.getStatus())){
			throw new RuntimeException("工单不存在，请选择其他车辆");
		}
		order.setStatus(Constants.Order.Status.ACCPEPTED);
		order.setAcceptUserId(userId);
		order.setAcceptTime(new Date());
		boolean result = orderManager.update(order);
		if(!result){
			return Optional.empty();
		}
		
		OrderOperate record =new OrderOperate();
		
		record.setId(IDUtils.getId());
		record.setOperateTime(new Date());
		record.setOperateUserId(String.valueOf(userId));
		record.setOrderNo(order.getOrderNo());
		record.setOperateType(Constants.OrderOperateType.ACCEPT);
		
		
		
		//record.setMemo("用户"+userId+"主动接单");
		 if(orderOperateMapper.insert(record)!=1){
			 return Optional.empty();
		 };
		 String code=null;
		 if(Constants.Order.OrderType.CLEAN == order.getOrderType()){
			 code="code3";
		 }else if(Constants.Order.OrderType.CHARGING == order.getOrderType()){
			 code="code2";
		 }else if(Constants.Order.OrderType.DISPATCH == order.getOrderType()){
			 code="code4";
		 }else{
			 code="code5";
		 }
		 
		 syncService.buildOrderOperateTimeoutSyncTimer(order.getCompanyId(), order.getId(), code);
		 return Optional.of(record);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Optional<OrderOperate> submit(Long userId, String description,Order order,Map<String,Map<String, String>> content) {
		order.setStatus(Constants.Order.Status.CONFIRM);
		order.setAcceptUserId(userId);
		order.setOperate(new Date());
		boolean result = orderManager.update(order);
		if(!result){
			return Optional.empty();
		}
		
		OrderOperate record =new OrderOperate();
		
		record.setId(IDUtils.getId());
		record.setOperateTime(new Date());
		record.setOperateUserId(String.valueOf(userId));
		record.setOrderNo(order.getOrderNo());
		record.setOperateType(Constants.OrderOperateType.SUBMIT);
		record.setMemo(description);
		 if(orderOperateMapper.insert(record)!=1){
			 return Optional.empty();
		 };
		 //设置检查项目
		 List<CheckList> cneckList=new ArrayList<>();
		 content.forEach((checkType,_content)->{
			 //_content [code1:1,code2:2]
			 CheckList cl = new CheckList();
			 cl.setId(IDUtils.getId());
			 
			 cl.setCheckType(checkType);
			 cl.setImages(_content.get("images"));
			 cl.setCheckTime(new Date());
			 cl.setCheckUserId(userId);
			 cl.setOrderId(order.getId());
			 cl.setCheckContent(JSON.toJSONString(_content));
			 cl.setOrderOperateId(record.getId());
			 cneckList.add(cl);
		 });
		 if(checkListService.save(cneckList)==null){
			 return Optional.empty();
		 }
		 return Optional.of(record);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Optional<OrderOperate> cancel(String userId,String userName,Order order, String content) {
		 
		order.setStatus(Constants.Order.Status.CANCEL);
		
		boolean result = orderManager.update(order);
		if(!result){
			return Optional.empty();
		}
		
		OrderOperate record =new OrderOperate();
		
		record.setId(IDUtils.getId());
		record.setOperateTime(new Date());
		record.setOperateUserId(userId);
		record.setOperateUserName(userName);
		
		record.setOrderNo(order.getOrderNo());
		record.setOperateType(Constants.OrderOperateType.CANCEL);
		record.setMemo(content);
		 if(orderOperateMapper.insert(record)==1){
			 return Optional.of(record);
		 };
		
		 return Optional.empty();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Optional<OrderOperate> recycle(String userId,String userName, Order order, String content) {
		order.setStatus(Constants.Order.Status.SENDED);
		order.setAcceptUserId(null);
		order.setAcceptTime(null);
		order.setOperate(null);
		boolean result = orderManager.updateAll(order);
		if(!result){
			return Optional.empty();
		}
		
		OrderOperate record =new OrderOperate();
		
		record.setId(IDUtils.getId());
		record.setOperateTime(new Date());
		record.setOperateUserId(userId);
		record.setOperateUserName(userName);
		record.setOrderNo(order.getOrderNo());
		record.setOperateType(Constants.OrderOperateType.RECYCLE);
		record.setMemo(content);
		 if(orderOperateMapper.insert(record)!=1){
			 return Optional.empty();
		 };
		//转派完成后，创建工单超时未接的任务
		 syncService.buildOrderSendTimeoutSyncTimer(order.getCompanyId(), order.getId());
		 return Optional.of(record);
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public Optional<OrderOperate> confirm(String userId,String userName, Order order, String content) {
		order.setStatus(Constants.Order.Status.COMPLATE);
		 
		boolean result = orderManager.update(order);
		if(!result){
			return Optional.empty();
		}
		
		OrderOperate record =new OrderOperate();
		
		record.setId(IDUtils.getId());
		record.setOperateTime(new Date());
		record.setOperateUserId(userId);
		record.setOperateUserName(userName);
		record.setOrderNo(order.getOrderNo());
		record.setOperateType(Constants.OrderOperateType.COMPALATE);
		record.setMemo(content);
		 if(orderOperateMapper.insert(record)!=1){
			 return Optional.empty();
			
		 };
		 
		 orderService.notityOrderComplete(order.getOrderNo());
		 
		 return Optional.of(record);
	}


	@Override
	public Optional<OrderOperate> evaluate(String userId,String userName, Order order, String star,String content) {
		order.setStatus(Constants.Order.Status.EVALUATE);
		order.setStarLevel(star);
		boolean result = orderManager.update(order);
		if(!result){
			return Optional.empty();
		}
		
		OrderOperate record =new OrderOperate();
		
		record.setId(IDUtils.getId());
		record.setOperateTime(new Date());
		record.setOperateUserId(userId);
		record.setOperateUserName(userName);
		record.setOrderNo(order.getOrderNo());
		record.setOperateType(Constants.OrderOperateType.EVALUATE);
		
		record.setMemo(content);
		 if(orderOperateMapper.insert(record)!=1){
			 return Optional.empty();
		 };
		 
		 //设置评价
		 CheckList cl = new CheckList();
		 cl.setId(IDUtils.getId());
		 
		 cl.setCheckType(Constants.CheckList.EVALUATE);
		 cl.setCheckTime(new Date());
		 cl.setCheckUserId(0L);
		 cl.setOrderId(order.getId());
		 
		 Map<String,String> memo =new HashMap<>();

		 memo.put("star", star);
		 memo.put("content", content);
			
		 cl.setCheckContent(JSON.toJSONString(memo));
		 cl.setOrderOperateId(record.getId());
		 
		 if(checkListService.save(cl)==null){
			 return Optional.empty();
		 }
		 return Optional.of(record);
	}

}
