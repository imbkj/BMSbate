package Controller.CoAgencies;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoAgencies.CoAg_CompactOperateBll;
import bll.CoAgencies.CoAg_CompactSelectBll;

import Model.CoAgencyBaseModel;
import Model.CoAgencyCompactModel;
import Model.PubProCityModel;
import Util.UserInfo;

public class CoAg_WtCompactAddController {
	private CoAgencyBaseModel coagmodel = (CoAgencyBaseModel)Executions.getCurrent().getArg().get("model");
	private CoAg_CompactSelectBll bll=new CoAg_CompactSelectBll();
	private List<CoAgencyBaseModel> coaglist=bll.getCoAgencyBaseList("");
	private List<PubProCityModel> citylist=new ArrayList<PubProCityModel>();
	private List<PubProCityModel> selectedcitylist=new ArrayList<PubProCityModel>();
	private CoAgencyCompactModel model=new CoAgencyCompactModel();
	private Date signdate,effectdate,expiredate;
	//private CoAgencyBaseModel coagmodel=new CoAgencyBaseModel();
	private String autoextend="是";
	
	public CoAg_WtCompactAddController()
	{
		model.setCoct_compactid("wt"+getCompactId());
		citylist=bll.getCityList(" and cabc_coab_id="+coagmodel.getCoab_id());
		model.setCoct_coagname(coagmodel.getCoab_name());
		model.setCoct_category("服务协议");
		model.setCoct_category("委托合同");
	}
	
	@Command
	public void summit(@BindingParam("gd") Grid gd,@BindingParam("win") Window win)
	{
		String str=isEmploy();
		if(str==null||str.equals(""))
		{
			CoAg_CompactOperateBll obll=new CoAg_CompactOperateBll();
			String[] message=obll.CoAg_CompactAdd(model,"1");
			if(message[0]=="1")
			{
				String id=message[3];
				CoAgencyCompactCityAdd(Integer.parseInt(id));
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				//Executions.sendRedirect("/CoAgencies/CoAg_WtCompactAdd.zul");
				win.detach();
			}
			else
			{
				Messagebox.show(message[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//新增合同和机构城市关系信息
	private Integer CoAgencyCompactCityAdd(Integer coct_id)
	{
		CoAg_CompactOperateBll obll=new CoAg_CompactOperateBll();
		Integer k=0;
		String username=UserInfo.getUsername();
		for(int i=0;i<selectedcitylist.size();i++)
		{
			PubProCityModel pml=selectedcitylist.get(i);
			k=k+obll.CoAgencyCompactCityAdd(coct_id, pml.getCabc_id(), username);
		}
		return k;
	}
	
	//检查必填项是否已经填写完整
	private String isEmploy()
	{
		String str="";
		if(signdate==null)
		{
			str="请选择签合同时间";
		}
		else if(effectdate==null)
		{
			str="请选择合同生效时间";
		}
		else if(expiredate==null)
		{
			str="请选择合同到期时间";
		}
		else if(model.getCoct_coagname()==null||model.getCoct_coagname().equals(""))
		{
			str="请选择委托机构";
		}
		else if(selectedcitylist.size()<=0)
		{
			str="请选择服务城市";
		}
		else
		{
			model.setCoct_signdate(DateToStr(signdate));
			model.setCoct_effectdate(DateToStr(effectdate));
			model.setCoct_expiredate(DateToStr(expiredate));
			model.setCoct_coagid(coagmodel.getCoab_id());
			model.setCoct_type("委托合同");
			model.setCoct_addname(UserInfo.getUsername());
			if(autoextend!=null)
			{
				if(autoextend.equals("是"))
				{
					model.setCoct_autoextend(1);
				}
				else
				{
					model.setCoct_autoextend(0);
				}
			}
		}
		return str;
	}
	
	//机构的选择事件
	@Command
	@NotifyChange("citylist")
	public void changecity(@BindingParam("cb") Combobox cb)
	{
		if(cb.getValue()!=null&&!cb.getValue().equals(""))
		{
			coagmodel=cb.getSelectedItem().getValue();
			citylist=bll.getCityList(" and cabc_coab_id="+coagmodel.getCoab_id());
		}
	}
	
	//全选
	@Command
	public void checkall(@BindingParam("cb") Checkbox cb,@BindingParam("gd") Grid gd)
	{
		if(!selectedcitylist.isEmpty())
		{
			selectedcitylist.clear();
		}
		for(int i=0;i<citylist.size();i++)
		{
			if(gd.getCell(i, 2)!=null)
			{
				Checkbox ck=(Checkbox) gd.getCell(i, 2).getChildren().get(0);
				ck.setChecked(cb.isChecked());
				PubProCityModel ml=ck.getValue();
				if(ck.isChecked())
				{
					selectedcitylist.add(ml);
				}
			}
		}
	}
	
	//单选
	@Command
	public void checkck(@BindingParam("cb") Checkbox cb,@BindingParam("gd") Grid gd)
	{
		if(!selectedcitylist.isEmpty())
		{
			selectedcitylist.clear();
		}
		for(int i=0;i<citylist.size();i++)
		{
			if(gd.getCell(i, 2)!=null)
			{
				Checkbox ck=(Checkbox) gd.getCell(i, 2).getChildren().get(0);
				if(ck.isChecked())
				{
					PubProCityModel ml=ck.getValue();
					selectedcitylist.add(ml);
				}
			}
		}
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String str ="";
	   if(date!=null)
	   {
		   str = format.format(date);
	   }
	   return str;
	}
	//根据时间生成合同编号
	private String getCompactId()
	{
		String dateString="";
		Date now = new Date(); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		dateString = formatter.format(now);
		return dateString;
	}
	public List<CoAgencyBaseModel> getCoaglist() {
		return coaglist;
	}
	public void setCoaglist(List<CoAgencyBaseModel> coaglist) {
		this.coaglist = coaglist;
	}
	public List<PubProCityModel> getCitylist() {
		return citylist;
	}
	public void setCitylist(List<PubProCityModel> citylist) {
		this.citylist = citylist;
	}

	public CoAgencyCompactModel getModel() {
		return model;
	}

	public void setModel(CoAgencyCompactModel model) {
		this.model = model;
	}

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public Date getEffectdate() {
		return effectdate;
	}

	public void setEffectdate(Date effectdate) {
		this.effectdate = effectdate;
	}

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public String getAutoextend() {
		return autoextend;
	}

	public void setAutoextend(String autoextend) {
		this.autoextend = autoextend;
	}
	
}
