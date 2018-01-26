package com.live.recording.domain.vo.output;

public class ProgressResp {

	private String keyId;
	private String progressValue;
	
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getProgressValue() {
		return progressValue;
	}
	public void setProgressValue(String progressValue) {
		this.progressValue = progressValue;
	}
	@Override
	public String toString() {
		return "ProgressResp [keyId=" + keyId + ", progressValue=" + progressValue + "]";
	}
	
	
}
