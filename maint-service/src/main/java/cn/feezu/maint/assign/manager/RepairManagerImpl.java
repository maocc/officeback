package cn.feezu.maint.assign.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.OrderDTO;
import cn.feezu.maint.assign.entity.Repair;
import cn.feezu.maint.assign.entity.RepairExample;
import cn.feezu.maint.assign.entity.RepairExample.Criteria;
import cn.feezu.maint.assign.mapper.RepairMapper;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.service.OrderManager;

@Service
public class RepairManagerImpl implements RepairManager {

	@Autowired
	private RepairMapper mapper;
	@Autowired
	private OrderManager orderManager;

	@Override
	public Repair save(Repair record) {
		mapper.insertSelective(record);
		return record;
	}

	@Override
	public Page<Repair> queryAll(List<Long> groups,String plantNo, boolean isCompleted, Date startTime, Date endTime, Long reportUserId,
			Pageable page) {
		RepairExample example = new RepairExample(page);
		Criteria criteria = example.createCriteria();

		if(!CollectionUtils.isEmpty(groups)){
			criteria.andGroupIdIn(groups);
		}
		
		if (StringUtils.isNotBlank(plantNo)) {
			criteria.andPlateNoLike("%"+plantNo+"%");
		}
		if (reportUserId != null) {
			criteria.andReportUserIdEqualTo(reportUserId);
		}
		 
		if (startTime != null) {
//			startTime = DateUtils.setHours(startTime, 0);
//			startTime = DateUtils.setMinutes(startTime, 0);
//			startTime = DateUtils.setSeconds(startTime, 0);
			criteria.andCreateTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
//			endTime = DateUtils.setHours(endTime, 0);
//			endTime = DateUtils.setMinutes(endTime, 0);
//			endTime = DateUtils.setSeconds(endTime, 0);
//			endTime= DateUtils.addDays(endTime, 1);
			criteria.andCreateTimeLessThanOrEqualTo(endTime);
		}
		if (isCompleted) {
			List<Short> value = new ArrayList<>();
			value.add(Constants.Repair.CLOSED);
			value.add(Constants.Repair.CREATE_ORDER);
			criteria.andStatusIn(value);
		}else{
			criteria.andStatusEqualTo(Constants.Repair.CREATE);
		}
		
		return findByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Repair> findByExample(RepairExample example) {
		example.setOrderByClause("id desc");
		return new PageImpl<>(findAll(example), example.getPageable(), count(example));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Repair> findAll(RepairExample example) {
		return mapper.selectByExample(example);
		
	}

	@Transactional(readOnly = true)
	@Override
	public int count(RepairExample example) {
		return mapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Repair> findById(Long id) {
		return Optional.ofNullable(mapper.selectByPrimaryKey(id));
	}

	@Override
	public boolean close(Repair entity, String reason, String closeUserId,String closeUserName) {
		Repair record = new Repair();
		record.setId(entity.getId());
		record.setClosedTime(new Date());
		record.setMemo(reason);
		record.setCheckUserId(closeUserId);
		record.setCheckUserName(closeUserName);
		record.setStatus(Constants.Repair.CLOSED);
		return mapper.updateByPrimaryKeySelective(record) == 1;
	}

	@Override
	public boolean operate(Long id,short type,long orderNo,String reason, String checkUserId,String checkUserName){
		Repair record = new Repair();
		record.setId(id);
		record.setCheckUserId(checkUserId);
		record.setCheckUserName(checkUserName);
		record.setClosedTime(new Date());
		record.setStatus(type);
		record.setMemo(reason);
		record.setOrderId(orderNo);
		return mapper.updateByPrimaryKeySelective(record) == 1;
	}
	@Override
	@Transactional
	public boolean createOrder(Repair entity, OrderDTO entityDTO,String checkUserId,String checkUserName) {
		Repair record = new Repair();
		record.setId(entity.getId());
		record.setCheckUserId(checkUserId);
		record.setCheckUserName(checkUserName);
		record.setClosedTime(new Date());
		record.setStatus(Constants.Repair.CREATE_ORDER);
		
		Order order = orderManager.createOrder(entityDTO, checkUserId.toString(), Constants.Order.FROM_REPAIR, entity.getLatitude(),
				entity.getLongitude(),entity.getAddress(), entity.getGroupId());

		record.setOrderId(order.getId());
		return mapper.updateByPrimaryKeySelective(record) == 1;
	}
}
