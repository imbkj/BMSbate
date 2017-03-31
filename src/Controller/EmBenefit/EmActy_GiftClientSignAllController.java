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
import Util.UserInfo;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;

public class EmActy_GiftClientSignAllController {


	private List<EmWelfareModel> list = (List<EmWelfareModel>) Executions.getCurrent().getArg()
			.get("list");
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("tarpid").toString());
	private String paytype=Executions.getCurrent().getArg()
			.get("paytype").toString();
	private String sortid="";
	private EmActy_GiftInfoSelectBll blls=new EmActy_GiftInfoSelectBll();
	private String name=UserInfo.getUsername();
	
	public EmActy_GiftClientSignAllController()
	{
		if(list.size()>0)
		{
			sortid=list.get(0).getEmwf_sortid();
		}
	}
	
	
	//批量签收礼品
	
	@Command
	public void sign(@BindingParam("win") Window win,@BindingParam("signtime") Date signtime,
			@BindingParam("signclient") String signclient)
	{
		EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
		if(list.size()>0)
		{
			Integer k=0;
			for(int i=0;i<list.size();i++)
			{
				EmWelfareModel model=list.get(i);
				model.setEmwf_sendtime(DatetoSting(signtime));
				model.setEmwf_takeclient(signclient);
				String sql=",emwf_signclient='"+signclient+"',emwf_clientsigntime='"+DatetoSting(signtime)+"',emwf_state=8";
				k=k+bl.updateEmWelfareInfo(sql, model.getEmwf_id());
			}
			if(k>0)
			{
				Integer m=blls.getEmWelfare(" and emwf_state=7 and emwf_sortid='"+sortid+"'");
				if(m==0)
				{
					tonext();
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
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
		String strsql=",gift_state=6";
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		str=obll.updateEmActy_GiftInfos(m, strsql,"1");
		return str;
	}
	
	//流程转到下一步(预付款)——直接结束流程
	private String[] tonexts()
	{
		String strsql=",gift_state=6";
		String[] str=new String[5];
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		m.setGift_remark("签收礼品");
		//String strsql=",gift_state=5";
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		str=obll.endEmActy_GiftInfos(m, strsql,"3");
		return str;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


}
