package impl;

import Util.MailSenderInfo;
import Util.SimpleMailSender;
import service.SendEmailService;

public class SendEmailServiceImpl implements SendEmailService {
	private MailSenderInfo mailInfo;

	public SendEmailServiceImpl(MailSenderInfo mailInfo) {
		this.mailInfo = mailInfo;
	}

	@Override
	public int sendEamil() {
		Integer k = 0;
		try {
			mailInfo.setMailServerHost("smtp.szciic.com"); // 发送服务器
			mailInfo.setMailServerPort("25"); // 发送端口
			mailInfo.setValidate(true);
			// 这个类主要来发送邮件
			SimpleMailSender sms = new SimpleMailSender();
			boolean flag = sms.sendTextMail(mailInfo);// 发送文体格式
			if (flag) {
				k = 1;
			}
		} catch (Exception e) {
			k = 0;
		}
		return k;
	}

}
