package Controller.EmBenefit;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmActy_NewOperateFactory;
import bll.EmBenefit.EmActy_NewOperateFactoryImpl;
import bll.EmBenefit.EmActy_NewOperateService;
import bll.EmBenefit.EmBenefit_comitListBll;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;

public class EmActy_paytypeController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	private EmActy_GiftInfoSelectBll blls=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> lists=blls.getEmActyGiftInfo(" and gift_id="+id);
	private String sortid="",sql="",paytype="";
	
	
	public EmActy_paytypeController()
	{
		if(lists.size()>0)
		{
			sortid=lists.get(0).getGift_sortid();
			sql="and emwf_sortid='" +sortid+"'  and emwf_state=5";
			list=bll.getLists(sql);
		}
	}


	//付款方式提交
	@Command
	public void addpaytype(@BindingParam("win") Window win)
	{
		String passType="下一步",skipType="";
		if(paytype!=null&&!paytype.equals(""))
		{
			if(paytype.equals("货到付款"))
			{
				skipType="跳过下一步";
			}
			String sqls=",emwf_paytype='"+paytype+"'";
			EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
			int k=obll.updateEmWelfareBySortid(sortid, sqls);
			if(k>0)
			{
				String strsql=",gift_paytype='"+paytype+"'";
				EmActy_NewOperateFactory factory=new EmActy_NewOperateFactoryImpl();
				EmActy_NewOperateService service=factory.operateFactory(passType);
				
				EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
				m.setGift_id(id);
				m.setGift_tarpid(tapr_id);
				m.setGift_remark("确认付款方式");
				String[] str=service.edit(m, strsql);
				
				if(str[0]=="1")
				{
					if(skipType!=null&&skipType.equals("跳过下一步"))
					{
						service=factory.operateFactory(skipType);
						Integer newTarpId=Integer.parseInt(str[2].toString());
						m.setGift_tarpid(newTarpId);
						m.setGift_remark("跳过预支付");
						str=service.edit(m, "");
						if(str[0].equals("1"))//如果跳过成功
						{
							Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
							win.detach();
						}else//如果跳过不成功，则把流程退回并把支付方式清空
						{
							sqls=",emwf_paytype=''";
							k=obll.updateEmWelfareBySortid(sortid, sqls);
							m.setGift_remark("跳过预支付失败后回滚");
							service=factory.operateFactory("退回上一步");
							str=service.edit(m, "");
						}
					}else
					{
						Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
						win.detach();
					}
				}
				else
				{
					Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
			
		}
		else
		{
			Messagebox.show("请选择付款方式", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public List<EmWelfareModel> getList() {
		return list;
	}


	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}


	public String getPaytype() {
		return paytype;
	}


	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	
	

}
