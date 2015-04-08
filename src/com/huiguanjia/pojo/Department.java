package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "department", catalog = "meetingmngdatabase")
public class Department {
	
	private int departmentId;
	private String companyId;
	private String departmentName;
	private int parentId;
	private int depth;

	public Department(){
		
	}
	
	public Department(String companyId, String departmentName, int parentId, int depth){
		this.companyId = companyId;
		this.departmentName = departmentName;
		this.parentId = parentId;
		this.depth = depth;
	}
	
	@Id
	@Column(name = "departmentId", unique = true, nullable = false, length = 11)
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "companyId", nullable = false, length = 80)
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name = "departmentName", unique = true, nullable = false, length = 50)
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "parentId", length = 11)
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(name = "depth", nullable = false, length = 11)
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}
