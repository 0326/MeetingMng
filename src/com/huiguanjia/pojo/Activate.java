package com.huiguanjia.pojo;

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
	private boolean activateMode;
	private String activateInfo;

	// Constructors

	/** default constructor */
	public Activate() {
	}

	/** full constructor */
	public Activate(String activateAddr, boolean activateMode,
			String activateInfo) {
		this.activateAddr = activateAddr;
		this.activateMode = activateMode;
		this.activateInfo = activateInfo;
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
	public boolean getActivateMode() {
		return this.activateMode;
	}

	public void setActivateMode(boolean activateMode) {
		this.activateMode = activateMode;
	}

	@Column(name = "activateInfo", nullable = false, length = 100)
	public String getActivateInfo() {
		return this.activateInfo;
	}

	public void setActivateInfo(String activateInfo) {
		this.activateInfo = activateInfo;
	}

}