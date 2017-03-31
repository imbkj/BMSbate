package Controller.CoReg;

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

import bll.CoReg.CoReg_ListBll;

import Model.CoOnlineRegisterInfoModel;
import Util.RegexUtil;

public class CoReg_qdOperateListController {

	private List<CoOnlineRegisterInfoModel> coregList;
	private List<CoOnlineRegisterInfoModel> scoregList = new ListModelList<>();
	private List<String> ownmonthList = new ListModelList<>();
	private List<CoOnlineRegisterInfoModel> stateList = new ListModelList<>();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String ownmonth = new SimpleDateFormat("yyyyMM").format(new Date());
	private String reg_type = "";
	private String reg_tran_type = "";
	private String statename = "";
	private String addname = "";
	private Date addtime;

	public CoReg_qdOperateListController() {
		try {
			CoReg_ListBll bll = new CoReg_ListBll();
			setCoregList(new ListModelList<>(bll.getCoRegOpList()));
			setOwnmonthList(bll.getownmonthList());
			setStateList(bll.getstateList(" and typename='前道状态'"));

			search();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("search")
	@NotifyChange("scoregList")
	public void search() {
		scoregList.clear();

		for (CoOnlineRegisterInfoModel m : coregList) {
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
			if (!ownmonth.isEmpty()) {
				if (!RegexUtil.isExists(ownmonth, m.getOwnmonth())) {
					continue;
				}
			}
			if (!reg_type.isEmpty()) {
				if (!RegexUtil.isExists(reg_type, m.getCori_reg_type())) {
					continue;
				}
			}
			if (!reg_tran_type.isEmpty()) {
				if (!RegexUtil.isExists(reg_tran_type,
						m.getCori_reg_transact_type())) {
					continue;
				}
			}
			if (!addname.isEmpty()) {
				if (!RegexUtil.isExists(addname, m.getCori_addname())) {
					continue;
				}
			}
			if (addtime != null) {
				if (!RegexUtil.isExists(sdf.format(addtime),
						m.getCori_addtime())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!RegexUtil.isExists(statename, m.getStatename())) {
					continue;
				}
			}

			scoregList.add(m);
		}
	}

	// 状态变更
	@Command("openwin")
	@NotifyChange("scoregList")
	public void openwin(@BindingParam("url") String url,
			@BindingParam("daid") Integer daid, @BindingParam("id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		map.put("id", id);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		CoReg_ListBll bll = new CoReg_ListBll();
		setCoregList(new ListModelList<>(bll.getCoRegOpList()));
		search();
	}

	public List<CoOnlineRegisterInfoModel> getCoregList() {
		return coregList;
	}

	public void setCoregList(List<CoOnlineRegisterInfoModel> coregList) {
		this.coregList = coregList;
	}

	public List<CoOnlineRegisterInfoModel> getScoregList() {
		return scoregList;
	}

	public void setScoregList(List<CoOnlineRegisterInfoModel> scoregList) {
		this.scoregList = scoregList;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<String> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public List<CoOnlineRegisterInfoModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<CoOnlineRegisterInfoModel> stateList) {
		this.stateList = stateList;
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

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getReg_type() {
		return reg_type;
	}

	public void setReg_type(String reg_type) {
		this.reg_type = reg_type;
	}

	public String getReg_tran_type() {
		return reg_tran_type;
	}

	public void setReg_tran_type(String reg_tran_type) {
		this.reg_tran_type = reg_tran_type;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}
