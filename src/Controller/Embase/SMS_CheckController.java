package Controller.Embase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.Embase.SMS_CheckBll;

import Model.SMSModel;

public class SMS_CheckController {
	private List<SMSModel> smslist = new ListModelList<SMSModel>();
	SMS_CheckBll bll = new SMS_CheckBll();
	String mobile="";

	public SMS_CheckController() throws SQLException {
		mobile = Executions.getCurrent().getArg().get("mobile").toString();
		
		smslist = new ListModelList<SMSModel>(bll.getsmslist(mobile));// 获取短信内容
	}

	public List<SMSModel> getSmslist() {
		return smslist;
	}

	public void setSmslist(List<SMSModel> smslist) {
		this.smslist = smslist;
	}

}
