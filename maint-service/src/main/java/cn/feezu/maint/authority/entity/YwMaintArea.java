package cn.feezu.maint.authority.entity;

import java.io.Serializable;
import java.util.Date;

public class YwMaintArea implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5061384775228758838L;

	private Long id;

    private String name;

    private Long groupId;

    private Date operateTime;

    private YwAreaGraph graph;//图形
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

	public YwAreaGraph getGraph() {
		return graph;
	}

	public void setGraph(YwAreaGraph graph) {
		this.graph = graph;
	}
    
}
