package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MeetingParticipatorId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class MeetingParticipatorId implements java.io.Serializable {

	// Fields

	private String participatorCellphone;
	private String meetingId;

	// Constructors

	/** default constructor */
	public MeetingParticipatorId() {
	}

	/** full constructor */
	public MeetingParticipatorId(String participatorCellphone, String meetingId) {
		this.participatorCellphone = participatorCellphone;
		this.meetingId = meetingId;
	}

	// Property accessors

	@Column(name = "participatorCellphone", nullable = false, length = 15)
	public String getParticipatorCellphone() {
		return this.participatorCellphone;
	}

	public void setParticipatorCellphone(String participatorCellphone) {
		this.participatorCellphone = participatorCellphone;
	}

	@Column(name = "meetingId", nullable = false, length = 32)
	public String getMeetingId() {
		return this.meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MeetingParticipatorId))
			return false;
		MeetingParticipatorId castOther = (MeetingParticipatorId) other;

		return ((this.getParticipatorCellphone() == castOther
				.getParticipatorCellphone()) || (this
				.getParticipatorCellphone() != null
				&& castOther.getParticipatorCellphone() != null && this
				.getParticipatorCellphone().equals(
						castOther.getParticipatorCellphone())))
				&& ((this.getMeetingId() == castOther.getMeetingId()) || (this
						.getMeetingId() != null
						&& castOther.getMeetingId() != null && this
						.getMeetingId().equals(castOther.getMeetingId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getParticipatorCellphone() == null ? 0 : this
						.getParticipatorCellphone().hashCode());
		result = 37 * result
				+ (getMeetingId() == null ? 0 : this.getMeetingId().hashCode());
		return result;
	}

}