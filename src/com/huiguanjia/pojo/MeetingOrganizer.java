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
 * MeetingOrganizer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "meeting_organizer", catalog = "meetingmngdatabase")
public class MeetingOrganizer implements java.io.Serializable {

	// Fields

	private MeetingOrganizerId id;
	private Meeting meeting;
	private Integer state;
	private boolean isCreator;
	private String feedback;

	// Constructors

	/** default constructor */
	public MeetingOrganizer() {
	}

	/** minimal constructor */
	public MeetingOrganizer(MeetingOrganizerId id, Meeting meeting,
			Integer state, boolean isCreator) {
		this.id = id;
		this.meeting = meeting;
		this.state = state;
		this.isCreator = isCreator;
	}

	/** full constructor */
	public MeetingOrganizer(MeetingOrganizerId id, Meeting meeting,
			Integer state, boolean isCreator, String feedback) {
		this.id = id;
		this.meeting = meeting;
		this.state = state;
		this.isCreator = isCreator;
		this.feedback = feedback;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "organizerCellphone", column = @Column(name = "organizerCellphone", nullable = false, length = 15)),
			@AttributeOverride(name = "meetingId", column = @Column(name = "meetingId", nullable = false,length = 32)) })
	public MeetingOrganizerId getId() {
		return this.id;
	}

	public void setId(MeetingOrganizerId id) {
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

	@Column(name = "isCreator", nullable = false)
	public boolean getIsCreator() {
		return this.isCreator;
	}

	public void setIsCreator(boolean isCreator) {
		this.isCreator = isCreator;
	}

	@Column(name = "feedback", length = 100)
	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}