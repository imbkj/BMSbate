package Controller.CoReg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_ListBll;

import Model.CoBaseModel;
import Util.RegexUtil;

public class CoReg_CobaseListController {

	private List<CoBaseModel> cobaList;
	private List<CoBaseModel> scobaList = new ListModelList<>();
	private String cid = "";
	private String company = "";

	public CoReg_CobaseListController() {
		try {
			CoReg_ListBll bll = new CoReg_ListBll();
			setCobaList(new ListModelList<>(bll.getCobaList()));
			search();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("openwin")
	public void openwin(@BindingParam("url") String url,
			@BindingParam("cid") Integer cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		CoReg_ListBll bll = new CoReg_ListBll();
		setCobaList(new ListModelList<>(bll.getCobaList()));
		search();
	}

	@Command("search")
	@NotifyChange("scobaList")
	public void search() {
		scobaList.clear();

		for (CoBaseModel m : cobaList) {
			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!company.isEmpty()) {
				if (!RegexUtil.isExists(company, m.getCoba_company())) {
					continue;
				}
			}
			scobaList.add(m);
		}
	}

	public List<CoBaseModel> getCobaList() {
		return cobaList;
	}

	public void setCobaList(List<CoBaseModel> cobaList) {
		this.cobaList = cobaList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<CoBaseModel> getScobaList() {
		return scobaList;
	}

	public void setScobaList(List<CoBaseModel> scobaList) {
		this.scobaList = scobaList;
	}
}
