package com.huiguanjia.comet;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.pojo.Message;
import com.huiguanjia.service.MeetingService;
import com.huiguanjia.service.OrdinaryUserService;

/**
 * @author Ling
 * 通过添加消息前缀来定义推送类型
 * 前缀定长10个字符,前端通过识别前缀区分推送类型
 * 详细消息内容（长度，数量，类型,响应地址）前端通过解析msgContent获取
 */
/**
 * @author Ling
 *
 */
public class CometCfg {
	
	/**
	 *广播消息，对所有用户发送 
	 */
	public static final String BROADCAST = "broadcast*";
	
	/**
	 * 默认消息类型，如果不清楚是什么类型，直接用该类型
	 */
	public static final String DEFAULT = "default***";
	
	/**
	 *用户登录时消息推送 
	 */
	public static final String LOGIN = "login*****";
	
	/**
	 *用户掉线时消息推送 
	 */
	public static final String CLOSE = "close*****";
	
	/**
	 *会议创建通知 
	 */
	public static final String MEET_CREATE = "mcrea*****";
	
	/**
	 *会议更新通知 
	 */
	public static final String MEET_UPDATE = "mupda*****";

	/**
	 *会议取消通知 
	 */
	public static final String MEET_CANCEL = "mcanc*****";
	
	/**
	 *邀请办会人通知 
	 */
	public static final String ORG_INVITE = "oinvi*****";
	
	/**
	 *受邀办会人同意参与 
	 */
	public static final String ORG_AGREE = "oagre*****";
	
	/**
	 *受邀办会人拒绝参与 
	 */
	public static final String ORG_REFUSE = "orefu*****";
	
	/**
	 *受邀办会人待定 
	 */
	public static final String ORG_HOLD = "ohold*****";

	/**
	 *邀请参会人通知 
	 */
	public static final String PART_INVITE = "pinvi*****";
	
	/**
	 *受邀参会人同意参与 
	 */
	public static final String PART_AGREE = "pagre*****";
	
	/**
	 *受邀参会人拒绝参与 
	 */
	public static final String PART_REFUSE = "prefu*****";
	
	/**
	 * 受邀参会人待定
	 */
	public static final String PART_HOLD = "phold*****";
	
	/**
	 *已同意参会人/办会人状态修改 
	 */
	public static final String MEETER_UPDATE = "opupd*****";
	
	//话题评论相关通知
	//....
	
	
	
	/**
	 * 新建推送消息，返回对应类型消息
	 * @return
	 */
	public static Message createMsg(String username,String url,String body,String type){
		JSONObject msgContent = new JSONObject();
		//设定消息类型，用于前端消息解析
		msgContent.put("type",type);
		//设定消息点击跳转链接
		msgContent.put("url", url);
		//设定消息主题内容
		msgContent.put("body", body);
		
		return  new Message(
				username,//被邀请用户
				msgContent.toJSONString(),//msgContent,消息内容
				false,//isPush,是否已推送
				false,//isChecked,用户是否已查看
				String.valueOf(new Date().getTime())//创建时间createTime
			);
	}

	/**
	 * 新建推送消息，返回对应类型消息
	 * @param from 发送人手机号id
	 * @param to   接收人手机号id
	 * @param type 消息类型
	 * @param rid  与消息内容相关的其他表id
	 * @return
	 */
	public static Message createMessage(String from,String to,String type,String rid){
		//msgContent = {from, to, type, body:
		//{meetingId,meetingName,meetingStartTime,meetingContent,meetingLocation} }
		JSONObject msgContent = new JSONObject();
		OrdinaryUserService userService = new OrdinaryUserService();
		msgContent.put("from", userService.findUsername(from));
		msgContent.put("to", userService.findUsername(to));
		msgContent.put("type", type);
		msgContent.put("body", CometCfg.setMsgContentBody(type,rid));

		return  new Message(
				to,                                   //被邀请用户id
				msgContent.toJSONString(),            //msgContent,消息内容
				false,                                //isPush,是否已推送
				false,                                //isChecked,用户是否已查看
				String.valueOf(new Date().getTime())  //创建时间createTime
			);
	}
	
	//msgContent.body 参数要根据type来设置
	private static String setMsgContentBody(String type,String rid){
		String res="这是消息主体内容";
		if(type == CometCfg.ORG_INVITE || type == CometCfg.PART_INVITE){
			//邀请会议人员需要会议相关信息，所以rid应该传入meetingId
			MeetingService ms =new MeetingService();
			res = ms.findBrifyInfo(rid);
		}
		return res;
	}
}
