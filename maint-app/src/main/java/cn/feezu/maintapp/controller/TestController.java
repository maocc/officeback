package cn.feezu.maintapp.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@Profile({"dev", "local"})//在生产环境不开启
public class TestController {
	@GetMapping("/")
	String home() {
		return "redirect:/swagger-ui.html";//TODO 测试
	}
}
