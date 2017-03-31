package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import bll.CoHousingFund.CoHousingFund_ListBll;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.RegexUtil;

public class CoHousingFund_DeclareListController {

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

	public CoHousingFund_DeclareListController() {
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
			setCohfList(bll.getCoHoList(
					" and cohf_state in(0,1,2) and a.cid is not null", false));
			updateCppChange();
			setClientList(bll.getCoHoClientList(
					" and cohf_state in(0,1,2) and a.cid is not null", false));
			clientList.add(0, "");
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateCppChange(){
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		List<CoHousingFundChangeModel> changelist = new ListModelList<>();
		Calendar calendar = new GregorianCalendar();
		Date d = new Date();
		calendar.setTime(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = sdf.format(d);
		String d2 = String.valueOf(calendar.get(Calendar.YEAR)) + "-7-1";
		String o1 = "";
		String o2 = "";
		Integer add = DateUtil.datediff(d1, d2, "d");
		if (add > 0) {
			o1 = String.valueOf(calendar.get(Calendar.YEAR) - 1) + "07";
			o2 = String.valueOf(calendar.get(Calendar.YEAR)) + "07";
		} else {
			o1 = String.valueOf(calendar.get(Calendar.YEAR)) + "-7-1";
			o2 = String.valueOf(calendar.get(Calendar.YEAR) + 1) + "-7-1";
		}
		changelist = bll
				.getCoHoChangeList(" and chfc_addtype='比例调整' and ownmonth between "
						+ o1 + " and " + o2+" and chfc_state in (1,2,3)");
		for (CoHousingFundModel m1 : cohfList) {
			for (CoHousingFundChangeModel m2 : changelist) {
				if (m1.getCid().equals(m2.getCid())) {
					m1.setCppChange(true);
					break;
				}
			}
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
		CoHousingFund_ListBll sebll = new CoHousingFund_ListBll();
		CoHousingFundChangeModel tm = sebll.getCoHouseingTapId(m.getCid());
		Integer state = sebll.getTaskState(tm.getChfc_tapr_id());
		if (state != null && state != 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("daid", m.getCohf_id());

			try {
				if (!url.isEmpty()) {
					Window window = (Window) Executions.createComponents(url,
							null, map);
					window.doModal();

					CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
					setCohfList(bll.getCoHoList(
							" and cohf_id=" + m.getCohf_id(), false));
					updateCppChange();
					search();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("弹出窗口出错：" + e.toString(), "ERROR",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else if (state == 1) {
			Messagebox.show("该客户公积金的“" + tm.getChfc_addtype()
					+ "”任务单还没有处理完成，不能做变更申报", "ERROR", Messagebox.OK,
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
