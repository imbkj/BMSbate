package Controller.Archives;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class EmArchiveDatum_FileCheckOutController extends SelectorComposer<Component>{
	@Wire
	private Textbox fid;
	@Wire
	private Combobox wtmode;
	@Wire
	private Textbox outtype;
	@Wire
	private Textbox outseasion;
	@Wire
	private Textbox tocom;
	@Wire
	private Datebox outdate;
	@Wire
	private Combobox ifhaveout;
	@Wire
	private Window checkein;
	private List<EmArchiveModel> archivelist;
	EmArchive_SelectBll blls=new EmArchive_SelectBll();
	
	EmArchiveDatumModel frommodel = (EmArchiveDatumModel)Executions.getCurrent().getArg().get("model");
	
	//调出提交事件
	@Listen("onClick =#summit")
	public void summitData(){
		String str=isEmploy();
		if(str=="")
		{
			String[] strs=new String[5];
			EmArchiveModel models=new EmArchiveModel();
			archivelist=blls.getEmArchiveInfo(" and a.gid="+frommodel.getGid());
			if(!archivelist.isEmpty())
			{
				models=archivelist.get(0);
			}
			EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model=new EmArchiveDatumModel();
			model.setEada_id(frommodel.getEada_id());
			model.setEada_wtmode(wtmode.getValue());
			model.setEada_checkoutmode(outtype.getValue());
			model.setEada_checkoutreason(outseasion.getValue());
			model.setEada_checkoutdate(outdate.getValue());
			model.setEada_checkoutsetup(tocom.getValue());
			model.setEada_addname(UserInfo.getUsername());
			if(ifhaveout.getValue()=="是"||ifhaveout.getValue().equals("是"))
			{
				model.setEada_final("3");
			}
			else
			{
				model.setEada_final("2");
			}
			strs=bll.EmArchiveCheckOut(model,"","","",models.getEmar_id());
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
			checkein.detach();
			}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private String isEmploy()
	{
		String str="";
		if(outtype.getValue()==null||outtype.getValue().equals(""))
		{
			str="请填写调出方式";
		}
		else if(outdate.getValue()==null||outseasion.getValue().equals(""))
		{
			str="请选择调出日期";
		}
		else if(outseasion.getValue()==""||outseasion.getValue().equals(""))
		{
			str="请填写调出原因";
		}
		else if(tocom.getValue()==null||tocom.getValue().equals(""))
		{
			str="请选择往单位";
		}
		return str;
	}

}
