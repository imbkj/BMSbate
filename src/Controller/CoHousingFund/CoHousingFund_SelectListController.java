package Controller.CoHousingFund;

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

import Model.CoHousingFundModel;
import Util.DateStringChange;
import Util.RegexUtil;
import bll.CoHousingFund.CoHousingFund_ListBll;

public class CoHousingFund_SelectListController {
	private List<CoHousingFundModel> cohfList;
	private List<CoHousingFundModel> scohfList = new ListModelList<>();
	private List<String> clientList = new ListModelList<>();

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private Date firmonth = null;
	private String tsday = "";
	private String houseid = "";
	private String ispwd = "";
	private String client = "";
	private String statename = "";

	public CoHousingFund_SelectListController() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			setCohfList(bll.getCoHoList(" and a.cid>0",false));
			setClientList(bll.getCoHoClientList("",false));
			clientList.add(0, "");
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检索
	 * 
	 */
	@Command
	@NotifyChange({ "scohfList" })
	public void search() {
		try {
			scohfList.clear();

			for (CoHousingFundModel m : cohfList) {
				if (m.getCid()==null) {
					continue;
				}
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
				if (firmonth != null) {
					if (!(Integer.valueOf(DateStringChange.DatetoSting(
							firmonth, "yyyyMM"))).equals(m.getCohf_firmonth())) {
						continue;
					}
				}
				if (!tsday.isEmpty()) {
					if (!tsday.equals(m.getCohf_tsday() + "")) {
						continue;
					}
				}
				if (!houseid.isEmpty()) {
					if (!houseid.equals(m.getCohf_houseid())) {
						continue;
					}
				}
				if (!ispwd.isEmpty()) {
					if (!ispwd.equals(m.getIspwd())) {
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
				scohfList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("检索出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 弹出窗口
	 * 
	 * @param url
	 *            窗口链接
	 * @param m
	 */
	@Command
	@NotifyChange({ "scohfList" })
	public void openwin(@BindingParam("each") CoHousingFundModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getCohf_id());
		map.put("cid", m.getCid());

		try {
			if (!url.isEmpty()) {
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();

				CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
				setCohfList(bll.getCoHoList("",false));
				search();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("弹出窗口出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<CoHousingFundModel> getCohfList() {
		return cohfList;
	}

	public void setCohfList(List<CoHousingFundModel> cohfList) {
		this.cohfList = cohfList;
	}

	public List<CoHousingFundModel> getScohfList() {
		return scohfList;
	}

	public void setScohfList(List<CoHousingFundModel> scohfList) {
		this.scohfList = scohfList;
	}

	public final String getCid() {
		return cid;
	}

	public final String getShortname() {
		return shortname;
	}

	public final Date getFirmonth() {
		return firmonth;
	}

	public final String getTsday() {
		return tsday;
	}

	public final String getHouseid() {
		return houseid;
	}

	public final String getIspwd() {
		return ispwd;
	}

	public final String getClient() {
		return client;
	}

	public final void setCid(String cid) {
		this.cid = cid;
	}

	public final void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public final void setFirmonth(Date firmonth) {
		this.firmonth = firmonth;
	}

	public final void setTsday(String tsday) {
		this.tsday = tsday;
	}

	public final void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public final void setIspwd(String ispwd) {
		this.ispwd = ispwd;
	}

	public final void setClient(String client) {
		this.client = client;
	}

	public List<String> getClientList() {
		return clientList;
	}

	public void setClientList(List<String> clientList) {
		this.clientList = clientList;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}
}
