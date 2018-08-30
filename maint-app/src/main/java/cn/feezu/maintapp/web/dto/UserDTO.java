package cn.feezu.maintapp.web.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String userName;
	private Collection<? extends GrantedAuthority> role;
	
	private String groupName;

}
