package Controller.EmBenefit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActyGiftOutInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActySupplierInfoModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmActy_SupplierSelectBll;

public class EmActy_GiftInfoEditController {
	private EmActy_GiftInfoSelectBll bll=new EmActy_GiftInfoSelectBll();
	private List<EmActySupplierInfoModel> suplist=bll.getSupName();
	private EmActySuppilerGiftInfoModel m = (EmActySuppilerGiftInfoModel) Executions.getCurrent().getArg().get("model");
	private EmActy_SupplierSelectBll probll=new EmActy_SupplierSelectBll();
	List<EmActySupProductInfoModel> prolist=new ArrayList<EmActySupProductInfoModel>();
	
	//礼品信息修改提交
	@Command
	public void editgiftinfo(@BindingParam("giftname") String giftname,@BindingParam("giftband") String giftband,
			@BindingParam("gifttype") String gifttype,@BindingParam("supname") String supname,
			@BindingParam("giftcolor") String giftcolor,@BindingParam("innum") String innum,
			@BindingParam("giftaddress") String giftaddress,@BindingParam("intime") String intime,
			@BindingParam("win") Window win,@BindingParam("remark") String remark,@BindingParam("totalprice") String totalprice,
			@BindingParam("address") String address,@BindingParam("nownum") String nownum,
			@BindingParam("price") String price,@BindingParam("nowprice") String nowprice)
	{
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		EmActySuppilerGiftInfoModel model=new EmActySuppilerGiftInfoModel();
		model.setGift_name(giftname);
		model.setGift_brand(giftband);
		model.setGift_class(gifttype);
		model.setSupp_name(supname);
		model.setGift_color(giftcolor);
		if(innum!=null&&!innum.equals("")&&innum!="")
		{
			model.setGift_totalnum(Integer.parseInt(innum));
		}
		if(nownum!=null&&!nownum.equals("")&&nownum!="")
		{
			model.setGift_nownum(Integer.parseInt(nownum));
		}
		if(price!=null&&!price.equals("")&&price!="")
		{
			BigDecimal dis=new BigDecimal(price); 
			model.setGift_price(dis);
		}
		if(totalprice!=null&&!totalprice.equals("")&&totalprice!="")
		{
			BigDecimal totalp=new BigDecimal(totalprice); 
			model.setGift_totalprice(totalp);
		}
		if(nowprice!=null&&!nowprice.equals("")&&nowprice!="")
		{
			BigDecimal diss=new BigDecimal(nowprice); 
			model.setGift_nowprice(diss);
		}
		model.setGift_production(giftaddress);
		model.setGift_intime(intime);
		model.setGift_addname(UserInfo.getUsername());
		model.setGift_inaddress(address);
		model.setGift_id(m.getGift_id());
		model.setGift_remark(remark);
		int k=obll.EmActy_GiftEdit(model);
		if(k>0)
		{
			Messagebox.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//礼品信息重新提交
	@Command
	public void addagain(@BindingParam("giftname") String giftname,@BindingParam("giftband") String giftband,
		@BindingParam("gifttype") String gifttype,@BindingParam("supname") String supname,
		@BindingParam("giftcolor") String giftcolor,@BindingParam("innum") String innum,
		@BindingParam("giftaddress") String giftaddress,@BindingParam("totalrice") String totalrice,
		@BindingParam("win") Window win,@BindingParam("remark") String remark,
		@BindingParam("address") String address,@BindingParam("price") String price,
		@BindingParam("nowprice") String nowprice)
	{
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		EmActySuppilerGiftInfoModel model=new EmActySuppilerGiftInfoModel();
		model.setGift_name(giftname);
		model.setGift_brand(giftband);
		model.setGift_class(gifttype);
		model.setSupp_name(supname);
		model.setGift_color(giftcolor);
		if(innum!=null&&!innum.equals("")&&innum!="")
		{
			model.setGift_totalnum(Integer.parseInt(innum));
		}
		model.setGift_production(giftaddress);
		model.setGift_addname(UserInfo.getUsername());
		model.setGift_inaddress(address);
		model.setGift_remark(remark);
		model.setGift_id(m.getGift_id());
		if(price!=null&&!price.equals("")&&price!="")
		{
			BigDecimal dis=new BigDecimal(price); 
			model.setGift_price(dis);
		}
		if(nowprice!=null&&!nowprice.equals("")&&nowprice!="")
		{
			BigDecimal diss=new BigDecimal(nowprice); 
			model.setGift_nowprice(diss);
		}
		if(totalrice!=null&&!totalrice.equals("")&&totalrice!="")
		{
			BigDecimal totalpri=new BigDecimal(totalrice); 
			model.setGift_totalprice(totalpri);
		}
		String str[]=obll.EmActy_GiftAdd(model,"3");
		if(str[0]=="1")
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	@NotifyChange("prolist")
	public void updatelist(@BindingParam("val") String val)
	{
		String sql=" and pro_supid in(select sup_id from EmActySupplierInfo where sup_name='"+val+"')";
		prolist=probll.getEmActySupProductInfo(sql);
	}
	
	//出库
	@Command
	public void outgift(@BindingParam("outnum") Integer outnum,@BindingParam("win") Window win,
			@BindingParam("remark") String remark)
	{
		if(m.getGift_state().equals(3)||m.getGift_state()==3)
		{
			if(outnum!=null&&!outnum.equals(""))
			{
				EmActyGiftOutInfoModel ml=new EmActyGiftOutInfoModel();
				ml.setGout_giftid(m.getGift_id());
				ml.setGout_name(UserInfo.getUsername());
				ml.setGout_num(outnum);
				ml.setGout_remark(remark);
				EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
				int k=obll.EmActy_Giftout(ml);
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
				Messagebox.show("出库量不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("还没有入库不能出库", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//判断出库量是否小于或等于现存量
	@Command
	public void ifmax(@BindingParam("num") Integer num,@BindingParam("outtxt") Intbox outtxt)
	{
		if(num!=null)
		{
			if(num>=0)
			{
				if(num>m.getGift_nownum())
				{
					Clients.showNotification("出库量不能大于现存总量","info",outtxt,"end_center",2000);
					outtxt.setValue(null);
				}
			}
			else
			{
				Clients.showNotification("出库量不能小于0","info",outtxt,"end_center",2000);
				outtxt.setValue(null);
			}
		}
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
	
}
