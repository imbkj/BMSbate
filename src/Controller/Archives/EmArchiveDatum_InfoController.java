package Controller.Archives;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmArchiveDatumModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class EmArchiveDatum_InfoController extends SelectorComposer<Component>{
	@Wire
	private Textbox remark;
	@Wire
	private Window backwin;
	EmArchiveDatumModel frommodel = (EmArchiveDatumModel)Executions.getCurrent().getArg().get("model");
	
	EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
	//退回原因提交
	@Listen("onClick =#backinfo")
	public void updateArchives(){
		if(remark.getValue()!=""&&!remark.getValue().equals(""))
		{
			EmArchive_SelectBll blls=new EmArchive_SelectBll();
			List<TaskProcessViewModel> tlist=blls.getLastId(frommodel.getEada_tapr_id()+"");
			TaskProcessViewModel tmodel=new TaskProcessViewModel();
			if(!tlist.isEmpty())
			{
				tmodel=tlist.get(0);
			}
			EmArchiveDatumModel model=new EmArchiveDatumModel();
			model.setEada_id(frommodel.getEada_id());
			model.setRemark(remark.getValue());
			model.setCid(frommodel.getCid());
			model.setGid(frommodel.getGid());
			model.setEada_tapr_id(frommodel.getEada_tapr_id());
			model.setEada_addname(UserInfo.getUsername()); 
			String sql=",eada_final=4";
			String[] str=bll.EmArchiveReturn(model,remark.getValue(),sql);
			if(str[0]=="1")
			{
				Messagebox.show("退回成功","提示",Messagebox.OK, Messagebox.INFORMATION);
				backwin.detach();
			}
			else
			{
				Messagebox.show(str[1],"提示",Messagebox.OK, Messagebox.INFORMATION);
			}
			
		}
		else
		{
			Messagebox.show("退回原因不能为空","提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
}
