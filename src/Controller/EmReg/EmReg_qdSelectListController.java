package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import Model.EmRegistrationInfoModel;
import Util.RegexUtil;

public class EmReg_qdSelectListController {
	private List<EmRegistrationInfoModel> emregList;
	private List<EmRegistrationInfoModel> semregList = new ListModelList<>();
	private List<EmRegistrationInfoModel> stateList = new ListModelList<>();

	String wherestr = " and type=1 and typename='前道状态'";

	// 检索条件
	private String cid = "";
	private String gid = "";
	private String shortname = "";
	private String name = "";
	private String t_kind = "";
	private Date addtime = null;
	private String is_sh = "";
	private String ownmonth = new SimpleDateFormat("yyyyMM").format(new Date());

	public EmReg_qdSelectListController() {
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
	@NotifyChange({ "semregList" })
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
			if (addtime != null) {
				if (!new SimpleDateFormat("yyyy-MM-dd").format(addtime).equals(
						m.getErin_addtime())) {
					continue;
				}
			}
			if (!is_sh.isEmpty()) {
				if (!is_sh.equals(m.getIs_sh())) {
					continue;
				}
			}
			if (!ownmonth.isEmpty()) {
				if (!ownmonth.equals(m.getOwnmonth())) {
					continue;
				}
			}

			semregList.add(m);
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

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}
}
