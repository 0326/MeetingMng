package com.huiguanjia.pojo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MeetingParticipator entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "meeting_participator", catalog = "meetingmngdatabase")
public class MeetingParticipator implements java.io.Serializable {

	// Fields

	private MeetingParticipatorId id;
	private Meeting meeting;
	private Integer state;
	private String feedback;

	// Constructors

	/** default constructor */
	public MeetingParticipator() {
	}

	/** minimal constructor */
	public MeetingParticipator(MeetingParticipatorId id, Meeting meeting,
			Integer state) {
		this.id = id;
		this.meeting = meeting;
		this.state = state;
	}

	/** full constructor */
	public MeetingParticipator(MeetingParticipatorId id, Meeting meeting,
			Integer state, String feedback) {
		this.id = id;
		this.meeting = meeting;
		this.state = state;
		this.feedback = feedback;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "participatorCellphone", column = @Column(name = "participatorCellphone", nullable = false, length = 15)),
			@AttributeOverride(name = "meetingId", column = @Column(name = "meetingId", nullable = false, length = 32)) })
	public MeetingParticipatorId getId() {
		return this.id;
	}

	public void setId(MeetingParticipatorId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetingId", nullable = false, insertable = false, updatable = false)
	public Meeting getMeeting() {
		return this.meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	@Column(name = "state", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "feedback", length = 100)
	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}