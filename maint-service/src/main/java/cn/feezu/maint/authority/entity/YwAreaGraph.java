package cn.feezu.maint.authority.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class YwAreaGraph implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -372914965208917250L;

	private Long id;

	private Byte graphType;

	private String points;

	private Long areaId;

	private Date operateTime;

	public YwAreaGraph(Long areaId) {
		super();
		this.areaId = areaId;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getGraphType() {
		return graphType;
	}

	public void setGraphType(Byte graphType) {
		this.graphType = graphType;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points == null ? null : points.trim();
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	public String toString() {
		return "YwAreaGraph{" + "id=" + id + ", graphType=" + graphType + ", points='" + points + '\'' + ", areaId=" + areaId + '}';
	}


	public YwAreaGraph(Byte graphType, String points, Long areaId) {
		super();
		this.graphType = graphType;
		this.points = points;
		this.areaId = areaId;
	}
}
