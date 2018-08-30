package cn.feezu.maint.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.feezu.maint.entity.Dict;
import cn.feezu.maint.service.DictManager;

public class DictUtils {
	final static Logger log = LoggerFactory.getLogger(DictUtils.class);

	/**
	 * 
	 * 根据code类型获取相应值的列表
	 * 
	 * 
	 * 
	 * @param codeType
	 * 
	 * @return
	 * 
	 */
	public static Map<String, String> getCode(String codeType) {
		if (log.isDebugEnabled()) {
			log.debug("codeType:" + codeType);
		}
		return SpringContextUtils.getBean(DictManager.class).getDictMapByType(codeType);
	}

	/**
	 * 
	 * 获取多个字典表数据
	 * 
	 * @param codeTypes
	 * 
	 * @return
	 * 
	 */
	public static Map<String, Map<String, String>> getCodes(String... codeTypes) {
		if (log.isDebugEnabled()) {
			log.debug("codeType:" + codeTypes);
		}
		return SpringContextUtils.getBean(DictManager.class).getDictByTypes(codeTypes);
	}

	/**
	 * 
	 * 根据code查询获取相应的list
	 * 
	 */
	public static List<Dict> getList(String codeType) {
		if (log.isDebugEnabled()) {
			log.debug("codeType:" + codeType);
		}
		return SpringContextUtils.getBean(DictManager.class).getDictListByType(codeType);
	}

	/**
	 * 
	 * 根据类型取得唯一列表
	 * 
	 * @param codeType
	 * 
	 * @return
	 * 
	 */
	public static String getCodeByType(String codeType) {
		if (log.isDebugEnabled()) {
			log.debug("codeType:" + codeType);
		}
		Map<String, String> map = getCode(codeType);
		if (map.size() != 1) {
			return null;
		}
		return map.get(map.keySet().iterator().next());
	}

}
