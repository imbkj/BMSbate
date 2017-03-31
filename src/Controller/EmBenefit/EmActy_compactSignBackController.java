package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_compactSignBackBll;

import Model.EmActyCompactModel;
import Util.UserInfo;

public class EmActy_compactSignBackController {
	private EmActyCompactModel eacm = new EmActyCompactModel();
	private List<EmActyCompactModel> list = new ListModelList<>();
	private EmActy_compactSignBackBll bll = new EmActy_compactSignBackBll();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private String username = UserInfo.getUsername();
	private Window win = (Window) Path.getComponent("/wincompactsign");

	public  EmActy_compactSignBackController() {
		
		setList(eadaId);
		eacm.setEaco_id(list.get(0).getEaco_id());
		eacm.setEaco_tapr_id(list.get(0).getEaco_tapr_id());
		eacm.setEaco_modname(username);
		eacm.setEaco_name(list.get(0).getEaco_name());
		eacm.setEaco_compactid(list.get(0).getEaco_compactid());
	}

	@Command("submit")
	public void submit() {
		Datebox db = (Datebox) win.getFellow("db");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (db.getValue() != null && !db.getValue().equals("")) {
			eacm.setEaco_signdate(sdf.format(db.getValue()));
			eacm.setEaco_state(6);
			eacm.setEaco_together(1);
			Integer i = bll.sign(eacm);
			if (i>0) {
				Messagebox.show("操作成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}else {
				Messagebox.show("操作失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请输入签回时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public EmActyCompactModel getEacm() {
		return eacm;
	}

	public void setEacm(EmActyCompactModel eacm) {
		this.eacm = eacm;
	}

	public List<EmActyCompactModel> getList() {
		return list;
	}

	public void setList(Integer id) {
		list = bll.getListInfo(id);
	}

}
