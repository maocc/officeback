package cn.feezu.maint.assign.entity;

import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

public class Settings implements Serializable {
    private Long id;

    @Length(max=36)
    private String companyId;

    @Length(max=120)
    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public static class Builder {
        private Settings obj;

        public Builder() {
            this.obj = new Settings();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder companyId(String companyId) {
            obj.companyId = companyId;
            return this;
        }

        public Builder content(String content) {
            obj.content = content;
            return this;
        }

        public Settings build() {
            return this.obj;
        }
    }
}