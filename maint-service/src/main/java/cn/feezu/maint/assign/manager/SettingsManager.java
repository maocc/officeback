package cn.feezu.maint.assign.manager;

import java.util.Optional;

import cn.feezu.maint.assign.entity.Settings;

public interface SettingsManager {

	/**
	 * 保存公司设置
	 * @param companyId
	 * @param content Map<String,Map<String, String> json格式
	 * @return
	 */
	boolean config(String companyId,String configType,String content);

	/**
	 * 获取公司配置信息
	 * @param companyId
	 * @return
	 */
	Optional<Settings> findByCompanyId(String companyId);
}
