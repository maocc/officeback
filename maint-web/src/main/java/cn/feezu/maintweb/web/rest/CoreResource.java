package cn.feezu.maintweb.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.GroupExample;
import cn.feezu.maint.authority.manager.GroupManager;
import cn.feezu.maint.authority.manager.MaintUserManager;
import cn.feezu.maint.vehicle.entity.MaintUserDTO;
import cn.feezu.maint.webservice.ws.authority.service.Result;
import cn.feezu.maintweb.web.dto.UserDTO;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import cn.feezu.wzc.common.auth.security.JwtUser;

public class CoreResource {

	@Autowired
	private GroupManager groupManager;

	@Autowired
	private MaintUserManager maintUserManager;

	ResponseEntity<Map<String, Object>> notFound = ResponseUtil
			.badResponse(HeaderUtil.createFailureAlert("repair", "message", "条目不存在"), HttpStatus.NOT_FOUND);

	ResponseEntity<Map<String, Object>> returnNotFound(HttpHeaders headers) {
		return badResponse(headers, HttpStatus.NOT_FOUND);
	}
	
	ResponseEntity<Map<String, Object>> badResponse(HttpHeaders headers ,HttpStatus status){
		return ResponseUtil.badResponse(headers, status);
	}

	/**
	 * 获取当前登录用户信息
	 *
	 * @return
	 */
	JwtUser gerUserInfo() {
		JwtUser userDetails = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
	}
 
	MaintUserDTO getUserDTO() {
		JwtUser userDetails =gerUserInfo();
		
		Result result = maintUserManager.maintianUserLogin(userDetails.getUsername().split("###")[0], userDetails.getUsername().split("###")[1]);
		
		if(!result.isSuccess()){
			 return null;
		}
		return result.getMaintUserDTO();
	}
	Map<String, Object> results(String key, Object value) {
		Map<String, Object> result = new HashMap<>();
		result.put(key, value);
		return result;
	}

	/**
	 * 获取公司ID
	 *
	 * @return
	 */
	String getCompanyId() {
		for (GrantedAuthority entity : gerUserInfo().getAuthorities()) {
			if (entity.getAuthority().startsWith("ROLE_COMPANY_")) {
				return entity.getAuthority().replaceAll("ROLE_COMPANY_", "");
			}
		}
		return null;
	}
	/**
	 * 获取当前登录账号的分组Id集合
	 * @return
	 */
	List<Long> getGroupIds(){
		GroupExample example =new GroupExample();
		example.createCriteria().andCompanyIdEqualTo(getCompanyId());
		return groupManager.selectByExample(example).stream().map(Group::getId).collect(Collectors.toList());
	}

}
