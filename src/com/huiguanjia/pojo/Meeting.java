package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Meeting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "meeting", catalog = "meetingmngdatabase")
public class Meeting implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String meetingId;
	private OrdinaryUser ordinaryUser;
	private String meetingName;
	private String meetingContent;
	private String meetingLocation;
	private String meetingRemark;
	private String meetingQrcode;
	private Integer meetingState;
	private Integer meetingFrequency;
	private String meetingStartTime;
	private String meetingPredictFinishTime;
	private String meetingCreateTime;
	private String meetingFinishTime;
	private String meetingDeleteTime;

	// Constructors

	/** default constructor */
	public Meeting() {
	}

	/** minimal constructor */
	public Meeting(String meetingId,String meetingName, String meetingContent,
			String meetingLocation, String meetingQrcode, Integer meetingState,
			Integer meetingFrequency, String meetingStartTime,
			String meetingPredictFinishTime, String meetingCreateTime) {
		this.meetingId = meetingId;
		this.meetingName = meetingName;
		this.meetingContent = meetingContent;
		this.meetingLocation = meetingLocation;
		this.meetingQrcode = meetingQrcode;
		this.meetingState = meetingState;
		this.meetingFrequency = meetingFrequency;
		this.meetingStartTime = meetingStartTime;
		this.meetingPredictFinishTime = meetingPredictFinishTime;
		this.meetingCreateTime = meetingCreateTime;
	}

	/** full constructor */
	public Meeting(String meetingId,OrdinaryUser ordinaryUser, String meetingName,
			String meetingContent, String meetingLocation,
			String meetingRemark, String meetingQrcode, Integer meetingState,
			Integer meetingFrequency, String meetingStartTime,
			String meetingPredictFinishTime, String meetingCreateTime,
			String meetingFinishTime, String meetingDeleteTime) {
		this.meetingId = meetingId;
		this.ordinaryUser = ordinaryUser;
		this.meetingName = meetingName;
		this.meetingContent = meetingContent;
		this.meetingLocation = meetingLocation;
		this.meetingRemark = meetingRemark;
		this.meetingQrcode = meetingQrcode;
		this.meetingState = meetingState;
		this.meetingFrequency = meetingFrequency;
		this.meetingStartTime = meetingStartTime;
		this.meetingPredictFinishTime = meetingPredictFinishTime;
		this.meetingCreateTime = meetingCreateTime;
		this.meetingFinishTime = meetingFinishTime;
		this.meetingDeleteTime = meetingDeleteTime;
	}
	
//	public Meeting(String meetingId, String meetingName,
//			String meetingContent, String meetingLocation,
//			String meetingRemark, String meetingQrcode, Integer meetingState,
//			Integer meetingFrequency, String meetingStartTime,
//			String meetingPredictFinishTime, String meetingCreateTime,
//			String meetingFinishTime, String meetingDeleteTime) {
//		this.meetingId = meetingId;
//		this.meetingName = meetingName;
//		this.meetingContent = meetingContent;
//		this.meetingLocation = meetingLocation;
//		this.meetingRemark = meetingRemark;
//		this.meetingQrcode = meetingQrcode;
//		this.meetingState = meetingState;
//		this.meetingFrequency = meetingFrequency;
//		this.meetingStartTime = meetingStartTime;
//		this.meetingPredictFinishTime = meetingPredictFinishTime;
//		this.meetingCreateTime = meetingCreateTime;
//		this.meetingFinishTime = meetingFinishTime;
//		this.meetingDeleteTime = meetingDeleteTime;
//	}

	// Property accessors
	@Id
	@Column(name = "meetingId", unique = true, nullable = false)
	public String getMeetingId() {
		return this.meetingId;
	}

	public void setMeetingId(String meetingId2) {
		this.meetingId = meetingId2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetingCreatorId")
	public OrdinaryUser getOrdinaryUser() {
		return this.ordinaryUser;
	}

	public void setOrdinaryUser(OrdinaryUser ordinaryUser) {
		this.ordinaryUser = ordinaryUser;
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

	@Column(name = "meetingState", nullable = false)
	public Integer getMeetingState() {
		return this.meetingState;
	}

	public void setMeetingState(Integer meetingState) {
		this.meetingState = meetingState;
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

	@Column(name = "meetingFinishTime", length = 13)
	public String getMeetingFinishTime() {
		return this.meetingFinishTime;
	}

	public void setMeetingFinishTime(String meetingFinishTime) {
		this.meetingFinishTime = meetingFinishTime;
	}

	@Column(name = "meetingDeleteTime", length = 13)
	public String getMeetingDeleteTime() {
		return this.meetingDeleteTime;
	}

	public void setMeetingDeleteTime(String meetingDeleteTime) {
		this.meetingDeleteTime = meetingDeleteTime;
	}

}