package Controller.CoCompact.CoQuotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.CoQuotation.CoQuotation_List1Bll;

import Model.CoLatencyClientModel;

public class CoQuotation_ListkfController {

	private String companyName = "";
	private String addname = "";
	private String level = "";
	String str = " and a.coba_LTS=1 ";
	List<CoLatencyClientModel> listmodel = new ListModelList<CoLatencyClientModel>();
	private List<String> addnamelist = new ListModelList<String>();
	private CoQuotation_List1Bll bll = new CoQuotation_List1Bll();

	private Window winL;
	
	@Init
	public void init() throws Exception {

		setListmodel(bll.getCoLatencyClientAllInfo(str," top 100 "));
		setAddnamelist(bll.getAddnameList());
	}
	
	@Command
	public void gridPage(@BindingParam("a") Window w) {
		winL = w;
	}

	@Command("searchmenu")
	@NotifyChange("listmodel")
	public void searchmenu() {
		CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
		String levelstr = "";
		str = " and a.coba_LTS=1 ";
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
		setListmodel(bll.getCoLatencyClientAllInfo(str,""));

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

	// 弹出修改潜在客户事件
	@Command
	@NotifyChange("listmodel")
	public void updateColaClient(
			@BindingParam("clientpdate") final CoLatencyClientModel model) {
		Map colaMap = new HashMap();
		colaMap.put("cola", model);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/CoLatencyClientUpdate.zul", null, colaMap);
		window.doModal();
		//listmodel = bll.getCoLatencyClientAllInfo(" and cola_id="+model.getCola_id());
		Grid gd = (Grid) winL.getFellow("gd");
		Integer acPage = gd.getActivePage();
		searchmenu();
		gd.setActivePage(acPage);
	}

	// 弹出联系人管理页面
	@Command
	public void openlink(@BindingParam("model") final CoLatencyClientModel model) {
		Map colaMap = new HashMap();
		colaMap.put("model", model);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/Cola_LinkManInfo.zul", null, colaMap);
		window.doModal();
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
