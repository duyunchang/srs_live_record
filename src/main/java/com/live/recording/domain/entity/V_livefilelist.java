package com.live.recording.domain.entity;


public class V_livefilelist {
	private String contentName;
	
	private String channelId;// 直播编号

	private String startTime;// 开始时间
	
	private String endTime;// 结束时间

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
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

	@Override
	public String toString() {
		return "v_livefilelist [contentName=" + contentName + ", channelId=" + channelId + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
	
	
}
