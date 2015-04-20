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

	public void activatePhone()
	{
        ActivateService service = new ActivateService();
		
		Date d = new Date();
		if(false == service.activatePhone("15071345115","5501",d))
		{
			System.out.println("手机验证码激活失败");
		}
		else 
		{
			System.out.println("手机验证码激活成功");
		}
		
	}
	
}
