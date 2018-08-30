package cn.feezu.maint.vehicle.entity;

import cn.feezu.wzc.common.auth.security.JwtUser;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author maocc
 * @description
 * @date: 2018/3/30 15:55
 */
public class MaintUserDTO extends JwtUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2746775384596305342L;

	private String userId;//用户ID

	private String userName;//用户姓名

	private String userAccount;//登录账户

	private String companyId;//所属公司ID

	private String companyName;//所属公司名称

	public MaintUserDTO() {
		super(null, null, null, null, null);
	}
	public MaintUserDTO(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Date lastPasswordResetDate) {
		super(id, username, password, authorities, lastPasswordResetDate);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
