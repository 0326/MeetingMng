package com.huiguanjia.test;

import java.util.Date;

import com.huiguanjia.service.ActivateService;
import com.huiguanjia.util.PhoneSendUtil;

public class ActivateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	public void sendPhoneIdentifyCodeTest()
	{
        String activateCode;
		
		ActivateService as = new ActivateService();
		 
		activateCode = PhoneSendUtil.send("15071345115");
		if(null == activateCode)
		{
			System.out.println("发送手机验证码失败");
		}
		else
		{
			Date sendTime = new Date();
			as.save("15071345115", activateCode, sendTime, true, null);
			
			System.out.println("发送手机验证码成功");
		}
	}

}
