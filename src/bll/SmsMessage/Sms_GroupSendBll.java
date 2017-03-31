package bll.SmsMessage;

import java.sql.SQLException;

import Model.SMSModel;
import dal.SmsMessage.Sms_GroupSendDal;

public class Sms_GroupSendBll {
	private Sms_GroupSendDal dal=new Sms_GroupSendDal();
	
	public Integer SmsSend(SMSModel model) throws SQLException {
		return dal.SmsSend(model);
	}
	
	public Integer EmailSend(SMSModel model) throws SQLException {
		return dal.EmailSend(model);
	}

}
