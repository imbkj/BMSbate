package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;

public class EmActy_sendAllGiftController {

	private List<EmWelfareModel> list = (List<EmWelfareModel>) Executions.getCurrent().getArg()
			.get("list");
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("tarpid").toString());
	private String sortid="";
	private EmActy_GiftInfoSelectBll blls=new EmActy_GiftInfoSelectBll();
	
	public EmActy_sendAllGiftController()
	{
		if(list.size()>0)
		{
			sortid=list.get(0).getEmwf_sortid();
		}
	}
	
	
	//批量发放礼品
	@Command
	public void send(@BindingParam("win") Window win,@BindingParam("sendtime") Date sendtime,
			@BindingParam("takeclient") String takeclient)
	{
		EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
		if(list.size()>0)
		{
			Integer k=0;
			for(int i=0;i<list.size();i++)
			{
				EmWelfareModel model=list.get(i);
				model.setEmwf_sendtime(DatetoSting(sendtime));
				model.setEmwf_takeclient(takeclient);
				
				Integer l=bl.updateEmWelfare2(id, model);
				if(l>0)
				{
					k=k+l;
				}
				else if(l==-2)
				{
					k=-1;
					break;
				}
			}
			if(k>0)
			{
				Integer m=blls.getEmWelfare(" and emwf_state=6 and emwf_sortid='"+sortid+"'");
				if(m==0)
				{
					tonext();
				}
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
	}
	
	//流程转到下一步
	private String[] tonext()
	{
		String[] str=new String[5];
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		String strsql=",gift_state=5";
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		str=obll.updateEmActy_GiftInfos(m, strsql,"1");
		return str;
	}
	
	//查询是否已全部发放
	
	
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
