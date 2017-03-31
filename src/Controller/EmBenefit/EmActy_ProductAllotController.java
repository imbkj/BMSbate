package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import bll.EmBenefit.EmActy_SupplierSelectBll;
import bll.EmBenefit.EmBenefitAndproductOperateBll;

import Model.EmActySupProductInfoModel;
import Model.EmBenefitModel;
import Util.UserInfo;

public class EmActy_ProductAllotController {
	private EmBenefitModel model = (EmBenefitModel) Executions.getCurrent()
			.getArg().get("model");
	private EmActy_SupplierSelectBll selbll=new EmActy_SupplierSelectBll();
	private List<EmActySupProductInfoModel> prodList=selbll.getEmProductInfoList("",model.getEmbf_name());
	private List<EmActySupProductInfoModel> selectedList=selbll.getEmProductInfo(model.getEmbf_name());

	
	//产品查询
	@Command
	@NotifyChange("prodList")
	public void searchprod(@BindingParam("prodname") String prodname)
	{
		if(prodname!=null&&!prodname.equals(""))
		{
			prodList=selbll.getEmProductInfoList(" and prod_name like '%"+prodname+"%'",model.getEmbf_name());
		}
		else
		{
			prodList=selbll.getEmProductInfoList("",model.getEmbf_name());
		}
	}
	
	//左边产品的点击事件
	@Command
	@NotifyChange({"prodList","selectedList"})
	public void selectesprod(@BindingParam("model") EmActySupProductInfoModel model,
			@BindingParam("a") String a)
	{
		model.setProd_addname(UserInfo.getUsername());
		//选择左边
		if(a!=null&&a.equals("1"))
		{
			selectedList.add(model);
			prodList.remove(model);
			EmBenefitAndproductAdd(model);
		}
		//选择右边
		else if(a!=null&&a.equals("2"))
		{
			selectedList.remove(model);
			prodList.add(model);
			EmBenefitAndproductDel(model);
		}
	}
	
	//关联产品
	private Integer EmBenefitAndproductAdd(EmActySupProductInfoModel m)
	{
		EmBenefitAndproductOperateBll obll=new EmBenefitAndproductOperateBll();
		Integer k=0;
		m.setEmbf_id(model.getEmbf_id());
		k=k+obll.EmBenefitAndproductAdd(m);
		return k;
	}
	
	//关联产品删除
	private Integer EmBenefitAndproductDel(EmActySupProductInfoModel m)
	{
		EmBenefitAndproductOperateBll obll=new EmBenefitAndproductOperateBll();
		Integer k=0;
		m.setEmbf_id(model.getEmbf_id());
		k=k+obll.EmBenefitAndproductUpdate(m);
		return k;
	}
	
	//
	public EmBenefitModel getModel() {
		return model;
	}

	public void setModel(EmBenefitModel model) {
		this.model = model;
	}

	public List<EmActySupProductInfoModel> getProdList() {
		return prodList;
	}

	public void setProdList(List<EmActySupProductInfoModel> prodList) {
		this.prodList = prodList;
	}

	public List<EmActySupProductInfoModel> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<EmActySupProductInfoModel> selectedList) {
		this.selectedList = selectedList;
	}
	
	

}
