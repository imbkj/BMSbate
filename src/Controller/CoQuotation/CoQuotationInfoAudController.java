package Controller.CoQuotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.CoLatencyClientModel;
import Model.CoOfferListModel;
import bll.CoQuotation.CoQuotationInfoBll;
import bll.CoQuotation.CoQuotation_List1Bll;

public class CoQuotationInfoAudController {
	private String companyName = "";
	private String addname = "";
	private String level = "";
	String str = "";
	List<CoLatencyClientModel> listmodel = new ListModelList<CoLatencyClientModel>();
	private List<String> addnamelist = new ListModelList<String>();

	@Init
	public void init() throws Exception {
		CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
		str="and coof_cola_id=33";
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

	@Command("quotationadd")
	@NotifyChange("listmodel")
	public void quotationadd(@BindingParam("model") CoLatencyClientModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("colaid", model.getCola_id());
		map.put("colacompany", model.getCola_company());
		Window window = (Window) Executions.createComponents(
				"Coquotation_Add.zul", null, map);
		window.doModal();
		searchmenu();
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
