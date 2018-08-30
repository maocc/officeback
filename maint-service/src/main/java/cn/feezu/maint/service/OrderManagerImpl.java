package cn.feezu.maint.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.feezu.common.util.Constants;
import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.assign.entity.OrderDTO;
import cn.feezu.maint.job.scheduler.SyncService;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderExample;
import cn.feezu.maint.order.entity.OrderOperate;
import cn.feezu.maint.order.mapper.OrderMapper;
import cn.feezu.maint.order.mapper.OrderOperateMapper;

@Service
public class OrderManagerImpl implements OrderManager {
	final static Logger logger = LoggerFactory.getLogger(OrderManagerImpl.class);
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private SyncService syncService;
	@Autowired
	private OrderOperateMapper orderOperateMapper;
	@Override
	@Transactional
	public Order createOrder(OrderDTO entityDTO, String createUserId, String source, String latitude, String longitude,
			String address, Long groupId) {

		Order order = new Order();

		order.setId(IDUtils.getId());

		order.setMemo(entityDTO.getMeno());
		String orderNo = null;
		order.setOrderNo(orderNo);
		order.setOrderType(Short.valueOf(entityDTO.getOrderType()));
		if (Constants.Order.FROM_REPAIR.equals(source)) {

			order.setSource(Short.valueOf(Constants.Order.FROM_REPAIR));
		}
		order.setAddress(address);
		order.setPlantNo(entityDTO.getPlantNo());
		order.setCreateTime(new Date());
		order.setCreateId(createUserId);
		order.setStatus(Constants.Order.Status.CREATE);
		order.setLatitude(latitude);
		order.setLongitude(longitude);
		// TODO 未完成
		if (orderMapper.insertSelective(order) != 1) {
			return null;
		}
		return order;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Order> findById(Long id) {
		return Optional.ofNullable(orderMapper.selectByPrimaryKey(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Order> selectByExample(OrderExample example) {
		return orderMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Order> findByExample(OrderExample example) {
		example.setOrderByClause("id desc");
		return new PageImpl<>(findAll(example), example.getPageable(), count(example));
	}

	private List<Order> findAll(OrderExample example) {
		return orderMapper.selectByExample(example);
	}

	private int count(OrderExample example) {
		return orderMapper.countByExample(example);
	}

	@Override
	@Transactional
	public boolean createOrder(Order order) {
		boolean result = orderMapper.insert(order) > 0;
		//TODO 创建完成订单之后，进行派单动作
		
		OrderOperate record =new OrderOperate();
		
		record.setId(IDUtils.getId());
		record.setOperateTime(new Date());
		record.setOperateUserId(order.getCreateId());
		record.setOperateUserName(order.getCreateName());
		record.setOrderNo(order.getOrderNo());
		record.setOperateType(Constants.OrderOperateType.CREATE);
		
		record.setMemo(order.getMemo());
		result = orderOperateMapper.insert(record)==1;
		
		//尝试接单超时设置
		logger.info("工单创建后分配到车辆所在组 start...");
		syncService.buildOrderSendToGroupSyncTimer(order.getCompanyId(), order.getId());
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Order> findByOrderNo(String orderNo) {
		OrderExample example = new OrderExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		List<Order> entities = orderMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(entities)) {
			return Optional.empty();
		}
		return Optional.of(entities.get(0));
	}

	@Override
	@Transactional
	public boolean update(Order order) {
		return orderMapper.updateByPrimaryKeySelective(order) == 1;
	}
	@Override
	public boolean updateAll(Order order) {
		return orderMapper.updateByPrimaryKey(order) == 1;
	}
}
