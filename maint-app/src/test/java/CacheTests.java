import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import cn.feezu.common.util.Constants;
import cn.feezu.maint.assign.entity.Settings;
import cn.feezu.maint.assign.manager.SettingsManager;
import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.YwAreaGraph;
import cn.feezu.maint.authority.manager.GroupManager;
import cn.feezu.maint.authority.manager.YwAreaGraphManager;
import cn.feezu.maint.job.OrderSendTimeoutSyncTimer;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.service.CacheService;
import cn.feezu.maint.service.OrderManager;
import cn.feezu.maint.util.AreaUtil;
import cn.feezu.maintapp.MaintApplication;

 
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaintApplication.class)
public class CacheTests {
	
	private static final Logger log = LoggerFactory.getLogger(CacheTests.class);
	@Resource
	private CacheService testService;
	@Autowired
	private GroupManager groupManager;
	
	@Autowired
	private YwAreaGraphManager ywAreaGraphManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private SettingsManager settingsManager;
	@Test
	public void testCache() {
		int domovalue1 =testService.countByUserId(1L);
		System.out.println("第一次查询，值："+domovalue1);
		
		int domovalue2=testService.countByUserId(1L);
		System.out.println("第二次查询，值："+domovalue2);

		int domovalue11 =testService.update(1L, domovalue2+1);
		System.out.println("第1次更新，值："+domovalue11);
		
		int domovalue3=testService.countByUserId(1L);
		System.out.println("第3次查询，值："+domovalue3);
		
		 testService.deleteAll();
		int domovalue4=testService.countByUserId(1L);
		System.out.println("第4次查询，值："+domovalue4);
	}
	@Test
	public void testCc(){
		/*OrderExample example = new OrderExample();
		Page<Order> orderList = orderManager.findByExample(example);
		for(Order o:orderList){
		
		}
*/
		getSettings("com_10001dgjp3jl", "code1").map(time -> {

			// 任务名称
			String name = OrderSendTimeoutSyncTimer.class.getName() + UUID.randomUUID().toString();

			// 秒杀开始时间(X 分钟后)
			long startTime = System.currentTimeMillis() + 1000 * 60 * Long.parseLong(time);

			 System.out.println(Long.parseLong(time)+"----------------"+startTime);
			return null;
		});
		
	}
	
	private Optional<String> getSettings(String companyId, String code) {

		Optional<Settings> settings = settingsManager.findByCompanyId(companyId);
		Optional<Map<String, String>> maps = settings.map(setting -> {
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> content = JSON.parseObject(setting.getContent(), Map.class);
			return content.get(Constants.Order.OVER_TIME);
		});

		return maps.map(map -> {
			if (map.containsKey(code)) {
				return map.get(code);
			}
			return null;
		});

	}
	@Test
	public void testOrderId(){
		
		
		orderManager.findById(3856157538829312L).map(order->{
        	

			//获取公司的组
					Optional<List<Group>> groups = groupManager.findByCompanyId(order.getCompanyId());
					
					 groups.map(_groups->{
						
						_groups.forEach(group->{
							//TODO 各种判断后
							if(group.getId().equals(3854890296230912L)){
								System.out.println("找到了组");
							}
							System.out.println("_________________________"+group.getId());
							List<YwAreaGraph> graphs =ywAreaGraphManager.findByGroupIdAndAreaId(group.getId(),null);
							if(!CollectionUtils.isEmpty(graphs)){
								for(YwAreaGraph graph:graphs){
									boolean result = AreaUtil.in(graph.getGraphType(), graph.getPoints(), order.getLatitude(), order.getLongitude());
									
									if(!result){
										log.error("==============================未找到区域");
										
									}else{
										log.info("==============================找到区域成功");
									}
									if(result){
										//TODO 如果有合适的
										order.setGroupId(group.getId());
										
										Order entity =new Order();
										entity.setId(order.getId());
										entity.setGroupId(group.getId());
										entity.setStatus(Constants.Order.Status.SENDED);
										 boolean updateResult = orderManager.update(entity);
										if(!updateResult){
											log.error("==============================更新分组任务失败");
											
										}else{
											log.info("==============================更新分组任务成功");
											break;
										} 
										 
									}
								}
							}
							
						});
						return null;
					});
					 return null;
        });
	}
}
