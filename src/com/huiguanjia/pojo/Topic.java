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
 * Topic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "topic", catalog = "meetingmngdatabase")
public class Topic implements java.io.Serializable {

	// Fields

	private Integer id;
	private Meeting meeting;
	private String title;
	private String content;
	private String creatorId;
	private String createTime;

	// Constructors

	/** default constructor */
	public Topic() {
	}

	/** full constructor */
	public Topic(Meeting meeting, String title, String content,
			String creatorId, String createTime) {
		this.meeting = meeting;
		this.title = title;
		this.content = content;
		this.creatorId = creatorId;
		this.createTime = createTime;
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
	@JoinColumn(name = "meetingId", nullable = false)
	public Meeting getMeeting() {
		return this.meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	@Column(name = "title", nullable = false, length = 500)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "creatorId", nullable = false, length = 15)
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "createTime", nullable = false, length = 13)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}