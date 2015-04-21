package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "message", catalog = "meetingmngdatabase")
public class Message implements java.io.Serializable {

	// Fields

	private Integer msgId;
	private String username;
	private String msgContent;
	private boolean isPush;
	private boolean isChecked;
	private String createTime;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** full constructor */
	public Message(String username, String msgContent, boolean isPush,
			boolean isChecked, String createTime) {
		this.username = username;
		this.msgContent = msgContent;
		this.isPush = isPush;
		this.isChecked = isChecked;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "msgId", unique = true, nullable = false)
	public Integer getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	@Column(name = "username", nullable = false, length = 80)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "msgContent", nullable = false, length = 500)
	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Column(name = "isPush", nullable = false)
	public boolean getIsPush() {
		return this.isPush;
	}

	public void setIsPush(boolean isPush) {
		this.isPush = isPush;
	}

	@Column(name = "isChecked", nullable = false)
	public boolean getIsChecked() {
		return this.isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Column(name = "createTime", nullable = false, length = 13)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}