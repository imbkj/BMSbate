package Controller.CoQuotation;

import impl.MessageImpl;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.LoginDal;

import service.MessageService;

import bll.CoQuotation.CoQuotation_List1Bll;
import bll.CoQuotation.CoofferOperateBll;

import Model.CoOfferModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class CoQuotation_AuditBackController {
	private String id = (String)Executions.getCurrent().getArg().get("id");
	private String tarpid = (String)Executions.getCurrent().getArg().get("tarpid");
	private CoOfferModel model = (CoOfferModel)Executions.getCurrent().getArg().get("model");
	private String backcase="";
	private Map map=Executions.getCurrent().getArg();
	//退回提交
	@Command
	public void back(@BindingParam("win") Window win)
	{
		LoginDal d =new LoginDal();
		if(backcase!=null&&!backcase.equals(""))
		{
			CoofferOperateBll bll=new CoofferOperateBll();
			model.setCoof_backcase(backcase);
			String[] str=bll.CoofferAuditBack(model,Integer.parseInt(tarpid));
			if(str[0]=="1")
			{
				CoQuotation_List1Bll bls=new CoQuotation_List1Bll();
				String company=bls.getCompany(Integer.parseInt(id));
				map.put("flag","1");
				//发短信
				MessageService msgservice=new MessageImpl("CoOffer",model.getCoof_id());
				SysMessageModel msgmodel=new SysMessageModel();
				msgmodel.setSyme_content(backcase);//短信内容
				msgmodel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));//发件人id
				msgmodel.setSymr_name(model.getCoof_auditaddname());//收件人姓名
				msgmodel.setSyme_title("“"+company+"”报价单“"+model.getCoof_name()+"”被退回");
				msgmodel.setSymr_log_id(d.getUserIDByname(model.getCoof_auditaddname()));//;
				msgmodel.setEmail(1);
				msgmodel.setEmailtitle("“"+company+"”报价单“"+model.getCoof_name()+"”被退回");
				msgservice.Add(msgmodel);
				Messagebox.show("退回成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请输入退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getBackcase() {
		return backcase;
	}

	public void setBackcase(String backcase) {
		this.backcase = backcase;
	}
	
}
