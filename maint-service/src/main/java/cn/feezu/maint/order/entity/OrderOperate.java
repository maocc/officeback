package cn.feezu.maint.order.entity;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description="工单流转")
public class OrderOperate implements Serializable {
    @NotEmpty
    private Long id;

    @Length(max=30)
    @NotEmpty
    private String orderNo;

    @ApiModelProperty(value="操作类型"
    		+ "\n\t0:创建"
    		+ "\n\t1:取消"
    		+ "\n\t2:创建"
    		+ "\n\t3:完成"
    		+ "\n\t4:转派",allowableValues="range[0, 4]")
    @NotEmpty
    private Short operateType;

    private String operateUserId;

    private Date operateTime;
    @ApiModelProperty(value="操作人姓名")
    private String operateUserName;
    @ApiModelProperty(value="操作时间")
    private String operateTimeString;
    @Length(max=200)
    private String memo;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Short getOperateType() {
        return operateType;
    }

    public void setOperateType(Short operateType) {
        this.operateType = operateType;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public String getOperateTimeString() {
		return operateTimeString;
	}

	public void setOperateTimeString(String operateTimeString) {
		this.operateTimeString = operateTimeString;
	}

	public static class Builder {
        private OrderOperate obj;

        public Builder() {
            this.obj = new OrderOperate();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder orderNo(String orderNo) {
            obj.orderNo = orderNo;
            return this;
        }

        public Builder operateType(Short operateType) {
            obj.operateType = operateType;
            return this;
        }

        public Builder operateUserId(String operateUserId) {
            obj.operateUserId = operateUserId;
            return this;
        }

        public Builder operateTime(Date operateTime) {
            obj.operateTime = operateTime;
            return this;
        }

        public Builder memo(String memo) {
            obj.memo = memo;
            return this;
        }

        public OrderOperate build() {
            return this.obj;
        }
    }
}