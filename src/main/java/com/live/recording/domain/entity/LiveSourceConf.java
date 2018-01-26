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
@Table(name = "livesourceconf")
public class LiveSourceConf implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1008428035422027956L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

	@Column(name = "sourcecode", length = 32)
	private String sourceCode;// 编号

	@Column(name = "sourcename", length = 32)
	private String sourceName;// 名称

	@Column(name = "callbackurl", length = 256)
	private String callbackUrl;// 回调地址

	@Column(name = "savepath", length = 256)
	private String savePath;// 存储合并视频路径

	@Column(name = "remark", length = 512)
	private String remark;// 描述

	@Column(name = "status", length = 11)
	private Integer status;// 状态，1可用，2不可用

	@Column(name = "isdelete", length = 11)
	private Integer isDelete;// 是否删除，1被删除，2未删除


	@Column(name = "creater", length = 32)
	private String creater;// 创建人

	

	@Column(name = "createtime", nullable = false)
	private Date createTime;// 创建时间

	@Column(name = "lastupdater", length = 32)
	private String lastUpdater;// 最后更新人
	
	@Column(name = "lastupdatetime", nullable = false)
	private Date lastUpdateTime;// 最后更新时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
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

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "LiveSourceConf [id=" + id + ", sourceCode=" + sourceCode + ", sourceName=" + sourceName + ", callbackUrl=" + callbackUrl + ", savePath=" + savePath + ", remark=" + remark + ", status=" + status + ", isDelete=" + isDelete + ", creater=" + creater + ", createTime=" + createTime + ", lastUpdater=" + lastUpdater + ", lastUpdateTime=" + lastUpdateTime + "]";
	}

	

	

}
