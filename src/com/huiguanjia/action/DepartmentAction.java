package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.huiguanjia.pojo.Department;
import com.huiguanjia.service.DepartmentService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DepartmentAction extends ActionSupport{

	private int departmentId;
	private String companyId;
	private String departmentName;
	private int parentId;
	private int depth;
	private Map<String,Object> jsonData;
	
	public String execute() throws Exception{
		return "json";

	}
	public void printProperty(){
		System.out.println("departmentId:"+this.departmentId);
		System.out.println("companyId:"+this.companyId);
		System.out.println("departmentName:"+this.departmentName);
		System.out.println("parentId:"+this.parentId);
		System.out.println("depth:"+this.depth);
	}
	
	public String add(){
		jsonData = new HashMap<String,Object>();
		this.printProperty();
		Department depart = new Department(companyId,departmentName,parentId,depth);
		DepartmentService departService = new DepartmentService();
		if(false == departService.add(depart)){
			jsonData.put("code", "-1");
		}
		else{
			jsonData.put("code", "0");
		}
		
		return SUCCESS;
	}

	public String delete(){
		jsonData = new HashMap<String,Object>();
		
		DepartmentService departService = new DepartmentService();
		if(false == departService.delete(departmentId)){
			jsonData.put("code", "-1");
		}
		else{
			jsonData.put("code", "0");
		}
		
		return SUCCESS;
	}
	
	public String update(){
		jsonData = new HashMap<String,Object>();
		Department depart = new Department(companyId,departmentName,parentId,depth);
		DepartmentService departService = new DepartmentService();
		if(false == departService.update(depart)){
			jsonData.put("code", "-1");
		}
		else{
			jsonData.put("code", "0");
		}
		
		return SUCCESS;
	}
	
	public String findByName(){
		jsonData = new HashMap<String,Object>();
		Department depart = new Department(companyId,departmentName,parentId,depth);
		DepartmentService departService = new DepartmentService();
		if(false == departService.update(depart)){
			jsonData.put("code", "-1");
		}
		else{
			jsonData.put("code", "0");
		}
		
		return SUCCESS;
	}
	
	public String findByCompanyId(){
		jsonData = new HashMap<String,Object>();
		Department depart = new Department(companyId,departmentName,parentId,depth);
		DepartmentService departService = new DepartmentService();
		if(false == departService.update(depart)){
			jsonData.put("code", "-1");
		}
		else{
			jsonData.put("code", "0");
		}
		
		return SUCCESS;
	}
	
	
	
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Map<String,Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String,Object> jsonData) {
		this.jsonData = jsonData;
	}
	
}
