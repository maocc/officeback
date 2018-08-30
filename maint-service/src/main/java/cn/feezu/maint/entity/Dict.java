package cn.feezu.maint.entity;

import java.io.Serializable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Dict implements Serializable {
    private Long dictId;

    @Length(max=50)
    @NotEmpty
    private String dictName;

    @Length(max=60)
    @NotEmpty
    private String dictCode;

    @Length(max=500)
    private String dictDesc;

    private Integer dictOrder;

    @Length(max=50)
    @NotEmpty
    private String dictType;

    @Length(max=2)
    private String dictStatus;

    private static final long serialVersionUID = 1L;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc == null ? null : dictDesc.trim();
    }

    public Integer getDictOrder() {
        return dictOrder;
    }

    public void setDictOrder(Integer dictOrder) {
        this.dictOrder = dictOrder;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    public String getDictStatus() {
        return dictStatus;
    }

    public void setDictStatus(String dictStatus) {
        this.dictStatus = dictStatus == null ? null : dictStatus.trim();
    }

    public static class Builder {
        private Dict obj;

        public Builder() {
            this.obj = new Dict();
        }

        public Builder dictId(Long dictId) {
            obj.dictId = dictId;
            return this;
        }

        public Builder dictName(String dictName) {
            obj.dictName = dictName;
            return this;
        }

        public Builder dictCode(String dictCode) {
            obj.dictCode = dictCode;
            return this;
        }

        public Builder dictDesc(String dictDesc) {
            obj.dictDesc = dictDesc;
            return this;
        }

        public Builder dictOrder(Integer dictOrder) {
            obj.dictOrder = dictOrder;
            return this;
        }

        public Builder dictType(String dictType) {
            obj.dictType = dictType;
            return this;
        }

        public Builder dictStatus(String dictStatus) {
            obj.dictStatus = dictStatus;
            return this;
        }

        public Dict build() {
            return this.obj;
        }
    }
}