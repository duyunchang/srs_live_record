package com.live.recording.domain.vo.output;

import java.util.Date;
import java.util.List;

import com.live.recording.domain.vo.input.V_Livecutlist;

public class LivecutlistResp {

	private Integer id;

	private String channelId;// 直播编号

	private String recordName;// 录制节目名称

	private String recordStartTime;// 开始时间

	private String recordEndTime;// 结束时间

	private String recordPath;// 存储路径

	private String remark;// 描述

	private String creater;// 创建人

	private Integer fileStatus;// 状态，1未录制，2录制中，3录制完成，4录制失败

	private Integer isDelete;// 是否删除，1被删除，2未删除

	private Date createTime;// 创建时间

	private String recordDuration;// 录制时长

	private String recordId;// 录制节目Id

	private List<V_Livecutlist> cutInfo;

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

	public List<V_Livecutlist> getCutInfo() {
		return cutInfo;
	}

	public void setCutInfo(List<V_Livecutlist> cutInfo) {
		this.cutInfo = cutInfo;
	}

	@Override
	public String toString() {
		return "LivecutlistResp [id=" + id + ", channelId=" + channelId + ", recordName=" + recordName + ", recordStartTime=" + recordStartTime + ", recordEndTime=" + recordEndTime + ", recordPath=" + recordPath + ", remark=" + remark + ", creater=" + creater + ", fileStatus=" + fileStatus + ", isDelete=" + isDelete + ", createTime=" + createTime + ", recordDuration=" + recordDuration + ", recordId=" + recordId + ", cutInfo=" + cutInfo + "]";
	}

	

	

}
