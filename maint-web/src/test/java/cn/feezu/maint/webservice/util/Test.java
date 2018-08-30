package cn.feezu.maint.webservice.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.html.HtmlEscapers;

public class Test {

	public static void main(String[] args) {
		Map<String,Map<String, String>> content =new HashMap<>();
		
		Map<String, String> clear = new HashMap<>();
		clear.put("code1", "1");
		clear.put("code2", "1");
		clear.put("code3", "1");
		clear.put("code4", "1");
		clear.put("images", "1.jpg:2.jpg:3.jpg:4.jpg");
		
		content.put("clear", clear);
		
		Map<String, String> vehicle_condition = new HashMap<>();
		vehicle_condition.put("code1", "1");
		
		content.put("vehicle_condition", vehicle_condition);
		
		Map<String, String> charging_condition = new HashMap<>();
		charging_condition.put("code1", "1");
		
		content.put("charging_condition", charging_condition);
		
		System.out.println(JSON.toJSONString(content));
		//{"clear":{"code4":"1","code3":"1","code2":"1","images":"1.jpg:2.jpg:3.jpg:4.jpg","code1":"1"},"charging_condition":{"code1":"1"},"vehicle_condition":{"code1":"1"}}

		Map<String, String> cc = new HashMap<>();
		cc.put("power", "23%");
		System.out.println(JSON.toJSONString(cc));
		
		
		Map<String,Map<String, String>> a =new HashMap<>();
		
		Map<String, String> cc1 = new HashMap<>();
		cc.put("address", "北京市昌平去。。。。");
		cc.put("latitude", "40.057482");
		cc.put("longitude", "116.305671");
		a.put("targetLocation", cc);
		System.out.println(JSON.toJSONString(a));
		
		String s ="{&quot;power&quot;:&quot;57%&quot;}";
		s =s.replaceAll("&quot;", "\"");
		System.out.println(s);
		System.out.println(JSON.parseObject(s,Map.class));
		
		
		System.out.println("{&quot;center&quot;:{&quot;lng&quot;:116.292448,&quot;lat&quot;:40.058007},&quot;radius&quot;:1328.0699971549316}".replaceAll("&quot;", "\""));
		
	}
}
