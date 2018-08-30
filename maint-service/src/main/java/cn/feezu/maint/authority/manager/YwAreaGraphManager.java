package cn.feezu.maint.authority.manager;

import cn.feezu.maint.authority.entity.YwAreaGraph;

import java.util.List;

/**
 * @author maocc
 * @description
 * @date: 2018/4/4 10:33
 */
public interface YwAreaGraphManager {
	/**
	 * 保存图形
	 * @param ywAreaGraph
	 * @return
	 */
	public boolean saveYwAreaGraph(YwAreaGraph ywAreaGraph);
	
	public boolean saveYwAreaGraph(List<YwAreaGraph> ywAreaGraphs);

	/**
	 * 保存区域名称及区域图形
	 * @param name 区域名称
	 * @param groupId 所属运维组
	 * @param graphType 图形类型
	 * @param points 图形点
	 * @return
	 */
	boolean saveYwAreaGraph(String name, Long groupId,Byte graphType,String points);
	/**
	 * 更新区域名称及区域图形
	 * @param id 区域Id
	 * @param name 区域名称
	 * @param groupId 所属运维组
	 * @param graphType 图形类型
	 * @param points 图形点
	 * @return
	 */
	boolean updateYwAreaGraph(Long id,String name, Long groupId,Byte graphType,String points);
	/**
	 * 根据主键删除区域图形信息
	 * @param ywAreaGraphId
	 * @return
	 */
	public boolean deleteYwAreaGraphById(Long ywAreaGraphId);

	/**
	 *根据条件删除区域图形信息
	 * @param ywAreaGraph
	 * @return
	 */
	public boolean deleteYwAreaGraph(YwAreaGraph ywAreaGraph);

	/**
	 *更新区域图形信息
	 * @param ywAreaGraph
	 * @return
	 */
	public boolean updateYwAreaGraph(YwAreaGraph ywAreaGraph);

	/**
	 *查询区域图形信息
	 * @param ywAreaGraph
	 * @return
	 */
	public List<YwAreaGraph> findYwAreaGraphList(YwAreaGraph ywAreaGraph);
	/**
	 * 查询
	 * @param groupId
	 * @return
	 */
	public List<YwAreaGraph> findByGroupIdAndAreaId(Long groupId,Long areaId);
	/**
	 * 删除
	 * @param areaId
	 * @return
	 */
	public boolean deleteByAreaId(Long areaId);
	
	List<YwAreaGraph> findByAreaId(Long areaId);

}
