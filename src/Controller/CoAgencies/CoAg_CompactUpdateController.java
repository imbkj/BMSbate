package Controller.CoAgencies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.CoAgencies.CoAg_CompactSelectBll;

import Model.CoAgencyBaseModel;
import Model.CoAgencyCompactModel;
import Model.PubProCityModel;

public class CoAg_CompactUpdateController {
	private CoAgencyCompactModel model = (CoAgencyCompactModel)Executions.getCurrent().getArg().get("model");
	private CoAg_CompactSelectBll bll=new CoAg_CompactSelectBll();
	private List<CoAgencyBaseModel> coaglist=bll.getCoAgencyBaseList("");
	private Date signdate,effectdate,expiredate;
	private List<PubProCityModel> citylist=bll.getCoPubProCityList(" and cabc_coab_id="+model.getCoct_coagid());
	
	public CoAg_CompactUpdateController()
	{
		signdate=StringToDate(model.getCoct_signdate());
		effectdate=StringToDate(model.getCoct_effectdate());
		expiredate=StringToDate(model.getCoct_expiredate());
	}
	
	public CoAgencyCompactModel getModel() {
		return model;
	}

	public void setModel(CoAgencyCompactModel model) {
		this.model = model;
	}

	public List<CoAgencyBaseModel> getCoaglist() {
		return coaglist;
	}

	public void setCoaglist(List<CoAgencyBaseModel> coaglist) {
		this.coaglist = coaglist;
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

	public List<PubProCityModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<PubProCityModel> citylist) {
		this.citylist = citylist;
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
	
	/**
	* 字符串转换成日期
	* @param date 
	* @return str
	*/
	public static Date StringToDate(String dateStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			if(dateStr!=null)
			{
				date = format.parse(dateStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
