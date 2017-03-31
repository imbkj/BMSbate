package Controller.CoHousingFund;

import impl.MessageImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;

import Model.CoHousingFundChangeModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;
import bll.SysMessage.SysMessage_AddBll;

public class CoHousingFund_DocBackController {
	Integer daid = 0,id=0;

	private CoHousingFundChangeModel m = new CoHousingFundChangeModel();

	public CoHousingFund_DocBackController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());

			m = new CoHousingFund_ListBll().getCoHoChangeList(
					" and chfc_id=" + daid).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
		System.out.println("id="+id);
		System.out.println("daid="+daid);
	}
	
	public boolean sendMessage() {

		SysMessage_AddBll bll = new SysMessage_AddBll();
		SysMessageModel model = new SysMessageModel();
		List<SysMessageModel> list = new ListModelList<>();
		List<LoginModel> list2 = new ListModelList<>();
		// 传参数
		model.setSyme_content(m.getCoba_company() + "-公积金单位账户信息变更已完成");
		model.setSyme_title("公积金单位信息变更通知");
		model.setSyme_state(1);
		model.setEmail(1);
		model.setEmailtitle("公积金单位信息变更通知");
		MessageService msgservice = new MessageImpl();
		list2 = bll.getLoginList(" and log_name='" + UserInfo.getUsername()
				+ "'");
		model.setSymr_log_id(list2.get(0).getLog_id());
		model.setSymr_name(list2.get(0).getLog_name());
		model.setSymr_state(1);
		list.add(model);
		String[] str = msgservice.Add(model);
		if (str[0].equals("1")) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param gd
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			String[] str = new CoHousingFund_OperateBll().DocBack(m, gd);
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public final CoHousingFundChangeModel getM() {
		return m;
	}

	public final void setM(CoHousingFundChangeModel m) {
		this.m = m;
	}
}
