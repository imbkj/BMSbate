package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmPay.EmPay_OperateBll;

public class EmActy_hdprepayController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private EmActySuppilerGiftInfoModel model=new EmActySuppilerGiftInfoModel();
	private EmActy_GiftInfoSelectBll bll=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> list=bll.getEmActyGiftInfo(" and gift_id="+id);
	private String sortid;
	private BigDecimal allpri=new BigDecimal(0.0);
	private BigDecimal prepay=new BigDecimal(0.0);//预支付金额
	private BigDecimal evpay=new BigDecimal(0.0);//每一份礼品需要支付金额
	private Integer zn=0;//总份数
	EmBenefit_comitListBll bllss = new EmBenefit_comitListBll();
	List<EmWelfareModel> listss = new ListModelList<>();
	public EmActy_hdprepayController()
	{
		
		if(list.size()>0)
		{
			model=list.get(0);
			sortid=model.getGift_sortid();
			listss=bllss.getLists(" and emwf_state=9 and emwf_sortid='"+sortid+"'");
			if(listss.size()>0)
			{
				for(int i=0;i<listss.size();i++)
				{
					if(listss.get(i).getEmwf_amount()!=null&&listss.get(i).getProd_discountprice()!=null)
					{
						BigDecimal num=new BigDecimal(listss.get(i).getEmwf_amount());
						allpri=allpri.add(listss.get(i).getProd_discountprice().multiply(num));
						zn=zn+listss.get(i).getEmwf_amount();
					}
				}
			}
		}
	}
	
	//活动预付款生成支付通知
	@Command
	public void addpayinfo(@BindingParam("win") Window win)
	{
		if(listss.size()>0)
		{
			EmBenefit_comitListBll mlbll = new EmBenefit_comitListBll();
			String sql="";
			sql=sql+"";
			EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
			EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
			m.setGift_id(id);
			m.setGift_tarpid(tapr_id);
			m.setGift_remark("生成支付通知");
			String[] str=obll.updateEmActy_GiftInfo(m,sql,"2");
			if(str[0]=="1")
			{
				BigDecimal pr=new BigDecimal(0);
				if(listss.size()>0)
				{
					SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
					Date date = new Date();
					String nowtime = formatter.format(date);
					String paynum = "FL" + nowtime; //支付单号，
					String ownmonth=form.format(date);
					String type="福利费";
					int add_message=0;
					EmPay_OperateBll payBll = new EmPay_OperateBll();
					//计算每一份需要预支付多少金额
					BigDecimal znn=new BigDecimal(zn);//份数类型转换
					evpay=prepay.divide(znn,2,BigDecimal.
							 ROUND_HALF_UP);//计算每一份礼品预支付多少钱
					for(int ij=0;ij<listss.size();ij++)
					{
						EmWelfareModel wm=listss.get(ij);
						if(wm.getProd_discountprice()!=null&&wm.getEmwf_amount()!=null)
						{
							BigDecimal monum=new BigDecimal(wm.getEmwf_amount());
							pr=evpay.multiply(monum);//计算每个人需要预支付多少钱
							String sqlp=",emwf_truecharge='"+pr+"',emwf_state=14,emwf_paynum='"+paynum+"'";
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
				}
				//把预付款金额更新到礼品表
				obll.updateGiftInfo(",gift_prepay="+prepay,m.getGift_id());
				win.detach();
				Messagebox.show("提交成功","提示",Messagebox.OK,Messagebox.INFORMATION);
			}
			else
			{
				win.detach();
				Messagebox.show("发放失败","提示",Messagebox.OK,Messagebox.ERROR);
			}	
		}
		else
		{
			Messagebox.show("没有数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

		public BigDecimal getAllpri() {
			return allpri;
		}

		public void setAllpri(BigDecimal allpri) {
			this.allpri = allpri;
		}

		public List<EmWelfareModel> getListss() {
			return listss;
		}

		public void setListss(List<EmWelfareModel> listss) {
			this.listss = listss;
		}

		public BigDecimal getPrepay() {
			return prepay;
		}

		public void setPrepay(BigDecimal prepay) {
			this.prepay = prepay;
		}
}
