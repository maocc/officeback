package cn.feezu.maint.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.feezu.maint.assign.manager.CheckListService;
import cn.feezu.maint.assign.manager.OrderOperateManager;
import cn.feezu.maintweb.MaintWeblication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaintWeblication.class)
public class OrderOperateManagerTests {

	@Autowired
	private OrderOperateManager rderOperateManager;
	@Autowired
	private CheckListService checkListService;

	public void testSubmit() {

	
	}
}
