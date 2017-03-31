package Controller.Archives;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Archives.EmArchive_OperateBll;


import Model.EmArchiveModel;
import Model.EmArchiveRemarkModel;
import Util.UserInfo;

public class Archives_RemarkAddController extends SelectorComposer<Component>{
	@Wire
	private Textbox remark;
	@Wire
	private Window remarkwin;
	EmArchiveModel frommodel = (EmArchiveModel)Executions.getCurrent().getArg().get("model");
	//提交
	@Listen("onClick =#addremark")
	public void addremark(){
		if(!remark.getValue().equals("")&&remark.getValue()!="")
		{
			EmArchive_OperateBll bll=new EmArchive_OperateBll();
			EmArchiveRemarkModel model=new EmArchiveRemarkModel();
			String username=UserInfo.getUsername();
			int tid=frommodel.getEmar_id();
			model.setEare_addname(username);
			model.setEare_trid(tid);
			model.setEare_content(remark.getValue());
			model.setGid(frommodel.getGid());
			int k=bll.addRemark(model);
			if(k>0)
			{
				Messagebox.show("添加成功","提示",Messagebox.OK, Messagebox.INFORMATION);
				remarkwin.detach();
			}
			else
			{
				Messagebox.show("添加失败","提示",Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

}
