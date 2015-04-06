package com.huiguanjia.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Activate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "activate", catalog = "meetingmngdatabase")
public class Activate implements java.io.Serializable {

	// Fields

	private String activateAddr;
	private int activateMode;
	private String activateInfo;
	private Date sendTime;
	// Constructors

	/** default constructor */
	public Activate() {
	}

	/** full constructor */
	public Activate(String activateAddr, int activateMode,
			String activateInfo, Date sendTime) {
		this.activateAddr = activateAddr;
		this.activateMode = activateMode;
		this.activateInfo = activateInfo;
		this.sendTime = sendTime;
	}

	// Property accessors
	@Id
	@Column(name = "activateAddr", unique = true, nullable = false, length = 80)
	public String getActivateAddr() {
		return this.activateAddr;
	}

	public void setActivateAddr(String activateAddr) {
		this.activateAddr = activateAddr;
	}

	@Column(name = "activateMode", nullable = false)
	public int getActivateMode() {
		return this.activateMode;
	}

	public void setActivateMode(int activateMode) {
		this.activateMode = activateMode;
	}

	@Column(name = "activateInfo", nullable = false, length = 100)
	public String getActivateInfo() {
		return this.activateInfo;
	}

	public void setActivateInfo(String activateInfo) {
		this.activateInfo = activateInfo;
	}
	
	@Column(name = "sendTime", nullable = false)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}