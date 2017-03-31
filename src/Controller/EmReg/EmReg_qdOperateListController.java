package Controller.EmReg;

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

import bll.EmReg.EmReg_ListBll;

import Model.EmRegistrationInfoModel;
import Util.RegexUtil;

public class EmReg_qdOperateListController {
	private List<EmRegistrationInfoModel> emregList;
	private List<EmRegistrationInfoModel> semregList = new ListModelList<>();
	private List<EmRegistrationInfoModel> stateList = new ListModelList<>();

	String wherestr = " and type=1 and typename='前道状态' and state=1 and erin_stop_state=0";

	// 检索条件
	private String cid = "";
	private String gid = "";
	private String shortname = "";
	private String name = "";
	private String t_kind = "";
	private String statename = "";
	private Date addtime = null;
	private String is_sh = "";

	private final boolean mult = true;
	private boolean opsDis;

	public EmReg_qdOperateListController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setStateList(bll.getStateList(" and type=1 and typename='前道状态'"));

			setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
			search();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange({ "semregList", "opsDis" })
	public void search() {
		semregList.clear();

		for (EmRegistrationInfoModel m : emregList) {

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
			if (!t_kind.isEmpty()) {
				if (!t_kind.equals(m.getErin_t_kind())) {
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
						m.getErin_addtime())) {
					continue;
				}
			}
			if (!is_sh.isEmpty()) {
				if (!is_sh.equals(m.getIs_sh())) {
					continue;
				}
			}

			semregList.add(m);
		}
		if (statename.equals("办理完成")) {
			setOpsDis(false);
		} else {
			setOpsDis(true);
		}
	}

	// 详情
	@Command("detail")
	public void detail(@BindingParam("daid") Integer daid,
			@BindingParam("role") String role) {
		String url = "EmReg_Detail.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		map.put("role", role);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	// 材料详情
	@Command("docdetail")
	public void docdetail(@BindingParam("daid") Integer daid) {
		String url = "EmReg_DocDetail.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	// 退回
	@Command("back")
	@NotifyChange("semregList")
	public void back(@BindingParam("daid") Integer daid) {
		String url = "EmReg_Back.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
		search();
	}

	// 终止
	@Command("termination")
	@NotifyChange("semregList")
	public void termination(@BindingParam("daid") Integer daid) {
		String url = "EmReg_Termination.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
		search();
	}

	// 材料退回
	@Command("docback")
	@NotifyChange("semregList")
	public void docback(@BindingParam("daid") Integer daid) {
		String url = "EmReg_DocBack.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
		search();
	}

	// 重新提交
	@Command("resubmit")
	@NotifyChange("semregList")
	public void resubmit(@BindingParam("each") EmRegistrationInfoModel m) {
		String url = "";
		if (m.getErin_laststate() == 0 || m.getErin_laststate() == 1) {
			url = "EmReg_Mod.zul";
		} else {
			url = "EmReg_Operate.zul";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErin_id());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
		search();
	}

	// 单条状态变更
	@Command("openwin")
	@NotifyChange("semregList")
	public void openwin(@BindingParam("each") EmRegistrationInfoModel m) {
		List<EmRegistrationInfoModel> list = new ListModelList<>();
		list.add(m);
		String url = "";
		if (m.getErin_state() == 1) {
			url = "EmReg_Mod.zul";
		} else {
			url = "EmReg_Operate.zul";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
		search();
	}

	// 批量状态变更
	@Command("openwins")
	@NotifyChange("semregList")
	public void openwins(@BindingParam("set") Set<Listitem> set) {
		List<EmRegistrationInfoModel> list = new ListModelList<>();
		String url = "";
		for (Listitem it : set) {
			list.add((EmRegistrationInfoModel) it.getValue());
		}

		if (list.size() > 0) {
			// 根据所选数量判断是批量还是个人
			url = list.size() == 1 ? "EmReg_Operate.zul" : "EmReg_Operates.zul";

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			Window win = (Window) Executions.createComponents(url, null, map);
			win.doModal();

			EmReg_ListBll bll = new EmReg_ListBll();
			setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
			search();
		} else {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<EmRegistrationInfoModel> getEmregList() {
		return emregList;
	}

	public void setEmregList(List<EmRegistrationInfoModel> emregList) {
		this.emregList = emregList;
	}

	public List<EmRegistrationInfoModel> getSemregList() {
		return semregList;
	}

	public void setSemregList(List<EmRegistrationInfoModel> semregList) {
		this.semregList = semregList;
	}

	public List<EmRegistrationInfoModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<EmRegistrationInfoModel> stateList) {
		this.stateList = stateList;
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

	public String getIs_sh() {
		return is_sh;
	}

	public void setIs_sh(String is_sh) {
		this.is_sh = is_sh;
	}

	public boolean isMult() {
		return mult;
	}

	public boolean isOpsDis() {
		return opsDis;
	}

	public void setOpsDis(boolean opsDis) {
		this.opsDis = opsDis;
	}
}
