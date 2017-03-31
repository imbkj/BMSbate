package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActyProduceModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;
import bll.EmBenefit.EmActy_SupplierSelectBll;
import bll.EmBenefit.EmBenefit_commitInfoBll;

public class EmBenefit_selSupController {
	private List<EmWelfareModel> list = (List<EmWelfareModel>) Executions
			.getCurrent().getArg().get("list");
	private EmActy_SupplierSelectBll bll = new EmActy_SupplierSelectBll();
	private List<EmActySupplierInfoModel> supList = bll.getEmActySupplierInfo("");
	private EmBenefit_commitInfoBll pbll = new EmBenefit_commitInfoBll();
	private List<EmActyProduceModel> plist =new ArrayList<EmActyProduceModel>();
	
	
	@Command
	@NotifyChange("plist")
	public void selSup(@BindingParam("cb") Combobox cb)
	{
		if(cb.getValue()!=null)
		{
			EmActySupplierInfoModel m=cb.getSelectedItem().getValue();
			plist=pbll.getEmActyProduceBySupId(m.getSupp_id());
		}
	}
	
	@Command
	public void submit(@BindingParam("cb") Combobox cb,@BindingParam("win") Window win)
	{
		if(cb.getValue()!=null)
		{
			EmActyProduceModel m=cb.getSelectedItem().getValue();
			int k=0;
			for(EmWelfareModel wm:list)
			{
				k=k+pbll.editWm(m.getProd_id(), wm.getEmwf_id());
			}
			if(k>0)
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请选择活动名称", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public List<EmActySupplierInfoModel> getSupList() {
		return supList;
	}
	public void setSupList(List<EmActySupplierInfoModel> supList) {
		this.supList = supList;
	}

	public List<EmActyProduceModel> getPlist() {
		return plist;
	}

	public void setPlist(List<EmActyProduceModel> plist) {
		this.plist = plist;
	}
	
	
}
