package cn.feezu.maint.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import cn.feezu.maint.assign.entity.OrderDTO;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderExample;

public interface OrderManager {

	/**
	 * 
	 * @param entityDTO
	 * @param createUserId 创建人
	 * @param source 工单来源 0：报修派单
	 * @param latitude 经纬度
	 * @param longitude 经纬度
	 * @param groupId 接单运维组Id
	 * @return
	 */
	Order createOrder(OrderDTO entityDTO,String createUserId,String source,String latitude,String longitude,String address,Long groupId);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Optional<Order> findById(Long id);
	
	List<Order> selectByExample(OrderExample example);
	
	Page<Order> findByExample(OrderExample example);
	
	boolean createOrder(Order order);
	/**
	 * 
	 * @param orderNo
	 * @return
	 */
	Optional<Order> findByOrderNo(String orderNo);
	
	boolean update(Order order);
	
	boolean updateAll(Order order);
}
