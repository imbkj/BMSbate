package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.Archives.EmArchive_SelectBll;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;

import Model.EmActyContactContentInfoModel;
import Model.EmActyGiftBackInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;

public class EmActy_GiftBuyUpdateController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private EmActySuppilerGiftInfoModel model=new EmActySuppilerGiftInfoModel();
	private EmActy_GiftInfoSelectBll bll=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> list=bll.getEmActyGiftInfo(" and gift_id="+id);
	private String username=UserInfo.getUsername();
	EmArchive_SelectBll tbll=new EmArchive_SelectBll();
	List<TaskProcessViewModel> tlist=new ArrayList<TaskProcessViewModel>();
	TaskProcessViewModel tmodel=new TaskProcessViewModel();
	private String steppeopstr,steptimestr,sortid;
	private Integer buynum=0;
	
	public EmActy_GiftBuyUpdateController()
	{
		try{
			tlist=tbll.getLastId(tapr_id+"");
		if(list.size()>0)
		{
			model=list.get(0);
			sortid=model.getGift_sortid();
			buynum=model.getGift_nownum();
		}
		if(tlist.size()>0)
		{
			tmodel=tlist.get(0);
			if(tmodel.getWfno_step()==2)
			{
				steppeopstr="审核人";
				steptimestr="审核时间";
			}
			else if(tmodel.getWfno_step()==5)
			{
				steppeopstr="采购人";
				steptimestr="采购时间";
			}
//			else if(tmodel.getWfno_step()==5)
//			{
//				steppeopstr="入库人";
//				steptimestr="入库时间";
//			}
		}
		}
		catch(Exception e)
		{
			System.out.println("错误信息："+e.getMessage());
		}
	}
	
	//更新采购申请信息并更新任务单
	@Command
	public void EditGift(@BindingParam("win") Window win,@BindingParam("dateval") Date dateval,
			@BindingParam("remark") String remark)
	{
		List<EmWelfareModel> lists = new ListModelList<>();
		EmBenefit_comitListBll sbll=new EmBenefit_comitListBll();
		String strsql="",idstr="";
		Integer stateId=null;
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		//审核
		if(tmodel.getWfno_step()==2)
		{
			stateId=5;
			lists=sbll.getLists(" and emwf_state=4 and emwf_sortid='"+sortid+"'");
			strsql=",gift_AuditTime='"+datechange(dateval)+"',gift_AuditName='"+UserInfo.getUsername()+"',gift_state=1,gift_remark='"+remark+"'";
		}
		//联系供应商（通知供应商供货——采购）
		else if(tmodel.getWfno_step()==5)
		{
			stateId=6;
			String sqls="";
			if(model.getGift_paytype().equals("预付款"))
			{
				sqls=" and emwf_state=10 ";
			}
			lists=sbll.getLists(" and emwf_paytype is not null and emwf_sortid='"+sortid+"'"+sqls);
			strsql=",gift_buytime='"+datechange(dateval)+"',gift_buyname='"+UserInfo.getUsername()+"',gift_state=4,gift_remark='"+remark+"'";
		}
		
//		else if(tmodel.getWfno_step()==5)
//		{
//			strsql=",gift_intime='"+datechange(dateval)+"',gift_inname='"+UserInfo.getUsername()+"',gift_state=5,gift_remark='"+remark+"'";
//		}
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(model.getGift_id());
		m.setGift_remark(tmodel.getWfno_name());
		m.setGift_tarpid(model.getGift_tarpid());
		String[] str=obll.updateEmActy_GiftInfo(m, strsql,"2");
		for(int j=0;j<lists.size();j++)
		{
			if(j==0)
			{
				idstr=lists.get(j).getEmwf_id()+"";
			}
			else
			{
				idstr=idstr+","+lists.get(j).getEmwf_id();
			}
		}
		if(str[0]=="1")
		{
			if(tmodel.getWfno_step()==5)
			{
				String content="";
				if(content!=null&&!content.equals("")&&content!="")
				{
					EmActyContactContentInfoModel cml=new EmActyContactContentInfoModel();
					cml.setCact_addname(username);
					cml.setCact_content(content);
					cml.setCact_gift_id(id);
					cml.setCact_remark(remark);
					obll.EmActyContactContentInfo(cml);
				}
				//更新emwfare表的状态
				if(!idstr.equals("") && stateId!=null)
				{
					obll.updateEmWelfare(sortid, idstr,stateId);
				}
			}
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//审核
	@Command
	public void AuditGift(@BindingParam("win") Window win,@BindingParam("dateval") Date dateval,
		@BindingParam("remark") String remark,
		@BindingParam("realinprice") String realinprice,@BindingParam("inname") String inname,
		@BindingParam("intime") Date intime)
	{
		String strsql="";
		EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
		//审核
		strsql=",gift_AuditTime='"+datechange(dateval)+"',gift_AuditName='"+UserInfo.getUsername()+"'," +
				"gift_state=1,gift_remark='"+remark+"'";
		strsql=strsql+",gift_realinmoney='"+realinprice+"',gift_realinname='"+inname+"',gift_realintime='"+datechange(intime)+"' ";
		EmActySuppilerGiftInfoModel m=new EmActySuppilerGiftInfoModel();
		m.setGift_id(model.getGift_id());
		m.setGift_remark(tmodel.getWfno_name());
		m.setGift_tarpid(model.getGift_tarpid());
		String ty="";
		String idstr="";
		if(list.size()>0)
		{
			EmWelfareModel ewfm = new EmWelfareModel();
			ewfm.setEmwf_state(4);
			String sortid=list.get(0).getGift_sortid();
			ewfm.setEmwf_sortid(sortid);
			EmBenefit_comitListBll bllss = new EmBenefit_comitListBll();
			List<EmWelfareModel> listss = new ListModelList<>();
			listss=bllss.getLists(" and emwf_state=4 and emwf_sortid='"+sortid+"'");
			
			if(listss.size()>0)
			{
				if(listss.get(0).getEmbf_mold().equals("礼品")||listss.get(0).getEmbf_mold()=="礼品")
				{
					ty="3";
				}
				else
				{
					ty="2";
				}
				for(int j=0;j<listss.size();j++)
				{
					if(j==0)
					{
						idstr=listss.get(j).getEmwf_id()+"";
					}
					else
					{
						idstr=idstr+","+listss.get(j).getEmwf_id();
					}
				}
			}
		}
		String[] str=new String[5];
		if(ty.equals("2"))
		{
			str=obll.updateEmActy_skip(m, strsql);
		}
		else
		{
			str=obll.updateEmActy_GiftInfos(m, strsql,ty);
		}
		if(str[0]=="1")
		{
			if(!idstr.equals(""))
			{
				obll.updateEmWelfare(sortid, idstr,5);
			}
			win.detach();
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}
		else
		{
			
			win.detach();
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//退回采购申请
	@Command
	public void returnGift(@BindingParam("win") Window win,@BindingParam("cause") String cause)
	{
		if(cause!=null&&!cause.equals("")&&cause!="")
		{
			EmActyGiftBackInfoModel model=new EmActyGiftBackInfoModel();
			model.setGibk_cause(cause);
			model.setGtbk_giftid(id);
			model.setGtbk_name(UserInfo.getUsername());
			EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
			String[] str=obll.backEmActy_GiftInfo(model, tapr_id);
			if(str[0]=="1")
			{
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
			Messagebox.show("退回原因不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//弹出退回页面
	@Command
	public void back(@BindingParam("win") Window win)
	{
		win.detach();
		Map map=new HashMap<>();
		map.put("sortid", sortid);
		map.put("tarpid", tapr_id);
		map.put("id", id);
		Window window=(Window)Executions.createComponents("../EmBenefit/EmActy_backInfo.zul", null, map);
		window.doModal();
		
	}
	public EmActySuppilerGiftInfoModel getModel() {
		return model;
	}
	public void setModel(EmActySuppilerGiftInfoModel model) {
		this.model = model;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getSteppeopstr() {
		return steppeopstr;
	}

	public void setSteppeopstr(String steppeopstr) {
		this.steppeopstr = steppeopstr;
	}

	public String getSteptimestr() {
		return steptimestr;
	}

	public void setSteptimestr(String steptimestr) {
		this.steptimestr = steptimestr;
	}
	
	public TaskProcessViewModel getTmodel() {
		return tmodel;
	}

	public void setTmodel(TaskProcessViewModel tmodel) {
		this.tmodel = tmodel;
	}
	

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}
	
	
	
	public Integer getBuynum() {
		return buynum;
	}

	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}

	private String datechange(Date d)
	{
		String date="";
		if(d!=null&&!d.equals(""))
		{
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
			date=time.format(d);
		}
		return date;
	}
}
