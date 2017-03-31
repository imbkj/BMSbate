package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmActyGiftBackInfoModel;
import Model.EmActyGiftOutInfoModel;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;

public class EmActy_GiftOutLogListController {
	private Integer gift_id = (Integer) Executions.getCurrent().getArg().get("gift_id");
	private EmActy_GiftInfoSelectBll bll=new EmActy_GiftInfoSelectBll();
	private List<EmActyGiftOutInfoModel> list=bll.getEmActyGiftOutInfo(" and gout_giftid="+gift_id);
	private List<EmActyGiftBackInfoModel> blist=bll.getEmActyGiftBackInfo(" and gtbk_giftid="+gift_id);
	private Integer listnum,blistnum;
	public EmActy_GiftOutLogListController()
	{
		listnum=list.size();
		blistnum=blist.size();
	}
	public List<EmActyGiftOutInfoModel> getList() {
		return list;
	}
	public void setList(List<EmActyGiftOutInfoModel> list) {
		this.list = list;
	}
	public List<EmActyGiftBackInfoModel> getBlist() {
		return blist;
	}
	public void setBlist(List<EmActyGiftBackInfoModel> blist) {
		this.blist = blist;
	}
	public Integer getListnum() {
		return listnum;
	}
	public void setListnum(Integer listnum) {
		this.listnum = listnum;
	}
	public Integer getBlistnum() {
		return blistnum;
	}
	public void setBlistnum(Integer blistnum) {
		this.blistnum = blistnum;
	}
	
}
