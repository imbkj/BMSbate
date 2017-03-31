package Controller.ClientRelations.CreditRating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoLatencyClientModel;
import Model.CreditCriterionModel;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

public class CreditContentInfo_ManagerController{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
	List<CreditCriterionModel> creditmodel=bll.getCreditIndo("");
	private String ifvalidvalue="";
	private String creaditename="";
	private String creaditetype="";
	private String creaditeaddName="";
	
	public List<CreditCriterionModel> getCreditmodel() {
		return creditmodel;
	}
	public void setCreditmodel(List<CreditCriterionModel> creditmodel) {
		this.creditmodel = creditmodel;
	}
	
	//弹出窗口,修改标准信息
	@Command
	public void updateCreditCriterion(@BindingParam("coninfo") final CreditCriterionModel model)
	{
		Map colaMap=new HashMap();
		colaMap.put("model",model);
		Window window = (Window)Executions.createComponents("CreditContentInfo_Edit.zul",null, colaMap);
		window.doModal();
	}
	
	//添加标准
	@Command
	public void addCreditCon(@BindingParam("coninfo") final CoLatencyClientModel model)
	{
		Map map=new HashMap();
		map.put("model",model);
		Window window = (Window)Executions.createComponents("CreditContentAdd.zul",null, map);
		window.doModal();
	}
	
	//添加内容
	@Command
	public void addCreditConInfo(@BindingParam("con") final CreditCriterionModel model)
	{
		Map colaMap=new HashMap();
		colaMap.put("model",model);
		Window window = (Window)Executions.createComponents("CreditContentInfoAdd.zul",null, colaMap);
		window.doModal();
	}
	
	//查询
	@Command
	@NotifyChange("creditmodel")
	public void searchCreditCriterion()
	{
		String str="";
		if(ifvalidvalue=="是"||ifvalidvalue.equals("是"))
		{
			str=str+" and crcr_state=1";
		}
		else if(ifvalidvalue=="否"||ifvalidvalue.equals("否"))
		{
			str=str+" and crcr_state=0";
		}
		if(creaditename!="")
		{
			str=str+" and crcr_content like '%"+creaditename+"%'";
		}
		if(creaditetype!="")
		{
			str=str+" and crcr_type like '%"+creaditetype+"%'";
		}
		if(creaditeaddName!="")
		{
			str=str+" and crcr_addname like '%"+creaditeaddName+"%'";
		}
		creditmodel=bll.getCreditIndo(str);
	}
	public String getIfvalidvalue() {
		return ifvalidvalue;
	}
	public void setIfvalidvalue(String ifvalidvalue) {
		this.ifvalidvalue = ifvalidvalue;
	}
	public String getCreaditename() {
		return creaditename;
	}
	public void setCreaditename(String creaditename) {
		this.creaditename = creaditename;
	}
	public String getCreaditetype() {
		return creaditetype;
	}
	public void setCreaditetype(String creaditetype) {
		this.creaditetype = creaditetype;
	}
	public String getCreaditeaddName() {
		return creaditeaddName;
	}
	public void setCreaditeaddName(String creaditeaddName) {
		this.creaditeaddName = creaditeaddName;
	}
	
}
