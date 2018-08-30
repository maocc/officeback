package cn.feezu.maintweb.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@Profile({"dev", "local"})//在生产环境不开启
public class TestController {
	
	@GetMapping("/test/baidu")
	String baidu() {
		return "baiduindex"; 
	}
	@GetMapping({"/swagger",""})
	String swagger() {
		return "redirect:/swagger-ui.html";// 测试
	}

	
}