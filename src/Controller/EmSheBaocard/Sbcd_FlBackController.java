package Controller.EmSheBaocard;

import impl.MessageImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;

import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoCardInfoModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class Sbcd_FlBackController {
	private EmShebaoCardInfoModel model = (EmShebaoCardInfoModel)Executions.getCurrent().getArg().get("model");
	private String tag = (String)Executions.getCurrent().getArg().get("tag");
	
	@Command
	public void back(@BindingParam("win") Window win,@BindingParam("gd") Grid gd)
	{
		if(model.getSbcd_backcase()!=null&&!model.getSbcd_backcase().equals(""))
		{
			EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
			Integer stateid=bll.getState("退回");
			String sql=",sbcd_backname='"+UserInfo.getUsername()+"'";
			sql=sql+",sbcd_backcase='"+model.getSbcd_backcase()+"'";
			sql=sql+",sbcd_backtime='"+DateToStr(new Date())+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
			EmShebaoCardInfoOperateBll obll=new EmShebaoCardInfoOperateBll();
			String str[]=obll.EmShebaoCardback(model, sql);
			if(str[0]=="1")
			{
				String content=model.getSbcd_company()+"-"+model.getSbcd_name()+"社保卡被退回";
				MessageService msgservice=new MessageImpl("EmShebaoCardInfo",model.getSbcd_id());
				SysMessageModel msgmodel=new SysMessageModel();
				msgmodel.setSyme_content(content);
				msgmodel.setSymr_name(model.getCoba_client());
				msgmodel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));
				msgservice.Add(msgmodel);
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				try {
					docOC.UpsubmitDoc(gd,model.getSbcd_id()+"");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tag="2";
				Messagebox.show("退回成功", "提示", Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("退回失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请填写退回理由", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String str ="";
	   if(date!=null)
	   {
		   str = format.format(date);
	   }
	   return str;
	}

	public EmShebaoCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmShebaoCardInfoModel model) {
		this.model = model;
	}
}
