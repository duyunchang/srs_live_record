package com.live.recording.config;
/**
 * 配置文件value映射
 * */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "liverecord")
public class MyConfig {

	// ffmpeg日志存路径
	private String saveLogPath;

	// 执行ffmpeg程序路径
	private String ffmpegPath;
	// 远程挂载 获取srs视频文件相对路径
	private String multipleMountFlvpath;
	// 拆条超时时间
	private long timeouts;
	// 拆条并发数
	private Integer dismantleConcurrent;
	
	//sys视频失效时间
	private Integer srsVideoFailureTime;
	//录制文件失效时间
	private Integer localVideoFailureTime;
	
	//录制视频延迟时间
	private Integer recordingDelayTime;
	
	public MyConfig() {
		super();
	}

	public Integer getRecordingDelayTime() {
		return recordingDelayTime;
	}

	public void setRecordingDelayTime(Integer recordingDelayTime) {
		
		this.recordingDelayTime = recordingDelayTime==null?0:recordingDelayTime;
	}

	public Integer getSrsVideoFailureTime() {
		return srsVideoFailureTime;
	}

	public void setSrsVideoFailureTime(Integer srsVideoFailureTime) {
		this.srsVideoFailureTime = srsVideoFailureTime;
	}

	public Integer getLocalVideoFailureTime() {
		return localVideoFailureTime;
	}

	public void setLocalVideoFailureTime(Integer localVideoFailureTime) {
		this.localVideoFailureTime = localVideoFailureTime;
	}

	public Integer getDismantleConcurrent() {
		return dismantleConcurrent;
	}

	public void setDismantleConcurrent(Integer dismantleConcurrent) {
		this.dismantleConcurrent = dismantleConcurrent;
	}

	public String getMultipleMountFlvpath() {
		return multipleMountFlvpath;
	}

	public void setMultipleMountFlvpath(String multipleMountFlvpath) {
		this.multipleMountFlvpath = multipleMountFlvpath;
	}

	public long getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(long timeouts) {
		this.timeouts = timeouts;
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}

	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}



	public String getSaveLogPath() {
		return saveLogPath;
	}

	public void setSaveLogPath(String saveLogPath) {
		this.saveLogPath = saveLogPath;
	}

	

}
