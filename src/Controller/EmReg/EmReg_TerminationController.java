package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

import Model.EmRegistrationInfoModel;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmReg_TerminationController {

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private Date stop_date = new Date();
	private String[] reason = { "", "合同终止", "单位原因解除", "个人原因解除", "修改个人信息" };
	private Integer daid,gid;
	private EmReg_ListBll bll = new EmReg_ListBll();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public EmReg_TerminationController() {
		try {
			
			if(Executions.getCurrent().getArg()
					.get("daid")!=null)
			{
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			}
			else{
				if(Executions.getCurrent().getArg()
						.get("gid")!=null)
				{
					EmRegistrationInfoModel ml=bll.getInfo(Integer.parseInt(Executions.getCurrent().getArg()
					.get("gid").toString()));
					daid=ml.getErin_id();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	public void addwin(@BindingParam("win") Window win)
	{
		if(daid!=null)
		{
			setErm(bll.getEmRegInfo(daid, ""));
		}
		else
		{
			Messagebox
			.show("该员工没有就业登记数据!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			refreshUrl();
		}
	}
	
	private void refreshUrl()
	{
		RedirectUtil util=new RedirectUtil();
		util.refreshEmUrl("/EmReg/EmReg_List.zul");
	}

	// 提交事件
	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {
		if (stop_date != null && !"".equals(erm.getErin_stop_reason())
				&& erm.getErin_stop_reason() != null) {

			EmReg_OperateBll bll = new EmReg_OperateBll();
			DateStringChange dsc = new DateStringChange();
			erm.setErin_stop_date(dsc.DatetoSting(stop_date, "yyyy-MM-dd"));
			erm.setErin_stop_people(UserInfo.getUsername());

			// 调用方法
			String[] message = bll.erinStopApp(erm);
			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// //跳转页面
							refreshUrl();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			}
		} else {
			if ("".equals(erm.getErin_stop_reason())
					|| erm.getErin_stop_reason() == null) {
				Messagebox.show("请选择“终止原因!”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (stop_date == null) {
				Messagebox.show("请选择“终止日期!”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public Date getStop_date() {
		return stop_date;
	}

	public void setStop_date(Date stop_date) {
		this.stop_date = stop_date;
	}

	public String[] getReason() {
		return reason;
	}

	public void setReason(String[] reason) {
		this.reason = reason;
	}
}
