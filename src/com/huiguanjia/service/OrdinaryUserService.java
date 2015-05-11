package com.huiguanjia.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.pojo.TempOrdinaryUser;
import com.huiguanjia.util.JSONUtil;

public class OrdinaryUserService {

	public class User {
		private String cellphone;
		private String companyName;
		private String departmentName;
		private String workNo;
		private boolean isCellphoneHide;
		private String name;
		private String email;
		private boolean isBindEmail;
		private boolean sex;
		private String officePhone;
		private String job;
		private String avatarUrl;
		private String officeLocation;

		public User() {

		}

		public User(String cellphone, String companyName,
				String departmentName, String workNo, boolean isCellphoneHide,
				String name, String email, boolean isBindEmail, boolean sex,
				String officePhone, String job, String avatarUrl,
				String officeLocation) {
			this.cellphone = cellphone;
			this.companyName = companyName;
			this.departmentName = departmentName;
			this.workNo = workNo;
			this.isCellphoneHide = isCellphoneHide;
			this.name = name;
			this.email = email;
			this.isBindEmail = isBindEmail;
			this.sex = sex;
			this.officePhone = officePhone;
			this.job = job;
			this.avatarUrl = avatarUrl;
			this.officeLocation = officeLocation;

		}

		public String getCellphone() {
			return cellphone;
		}

		public void setCellphone(String cellphone) {
			this.cellphone = cellphone;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public String getWorkNo() {
			return workNo;
		}

		public void setWorkNo(String workNo) {
			this.workNo = workNo;
		}

		public boolean getIsCellphoneHide() {
			return isCellphoneHide;
		}

		public void setIsCellphoneHide(boolean isCellphoneHide) {
			this.isCellphoneHide = isCellphoneHide;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public boolean getIsBindEmail() {
			return isBindEmail;
		}

		public void setIsBindEmail(boolean isBindEmail) {
			this.isBindEmail = isBindEmail;
		}

		public boolean getSex() {
			return sex;
		}

		public void setSex(boolean sex) {
			this.sex = sex;
		}

		public String getOfficePhone() {
			return officePhone;
		}

		public void setOfficePhone(String officePhone) {
			this.officePhone = officePhone;
		}

		public String getJob() {
			return job;
		}

		public void setJob(String job) {
			this.job = job;
		}

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}

		public String getOfficeLocation() {
			return officeLocation;
		}

		public void setOfficeLocation(String officeLocation) {
			this.officeLocation = officeLocation;
		}
	}

	public boolean logout(String username) {

		return true;
	}

	/**
	 * @info 
	 * @param cellphone
	 * @return
	 */
	public String findUserForRegister(String cellphone) {
		String userInfoStr;
		Map<String, Object> userInfoMap = new HashMap<String, Object>();

		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select o.name , o.companyAndCompanyAdmin.username , o.companyAndCompanyAdmin.companyName "
				+ "from OrdinaryUser as o where o.cellphone = ?";
		Object[] values = new Object[] { cellphone };

		Session sess = SessionDAO.getSession();
		List<Object[]> l = (List<Object[]>) aBaseDao.findObjectByHql(hql,
				values);
		Iterator it = l.iterator();

		if (false == it.hasNext())
			userInfoStr = null;
		else {
			Object[] obj = (Object[]) it.next();
			userInfoMap.put("name", obj[0]);
			userInfoMap.put("companyId", obj[1]);
			userInfoMap.put("companyName", obj[2]);

			userInfoStr = JSONArray.fromObject(userInfoMap).toString();
			System.out.println(userInfoStr);
		}

		SessionDAO.closeSession();

		return userInfoStr;
	}

	/**
	 * @info 普通用户注册
	 * @param
	 * @return
	 */
	public boolean register(String cellphone, String companyId, String name,
			String password) {
		boolean res;

		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();

		// 判断该用户是否已被录入某公司.如果是，则注册成功；如果不是，则添加到临时普通用户表中
		String hql1 = "select o.cellphone from OrdinaryUser as o where o.cellphone = ?";
		Object[] values1 = new Object[] { cellphone };
		List list1 = aBaseDao.findObjectByHql(hql1, values1);

		Transaction ts = sess.beginTransaction();
		try {
			if (1 == list1.size()) {
				Date d = new Date();
				String hql2 = "update OrdinaryUser set isRegister = ?,password = ?,registerTime = ? where cellphone = ?";
				Object[] values2 = new Object[] { true, password, d, cellphone };
				aBaseDao.updateObjectByHql(hql2, values2);

				ts.commit();
				res = true;
			} else {
				TempOrdinaryUser tmpOrdinaryUser = (TempOrdinaryUser) aBaseDao
						.findObjectById(TempOrdinaryUser.class, cellphone);
				if (null == tmpOrdinaryUser) {
					TempOrdinaryUser tou = new TempOrdinaryUser();
					tou.setCellphone(cellphone);
					tou.setName(name);
					tou.setPassword(password);
					CompanyAndCompanyAdmin ca = new CompanyAndCompanyAdmin();
					ca.setUsername(companyId);
					tou.setCompanyAndCompanyAdmin(ca);

					aBaseDao.saveObject(tou);

					ts.commit();
					res = true;
				} else {
					tmpOrdinaryUser.setName(name);
					tmpOrdinaryUser.setPassword(password);
					CompanyAndCompanyAdmin ca = new CompanyAndCompanyAdmin();
					ca.setUsername(companyId);
					tmpOrdinaryUser.setCompanyAndCompanyAdmin(ca);

					aBaseDao.updateObject(tmpOrdinaryUser);

					ts.commit();
					res = true;
				}
			}
		} catch (Exception e) {
			ts.rollback();
			res = false;
			System.out.println(e);
		}

		SessionDAO.closeSession();

		return res;
	}

	/**
	 * @info 普通用户登录
	 * @param username
	 * @param password
	 * @return 
	 */
	public String login(String username, String password, int loginMode) {
		String res = null;

		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();

		if (1 == loginMode) {
			String hql1 = "select o.isRegister from OrdinaryUser as o where o.cellphone = ? and o.password = ?";
			Object[] values1 = new Object[] { username, password };
			List list1 = (List) aBaseDao.findObjectByHql(hql1, values1);
			if (1 == list1.size()) {
				if (true == (Boolean) list1.get(0))
					res = username;
				else
					res = null;
			} else {
				res = null;
			}
		} else if (0 == loginMode) {
			String hql2 = "select o.cellphone from OrdinaryUser as o where o.email = ? and o.isBindEmail = ? "
					+ "and o.password = ? and o.isRegister = ?";
			Object[] values2 = new Object[] { username, true, password, true };
			List list2 = (List) aBaseDao.findObjectByHql(hql2, values2);
			if (1 == list2.size()) {
				res = (String) list2.get(0);
			} else {
				res = null;
			}
		}

		SessionDAO.getSession();

		return res;
	}
	
	/**
	 * @info 更改基本信息
	 * @param cellphone
	 * @param isCellphoneHide
	 * @param name
	 * @param email
	 * @param sex
	 * @param officePhone
	 * @param job
	 * @param avatarUrl
	 * @param officeLocation
	 * @return boolean
	 */
	public boolean updateInfo(String cellphone, boolean isCellphoneHide,
			String name, String email, boolean sex, String officePhone,
			String job, String avatarUrl, String officeLocation) {
		boolean res;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try {
			String hql = "update OrdinaryUser ordinaryUser set ordinaryUser.isCellphoneHide=?,ordinaryUser.name=?,ordinaryUser.email=?,ordinaryUser.sex=?,"
					+ "ordinaryUser.officePhone=?,ordinaryUser.job=?,ordinaryUser.avatarUrl=?,ordinaryUser.officeLocation=?"
					+ "where ordinaryUser.cellphone=?";
			Object[] values = new Object[] { isCellphoneHide, name, email, sex,
					officePhone, job, avatarUrl, officeLocation, cellphone };
			b.updateObjectByHql(hql, values);
			ts.commit();
			res = true;
		} catch (Exception e) {
			ts.rollback();
			res = false;
			System.out.println(e);
		}

		SessionDAO.closeSession();

		return res;
	}
	
	/**
	 * @info 更改密码
	 * @param cellphone
	 * @param password
	 * @return boolean
	 */
	public boolean updatePass(String cellphone, String password) {
		boolean res;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try {
			String hql = "update OrdinaryUser ordinaryUser set ordinaryUser.password=?"
					+ " where ordinaryUser.cellphone=?";
			Object[] values = new Object[] { password, cellphone };
			b.updateObjectByHql(hql, values);
			ts.commit();
			res = true;
		} catch (Exception e) {
			ts.rollback();
			res = false;
			System.out.println(e);
		}

		SessionDAO.closeSession();

		return res;
	}
	
	/**
	 * @info 绑定邮箱
	 * @param cellphone
	 * @return boolean
	 */
	public boolean updateIsBindEmail(String cellphone) {
		boolean res;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try {
			String hql = "update OrdinaryUser ordinaryUser set ordinaryUser.isBindEmail=?"
					+ " where ordinaryUser.cellphone=?";
			Object[] values = new Object[] { true, cellphone };
			b.updateObjectByHql(hql, values);
			ts.commit();
			res = true;
		} catch (Exception e) {
			ts.rollback();
			res = false;
			System.out.println(e);
		}

		SessionDAO.closeSession();

		return res;
	}
	
	/**
	 * @info 根据手机号查找用户基本信息
	 * @param cellphone
	 * @return class User
	 */
	public String findUserByCellphone(String cellphone) {
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();

		String hql = "select o from OrdinaryUser as o where o.cellphone=?";
		Object[] values = new Object[] { cellphone };
		OrdinaryUser or = (OrdinaryUser) b.findSingletonResultByHql(hql, values);
		if (null == or) {
			return null;
		}

		JSONObject obj = new JSONObject();
		obj.put("cellphone", or.getCellphone());
		obj.put("companyName",or.getCompanyAndCompanyAdmin().getCompanyName());
		obj.put("name", or.getName());
		obj.put("departmentName", or.getDepartment().getDepartmentName());
		obj.put("username",or.getCompanyAndCompanyAdmin().getUsername()); //companyId
		
		
		
		String objs = JSONUtil.serialize(obj);	
		SessionDAO.closeSession();
		return objs;
	}
	
	//获取用户所有个人信息
	public String findUserInfo(String cellphone) {
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();

		String hql = "select o from OrdinaryUser as o where o.cellphone=?";
		Object[] values = new Object[] { cellphone };
		OrdinaryUser or = (OrdinaryUser) b.findSingletonResultByHql(hql, values);
		if (null == or) {
			return null;
		}

		JSONObject obj = new JSONObject();
		obj.put("cellphone", or.getCellphone());
		obj.put("isCellphoneHide",or.getIsCellphoneHide());
		obj.put("companyName",or.getCompanyAndCompanyAdmin().getCompanyName());
		obj.put("name", or.getName());
		obj.put("departmentName", or.getDepartment().getDepartmentName());
//		obj.put("username",or.getCompanyAndCompanyAdmin().getUsername()); //companyId
		obj.put("registerTime", or.getRegisterTime());
		obj.put("email",or.getEmail());
		obj.put("isBindEmail", or.getIsBindEmail());
		obj.put("sex", or.getSex());
		obj.put("officePhone", or.getOfficePhone());
		obj.put("officeLocation", or.getOfficeLocation());
		obj.put("avatarUrl", or.getAvatarUrl());
		obj.put("workNo", or.getWorkNo());
		
		String objs = JSONUtil.serialize(obj);	
		SessionDAO.closeSession();
		
		return objs;
	}
	//获取用户姓名
	public String findUsername(String cellphone) {
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();

		String hql = "select o from OrdinaryUser as o where o.cellphone=?";
		Object[] values = new Object[] { cellphone };
		OrdinaryUser or = (OrdinaryUser) b.findSingletonResultByHql(hql, values);
		if (null == or) {
			return null;
		}
		String username = or.getName();	
		SessionDAO.closeSession();
		
		return username;
	}
}
