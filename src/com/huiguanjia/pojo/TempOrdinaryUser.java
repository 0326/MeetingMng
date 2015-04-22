package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TempOrdinaryUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "temp_ordinary_user", catalog = "meetingmngdatabase")
public class TempOrdinaryUser implements java.io.Serializable {

	// Fields

	private String cellphone;
	private CompanyAndCompanyAdmin companyAndCompanyAdmin;
	private String name;
	private String password;

	// Constructors

	/** default constructor */
	public TempOrdinaryUser() {
	}

	/** full constructor */
	public TempOrdinaryUser(String cellphone,
			CompanyAndCompanyAdmin companyAndCompanyAdmin, String name,
			String password) {
		this.cellphone = cellphone;
		this.companyAndCompanyAdmin = companyAndCompanyAdmin;
		this.name = name;
		this.password = password;
	}

	// Property accessors
	@Id
	@Column(name = "cellphone", unique = true, nullable = false, length = 15)
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyId", nullable = false)
	public CompanyAndCompanyAdmin getCompanyAndCompanyAdmin() {
		return this.companyAndCompanyAdmin;
	}

	public void setCompanyAndCompanyAdmin(
			CompanyAndCompanyAdmin companyAndCompanyAdmin) {
		this.companyAndCompanyAdmin = companyAndCompanyAdmin;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}