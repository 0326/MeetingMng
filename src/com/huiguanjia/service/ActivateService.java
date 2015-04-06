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
	public boolean save(String userId, String activeCode, Date sendTime,
			int mode){
		
		ActivateDao acd = new ActivateDao();
		return acd.save(userId, activeCode, sendTime, mode);
	}
	
	/**
	 * @info 激活验证
	 * @param userId
	 * @param activeCode
	 * @param sendTime
	 * @return int
	 * 0: 激活成功
	 * 1：该激活链接不存在
	 * 2: 该激活请求已过期，请重新发送激活请求
	 */
	public int active(String userId, String activeCode, Date sendTime){
		ActivateDao acd = new ActivateDao();
		return acd.active(userId, activeCode, sendTime);
	}
}
