package Controller.Archives;

import impl.MessageImpl;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;

import bll.Archives.EmArchiveDatum_OperateBll;

import Model.EmArchiveDatumModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class Archive_backController {
	private String id = (String) Executions.getCurrent().getArg().get("id");
	private String tarpid =null;
	private EmArchiveDatumModel model = (EmArchiveDatumModel) Executions.getCurrent().getArg().get("model");
	private String backcase="";
	private Map map=Executions.getCurrent().getArg();
	
	public Archive_backController(){
		if(Executions.getCurrent().getArg().get("tarpid")!=null)
		{
			tarpid=Executions.getCurrent().getArg().get("tarpid").toString();
		}
	}
	
	//退回到客服确认（流程第二步）
	@Command
	public void back(@BindingParam("win") Window win)
	{
		if(backcase!=null&&!backcase.equals(""))
		{
			model.setEada_backcase(backcase);
			EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
			String str[]=bll.EmArchiveReturn(model,"","");
			if(str[0]=="1")
			{
				sendmsg(model.getEada_addname());
				map.put("flag", "1");
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("退回原因不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//退回到上一步
	@Command
	public void backpre(@BindingParam("win") Window win)
	{
		if(backcase!=null&&!backcase.equals(""))
		{
			model.setEada_backcase(backcase);
			EmArchiveDatum_OperateBll bll=new EmArchiveDatum_OperateBll();
			String str[]=bll.EmArchiveback(model,"","");
			if(str[0]=="1")
			{
				sendmsg(model.getEada_modname());
				map.put("flag", "1");
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("退回原因不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private void sendmsg(String swmr_name)
	{
		 MessageService msgservice=new MessageImpl("EmArchiveDatum",Integer.parseInt(id));
		 SysMessageModel m=new SysMessageModel();
		 m.setSyme_title(model.getEada_name()+"("+model.getGid()+")——"+model.getEada_type()+" 业务被退回");
		 m.setSyme_content(backcase);//短信内容
	     m.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));//发件人id
	     m.setSymr_name(swmr_name);//收件人姓名
	     msgservice.Add(m);
	}

	public String getBackcase() {
		return backcase;
	}

	public void setBackcase(String backcase) {
		this.backcase = backcase;
	}
	
	
}
