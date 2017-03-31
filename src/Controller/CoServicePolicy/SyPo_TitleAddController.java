package Controller.CoServicePolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import bll.CoServicePolicy.SePy_CityPolicyOperateBll;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

import Model.CoServicePolicyTitleDropInfoModel;
import Model.CoServicePolicyTitleModel;

public class SyPo_TitleAddController {
	private SePy_CityPolicySelectBll bll=new SePy_CityPolicySelectBll();
	private List<CoServicePolicyTitleDropInfoModel> droplist=new ArrayList<CoServicePolicyTitleDropInfoModel>();
	private CoServicePolicyTitleModel ml=new CoServicePolicyTitleModel();
	private boolean visableable=false;
	
	//类型的选择事件
	@Command
	@NotifyChange({"visableable","droplist"})
	public void checktype(@BindingParam("cb") Radiogroup cb)
	{
		if(cb.getSelectedItem()!=null)
		{
			ml.setItem_inftype(Integer.parseInt(cb.getSelectedItem().getValue().toString()));
			String val=cb.getSelectedItem().getValue();
			if(val!=null&&val.equals("2"))
			{
				visableable=true;
				Map map=new HashMap<>();
				map.put("droplist", droplist);
				Window window=(Window)Executions.createComponents("Sypo_DropListAdd.zul", null, map);
				window.doModal();
				droplist=(List<CoServicePolicyTitleDropInfoModel>) map.get("droplist");
			}
			else
			{
				visableable=false;
			}
		}
	}
	
	//删除
	@Command
	@NotifyChange("droplist")
	public void del(@BindingParam("model") CoServicePolicyTitleDropInfoModel m)
	{
		droplist.remove(m);
	}
	
	//提交
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		if(ml.getItem_title()!=null&&!ml.getItem_title().equals(""))
		{
			SePy_CityPolicyOperateBll bl=new SePy_CityPolicyOperateBll();
		}
		else
		{
			Messagebox.show("请输入标题", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public List<CoServicePolicyTitleDropInfoModel> getDroplist() {
		return droplist;
	}
	public void setDroplist(List<CoServicePolicyTitleDropInfoModel> droplist) {
		this.droplist = droplist;
	}
	public boolean isVisableable() {
		return visableable;
	}
	public void setVisableable(boolean visableable) {
		this.visableable = visableable;
	}

	public CoServicePolicyTitleModel getMl() {
		return ml;
	}

	public void setMl(CoServicePolicyTitleModel ml) {
		this.ml = ml;
	}
	
	
	
}
