package com.live.recording.domain.vo.input;

import java.util.List;

public class V_MultiplePeriodsRecordsReq {
	private List<V_Livecutlist> liverecordlist;
	
	private String channelId;

	private String creater;
		
	private String recordName;
	
	private String recordId;
	
	private String sourceCode;
	
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
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

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public List<V_Livecutlist> getLiverecordlist() {
		return liverecordlist;
	}

	public void setLiverecordlist(List<V_Livecutlist> liverecordlist) {
		this.liverecordlist = liverecordlist;
	}

	@Override
	public String toString() {
		return "MultiplePeriodsRecordsReq [liverecordlist=" + liverecordlist + ", channelId=" + channelId + ", creater=" + creater + ", recordName=" + recordName + "]";
	}

	
	
}
