package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_OperateBll;

import Model.CoOnlineRegisterInfoModel;
import Util.UserInfo;

public class CoReg_ResponseBookController {
	private Integer daid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer taprid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private CoOnlineRegisterInfoModel m = new CoReg_ListBll()
			.getCoregInfo(daid);
	private Date rebkdate;
	private String rebkenddate;

	@Command
	public void summit(@BindingParam("win") Window win) {
		String strinfo = "", restr = "";
		if (rebkdate == null) {
			strinfo = "请选择责任书签订时间";
		} else if (rebkenddate == null || rebkenddate.equals("")) {
			strinfo = "请选择责任书有效期";
		} else {
			restr = ",rebk_signdate='" + getStringDate(rebkdate)
					+ "',rebk_signname='" + UserInfo.getUsername()
					+ "',rebk_limit='" + rebkenddate + "',rebk_state=5";
		}
		if (strinfo.equals("")) {
			Integer k=new CoReg_OperateBll().updateresponebook(restr,daid);
			if(k>0)
			{
				String[] str=new CoReg_OperateBll().CoRegreEnd(m,taprid);
				if(str[0]=="1")
				{
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show(str[1], "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
			else
			{
				Messagebox.show("提交失败", "ERROR", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(strinfo, "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	public CoOnlineRegisterInfoModel getM() {
		return m;
	}

	public void setM(CoOnlineRegisterInfoModel m) {
		this.m = m;
	}

	public Date getRebkdate() {
		return rebkdate;
	}

	public void setRebkdate(Date rebkdate) {
		this.rebkdate = rebkdate;
	}

	public String getRebkenddate() {
		return rebkenddate;
	}

	public void setRebkenddate(String rebkenddate) {
		this.rebkenddate = rebkenddate;
	}

}
