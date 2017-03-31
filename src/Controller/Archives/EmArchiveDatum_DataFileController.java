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

public class EmArchiveDatum_DataFileController extends SelectorComposer<Component> {
	@Wire
	private Datebox filedate;
	@Wire
	private Window filewin;
	EmArchiveDatumModel frommodel = (EmArchiveDatumModel)Executions.getCurrent().getArg().get("model");
	
	//材料归档事件提交
	@Listen("onClick =#summit")
	public void summitFile(){
		if(filedate.getValue()!=null)
		{
			EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model=new EmArchiveDatumModel();
			model.setEada_id(frommodel.getEada_id());
			model.setEada_filedate(filedate.getValue());
			model.setEada_addname(UserInfo.getUsername());
			String[] str=bll.EmArchiveDataFile(model,"","","");
			Messagebox.show(str[1],"提示",Messagebox.OK, Messagebox.INFORMATION);
			filewin.detach();
		}
		else
		{
			Messagebox.show("请填写材料归档时间","提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
}
