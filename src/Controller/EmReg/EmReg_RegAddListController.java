package Controller.EmReg;

import Model.CoOnlineRegisterInfoModel;
import Model.EmRegistrationInfoModel;
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

import bll.EmReg.EmReg_ListBll;

public class EmReg_RegAddListController {

	private List<CoOnlineRegisterInfoModel> emList;
	private List<CoOnlineRegisterInfoModel> semList = new ListModelList<>();

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String gid = "";
	private String name = "";

	public EmReg_RegAddListController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();
			setEmList(bll.getEmbaseList());
			search();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	

	@Command("search")
	@NotifyChange("semList")
	public void search() {
		semList.clear();

		for (CoOnlineRegisterInfoModel m : emList) {

			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
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
			semList.add(m);
		}
	}

	// 弹出新办窗口
	@Command("add")
	@NotifyChange("semList")
	public void add(@BindingParam("gid") Integer gid,
			@BindingParam("type") String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gid", gid);
		map.put("type", type);
		Window window = (Window) Executions.createComponents("EmReg_Add.zul",
				null, map);
		window.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmList(bll.getEmbaseList());
		search();
	}

	public List<CoOnlineRegisterInfoModel> getEmList() {
		return emList;
	}

	public void setEmList(List<CoOnlineRegisterInfoModel> emList) {
		this.emList = emList;
	}

	public List<CoOnlineRegisterInfoModel> getSemList() {
		return semList;
	}

	public void setSemList(List<CoOnlineRegisterInfoModel> semList) {
		this.semList = semList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
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
