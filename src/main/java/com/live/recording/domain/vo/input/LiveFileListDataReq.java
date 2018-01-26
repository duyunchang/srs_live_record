package com.live.recording.domain.vo.input;

public class LiveFileListDataReq {
	private String channelId;
	private String startTime;
	private String endTime;

	private String cutId;
	private String recordId;
	private String path;
	private String remark;
	private String creater;

	private Integer isDelete;

	public LiveFileListDataReq() {
		super();
	}

	public LiveFileListDataReq(String channelId, String startTime, String endTime, String path, String remark, String creater) {
		super();
		this.channelId = channelId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.path = path;
		this.remark = remark;
		this.creater = creater;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getCutId() {
		return cutId;
	}

	public void setCutId(String cutId) {
		this.cutId = cutId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
		this.creater = (creater==null?"":creater);
	}

	@Override
	public String toString() {
		return "LiveFileListDataReq [channelId=" + channelId + ", startTime=" + startTime + ", endTime=" + endTime + ", cutId=" + cutId + ", recordId=" + recordId + ", path=" + path + ", remark=" + remark + ", creater=" + creater + ", isDelete=" + isDelete + "]";
	}

}
