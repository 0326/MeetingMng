package com.huiguanjia.action;

import java.util.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.MeetingService;  //if import com.huigunajia.service.MeetingBulletinService?

public class MeetingAction  extends ActionSupport{
	public String createMeeting() {
		
		return SUCCESS;
	}
	
}
