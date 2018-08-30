package cn.feezu.maint.authority.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import cn.feezu.maint.vehicle.entity.MaintUserDTO;
import cn.feezu.maint.webservice.util.WebServiceUtils;
import cn.feezu.maint.webservice.ws.authority.service.Result;
import cn.feezu.maint.webservice.ws.authority.service.UserDTO;

/**
 * @author maocc
 * @description
 * @date: 2018/3/30 16:04
 */
@Service
public class MaintUserManagerImpl implements MaintUserManager {
	final static Logger log = LoggerFactory.getLogger(MaintUserManagerImpl.class);
	@Value("${webservice.host}")
	private String host;

	@Override
	@Cacheable(value = "authCache", key = "'maintianUserLogin'+#userAccount+'--'+#userPassword")
	public Result maintianUserLogin(String userAccount, String userPassword) {

		log.debug("access wzc 1.0");
		Result result = WebServiceUtils.login(host, userAccount, userPassword);
		
		if(!result.isSuccess()){
			return result;
		}
		
		UserDTO userDTO =  result.getReturnValue();
		
		List<GrantedAuthority> authoritiy =new ArrayList<>();
		authoritiy.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		authoritiy.add(new SimpleGrantedAuthority("ROLE_COMPANY_"+userDTO.getCompanyId()));
		
		MaintUserDTO user = new MaintUserDTO(1L, userDTO.getUserAccount()+"###"+ userPassword, userPassword, authoritiy, DateUtils.addDays(new Date(), -3));

		user.setCompanyId(userDTO.getCompanyId());
		user.setUserName(userDTO.getUserAccount());
		user.setUserId(userDTO.getId());
		result.setMaintUserDTO(user);
		return result;
	}
}
