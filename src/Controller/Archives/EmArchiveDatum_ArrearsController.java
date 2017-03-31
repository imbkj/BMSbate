package Controller.Archives;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Archives.EmArchiveDatum_OperateBll;

import Model.EmArchiveDatumModel;
import Util.UserInfo;

public class EmArchiveDatum_ArrearsController extends SelectorComposer<Component>{
	@Wire
	private Textbox fee;
	@Wire
	private Window feewin;
	EmArchiveDatumModel frommodel = (EmArchiveDatumModel)Executions.getCurrent().getArg().get("model");
	
	//欠费情况提交事件
	@Listen("onClick =#summit")
	public void summitFee(){
		if(fee.getValue()!=""&&!fee.getValue().equals(""))
		{
			EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model=new EmArchiveDatumModel();
			model.setEada_arrearageinfo(fee.getValue());
			model.setEada_addname(UserInfo.getUsername());
			model.setEada_id(frommodel.getEada_id());
			String str=bll.EmArchiveFee(model);
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
			feewin.detach();
		}
	else
	{
		Messagebox.show("请输入欠费情况","提示",Messagebox.OK, Messagebox.ERROR);
	}
}
}
