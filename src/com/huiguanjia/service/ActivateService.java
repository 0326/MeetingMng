package com.huiguanjia.service;

import java.util.Date;

import com.huiguanjia.dao.ActivateDao;
import com.huiguanjia.pojo.Activate;
import com.huiguanjia.util.MD5Util;

public class ActivateService {
	
	/**
	 * @info 存储用户激活请求
	 * @param userId String 用户名加密后的字符串
	 * @param activeCode String 加密后的验证码
	 * @param sendTime Date 发送的时间
	 * @param mode int 验证类型.
	 * 0: 邮箱验证
	 * 1: 为手机验证
	 * @return
	 */
	public boolean save(String userId, String activateCode, Date sendTime,
			boolean mode,String username){
		
		ActivateDao acd = new ActivateDao();
		return acd.save(userId, activateCode, sendTime, mode,username);
	}
	
	
	/**
	 * @info 激活验证
	 * @param userId
	 * @param activeCode
	 * @param sendTime
	 * @return String
	 * 有三种情况：0: 激活成功
	 * 1：该激活链接不存在
	 * 2: 该激活请求已过期，请重新发送激活请求
	 * 第一种情况返回公司名称字符串，第二种情况返回空字符串
	 */
	public String activate(String userId, String activeCode, Date activateTime){
		ActivateDao acd = new ActivateDao();
		return acd.activate(userId, activeCode, activateTime);
	}
	
	/**
	 * @info 手机验证码激活验证
	 * @param cellphone
	 * @param activateCode
	 * @param activateTime
	 * @return true，激活成功
	 *         false，验证码不存在、验证码错误或验证码过期
	 */
	public boolean activatePhone(String cellphone,String activateCode,Date activateTime)
	{
		ActivateDao acd = new ActivateDao();
		return acd.activatePhone(cellphone,activateCode,activateTime);
	}
}
