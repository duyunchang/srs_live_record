package com.live.recording.domain.vo.input;

import java.io.Serializable;

public class V_Livereport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388606964303213441L;

	private Integer id;

	private String videoId;// 编号

	private Integer minfileStatus;//1未录制,2录制中,3录制完成,4录制失败,5没有视频
	
	private Integer maxfileStatus;//1未录制,2录制中,3录制完成,4录制失败,5没有视频
	
	private String sourceId;// 编号

	private String sourceName;// 名称

	private String callbackUrl;// 回调地址
	
	private Integer resultstatus;//最终结果状态
	
	private String resultsMsg;// 最终结果状态msg
	
	private Integer reportFailCount;//上报失败次数

	public Integer getReportFailCount() {
		return reportFailCount;
	}

	public void setReportFailCount(Integer reportFailCount) {
		this.reportFailCount = reportFailCount;
	}

	public String getResultsMsg() {
		return resultsMsg;
	}

	public void setResultsMsg(String resultsMsg) {
		this.resultsMsg = resultsMsg;
	}

	public Integer getResultstatus() {
		return resultstatus;
	}

	public void setResultstatus(Integer resultstatus) {
		this.resultstatus = resultstatus;
	}

	public Integer getMinfileStatus() {
		return minfileStatus;
	}

	public void setMinfileStatus(Integer minfileStatus) {
		this.minfileStatus = minfileStatus;
	}

	public Integer getMaxfileStatus() {
		return maxfileStatus;
	}

	public void setMaxfileStatus(Integer maxfileStatus) {
		this.maxfileStatus = maxfileStatus;
	}

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

	

	

}
