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
	
	public static String UUID(){
		String s = java.util.UUID.randomUUID().toString();
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
	}

	/**
	 * 比较时间戳大小
	 * @param t1
	 * @param t2
	 * @return true:t1>t2; false:t1<t2
	 */
	public static boolean compareTimer(String t1,String t2){
		return Long.parseLong(t1) > Long.parseLong(t2);
	}
	public static void main(String[] args){
		for(int i=0; i<10; i++){
			System.out.println(RandomUtil.UUID());
		}
	}
}
