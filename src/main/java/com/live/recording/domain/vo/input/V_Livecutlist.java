package com.live.recording.domain.vo.input;

import java.io.Serializable;
import java.util.Date;

public class V_Livecutlist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1647066437007132819L;


	private Integer id;

	
	private String recordId;// 节目录制Id

	private String cutId;// 节目切片Id

	private String cutName;// 节目切片名称

	private String cutStartTime;// 切片开始时间

	private String cutEndTime;// 切片结束时间

	private String cutInfo;// 切片存储路径

	private String remark;// 描述

	private Integer fileStatus;// 状态，1未切片，2切片中，3切片完成，4切片失败

	private String creater;// 创建人

	private Integer isDelete;// 是否删除，1被删除，2未删除

	private Date createTime;// 创建时间

	private String cutDuration;// 创建人
	
	
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

	public String getCutInfo() {
		return cutInfo;
	}

	public void setCutInfo(String cutInfo) {
		this.cutInfo = cutInfo;
	}

	@Override
	public String toString() {
		return "V_Livecutlist [id=" + id + ", recordId=" + recordId + ", cutId=" + cutId + ", cutName=" + cutName + ", cutStartTime=" + cutStartTime + ", cutEndTime=" + cutEndTime + ", cutInfo=" + cutInfo + ", remark=" + remark + ", fileStatus=" + fileStatus + ", creater=" + creater + ", isDelete=" + isDelete + ", createTime=" + createTime + ", cutDuration=" + cutDuration + "]";
	}

	

}
