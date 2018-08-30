package cn.feezu.maint.authority.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import cn.feezu.common.util.Constants;

public class Group implements Serializable {
    private Long id;

    private Integer groupNo;

    @Length(max=60)
    private String name;

    private Byte status = Constants.Group.NOMARL;

    private Date createTime;

    @Length(max=32)
    private String companyId;

    private static final long serialVersionUID = 1L;
    
    public Group() {
    	super();
    }
    
    public Group(String name, String companyId) {
    	super();
    	this.name = name;
    	this.companyId = companyId;
    	this.createTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public static class Builder {
        private Group obj;

        public Builder() {
            this.obj = new Group();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder groupNo(Integer groupNo) {
            obj.groupNo = groupNo;
            return this;
        }

        public Builder name(String name) {
            obj.name = name;
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

        public Builder companyId(String companyId) {
            obj.companyId = companyId;
            return this;
        }

        public Group build() {
            return this.obj;
        }
    }
}