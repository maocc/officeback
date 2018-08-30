package cn.feezu.maint.authority.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cn.feezu.common.util.Constants;
import cn.feezu.wzc.common.auth.security.JwtUser;
import io.swagger.annotations.ApiModelProperty;

public class Member extends JwtUser implements Serializable {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -6090297375813194886L;

	private Long id;

    private Integer memberNo;

    @Length(max=20)
    private String name;

    @ApiModelProperty(value="性别：1男2女")
    private Byte sex;

    @Length(max=11)
    private String mobile;

    @Length(max=36)
    private String password;

    @NotEmpty
    private Long groupId;

    @Length(max=30)
    private String groupName;
    @ApiModelProperty(value="状态"
    		+ "\t\n 正常： "+Constants.Member.NOMARL
    		+ "\t\n 禁用："+Constants.Member.DISABLED
    		+ "\t\n 删除："+Constants.Member.DELETE
    		)
    private Byte status;

    private Date createTime;

    private Date updateTime;

    @Length(max=256)
    private String token;

    private Date tokenCreateTime;
    
    
    public Member(){
    	super(null, null, null, null, null);
    }
    
	public Member(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities,
			Date lastPasswordResetDate) {
		super(id, username, password, authorities, lastPasswordResetDate);
		// TODO Auto-generated constructor stub
	}
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getTokenCreateTime() {
        return tokenCreateTime;
    }

    public void setTokenCreateTime(Date tokenCreateTime) {
        this.tokenCreateTime = tokenCreateTime;
        
        setLastPasswordResetDate(tokenCreateTime);
    }
   
   
    public static class Builder {
        private Member obj;

        public Builder() {
            this.obj = new Member();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder memberNo(Integer memberNo) {
            obj.memberNo = memberNo;
            return this;
        }

        public Builder name(String name) {
            obj.name = name;
            return this;
        }

        public Builder sex(Byte sex) {
            obj.sex = sex;
            return this;
        }

        public Builder mobile(String mobile) {
            obj.mobile = mobile;
            return this;
        }

        public Builder password(String password) {
            obj.password = password;
            return this;
        }

        public Builder groupId(Long groupId) {
            obj.groupId = groupId;
            return this;
        }

        public Builder groupName(String groupName) {
            obj.groupName = groupName;
            return this;
        }

        public Builder status(Byte status) {
            obj.status = status;
            return this;
        }

        public Builder createTime(Date createTime) {
            obj.createTime = createTime;
            return this;
        }

        public Builder updateTime(Date updateTime) {
            obj.updateTime = updateTime;
            return this;
        }

        public Builder token(String token) {
            obj.token = token;
            return this;
        }

        public Builder tokenCreateTime(Date tokenCreateTime) {
            obj.tokenCreateTime = tokenCreateTime;
            return this;
        }

        public Member build() {
            return this.obj;
        }
    }
	@Override
	public String getUsername() {
		 
		return getMobile();
	}

	@Override
	public boolean isEnabled() {
		return getStatus() == Constants.Member.NOMARL;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities =new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_GROUP_"+getGroupId()));
		return grantedAuthorities;
	}
}