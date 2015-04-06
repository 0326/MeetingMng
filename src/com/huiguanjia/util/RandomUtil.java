package com.huiguanjia.util;

/**
 * @author Ling
 * @info 随机数生成接口
 */
public class RandomUtil {
	
	
	/**
	 * @param num int 需要生成的数字个数
	 * @return String 随机数字组合
	 */
	public static String randomStr(int num){
		if(num<1) num = 1;
		String res="";
		for(int i=0;i<num;i++){
			res+=(int)(Math.random()*10);
		}
		return res;
	}
}
