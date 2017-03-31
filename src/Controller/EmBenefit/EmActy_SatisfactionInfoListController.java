package Controller.EmBenefit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.SurveyInfoModel;
import bll.EmBenefit.EmActy_SurveyInfoSelectBll;

public class EmActy_SatisfactionInfoListController {
	private EmActy_SurveyInfoSelectBll bll=new EmActy_SurveyInfoSelectBll();
	private List<SurveyInfoModel> list=bll.getSurverTitleInfo("");
	
	
	//全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,@BindingParam("ck") Checkbox ck)
	{
		int n=gd.getPageSize();
		if(list.size()<gd.getPageSize())
		{
			n=list.size();
		}
		for (int i = 0; i < n; i++) {
			Checkbox cb = (Checkbox) gd.getCell(i,6).getChildren().get(0);
			cb.setChecked(ck.isChecked());
		}
	}
	
	
	//弹出选择的编辑页面
	@Command
	public void edit(@BindingParam("win") Window win,@BindingParam("gd") Grid gd)
	{
		List<SurveyInfoModel> mlist=new ArrayList<SurveyInfoModel>();
		int n=gd.getPageSize();
		if(list.size()<gd.getPageSize())
		{
			n=list.size();
		}
		for (int i = 0; i < n; i++) {
			Checkbox cb = (Checkbox) gd.getCell(i,6).getChildren().get(0);
			if(cb.isChecked())
			{
				SurveyInfoModel m=cb.getValue();
				mlist.add(m);
			}
		}
		if(mlist.size()>0)
		{
			Map map=new HashMap<>();
			map.put("list", mlist);
			Window window=(Window)Executions.createComponents("EmActy_SurveryInfo.zul", null, map);
			window.doModal();
		}
		else
		{
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//弹出修改页面
	@Command
	@NotifyChange("list")
	public void editinfo(@BindingParam("model") SurveyInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("EmActy_SurveryInfoEdit.zul", null, map);
		window.doModal();
		list=bll.getSurverTitleInfo("");
	}
	public List<SurveyInfoModel> getList() {
		return list;
	}
	public void setList(List<SurveyInfoModel> list) {
		this.list = list;
	}
	
	

}
