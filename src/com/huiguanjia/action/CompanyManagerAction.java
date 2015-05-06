package com.huiguanjia.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.service.ActivateService;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.DepartmentService;
import com.huiguanjia.comet.MeetingMsgInbound;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Industry;
import com.huiguanjia.pojo.Message;
import com.huiguanjia.pojo.ProvinceAndCity;
import com.huiguanjia.util.MD5Util;
import com.huiguanjia.util.MailSendUtil;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.pojo.Department;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import net.sf.json.JSONArray;

@SuppressWarnings("serial")
public class CompanyManagerAction extends ActionSupport {
	private String email;
	private String name;
	private String officePhone;
	private String cellphone;
	private String avatarUrl;
	private boolean sex;

	private String username;
	private String password;
	private String newpassword;
	private String type;
	private String companyName;
	private String location;

	private boolean isCellphoneHide;
	private String job;
	private String officeLocation;
	private String workNo;
	private int departmentId;
	private int pageIndex;
	private String keyword;

	private Map<String, Object> jsonData;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAvatarurl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public boolean getSex() {

		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean getIsCellphoneHide() {
		return isCellphoneHide;
	}

	public void setIsCellphoneHide(boolean isCellphoneHide) {
		this.isCellphoneHide = isCellphoneHide;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public String execute() throws Exception {
		return "json";

	}

	/**
	 * 公司管理员登录
	 * 
	 * @return
	 */
	public String login() {
		jsonData = new HashMap<String, Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		// System.out.print(username+";"+password);
		if (true == companyManagerService.login(username, password)) {
			jsonData.put("code", 0);
			ActionContext.getContext().getSession().put("username", username);
		} else {
			jsonData.put("code", -10401);
		}

		return SUCCESS;
	}

	/**
	 * 公司管理员登出
	 * 
	 * @return
	 */
	public String logout() {
		jsonData = new HashMap<String, Object>();
		String name = (String) ActionContext.getContext().getSession()
				.get("username");
		// System.out.println(name);
		// System.out.println(username);
		if (name.equals(username)) {
			jsonData.put("code", 0);
			ActionContext.getContext().getSession().remove("username");
		} else {
			jsonData.put("code", -10408);
		}
		return SUCCESS;
	}

	/**
	 * 公司管理员注册
	 * 
	 * @return
	 */
	public String register() {
		jsonData = new HashMap<String, Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		// System.out.println(companyName);
		switch (companyManagerService.register(username, password, type,
				companyName, location)) {
		case 1:
			jsonData.put("code", -10400);
			break; // username
		case 2:
			jsonData.put("code", -10401);
			break; // companyName
		case 3:
			jsonData.put("code", -10402);
			break; // industy(type)
		case 4:
			jsonData.put("code", -10403);
			break; // location
		case 5:
			jsonData.put("code", -10404);
			break; // sql failed
		case 6: {
			String userId = MD5Util.MD5Code(username);
			System.out.println(userId);
			Date sendTime = new Date();
			String activateCode;
			boolean mode = false;
			activateCode = MD5Util.MD5Code(sendTime.toString());
			String activatelink = "http://localhost:8080/MeetingMng"
					+ "/#/activition?uid=" + userId + "&aid=" + activateCode;
			System.out.print(MailSendUtil.send(username, activatelink));
			if (MailSendUtil.send(username, activatelink)) {
				ActivateService activateService = new ActivateService();
				activateService.save(userId, activateCode, sendTime, mode,
						username);
				jsonData.put("code", 0);
			} else {
				jsonData.put("code", -10408);
			}
		}
			break;
		}
		return SUCCESS;

	}

	/**
	 * 公司管理员激活
	 * 
	 * @return
	 */
	public String activate() {
		Date activateTime = new Date();
		String activateCode;
		activateCode = MD5Util.MD5Code(activateTime.toString());
		String userId = MD5Util.MD5Code(username);
		ActivateService activateService = new ActivateService();
		if (activateService.activate(userId, activateCode, activateTime) == null) {
			jsonData.put("code", -10409);
		} else {
			String aUsername = activateService.activate(userId, activateCode,
					activateTime);
			CompanyManagerService companyManagerService = new CompanyManagerService();
			companyManagerService.registerAfterActivate(aUsername);
			jsonData.put("code", 0);
		}
		return SUCCESS;
	}

	/**
	 * 公司名重复
	 * 
	 * @return
	 */
	public String companyNameRepeat() {
		jsonData = new HashMap<String, Object>();
		// System.out.println(companyName);
		CompanyManagerService companyManagerService = new CompanyManagerService();
		if (companyManagerService.companyNameRepeat(companyName)) {
			jsonData.put("code", -10400);
		}
		return SUCCESS;
	}

	/**
	 * 用户名重复
	 * 
	 * @return
	 */
	public String usernameRepeat() {
		jsonData = new HashMap<String, Object>();
		// System.out.println(username);
		CompanyManagerService companyManagerService = new CompanyManagerService();
		if (companyManagerService.usernameRepeat(username)) {
			jsonData.put("code", -10400);
		}
		return SUCCESS;
	}

	/**
	 * 管理员天添加或者修改用户工号是否重复
	 * 
	 * @return
	 */
	public String workNoRepeat() {
		jsonData = new HashMap<String, Object>();
		// System.out.println(companyName);
		CompanyManagerService companyManagerService = new CompanyManagerService();
		if (companyManagerService.workNoRepeat(username, workNo)) {
			jsonData.put("code", -10409);
		}
		return SUCCESS;
	}

	/**
	 * @info 公司管理员修改个人信息
	 * @return
	 */

	public String updateInfo() {
		jsonData = new HashMap<String, Object>();

		CompanyManagerService companyManagerService = new CompanyManagerService();
		if (companyManagerService.updateInfo(username, email, name, sex,
				officePhone, avatarUrl, cellphone, officeLocation)) {
			jsonData.put("code", 0);
		} else {
			jsonData.put("code", -1);
		}
		return SUCCESS;
	}

	/**
	 * @info 公司管理员修改密码
	 * @return
	 */
	public String updatePass() {
		jsonData = new HashMap<String, Object>();

		CompanyManagerService cms = new CompanyManagerService();
		int code = cms.updatePass(username, password, newpassword);
		jsonData.put("code", code);

		return SUCCESS;
	}

	/**
	 * @info 公司管理员取得公司管理员信息
	 * @return
	 * @throws JSONException
	 */
	public String findInfo() throws JSONException {
		jsonData = new HashMap<String, Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		String u = companyManagerService.getInfo(username);
		if (u == null) {
			jsonData.put("user", null);
		} else {
			jsonData.put("user", u);
		}

		return SUCCESS;
	}

	public String findStuffs() {

		List<Message> mlist = new ArrayList<Message>();
		Message m = new Message();
		m.setMsgContent("findStuffs");
		mlist.add(m);
		MeetingMsgInbound.pushSigle(mlist, (String) ActionContext.getContext()
				.getSession().get("username"));

		jsonData = new HashMap<String, Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		String u = companyManagerService.getInfos(username, pageIndex);
		if (u == null) {
			jsonData.put("code", -10416);
		} else {
			jsonData.put("code", 0);
			jsonData.put("stuffs", u);
		}

		return SUCCESS;
	}

	/**
	 * 公司管理员手动添加普通用户
	 * 
	 * @return
	 */
	public String addOrdinaryUser() {
		jsonData = new HashMap<String, Object>();

		CompanyAndCompanyAdmin c = new CompanyAndCompanyAdmin();
		c.setUsername(username);
		Department d = new Department();
		d.setDepartmentId(departmentId);
		// Date registerTime = new Date();

		OrdinaryUser u = new OrdinaryUser();
		u.setCompanyAndCompanyAdmin(c);
		u.setDepartment(d);
		// u.setRegisterTime(registerTime);
		u.setCellphone(cellphone);
		// u.setIsCellphoneHide(false);
		u.setName(name);
		// default password "123456"
		u.setPassword("123456");
		u.setEmail(email);
		u.setSex(sex);
		u.setOfficePhone(officePhone);
		u.setJob(job);
		u.setAvatarUrl(avatarUrl);
		u.setOfficeLocation(officeLocation);
		u.setWorkNo(workNo);

		CompanyManagerService companyManagerService = new CompanyManagerService();
		if (companyManagerService.addOrdinaryUser(u)) {

			jsonData.put("code", 0);
		} else {
			jsonData.put("code", -10415);
		}

		return SUCCESS;
	}

	/**
	 * 公司管理员删除用户
	 * 
	 * @return
	 */
	public String deleteOrdinaryUser() {
		jsonData = new HashMap<String, Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		if (false == companyManagerService.deleteOrdinaryUser(cellphone)) {
			jsonData.put("code", -10417);
		} else {
			jsonData.put("code", 0);
		}

		return SUCCESS;
	}

	/**
	 * 公司管理员修改普通用户信息
	 * 
	 * @return
	 */
	public String updateOrdinaryUser() {
		jsonData = new HashMap<String, Object>();
		// CompanyAndCompanyAdmin c = new CompanyAndCompanyAdmin();
		// c.setUsername(username);
		// Department d = new Department();
		// d.setDepartmentId(departmentId);
		// Date registerTime = new Date();

		// OrdinaryUser u = new OrdinaryUser();
		// // u.setCompanyAndCompanyAdmin(c);
		// u.
		// // u.setRegisterTime(registerTime);
		// u.setCellphone(cellphone);
		// // u.setIsCellphoneHide(false);
		// u.setName(name);
		// // u.setPassword("123456");
		// u.setEmail(email);
		// u.setSex(sex);
		// u.setOfficePhone(officePhone);
		// u.setJob(job);
		// // u.setAvatarUrl(avatarUrl);
		// u.setOfficeLocation(officeLocation);
		// u.setWorkNo(workNo);
		Object[] updateValues = new Object[] { name, workNo, sex, departmentId,
				job, email, officePhone, officeLocation, cellphone };

		CompanyManagerService companyManagerService = new CompanyManagerService();
		if (companyManagerService.updateOrdinaryUser(updateValues)) {
			jsonData.put("code", 0);
		} else {
			jsonData.put("code", -10418);
		}

		return SUCCESS;
	}

	/**
	 * 公司管理员取得单个用户信息
	 * 
	 * @return
	 */
	public String findOrdinaryUserInfo() {
		jsonData = new HashMap<String, Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		String u = companyManagerService.getOrdinaryUserInfo(username,
				cellphone);
		if (u == null) {
			jsonData.put("code", -10416);
		} else {
			jsonData.put("code", 0);
			jsonData.put("user", u);

		}
		return SUCCESS;
	}

	public String search() {
		jsonData = new HashMap<String, Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		String line = keyword;
		String pattern1 = "[0-9]*";

		Pattern r1 = Pattern.compile(pattern1);

		Matcher m1 = r1.matcher(line);
		if (m1.find()) {
			// String u1 = companyManagerService.searchWorkNo(username,keyword,
			// pageIndex);
			// String u2 =
			// companyManagerService.searchCellphone(username,keyword,
			// pageIndex);
			//
			// if(u1 == null && u2 == null){
			// jsonData.put("code", -10420);
			// }
			// else{
			// String u = u1 + u2;
			// jsonData.put("user", u);
			String u1 = companyManagerService.searchNumber(username, keyword,
					pageIndex);
			if (u1 == null) {
				jsonData.put("code", -10420);
			} else {
				jsonData.put("user", u1);

			}
		} else {
			String u3 = companyManagerService.searchName(username, keyword,
					pageIndex);
			if (u3 == null) {
				jsonData.put("code", -10420);
			} else {
				jsonData.put("user", u3);
			}
		}

		return SUCCESS;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 1) {
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;
		}
	}

}
