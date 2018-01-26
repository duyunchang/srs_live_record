package com.live.recording.domain.vo.input;

public class LiveRecordListReq {
	private String channelId;
	private String recordStartTime;
	private String recordEndTime;

	private String recordName;
	private String remark;
	private String creater;

	// 再次切片
	private String recordId;
	private String cutStartTime;
	private String cutEndTime;
	private String cutName;

	private Integer creatFlvFlag;// 是否需要重新创建flv
	private Integer id;// 表liverecordlist 的id

	private Integer isDelete;
	
	private String cutId;
	//private List<Map<String,Object>> test;

	private String sourceCode;//回调资源code
	private Integer sourceId;//回调资源code
	
	public LiveRecordListReq() {
		super();
	}

	public LiveRecordListReq(String channelId, String recordStartTime, String recordEndTime, String recordName, String remark, String creater,String sourceCode) {
		super();
		this.channelId = channelId;
		this.recordStartTime = recordStartTime;
		this.recordEndTime = recordEndTime;
		this.recordName = recordName;
		this.remark = remark;
		this.creater = creater;
		this. sourceCode= sourceCode;
	}

	public LiveRecordListReq(String channelId, String recordStartTime, String recordEndTime, String recordName, String remark, String creater) {
		super();
		this.channelId = channelId;
		this.recordStartTime = recordStartTime;
		this.recordEndTime = recordEndTime;
		this.recordName = recordName;
		this.remark = remark;
		this.creater = creater;
	}


	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getCutId() {
		return cutId;
	}

	public void setCutId(String cutId) {
		this.cutId = cutId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatFlvFlag() {
		return creatFlvFlag;
	}

	public void setCreatFlvFlag(Integer creatFlvFlag) {
		this.creatFlvFlag = creatFlvFlag;
	}

	public String getCutName() {
		return cutName;
	}

	public void setCutName(String cutName) {
		this.cutName = cutName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
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

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
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
		this.creater =(creater==null?"":creater);
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getRecordStartTime() {
		return recordStartTime;
	}

	public void setRecordStartTime(String recordStartTime) {
		if (recordStartTime.indexOf(" ") < 1) {
			String starttime = recordStartTime.substring(0, 10);
			String endtime = recordStartTime.substring(10, 18);
			recordStartTime = starttime + " " + endtime;
		}
		this.recordStartTime = recordStartTime;
	}

	public String getRecordEndTime() {
		return recordEndTime;
	}

	public void setRecordEndTime(String recordEndTime) {
		if (recordEndTime.indexOf(" ") < 1) {
			String starttime = recordEndTime.substring(0, 10);
			String endtime = recordEndTime.substring(10, 18);
			recordEndTime = starttime + " " + endtime;
		}
		this.recordEndTime = recordEndTime;
	}

	@Override
	public String toString() {
		return "LiveRecordListReq [channelId=" + channelId + ", recordStartTime=" + recordStartTime + ", recordEndTime=" + recordEndTime + ", recordName=" + recordName + ", remark=" + remark + ", creater=" + creater + ", recordId=" + recordId + ", cutStartTime=" + cutStartTime + ", cutEndTime=" + cutEndTime + ", cutName=" + cutName + ", creatFlvFlag=" + creatFlvFlag + ", id=" + id + ", isDelete=" + isDelete + ", cutId=" + cutId + ", sourceCode=" + sourceCode + "]";
	}

	

	

	

}
