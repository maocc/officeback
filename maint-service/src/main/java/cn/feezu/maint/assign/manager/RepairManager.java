package cn.feezu.maint.assign.manager;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.feezu.maint.assign.entity.OrderDTO;
import cn.feezu.maint.assign.entity.Repair;
import cn.feezu.maint.assign.entity.RepairExample;

/**
 * 报修管理
 * 
 * @author zhangfx
 *
 */
public interface RepairManager {

	Repair save(Repair entity);

	/**
	 * 查询
	 * 
	 * @param plantNo
	 * @param isCompleted
	 *            是否 已处理，未处理：false，是：true
	 * @param startTime
	 * @param endTime
	 * @param reportUserId
	 * @param page
	 * @return
	 */
	Page<Repair> queryAll(List<Long> groups,String plantNo, boolean isCompleted, Date startTime, Date endTime, Long reportUserId,
			Pageable page);

	Page<Repair> findByExample(RepairExample example);

	List<Repair> findAll(RepairExample example);
	
	int count(RepairExample example);
	
	Optional<Repair> findById(Long id);
	/**
	 * 关闭问题
	 * @param entity
	 * @param reason 原因
	 * @param closeUserId 关闭人员
	 * @return
	 */
	boolean close(Repair entity,String reason,String closeUserId,String closeUserName);
	/**
	 * 
	 * @param entity
	 * @param entityDTO
	 * @param checkUserId 点检员
	 * @return
	 */
	boolean createOrder(Repair entity,OrderDTO entityDTO,String checkUserId,String closeUserName);
	/**
	 * 
	 * @param id
	 * @param type
	 * @param orderNo
	 * @param reason
	 * @param checkUserId
	 * @param closeUserName
	 * @return
	 */
	boolean operate(Long id,short type,long orderNo,String reason, String checkUserId,String closeUserName);
}
