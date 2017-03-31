package Controller.EmCensus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmCensusModel;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensus_EditListController {
	private String cid="";
	private String hjno="";
	private String company="";
	private String inhjtype="";
	private String hjaddress="";
	private String embaclass="员工姓名";
	private String embainfo="";
	private EmCensus_SelectBll bll=new EmCensus_SelectBll();
	private String str=" and emhj_state<>8";
	private List<EmCensusModel> emcensus=bll.getEmCensusInfo(str);
	
	//查询
	@Command
	@NotifyChange("emcensus")
	public void search(@BindingParam("emstate") String emstate)
	{
		str="";
		//员工信息
		if(embaclass!=null&&!embaclass.equals("")&&embaclass!="")
		{
			if(embainfo!=null&&!embainfo.equals("")&&embainfo!="")
			{
				if(embaclass=="员工姓名"||embaclass.equals("员工姓名"))
				{
					str=str+"and (emhj_name='"+embainfo+"' or emhj_id in(select emhj_pid from " +
							"EmCensus where emhj_name='"+embainfo+"') or emhj_Pid in(select emhj_id " +
									"from EmCensus where emhj_name='"+embainfo+"')" +
							" or emhj_pid in(select emhj_pid from EmCensus where emhj_name='"+embainfo+"'))";
				}
				else if(embaclass=="员工编号"||embaclass.equals("员工编号"))
				{
					str=str+" and a.gid="+embainfo;
				}
				else if(embaclass=="身份证号"||embaclass.equals("身份证号"))
				{
					str=str+" and (emhj_idcard='"+embainfo+"'" +
							"or emhj_id in(select emhj_pid from EmCensus where " +
							"emhj_idcard='"+embainfo+"') or emhj_Pid in(select emhj_id from " +
							"EmCensus where emhj_idcard='"+embainfo+"')" +
							" or emhj_pid in(select emhj_pid from EmCensus where emhj_idcard='"+embainfo+"'))";
				}
			}
		}
		if(company!=null&&!company.equals("")&&company!="")
		{
			str=str+" and (b.coba_company like '%"+company+"%' or coba_shortname like '%"+company+"%')";
		}
		if(cid!=null&&!cid.equals("")&&cid!="")
		{
			str=str+" and a.cid="+cid;
		}
		if(emstate!=null&&!emstate.equals("")&&emstate!=""&&emstate!="0"&&!emstate.equals("0"))
		{
			str=str+" and emhj_state="+emstate;
		}
		if(hjno!=null&&!hjno.equals("")&&hjno!="")
		{
			str=str+" and (emhj_no='"+hjno+"'  or emhj_id in(select emhj_pid from EmCensus " +
					" where emhj_no='"+hjno+"') or emhj_Pid in(select emhj_id from EmCensus " +
					" where emhj_no='"+hjno+"')" +
				" or emhj_pid in(select emhj_pid from EmCensus where emhj_no='"+hjno+"'))";
		}
		if(hjaddress!=null&&!hjaddress.equals("")&&hjaddress!="")
		{
			str=str+" and emhj_place='"+hjaddress+"'";
		}
		if(hjaddress!=null&&!hjaddress.equals("")&&hjaddress!="")
		{
			str=str+" and emhj_place='"+hjaddress+"'";
		}
		if(inhjtype!=null&&!inhjtype.equals("")&&inhjtype!="")
		{
			str=str+" and emhj_in_class='"+inhjtype+"'";
		}
		emcensus=bll.getEmCensusInfo(str+" and emhj_state<>8");
	}
	
	//弹出页面
	@Command
	@NotifyChange("emcensus")
	public void openZUL(@BindingParam("model") EmCensusModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("daid", model.getEmhj_id()+"");
		map.put("model", model);
		String tarpid="";
		if(model.getEmhj_taprid()!=null)
		{
			tarpid=model.getEmhj_taprid()+"";
		}
		map.put("id", tarpid);
		map.put("gid", model.getGid());
		Window window=(Window)Executions.createComponents(url, null, map);
		window.doModal();
		emcensus=bll.getEmCensusInfo(str);
	}
	public List<EmCensusModel> getEmcensus() {
		return emcensus;
	}
	public void setEmcensus(List<EmCensusModel> emcensus) {
		this.emcensus = emcensus;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getHjno() {
		return hjno;
	}
	public void setHjno(String hjno) {
		this.hjno = hjno;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getInhjtype() {
		return inhjtype;
	}
	public void setInhjtype(String inhjtype) {
		this.inhjtype = inhjtype;
	}
	public String getEmbaclass() {
		return embaclass;
	}
	public void setEmbaclass(String embaclass) {
		this.embaclass = embaclass;
	}
	public String getEmbainfo() {
		return embainfo;
	}
	public void setEmbainfo(String embainfo) {
		this.embainfo = embainfo;
	}
	public String getHjaddress() {
		return hjaddress;
	}
	public void setHjaddress(String hjaddress) {
		this.hjaddress = hjaddress;
	}
	
}
