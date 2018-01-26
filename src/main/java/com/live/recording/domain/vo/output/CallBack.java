package com.live.recording.domain.vo.output;

import java.util.List;

public class CallBack {
	private Integer code;//返回结果，0为成功
	private String msg;//返回正确或错误信息
	private String recordId;//流媒体侧的任务编号
		
	private List<Videos> videos;

	public Integer getCode() {
		return code;
	}

	

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public List<Videos> getVideos() {
		return videos;
	}

	public void setVideos(List<Videos> videos) {
		this.videos = videos;
	}

	@Override
	public String toString() {
		return "CallBack [code=" + code + ", msg=" + msg + ", recordId=" + recordId + ", videos=" + videos + "]";
	}
	
	
}
