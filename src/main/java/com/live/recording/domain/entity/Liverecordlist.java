package com.live.recording.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "liverecordlist")
public class Liverecordlist implements Serializable {

	private static final long serialVersionUID = -6594998914354045414L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

	@Column(name = "channelid", length = 512)
	private String channelId;// 直播编号

	@Column(name = "recordname", length = 512)
	private String recordName;// 录制节目名称

	@Column(name = "recordstarttime", length = 512)
	private String recordStartTime;// 开始时间

	@Column(name = "recordendtime", length = 32)
	private String recordEndTime;// 结束时间

	@Column(name = "recordpath", length = 512)
	private String recordPath;// 存储路径

	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="remark", columnDefinition="TEXT", nullable=true) 
	private String remark;// 描述

	@Column(name = "creater", length = 32)
	private String creater;// 创建人

	@Column(name = "filestatus", length = 11)
	private Integer fileStatus;// 状态，1未录制，2录制中，3录制完成，4录制失败

	@Column(name = "isdelete", length = 11)
	private Integer isDelete;// 是否删除，1被删除，2未删除

	@Column(name = "createtime", nullable = false)
	// @Temporal(TemporalType.TIMESTAMP)
	private Date createTime;// 创建时间

	@Column(name = "recordduration", length = 32)
	private String recordDuration;// 录制时长

	@Column(name = "recordid", length = 32)
	private String recordId;// 录制节目Id

	@Column(name = "updatetime")
	private Date updateTime;// 更新时间
	
	@Column(name = "updater", length = 32)
	private String updater;// 更新人

	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getRecordDuration() {
		return recordDuration;
	}

	public void setRecordDuration(String recordDuration) {
		this.recordDuration = recordDuration;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRecordStartTime() {
		return recordStartTime;
	}

	public void setRecordStartTime(String recordStartTime) {
		this.recordStartTime = recordStartTime;
	}

	public String getRecordEndTime() {
		return recordEndTime;
	}

	public void setRecordEndTime(String recordEndTime) {
		this.recordEndTime = recordEndTime;
	}

	public String getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
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

	@Override
	public String toString() {
		return "Liverecordlist [id=" + id + ", channelId=" + channelId + ", recordName=" + recordName + ", recordStartTime=" + recordStartTime + ", recordEndTime=" + recordEndTime + ", recordPath=" + recordPath + ", remark=" + remark + ", creater=" + creater + ", fileStatus=" + fileStatus + ", isDelete=" + isDelete + ", createTime=" + createTime + ", recordDuration=" + recordDuration + ", recordId=" + recordId + "]";
	}

	

}
