package cn.feezu.maint.webservice.util;

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
public class DictUtilsTest {
	@Autowired
	private SettingsManager settingsManager;
	@Test
	public void testLoad() {
		System.out.println( DictUtils.getCode(Constants.Order.ORDER_TYPE));
		System.out.println("------------------+");
		System.out.println( DictUtils.getCode(Constants.Order.ORDER_TYPE));
		System.out.println("------------------+");
		
		System.out.println( DictUtils.getCode(Constants.Order.ORDER_TYPE));
		
		Map<String, String> orderType = DictUtils.getCode(Constants.Order.ORDER_TYPE);
		Short s=1;
		if (orderType.containsKey(String.valueOf(s))) {

			System.out.println(orderType.get(s));
		}
	}
	
	@Test
	public void testLoad2() {
		Optional<Settings>  settings = settingsManager.findByCompanyId("2");
		Optional<Map<String,String>> maps = 	settings.map(setting->{
			Map<String,Map<String,String>> content = JSON.parseObject(setting.getContent(), Map.class);
			Map<String,String> map = content.get(Constants.Order.OVER_TIME);
			return map;
		});
		System.out.println(maps);
	}
	
	@Test
	public void testCheckLists() {
		System.out.println( DictUtils.getCode(Constants.CheckList.VEHICLE_CONDITION));
		System.out.println( DictUtils.getCode(Constants.CheckList.CLEAR));
		System.out.println( DictUtils.getCode(Constants.CheckList.CHARGING_CONDITION));
		System.out.println( DictUtils.getCodes(Constants.CheckList.CLEAR,Constants.CheckList.VEHICLE_CONDITION,Constants.CheckList.CHARGING_CONDITION));
	}
}
