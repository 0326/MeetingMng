package com.huiguanjia.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CompanyAndCompanyAdmin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "company_and_company_admin", catalog = "meetingmngdatabase")
public class CompanyAndCompanyAdmin implements java.io.Serializable {

	// Fields

	private String companyName;
	private Industry industry;
	private ProvinceAndCity provinceAndCity;
	private String username;
	private String password;
	private Date registerTime;
	private boolean isLogin;
	private String email;
	private String name;
	private boolean sex;
	private String officePhone;
	private String avatarUrl;
	private String officeLocation;
	private String cellphone;

	// Constructors

	/** default constructor */
	public CompanyAndCompanyAdmin() {
	}

	/** minimal constructor */
	public CompanyAndCompanyAdmin(String companyName, Industry industry,
			ProvinceAndCity provinceAndCity, String username,
			String password, Date registerTime, boolean isLogin, String name,
			boolean sex, String avatarUrl) {
		this.companyName = companyName;
		this.industry = industry;
		this.provinceAndCity = provinceAndCity;
		this.username = username;
		this.password = password;
		this.registerTime = registerTime;
		this.isLogin = isLogin;
		this.name = name;
		this.sex = sex;
		this.avatarUrl = avatarUrl;
	}

	/** full constructor */
	public CompanyAndCompanyAdmin(String companyName, Industry industry,
			ProvinceAndCity provinceAndCity, String username,
			String password, Date registerTime, boolean isLogin, String email,
			String name, boolean sex, String officePhone, String avatarUrl,
			String officeLocation, String cellphone) {
		this.companyName = companyName;
		this.industry = industry;
		this.provinceAndCity = provinceAndCity;
		this.username = username;
		this.password = password;
		this.registerTime = registerTime;
		this.isLogin = isLogin;
		this.email = email;
		this.name = name;
		this.sex = sex;
		this.officePhone = officePhone;
		this.avatarUrl = avatarUrl;
		this.officeLocation = officeLocation;
		this.cellphone = cellphone;
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

	@Column(name = "registerTime", nullable = false, length = 0)
	public Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name = "isLogin", nullable = false)
	public boolean getIsLogin() {
		return this.isLogin;
	}

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	@Column(name = "email", length = 80)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sex", nullable = false)
	public boolean getSex() {
		return this.sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	@Column(name = "officePhone", length = 20)
	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name = "avatarUrl", nullable = false, length = 80)
	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Column(name = "officeLocation", length = 80)
	public String getOfficeLocation() {
		return this.officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	@Column(name = "cellphone", length = 15)
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

}