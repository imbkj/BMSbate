package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySupProductInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmActy_SupplierSelectBll;

public class EmActy_GiftInfoUpAgainController {
	private List<EmWelfareModel> list =(List<EmWelfareModel>) Executions.getCurrent().getArg()
			.get("list");
	private String gifttype =(String) Executions.getCurrent().getArg()
			.get("gifttype");
	private String id =(String) Executions.getCurrent().getArg()
			.get("id");
	private String tarpid =(String) Executions.getCurrent().getArg()
			.get("tarpid");
	private EmActy_GiftInfoSelectBll bll=new EmActy_GiftInfoSelectBll();
	private List<EmActySupplierInfoModel> suplist=bll.getSupName();
	private EmActy_SupplierSelectBll probll=new EmActy_SupplierSelectBll();
	private List<EmActySupProductInfoModel> prolist=new ArrayList<EmActySupProductInfoModel>();
	private EmActySupProductInfoModel promodel=new EmActySupProductInfoModel();
	private EmActySupplierInfoModel supmodel=new EmActySupplierInfoModel();
	private EmActySuppilerGiftInfoModel m =new EmActySuppilerGiftInfoModel();
	private Double pri=0.00;
	private BigDecimal ypri,nowpri;
	private List<EmActySuppilerGiftInfoModel> giftlist=bll.getEmActyGiftInfo(" and gift_id="+id);
	
	public EmActy_GiftInfoUpAgainController()
	{
		if(giftlist.size()>0)
		{
			m=giftlist.get(0);
			ypri=m.getGift_price();
			nowpri=m.getGift_nowprice();
		}
	}
	
	
	//供应商下来列表事件（根据选择的供应商查询报价报价信息）
	@Command
	@NotifyChange("prolist")
	public void updatelist(@BindingParam("val") String val)
	{
		String sql=" and prod_supid in(select supp_id from EmActySupplierInfo where supp_name='"+val+"')";
		prolist=probll.getEmActySupProductInfo(sql);
	}
	
	
	//采购总价的计算事件
	@Command
	@NotifyChange("m")
	public void getPrice(@BindingParam("innum") Integer innum,@BindingParam("nowprice") Double nowprice)
	{
		if(innum!=null&&nowprice!=null&&innum>0)
		{
			pri=innum*nowprice;
		}
		else
		{
			pri=0.00;
		}
		if(pri!=null&&!pri.equals(""))
		{
			BigDecimal p=new BigDecimal(pri);
			m.setGift_totalprice(p);
		}
	}
	
	//提交事件
	@Command
	public void summit(@BindingParam("addwin") Window win,@BindingParam("ownmonth") String ownmonth)
	{
		if(ownmonth==null||ownmonth.equals(""))
		{
			Messagebox.show("请选择所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			String idstr="";
			for(int i=0;i<list.size();i++)
			{
				if(i==0)
				{
					idstr=list.get(0).getEmwf_id()+"";
				}
				else
				{
					idstr=idstr+","+list.get(i).getEmwf_id()+"";
				}
			}
		//m.setGift_tarpid(tapr_id);
		m.setGift_addname(UserInfo.getUsername());
		m.setGift_inaddress(supmodel.getSupp_address());
		if(ownmonth!=null&&!ownmonth.equals(""))
		{
			m.setOwnmonth(Integer.parseInt(ownmonth));
		}
		EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
		String str[]=bl.updateEmActy_GiftInfo(m,"","3");
		if(str[0]=="1")
		{
			
			if(idstr!=null&&!idstr.equals("")&&idstr!="")
			{
				bl.updateEmWelfare(m.getGift_sortid(), idstr,4);
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
	
	
	public List<EmWelfareModel> getList() {
		return list;
	}
	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
	public List<EmActySupplierInfoModel> getSuplist() {
		return suplist;
	}
	public void setSuplist(List<EmActySupplierInfoModel> suplist) {
		this.suplist = suplist;
	}
	public List<EmActySupProductInfoModel> getProlist() {
		return prolist;
	}
	public void setProlist(List<EmActySupProductInfoModel> prolist) {
		this.prolist = prolist;
	}

	public EmActySupProductInfoModel getPromodel() {
		return promodel;
	}

	public void setPromodel(EmActySupProductInfoModel promodel) {
		this.promodel = promodel;
	}

	public EmActySupplierInfoModel getSupmodel() {
		return supmodel;
	}

	public void setSupmodel(EmActySupplierInfoModel supmodel) {
		this.supmodel = supmodel;
	}

	public Double getPri() {
		return pri;
	}

	public void setPri(Double pri) {
		this.pri = pri;
	}

	public EmActySuppilerGiftInfoModel getM() {
		return m;
	}

	public void setM(EmActySuppilerGiftInfoModel m) {
		this.m = m;
	}
	private String changedate(Date d)
	{
		String dateString="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if(d!=null&&!d.equals(""))
		{
			dateString = formatter.format(d);
		}
		return dateString;
	}
	
	//根据时间生成批量号
	private String changedate()
	{
		String dateString="";
		Date now = new Date(); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		dateString = formatter.format(now);
		return dateString;
	}
}
