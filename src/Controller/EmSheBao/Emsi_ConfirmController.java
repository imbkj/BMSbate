package Controller.EmSheBao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmSheBaoChangeHjModel;
import Model.EmShebaoChangeSZSIModel;
import Util.UserInfo;
import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_ConfirmController {
	private String id = Executions.getCurrent().getArg().get("daid").toString();
	private String tperid = Executions.getCurrent().getArg().get("id").toString();
	private Emsi_SelBll bll=new Emsi_SelBll();
	private Date ownmonth;
	
	private EmSheBaoChangeHjModel model=bll.getEmSheBaoChangeHjModelInfo(Integer.parseInt(id));
	
	public Emsi_ConfirmController()
	{
		if(model.getOwnmonth()!=null)
		{
			ownmonth=datediff(model.getOwnmonth().toString());
		}
	}
	
	//
	@Command
	public void changeSZSI(@BindingParam("win") Window win,@BindingParam("ownmonth") Date ownmonth)
	{
		if(ownmonth!=null)
		{
			model.setOwnmonth(Integer.parseInt(datetostr(ownmonth)));
			EmShebaoChangeSZSIModel ecModel = new EmShebaoChangeSZSIModel();
				ecModel.setGid(model.getGid());
				ecModel.setOwnmonth(model.getOwnmonth());
				ecModel.setEscs_change(model.getSbci_change());
				ecModel.setEscs_content(model.getSbci_content());
				ecModel.setEscs_s8("");
				ecModel.setEscs_addname(UserInfo.getUsername());
				ecModel.setEscs_remark(model.getSbci_remark());
				ecModel.setEscs_name(model.getEmba_name());
				// 调用新增方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.changeSZSI(ecModel);
				if ("1".equals(message[0])) {
					EmDh_OperateBll dbll=new EmDh_OperateBll();
					dbll.changeSZSIUpdate(model, Integer.parseInt(tperid));
					// 弹出提示
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
		}
		else
		{
			Messagebox.show("请选择所属月份", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	public EmSheBaoChangeHjModel getModel() {
		return model;
	}

	public void setModel(EmSheBaoChangeHjModel model) {
		this.model = model;
	}
	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	private static Date datediff(String ownmonth) {
		Date d=null;
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMM");
		try {
			 d = sdf.parse(ownmonth);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	private static String datetostr(Date ownmonth) {
		String d="";
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMM");
		try {
			 d = sdf.format(ownmonth);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
}
