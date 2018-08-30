package cn.feezu.maint.webservice.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.feezu.maint.authority.manager.MaintUserManager;
import cn.feezu.maintweb.MaintWeblication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaintWeblication.class)
public class WebServiceUtilsTest {
 
	@Autowired
	private MaintUserManager maintUserManager;
	@Test
	public void loginTests() {
		System.out.println(JSON.toJSONString(maintUserManager.maintianUserLogin("zhangfx", "Ywx*12345")));;
		System.out.println(JSON.toJSONString(maintUserManager.maintianUserLogin("zhangfx", "Ywx*12345")));;
		System.out.println(JSON.toJSONString(maintUserManager.maintianUserLogin("zhangfx", "Ywx*12345")));;
	 /*
		Result result = WebServiceUtils.login("https://www.feezu.cn", "zhangfx", "Ywx*12345");
		System.out.println(JSON.toJSONString(result));
		
		 
		UserDTO userDTO =  result.getReturnValue();
		System.out.println(JSON.toJSONString(userDTO));*/ 
	}

}
