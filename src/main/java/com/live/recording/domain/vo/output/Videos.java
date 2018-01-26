package com.live.recording.domain.vo.output;

public class Videos {
	private Integer qulityId;//清晰度标识，待约定
	private String filepath;//文件相对路径
	private Integer bitrate;//码率
	private Long filesize;//大小,B为单位
	private Integer duration;//时长，秒为单位
	private String format;//视频格式
	private String acodec;//音频格式
	private String vcodec;//视频格式
	
	private Integer width;//视频宽
	private Integer height;//视频高
	
	public Videos() {
		super();
	}
	
	
	


	public Integer getWidth() {
		return width;
	}





	public void setWidth(Integer width) {
		this.width = width;
	}





	public Integer getHeight() {
		return height;
	}





	public void setHeight(Integer height) {
		this.height = height;
	}





	public Integer getQulityId() {
		return qulityId;
	}
	public void setQulityId(Integer qulityId) {
		this.qulityId = qulityId;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Integer getBitrate() {
		return bitrate;
	}
	public void setBitrate(Integer bitrate) {
		this.bitrate = bitrate;
	}
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getAcodec() {
		return acodec;
	}
	public void setAcodec(String acodec) {
		this.acodec = acodec;
	}
	public String getVcodec() {
		return vcodec;
	}
	public void setVcodec(String vcodec) {
		this.vcodec = vcodec;
	}
	@Override
	public String toString() {
		return "Videos [qulityId=" + qulityId + ", filepath=" + filepath + ", bitrate=" + bitrate + ", filesize=" + filesize + ", duration=" + duration + ", format=" + format + ", acodec=" + acodec + ", vcodec=" + vcodec + "]";
	}
	
	
}
