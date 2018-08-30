package cn.feezu.maint.authority.manager;

import cn.feezu.maint.authority.entity.YwMaintArea;

import java.util.List;
import java.util.Optional;

public interface MaintAreaManager {

	boolean save(YwMaintArea maintArea);

	boolean update(YwMaintArea maintArea);

	public boolean deleteYwMaintArea(Long maintAreaId);

	public List<YwMaintArea> findYwMaintAreaList(YwMaintArea maintArea);
	
	public List<YwMaintArea> findByGroupId(Long groupId);
	
	/**
	 * 根据ID查询区域去腥
	 * @param id
	 * @return
	 */
	Optional<YwMaintArea> findById(Long id);
	
	/**
	 * 删除区域及图形
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);
	/**
	 * 查询区域名称时候存在
	 * @param areaName
	 * @return
	 */
	boolean exists(String areaName,List<Long> getGroupIds);
	
	List<YwMaintArea> findByCompanyId(String companyId, String name);
	
	List<YwMaintArea> findByGroupIds(List<Long> groupIds, String name);
}
