package Util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import javax.activation.*;

import Model.LoginModel;
import bll.SysMessage.SysMessage_AddBll;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
public class SimpleMailSender {
	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 * @throws UnsupportedEncodingException
	 */

	String toList = null;
	String toListcs = null;
	String toListms = null;

	public boolean sendTextMail(MailSenderInfo mailInfo)
			throws UnsupportedEncodingException {
		boolean flag = false;
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			mailMessage.reply(false);
			mailMessage.setReplyTo(mailInfo.getReplyAddress());

			
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中

			// 接收人
			if (mailInfo.getToAddress() != null
					&& !mailInfo.getToAddress().equals("")) {
				String[] s = mailInfo.getToAddress();
				{
					toList = getMailList(mailInfo.getToAddress());
					InternetAddress[] iaToList = new InternetAddress()
							.parse(toList);
					mailMessage.setRecipients(Message.RecipientType.TO,
							iaToList); // 收件人
				}
			}

			// 抄送
			if (mailInfo.getCsAddress() != null) {
				toListcs = getMailList(mailInfo.getCsAddress());
				InternetAddress[] iaToListcs = new InternetAddress()
						.parse(toListcs);
				mailMessage.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
			}

			// 密送
			if (mailInfo.getMsAddress() != null) {
				toListms = getMailList(mailInfo.getMsAddress());
				InternetAddress[] iaToListms = new InternetAddress()
						.parse(toListms);
				mailMessage
						.setRecipients(Message.RecipientType.BCC, iaToListms); // 密送人
			}

			// Address to = new InternetAddress(mailInfo.getToAddress());
			// mailMessage.setRecipient(Message.RecipientType.TO,to);

			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件正文
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(mailInfo.getContent());
			multipart.addBodyPart(contentPart);
			mailMessage.setContent(multipart);

			// BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
			// Multipart mm = new MimeMultipart();//
			// 新建一个MimeMultipart对象用来存放BodyPart对象
			// mm.addBodyPart(multipart);//
			// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
			// 把mm作为消息对象的内容
			MimeBodyPart filePart;
			FileDataSource filedatasource;
			// 逐个加入附件
			if (mailInfo.getAttachFileNames() != null) {
				for (int j = 0; j < mailInfo.getAttachFileNames().length; j++) {
					filePart = new MimeBodyPart();
					if (mailInfo.getAttachFileNames()[j][1] != null) {
						filedatasource = new FileDataSource(
								mailInfo.getAttachFileNames()[j][1]);//路径
						filePart.setDataHandler(new DataHandler(filedatasource));
						try {
							String filename = "";
							if (mailInfo.getAttachFileNames()[j][0] != null
									&& !mailInfo.getAttachFileNames()[j][0]
											.equals("")) {
								filename = mailInfo.getAttachFileNames()[j][0];
							} else {
								filename = filedatasource.getName();
							}
							filePart.setFileName(MimeUtility
									.encodeText(filename));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					multipart.addBodyPart(filePart);
				}
			}
			mailMessage.setContent(multipart);
			/*
			 * ==================================================================
			 */
			// 添加附件
			// if (mailInfo.getAttachFileNames().length > 0) {
			/*
			 * for(int i=0;i<2;i++) { BodyPart messageBodyPart = new
			 * MimeBodyPart(); DataSource source = new FileDataSource(
			 * mailInfo.getAttachFileNames()[1]);
			 * messageBodyPart.setDataHandler(new DataHandler(source));
			 * messageBodyPart.setFileName(MimeUtility.encodeWord(mailInfo
			 * .getAttachFileNames()[0]));
			 * multipart.addBodyPart(messageBodyPart);
			 * mailMessage.setContent(multipart); }
			 */
			/* ======================================================== */
			// 设置邮件消息的主要内容
			// String mailContent = mailInfo.getContent();
			// Multipart arg0;

			// 发送邮件
			Transport.send(mailMessage);
			flag = true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 * @throws UnsupportedEncodingException
	 */
	public boolean sendHtmlMail(MailSenderInfo mailInfo)
			throws UnsupportedEncodingException {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			mailMessage.reply(false);
			mailMessage.setReplyTo(mailInfo.getReplyAddress());
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);

			// 接收人
			if (mailInfo.getToAddress() != null) {
				toList = getMailList(mailInfo.getToAddress());
				InternetAddress[] iaToList = new InternetAddress()
						.parse(toList);
				mailMessage.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
			}

			// 抄送
			if (mailInfo.getCsAddress() != null) {
				toListcs = getMailList(mailInfo.getCsAddress());
				InternetAddress[] iaToListcs = new InternetAddress()
						.parse(toListcs);
				mailMessage.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
			}

			// 密送
			if (mailInfo.getMsAddress() != null) {
				toListms = getMailList(mailInfo.getMsAddress());
				InternetAddress[] iaToListms = new InternetAddress()
						.parse(toListms);
				mailMessage
						.setRecipients(Message.RecipientType.BCC, iaToListms); // 密送人
			}

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

			if (mailInfo.getAttachFileNames().length > 0) {
				// 添加附件
				BodyPart messageBodyPart = new MimeBodyPart();

				DataSource source = new FileDataSource(
						mailInfo.getAttachFileNames()[0][1]);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(MimeUtility.encodeWord(mailInfo
						.getAttachFileNames()[0][2]));

				// mailMessage.setDataHandler(new DataHandler(source));
				// mailMessage.setFileName(MimeUtility.encodeWord(mailInfo.getAttachFileNames()[0]));
				mainPart.addBodyPart(messageBodyPart);
				mailMessage.setContent(mainPart);
			}

			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	private String getMailList(String[] mailArray) {

		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}

			}
		}
		return toList.toString();

	}
}