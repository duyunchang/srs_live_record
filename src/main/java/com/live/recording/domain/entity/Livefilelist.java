package com.live.recording.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "livefilelist")
public class Livefilelist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -918368954083884550L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

	@Column(name = "channelid", length = 512)
	private String channelId;// 直播编号

	@Column(name = "starttime", length = 512)
	private String startTime;// 开始时间

	@Column(name = "endtime", length = 32)
	private String endTime;// 结束时间

	@Column(name = "path", length = 512)
	private String path;// 存储路径

	@Column(name = "remark", length = 512)
	private String remark;// 描述

	@Column(name = "creater", length = 32)
	private String creater;// 创建人

	@Column(name = "isdelete", length = 1)
	private Integer isDelete;// 是否删除，1被删除，2未删除

	@Column(name = "createtime", nullable = false)
	private Date createTime;// 创建时间

	@Column(name = "duration", length = 32)
	private String duration;// 生成concat的参数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
		this.creater = creater;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Livefilelist [id=" + id + ", channelId=" + channelId + ", startTime=" + startTime + ", endTime=" + endTime + ", path=" + path + ", remark=" + remark + ", creater=" + creater + ", isDelete=" + isDelete + ", createTime=" + createTime + ", duration=" + duration + "]";
	}

}
