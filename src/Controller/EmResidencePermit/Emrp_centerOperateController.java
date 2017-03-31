package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmResidencePermitInfoModel;
import Util.RegexUtil;
import bll.EmResidencePermit.Emrp_ListBll;

public class Emrp_centerOperateController {

	private List<EmResidencePermitInfoModel> erpList;
	private List<EmResidencePermitInfoModel> serpList = new ListModelList<>();
	private List<EmResidencePermitInfoModel> stateList;

	String wherestr = " and type=1 and typename='前道状态' and state=1 order by erpi_id desc";

	// 检索条件
	private String cid = "";
	private String gid = "";
	private String shortname = "";
	private String name = "";
	private String t_kind = "";
	private String statename = "";
	private Date addtime = null;

	private final boolean mult = true;
	private boolean opsDis;

	public Emrp_centerOperateController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();
			wherestr=" and statename='待提交'";
			setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
			search();
			setStateList(new ListModelList<>(
					bll.getStateList(" and typename='前道状态'")));
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange({ "serpList", "opsDis" })
	public void search() {
		serpList.clear();

		for (EmResidencePermitInfoModel m : erpList) {

			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!gid.isEmpty()) {
				if (!RegexUtil.isExists(gid, m.getGid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getEmba_name())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!RegexUtil.isExists(statename, m.getStatename())) {
					continue;
				}
			}
			if (addtime != null) {
				if (!RegexUtil.isExists(
						new SimpleDateFormat("yyyy-MM-dd").format(addtime),
						m.getErpi_addtime())) {
					continue;
				}
			}
			if (!t_kind.isEmpty()) {
				if (!t_kind.equals(m.getErpi_t_kind())) {
					continue;
				}
			}
			serpList.add(m);
		}
		if (statename.equals("后道已取证")) {
			setOpsDis(false);
		} else {
			setOpsDis(true);
		}
	}

	// 状态变更
	@Command("operate")
	@NotifyChange("serpList")
	public void operate(@BindingParam("each") EmResidencePermitInfoModel m) {
		String url = "";
		if (m.getErpi_state() == 1) {
			url = "Emrp_Mod.zul";
		} else {
			url = "Emrp_Operate.zul";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	// 详情
	@Command("detail")
	@NotifyChange("serpList")
	public void detail(@BindingParam("each") EmResidencePermitInfoModel m,
			@BindingParam("role") String role) {
		String url = "Emrp_Detail.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		map.put("role", role);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	// 材料详情
	@Command("docdetail")
	@NotifyChange("serpList")
	public void docdetail(@BindingParam("each") EmResidencePermitInfoModel m) {
		String url = "Emrp_DocDetail.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		map.put("gid", m.getGid());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	public List<EmResidencePermitInfoModel> getErpList() {
		return erpList;
	}

	public void setErpList(List<EmResidencePermitInfoModel> erpList) {
		this.erpList = erpList;
	}

	public List<EmResidencePermitInfoModel> getSerpList() {
		return serpList;
	}

	public void setSerpList(List<EmResidencePermitInfoModel> serpList) {
		this.serpList = serpList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getT_kind() {
		return t_kind;
	}

	public void setT_kind(String t_kind) {
		this.t_kind = t_kind;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public boolean isOpsDis() {
		return opsDis;
	}

	public void setOpsDis(boolean opsDis) {
		this.opsDis = opsDis;
	}

	public boolean isMult() {
		return mult;
	}

	public List<EmResidencePermitInfoModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<EmResidencePermitInfoModel> stateList) {
		this.stateList = stateList;
	}
}
