package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DeleteMeetingView entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "delete_meeting_view", catalog = "meetingmngdatabase")
public class DeleteMeetingView implements java.io.Serializable {

	// Fields

	private Integer meetingId;
	private String meetingName;
	private String meetingContent;
	private String meetingLocation;
	private String meetingCreatorId;
	private String meetingRemark;
	private String meetingQrcode;
	private Integer meetingFrequency;
	private String meetingStartTime;
	private String meetingPredictFinishTime;
	private String meetingCreateTime;
	private String meetingDeleteTime;

	// Constructors

	/** default constructor */
	public DeleteMeetingView() {
	}

	/** minimal constructor */
	public DeleteMeetingView(String meetingName, String meetingContent,
			String meetingLocation, String meetingQrcode,
			Integer meetingFrequency, String meetingStartTime,
			String meetingPredictFinishTime, String meetingCreateTime) {
		this.meetingName = meetingName;
		this.meetingContent = meetingContent;
		this.meetingLocation = meetingLocation;
		this.meetingQrcode = meetingQrcode;
		this.meetingFrequency = meetingFrequency;
		this.meetingStartTime = meetingStartTime;
		this.meetingPredictFinishTime = meetingPredictFinishTime;
		this.meetingCreateTime = meetingCreateTime;
	}

	/** full constructor */
	public DeleteMeetingView(String meetingName, String meetingContent,
			String meetingLocation, String meetingCreatorId,
			String meetingRemark, String meetingQrcode,
			Integer meetingFrequency, String meetingStartTime,
			String meetingPredictFinishTime, String meetingCreateTime,
			String meetingDeleteTime) {
		this.meetingName = meetingName;
		this.meetingContent = meetingContent;
		this.meetingLocation = meetingLocation;
		this.meetingCreatorId = meetingCreatorId;
		this.meetingRemark = meetingRemark;
		this.meetingQrcode = meetingQrcode;
		this.meetingFrequency = meetingFrequency;
		this.meetingStartTime = meetingStartTime;
		this.meetingPredictFinishTime = meetingPredictFinishTime;
		this.meetingCreateTime = meetingCreateTime;
		this.meetingDeleteTime = meetingDeleteTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "meetingId", nullable = false)
	public Integer getMeetingId() {
		return this.meetingId;
	}

	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}

	@Column(name = "meetingName", nullable = false, length = 100)
	public String getMeetingName() {
		return this.meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	@Column(name = "meetingContent", nullable = false, length = 65535)
	public String getMeetingContent() {
		return this.meetingContent;
	}

	public void setMeetingContent(String meetingContent) {
		this.meetingContent = meetingContent;
	}

	@Column(name = "meetingLocation", nullable = false, length = 100)
	public String getMeetingLocation() {
		return this.meetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}

	@Column(name = "meetingCreatorId", length = 15)
	public String getMeetingCreatorId() {
		return this.meetingCreatorId;
	}

	public void setMeetingCreatorId(String meetingCreatorId) {
		this.meetingCreatorId = meetingCreatorId;
	}

	@Column(name = "meetingRemark", length = 65535)
	public String getMeetingRemark() {
		return this.meetingRemark;
	}

	public void setMeetingRemark(String meetingRemark) {
		this.meetingRemark = meetingRemark;
	}

	@Column(name = "meetingQRCode", nullable = false, length = 100)
	public String getMeetingQrcode() {
		return this.meetingQrcode;
	}

	public void setMeetingQrcode(String meetingQrcode) {
		this.meetingQrcode = meetingQrcode;
	}

	@Column(name = "meetingFrequency", nullable = false)
	public Integer getMeetingFrequency() {
		return this.meetingFrequency;
	}

	public void setMeetingFrequency(Integer meetingFrequency) {
		this.meetingFrequency = meetingFrequency;
	}

	@Column(name = "meetingStartTime", nullable = false, length = 13)
	public String getMeetingStartTime() {
		return this.meetingStartTime;
	}

	public void setMeetingStartTime(String meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}

	@Column(name = "meetingPredictFinishTime", nullable = false, length = 13)
	public String getMeetingPredictFinishTime() {
		return this.meetingPredictFinishTime;
	}

	public void setMeetingPredictFinishTime(String meetingPredictFinishTime) {
		this.meetingPredictFinishTime = meetingPredictFinishTime;
	}

	@Column(name = "meetingCreateTime", nullable = false, length = 13)
	public String getMeetingCreateTime() {
		return this.meetingCreateTime;
	}

	public void setMeetingCreateTime(String meetingCreateTime) {
		this.meetingCreateTime = meetingCreateTime;
	}

	@Column(name = "meetingDeleteTime", length = 13)
	public String getMeetingDeleteTime() {
		return this.meetingDeleteTime;
	}

	public void setMeetingDeleteTime(String meetingDeleteTime) {
		this.meetingDeleteTime = meetingDeleteTime;
	}

}