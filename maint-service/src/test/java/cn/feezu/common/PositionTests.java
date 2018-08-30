package cn.feezu.common;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.DistanceUtil;
import cn.feezu.wzc.common.map.Coords;
import cn.feezu.wzc.common.map.Position;

public class PositionTests {
	public static void main(String[] args) {
		Coords coords = new Coords();
		coords.setLongitude("116.301");
		coords.setLatitude("40.057067");
		System.out.println(Position.baiduCoordsToAddress(coords));
		
		
	//	DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(order.getLatitude()), Double.parseDouble(order.getLongitude()))

		Map<String, String> cc = new HashMap<>();
		cc.put("power", "23%");
		System.out.println(JSON.toJSONString(cc));
		
		
		Map<String,Map<String, String>> a =new HashMap<>();
		
		Map<String, String> cc1 = new HashMap<>();
		cc1.put("address", "北京市海淀区软件园中科大洋大厦");
		cc1.put("latitude", "40.057482");
		cc1.put("longitude", "116.305671");
		a.put("targetLocation", cc);
		System.out.println(JSON.toJSONString(cc1));
		
	}
	
	
}
