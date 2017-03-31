package Util;   
/**   
* 发送邮件需要使用的基本信息 
*author by wangfun
http://www.5a520.cn 小说520  
*/    
import java.util.Properties;    

import javax.mail.Address;
public class MailSenderInfo {    
    // 发送邮件的服务器的IP和端口    
    private String mailServerHost="smtp.szciic.com";    
    private String mailServerPort = "25";    
    // 邮件发送者的地址    
    private String fromAddress;    
    // 邮件接收者的地址    
    private String toAddress[];  
    
    // 抄送邮件接收者的地址    
    private String csAddress[];
    
    // 密送邮件接收者的地址    
    private String msAddress[];
    
    // 登陆邮件发送服务器的用户名和密码    
    private String userName;    
    private String password;    
    // 是否需要身份验证    
    private boolean validate = false;    
    // 邮件主题    
    private String subject;    
    // 邮件的文本内容    
    private String content;    
    // 邮件附件的文件名    
    private String[][] attachFileNames;
    private Address[] replyAddress;//制定收件人邮箱地址
    /**   
      * 获得邮件会话属性   
      */    
    public Properties getProperties(){    
      Properties p = new Properties();    
      p.put("mail.smtp.host", this.mailServerHost);    
      p.put("mail.smtp.port", this.mailServerPort);    
      p.put("mail.smtp.auth", validate ? "true" : "false");    
      return p;    
    }    
    
    


	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String[] getCsAddress() {
		return csAddress;
	}
	public void setCsAddress(String[] csAddress) {
		this.csAddress = csAddress;
	}

	public String[] getMsAddress() {
		return msAddress;
	}


	public void setMsAddress(String[] msAddress) {
		this.msAddress = msAddress;
	}




	public String getMailServerHost() {    
      return mailServerHost;    
    }    
    public void setMailServerHost(String mailServerHost) {    
      this.mailServerHost = mailServerHost;    
    }   
    public String getMailServerPort() {    
      return mailServerPort;    
    }   
    public void setMailServerPort(String mailServerPort) {    
      this.mailServerPort = mailServerPort;    
    }   
    public boolean isValidate() {    
      return validate;    
    }   
    public void setValidate(boolean validate) {    
      this.validate = validate;    
    }   
    
    public String[][] getAttachFileNames() {
		return attachFileNames;
	}
	public void setAttachFileNames(String[][] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}
	public String getFromAddress() {    
      return fromAddress;    
    }    
    public void setFromAddress(String fromAddress) {    
      this.fromAddress = fromAddress;    
    }   
    public String getPassword() {    
      return password;    
    }   
    public void setPassword(String password) {    
      this.password = password;    
    }   
  
    public String getUserName() {    
      return userName;    
    }   
    public void setUserName(String userName) {    
      this.userName = userName;    
    }   
    public String getSubject() {    
      return subject;    
    }   
    public void setSubject(String subject) {    
      this.subject = subject;    
    }   
    public String getContent() {    
      return content;    
    }   
    public void setContent(String textContent) {    
      this.content = textContent;    
    }
	public Address[] getReplyAddress() {
		return replyAddress;
	}
	public void setReplyAddress(Address[] replyAddress) {
		this.replyAddress = replyAddress;
	}
}   