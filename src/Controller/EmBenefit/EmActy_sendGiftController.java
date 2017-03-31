package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;

import Model.EmWelfareModel;

public class EmActy_sendGiftController {
	private EmWelfareModel model = (EmWelfareModel) Executions.getCurrent().getArg()
			.get("model");
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("tarpid").toString());
	
	@Command
	public void send(@BindingParam("win") Window win,@BindingParam("sendtime") Date sendtime,
			@BindingParam("takeclient") String takeclient)
	{
		
		EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
		model.setEmwf_sendtime(DatetoSting(sendtime));
		model.setEmwf_takeclient(takeclient);
		Integer k=bl.updateEmWelfare2(id, model);
		if(k>0)
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else if(k==-2)
		{
			Messagebox.show("库存不足，发放失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmWelfareModel getModel() {
		return model;
	}

	public void setModel(EmWelfareModel model) {
		this.model = model;
	}
	
	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str=null;
		try{
			if(d!=null)
			{
				str= sdf.format(d);
			}
		}catch(Exception e)
		{
			
		}
		return str;
	}
	
}
