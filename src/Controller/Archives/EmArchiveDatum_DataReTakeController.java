package Controller.Archives;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmArchiveDatumModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;

public class EmArchiveDatum_DataReTakeController extends SelectorComposer<Component> {
	@Wire
	private Datebox retaketime;
	@Wire
	private Window retakewin;
	
	EmArchiveDatumModel frommodel = (EmArchiveDatumModel)Executions.getCurrent().getArg().get("model");
	
	//收回材料事件提交
	@Listen("onClick =#summit")
	public void summitData(){
		if(retaketime.getValue()!=null)
		{
			EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model=new EmArchiveDatumModel();
			model.setEada_id(frommodel.getEada_id());
			model.setEada_returnarchivedate(retaketime.getValue());
			model.setEada_addname(UserInfo.getUsername());
			String str=bll.EmArchiveDataRetake(model);
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
			retakewin.detach();
		}
		else
		{
			Messagebox.show("请填写收回材料时间","提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
}
