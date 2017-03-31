package Controller.EmCensus.EmDh;

import java.util.Date;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Messagebox;

import bll.EmCensus.EmDh.EmDh_OperateBll;

import Model.EmDhModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class EmDh_InfoAddController {
	private EmbaseModel frommodel=new EmbaseModel();
	private EmDhModel model=new EmDhModel();
	
	//未入职员工调户新增
	@Command
	public void summit()
	{
		if(model.getEmdh_name()==null||model.getEmdh_name().equals(""))
		{
			Messagebox.show("请输入员工姓名","提示",Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(model.getEmdh_idcard()==null||model.getEmdh_idcard().equals(""))
		{
			Messagebox.show("请输入员工身份证号码","提示",Messagebox.OK, Messagebox.INFORMATION);
		}
		else if(model.getEmdh_dhtype()==null||model.getEmdh_dhtype().equals(""))
		{
			Messagebox.show("请输入调户方式","提示",Messagebox.OK, Messagebox.INFORMATION);
		}
		else 
		{
			if(model.getEmdh_ifdn()==null||model.getEmdh_ifdn().equals(""))
			{
				model.setEmdh_ifdn("否");
			}
			if(model.getEmdh_ifhk()==null||model.getEmdh_ifhk().equals(""))
			{
				model.setEmdh_ifhk("否");
			}
			Date now=new Date();
			model.setEmdh_state(1);
			model.setEmdh_addtime(now);
			model.setEmdh_addname(UserInfo.getUsername());
			model.setEmdh_time1(now);
			model.setEmdh_isnote(0);
			model.setEmdh_state1(2);
			EmDh_OperateBll bll=new EmDh_OperateBll();
			String[] str=bll.EmDhInfoAdd(model);
			if(str[0]=="1")
			{
				Messagebox.show(str[1],"提示",Messagebox.OK, Messagebox.INFORMATION);
			}
			else
			{
				Messagebox.show(str[1],"提示",Messagebox.OK, Messagebox.INFORMATION);
			}
		}
	}

	public EmbaseModel getFrommodel() {
		return frommodel;
	}

	public void setFrommodel(EmbaseModel frommodel) {
		this.frommodel = frommodel;
	}

	public EmDhModel getModel() {
		return model;
	}

	public void setModel(EmDhModel model) {
		this.model = model;
	}
	
	

}
