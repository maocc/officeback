package cn.feezu.maintweb.web.rest;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.feezu.common.util.Constants;
import cn.feezu.common.util.IDUtils;
import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.GroupExample;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.entity.MemberExample;
import cn.feezu.maint.authority.entity.YwMaintArea;
import cn.feezu.maint.authority.manager.GroupManager;
import cn.feezu.maint.authority.manager.MaintAreaManager;
import cn.feezu.maint.authority.manager.MemberManager;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderExample;
import cn.feezu.maint.service.OrderManager;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/web-api/group")
@Api(tags = "运维组服务", description = "提供运维组操作")
public class GroupResource extends CoreResource{
	
	private static final String ENTITY_NAME = "user";

	@Autowired
	private GroupManager groupManager;
	
	@Autowired
	private MemberManager memberManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private MaintAreaManager maintAreaManager;
	
	@ApiOperation("运维组新增")
	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> save(@RequestParam("name") String name) {
		String companyId = getCompanyId();
		if(StringUtils.isBlank(name)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组名称不能为空")).body(null);
		}
		
		GroupExample example = new GroupExample();
		example.createCriteria().andCompanyIdEqualTo(companyId).andNameEqualTo(name).andStatusEqualTo(Constants.Group.NOMARL);
		
		List<Group> groups = groupManager.selectByExample(example);
		if(CollectionUtils.isNotEmpty(groups)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "此运维组已存在")).body(null);
		}
		
		Group group = new Group(name, companyId);
		group.setId(IDUtils.getId());
		
		int count = groupManager.save(group);
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("新增成功", "success")).body(results("result", count>0));
	}
	
	@ApiOperation("运维组修改")
	@PostMapping("/update")
	public ResponseEntity<Map<String, Object>> update(@RequestParam("id") Long id, @RequestParam("name") String name) {
		String companyId = getCompanyId();
		if(StringUtils.isBlank(name)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组名称不能为空")).body(null);
		}
		
		name = name.trim();
		//运维组不存在
		Group group = groupManager.selectByPrimaryKey(id);
		if(group == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组不存在")).body(null);
		}
		
		//判断运维组是不是该公司下的
		if(!companyId.equals(group.getCompanyId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "权限不足，请联系管理员")).body(null);
		}
		
		GroupExample example = new GroupExample();
		example.createCriteria().andCompanyIdEqualTo(companyId).andNameEqualTo(name).andStatusEqualTo(Constants.Group.NOMARL).andIdNotEqualTo(group.getId());
		
		List<Group> groups = groupManager.selectByExample(example);
		if(CollectionUtils.isNotEmpty(groups)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组名称已存在")).body(null);
		}
		
		group.setName(name);
		
		int count = groupManager.update(group);
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("更新成功", "success")).body(results("result", count>0));
	}
	
	@ApiOperation("运维组删除")
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> delete(@RequestParam("id") Long id) {
		String companyId = getCompanyId();
		
		//运维组不存在
		Group group = groupManager.selectByPrimaryKey(id);
		if(group == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组不存在")).body(null);
		}
		
		//判断运维组是不是该公司下的
		if(!companyId.equals(group.getCompanyId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "权限不足，请联系管理员")).body(null);
		}
		
		//有运维人员
		MemberExample example = new MemberExample();
		example.createCriteria().andGroupIdEqualTo(group.getId()).andStatusNotEqualTo(Constants.Member.DELETE);
		
		List<Member> members = memberManager.selectByExample(example);
		if(CollectionUtils.isNotEmpty(members)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "该运维组下还有运维人员")).body(null);
		}
		
		//还需要判断该运维组下是否有未处理的工单
		OrderExample orderExample = new OrderExample();
		orderExample.createCriteria().andGroupIdEqualTo(group.getId()).andStatusEqualTo(Constants.Order.Status.SENDED);
		
		List<Order> orders = orderManager.selectByExample(orderExample);
		if(CollectionUtils.isNotEmpty(orders)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "该运维组下还有未处理的工单")).body(null);
		}
		
		
		//判断运维组下有没有运维区域
		YwMaintArea area = new YwMaintArea();
		area.setGroupId(group.getId());
		List<YwMaintArea> areas = maintAreaManager.findYwMaintAreaList(area);
		if(CollectionUtils.isNotEmpty(areas)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "该运维组下还有关联区域")).body(null);
		}
		
		group.setStatus(Constants.Group.DELETE);
		int count = groupManager.update(group);
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除成功", "success")).body(results("result", count>0));
	}
	
	@ApiOperation("运维组列表")
	@GetMapping("/list")
	public ResponseEntity<List<Group>> list() {
		String companyId = getCompanyId();
		
		GroupExample example = new GroupExample();
		example.createCriteria().andCompanyIdEqualTo(companyId).andStatusEqualTo(Constants.Group.NOMARL);
		
		List<Group> groups = groupManager.selectByExample(example);
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "success")).body(groups);
	}
}
