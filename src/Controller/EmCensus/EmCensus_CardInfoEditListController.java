package Controller.EmCensus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmHJBorrowCardModel;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensus_CardInfoEditListController {
	private String cid="";
	private String hjno="";
	private String company="";
	private String inhjtype="";
	private String emhj_type="";
	private String embaclass="员工姓名";
	private String embainfo="";
	private EmCensus_SelectBll bll=new EmCensus_SelectBll();
	private String str="";
	private List<EmHJBorrowCardModel> borrowlist=bll.getEmHJBorrowCardInfo(str);
	
	
	//查询
	@Command
	@NotifyChange("borrowlist")
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
					str=str+" and emhj_name='"+embainfo+"'";
				}
				else if(embaclass=="员工编号"||embaclass.equals("员工编号"))
				{
					str=str+" and b.gid="+embainfo;
				}
				else if(embaclass=="身份证号"||embaclass.equals("身份证号"))
				{
					str=str+" and emhj_idcard='"+embainfo+"'";
				}
			}
		}
		if(company!=null&&!company.equals("")&&company!="")
		{
			str=str+" and (c.coba_company like '%"+company+"%' or coba_shortname like '%"+company+"%')";
		}
		if(cid!=null&&!cid.equals("")&&cid!="")
		{
			str=str+" and c.cid="+cid;
		}
		if(emstate!=null&&!emstate.equals("")&&emstate!=""&&emstate!="-1"&&!emstate.equals("-1"))
		{
			str=str+" and ehbc_state="+emstate;
		}
		if(hjno!=null&&!hjno.equals("")&&hjno!="")
		{
			str=str+" and emhj_no='"+hjno+"'";
		}
		if(emhj_type!=null&&!emhj_type.equals("")&&emhj_type!="")
		{
			str=str+" and emhj_type='"+emhj_type+"'";
		}
		borrowlist=bll.getEmHJBorrowCardInfo(str);
	}
	
	@Command
	@NotifyChange("borrowlist")
	public void openZUL(@BindingParam("model") EmHJBorrowCardModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("daid", model.getEhbc_id()+"");
		map.put("id", model.getEhbc_tarpid()+"");
		Window window=(Window)Executions.createComponents(url, null, map);
		window.doModal();
		borrowlist=bll.getEmHJBorrowCardInfo(str);
	}
	
	@Command
	@NotifyChange("borrowlist")
	public void openCashAdd(@BindingParam("model") EmHJBorrowCardModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("EmCensus_CashPledgeAdd.zul", null, map);
		window.doModal();
		borrowlist=bll.getEmHJBorrowCardInfo(str);
	}
	
	public List<EmHJBorrowCardModel> getBorrowlist() {
		return borrowlist;
	}
	public void setBorrowlist(List<EmHJBorrowCardModel> borrowlist) {
		this.borrowlist = borrowlist;
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
	public String getEmbainfo() {
		return embainfo;
	}
	public void setEmbainfo(String embainfo) {
		this.embainfo = embainfo;
	}
	public String getEmbaclass() {
		return embaclass;
	}
	public void setEmbaclass(String embaclass) {
		this.embaclass = embaclass;
	}

	public String getEmhj_type() {
		return emhj_type;
	}

	public void setEmhj_type(String emhj_type) {
		this.emhj_type = emhj_type;
	}
	
}
