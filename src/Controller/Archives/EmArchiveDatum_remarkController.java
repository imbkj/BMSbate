package Controller.Archives;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.Archives.EmArchiveDatumRemarkBll;

import Model.EmArchiveRemarkModel;
import Util.UserInfo;

public class EmArchiveDatum_remarkController {
	private EmArchiveRemarkModel earm = new EmArchiveRemarkModel();
	
	private EmArchiveDatumRemarkBll bll = new EmArchiveDatumRemarkBll();
	
	private Integer daid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	
	private Window win = (Window) Path.getComponent("/remark");
	
	public EmArchiveDatum_remarkController() {
		earm.setEare_tid(2);
		earm.setEare_trid(daid);
		earm.setEare_addname(UserInfo.getUsername());
	}
	
	@Command
	public void submit(@BindingParam("win") Window win){
		if (earm.getEare_content()==null || earm.getEare_content().equals("")) {
			Messagebox.show("请输入备注信息!", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		earm.setEare_remark(earm.getEare_content());
		Integer i = bll.add(earm);
		if (i>0) {
			Messagebox.show("添加成功!", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		}else {
			Messagebox.show("添加失败!", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}
		
	}

	public EmArchiveRemarkModel getEarm() {
		return earm;
	}

	public void setEarm(EmArchiveRemarkModel earm) {
		this.earm = earm;
	}

}
