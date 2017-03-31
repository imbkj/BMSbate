package dal.SmsMessage;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.SMSModel;

public class Sms_GroupSendDal {
	public Integer SmsSend(SMSModel model) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		// 创建存储过程的对象
		csmt = db.getcall("Sms_send_cyj(?,?,?,?,?,?)");
		Integer k = 0;
		try {
			// 给存储过程的参数设置值
			csmt.setString(1, model.getMobile());
			csmt.setString(2, model.getContent());
			csmt.setString(3, model.getSendname());
			if(model.getGid()==null)
			{
				model.setGid(0);
			}
			csmt.setInt(4, model.getGid());
			csmt.setString(5, model.getIdcard());
			// 注册存储过程的返回值
			csmt.registerOutParameter(6, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			k = csmt.getInt(6);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			db.Close();
		}
		return k;
	}
	
	public Integer EmailSend(SMSModel model) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		// 创建存储过程的对象
		csmt = db.getcall("Email_send_cyj(?,?,?,?,?)");
		Integer k = 0;
		try {
			// 给存储过程的参数设置值
			csmt.setString(1, model.getContent());//邮件内容
			csmt.setString(2, model.getSendname());//发件人姓名
			csmt.setString(3, model.getEmail());//收件人邮箱
			csmt.setString(4, model.getTitle());//邮件标题
			// 注册存储过程的返回值
			csmt.registerOutParameter(5, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			k = csmt.getInt(5);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return k;
	}
}
