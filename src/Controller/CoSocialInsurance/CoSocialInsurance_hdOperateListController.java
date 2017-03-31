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

import Model.CoShebaoChangeModel;
import Model.PubAreaCoShebaoModel;
import Model.PubStateModel;
import Util.FileOperate;
import Util.RegexUtil;

public class CoSocialInsurance_hdOperateListController {

	private List<CoShebaoChangeModel> csbcList;
	private List<CoShebaoChangeModel> scsbcList = new ListModelList<>();
	private List<PubStateModel> stateList = new ListModelList<>();
	private List<PubAreaCoShebaoModel> areaList = new ListModelList<>();
	private List<CoShebaoChangeModel> clientList = new ListModelList<>();

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String sorid = "";
	private String sbadd = "";
	private String sorarea = "";
	private String addtype = "";
	private String client = "";
	private String ownmonth = "";
	private String statename = "";

	public CoSocialInsurance_hdOperateListController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			setCsbcList(new ListModelList<>(bll.getCoShebaoChangeList("")));
			search();
			setAreaList(bll.getAreaList());
			areaList.add(0, new PubAreaCoShebaoModel());
			setClientList(bll.getClientList());
			clientList.add(0, new CoShebaoChangeModel());
			setStateList(bll.getStateList(" and type='csoichange'"));
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange({ "scsbcList" })
	public void search() {
		scsbcList.clear();
		for (CoShebaoChangeModel m : csbcList) {
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
				if (!RegexUtil.isExists(sorid, m.getCsbc_sorid())) {
					continue;
				}
			}
			if (!sbadd.isEmpty()) {
				if (!RegexUtil.isExists(sbadd, m.getCsbc_sbadd())) {
					continue;
				}
			}
			if (!sorarea.isEmpty()) {
				if (!sorarea.equals(m.getCsbc_sorarea())) {
					continue;
				}
			}
			if (!addtype.isEmpty()) {
				if (!addtype.equals(m.getCsbc_addtype())) {
					continue;
				}
			}
			if (!client.isEmpty()) {
				if (!client.equals(m.getCoba_client())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!statename.equals(m.getStatename())) {
					continue;
				}
			}

			scsbcList.add(m);
		}
	}

	@Command("addtypeOnSelect")
	@NotifyChange({ "stateList", "statename" })
	public void addtypeOnSelect() {
		if (!addtype.isEmpty()) {
			stateList.clear();
			String type = "";
			switch (addtype) {
			case "缴存登记":
				type = "csoiadd";
				break;
			case "账户接管":
				type = "csoiadd1";
				break;
			case "信息变更":
				type = "csoichange";
				break;
			case "账户注销":
				type = "csoican";
				break;
			case "管理终止":
				type = "csoiter";
				break;
			default:
				break;
			}
			setStateList(new CoSocialInsurance_ListBll()
					.getStateList(" and type='" + type + "'"));
			stateList.add(0, new PubStateModel());
			statename = "";
		} else {
			stateList.clear();
			statename = "";
		}
	}

	@Command("download")
	public void download(@BindingParam("filename") String filename) {
		try {
			String fileurl = "OfficeFile/DownLoad/CoSocialInsurance/"
					+ filename;
			FileOperate.download(fileurl);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("下载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 状态变更
	 * 
	 * @param m
	 */
	@Command("updatestate")
	@NotifyChange({ "csbcList", "scsbcList" })
	public void updatestate(@BindingParam("each") CoShebaoChangeModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCsbc_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();
		setCsbcList(new ListModelList<>(bll.getCoShebaoChangeList("")));
		search();
	}

	/**
	 * 申报详情
	 * 
	 * @param m
	 */
	@Command("detail")
	public void detail(@BindingParam("each") CoShebaoChangeModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCsbc_id());
		map.put("role", "hd");

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	/**
	 * 材料详情
	 * 
	 * @param m
	 */
	@Command("docdetail")
	public void docdetail(@BindingParam("each") CoShebaoChangeModel m) {
		String url = "/CoSocialInsurance/CoSocialInsurance_DocDetail.zul";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCsbc_id());
		if (m.getCsbc_addtype().equals("缴存登记")) {
			map.put("puzu_id", 36);
		} else if (m.getCsbc_addtype().equals("信息变更")) {
			map.put("puzu_id", 37);
		} else if (m.getCsbc_addtype().equals("账户注销")) {
			map.put("pzuu_id", 38);
		}

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	/**
	 * 退回
	 * 
	 * @param m
	 */
	@Command("back")
	@NotifyChange({ "csbcList", "scsbcList" })
	public void back(@BindingParam("each") CoShebaoChangeModel m) {
		String url = "/CoSocialInsurance/CoSocialInsurance_Back.zul";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCsbc_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();
		setCsbcList(new ListModelList<>(bll.getCoShebaoChangeList("")));
		search();
	}

	/**
	 * 交接密码
	 * 
	 * @param m
	 */
	@Command("pwd")
	@NotifyChange({ "csbcList", "scsbcList" })
	public void pwd(@BindingParam("each") CoShebaoChangeModel m) {
		String url = "/CoSocialInsurance/CoSocialInsurance_TerminationPwd.zul";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCsbc_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();
		setCsbcList(new ListModelList<>(bll.getCoShebaoChangeList("")));
		search();
	}

	public List<CoShebaoChangeModel> getCsbcList() {
		return csbcList;
	}

	public void setCsbcList(List<CoShebaoChangeModel> csbcList) {
		this.csbcList = csbcList;
	}

	public List<CoShebaoChangeModel> getScsbcList() {
		return scsbcList;
	}

	public void setScsbcList(List<CoShebaoChangeModel> scsbcList) {
		this.scsbcList = scsbcList;
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

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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

	public List<PubStateModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<PubStateModel> stateList) {
		this.stateList = stateList;
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
}
