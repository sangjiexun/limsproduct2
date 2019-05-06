package net.zjcclims.util.mail;

import java.io.File;
import java.util.Date; 
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address; 
import javax.mail.BodyPart; 
import javax.mail.Message; 
import javax.mail.MessagingException; 
import javax.mail.Multipart; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 
import javax.mail.internet.MimeUtility;

import java.util.Vector;
import java.util.Enumeration; 
/** 
* 简单邮件（不带附件的邮件）发送器 
*/ 
public class SimpleMailSender  { 
/** 
  * 以文本格式发送邮件 
  * @param mailInfo 待发送的邮件的信息 
  */ 
	public boolean sendTextMail(MailSenderInfo mailInfo) { 
	  // 判断是否需要身份认证 
	  MyAuthenticator authenticator = null; 
	  Properties pro = mailInfo.getProperties();
	  if (mailInfo.isValidate()) { 
	  // 如果需要身份认证，则创建一个密码验证器 
		authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()); 
	  }
	  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
	  Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
	  try { 
	  // 根据session创建一个邮件消息 
	  Message mailMessage = new MimeMessage(sendMailSession); 
	  // 创建邮件发送者地址 
	  Address from = new InternetAddress(mailInfo.getFromAddress()); 
	  // 设置邮件消息的发送者 
	  mailMessage.setFrom(from); 
	  // 创建邮件的接收者地址，并设置到邮件消息中 
	  Address to = new InternetAddress(mailInfo.getToAddress()); 
	  mailMessage.setRecipient(Message.RecipientType.TO,to); 
	  // 设置邮件消息的主题 
	  mailMessage.setSubject(mailInfo.getSubject()); 
	  // 设置邮件消息发送的时间 
	  	mailMessage.setSentDate(new Date()); 
	  // 设置邮件消息的主要内容 
	  String mailContent = mailInfo.getContent(); 
	  mailMessage.setText(mailContent); 
	  // 发送邮件 
	  Transport.send(mailMessage);
	  return true; 
	  } catch (MessagingException ex) { 
		  ex.printStackTrace(); 
	  } 
	  return false; 
	} 
	
	/** 
	  * 以HTML格式发送邮件 
	  * @param mailInfo 待发送的邮件信息 
	  */ 
	public static boolean sendHtmlMail(MailSenderInfo mailInfo){ 
	  // 判断是否需要身份认证 
	  MyAuthenticator authenticator = null;
	  Properties pro = mailInfo.getProperties();
	  //如果需要身份认证，则创建一个密码验证器  
	  if (mailInfo.isValidate()) { 
		authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
	  } 
	  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
	  Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
	  try { 
	  // 根据session创建一个邮件消息 
	  Message mailMessage = new MimeMessage(sendMailSession); 
	  // 创建邮件发送者地址 
	  Address from = new InternetAddress(mailInfo.getFromAddress()); 
	  // 设置邮件消息的发送者 
	  mailMessage.setFrom(from); 
	  // 创建邮件的接收者地址，并设置到邮件消息中 
	  Address to = new InternetAddress(mailInfo.getToAddress()); 
	  // Message.RecipientType.TO属性表示接收者的类型为TO 
	  mailMessage.setRecipient(Message.RecipientType.TO,to); 
	  // 设置邮件消息的主题 
	  mailMessage.setSubject(mailInfo.getSubject()); 
	  // 设置邮件消息发送的时间 
	  mailMessage.setSentDate(new Date()); 
	  // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象 
	  Multipart mainPart = new MimeMultipart(); 
	  // 创建一个包含HTML内容的MimeBodyPart 
	  BodyPart html = new MimeBodyPart(); 
	  // 设置HTML内容 
	  html.setContent(mailInfo.getContent(), "text/html; charset=utf-8"); 
	  mainPart.addBodyPart(html); 
	  // 将MiniMultipart对象设置为邮件内容 
	  mailMessage.setContent(mainPart); 
	  // 发送邮件 
	  Transport.send(mailMessage); 
	  return true; 
	  } catch (MessagingException ex) { 
		  ex.printStackTrace(); 
	  } 
	  return false; 
	} 

	/**
	   * 以html发送邮件(带附件)
	   * 
	   * @param mailInfo
	   *            待发送的邮件的信息
	   */
	/*MimeBodyPart mbpContent = new MimeBodyPart();// 向Multipart添加正文
	mbpContent.setContent(mailInfo.getContent(),
	    "text/html;charset=GB2312");
	mainPart.addBodyPart(mbpContent);// 向MimeMessage添加（Multipart代表正文）
	//Vector file = mailInfo.getAttachFileNames();
	Vector file = new Vector();// 附件文件集合 
	Enumeration efile = file.elements();// 向Multipart添加附件
	while (efile.hasMoreElements()) {
	  MimeBodyPart mbpFile = new MimeBodyPart();
	  String filename = efile.nextElement().toString();
	 
	  FileDataSource fds = new FileDataSource(filename);
	  mbpFile.setDataHandler(new DataHandler(fds));
	  mbpFile.setFileName(filename);*/
	/*  public boolean sendHtmlAndAffixMail(MailSenderInfo mailInfo) {
	    // 判断是否需要身份认证
		  MyAuthenticator authenticator = null;
	    Properties pro = mailInfo.getProperties();
	    //如果需要身份认证，则创建一个密码验证器  
		  if (mailInfo.isValidate()) { 
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		  } 
	    // 根据邮件会话属性和密码验证器构造一个发送邮件的session
	    Session session = Session.getDefaultInstance(pro, authenticator);
	    try {
	      MimeMessage msg = new MimeMessage(session); // 构造MimeMessage 并设定基本的值
	      // MimeMessage msg = new MimeMessage();
	      msg.setFrom(new InternetAddress(mailInfo.getFromAddress()));
	      // msg.addRecipients(Message.RecipientType.TO, address);
	      // //这个只能是给一个人发送email
	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getToAddress()));
	      msg.setSubject(MimeUtility.encodeText(mailInfo.getSubject()));
	      Multipart mp = new MimeMultipart();// 构造Multipart
	      MimeBodyPart mbpContent = new MimeBodyPart();// 向Multipart添加正文
	      mbpContent.setContent(mailInfo.getContent(),
	          "text/html;charset=GB2312");
	      mp.addBodyPart(mbpContent);// 向MimeMessage添加（Multipart代表正文）
	      Vector file = mailInfo.getAttachFileNames();
	      Enumeration efile = file.elements();// 向Multipart添加附件
	      while (efile.hasMoreElements()) {
	        MimeBodyPart mbpFile = new MimeBodyPart();
	        String filename = efile.nextElement().toString();
	        System.out.println(filename.toLowerCase());
	        FileDataSource fds = new FileDataSource(filename);
	        mbpFile.setDataHandler(new DataHandler(fds));
	        System.out.println(fds.getName());
	        mbpFile.setFileName(MimeUtility.encodeText(fds.getName()));
	        // 向MimeMessage添加（Multipart代表附件）
	        mp.addBodyPart(mbpFile);
	      }
	     // file.removeElement(file.elements());
	      //file.removeAllElements();
	      // 向Multipart添加MimeMessage
	      msg.setContent(mp);
	      msg.setSentDate(new Date());
	      msg.saveChanges();
	      // 发送邮件
	      Transport transport = session.getTransport("smtp");
	      transport.connect(mailInfo.getMailServerHost(), mailInfo
	          .getUserName(), mailInfo.getPassword());
	      transport.sendMessage(msg, msg.getAllRecipients());
	      transport.close();
	    } catch (Exception mex) {
	      mex.printStackTrace();
	      return false;
	      
	    }
	    return true;

	  }*/

} 
