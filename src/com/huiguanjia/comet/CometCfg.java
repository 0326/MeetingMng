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
	
}
