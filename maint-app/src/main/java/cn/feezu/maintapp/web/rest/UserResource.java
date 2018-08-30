package cn.feezu.maintapp.web.rest;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.feezu.common.util.PasswordEncoder;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.manager.MemberManager;
import cn.feezu.maint.service.CacheService;
import cn.feezu.maintapp.web.dto.ResultDTO;
import cn.feezu.maintapp.web.dto.UserDTO;
import cn.feezu.security.SecurityUtils;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import cn.feezu.wzc.common.auth.security.JwtTokenUtil;
import cn.feezu.wzc.common.auth.security.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(tags = "用户服务", description = "提供用户登录，个人信息")
public class UserResource extends CoreResource{
	private static final String ENTITY_NAME = "login user";
	@Autowired
	private MemberManager memberManager;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
 
	@ApiOperation("用户登录")
	@PostMapping("/login")
	public ResponseEntity<ResultDTO> login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		if ("".equals(userName) || "".equals(password)) {

			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "请输入手机号！"),HttpStatus.UNAUTHORIZED);
		}
		Optional<Member> member = memberManager.findByUserName(userName);
	  
		 return  member.map(user -> {
			 long userId=user.getId();
			 int errorCount = cacheService.countByUserId(userId);
			 
			 if(errorCount>=5){
				 return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "您错误次数超过5次，请明天再登录！"), HttpStatus.UNAUTHORIZED);
			 }
			 
			if (!user.isEnabled()) {
				
				return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "账号已禁用，请联系管理员！"), HttpStatus.UNAUTHORIZED);
					
			}else if (!memberManager.login(user, password)) {
				cacheService.update(user.getId(), errorCount+1);
				return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "账号密码不匹配，请重新输入！"), HttpStatus.UNAUTHORIZED);
			}
			
			cacheService.delete(userId);
			Member user1 =new Member();
			user1.setId(userId);
			
			String token = jwtTokenUtil.generateToken(user);
			
			user1.setToken(token);
			user1.setTokenCreateTime(DateUtils.addSeconds(new Date(), -5));
			memberManager.update(user1);
			
			return  ResponseEntity.ok().headers(HeaderUtil.createAlert("login success", ENTITY_NAME)).body(new ResultDTO(token));
			
		}).orElse(badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "手机号不存在！"),HttpStatus.UNAUTHORIZED)); 
	}
	
	@ApiOperation("用户登出")
	@GetMapping("/logout")
	public ResponseEntity<ResultDTO> logout() {
		JwtUser userDetails = (JwtUser) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		String token = jwtTokenUtil.generateToken(userDetails);
		 
		SecurityUtils.getCurrentUserLogin()
        .flatMap(memberManager::findByUserName)
        .ifPresent(user -> {
         
        	user.setToken(token);
        	user.setTokenCreateTime(new Date());
            memberManager.update(user);
        });
		
		return  ResponseEntity.ok().headers(HeaderUtil.createAlert("logout success", "user")).body(new ResultDTO(true));
	}
	
	@ApiOperation("用户信息")
	@GetMapping("/profile")
	public ResponseEntity<UserDTO> profile() {
		JwtUser userDetails = (JwtUser) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		
		Optional<Member> member = memberManager.findByUserName(userDetails.getUsername());
		
		String groupName=getGroup().getName();
		
		UserDTO userDTO = new UserDTO(userDetails.getId(), member.get().getName(), userDetails.getAuthorities(),groupName);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("query success", "user")).body(userDTO);
	}
	
	@ApiOperation("修改密码")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "password",  dataType = "string", required = false, value = "旧密码"),
		@ApiImplicitParam(paramType = "query", name = "newPassword", dataType = "string", required = false, value = "新密码"),
		})
	@PostMapping("/modify-password")
	public ResponseEntity<ResultDTO> modifyPassword(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
		
		return SecurityUtils.getCurrentUserLogin()
        .flatMap(memberManager::findByUserName).map(user->{
			
			if(!PasswordEncoder.encodePassword(password, user.getId()).equals(user.getPassword())){
				return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "旧密码不正确"),HttpStatus.BAD_REQUEST);
        	}
			 String encryptedPassword = PasswordEncoder.encodePassword(newPassword, user.getId());
             user.setPassword(encryptedPassword);
             memberManager.changePassword(user);
             
             return  ResponseEntity.ok().headers(HeaderUtil.createAlert("修改成功", "success")).body(new ResultDTO(true));
		}).orElse(badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "message", "修改失败"),HttpStatus.BAD_REQUEST));
		
	}
	
}

