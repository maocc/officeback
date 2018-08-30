package cn.feezu.maint.assign.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CheckList implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 54327938839965965L;

	private Long id;

    @NotEmpty
    private String checkType;

    @Length(max=2000)
    private String checkContent;

    @Length(max=1200)
    private String images;

    private Date checkTime;

    private Long checkUserId;

    @NotEmpty
    private Long orderId;

    @NotEmpty
    private Long orderOperateId;


    /**
     * 檢查項目
     */
    private Map<String,Map<String,Object>> contents=null;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent == null ? null : checkContent.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Long getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderOperateId() {
        return orderOperateId;
    }

    public void setOrderOperateId(Long orderOperateId) {
        this.orderOperateId = orderOperateId;
    }

    public Map<String, Map<String, Object>> getContents() {
		return contents;
	}

	public void setContents(Map<String, Map<String, Object>> contents) {
		this.contents = contents;
	}

	public static class Builder {
        private CheckList obj;

        public Builder() {
            this.obj = new CheckList();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder checkType(String checkType) {
            obj.checkType = checkType;
            return this;
        }

        public Builder checkContent(String checkContent) {
            obj.checkContent = checkContent;
            return this;
        }

        public Builder images(String images) {
            obj.images = images;
            return this;
        }

        public Builder checkTime(Date checkTime) {
            obj.checkTime = checkTime;
            return this;
        }

        public Builder checkUserId(Long checkUserId) {
            obj.checkUserId = checkUserId;
            return this;
        }

        public Builder orderId(Long orderId) {
            obj.orderId = orderId;
            return this;
        }

        public Builder orderOperateId(Long orderOperateId) {
            obj.orderOperateId = orderOperateId;
            return this;
        }

        public CheckList build() {
            return this.obj;
        }
    }
}