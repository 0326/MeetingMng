package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MeetingOrganizerId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class MeetingOrganizerId implements java.io.Serializable {

	// Fields

	private String organizerCellphone;
	private String meetingId;

	// Constructors

	/** default constructor */
	public MeetingOrganizerId() {
	}

	/** full constructor */
	public MeetingOrganizerId(String organizerCellphone, String meetingId) {
		this.organizerCellphone = organizerCellphone;
		this.meetingId = meetingId;
	}

	// Property accessors

	@Column(name = "organizerCellphone", nullable = false, length = 15)
	public String getOrganizerCellphone() {
		return this.organizerCellphone;
	}

	public void setOrganizerCellphone(String organizerCellphone) {
		this.organizerCellphone = organizerCellphone;
	}

	@Column(name = "meetingId", nullable = false,length = 32)
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
		if (!(other instanceof MeetingOrganizerId))
			return false;
		MeetingOrganizerId castOther = (MeetingOrganizerId) other;

		return ((this.getOrganizerCellphone() == castOther
				.getOrganizerCellphone()) || (this.getOrganizerCellphone() != null
				&& castOther.getOrganizerCellphone() != null && this
				.getOrganizerCellphone().equals(
						castOther.getOrganizerCellphone())))
				&& ((this.getMeetingId() == castOther.getMeetingId()) || (this
						.getMeetingId() != null
						&& castOther.getMeetingId() != null && this
						.getMeetingId().equals(castOther.getMeetingId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getOrganizerCellphone() == null ? 0 : this
						.getOrganizerCellphone().hashCode());
		result = 37 * result
				+ (getMeetingId() == null ? 0 : this.getMeetingId().hashCode());
		return result;
	}

}