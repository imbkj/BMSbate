package Controller.EmReg;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

import Model.EmRegContactModel;
import Util.UserInfo;

public class EmReg_ContactController {
	private int id=0;
	private String table ="";
	private int gid=0;
	private EmReg_ListBll bll = new EmReg_ListBll();
	private List<EmRegContactModel> list =new ArrayList<EmRegContactModel>();
	private EmRegContactModel model = new EmRegContactModel();
	public EmReg_ContactController()
	{
		try{
		 id = (Integer) Executions.getCurrent().getArg().get("id");
		 table =Executions.getCurrent().getArg().get("table").toString();
		 list = bll.getEmRegContactList(id,table);
		}catch(Exception e)
		{
			gid = (Integer) Executions.getCurrent().getArg().get("gid");
			list = bll.getEmRegContactListByGid(gid);
		}
	}

	@Command
	@NotifyChange({ "list", "model" })
	public void add(@BindingParam("win") Window win) {
		if (model.getCont_content() == null
				|| model.getCont_content().equals("")) {
			Messagebox.show("请输入联系内容或者备注", "提示", Messagebox.OK,Messagebox.ERROR);
		} else {
			EmReg_OperateBll obll = new EmReg_OperateBll();
			model.setCont_addname(UserInfo.getUsername());
			model.setCont_erin_id(id);
			model.setCont_tablename(table);
			Integer k = obll.EmRegContactAdd(model);
			if (k > 0) {
				Clients.showNotification("提交成功", "info", win, "middle_center",
						3000);
				model.setCont_content("");
				list = bll.getEmRegContactList(id,table);
			} else {
				Clients.showNotification("提交失败", "info", win, "middle_center",
						3000);
			}
		}
	}

	public List<EmRegContactModel> getList() {
		return list;
	}

	public void setList(List<EmRegContactModel> list) {
		this.list = list;
	}

	public EmRegContactModel getModel() {
		return model;
	}

	public void setModel(EmRegContactModel model) {
		this.model = model;
	}

}
