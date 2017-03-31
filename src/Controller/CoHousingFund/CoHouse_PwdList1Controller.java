package Controller.CoHousingFund;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoHousingFundModel;
import Util.RegexUtil;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFundZbBll;
import bll.CoHousingFund.CoHousingFund_PwdBll;

/**
 * 密钥前到控制
 * 
 * @author Administrator
 * 
 */
public class CoHouse_PwdList1Controller {

	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private String queryCondition; // 查询条件
	private List<CoHousingFundModel> chfmList;
	private List<CoHousingFundModel> schfmList = new ArrayList<CoHousingFundModel>();
	private Window window;

	private String cid = "";
	private String cogjjid = "";
	private String firstMonth = "";
	private String coName = "";

	public CoHouse_PwdList1Controller() {
		chfmList = cfpb.getChfzList();
		search();
	}

	/**
	 * 按条件查询申报信息
	 */
	@Command
	@NotifyChange("schfmList")
	public void search() {
		if (chfmList != null && chfmList.size() > 0) {
			schfmList.clear();
			for (CoHousingFundModel m : chfmList) {
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, m.getCid().toString())) {
						continue;
					}
				}
				if (!cogjjid.isEmpty()) {
					if (!RegexUtil.isExists(cogjjid, m.getCohf_houseid())) {
						continue;
					}
				}
				if (!coName.isEmpty()) {
					if (!RegexUtil.isExists(coName, m.getCoba_company())) {
						continue;
					}
				}
				if (!firstMonth.isEmpty()) {
					if (!RegexUtil.isExists(firstMonth, m.getCohf_firmonth()
							.toString())) {
						continue;
					}
				}
				schfmList.add(m);
			}
		}
	}

	@Command
	@NotifyChange("schfmList")
	public void contro(@BindingParam("status") String status,
			@BindingParam("houseid") String houseid,
			@BindingParam("company") String company,
			@BindingParam("cohf_id") int cohf_id) {
		CoHousingFundModel cohf = new CoHousingFundModel();
		Map<String, CoHousingFundModel> map = new HashMap<String, CoHousingFundModel>();
		String url = "/CoHousingFund/Co_House_Pwd_cz.zul";
		for (int i = 0; i < chfmList.size(); i++) {
			if (chfmList.get(i).getCohf_id() == cohf_id) {
				cohf = chfmList.get(i);
				map.put("cohf", cohf);
			}
		}

		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		schfmList = cfpb.getChfzList();
	}

	/**
	 * 单位缴存信息
	 * 
	 * @param coname
	 */
	@Command
	@NotifyChange("schfmList")
	public void CoInfo(@BindingParam("coname") String coname,
			@BindingParam("cid") int cid, @BindingParam("cohfid") int cohf_id) {
		System.out.println(cid);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cid", cid);
		map.put("cohf_id", cohf_id);
		String url = "/CoHousingFund/Co_House_Info.zul";
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		schfmList = cfpb.getChfzList();
	}

	public List<CoHousingFundModel> getSchfmList() {
		return schfmList;
	}

	public void setSchfmList(List<CoHousingFundModel> schfmList) {
		this.schfmList = schfmList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCogjjid() {
		return cogjjid;
	}

	public void setCogjjid(String cogjjid) {
		this.cogjjid = cogjjid;
	}

	public String getFirstMonth() {
		return firstMonth;
	}

	public void setFirstMonth(String firstMonth) {
		this.firstMonth = firstMonth;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public List<CoHousingFundModel> getChfmList() {
		return chfmList;
	}

	public void setChfmList(List<CoHousingFundModel> chfmList) {
		this.chfmList = chfmList;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

}
