package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmActySupProductInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActySupplierInfoModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmActy_SupplierSelectBll;

public class EmActy_GiftInfoAddController {
	private EmActy_GiftInfoSelectBll bll=new EmActy_GiftInfoSelectBll();
	private List<EmActySupplierInfoModel> suplist=bll.getSupName();
	private EmActy_SupplierSelectBll probll=new EmActy_SupplierSelectBll();
	List<EmActySupProductInfoModel> prolist=new ArrayList<EmActySupProductInfoModel>();
	
	//礼品信息新增提交
	@Command
	public void addgift(@BindingParam("giftname") String giftname,@BindingParam("giftband") String giftband,
			@BindingParam("gifttype") String gifttype,@BindingParam("supname") String supname,
			@BindingParam("giftcolor") String giftcolor,@BindingParam("innum") String innum,
			@BindingParam("giftaddress") String giftaddress,@BindingParam("totalrice") String totalrice,
			@BindingParam("win") Window win,@BindingParam("remark") String remark,
			@BindingParam("address") String address,@BindingParam("price") String price,
			@BindingParam("nowprice") String nowprice,@BindingParam("ownmonth") Date ownmonth)
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
		else
		{
			model.setGift_totalnum(0);
		}
		model.setGift_production(giftaddress);
		model.setGift_addname(UserInfo.getUsername());
		model.setGift_inaddress(address);
		model.setGift_remark(remark);
		
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
		if(ownmonth!=null&&!ownmonth.equals(""))
		{
			model.setOwnmonth(Integer.parseInt(datechange(ownmonth)));
		}
		else
		{
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int mon=cal.get(Calendar.MONTH);
			String ownm="";
			if(mon<10)
			{
				ownm=year+"0"+mon;
			}
			else
			{
				ownm=year+""+mon;
			}
			model.setOwnmonth(Integer.parseInt(ownm));
		}
		String str[]=obll.EmActy_GiftAdd(model,"1");
		if(str[0]=="1")
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			Executions.sendRedirect("/EmBenefit/EmActy_GiftInfoAdd.zul");
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//更新
	@Command
	@NotifyChange("prolist")
	public void updatelist(@BindingParam("val") String val)
	{
		String sql=" and prod_supid in(select supp_id from EmActySupplierInfo where supp_name='"+val+"')";
		prolist=probll.getEmActySupProductInfo(sql);
	}
	
	//新增页面根据采购数和折扣价计算总价
	@Command
	public void gettotalprice(@BindingParam("txt") Textbox totaltxt,@BindingParam("innum") String innum,
			@BindingParam("nowprice") String nowprice)
	{
		if(nowprice!=null&&!nowprice.equals("")&&nowprice!=""&&innum!=null&&!innum.equals("")&&innum!="")
		{
			try
			{
				Double d=Double.parseDouble(nowprice);
				totaltxt.setValue(d*Integer.parseInt(innum)+"");
			}
			catch(Exception e)
			{
				System.out.println("错误："+e.getMessage());
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

	private String datechange(Date d)
	{
		String date=null;
		if(d!=null&&!d.equals(""))
		{
			SimpleDateFormat time=new SimpleDateFormat("yyyyMM"); 
			date=time.format(d);
		}
		return date;
	}
}
