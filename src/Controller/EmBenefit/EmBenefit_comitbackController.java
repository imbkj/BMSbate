package Controller.EmBenefit;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;

import Model.EmWelfareModel;

public class EmBenefit_comitbackController {
	private List<EmWelfareModel> list = (List<EmWelfareModel>) Executions
			.getCurrent().getArg().get("list");
	private String backcase="";
	public EmBenefit_comitbackController()
	{
		if(list.size()>0)
		{
			backcase=list.get(0).getEmwf_backcase();
		}
	}
	
	@Command
	public void back(@BindingParam("win") Window win)
	{
		String strid="";
		if(backcase==null||backcase.equals(""))
		{
			Messagebox.show("请输入退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			
			for(EmWelfareModel m:list)
			{
				strid=strid+m.getEmwf_id()+",";
			}
			if(strid!=null&&!strid.equals(""))
			{
				strid=strid.substring(0,strid.length()-1);
			}
			EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
			if(strid!=null&&!strid.equals(""))
			{
				Integer k=bl.backEmWelfareInfo(backcase,strid);
				if(k>0)
				{
					Messagebox.show("退回成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show("退回失败", "提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
		
	}

	public String getBackcase() {
		return backcase;
	}

	public void setBackcase(String backcase) {
		this.backcase = backcase;
	}
	
}
