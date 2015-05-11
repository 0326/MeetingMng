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
 * Alarm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "alarm", catalog = "meetingmngdatabase")
public class Alarm implements java.io.Serializable {

	// Fields

	private Integer id;
	private OrdinaryUser ordinaryUser;
	private Meeting meeting;
	private String alarmTime;

	// Constructors

	/** default constructor */
	public Alarm() {
	}

	/** full constructor */
	public Alarm(OrdinaryUser ordinaryUser, Meeting meeting, String alarmTime) {
		this.ordinaryUser = ordinaryUser;
		this.meeting = meeting;
		this.alarmTime = alarmTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cellphone", nullable = false)
	public OrdinaryUser getOrdinaryUser() {
		return this.ordinaryUser;
	}

	public void setOrdinaryUser(OrdinaryUser ordinaryUser) {
		this.ordinaryUser = ordinaryUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetingId", nullable = false)
	public Meeting getMeeting() {
		return this.meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	@Column(name = "alarmTime", nullable = false, length = 13)
	public String getAlarmTime() {
		return this.alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

}