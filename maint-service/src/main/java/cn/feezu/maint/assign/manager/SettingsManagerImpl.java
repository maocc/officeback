package cn.feezu.maint.assign.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.assign.entity.Settings;
import cn.feezu.maint.assign.entity.SettingsExample;
import cn.feezu.maint.assign.mapper.SettingsMapper;

/**
 * 相关设置
 * 
 * @author zhangfx
 *
 */
@Service
public class SettingsManagerImpl implements SettingsManager {

	@Autowired
	private SettingsMapper mapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean config(String companyId,String configType, String content) {
		 
		if(!Constants.Order.OVER_TIME.equals(configType)){
			return false;
		}
		
		Settings record = new Settings();
		record.setCompanyId(companyId);
		
		Optional<Settings> entity =findByCompanyId(companyId);
		
		if(entity.isPresent()){
			//TODO 应该合并，
			Settings settings = entity.get();
			record.setId(settings.getId());
			Map<String,Map<String, String>> _content=JSON.parseObject(settings.getContent(), Map.class);
			
			_content.put(Constants.Order.OVER_TIME, JSON.parseObject(content, Map.class));
			
			record.setContent(JSON.toJSONString(_content));
			return mapper.updateByPrimaryKey(record) == 1;
		}else{
			record.setId(IDUtils.getId());
			Map<String,Map<String, String>> _content=new HashMap<>();
			
			_content.put(configType, JSON.parseObject(content, Map.class));
			record.setContent(JSON.toJSONString(_content));
			return mapper.insert(record) == 1;
		}

		
	}
	@Override
	public Optional<Settings> findByCompanyId(String companyId){
		
		SettingsExample example =new SettingsExample();
		example.createCriteria().andCompanyIdEqualTo(companyId);
		 
		List<Settings> results = mapper.selectByExample(example);
		 
		if(results==null || results.isEmpty()){
			Settings record = new Settings();
			record.setId(IDUtils.getId());
			record.setCompanyId(companyId);
			Map<String,Map<String, String>> _content=new HashMap<>();
			
			Map<String, String> content =new HashMap<>();
			content.put("code1","10");
			content.put("code2","120");
			content.put("code3","120");
			content.put("code4","120");
			content.put("code5","120");
			
			_content.put(Constants.Order.OVER_TIME,content);
			record.setContent(JSON.toJSONString(_content));
			 mapper.insert(record);
			 return Optional.ofNullable(record);
		}
		 return Optional.ofNullable(results.get(0));
	}
	
}
