package com.huiguanjia.comet;

/**
 * @author Ling
 * 通过添加消息前缀来定义推送类型
 * 前缀定长10个字符,前端通过识别前缀区分推送类型
 * 详细消息内容（长度，数量，类型）前端通过解析msgContent获取
 */
public class CometCfg {
	
	//广播消息，对所有用户发送
	public static final String BROADCAST = "broadcast*";
	//用户登录时消息推送
	public static final String LOGIN = "login*****";
	//用户掉线时消息推送
	public static final String CLOSE = "close*****";
	
	//会议创建通知
	public static final String MEET_CREATE = "mcrea*****";
	//会议更新通知
	public static final String MEET_UPDATE = "mupda*****";
	//会议取消通知
	public static final String MEET_CANCEL = "mcanc*****";
	
	//邀请办会人通知
	public static final String ORG_INVITE = "oinvi*****";
	//受邀办会人同意参与
	public static final String ORG_AGREE = "oagre*****";
	//受邀办会人拒绝参与
	public static final String ORG_REFUSE = "orefu*****";
	//受邀办会人待定
	public static final String ORG_HOLD = "ohold*****";

	//邀请参会人通知
	public static final String PART_INVITE = "pinvi*****";
	//受邀参会人同意参与
	public static final String PART_AGREE = "pagre*****";
	//受邀参会人拒绝参与
	public static final String PART_REFUSE = "prefu*****";
	//受邀参会人待定
	public static final String PART_HOLD = "phold*****";
	
	//已同意参会人/办会人状态修改
	public static final String MEETER_UPDATE = "opupd*****";
	
	//话题评论相关通知
}
