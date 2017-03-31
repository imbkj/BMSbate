package Controller.EmBenefit;

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
import org.zkoss.zul.Window;

import Model.SurveyHistoryContentInfoModel;
import Model.SurveyHistoryTitleInfoModel;
import bll.EmBenefit.EmActy_SurveryHistorySelectBll;

public class EmActy_SurveryHistoryListController {
	private EmActy_SurveryHistorySelectBll bll=new EmActy_SurveryHistorySelectBll();
	private List<SurveyHistoryTitleInfoModel> list=bll.getSurveyHistoryTitleInfo("");
	List<SurveyHistoryContentInfoModel> rlist=new ArrayList<SurveyHistoryContentInfoModel>();
	
	
	@Command
	public void addgd(@BindingParam("id") Integer id)
	{
		rlist=bll.getSurveyResultInfo(" and a.relt_contid is NULL and relt_titlid="+id);
	}
	
	
	//查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("surveytype") String surveytype,@BindingParam("ownyear") Date ownyear)
	{
		String sql="";
		if(surveytype!=null&&!surveytype.equals("")&&surveytype!="")
		{
			sql=sql+" and hitl_type="+surveytype;
		}
		else if(ownyear!=null&&!ownyear.equals(""))
		{
			sql=sql+" and hiti_ownyear="+changedate(ownyear);
		}
		list=bll.getSurveyHistoryTitleInfo(sql);
	}
	
	//弹出页面
	@Command
	public void openzul(@BindingParam("model") SurveyHistoryTitleInfoModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents(url, null, map);
		window.doModal();
	}
	public List<SurveyHistoryTitleInfoModel> getList() {
		return list;
	}
	public void setList(List<SurveyHistoryTitleInfoModel> list) {
		this.list = list;
	}
	public List<SurveyHistoryContentInfoModel> getRlist() {
		return rlist;
	}
	public void setRlist(List<SurveyHistoryContentInfoModel> rlist) {
		this.rlist = rlist;
	}
	
	private String changedate(Date d)
	{
		String dateString="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		if(d!=null&&!d.equals(""))
		{
			dateString = formatter.format(d);
		}
		return dateString;
	}
}
