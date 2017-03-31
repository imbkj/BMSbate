package Controller.CoQuotation;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import Model.CoLatencyClientModel;
import bll.CoQuotation.CoQuotation_List1Bll;

public class CoQuotation_List1Controller{

	private String companyName = "";
	private String addname = "";
	private String level = "";
	String str="";
	List<CoLatencyClientModel> listmodel = new ListModelList<CoLatencyClientModel>();
	private List<String> addnamelist = new ListModelList<String>(); 

	@Init
	public void init() throws Exception {
		CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
		setListmodel(bll.getCoLatencyClientAllInfo(str));
		setAddnamelist(bll.getAddnameList());
	}
	
	@Command("searchmenu")
	@NotifyChange("listmodel")
	public void searchmenu() {
		CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
		String levelstr = "";
		str = "";
		if (!companyName.isEmpty()) {
			str += " and cola_company like '%" + companyName + "%'";
		}
		if (!addname.isEmpty()) {
			str += " and cola_addname='" + addname + "'";
		}
		if (!level.isEmpty()) {
			if (level.equals("较低")) {
				levelstr = "(1,2)";
			} else if (level.equals("一般")) {
				levelstr = "(3)";
			} else {
				levelstr = "(4,5)";
			}
			str += " and cola_successlevel in " + levelstr;
		}
		setListmodel(bll.getCoLatencyClientAllInfo(str));
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<CoLatencyClientModel> getListmodel() {
		return listmodel;
	}

	public void setListmodel(List<CoLatencyClientModel> listmodel) {
		this.listmodel = listmodel;
	}

	public List<String> getAddnamelist() {
		return addnamelist;
	}

	public void setAddnamelist(List<String> addnamelist) {
		this.addnamelist = addnamelist;
	}
}
