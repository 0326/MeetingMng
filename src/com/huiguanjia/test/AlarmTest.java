package com.huiguanjia.test;

import com.huiguanjia.pojo.Alarm;
import com.huiguanjia.service.AlarmService;

public class AlarmTest {
	public static void main(String[] args){
		AlarmTest test = new AlarmTest();
		test.create();
	}
	
	private void create(){
		AlarmService as = new AlarmService();
		Alarm alarm = new Alarm();
	}
}
