package Controller.CoHousingFund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.CoHousingFundModel;
import bll.CoHousingFund.CoHousingFund_ListBll;

public class CoHouseInfoController {
	private List<CoHousingFundModel> hlist = new ListModelList<>();
	private List<Integer> tslist = new ListModelList<>();
	private List<String> cplist = new ListModelList<>();

	private CoHousingFund_ListBll bll = new CoHousingFund_ListBll();

	private String houseid, cid, company, tsday, cpp, single;

	public CoHouseInfoController() {
		hlist = bll.getHouseList(houseid, cid, company, tsday, cpp, single);
		tslist.add(null);
		cplist.add(null);
		for (int i = 1; i < 32; i++) {
			tslist.add(i);
		}
		for (Double i = 0.05; i < 0.21; i = i + 0.01) {
			cplist.add(String.format("%.2f", i));
		}
	}

	@Command
	@NotifyChange("hlist")
	public void search() {
		hlist = bll.getHouseList(houseid, cid, company, tsday, cpp, single);
	}

	@Command
	public void mod(@BindingParam("a") CoHousingFundModel m) {
		Map map = new HashMap();
		map.put("id", m.getCohf_id());
	
		Window window = (Window) Executions.createComponents(
				"CoHouseInfoMod.zul", null, map);
		window.doModal();
	}

	public List<CoHousingFundModel> getHlist() {
		return hlist;
	}

	public void setHlist(List<CoHousingFundModel> hlist) {
		this.hlist = hlist;
	}

	public List<Integer> getTslist() {
		return tslist;
	}

	public void setTslist(List<Integer> tslist) {
		this.tslist = tslist;
	}

	public List<String> getCplist() {
		return cplist;
	}

	public void setCplist(List<String> cplist) {
		this.cplist = cplist;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTsday() {
		return tsday;
	}

	public void setTsday(String tsday) {
		this.tsday = tsday;
	}

	public String getCpp() {
		return cpp;
	}

	public void setCpp(String cpp) {
		this.cpp = cpp;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
