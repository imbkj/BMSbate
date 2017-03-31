package Controller.EmSocialinPaper;

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

import bll.EmSocialinPaper.EmSocialinPaperListBll;

import Model.EmSocialinPaperModel;
import Util.RegexUtil;

public class EmSocialinPaper_qdOperateListController {

	private List<EmSocialinPaperModel> espList;
	private List<EmSocialinPaperModel> sespList = new ListModelList<>();
	private List<EmSocialinPaperModel> stateList;
	private boolean mult = true;
	private boolean batchDis = false;

	// 检索字段
	private String company = "";
	private String gid = "";
	private String name = "";
	private String idcard = "";
	private String computerid = "";
	private String type = "";
	private String statename = "资料收齐";

	public EmSocialinPaper_qdOperateListController() {
		try {
			EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
			setEspList(new ListModelList<>(bll.getqdEmSocailinPaperList()));
			setStateList(new ListModelList<>(
					bll.getEmSocialinPaperState(" and typename='前道状态'")));
			stateList.add(0, new EmSocialinPaperModel());
			search();
		} catch (Exception e) {
			Messagebox.show("页面加载出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	// 检索
	@Command("search")
	@NotifyChange({ "sespList", "batchDis" })
	public void search() {
		sespList.clear();

		for (EmSocialinPaperModel m : espList) {
			if (!company.isEmpty()) {
				if (!RegexUtil.isExists(company, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!gid.isEmpty()) {
				if (!RegexUtil.isExists(gid, m.getGid() + "")) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getName())) {
					continue;
				}
			}
			if (!idcard.isEmpty()) {
				if (!RegexUtil.isExists(idcard, m.getEspa_idcard())) {
					continue;
				}
			}
			if (!computerid.isEmpty()) {
				if (!RegexUtil.isExists(computerid, m.getEmba_computerid())) {
					continue;
				}
			}
			if (!type.isEmpty()) {
				if (!RegexUtil.isExists(type, m.getEspa_type())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				batchDis = false;
				if (statename.equals("退回")) {
					if (m.getEspa_isback() != 1) {
						continue;
					}
				} else if (statename.equals("已撤销")) {
					if (m.getEspa_isrevoke() != 1) {
						continue;
					}
				} else {
					if (!RegexUtil.isExists(statename, m.getStatename())) {
						continue;
					}
				}
			} else {
				batchDis = true;
			}
			sespList.add(m);
		}
	}

	// 批量操作
	@Command("openwins")
	@NotifyChange("sespList")
	public void openwins(@BindingParam("url") String url,
			@BindingParam("list") Set<Listitem> set) {
		List<EmSocialinPaperModel> list = new ListModelList<EmSocialinPaperModel>();
		for (Listitem lt : set) {
			list.add((EmSocialinPaperModel) lt.getValue());
		}
		if (list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
			setEspList(new ListModelList<>(bll.getqdEmSocailinPaperList()));
			search();
		} else {
			Messagebox.show("请至少选择一个人!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 单条操作
	@Command("openwin")
	@NotifyChange("sespList")
	public void openwin(@BindingParam("url") String url,
			@BindingParam("each") EmSocialinPaperModel m) {
		List<EmSocialinPaperModel> list = new ListModelList<EmSocialinPaperModel>();
		list.add(m);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("daid", m.getEspa_id());
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		EmSocialinPaperListBll bll = new EmSocialinPaperListBll();
		setEspList(new ListModelList<>(bll.getqdEmSocailinPaperList()));
		search();
	}

	public List<EmSocialinPaperModel> getEspList() {
		return espList;
	}

	public void setEspList(List<EmSocialinPaperModel> espList) {
		this.espList = espList;
	}

	public List<EmSocialinPaperModel> getSespList() {
		return sespList;
	}

	public void setSespList(List<EmSocialinPaperModel> sespList) {
		this.sespList = sespList;
	}

	public boolean isMult() {
		return mult;
	}

	public void setMult(boolean mult) {
		this.mult = mult;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getComputerid() {
		return computerid;
	}

	public void setComputerid(String computerid) {
		this.computerid = computerid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public List<EmSocialinPaperModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<EmSocialinPaperModel> stateList) {
		this.stateList = stateList;
	}

	public boolean isBatchDis() {
		return batchDis;
	}

	public void setBatchDis(boolean batchDis) {
		this.batchDis = batchDis;
	}
}
