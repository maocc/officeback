package cn.feezu.maint.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.feezu.common.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description="订单详情")
public class Order implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4965821951971245023L;

	private Long id;

    @Length(max=200)
    private String memo;

    @Length(max=30)
    private String orderNo;

    @NotEmpty
    private Short orderType;

    @NotEmpty
    private Short source;

    @Length(max=10)
    @NotEmpty
    private String plantNo;

    @ApiModelProperty(value="创建时间")
    @NotEmpty
    private Date createTime;
    @ApiModelProperty(value="创建人")
    private String createId;
    @ApiModelProperty(value="创建名称")
    @Length(max=32)
    private String createName;

    @ApiModelProperty(value="派单人")
    private Long sendUserId;
    @ApiModelProperty(value="派单时间")
    private Date sendTime;
    @ApiModelProperty(value="接单人")
    private Long acceptUserId;
    @ApiModelProperty(value="接单时间")
    private Date acceptTime;
    @ApiModelProperty(value="运维结束时间")
    private Date operate;
    @ApiModelProperty(value="操作类型"
    		+ "\n\t待处理：新生成:"+Constants.Order.Status.CREATE
    		+ "\n\t待处理：派单中:"+Constants.Order.Status.SENDING
    		+ "\n\t待处理：待接单:"+Constants.Order.Status.SENDED
    		+ "\n\t处理中：已接单:"+Constants.Order.Status.ACCPEPTED
    		+ "\n\t待确认：已报单:"+Constants.Order.Status.CONFIRM
    		+ "\n\t待评价：已完成:"+Constants.Order.Status.COMPLATE
    		+ "\n\t已取消:"+Constants.Order.Status.CANCEL
    		+ "\n\t已评价:"+Constants.Order.Status.EVALUATE
    		+ "\n\t超时未接:"+Constants.Order.Status.ACCEPT_TIMEOUT
    		+ "\n\t超时未完成:"+Constants.Order.Status.COMPLATE_TIMEOUT
    		,allowableValues="range[0, 8]")
    @NotEmpty
    private Short status;

    @Length(max=10)
    private String latitude;

    @Length(max=10)
    private String longitude;

    @Length(max=2000)
    private String content;

    @Length(max=36)
    private String companyId;

    private Long groupId;

    @Length(max=3000)
    private String address;
    
    @ApiModelProperty(value="创建工单时间")
    private String createTimeString;
    
    @ApiModelProperty(value="订单类型名称")
    private String orderTypeName;//订单类型名称
    @ApiModelProperty(value="派单时间展示")
    private String sendTimeString;//派单时间字符
    @ApiModelProperty(value="接单时间展示")
    private String acceptTimeString;//接单时间字符
    @ApiModelProperty(value="结单时间展示")
    private String operateTimeString;//结单时间字符
    @ApiModelProperty(value="运维人员名称")
    private String acceptUserName;//运维人员
    @ApiModelProperty(value="订单状态名称")
    private String statusName;//订单状态
    
    private Double distance;//距离
    
    @ApiModelProperty(value="接单时长")
    private String sendToacceptTime;//接单时长
    
    @ApiModelProperty(value="运维时长")
    private String operateTime;//运维时长
    @ApiModelProperty(value="超时时长")
    private String timeout;//超时时长
    @ApiModelProperty(value="扩展信息（根据工单类型不同）")
    private Map<String,Object> extendContent;

    @Length(max=4)
    private String starLevel;
    
    @ApiModelProperty(value="确认结单人姓名")
    private String finishUserName;
    @ApiModelProperty(value="确认结单时间")
    private String finishTimeString;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    public Short getSource() {
        return source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public String getPlantNo() {
        return plantNo;
    }

    public void setPlantNo(String plantNo) {
        this.plantNo = plantNo == null ? null : plantNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Long getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(Long acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getOperate() {
        return operate;
    }

    public void setOperate(Date operate) {
        this.operate = operate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
    
    public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getSendTimeString() {
		return sendTimeString;
	}

	public void setSendTimeString(String sendTimeString) {
		this.sendTimeString = sendTimeString;
	}

	public String getAcceptTimeString() {
		return acceptTimeString;
	}

	public void setAcceptTimeString(String acceptTimeString) {
		this.acceptTimeString = acceptTimeString;
	}
	public String getOperateTimeString() {
		return operateTimeString;
	}

	public void setOperateTimeString(String operateTimeString) {
		this.operateTimeString = operateTimeString;
	}

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getSendToacceptTime() {
		return sendToacceptTime;
	}

	public void setSendToacceptTime(String sendToacceptTime) {
		this.sendToacceptTime = sendToacceptTime;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
    public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}


	public Map<String, Object> getExtendContent() {
		return extendContent;
	}

	public void setExtendContent(Map<String, Object> extendContent) {
		this.extendContent = extendContent;
	}
    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel == null ? null : starLevel.trim();
    }

    public String getFinishUserName() {
		return finishUserName;
	}

	public void setFinishUserName(String finishUserName) {
		this.finishUserName = finishUserName;
	}

	public String getFinishTimeString() {
		return finishTimeString;
	}

	public void setFinishTimeString(String finishTimeString) {
		this.finishTimeString = finishTimeString;
	}

	public static class Builder {
        private Order obj;

        public Builder() {
            this.obj = new Order();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder memo(String memo) {
            obj.memo = memo;
            return this;
        }

        public Builder orderNo(String orderNo) {
            obj.orderNo = orderNo;
            return this;
        }

        public Builder orderType(Short orderType) {
            obj.orderType = orderType;
            return this;
        }

        public Builder source(Short source) {
            obj.source = source;
            return this;
        }

        public Builder plantNo(String plantNo) {
            obj.plantNo = plantNo;
            return this;
        }

        public Builder createTime(Date createTime) {
            obj.createTime = createTime;
            return this;
        }

        public Builder createId(String createId) {
            obj.createId = createId;
            return this;
        }

        public Builder createName(String createName) {
            obj.createName = createName;
            return this;
        }

        public Builder sendUserId(Long sendUserId) {
            obj.sendUserId = sendUserId;
            return this;
        }

        public Builder sendTime(Date sendTime) {
            obj.sendTime = sendTime;
            return this;
        }

        public Builder acceptUserId(Long acceptUserId) {
            obj.acceptUserId = acceptUserId;
            return this;
        }

        public Builder acceptTime(Date acceptTime) {
            obj.acceptTime = acceptTime;
            return this;
        }

        public Builder operate(Date operate) {
            obj.operate = operate;
            return this;
        }

        public Builder status(Short status) {
            obj.status = status;
            return this;
        }

        public Builder latitude(String latitude) {
            obj.latitude = latitude;
            return this;
        }

        public Builder longitude(String longitude) {
            obj.longitude = longitude;
            return this;
        }

        public Builder content(String content) {
            obj.content = content;
            return this;
        }

        public Builder companyId(String companyId) {
            obj.companyId = companyId;
            return this;
        }

        public Builder groupId(Long groupId) {
            obj.groupId = groupId;
            return this;
        }

        public Builder address(String address) {
            obj.address = address;
            return this;
        }

        public Builder starLevel(String starLevel) {
            obj.starLevel = starLevel;
            return this;
        }

        public Order build() {
            return this.obj;
        }
    }
}