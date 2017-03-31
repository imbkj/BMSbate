package Controller.EmSheBao;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Model.EmShebaoBJModel;
import Util.UserInfo;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;

public class Emsc_DeclareBj_FlagController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private Emsc_DeclareSelBll selbll;
	private EmShebaoBJModel m;

	public Emsc_DeclareBj_FlagController() {
		selbll = new Emsc_DeclareSelBll();
		m = selbll.getBjFlag(id);
	}

	// 添加备注
	@Command("upflag")
	@NotifyChange("m")
	public void upflag() {
		Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
		int i = bll.UpFlag(m, UserInfo.getUsername());
		if (i == 1) {
			m = selbll.getBjFlag(id);
			Messagebox.show("备注修改成功。", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else {
			Messagebox.show("备注修改失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmShebaoBJModel getM() {
		return m;
	}

	public void setM(EmShebaoBJModel m) {
		this.m = m;
	}

}
