package cn.feezu.maintapp.security;

import java.util.List;
import java.util.Optional;
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

import cn.feezu.common.util.Constants;
import cn.feezu.maint.authority.entity.Member;
import cn.feezu.maint.authority.manager.MemberManager;
import cn.feezu.security.UserNotActivatedException;
import cn.feezu.wzc.common.auth.security.JwtUser;

@Service
public class GateUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(GateUserDetailsService.class);

	@Autowired
	private MemberManager memberManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Authenticating {}", username);
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("用户名为空");
		}
		Optional<Member> member = memberManager.findByUserName(username);

		return member.map(user -> {
			if (user.getStatus() != Constants.Member.NOMARL) {
				throw new UserNotActivatedException("User " + username + " was not activated");
			}
			List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
					.map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
					.collect(Collectors.toList());
			return new JwtUser(user.getId(), user.getMobile(), user.getPassword(), grantedAuthorities,
					user.getLastPasswordResetDate());

		}).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the " + "database"));
	}
}
