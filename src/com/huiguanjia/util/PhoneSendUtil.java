package com.huiguanjia.util;

public class PhoneSendUtil {
	
	/**
	 * @param cellphone
	 * @return 
	 * String: 发送成功，返回验证码字符串
	 * null: 发送失败，返回null
	 */
	public static String send(String cellphone) {
		String code = null;
		code = RandomUtil.randomStr(4);
		return code;
	}
}
