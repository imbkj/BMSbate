package Controller.CoServicePolicy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.poi.util.Beta;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.CoServicePolicy.SePy_CityPolicyOperateBll;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

import Model.CoAgencyBaseCityRelModel;
import Model.CoServicePolicyModel;
import Model.CoServicePolicyTitleDropInfoModel;
import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;
import Util.UserInfo;

public class SePy_CityPolicyInfoAddController {
	private String agencies = (String)Executions.getCurrent().getArg().get("classname");
	private CoAgencyBaseCityRelModel citymodel = (CoAgencyBaseCityRelModel)Executions.getCurrent().getArg().get("model");
	private SePy_CityPolicySelectBll bll=new SePy_CityPolicySelectBll();
	private List<CoServicePolicyTypeModel> list=new ArrayList<CoServicePolicyTypeModel>();
	private List<CoServicePolicyTypeModel> typelist=bll.getCoServicePolicyType("");
	private List<CoServicePolicyTitleModel> titlelist=new ArrayList<CoServicePolicyTitleModel>();
	private String typename="";
	private boolean btnvis=false;
	private Button btn;
	private List<CoServicePolicyTitleDropInfoModel> droplist=bll.getDropInfoList("");
	
	//类型的点击事件
	@Command
	@NotifyChange({"titlelist","typename","btnvis"})
	public void changetitle(@BindingParam("model") CoServicePolicyTypeModel model,
			@BindingParam("btn") Button btn)
	{
		titlelist.clear();
		titlelist=bll.getCoServicePolicyTitles(" and item_type_id="+model.getNote_id(),citymodel.getCabc_id());
		typename=model.getNote_type();
		btnvis=true;
		this.btn=btn;
	}
	
	//提交
	@Command
	@NotifyChange({"titlelist","typename","btnvis"})
	public void summit(@BindingParam("win") Window win,@BindingParam("gd") Grid gd)
	{
		if(titlelist.size()>0)
		{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		String om=""+month;
		if(month<10)
		{
			om="0"+month;
		}
		String strowm=year+""+om;
		SePy_CityPolicyOperateBll obll=new SePy_CityPolicyOperateBll();
		int id=0;
		for(int i=0;i<gd.getRows().getChildren().size();i++)
		{
			if(gd.getCell(i, 0)!=null)
			{
				Label lb= (Label) gd.getCell(i, 0).getChildren().get(1);
				Label titlelb= (Label) gd.getCell(i, 1).getChildren().get(0);
				Combobox cb=(Combobox) gd.getCell(i, 2).getChildren().get(0);
				String itemid=lb.getValue();
				String title=titlelb.getValue();
				//CoServicePolicyTitleModel m=new CoServicePolicyTitleModel();
				CoServicePolicyModel model=new CoServicePolicyModel();
				model.setOwnmonth(Integer.parseInt(strowm));
				model.setSypo_title(title);
				model.setSypo_agencies(agencies);
				model.setSypo_city(citymodel.getCity());
				model.setSypo_type(typename);
				model.setSypo_addname(UserInfo.getUsername());
				model.setSypo_content(cb.getValue());
				model.setSypo_cityid(citymodel.getId());
				model.setSypo_cabc_id(citymodel.getCabc_id());
				model.setSypo_item_id(Integer.parseInt(itemid));
				id=id+obll.CoServicePolicyAdd(model);
			}
		}
		if(id>0)
		{
			btn.setStyle("background:#CCCCFF;");
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			titlelist.clear();
			typename="";
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		}
		else
		{
			Messagebox.show("没有数据，不能提交", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//生成填写内容
	@Command
	public void crtxtbox(@BindingParam("model") CoServicePolicyTitleModel model,
			@BindingParam("cl") Cell cl)
	{
		if(model.getItem_inftype()!=null)
		{
			if(model.getItem_inftype()==1)
			{
				Combobox tx=new Combobox(model.getItem_content());
				tx.setParent(cl);
				tx.setWidth("90%");
				tx.setButtonVisible(false);
			}
			else if(model.getItem_inftype()==2)
			{
				Combobox cb=new Combobox(model.getItem_content());
				cb.setReadonly(true);
				List<CoServicePolicyTitleDropInfoModel> lt=getDroplist(model.getItem_id());
				for(int i=0;i<lt.size();i++)
				{
					Comboitem item=new Comboitem(lt.get(i).getDrop_content());
					item.setParent(cb);
				}
				cb.setParent(cl);
			}
		}
		else
		{
			Combobox tx=new Combobox(model.getItem_content());
			tx.setParent(cl);
			tx.setWidth("90%");
			tx.setButtonVisible(false);
		}
	}
	//根据问题id查下拉列表内容
	private List<CoServicePolicyTitleDropInfoModel> getDroplist(Integer tite_id)
	{
		List<CoServicePolicyTitleDropInfoModel> droplists=
				new ArrayList<CoServicePolicyTitleDropInfoModel>();
		for(CoServicePolicyTitleDropInfoModel m:droplist)
		{
			if(m.getDrop_tite_id()!=null&&tite_id!=null)
			{
				if(m.getDrop_tite_id().equals(tite_id))
				{
					droplists.add(m);
				}
			}
		}
		return droplists;
		
	}
	
	public List<CoServicePolicyTypeModel> getList() {
		return list;
	}
	public void setList(List<CoServicePolicyTypeModel> list) {
		this.list = list;
	}
	public List<CoServicePolicyTypeModel> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CoServicePolicyTypeModel> typelist) {
		this.typelist = typelist;
	}
	public List<CoServicePolicyTitleModel> getTitlelist() {
		return titlelist;
	}
	public void setTitlelist(List<CoServicePolicyTitleModel> titlelist) {
		this.titlelist = titlelist;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public boolean isBtnvis() {
		return btnvis;
	}
	public void setBtnvis(boolean btnvis) {
		this.btnvis = btnvis;
	}
	
	
}
