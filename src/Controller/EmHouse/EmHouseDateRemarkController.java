package Controller.EmHouse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouseDataListBll;

import Model.EmbaseGDModel;

public class EmHouseDateRemarkController {

	private EmbaseGDModel egm = (EmbaseGDModel) Executions.getCurrent()
			.getArg().get("egm");

	private Window win;

	public EmHouseDateRemarkController() {

	}
	
	@Command
	public void winR(@BindingParam("a") Window w){
		win=w;
	}
	
	@Command
	public void submit(){
		EmHouseDataListBll bll = new EmHouseDataListBll();
		bll.modinfo(egm);
		if (egm.getRemark()!=null && !egm.getRemark().equals("")) {
			egm.setRemarkFlag(true);
			egm.setRemarkFlag2(false);
		}else {
			egm.setRemarkFlag(false);
			egm.setRemarkFlag2(true);
		}
		Messagebox.show("修改成功.", "操作提示", Messagebox.OK,
				Messagebox.INFORMATION);
		win.detach();
	}

	public EmbaseGDModel getEgm() {
		return egm;
	}

	public void setEgm(EmbaseGDModel egm) {
		this.egm = egm;
	}

}
