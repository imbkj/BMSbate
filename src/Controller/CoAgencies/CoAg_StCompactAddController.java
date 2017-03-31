package Controller.CoAgencies;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.zhuozhengsoft.pageoffice.zoomseal.User;

import Model.CoAgencyBaseModel;
import Model.CoAgencyCompactModel;
import Model.PubProCityModel;
import Util.UserInfo;
import bll.CoAgencies.CoAg_CompactOperateBll;
import bll.CoAgencies.CoAg_CompactSelectBll;

public class CoAg_StCompactAddController {
	private CoAgencyBaseModel coagmodel = (CoAgencyBaseModel)Executions.getCurrent().getArg().get("model");
	private CoAg_CompactSelectBll bll=new CoAg_CompactSelectBll();
	private List<CoAgencyBaseModel> coaglist=bll.getCoAgencyBaseList("");
	private List<PubProCityModel> citylist=bll.getCoPubProCityList("");
	private CoAgencyCompactModel model=new CoAgencyCompactModel();
	private Date signdate,effectdate,expiredate;
	//private CoAgencyBaseModel coagmodel=new CoAgencyBaseModel();
	private String autoextend="是";
	
	public CoAg_StCompactAddController()
	{
		model.setCoct_compactid("st"+getCompactId());
		model.setCoct_coagname(coagmodel.getCoab_name());
		model.setCoct_category("服务协议");
		model.setCoct_category("受托合同");
	}
	
	@Command
	public void stsummit(@BindingParam("win") Window win)
	{
		String str=isEmploy();
		if(str==null||str.equals(""))
		{
			CoAg_CompactOperateBll obll=new CoAg_CompactOperateBll();
			String[] message=obll.CoAg_CompactAdd(model,"2");
			if(message[0]=="1")
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				//Executions.sendRedirect("/CoAgencies/CoAg_StCompactAdd.zul");
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
	
	//检查必填项是否已经填写完整
	private String isEmploy()
	{
		String str="";
		if(model.getCoct_coagname()==null||model.getCoct_coagname().equals(""))
		{
			str="请选择受托机构";
		}
		else if(signdate==null)
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
		else
		{
			model.setCoct_signdate(DateToStr(signdate));
			model.setCoct_effectdate(DateToStr(effectdate));
			model.setCoct_expiredate(DateToStr(expiredate));
			model.setCoct_coagid(coagmodel.getCoab_id());
			model.setCoct_type("受托合同");
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
