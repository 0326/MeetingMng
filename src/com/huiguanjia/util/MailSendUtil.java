package com.huiguanjia.util;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailSendUtil { 

	private MimeMessage mimeMsg; //MIME邮件对象 
	private Session session; //邮件会话对象 
	private Properties props; //系统属性 
	//smtp认证用户名和密码 
	private String username; 
	private String password; 
	private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象 
	 
	/**
	 * Constructor
	 * @param smtp 邮件发送服务器
	 */
	public MailSendUtil(String smtp){ 
		setSmtpHost(smtp); 
		createMimeMessage(); 
	} 

	/**
	 * 设置邮件发送服务器
	 * @param hostName String 
	 */
	private void setSmtpHost(String hostName) { 
//		System.out.println("设置系统属性：MailSendUtil.smtp.host = "+hostName); 
		if(props == null)
			props = System.getProperties(); //获得系统属性对象 	
		props.put("MailSendUtil.smtp.host",hostName); //设置SMTP主机 
	} 


	/**
	 * 创建MIME邮件对象  
	 * @return
	 */
	private boolean createMimeMessage() 
	{ 
		try { 
//			System.out.println("准备获取邮件会话对象！"); 
			session = Session.getDefaultInstance(props,null); //获得邮件会话对象 
		} 
		catch(Exception e){ 
//			System.err.println("获取邮件会话对象时发生错误！"+e); 
			return false; 
		} 
	
		System.out.println("准备创建MIME邮件对象！"); 
		try { 
			mimeMsg = new MimeMessage(session); //创建MIME邮件对象 
			mp = new MimeMultipart(); 
		
			return true; 
		} catch(Exception e){ 
			System.err.println("创建MIME邮件对象失败！"+e); 
			return false; 
		} 
	} 	
	
	/**
	 * 设置SMTP是否需要验证
	 * @param need
	 */
	private void setNeedAuth(boolean need) { 
//		System.out.println("设置smtp身份认证：MailSendUtil.smtp.auth = "+need); 
		if(props == null) props = System.getProperties(); 
		if(need){ 
			props.put("MailSendUtil.smtp.auth","true"); 
		}else{ 
			props.put("MailSendUtil.smtp.auth","false"); 
		} 
	} 

	/**
	 * 设置用户名和密码
	 * @param name
	 * @param pass
	 */
	private void setNamePass(String name,String pass) { 
		username = name; 
		password = pass; 
	} 

	/**
	 * 设置邮件主题
	 * @param MailSendUtilSubject
	 * @return
	 */
	private boolean setSubject(String MailSendUtilSubject) { 
//		System.out.println("设置邮件主题！"); 
		try{ 
			mimeMsg.setSubject(MailSendUtilSubject); 
			return true; 
		} 
		catch(Exception e) { 
			System.err.println("设置邮件主题发生错误！"); 
			return false; 
		} 
	}
	
	/** 
	 * 设置邮件正文
	 * @param MailSendUtilBody String 
	 */ 
	private boolean setBody(StringBuffer MailSendUtilBody) { 
		try{ 
			BodyPart bp = new MimeBodyPart(); 
			bp.setContent(""+MailSendUtilBody,"text/html;charset=GBK"); 
			mp.addBodyPart(bp); 
		
			return true; 
		} catch(Exception e){ 
		System.err.println("设置邮件正文时发生错误！"+e); 
		return false; 
		} 
	} 
	/** 
	 * 设置发信人
	 * @param from String 
	 */ 
	private boolean setFrom(String from) { 
//		System.out.println("设置发信人！"); 
		try{ 
			mimeMsg.setFrom(new InternetAddress(from)); //设置发信人 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 
	} 
	/** 
	 * 设置收信人
	 * @param to String 
	 */ 
	private boolean setTo(String to){ 
		if(to == null)return false; 
		try{ 
			mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to)); 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 	
	} 
	
	/** 
	 * 发送邮件
	 */ 
	private boolean sendOut() 
	{ 
		try{ 
			mimeMsg.setContent(mp); 
			mimeMsg.saveChanges(); 
//			System.out.println("正在发送邮件...."); 
			
			Session MailSendUtilSession = Session.getInstance(props,null); 
			Transport transport = MailSendUtilSession.getTransport("smtp"); 
			transport.connect((String)props.get("MailSendUtil.smtp.host"),username,password); 
			transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO)); 
			//transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC)); 
			//transport.send(mimeMsg); 
			
//			System.out.println("发送邮件成功！"); 
			transport.close(); 
			
			return true; 
		} catch(Exception e) { 
			System.err.println("邮件发送失败！"+e); 
			return false; 
		} 
	} 

	public static boolean send(String email,String activelink) {
		MailSendUtil theMailSendUtil = new MailSendUtil("smtp.sina.com");
		theMailSendUtil.setNeedAuth(true); 
		
		theMailSendUtil.setFrom("huiguanjia@sina.com");
		theMailSendUtil.setSubject("会管家账号激活");
		theMailSendUtil.setTo(email);

		StringBuffer  content = new StringBuffer();
		content.append("<p>欢迎注册会管家，请在24小时内点击以下链接激活，逾期无效：");
		content.append("<a href='"+activelink+"'>"+activelink+"</a></p>");
		
		theMailSendUtil.setBody(content);
		theMailSendUtil.setNamePass("huiguanjia@sina.com","huiguanjia2015");
		
		if(!theMailSendUtil.sendOut()) return false;
		return true;
	}
} 

