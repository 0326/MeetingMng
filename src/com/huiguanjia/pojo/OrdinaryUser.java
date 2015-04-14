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
 * OrdinaryUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ordinary_user", catalog = "meetingmngdatabase")
public class OrdinaryUser implements java.io.Serializable {

	// Fields

	private String cellphone;
	private CompanyAndCompanyAdmin companyAndCompanyAdmin;
	private Department department;
	private boolean isCellphoneHide;
	private String name;
	private String password;
	private boolean isRegister;
	private Date registerTime;
	private String email;
	private boolean isBindEmail;
	private boolean sex;
	private String officePhone;
	private String job;
	private String avatarUrl;
	private String officeLocation;
	private String workNo;

	// Constructors

	/** default constructor */
	public OrdinaryUser() {
	}

	/** minimal constructor */
	public OrdinaryUser(String cellphone,
			CompanyAndCompanyAdmin companyAndCompanyAdmin,
			Department department, boolean isCellphoneHide, String name,
			String password, boolean isRegister, boolean isBindEmail,
			boolean sex, String avatarUrl) {
		this.cellphone = cellphone;
		this.companyAndCompanyAdmin = companyAndCompanyAdmin;
		this.department = department;
		this.isCellphoneHide = isCellphoneHide;
		this.name = name;
		this.password = password;
		this.isRegister = isRegister;
		this.isBindEmail = isBindEmail;
		this.sex = sex;
		this.avatarUrl = avatarUrl;
	}

	/** full constructor */
	public OrdinaryUser(String cellphone,
			CompanyAndCompanyAdmin companyAndCompanyAdmin,
			Department department, boolean isCellphoneHide, String name,
			String password, boolean isRegister, Date registerTime,
			String email, boolean isBindEmail, boolean sex, String officePhone,
			String job, String avatarUrl, String officeLocation, String workNo) {
		this.cellphone = cellphone;
		this.companyAndCompanyAdmin = companyAndCompanyAdmin;
		this.department = department;
		this.isCellphoneHide = isCellphoneHide;
		this.name = name;
		this.password = password;
		this.isRegister = isRegister;
		this.registerTime = registerTime;
		this.email = email;
		this.isBindEmail = isBindEmail;
		this.sex = sex;
		this.officePhone = officePhone;
		this.job = job;
		this.avatarUrl = avatarUrl;
		this.officeLocation = officeLocation;
		this.workNo = workNo;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departmentId", nullable = false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "isCellphoneHide", nullable = false)
	public boolean getIsCellphoneHide() {
		return this.isCellphoneHide;
	}

	public void setIsCellphoneHide(boolean isCellphoneHide) {
		this.isCellphoneHide = isCellphoneHide;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false, length = 15)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "isRegister", nullable = false)
	public boolean getIsRegister() {
		return this.isRegister;
	}

	public void setIsRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}

	@Column(name = "registerTime", length = 0)
	public Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name = "email", length = 80)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "isBindEmail", nullable = false)
	public boolean getIsBindEmail() {
		return this.isBindEmail;
	}

	public void setIsBindEmail(boolean isBindEmail) {
		this.isBindEmail = isBindEmail;
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

	@Column(name = "job", length = 30)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
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

	@Column(name = "workNo", length = 50)
	public String getWorkNo() {
		return this.workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

}