package cn.feezu.maintweb.security;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.feezu.maint.authority.manager.MaintUserManager;
import cn.feezu.maint.vehicle.entity.MaintUserDTO;
import cn.feezu.maint.webservice.ws.authority.service.Result;
import cn.feezu.wzc.common.auth.security.JwtUser;

@Service
public class GateUserDetailsService implements UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(GateUserDetailsService.class);
	
	@Autowired
	private MaintUserManager maintUserManager;
  
  @Override
  public UserDetails loadUserByUsername(String usernameAndPAssword) throws UsernameNotFoundException {
	 String[] uap= usernameAndPAssword.split("###");
	 String username=uap[0];
	 String password=uap[1];
	  log.debug("Authenticating {}", username);
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("用户名为空");
		}
		Result result = maintUserManager.maintianUserLogin(username,password);
		
		if(!result.isSuccess()){
			 new UsernameNotFoundException(result.getErrors().get(0));
		}
		MaintUserDTO user = result.getMaintUserDTO();
		
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toList());
		return new JwtUser(user.getId(), user.getUsername(), user.getPassword(),
				grantedAuthorities, user.getLastPasswordResetDate());
  }
}
