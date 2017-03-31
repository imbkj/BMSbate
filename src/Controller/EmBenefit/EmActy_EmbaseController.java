package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmPay.EmPay_OperateBll;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import Util.UserInfo;

public class EmActy_EmbaseController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<EmWelfareModel> list = new ListModelList<>();
	private List<EmWelfareModel> listc = new ListModelList<>();
	private EmWelfareModel ewfm = new EmWelfareModel();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	
	private EmActy_GiftInfoSelectBll blls=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> lists=blls.getEmActyGiftInfo(" and gift_id="+id);
	private Window win = (Window) Path.getComponent("/winEmp");
	private String sortid="",giftname="";
	private Integer num=0,noenum;
	String sql="",sqlc="",username=UserInfo.getUsername();
	private EmActySuppilerGiftInfoModel ml=new EmActySuppilerGiftInfoModel();
	
	private List<EmWelfareModel> warelist =new ArrayList<EmWelfareModel>();
	
	
	public EmActy_EmbaseController()
	{
		if(lists.size()>0)
		{
			giftname=lists.get(0).getGift_name();
			noenum=lists.get(0).getGift_nownum();
			ewfm.setEmwf_state(4);
			sortid=lists.get(0).getGift_sortid();
			ewfm.setEmwf_sortid(sortid);
			sql="and emwf_sortid='" + ewfm.getEmwf_sortid()+"'  and emwf_state=" + ewfm.getEmwf_state();
			list=bll.getLists(sql);
			sqlc="and emwf_sortid='" + ewfm.getEmwf_sortid()+"' and (emwf_state=5 or emwf_state=10)";
			listc=bll.getLists(sqlc);
			num=list.size();
			ml=lists.get(0);
			warelist=bll.getEmWelfareList(" and emwf_state=7 ");
		}
	}
	
	
	//全选
	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka) {
		Grid gd = (Grid) win.getFellow("gdList");
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 13)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren().get(0)
						.getChildren().get(0);
				ck.setChecked(cka.isChecked());
				
			}else {
				
				return;
			}

		}
	}
	
	//单个礼品方法
	@Command
	@NotifyChange({"list","num"})
	public void send(@BindingParam("win") Window win,@BindingParam("model") final EmWelfareModel model)
	{
//		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
//	          public void onEvent(ClickEvent event) throws Exception {
//	               if(Messagebox.Button.YES.equals(event.getButton())) { 
//	            	   Integer k=sentgift(id,model.getEmwf_id());
//	            	   if(k>0)
//	            	   {
//	            		   Messagebox.show("发放成功","提示",Messagebox.OK,Messagebox.INFORMATION);
//	            		   list=bll.getLists(sql);
//	            		   num=list.size();
//	            			if(list.size()==0)
//	            			{
//	            				tonext();
//	            			}
//	            			else
//	            			{
//	            				EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
//	            				String sqlstr=",gift_state=3";
//	            				bl.updateGiftInfo(sqlstr, id);
//	            			}
//	            	   }
//	            	   else
//	            	   {
//	            		   Messagebox.show("发放失败","提示",Messagebox.OK,Messagebox.ERROR);
//	            	   }
//	               }
//	           }
//	       };
//	      Messagebox.show("是否确认发放礼品?", "提示", new Messagebox.Button[]{
//	    		  Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
		
		Map map=new HashMap<>();
		map.put("model", model);
		map.put("id", id);
		map.put("tarpid", tapr_id);
		Window window=(Window)Executions.createComponents("../EmBenefit/EmActy_sendGift.zul", null, map);
		window.doModal();
		list=bll.getLists(sql);
		num=list.size();
		if(num<=0)
		{
			win.detach();
		}
	}
	
	//批量发放礼品
	@Command
	@NotifyChange({"list","num"})
	public void sendbatch()
	{
		Integer k=0,kl=0,checknum=0;;
		Grid gd = (Grid) win.getFellow("gdList");
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 14)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren().get(0)
						.getChildren().get(0);
				if(ck.isChecked())
				{
					checknum=checknum+1;
				}
			}		
		}
		if(noenum>=checknum)
		{
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 14)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren().get(0)
						.getChildren().get(0);
				if(ck.isChecked())
				{
					EmWelfareModel ml=new EmWelfareModel();
					ml=ck.getValue();
					Integer ks=sentgift(id,ml.getEmwf_id());
					if(ks>0)
					{
						k=1;
					}
					else
					{
						kl=ks;
					}
				}
			}
		}
		if(k>0)
		{
			Messagebox.show("发放成功","提示",Messagebox.OK,Messagebox.INFORMATION);
 		   list=bll.getLists(sql);
 		  num=list.size();
 			if(list.size()==0)
 			{
 				tonext();
 			}
 			else
 			{
 				EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
 				String sqlstr=",gift_state=3";
 				bl.updateGiftInfo(sqlstr, id);
 			}
		}
		else if(kl==-1)
		{
			 Messagebox.show("礼品库存为0，发放失败","提示",Messagebox.OK,Messagebox.ERROR);
		}
		else
 	   {
 		   Messagebox.show("发放失败","提示",Messagebox.OK,Messagebox.ERROR);
 	   }
		}
		else
		{
			Messagebox.show("库存不足，不能发放","提示",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	
	//发放礼品
	private Integer sentgift(Integer gift_id,Integer emwf_id)
	{
		Textbox clienttxt = (Textbox) win.getFellow("client");
		EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
		Integer k=bl.updateEmWelfare(gift_id, emwf_id,clienttxt.getValue());
		return k;
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
		str=obll.updateEmActy_GiftInfos(m, strsql,"3");
		win.detach();
		return str;
	}
	
	//提交
	@Command
	public void summit()
	{
		String str="提交后流程将转到下一步，是否确认提交";
		if(list.size()>0)
		{
			str="还有礼品没有发放,是否确定转下一步";
		}
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
		 public void onEvent(ClickEvent event) throws Exception {
             if(Messagebox.Button.YES.equals(event.getButton())) {
     			tonext();
             }
		 }
     	};
     	Messagebox.show(str, "提示", new Messagebox.Button[]{
  		  Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
	}
	
	//单个确认员工已报名
	@Command
	@NotifyChange("listc")
	public void link(@BindingParam("model") final EmWelfareModel model)
	{
		Integer k=linkcumstomer(model.getEmwf_id());
		if(k>0)
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			listc=bll.getLists(sqlc);
			//如果所有员工已经联系完毕则自动跳转到下一步
			if(listc.size()==0)
			{
				tonext();
			}
		}
	}
	
	//批量确认员工已报名
	@Command
	@NotifyChange("listc")
	public void linkbatch()
	{
		Integer k=0,l=0;
		Grid gd = (Grid) win.getFellow("gdList");
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 14)!=null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren().get(0)
						.getChildren().get(0);
				if(ck.isChecked())
				{
					l=1;
					EmWelfareModel ml=new EmWelfareModel();
					ml=ck.getValue();
					Integer ks=linkcumstomer(ml.getEmwf_id());
					if(ks>0)
					{
						k=1;
					}
				}
			}
		}
		if(l==1)
		{
		if(k>0)
		{
			Messagebox.show("提交成功","提示",Messagebox.OK,Messagebox.INFORMATION);
 		   listc=bll.getLists(sqlc);
 			if(listc.size()==0)
 			{
 				tonext();
 			}
		}
		else
 	   {
 		   Messagebox.show("发放失败","提示",Messagebox.OK,Messagebox.ERROR);
 	   }
		}
		else
	 	   {
	 		   Messagebox.show("请选择数据","提示",Messagebox.OK,Messagebox.ERROR);
	 	   }
	}
	
	//确认员工已报名
	private Integer linkcumstomer(Integer emwf_id)
	{
		EmActy_GiftInfoOperateBll bl=new EmActy_GiftInfoOperateBll();
		Integer k=bl.updateEmWelfares(changedate(new Date()),"",emwf_id);
		return k;
	}
	
	//生成支付通知
	@Command
	public void pay(@BindingParam("invda") Date invda,@BindingParam("invda") Date vaida,
			@BindingParam("number") String number,@BindingParam("upinvda") Date upinvda)
	{
		String invdastr=changedate(invda);
		String vaidastr=changedate(vaida);
		String sql="";
		String mold=bll.getMold(ml.getGift_sortid());
		System.out.println("mold="+mold);
		if(invdastr!=null&&!invdastr.equals("")&&invdastr!="")
		{
			sql=sql+",gift_invoicedate='"+invdastr+"'";
		}
		if(vaidastr!=null&&!vaidastr.equals("")&&vaidastr!="")
		{
			sql=sql+",gift_validdate='"+vaidastr+"'";
		}
		if(upinvda!=null&&!upinvda.equals(""))
		{
			sql=sql+",gift_invoiceupdate='"+changedate(upinvda)+"'";
		}
		sql=sql+",gift_invoicenumber='"+number+"',gift_state=7";
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		String[] str=obll.updateEmActy_GiftInfo(m,sql,"2");
		if(str[0]=="1")
		{
			Integer nums=bll.getIfPay();
			obll.updateEmWelfareInfo2(ml.getGift_sortid());
			
			String sqlstr="";
			if(mold.equals("礼品")||mold=="礼品")
			{
				sqlstr=" and emwf_state=8 ";
			}
			else
			{
				sqlstr=" and emwf_linktime is not null ";
			}
			List<EmWelfareModel> wlist =bll.getEmWelfareList(sqlstr+" and emwf_sortid='"+ml.getGift_sortid()+"'");
			if(wlist.size()>0)
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
				for(int ij=0;ij<wlist.size();ij++)
				{
					EmWelfareModel wm=wlist.get(ij);
					if(wm.getProd_discountprice()!=null&&wm.getEmwf_amount()!=null)
					{
						BigDecimal monum=new BigDecimal(wm.getEmwf_amount());
						BigDecimal pr=wm.getProd_discountprice().multiply(monum);
						String sqlp=",emwf_truecharge='"+pr+"',emwf_state=10";
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
			//查询是否有还没有支付的员工信息，没有的话则生成一个支付任务单
			if(nums<=0)
			{
				//obll.EmActy_payAdd(ml);
			}
			win.detach();
			Messagebox.show("提交成功","提示",Messagebox.OK,Messagebox.INFORMATION);
		}
		else
		{
			win.detach();
			Messagebox.show("发放失败","提示",Messagebox.OK,Messagebox.ERROR);
		}	
	}
	
	//支付费用
		@Command
		public void payinfo(@BindingParam("paydate") Date paydate,@BindingParam("paymoney") String paymoney,
				@BindingParam("payname") String payname)
		{
			
			String sql="";
			if(paymoney!=null&&!paymoney.equals("")&&paymoney!="")
			{
				sql=sql+",gift_realpay='"+paymoney+"'";
			}
			if(payname!=null&&!payname.equals("")&&payname!="")
			{
				sql=sql+",gift_payname='"+payname+"'";
			}
			if(paydate!=null&&!paydate.equals(""))
			{
				sql=sql+",gift_paydate='"+changedate(paydate)+"'";
			}
			sql=sql+",gift_state=8";
			EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
			EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
			m.setGift_id(id);
			m.setGift_tarpid(tapr_id);
			String[] str=obll.updateEmActy_GiftInfo(m,sql,"2");
			if(str[0]=="1")
			{
				//查询是否有还没有支付的员工信息，如果没有则结束任务单
				Integer nums=bll.getIfPay();
				if(nums<=0)
				{
					obll.EmActy_payUpdate(ml,tapr_id);
				}
				win.detach();
				Messagebox.show("提交成功","提示",Messagebox.OK,Messagebox.INFORMATION);
			}
			else
			{
				win.detach();
				Messagebox.show("发放失败","提示",Messagebox.OK,Messagebox.ERROR);
			}	
		}
	
	
	
	//全选（客户报名页面）
	@Command
	public void checkallc(@BindingParam("a") Checkbox ck,@BindingParam("gd") Grid gd)
	{
		if(listc.size()>0)
		{
			for(int i=0;i<gd.getRows().getChildren().size();i++)
			{
				if (gd.getCell(i, 14).getChildren().get(0).getChildren().get(0)!=null) {
					Checkbox c=(Checkbox) gd.getCell(i,14).getChildren().get(0).getChildren().get(0);
					c.setChecked(ck.isChecked());
				}
			}
		}
	}
	
	//全选（生成支付账单页面）
	@Command
	public void checkalls(@BindingParam("a") Checkbox ck,@BindingParam("gd") Grid gd)
	{
		if(warelist.size()>0)
		{
			for(int i=0;i<gd.getRows().getChildren().size();i++)
			{
				if (gd.getCell(i, 7)!=null) {
					Checkbox c=(Checkbox) gd.getCell(i, 7).getChildren().get(0);
					c.setChecked(ck.isChecked());
				}
			}
		}
	}
	
	//报名取消
	@Command
	@NotifyChange("listc")
	public void cancel(@BindingParam("win") Window win,@BindingParam("model") final EmWelfareModel model)
	{
		Map map=new HashMap<>();
		map.put("id", model.getEmwf_id());
		Window window =(Window)Executions.createComponents("../EmBenefit/EmActy_Cancel.zul", null, map);
		window.doModal();
		listc=bll.getLists(sqlc);
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
	
	public String getGiftname() {
		return giftname;
	}


	public void setGiftname(String giftname) {
		this.giftname = giftname;
	}


	public Integer getNum() {
		return num;
	}


	public void setNum(Integer num) {
		this.num = num;
	}


	public List<EmWelfareModel> getListc() {
		return listc;
	}


	public void setListc(List<EmWelfareModel> listc) {
		this.listc = listc;
	}


	public EmWelfareModel getEwfm() {
		return ewfm;
	}


	public void setEwfm(EmWelfareModel ewfm) {
		this.ewfm = ewfm;
	}


	public String getSortid() {
		return sortid;
	}


	public void setSortid(String sortid) {
		this.sortid = sortid;
	}
	

	public EmActySuppilerGiftInfoModel getMl() {
		return ml;
	}


	public void setMl(EmActySuppilerGiftInfoModel ml) {
		this.ml = ml;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public List<EmWelfareModel> getWarelist() {
		return warelist;
	}


	public void setWarelist(List<EmWelfareModel> warelist) {
		this.warelist = warelist;
	}


	private String changedate(Date d)
	{
		String dateString="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(d!=null&&!d.equals(""))
		{
			dateString = formatter.format(d);
		}
		return dateString;
	}
	
}
