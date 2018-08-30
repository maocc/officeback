package cn.feezu.maint.assign.entity;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Repair implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4975113082636482716L;

	private Long id;

    @Length(max=10)
    @NotEmpty
    private String plateNo;

    @Length(max=60)
    private String address;

    @Length(max=20)
    private String latitude;

    @Length(max=20)
    private String longitude;

    @Length(max=2000)
    private String content;

    @NotEmpty
    private Long reportUserId;

    @Length(max=1200)
    private String images;

    private Date createTime;

    @NotEmpty
    private Short status;

    private String checkUserId;

    private Date closedTime;

    @Length(max=10)
    private String memo;

    private Long groupId;

    private Long orderId;


    private String reportUserName;//报障人员
    private String createTimeString;//报障时间
    private String statusName;//状态显示
    
    private String checkUserName;//点检员名字
    /**
     * 图片路径
     */
    private String[] imageList;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo == null ? null : plateNo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public Long getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Long reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

   

	public String[] getImageList() {
		return imageList;
	}

	public void setImageList(String[] imageList) {
		this.imageList = imageList;
	}

	public String getReportUserName() {
		return reportUserName;
	}

	public void setReportUserName(String reportUserName) {
		this.reportUserName = reportUserName;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}



	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}



	public static class Builder {
        private Repair obj;

        public Builder() {
            this.obj = new Repair();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder plateNo(String plateNo) {
            obj.plateNo = plateNo;
            return this;
        }

        public Builder address(String address) {
            obj.address = address;
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

        public Builder reportUserId(Long reportUserId) {
            obj.reportUserId = reportUserId;
            return this;
        }

        public Builder images(String images) {
            obj.images = images;
            return this;
        }

        public Builder createTime(Date createTime) {
            obj.createTime = createTime;
            return this;
        }

        public Builder status(Short status) {
            obj.status = status;
            return this;
        }

        public Builder checkUserId(String checkUserId) {
            obj.checkUserId = checkUserId;
            return this;
        }

        public Builder closedTime(Date closedTime) {
            obj.closedTime = closedTime;
            return this;
        }

        public Builder memo(String memo) {
            obj.memo = memo;
            return this;
        }

        public Builder groupId(Long groupId) {
            obj.groupId = groupId;
            return this;
        }

        public Builder orderId(Long orderId) {
            obj.orderId = orderId;
            return this;
        }

        public Repair build() {
            return this.obj;
        }
    }
}