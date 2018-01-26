package com.live.recording.domain.vo.input;

public class SysReq {

	private String action;// "on_dvr",
	private Integer client_id;// 119,
	private String ip;// "127.0.0.1",
	private String vhost;// "__defaultVhost__",
	private String app;// "slive",
	private String stream;// "tysxlive_dragon",
	private String cwd;// "/opt/pro/srs",
	private String file;// "/data/dvrvideo/srs/slive/tysxlive_dragon/20170901/tysxlive_dragon.0909.58.1504228198312.flv"
	private String duration;

	public SysReq() {
		super();
	}

	public SysReq(String action, Integer client_id, String ip, String vhost, String app, String stream, String cwd, String file, String duration) {
		super();
		this.action = action;
		this.client_id = client_id;
		this.ip = ip;
		this.vhost = vhost;
		this.app = app;
		this.stream = stream;
		this.cwd = cwd;
		this.file = file;
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getClient_id() {
		return client_id;
	}

	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVhost() {
		return vhost;
	}

	public void setVhost(String vhost) {
		this.vhost = vhost;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getCwd() {
		return cwd;
	}

	public void setCwd(String cwd) {
		this.cwd = cwd;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "SysReq [action=" + action + ", client_id=" + client_id + ", ip=" + ip + ", vhost=" + vhost + ", app=" + app + ", stream=" + stream + ", cwd=" + cwd + ", file=" + file + ", duration=" + duration + "]";
	}

}
