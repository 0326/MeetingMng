package com.huiguanjia.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Department entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "department", catalog = "meetingmngdatabase")
public class Department implements java.io.Serializable {

	// Fields

	private Integer departmentId;
	private CompanyAndCompanyAdmin companyAndCompanyAdmin;
	private Department department;
	private String departmentName;
	private Integer depth;

	// Constructors

	/** default constructor */
	public Department() {
	}

	/** minimal constructor */
	public Department(CompanyAndCompanyAdmin companyAndCompanyAdmin,
			Department department, String departmentName, Integer depth) {
		this.companyAndCompanyAdmin = companyAndCompanyAdmin;
		this.department = department;
		this.departmentName = departmentName;
		this.depth = depth;
	}


	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "departmentId", unique = true, nullable = false)
	public Integer getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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
	@JoinColumn(name = "parentId", nullable = false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "departmentName", nullable = false, length = 50)
	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "depth", nullable = false)
	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	

}