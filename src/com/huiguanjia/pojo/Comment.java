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
 * Comment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "comment", catalog = "meetingmngdatabase")
public class Comment implements java.io.Serializable {

	// Fields

	private Integer id;
	private Topic topic;
	private String content;
	private String commentTime;
	private String commentorId;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** full constructor */
	public Comment(Topic topic, String content, String commentTime,
			String commentorId) {
		this.topic = topic;
		this.content = content;
		this.commentTime = commentTime;
		this.commentorId = commentorId;
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
	@JoinColumn(name = "topicId", nullable = false)
	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "commentTime", nullable = false, length = 13)
	public String getCommentTime() {
		return this.commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	@Column(name = "commentorId", nullable = false, length = 15)
	public String getCommentorId() {
		return this.commentorId;
	}

	public void setCommentorId(String commentorId) {
		this.commentorId = commentorId;
	}

}