package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmBenefit_comitListBll;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;

public class EmActy_EmwfareAduitController {
	private EmBenefit_comitListBll bll=new EmBenefit_comitListBll();
	private List<EmWelfareModel> list=bll.getWfListAudit();
	
	
	//弹出审核页面
	@Command
	@NotifyChange("list")
	public void audit(@BindingParam("model") EmWelfareModel model,@BindingParam("win") Window win)
	{
		String sortid=model.getEmwf_sortid();
		if(sortid!=null&&!sortid.equals(""))
		{
			EmActySuppilerGiftInfoModel ml=bll.getEmActySuppilerGiftInfo(" and gift_sortid='"+sortid+"'");
			Integer tarpid=ml.getGift_tarpid();
			Integer gift_id=ml.getGift_id();
			Map map=new HashMap<>();
			map.put("id", tarpid);
			map.put("daid", gift_id);
			map.put("flag", "0");
			Window window=(Window) Executions.createComponents("/EmBenefit/EmActy_AuditInfo.zul", null, map);
			window.doModal();
			list=bll.getWfListAudit();
			if(list.size()<=0)
			{
				if(map.get("flag")=="1")
				{
					Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
			}
		}
	}
	
	public List<EmWelfareModel> getList() {
		return list;
	}
	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
	
	
	
	
}
