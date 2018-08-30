package cn.feezu.maint.service;

import java.util.List;
import java.util.Map;

import cn.feezu.maint.entity.Dict;

public interface DictManager {
	/**
	 * 
	 * 根据类型，查找
	 * 
	 * @param classfyType
	 * 
	 */
	public Map<String, String> getDictByType(String classfyType);

	public List<Dict> getDictListByType(String classfyType);

	public Map<String, String> getDictMapByType(String classfyType);

	/**
	 * 
	 * 根据多个类型，查找
	 * 
	 * @param classfyTypes
	 *            类型集合
	 * 
	 * @return
	 * 
	 */
	public Map<String, Map<String, String>> getDictByTypes(String... classfyTypes);

	public Dict findByDictCode(String dictCode);
	
	public Dict findByDictType(String dictType);

}
