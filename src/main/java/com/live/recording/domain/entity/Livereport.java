package com.live.recording.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "livereport")
public class Livereport implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2765029859092458956L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

	@Column(name = "videoid", length = 32)
	private String videoId;// 编号

	@Column(name = "sourceid", length = 32)
	private String sourceId;// 编号

	@Column(name = "sourcename", length = 32)
	private String sourceName;// 名称

	@Column(name = "callbackurl", length = 512)
	private String callbackUrl;// 回调地址

	

	@Column(name = "remark", length = 512)
	private String remark;// 描述

	@Column(name = "callbackstatus", length = 11)
	private Integer callBackStatus;// 状态，1未上报，2上报成功，3上报失败

	@Column(name = "reportfailcount", length = 11)
	private Integer reportFailCount;// 上报失败次数
	
	
	@Column(name = "creater", length = 32)
	private String creater;// 创建人

	@Column(name = "isdelete", length = 11)
	private Integer isDelete;// 是否删除，1被删除，2未删除

	@Column(name = "createtime", nullable = false)
	private Date createTime;// 创建时间

	@Column(name = "lastupdater", length = 32)
	private String lastUpdater;// 最后更新人
	
	@Column(name = "lastupdatetime")
	private Date lastUpdateTime;// 最后更新时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCallBackStatus() {
		return callBackStatus;
	}

	public void setCallBackStatus(Integer callBackStatus) {
		this.callBackStatus = callBackStatus;
	}

	public Integer getReportFailCount() {
		return reportFailCount;
	}

	public void setReportFailCount(Integer reportFailCount) {
		this.reportFailCount = reportFailCount;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "Livereport [id=" + id + ", videoId=" + videoId + ", sourceId=" + sourceId + ", sourceName=" + sourceName + ", callbackUrl=" + callbackUrl + ", remark=" + remark + ", callBackStatus=" + callBackStatus + ", reportFailCount=" + reportFailCount + ", creater=" + creater + ", isDelete=" + isDelete + ", createTime=" + createTime + ", lastUpdater=" + lastUpdater + ", lastUpdateTime=" + lastUpdateTime + "]";
	}
	
	

	

}
