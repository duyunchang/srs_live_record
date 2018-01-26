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
@Table(name = "livecutlist")
public class Livecutlist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8242791435218181343L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

	@Column(name = "recordid", length = 32)
	private String recordId;// 节目录制Id

	@Column(name = "cutid", length = 512)
	private String cutId;// 节目切片Id

	@Column(name = "cutname", length = 512)
	private String cutName;// 节目切片名称

	@Column(name = "cutstarttime", length = 512)
	private String cutStartTime;// 切片开始时间

	@Column(name = "cutendtime", length = 32)
	private String cutEndTime;// 切片结束时间

	@Column(name = "cutpath", length = 512)
	private String cutPath;// 切片存储路径

	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="remark", columnDefinition="TEXT", nullable=true) 
	private String remark;// 描述

	@Column(name = "filestatus", length = 11)
	private Integer fileStatus;// 状态，1未切片，2切片中，3切片完成，4切片失败

	@Column(name = "creater", length = 32)
	private String creater;// 创建人

	@Column(name = "isdelete", length = 11)
	private Integer isDelete;// 是否删除，1被删除，2未删除

	@Column(name = "createtime", nullable = false)
	private Date createTime;// 创建时间

	@Column(name = "cutduration", length = 32)
	private String cutDuration;// 创建人
	
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

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getCutId() {
		return cutId;
	}

	public void setCutId(String cutId) {
		this.cutId = cutId;
	}

	public String getCutName() {
		return cutName;
	}

	public void setCutName(String cutName) {
		this.cutName = cutName;
	}

	public String getCutStartTime() {
		return cutStartTime;
	}

	public void setCutStartTime(String cutStartTime) {
		this.cutStartTime = cutStartTime;
	}

	public String getCutEndTime() {
		return cutEndTime;
	}

	public void setCutEndTime(String cutEndTime) {
		this.cutEndTime = cutEndTime;
	}

	public String getCutPath() {
		return cutPath;
	}

	public void setCutPath(String cutPath) {
		this.cutPath = cutPath;
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCutDuration() {
		return cutDuration;
	}

	public void setCutDuration(String cutDuration) {
		this.cutDuration = cutDuration;
	}

	@Override
	public String toString() {
		return "livecutlist [id=" + id + ", recordId=" + recordId + ", cutId=" + cutId + ", cutName=" + cutName + ", cutStartTime=" + cutStartTime + ", cutEndTime=" + cutEndTime + ", cutPath=" + cutPath + ", remark=" + remark + ", fileStatus=" + fileStatus + ", creater=" + creater + ", isDelete=" + isDelete + ", createTime=" + createTime + "]";
	}

}
