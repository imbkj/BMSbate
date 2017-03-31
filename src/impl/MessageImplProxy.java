package impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import service.MessageService;
import service.SendEmailService;
import Model.LoginModel;
import Model.SysEmailFileModel;
import Model.SysEmailModel;
import Model.SysMessageModel;
import Util.MailSenderInfo;
import Util.ReadProperties;
import Util.UserInfo;
import bll.CoPolicyNotice.PoNo_SelectBll;
import bll.SysMessage.SysMessage_AddBll;

public class MessageImplProxy implements MessageService {
	private MessageService service;
	ReadProperties rProp;

	public MessageImplProxy(MessageService service) {
		this.service = service;
		rProp = new ReadProperties("/Conn/mail.properties");
	}

	@Override
	public String[] Add(SysMessageModel model) {
		// 判断是否发需要发邮件，如果需要发邮件则先发邮件并发附件
		if (model.getEmail() != null && model.getEmail().equals(1)) {
			String[][] AttachFile = model.getFileurl();// 附件
			String[] toaddress = new String[1]; // 目标邮件，收件人地址
			Address[] replyAddress = new Address[1];// 指定回复地址
			String[] csaddress = new String[1]; // 抄送邮件
			PoNo_SelectBll ponobll = new PoNo_SelectBll();
			String toEmail = ponobll.getEmail(model.getSymr_log_id(),
					model.getSymr_name());// 收件地址
			String fromEmail = ponobll.getEmail(model.getSyme_log_id(), "");// 发件地址
			String csEmail = "";// 抄送地址
			//fromEmail ="service@szciic.com";
			fromEmail =rProp.getString("email.username");
			MailSenderInfo mailInfo = new MailSenderInfo();
			csaddress[0] = new String(csEmail);
			toaddress[0] = new String(toEmail);
			// mailInfo.setUserName("cyj@szciic.com"); //用户名
			// mailInfo.setPassword("cyj1426");//您的邮箱密码
			//mailInfo.setUserName("service@szciic.com"); // 用户名
			//mailInfo.setPassword("Service!((#1993");// 您的邮箱密码
			mailInfo.setUserName(rProp.getString("email.username")); // 用户名
			mailInfo.setPassword(rProp.getString("email.password"));// 您的邮箱密码
			mailInfo.setFromAddress(fromEmail); // 发送邮箱名
			if (model.getEmail_address() != null
					&& !model.getEmail_address().equals("")) {
				toaddress[0] = new String(model.getEmail_address());
			}

			try {
				// 如果指定回复人为空，则获取当前操作人的邮箱
				if (model.getReplyEmailAddress() == null
						|| model.getReplyEmailAddress().equals("")) {
					String replyEmail = "";
					SysMessage_AddBll bll = new SysMessage_AddBll();
					List<LoginModel> lList = bll.getLoginList(" and log_id="
							+ UserInfo.getUserid());
					if (lList.size() > 0) {
						replyEmail = lList.get(0).getLog_email();
					}
					model.setReplyEmailAddress(replyEmail);
				}
				if (model.getReplyEmailAddress() != null
						&& !model.getReplyEmailAddress().equals("")) {
					replyAddress[0] = new InternetAddress(
							model.getReplyEmailAddress());
				}
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mailInfo.setToAddress(toaddress); // 目标邮箱名
			mailInfo.setReplyAddress(replyAddress);// 指定回复人地址
			mailInfo.setCsAddress(csaddress); // 抄送邮件名
			mailInfo.setSubject(model.getEmailtitle()); // 邮件标题
			mailInfo.setContent(model.getSyme_content()); // 邮件正文
			mailInfo.setAttachFileNames(AttachFile); // 邮件附件
														// String[0]为附件名，String[1]为附件地址

			// 使用多线程发送邮件
			Thread myThread = new MyThread(mailInfo, UserInfo.getUsername());
			myThread.start();

			// 把系统短信的邮件标志设为0，就不会发送系统内部邮件
			model.setEmail(0);
			String content = model.getSyme_content();
			// 获取附件路径
			for (int j = 0; j < AttachFile.length; j++) {
				if (AttachFile[j][1] != null) {
					String fileUrl = AttachFile[ j][1];
					String fileName = AttachFile[j][0];
					content = content + fileName;
				}
			}

		}
		return service.Add(model);
	}

	class MyThread extends Thread {
		SendEmailService eService;
		MailSenderInfo mailInfo;
		String addname;

		public MyThread(MailSenderInfo mailInfo, String name) {
			this.mailInfo = mailInfo;
			this.addname = name;
			eService = new SendEmailServiceImpl(mailInfo);

		}

		@Override
		public void run() {
			int k=0;
			try {
				k = eService.sendEamil();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[][] AttachFile = mailInfo.getAttachFileNames();
			String content = mailInfo.getContent();
			String title = mailInfo.getSubject();
			String[] toaddress = mailInfo.getToAddress();
			SysEmailModel m = new SysEmailModel();
			m.setPfil_content(content);
			m.setPfil_title(title);
			m.setPfil_addname(addname);
			m.setPfil_email(toaddress[0]);
			SysMessage_AddBll ebll = new SysMessage_AddBll();
			if (k > 0)// 添加发送记录到系统
			{
				m.setPfil_remark("成功到达对方邮件");
			}
			else{
				m.setPfil_remark("发送失败");
			}
			
			int id = ebll.addSysEmail(m);
			
			if (id > 0) {
				for (int j = 0; j < AttachFile.length; j++) {
					if (AttachFile[j][1] != null) {
						SysEmailFileModel fm = new SysEmailFileModel();
						fm.setFile_addname(addname);
						String fileUrl = AttachFile[j][1];
						String fileName = AttachFile[j][0];
						fm.setFile_pfil_id(id);
						fm.setFile_title(fileName);
						fm.setFile_url(fileUrl);
						ebll.addSysEmailFile(fm);
					}
				}
			}
		}
	}

	@Override
	public String[] Reply(SysMessageModel model) {
		// TODO Auto-generated method stub
		return service.Reply(model);
	}

	@Override
	public List<SysMessageModel> SelectList() {
		// TODO Auto-generated method stub
		return service.SelectList();
	}

	@Override
	public List<SysMessageModel> Select() {
		// TODO Auto-generated method stub
		return service.Select();
	}

	@Override
	public List<SysMessageModel> SelectByLogId() {
		// TODO Auto-generated method stub
		return service.SelectByLogId();
	}

	@Override
	public Integer Read() {
		// TODO Auto-generated method stub
		return service.Read();
	}

	@Override
	public Integer Read(Integer symr_id) {
		// TODO Auto-generated method stub
		return service.Read(symr_id);
	}

	@Override
	public boolean isRead() {
		// TODO Auto-generated method stub
		return service.isRead();
	}

}
