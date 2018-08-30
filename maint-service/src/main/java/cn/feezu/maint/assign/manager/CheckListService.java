package cn.feezu.maint.assign.manager;

import java.util.List;
import java.util.Map;

import cn.feezu.maint.assign.entity.CheckList;
import cn.feezu.maint.assign.entity.SettingContent;

public interface CheckListService {

	/**
	 * 
	 * @param orderId
	 *            工单ID
	 * @param opId
	 *            工单流转ID
	 * @return Map<分类,Map<条目,Map<text/value,对应内容>>>
	 */
	public Map<String, Map<String, SettingContent>> findByOrderIdAndOpId(Long orderId, Long opId);

	CheckList save(CheckList entity);
	
	List<CheckList> save(List<CheckList> entities);
}
