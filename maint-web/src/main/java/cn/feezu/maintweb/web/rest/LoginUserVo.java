package cn.feezu.maintweb.web.rest;

/**
 * @author maocc
 * @description
 * @date: 2018/4/2 10:56
 */
public class LoginUserVo {

	private String userId;//用户ID

	private String userName;//用户姓名

	private String userAccount;//登录账户

	private String companyId;//所属公司ID

	private String companyName;//所属公司名称

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
