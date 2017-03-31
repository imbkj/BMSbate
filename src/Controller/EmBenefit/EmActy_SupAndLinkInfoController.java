package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmBenefit.EmActy_SupplierSelectBll;

import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;

public class EmActy_SupAndLinkInfoController {
	private EmActy_SupplierSelectBll bll=new EmActy_SupplierSelectBll();
	private Integer sup_id = (Integer) Executions.getCurrent().getArg().get("sup_id");
	private List<EmActySupContactManInfoModel> conlist=bll.getEmActySupContactManInfo(" and coct_supId="+sup_id);
	private List<EmActySupProductInfoModel> prolist=bll.getEmActySupProductInfo(" and prod_supId="+sup_id);
	private Integer connum=0,pronum=0;
	public EmActy_SupAndLinkInfoController()
	{
		connum=conlist.size();
		pronum=prolist.size();
		
	}
	public List<EmActySupContactManInfoModel> getConlist() {
		return conlist;
	}
	public void setConlist(List<EmActySupContactManInfoModel> conlist) {
		this.conlist = conlist;
	}
	public List<EmActySupProductInfoModel> getProlist() {
		return prolist;
	}
	public void setProlist(List<EmActySupProductInfoModel> prolist) {
		this.prolist = prolist;
	}
	public Integer getConnum() {
		return connum;
	}
	public void setConnum(Integer connum) {
		this.connum = connum;
	}
	public Integer getPronum() {
		return pronum;
	}
	public void setPronum(Integer pronum) {
		this.pronum = pronum;
	}
	
	
}
