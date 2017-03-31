package Controller.CoSocialInsurance;

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

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;

import Model.CoBaseModel;
import Model.CoShebaoChangeModel;
import Model.CoShebaoModel;
import Model.PubAreaCoShebaoModel;
import Util.RegexUtil;

public class CoSocialInsurance_qdSelectListallinoneController {

	private List<CoShebaoModel> csbList;
	private List<CoShebaoModel> scsbList = new ListModelList<>();
	private List<PubAreaCoShebaoModel> areaList = new ListModelList<>();
	private List<CoShebaoChangeModel> clientList = new ListModelList<>();

	private CoBaseModel cbm = (CoBaseModel) Executions.getCurrent().getArg()
			.get("model");
	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String sorid = "";
	private String sbadd = "";
	private String sorarea = "";
	private String addtype = "";
	private String ownmonth = "";
	private String statename = "";

	public CoSocialInsurance_qdSelectListallinoneController() {
		try {
			
			cid=cbm.getCid().toString();
			
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			setCsbList(bll.getCoShebaoList(" and a.cid="+cbm.getCid()+" "));
			search();
			setAreaList(bll.getAreaList());
			areaList.add(0, new PubAreaCoShebaoModel());
			setClientList(bll.getClientList());
			clientList.add(0, new CoShebaoChangeModel());

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange({ "scsbList" })
	public void search() {
		scsbList.clear();
		for (CoShebaoModel m : csbList) {
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
				if (!ownmonth.equals(m.getOwnmonth() + "")) {
					continue;
				}
			}
			if (!sorid.isEmpty()) {
				if (!RegexUtil.isExists(sorid, m.getCosb_sorid())) {
					continue;
				}
			}
			if (!sbadd.isEmpty()) {
				if (!RegexUtil.isExists(sbadd, m.getCosb_sbadd())) {
					continue;
				}
			}
			if (!sorarea.isEmpty()) {
				if (!sorarea.equals(m.getCosb_sorarea())) {
					continue;
				}
			}
			if (!addtype.isEmpty()) {
				if (!addtype.equals(m.getCosb_addtype())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!statename.equals(m.getStatename())) {
					continue;
				}
			}

			scsbList.add(m);
		}
	}

	/**
	 * 账户详情
	 * 
	 * @param m
	 */
	@Command("detail")
	public void detail(@BindingParam("each") CoShebaoModel m) {
		String url = "/CoSocialInsurance/CoSocialInsurance_Detail.zul";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCosb_id());
		map.put("role", "qd");

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	public List<CoShebaoModel> getCsbList() {
		return csbList;
	}

	public void setCsbList(List<CoShebaoModel> csbList) {
		this.csbList = csbList;
	}

	public List<CoShebaoModel> getScsbList() {
		return scsbList;
	}

	public void setScsbList(List<CoShebaoModel> scsbList) {
		this.scsbList = scsbList;
	}

	public List<PubAreaCoShebaoModel> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<PubAreaCoShebaoModel> areaList) {
		this.areaList = areaList;
	}

	public List<CoShebaoChangeModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<CoShebaoChangeModel> clientList) {
		this.clientList = clientList;
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

	public String getSorid() {
		return sorid;
	}

	public void setSorid(String sorid) {
		this.sorid = sorid;
	}

	public String getSbadd() {
		return sbadd;
	}

	public void setSbadd(String sbadd) {
		this.sbadd = sbadd;
	}

	public String getSorarea() {
		return sorarea;
	}

	public void setSorarea(String sorarea) {
		this.sorarea = sorarea;
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}
}
