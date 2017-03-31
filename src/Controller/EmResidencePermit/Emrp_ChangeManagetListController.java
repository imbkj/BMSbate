package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;
import bll.EmResidencePermit.Emrp_ChangeSelectBll;
import bll.EmResidencePermit.Emrp_ListBll;

public class Emrp_ChangeManagetListController {
	private Emrp_ChangeSelectBll bll = new Emrp_ChangeSelectBll();
	private List<EmResidencePermitChangeModel> list = bll.getChangeList("");
	private String shortname, name, idcard, client,statename;
	private Date sqdate, czdate;
	private String str="";

	@Command
	@NotifyChange("list")
	public void open(@BindingParam("model") EmResidencePermitChangeModel model) {
		Map map = new HashMap<>();
		map.put("id", model.getErpc_tapr_id());
		map.put("daid", model.getErpc_id());
		Window window = (Window) Executions.createComponents(
				"Emrp_ChangeOperate.zul", null, map);
		window.doModal();
		list = bll.getChangeList(str);
	}

	// 打开详细页面
	@Command
	public void detail(@BindingParam("model") EmResidencePermitChangeModel model) {
		EmResidencePermitInfoModel mm = new EmResidencePermitInfoModel();
		Emrp_ListBll bll = new Emrp_ListBll();
		mm = bll.getEmResidencePermitInfo(model.getGid());
		Map map = new HashMap<>();
		map.put("role", "hd");
		map.put("daid", mm.getErpi_id());
		Window window = (Window) Executions.createComponents("Emrp_Detail.zul",
				null, map);
		window.doModal();
	}

	// 查询
	@Command
	@NotifyChange("list")
	public void search() {
		String sql = "";
		str="";
		if (shortname != null && !shortname.equals("")) {
			sql=sql+" and coba_shortname='"+shortname+"'";
		}
		if (name != null && !name.equals("")) {
			sql=sql+" and emba_name='"+name+"'";
		}
		if (idcard != null && !idcard.equals("")) {
			sql=sql+" and emba_idcard='"+idcard+"'";
		}
		if (client != null && !client.equals("")) {
			sql=sql+" and coba_client='"+client+"'";
		}
		if (sqdate != null) {
			sql=sql+" and convert(varchar(10),erpc_csd_ac_date,120)='"+DateToStr(sqdate)+"'";
		}
		if (czdate != null) {
			sql=sql+" and convert(varchar(10),erpc_wd_oc_date,120)='"+DateToStr(czdate)+"'";
		}
		if(statename!=null&&!statename.equals(""))
		{
			if(statename.equals("待转换"))
			{
				sql=sql+" and erpc_state=1";
			}
			else if(statename.equals("转换中"))
			{
				sql=sql+" and erpc_state=2";
			}
			else if(statename.equals("已转换"))
			{
				sql=sql+" and erpc_state=3";
			}
			else if(statename.equals("退回"))
			{
				sql=sql+" and erpc_state=4";
			}
		}
		str=sql;
		list = bll.getChangeList(sql);
	}

	public List<EmResidencePermitChangeModel> getList() {
		return list;
	}

	public void setList(List<EmResidencePermitChangeModel> list) {
		this.list = list;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Date getSqdate() {
		return sqdate;
	}

	public void setSqdate(Date sqdate) {
		this.sqdate = sqdate;
	}

	public Date getCzdate() {
		return czdate;
	}

	public void setCzdate(Date czdate) {
		this.czdate = czdate;
	}
	
	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String str = format.format(date);
	   return str;
	} 
}
