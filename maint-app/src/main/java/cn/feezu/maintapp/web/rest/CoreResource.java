package cn.feezu.maintapp.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.manager.GroupManager;
import cn.feezu.maintapp.web.dto.ResultDTO;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import cn.feezu.wzc.common.auth.security.JwtUser;

public class CoreResource {
	@Autowired
	private GroupManager groupManager;
	 
	 ResponseEntity<ResultDTO> badResponse(HttpHeaders headers ,HttpStatus status){
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

		Group group = getGroup();
		if (group == null) {
			return null;
		}
		return group.getCompanyId();
	}

	Group getGroup(){
		Long groupId = getGroupId();
		if (groupId == null) {
			return null;
		}

		Group group = groupManager.selectByPrimaryKey(groupId);
		return group;
	}
	/**
	 * 获取运维组ID
	 * 
	 * @return
	 */
	Long getGroupId() {
		String groupId = null;

		for (GrantedAuthority entity : gerUserInfo().getAuthorities()) {
			if (entity.getAuthority().startsWith("ROLE_GROUP_")) {
				groupId = entity.getAuthority().replaceAll("ROLE_GROUP_", "");
				break;
			}
		}
		if (groupId == null) {
			return null;
		}
		return Long.parseLong(groupId);
	}
}
