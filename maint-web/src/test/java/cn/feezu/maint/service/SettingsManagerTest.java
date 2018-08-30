package cn.feezu.maint.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.Settings;
import cn.feezu.maint.assign.manager.SettingsManager;
import cn.feezu.maint.util.DictUtils;
import cn.feezu.maintweb.MaintWeblication;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaintWeblication.class)
public class SettingsManagerTest {
	@Autowired
	private SettingsManager settingsManager;
	
	private final static String SETTINGS_VALUE = "value";
	Map<String, Object> results(String key, Object value) {
		Map<String, Object> result = new HashMap<>();
		result.put(key, value);
		return result;
	}

	Map<String, Object> resultsKeyAndValue(String key, Object value) {
		Map<String, Object> result = new HashMap<>();
		result.put("text", key);
		result.put(SETTINGS_VALUE, value);
		return result;
	}
	@Test
	public void testSelect(){
		Map<String, Map<String, Object>> value=new HashMap<>();
		DictUtils.getCode(Constants.ORDER_OVER_TIME).forEach((k,v)->{
			value.put(k, resultsKeyAndValue(v, ""));
		});
		System.out.println("value=============="+value);
		String companyId="1";//公司ID
		Optional<Settings> entity = settingsManager.findByCompanyId(companyId);
		
		System.out.println("entity=============="+JSON.toJSONString(entity));
		if(entity.isPresent()){
			 
			Settings settings = entity.get();
			@SuppressWarnings("unchecked")
			Map<String,Map<String, String>> _content=JSON.parseObject(settings.getContent(), Map.class);
			if(_content.containsKey(Constants.Order.OVER_TIME)){
				Map<String, String> kv = _content.get(Constants.Order.OVER_TIME);
				kv.forEach((k,v)->{
					value.get(k).put(SETTINGS_VALUE, v);
				});
			}
		}
		
		Map<String, Map<String,Map<String, Object>>> result = new HashMap<>();
		result.put(Constants.Order.OVER_TIME, value);
		System.out.println("--------"+result);
	}
}
