package com.live.recording.domain.vo.output;

import java.io.Serializable;

public class Livefilelists implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4982456684968068790L;

	private String channelId;// 直播编号

	private String startTime;// 开始时间

	private String endTime;// 结束时间

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
		return "Livefilelists [channelId=" + channelId + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
