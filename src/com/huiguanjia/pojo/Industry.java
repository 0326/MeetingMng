package com.huiguanjia.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Industry entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "industry", catalog = "meetingmngdatabase")
public class Industry implements java.io.Serializable {

	// Fields

	private String industryCode;
	private String industryName;
	private String category;
	

	// Constructors

	/** default constructor */
	public Industry() {
	}

	/** minimal constructor */
	public Industry(String industryCode, String industryName, String category) {
		this.industryCode = industryCode;
		this.industryName = industryName;
		this.category = category;
	}

	

	// Property accessors
	@Id
	@Column(name = "industryCode", unique = true, nullable = false, length = 6)
	public String getIndustryCode() {
		return this.industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	@Column(name = "industryName", nullable = false, length = 20)
	public String getIndustryName() {
		return this.industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	@Column(name = "category", nullable = false, length = 20)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	

}
