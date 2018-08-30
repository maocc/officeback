package cn.feezu.maintweb.web.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.feezu.common.util.BeanUtil;
import cn.feezu.common.util.Constants;
import cn.feezu.common.util.IDUtils;
import cn.feezu.common.util.PasswordEncoder;
import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.GroupExample;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.entity.MemberExample;
import cn.feezu.maint.authority.entity.MemberExample.Criteria;
import cn.feezu.maint.authority.manager.GroupManager;
import cn.feezu.maint.authority.manager.MaintUserManager;
import cn.feezu.maint.authority.manager.MemberManager;
import cn.feezu.maint.order.entity.Order;
import cn.feezu.maint.order.entity.OrderExample;
import cn.feezu.maint.service.CacheService;
import cn.feezu.maint.service.OrderManager;
import cn.feezu.maint.vehicle.entity.MaintUserDTO;
import cn.feezu.maint.webservice.ws.authority.service.Result;
import cn.feezu.maintweb.web.dto.UserDTO;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.PaginationUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import cn.feezu.wzc.common.auth.security.JwtTokenUtil;
import cn.feezu.wzc.common.auth.security.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/web-api")
@Api(tags = "用户服务", description = "提供用户登录，个人信息")
public class UserResource extends CoreResource{
	private static final String ENTITY_NAME = "login user";
	@Autowired
	private MemberManager memberManager;
	
	@Autowired
	private OrderManager orderManager;

	@Autowired
	private GroupManager groupManager;

	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private MaintUserManager maintUserManager;

	@Value("${webservice.host}")
	private String host;

	@ApiOperation("用户登录")
	@PostMapping("/login")
	ResponseEntity<String> login(@RequestParam String userName, @RequestParam String password) {
		if ("".equals(userName) || "".equals(password)) {

			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "用户名或密码不能为空"), HttpStatus.UNAUTHORIZED);
		}
		Result result = maintUserManager.maintianUserLogin(userName, password);
	
		if(!result.isSuccess()){
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", result.getErrors().get(0)),HttpStatus.UNAUTHORIZED);
		}
		MaintUserDTO userDTO =result.getMaintUserDTO();
		
		
		String token = jwtTokenUtil.generateToken(userDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.add("feezu-alert", "login success");
		headers.add("feezu-params", ENTITY_NAME);
		headers.add("role", "ROLE_ADMIN");
		
		return  ResponseEntity.ok().headers(headers).body(token);
		
 
	}

	@ApiOperation("登录用户个人信息")
	@GetMapping("/profile")
	public ResponseEntity<UserDTO> profile() {
		JwtUser userDetails = (JwtUser) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		UserDTO userDTO = new UserDTO(userDetails.getId(), userDetails.getUsername().split("###")[0], userDetails.getAuthorities());
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("query success", "user")).body(userDTO);
	}

	@ApiOperation("运维人员新增")
	@PostMapping("/user/save")
	public ResponseEntity<Map<String, Object>> save(Member member, @RequestParam("passwordAgain") String passwordAgain) {
		String companyId = getCompanyId();

		//校验参数
		if(StringUtils.isBlank(member.getName())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维人员名称不能为空")).body(null);
		}

		member.setName(member.getName().trim());
		if(member.getName().length() > 20){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维人员名称不能超过20个字符")).body(null);
		}

		if(member.getSex()>2){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "性别不正确")).body(null);
		}

		if(StringUtils.isBlank(member.getMobile()) || member.getMobile().trim().length() != 11){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "手机号码不正确")).body(null);
		}
		member.setMobile(member.getMobile().trim());

		if(StringUtils.isBlank(member.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "密码不能为空")).body(null);
		}

		member.setPassword(member.getPassword().trim());
		if(!member.getPassword().equals(passwordAgain)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "密码和确认密码一样")).body(null);
		}

		if(member.getGroupId()<=0){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "请选择运维组")).body(null);
		}

		//判断运维组是否存在
		Group group = groupManager.selectByPrimaryKey(member.getGroupId());
		if(group == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组不存在")).body(null);
		}

		//判断该运维组是否是该公司下的， 并且组状态是正常状态
		if(!group.getCompanyId().equals(companyId) || group.getStatus() != 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "选择的运维组不合法")).body(null);
		}

		member.setId(IDUtils.getId());
		member.setPassword(PasswordEncoder.encodePassword(member.getPassword(), member.getId()));
		member.setGroupName(group.getName());
		member.setCreateTime(new Date());
		member.setUpdateTime(new Date());
		member.setStatus(Constants.Member.NOMARL);

		MemberExample example = new MemberExample();
		example.createCriteria().andMobileEqualTo(member.getMobile()).andStatusNotEqualTo(Constants.Member.DELETE);

		List<Member> members = memberManager.selectByExample(example);
		if(CollectionUtils.isNotEmpty(members)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "该手机号已存在")).body(null);
		}

		boolean result = memberManager.save(member);

		return ResponseEntity.ok().headers(HeaderUtil.createAlert("新增成功", "success")).body(results("result", result));
	}

	@ApiOperation("运维人员修改")
	@PostMapping("/user/update")
	public ResponseEntity<Map<String, Object>> update(Member member) {
		String companyId = getCompanyId();

		//校验参数
		if(member.getId()<=0){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "参数不正确")).body(null);
		}

		if(StringUtils.isBlank(member.getName())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维人员名称不能为空")).body(null);
		}

		member.setName(member.getName().trim());
		if(member.getName().length() > 20){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维人员名称不能超过20个字符")).body(null);
		}

		if(member.getSex()>2){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "性别不正确")).body(null);
		}

		if(StringUtils.isBlank(member.getMobile()) || member.getMobile().trim().length() != 11){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "手机号码不正确")).body(null);
		}
		member.setMobile(member.getMobile().trim());

		if(member.getGroupId()<=0){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "请选择运维组")).body(null);
		}

		//判断运维组是否存在
		Group group = groupManager.selectByPrimaryKey(member.getGroupId());
		if(group == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组不存在")).body(null);
		}

		//判断该运维组是否是该公司下的， 并且组状态是正常状态
		if(!group.getCompanyId().equals(companyId) || group.getStatus() != 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "选择的运维组不合法")).body(null);
		}

		member.setGroupName(group.getName());
		member.setUpdateTime(new Date());

		MemberExample example = new MemberExample();
		example.createCriteria().andMobileEqualTo(member.getMobile()).andIdNotEqualTo(member.getId()).andStatusNotEqualTo(Constants.Member.DELETE);

		List<Member> members = memberManager.selectByExample(example);
		if(CollectionUtils.isNotEmpty(members)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "该手机号已存在")).body(null);
		}

		boolean result = memberManager.update(member);

		return ResponseEntity.ok().headers(HeaderUtil.createAlert("更新成功", "success")).body(results("result", result));
	}

	@ApiOperation("运维人员改变状态")
	@PostMapping("/user/change-status")
	public ResponseEntity<Map<String, Object>> changeStatus(@RequestParam("id") Long id, @RequestParam("status") Byte status) {
		String companyId = getCompanyId();

		//校验参数
		Member member = memberManager.selectByPrimaryKey(id);
		if(member == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维人员不存在")).body(null);
		}

		//判断运维组是否存在，并检查组状态是否正常
		Group group = groupManager.selectByPrimaryKey(member.getGroupId());
		if(group == null || group.getStatus() != 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组异常")).body(null);
		}

		//判断该运维组是否是该公司下的
		if(!group.getCompanyId().equals(companyId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "权限不足，请联系管理员")).body(null);
		}

		if(Constants.Member.DELETE == status) {
			//查询是否有未处理的订单
			OrderExample example = new OrderExample();
			example.createCriteria().andStatusIn(Arrays.asList(Constants.Order.Status.ACCPEPTED,Constants.Order.Status.COMPLATE_TIMEOUT)).andAcceptUserIdEqualTo(member.getId());
			
			List<Order> orders = orderManager.selectByExample(example);
			if(CollectionUtils.isNotEmpty(orders)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "此账号有未处理完的订单，无法删除")).body(null);
			}
		}

		member.setStatus(status);
		member.setUpdateTime(new Date());
		member.setTokenCreateTime(new Date());
		
		boolean result = memberManager.update(member);

		return ResponseEntity.ok().headers(HeaderUtil.createAlert("改变状态成功", "success")).body(results("result", result));
	}

	@ApiOperation("运维人员修改密码")
	@PostMapping("/user/modify-password")
	public ResponseEntity<Map<String, Object>> modifyPassword(@RequestParam("id") Long id, 
			@RequestParam("newPassword") String newPassword, @RequestParam("newPasswordAgain") String newPasswordAgain) {
		String companyId = getCompanyId();

		//校验参数
		if(StringUtils.isBlank(newPassword)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "新密码不能为空")).body(null);
		}

		if(!newPassword.trim().equals(newPasswordAgain)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "新密码和确认密码不一样")).body(null);
		}

		Member member = memberManager.selectByPrimaryKey(id);
		if(member == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维人员不存在")).body(null);
		}

		/*if(!member.getPassword().equals(PasswordEncoder.encodePassword(password, member.getId()))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "原密码不正确")).body(null);
		}
*/
		//判断运维组是否存在，并检查组状态是否正常
		Group group = groupManager.selectByPrimaryKey(member.getGroupId());
		if(group == null || group.getStatus() != 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "运维组异常")).body(null);
		}

		//判断该运维组是否是该公司下的
		if(!group.getCompanyId().equals(companyId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "权限不足，请联系管理员")).body(null);
		}

		member.setPassword(PasswordEncoder.encodePassword(newPassword.trim(), member.getId()));
		member.setUpdateTime(new Date());

		boolean result = memberManager.update(member);

		cacheService.delete(id);//删除密码错误超限
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("密码修改成功", "success")).body(results("result", result));
	}

	@ApiOperation("运维人员分页列表")
	@GetMapping("/user/page")
	public ResponseEntity<List<Member>> page(Pageable pageable, Member member) {
		String companyId = getCompanyId();

		GroupExample groupExample = new GroupExample();
		groupExample.createCriteria().andCompanyIdEqualTo(companyId);
		List<Group> groups = groupManager.selectByExample(groupExample);
		if(CollectionUtils.isEmpty(groups)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "还没有运维组")).body(null);
		}

		List<Long> groupIds = groups.stream().map(Group::getId).collect(Collectors.toList());
		 

		MemberExample example = new MemberExample(pageable);
		Criteria criteria = example.createCriteria();

		criteria
		.andGroupIdIn(groupIds)
		.andStatusNotEqualTo(Constants.Member.DELETE);

		if(member!=null && member.getGroupId()!=null) {
			criteria.andGroupIdEqualTo(member.getGroupId());
		}

		if(member!=null && member.getStatus() != null) {
			criteria.andStatusEqualTo(member.getStatus());
		}

		if(member!=null && StringUtils.isNotBlank(member.getName())) {
			criteria.andNameLike(member.getName().trim());
		}

		if(member!=null && StringUtils.isNotBlank(member.getMobile())) {
			criteria.andMobileLike(member.getMobile().trim());
		}

		Page<Member> page = memberManager.findByExample(example);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/web-api/user/page");
		return new ResponseEntity<List<Member>>(page.getContent(), headers, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation("运维人员id和名称对应关系")
	@GetMapping("/user")
	public ResponseEntity<Map> list(){
		String companyId = getCompanyId();

		GroupExample groupExample = new GroupExample();
		groupExample.createCriteria().andCompanyIdEqualTo(companyId);
		List<Group> groups = groupManager.selectByExample(groupExample);
		if(CollectionUtils.isEmpty(groups)) {
			Map<String, Object> result = new HashMap<>();
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "success")).body(result);
		}

		List<Long> groupIds = groups.stream().map(Group::getId).collect(Collectors.toList());
		 

		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();

		criteria.andGroupIdIn(groupIds).andStatusNotEqualTo(Constants.Member.DELETE);

		
		Page<Member> page = memberManager.findByExample(example);

		if(page ==null || org.springframework.util.CollectionUtils.isEmpty(page.getContent())){
			
			Map<String, Object> result = new HashMap<>();
			return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "success")).body(result);
		}
		Map<Long, String> users = BeanUtil.convert(page.getContent(),"id" , "name", Long.class, String.class);
		
		
		Map<String,String> userResult =new HashMap<>();
		
		users.forEach((k,v)->userResult.put(String.valueOf(k),v ));
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "success")).body(userResult);
	}

}
