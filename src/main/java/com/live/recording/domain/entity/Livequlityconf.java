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
@Table(name = "livequlityconf")
public class Livequlityconf implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -465143977034475866L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

	@Column(name = "qulitycode", length = 8)
	private String qulityCode;// 清晰度编号

	@Column(name = "qulityname", length = 32)
	private String qulityName;// 开始清晰度名称

	@Column(name = "qulityrangelow", length = 32)
	private String qulityRangeLow;// 结束时间

	@Column(name = "qulityrangehigh", length = 32)
	private String qulityRangeHigh;// 最高码率

	
	@Column(name = "remark", length = 512)
	private String remark;// 描述

	

	@Column(name = "status", length = 1)
	private Integer status;// 状态，1可用，2不可用
	
	@Column(name = "isdelete", length = 1)
	private Integer isDelete;// 是否删除，1被删除，2未删除

//	@Column(name = "creater", length = 32)
//	private String creater;// 创建人
//	
//	@Column(name = "createtime")
//	private Date createTime;// 创建时间
//
//	@Column(name = "lastupdater", length = 32)
//	private String lastUpdater;// 最后更新人
//	
//	@Column(name = "lastupdatetime")
//	private Date lastUpdateTime;// 最后更新时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQulityCode() {
		return qulityCode;
	}

	public void setQulityCode(String qulityCode) {
		this.qulityCode = qulityCode;
	}

	public String getQulityName() {
		return qulityName;
	}

	public void setQulityName(String qulityName) {
		this.qulityName = qulityName;
	}

	public String getQulityRangeLow() {
		return qulityRangeLow;
	}

	public void setQulityRangeLow(String qulityRangeLow) {
		this.qulityRangeLow = qulityRangeLow;
	}

	public String getQulityRangeHigh() {
		return qulityRangeHigh;
	}

	public void setQulityRangeHigh(String qulityRangeHigh) {
		this.qulityRangeHigh = qulityRangeHigh;
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

	@Override
	public String toString() {
		return "Livequlityconf [id=" + id + ", qulityCode=" + qulityCode + ", qulityName=" + qulityName + ", qulityRangeLow=" + qulityRangeLow + ", qulityRangeHigh=" + qulityRangeHigh + ", remark=" + remark + ", status=" + status + ", isDelete=" + isDelete + "]";
	}

//	public String getCreater() {
//		return creater;
//	}
//
//	public void setCreater(String creater) {
//		this.creater = creater;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//
//	public String getLastUpdater() {
//		return lastUpdater;
//	}
//
//	public void setLastUpdater(String lastUpdater) {
//		this.lastUpdater = lastUpdater;
//	}
//
//	public Date getLastUpdateTime() {
//		return lastUpdateTime;
//	}
//
//	public void setLastUpdateTime(Date lastUpdateTime) {
//		this.lastUpdateTime = lastUpdateTime;
//	}

//	@Override
//	public String toString() {
//		return "Livequlityconf [id=" + id + ", qulityCode=" + qulityCode + ", qulityName=" + qulityName + ", qulityRangeLow=" + qulityRangeLow + ", qulityRangeHigh=" + qulityRangeHigh + ", remark=" + remark + ", status=" + status + ", isDelete=" + isDelete + ", creater=" + creater + ", createTime=" + createTime + ", lastUpdater=" + lastUpdater + ", lastUpdateTime=" + lastUpdateTime + "]";
//	}
	
	
	
	
	

}
