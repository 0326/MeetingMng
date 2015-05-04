package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.authorityvalidate.MeetingTopicValidate;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.CompanyAndCompanyAdminDao;
import com.huiguanjia.dao.IndustryDao;
import com.huiguanjia.dao.ProvinceAndCityDao;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.dao.TempCompanyAndCompanyAdminDao;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.pojo.TempCompanyAndCompanyAdmin;
import com.huiguanjia.util.JSONUtil;
import com.huiguanjia.util.MD5Util;
import java.util.ArrayList;

public class CompanyManagerService {

	private class Stuff {
		private String cellphone;
		private String name;
		private String job;
		private String departName;
		private String workNo;
		
		public Stuff(){
			
		}
		public Stuff(String cellphone,String name,String job,
				String departName,String workNo){
			this.cellphone = cellphone;
			this.name = name;
			this.job = job;
			this.departName = departName;
			this.workNo = workNo;
		}
		public String getCellphone() {
			return cellphone;
		}
		public void setCellphone(String cellphone) {
			this.cellphone = cellphone;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		public String getDepartName() {
			return departName;
		}
		public void setDepartName(String departName) {
			this.departName = departName;
		}
		public String getWorkNo() {
			return workNo;
		}
		public void setWorkNo(String workNo) {
			this.workNo = workNo;
		}
	}
	/**
	 * @info 注册时验证用户名是否已存在
	 * @param username
	 * @return boolean；若存在，返回true
	 */

	public boolean usernameRepeat(String username)
	{
		boolean res;
		CompanyAndCompanyAdminDao aCompanyAndCompanyAdminDao = new CompanyAndCompanyAdminDao();
		
		res = aCompanyAndCompanyAdminDao.usernameExist(username);
		
		return res;
	}
	
	/**
	 * @info 注册时验证公司名是否已存在
	 * @param companyName
	 * @return boolean；若存在，返回true
	 */
	public boolean companyNameRepeat(String companyName)
	{
		boolean res;
		CompanyAndCompanyAdminDao aCompanyAndCompanyAdminDao = new CompanyAndCompanyAdminDao();
		
		res = aCompanyAndCompanyAdminDao.companyNameExist(companyName);
		
		return res;
	}
	
	/**
	 * @info 公司管理员添加或者修改普通用户基本信息时，工号不能重复
	 * @param username
	 * @param workNo
	 * @return
	 */
	public boolean workNoRepeat(String username,String workNo)
	{
		boolean res=false;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			
			String hql = "select o.workNo from OrdinaryUser as o where o.companyAndCompanyAdmin.username = ? ";
			Object[] values = new Object[]{username};
			List<OrdinaryUser> l = (List<OrdinaryUser>)b.findObjectByHql(hql, values);
			if(l.contains(workNo)){
			res = true;
			}
		}
		catch(Exception e)
		{
			ts.rollback();
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 公司管理员注册
	 * @param username
	 * @param password
	 * @param type
	 * @param companyName
	 * @param location
	 * @return int
	 */
	public int register(String username,String password,String type,
			String companyName,String location)
	{
		String md5pass = MD5Util.MD5Code(password);
		CompanyAndCompanyAdminDao aCompanyAndCompanyAdminDao = new CompanyAndCompanyAdminDao();
		IndustryDao aIndustryDao = new IndustryDao();
		ProvinceAndCityDao aProvinceAndCityDao = new ProvinceAndCityDao();
		TempCompanyAndCompanyAdminDao aTempCompanyAndCompanyAdminDao = new TempCompanyAndCompanyAdminDao();
		if(true == aCompanyAndCompanyAdminDao.usernameExist(username))
			return 1;
		else if(true == aCompanyAndCompanyAdminDao.companyNameExist(companyName))
			return 2;
		else if(false == aIndustryDao.industryCodeValid(type))
			return 3;
		else if(false == aProvinceAndCityDao.cityCodeValid(location))
			return 4;
		else if(true == aTempCompanyAndCompanyAdminDao.add(username, md5pass, type, companyName, location))
			return 6;
		else 
			return 5;
		
	}
	
    /**
     * @info 激活成功后，将公司从临时表移动到正规表
     * @param companyName
     * @return int
     * 1,操作成功；
     * 2,插入正规表失败；
     * 3，从临时表中删除记录失败。
     */
	public int registerAfterActivate(String username)
	{	
		TempCompanyAndCompanyAdminDao tcad = new TempCompanyAndCompanyAdminDao();
		TempCompanyAndCompanyAdmin tca = tcad.find(username);
		
		CompanyAndCompanyAdminDao cad = new CompanyAndCompanyAdminDao();
		boolean a = cad.addByTemp(tca);
		if(false == a)
			return 2;
		
		boolean b = tcad.delete(username);
		if(false == b)
			return 3;
		
		return 1;
	}
	
	/**
	 * 
	 * @param username String
	 * @param password String 
	 * @return boolean
	 * true,用户名密码匹配
	 * false，用户名不存在或密码错误
	 */
	public boolean login(String username,String password){
		
		BaseDAO b = new BaseDAO();
		String md5pass = MD5Util.MD5Code(password);
		Session sess = SessionDAO.getSession();
		CompanyAndCompanyAdmin ca = (CompanyAndCompanyAdmin)b.findObjectById(CompanyAndCompanyAdmin.class, username);
		SessionDAO.closeSession();
		if(null == ca)
			return false;
		else if(true == md5pass.equals(ca.getPassword()))
			return true;
		else 
			return false;
	}
	
	
	/**
	 * @info 公司管理员获取个人信息
	 * @param username
	 * @return
	 */
	public JSONObject getInfo(String username){	
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		CompanyAndCompanyAdmin ca = (CompanyAndCompanyAdmin)b.findObjectById(CompanyAndCompanyAdmin.class, username);
		
		if(null == ca) return null;
		
		JSONObject obj = new JSONObject();
		obj.put("companyName", ca.getCompanyName());
		obj.put("industry", ca.getIndustry());
		obj.put("provinceAndCity", ca.getProvinceAndCity());
		obj.put("username", ca.getUsername());
		obj.put("avatarUrl", ca.getAvatarUrl());
		obj.put("name", ca.getName());
		obj.put("sex", ca.getSex());
		obj.put("email", ca.getEmail());
		obj.put("cellphone", ca.getCellphone());
		obj.put("officePhone", ca.getOfficePhone());
		obj.put("officeLocation", ca.getOfficeLocation());
		SessionDAO.closeSession();
		
//		String stres = JSONUtil.serialize(obj);
		return obj;
	}
	
	/**
	 * @info 公司管理员获取全部用户信息
	 * @param username
	 * @return
	 */
	public String getInfos(String username,int pageIndex){	
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		
		String hql = "select o from OrdinaryUser as o where o.companyAndCompanyAdmin.username = ?"; 
		Object[] values = new Object[]{username};
		//List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)b.findPagingObjectByHql(hql, 10*(pageIndex-1), 10*pageIndex, values);
		JSONObject obj = b.findPagingObjectByHqlPro(hql, 10*(pageIndex-1), 10*pageIndex, values);
		List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)obj.get("list");
		if(null == or){
			return null;
		}
		
		List<Stuff> res = new ArrayList<Stuff>();
		for(int i=0;i<or.size();i++){
			Stuff stuff = new Stuff();
			stuff.setCellphone(or.get(i).getCellphone());
			stuff.setName(or.get(i).getName());
			stuff.setJob(or.get(i).getJob());
			stuff.setWorkNo(or.get(i).getWorkNo());
			stuff.setDepartName(or.get(i).getDepartment().getDepartmentName());
			stuff.setDepartName("departName");
			res.add(stuff);
		}
		
		JSONObject result = new JSONObject();
		result.put("list", res);
		result.put("total", obj.get("total"));
		String stres = JSONUtil.serialize(result);
		System.out.println("json:"+stres);
		
		SessionDAO.closeSession();
		
		return stres;
	}
	
	/**
	 * @info 公司管理员修改账号信息，action层直接传pojo对象来获取数据
	 * @param admin
	 * @return
	 */
	public boolean updateInfo(String username,String email,String name,boolean sex,String officePhone,String avatarUrl,String cellphone,String officeLocation){
		boolean res;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "update CompanyAndCompanyAdmin u set u.email=?,u.name=?,u.sex=?,u.officePhone=?,u.avatarUrl=?,u.cellphone=?,u.officeLocation=?" +
					"where u.username=?";
			Object[] values = new Object[]{email,name,sex,officePhone,avatarUrl,cellphone,officeLocation,username};
			b.updateObjectByHql(hql,values);
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = false;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}


	/**
	 * @info 公司管理员修改密码
	 * @param admin 
	 * @param newpass String 新密码
	 * @return
	 */
	public int updatePass(String username,String password,String newpass){
		int res;
		
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			CompanyAndCompanyAdmin ca = (CompanyAndCompanyAdmin)b.findObjectById(CompanyAndCompanyAdmin.class, username);
			if(ca.getPassword().equals(MD5Util.MD5Code(password)) == false){
				res = -1;
			}
			else{
				String md5pass = MD5Util.MD5Code(newpass);
				String hql = "update CompanyAndCompanyAdmin u set u.password=? where u.username=?";
				Object[] values = new Object[]{md5pass,username};
				b.updateObjectByHql(hql,values);
				ts.commit();
				res = 0;
			}
			
		}
		catch(Exception e)
		{
			ts.rollback();
			res = -100;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info公司管理员添加普通用户
	 * @param u
	 * @return
	 */
	public boolean addOrdinaryUser(OrdinaryUser u){
		
		boolean res;
		BaseDAO aDAO = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			aDAO.saveObject(u);
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = false;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}

	/**
	 * @info公司管理员删除普通用户
	 * @param cellphone
	 * @return
	 */
	public boolean deleteOrdinaryUser(String cellphone) {
		boolean res;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "delete OrdinaryUser u where u.cellphone=?";
			Object[] values = new Object[]{cellphone};
			b.deleteObjectByHql(hql,values);
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = false;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 公司管理员修改普通用户基本信息
	 * @param u
	 * @return
	 */
	public boolean updateOrdinaryUser(OrdinaryUser u) {
		boolean res;
		BaseDAO aDAO = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			aDAO.updateObject(u);
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = false;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}


	/**
	 * @info 公司管理员获取单个用户信息
	 * @param username
	 * @return
	 */
	public OrdinaryUser getOrdinaryUserInfo(String username,String cellphone) {
	
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();

		String hql = "select o from OrdinaryUser as o where o.companyAndCompanyAdmin.username=? and o.cellphone=?"; 
		Object[] values = new Object[]{username,cellphone};
		OrdinaryUser or = (OrdinaryUser)b.findSingletonResultByHql(hql,values);
		SessionDAO.closeSession();
		if(null == or)
			return null;
		else 
			return or;
	}
	
//	public JSONObject searchWorkNo(String username,String keyword,int pageIndex){	
//		BaseDAO b = new BaseDAO();	
//		Session sess = SessionDAO.getSession();
//		
//		String hql = "select o from OrdinaryUser as o where o.workNo like ? and o.companyAndCompanyAdmin.username=?"; 
//		Object[] values = new Object[]{keyword+'%',username};
//		//List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)b.findPagingObjectByHql(hql, 10*(pageIndex-1), 10*pageIndex, values);
//		JSONObject obj = b.findPagingObjectByHqlPro(hql, 10*(pageIndex-1), 10*pageIndex, values);
//		List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)obj.get("list");
////		System.out.println(obj.get("list"));
//		if(null == or){
//			return null;
//		}
//		
//		List<Stuff> res = new ArrayList<Stuff>();
//		for(int i=0;i<or.size();i++){
//			Stuff stuff = new Stuff();
//			stuff.setCellphone(or.get(i).getCellphone());
//			stuff.setName(or.get(i).getName());
//			stuff.setJob(or.get(i).getJob());
//			stuff.setWorkNo(or.get(i).getWorkNo());
//			stuff.setDepartName(or.get(i).getDepartment().getDepartmentName());
////			stuff.setDepartName("departName");
//			res.add(stuff);
//		}
//		
//		JSONObject result = new JSONObject();
//		result.put("list", res);
//		result.put("total", obj.get("total"));
////		String stres = JSONUtil.serialize(result);
////		System.out.println("json:"+stres);
//		
//		SessionDAO.closeSession();
//		
//		return result;
//				
//	}
//	
//	public String searchCellphone(String username,String keyword,int pageIndex){	
//		BaseDAO b = new BaseDAO();	
//		Session sess = SessionDAO.getSession();
//		
//		String hql = "select o from OrdinaryUser as o where o.cellphone like ? and o.companyAndCompanyAdmin.username=?"; 
//		Object[] values = new Object[]{"%"+keyword+"%",username};
//		//List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)b.findPagingObjectByHql(hql, 10*(pageIndex-1), 10*pageIndex, values);
//		JSONObject obj = b.findPagingObjectByHqlPro(hql, 10*(pageIndex-1), 10*pageIndex, values);
//		List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)obj.get("list");
////		System.out.println(obj.get("list"));
//		if(null == or){
//			return null;
//		}
//		
//		List<Stuff> res = new ArrayList<Stuff>();
//		for(int i=0;i<or.size();i++){
//			Stuff stuff = new Stuff();
//			stuff.setCellphone(or.get(i).getCellphone());
//			stuff.setName(or.get(i).getName());
//			stuff.setJob(or.get(i).getJob());
//			stuff.setWorkNo(or.get(i).getWorkNo());
//			stuff.setDepartName(or.get(i).getDepartment().getDepartmentName());
////			stuff.setDepartName("departName");
//			res.add(stuff);
//		}
//		
//		JSONObject result = new JSONObject();
//		result.put("list", res);
//		result.put("total", obj.get("total"));
//		String stres = JSONUtil.serialize(result);
////		System.out.println("json:"+stres);
//		
//		SessionDAO.closeSession();
//		
//		return stres;
//	
//	}
//	
	public String searchNumber(String username,String keyword,int pageIndex){	
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		
		String hql = "select o from OrdinaryUser as o where o.cellphone like ? or o.workNo like ? and o.companyAndCompanyAdmin.username=?"; 
		Object[] values = new Object[]{"%"+keyword+"%",keyword+"%",username};
		//List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)b.findPagingObjectByHql(hql, 10*(pageIndex-1), 10*pageIndex, values);
		JSONObject obj = b.findPagingObjectByHqlPro(hql, 10*(pageIndex-1), 10*pageIndex, values);
		List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)obj.get("list");
//		System.out.println(obj.get("list"));
		if(null == or){
			return null;
		}
		
		List<Stuff> res = new ArrayList<Stuff>();
		for(int i=0;i<or.size();i++){
			Stuff stuff = new Stuff();
			stuff.setCellphone(or.get(i).getCellphone());
			stuff.setName(or.get(i).getName());
			stuff.setJob(or.get(i).getJob());
			stuff.setWorkNo(or.get(i).getWorkNo());
			stuff.setDepartName(or.get(i).getDepartment().getDepartmentName());
//			stuff.setDepartName("departName");
			res.add(stuff);
		}
		
		JSONObject result = new JSONObject();
		result.put("list", res);
		result.put("total", obj.get("total"));
		String stres = JSONUtil.serialize(result);
//		System.out.println("json:"+stres);
		
		SessionDAO.closeSession();
		
		return stres;
	
	}
	
	

	public String searchName(String username,String keyword,int pageIndex){	
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		
		String hql = "select o from OrdinaryUser as o where o.name like ? and o.companyAndCompanyAdmin.username=?"; 
		Object[] values = new Object[]{'%'+keyword+'%',username};
		//List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)b.findPagingObjectByHql(hql, 10*(pageIndex-1), 10*pageIndex, values);
		JSONObject obj = b.findPagingObjectByHqlPro(hql, 10*(pageIndex-1), 10*pageIndex, values);
		List<OrdinaryUser> or = (ArrayList<OrdinaryUser>)obj.get("list");
//		System.out.println(obj.get("list"));
		if(null == or){
			return null;
		}
		
		List<Stuff> res = new ArrayList<Stuff>();
		for(int i=0;i<or.size();i++){
			Stuff stuff = new Stuff();
			stuff.setCellphone(or.get(i).getCellphone());
			stuff.setName(or.get(i).getName());
			stuff.setJob(or.get(i).getJob());
			stuff.setWorkNo(or.get(i).getWorkNo());
			stuff.setDepartName(or.get(i).getDepartment().getDepartmentName());
//			stuff.setDepartName("departName");
			res.add(stuff);
		}
		
		JSONObject result = new JSONObject();
		result.put("list", res);
		result.put("total", obj.get("total"));
		String stres = JSONUtil.serialize(result);
//		System.out.println("json:"+stres);
		
		SessionDAO.closeSession();
		
		return stres;
	
	}
	
	public String searchCompanyByName(String companyName)
	{
		String companyListStr;
		List<Map> companyList = new ArrayList<Map>();
		
		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select ca.username , ca.companyName from CompanyAndCompanyAdmin as ca " +
				"where ca.companyName like ?";
		Object[] values = new Object[]{'%' + companyName + '%'};
		
		Session sess = SessionDAO.getSession();
		List<Object[]> l = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
		Iterator it = l.iterator();
		
		if(false == it.hasNext())
		{
			companyListStr = null;
		}
		else
		{
			Object[] obj;
			while(true == it.hasNext())
			{
				obj = (Object[])it.next();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", obj[0]);
				map.put("name", obj[1]);
				
				companyList.add(map);
			}
			
			companyListStr = JSONArray.fromObject(companyList).toString();
		}
		
		SessionDAO.closeSession();
		String stres = JSONUtil.serialize(companyListStr);

		return stres;
	}
}
