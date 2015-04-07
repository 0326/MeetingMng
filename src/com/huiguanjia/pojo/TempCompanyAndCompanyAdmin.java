package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TempCompanyAndCompanyAdmin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "temp_company_and_company_admin", catalog = "meetingmngdatabase")
public class TempCompanyAndCompanyAdmin implements java.io.Serializable {

	// Fields

	private String companyName;
	private String username;
	private String password;
	private ProvinceAndCity provinceAndCity;
	private Industry industry;

	// Constructors

	/** default constructor */
	public TempCompanyAndCompanyAdmin() {
	}

	/** full constructor */
	public TempCompanyAndCompanyAdmin(String companyName, String username,
			String password, ProvinceAndCity provinceAndCity, Industry industry) {
		this.companyName = companyName;
		this.username = username;
		this.password = password;
		this.provinceAndCity = provinceAndCity;
		this.industry = industry;
	}

	// Property accessors
	@Id
	@Column(name = "companyName", unique = true, nullable = false, length = 50)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "username", nullable = false, length = 80)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 15)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type", nullable = false)
	public Industry getIndustry() {
		return this.industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location", nullable = false)
	public ProvinceAndCity getProvinceAndCity() {
		return this.provinceAndCity;
	}

	public void setProvinceAndCity(ProvinceAndCity provinceAndCity) {
		this.provinceAndCity = provinceAndCity;
	}
}