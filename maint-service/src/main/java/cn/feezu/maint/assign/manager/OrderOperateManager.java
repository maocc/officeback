package cn.feezu.maint.assign.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.feezu.maint.assign.entity.OrderDetail;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderOperate;

/**
 * 订单处理
 * 
 * @author zhangfx
 *
 */
public interface OrderOperateManager {

	/**
	 * 查询工单详细信息
	 * 
	 * @param id
	 * @return
	 */
	Optional<OrderDetail> findById(Long id);
	/**
	 * 查询工单详细信息
	 * @param orderNo
	 * @return
	 */
	Optional<OrderDetail> findByOrderNo(String orderNo);
	/**
	 * 查询指定用户是否存在未完成的工单
	 * @param userId
	 * @return
	 */
	Optional<List<Order>> findExistOrderByUserId(Long userId);

	/**
	 * 已完成订单
	 * @param orderNo
	 * @param operateUserId
	 * @param plantNo
	 * @param startTime
	 * @param endTime
	 * @param orderType
	 * @param orderStatus
	 * @param page
	 * @return
	 */
	Page<Order> queryComplateAll(String companyId, Long groupId,String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus, Pageable page);
	
	/**
	 * 未完成订单
	 * @param orderNo
	 * @param operateUserId
	 * @param plantNo
	 * @param startTime
	 * @param endTime
	 * @param orderType
	 * @param orderStatus
	 * @param page
	 * @return
	 */
	Page<Order> queryOperatIngAll(String companyId, Long groupId,String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType, Short orderStatus, Pageable page);
	/**
	 * 超时未接
	 * @param companyId
	 * @param groupId
	 * @param orderNo
	 * @param operateUserId
	 * @param plantNo
	 * @param startTime
	 * @param endTime
	 * @param orderType
	 * @param orderStatus
	 * @param page
	 * @return
	 */
	Page<Order> queryAcceptTimeoutAll(String companyId, Long groupId,String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType,Pageable page);
	/**
	 * 超时未完成
	 * @param companyId
	 * @param groupId
	 * @param orderNo
	 * @param operateUserId
	 * @param plantNo
	 * @param startTime
	 * @param endTime
	 * @param orderType
	 * @param page
	 * @return
	 */
	Page<Order> queryComplateTimeoutAll(String companyId, Long groupId,String orderNo, Long operateUserId, String plantNo, Date startTime, Date endTime,
			Short orderType,Pageable page);
	/**
	 * 查询个人已完成工单
	 * @param userId
	 * @param page
	 * @return
	 */
	Page<Order> queryComplatedAll(Long userId,String plantNo, Pageable page);
	/**
	 * 查询个人已接单处理中
	 * @param userId
	 * @param plantNo
	 * @param page
	 * @return
	 */
	Page<Order> queryAcceptdAll(Long userId, String plantNo, Pageable page);
	
	/**
	 * 改变订单状态-定时任务
	 * @param orderId
	 * @param status
	 * @return
	 */
	Optional<Order> chageOrderStatus(Long orderId,Short status);
	
	/**
	 * 分配工单到组
	 * @param userId
	 * @param order
	 * @return
	 */
	Optional<Order> allocate(Long userId, Order order);
	
	/*
	 * 接单
	 */
	Optional<OrderOperate> accept(Long userId, Order order);
	
	/**
	 * 报单
	 * @param userId
	 * @param order
	 * @return
	 */
	Optional<OrderOperate> submit(Long userId,String description, Order order,Map<String,Map<String, String>> content);
	/**
	 * 取消订单
	 * @param detail
	 * @param content
	 * @return
	 */
	Optional<OrderOperate> cancel(String userId,String userName,Order order,String content);
	/**
	 * 工单转派
	 * @param userId
	 * @param order
	 * @param content
	 * @return
	 */
	Optional<OrderOperate> recycle(String userId,String userName,Order order,String content);
	
	
	/**
	 * 管理员工单确认
	 * @param userId
	 * @param order
	 * @param content
	 * @return
	 */
	Optional<OrderOperate> confirm(String userId,String userName,Order order,String content);
	
	/**
	 * 评价工单
	 * @param userId
	 * @param star 评价
	 * @param order
	 * @param content
	 * @return
	 */
	Optional<OrderOperate> evaluate(String userId,String userName,Order order,String star,String content);
	
}
