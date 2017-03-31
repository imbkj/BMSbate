package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;

public class EmActy_GiftClientSignController {
	private EmWelfareModel model = (EmWelfareModel) Executions.getCurrent().getArg()
			.get("model");
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("tarpid").toString());
	private String name=UserInfo.getUsername();
	
	//客服签收礼品
	@Command
	public void sign(@BindingParam("win") Window win,@BindingParam("signtime") Date signtime,
			@BindingParam("signclient") String signclient)
	{
		if(signtime!=null)
		{
			EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
			model.setEmwf_sendtime(DatetoSting(signtime));
			model.setEmwf_takeclient(signclient);
			String sql=",emwf_signclient='"+signclient+"',emwf_clientsigntime='"+DatetoSting(signtime)+"',emwf_state=8";
			Integer k=bl.updateEmWelfareInfo(sql, model.getEmwf_id());
			if(k>0)
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请选择签收时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//流程转到下一步
	private String[] tonext()
	{
		String[] str=new String[5];
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		String strsql=",gift_state=6";
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		str=obll.updateEmActy_GiftInfos(m, strsql,"1");
		return str;
	}

	public EmWelfareModel getModel() {
		return model;
	}

	public void setModel(EmWelfareModel model) {
		this.model = model;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
