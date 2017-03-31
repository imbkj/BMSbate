package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmPay.EmPay_OperateBll;

public class EmActy_AddPaymentController {
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	private String id = (String) Executions.getCurrent().getArg()
			.get("daid");
	private String tapr_id = (String) Executions.getCurrent().getArg()
			.get("id");
	private EmActy_GiftInfoSelectBll bllgift=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> giftlist=bllgift.getEmActyGiftInfo(" and gift_id = "+id);
	private String sortid="",str="",mold="";
	private Window win = (Window) Path.getComponent("/winEmp");
	
	public EmActy_AddPaymentController()
	{
		if(giftlist.size()>0)
		{
			sortid=giftlist.get(0).getGift_sortid();
			mold=giftlist.get(0).getGift_type();
			str=" and embf_mold='"+mold+"'";
		}
		list=bll.getLists(str+" and emwf_sortid='"+sortid+"'");
	}
	
	@Command
	public void addpayment()
	{
		if(list.size()>0)
		{
			String sql=",gift_state=9";
			EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
			EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
			m.setGift_id(Integer.parseInt(id));
			m.setGift_tarpid(Integer.parseInt(tapr_id));
			String[] str=obll.updateEmActy_GiftInfo(m,sql,"2");
			if(str[0]=="1")
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String paynum = "FL" + nowtime; //支付单号，
				String ownmonth=form.format(date);
				String type="福利费";
				int add_message=0;
				EmPay_OperateBll payBll = new EmPay_OperateBll();
				for(int ij=0;ij<list.size();ij++)
				{
					EmWelfareModel wm=list.get(ij);
					if(wm.getProd_discountprice()!=null&&wm.getEmwf_amount()!=null)
					{
						BigDecimal monum=new BigDecimal(wm.getEmwf_amount());
						BigDecimal pr=wm.getProd_discountprice().multiply(monum);
						String sqlp=",emwf_truecharge='"+pr+"',emwf_state=9";
						//更新福利信息表的费用
						obll.updateEmWelfareInfo(sqlp, wm.getEmwf_id());
						
						//添加支付明细
						
						try {
							add_message=add_message+ payBll.add_pay(wm.getGid().toString(),wm.getCid().toString(),paynum,ownmonth,type,pr.toString(),wm.getEmwf_id().toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
				if(add_message>0)
				{
					try {
						String[] message = payBll.up_pay(paynum,ownmonth,type);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				Messagebox.show("提交成功","提示",Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败","提示",Messagebox.OK,Messagebox.ERROR);
			}
		}
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
	
	
}
