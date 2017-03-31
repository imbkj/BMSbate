package Controller.EmBodyCheck;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBodyCheck.EmBcInfo_OperateBll;

import B.K;
import Model.EmBodyCheckModel;
import Model.EmbaseGDModel;
import Model.embodycheckoperlogModel;
import Util.UserInfo;

public class EmBc_ClientSignAllController {
	private List<EmbaseGDModel> list = (List<EmbaseGDModel>) Executions
			.getCurrent().getArg().get("list");
	
	private Date signdate=new Date();
	private String signName=UserInfo.getUsername();
	
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		if(signdate==null)
		{
			Messagebox.show("请输入签收时间", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if(signName==null||signName.equals(""))
		{
			Messagebox.show("请输入签收人", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		String sql = ",ebcl_clientshowdate='"
				+ changedate(signdate)
				+ "',ebcl_showclient='" + signName
				+ "',ebcl_state=12";
		int k=0;
		for(EmbaseGDModel m:list)
		{
			EmBodyCheckModel model = new EmBodyCheckModel();
			model.setOcon("客服签收体检报告");
			model.setEbcl_id(m.getEbcl_id());
			model.setEmbc_id(m.getDataId());
			model.setEmbc_tapr_id(m.getTaprId());
			model.setCid(m.getCid());
			model.setGid(m.getGid());
			model.setEbcl_state(m.getEbcl_state());
			embodycheckoperlogModel logm=new embodycheckoperlogModel();
			logm.setBclg_addname(UserInfo.getUsername());
			logm.setBclg_content("签收了体检报告");
			logm.setBclg_ebcl_id(model.getEbcl_id());
			
			EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
			String[] str = obll.EmBodyCheckEdit(model, sql, "3");
			if (str[0] == "1") {
				obll.insertLog(logm);
				k=k+1;
			}
		}
		if(k>0)
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	public Date getSigndate() {
		return signdate;
	}
	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	
	private String changedate(Date d) {
		String formatDate = null;
		if (d != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			formatDate = df.format(d);
		}
		return formatDate;
	}
	
	
	
}
