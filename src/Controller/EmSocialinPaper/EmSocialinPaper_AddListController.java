package Controller.EmSocialinPaper;

import Model.EmbaseModel;
import Util.RegexUtil;

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

import bll.EmSocialinPaper.EmSocialinPaperListBll;

public class EmSocialinPaper_AddListController {

	// 员工列表
	private List<EmbaseModel> embaseList;
	private List<EmbaseModel> sembaseList;

	// 检索字段绑定
	private String cid = "";
	private String company = "";
	private String gid = "";
	private String name = "";

	public EmSocialinPaper_AddListController() {
		try {
			EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
			setEmbaseList(new ListModelList<EmbaseModel>(bll.getEmbaseList()));
			setSembaseList(new ListModelList<EmbaseModel>(embaseList));
		} catch (Exception e) {
			Messagebox.show("页面加载出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	// 检索
	@Command("search")
	@NotifyChange("sembaseList")
	public void search() {
		sembaseList.clear();

		for (EmbaseModel m : embaseList) {
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
			if (!gid.isEmpty()) {
				if (!RegexUtil.isExists(gid, m.getGid() + "")) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getEmba_name())) {
					continue;
				}
			}
			sembaseList.add(m);
		}
	}

	@Command("add")
	@NotifyChange("sembaseList")
	public void add(@BindingParam("daid") int daid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window window = (Window) Executions.createComponents(
				"/EmSocialinPaper/EmSocialinPaper_Add.zul", null, map);
		window.doModal();

		EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
		setEmbaseList(new ListModelList<EmbaseModel>(bll.getEmbaseList()));
		search();
	}

	public List<EmbaseModel> getEmbaseList() {
		return embaseList;
	}

	public void setEmbaseList(List<EmbaseModel> embaseList) {
		this.embaseList = embaseList;
	}

	public List<EmbaseModel> getSembaseList() {
		return sembaseList;
	}

	public void setSembaseList(List<EmbaseModel> sembaseList) {
		this.sembaseList = sembaseList;
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

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
