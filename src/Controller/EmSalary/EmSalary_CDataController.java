package Controller.EmSalary;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import Model.EmSalaryDataModel;
import bll.EmSalary.EmSalaryInfoListBll;

public class EmSalary_CDataController {
	private List<EmSalaryDataModel> emsdate = new ArrayList<EmSalaryDataModel>();
	private EmSalaryInfoListBll emsbll = new EmSalaryInfoListBll();
	private List ownmonthlist;
	private String cid;
	
	@Command("search")
	@NotifyChange("emsdate")
	public void search(@BindingParam("cid") String cid,@BindingParam("startownmonth") String startownmonth,@BindingParam("endownmonth") String endownmonth) throws Exception {
		//清除grid
		emsdate=emsbll.getSalaryDataByCidMonth(Integer.parseInt(cid),Integer.parseInt(startownmonth),Integer.parseInt(endownmonth));
	
	}
	
	@Command("setownmonthlist")
	@NotifyChange("ownmonthlist")
	public void setownmonthlist() throws Exception {
		//清除grid
		String str;
		str="and cid="+cid;
		ownmonthlist = emsbll.getownmonth(str);
	}

	public List<EmSalaryDataModel> getEmsdate() {
		return emsdate;
	}

	public void setEmsdate(List<EmSalaryDataModel> emsdate) {
		this.emsdate = emsdate;
	}

	public List getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
}
