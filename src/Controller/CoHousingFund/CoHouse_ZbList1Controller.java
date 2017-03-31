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

import bll.CoHousingFund.CoHousingFundZbBll;

import Model.CoHousingFundModel;
import Util.RegexUtil;
import Util.SingleBllFactory;

/**
 * 专办员前到控制
 * 
 * @author Administrator
 * 
 */
public class CoHouse_ZbList1Controller {

	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private String queryCondition; // 查询条件
	private List<CoHousingFundModel> chfmList;
	private List<CoHousingFundModel> schfmList = new ArrayList<CoHousingFundModel>();
	private Window window;

	private String cid = "";
	private String coName = "";
	private String firstMonth = "";
	private String cogjjid = "";

	public CoHouse_ZbList1Controller() {
		chfmList = cfzb.getChfzList();
		search(null);
		
	}

	/**
	 * 按条件查询
	 */
	@Command
	@NotifyChange("schfmList")
	public void search(@BindingParam("pwd") String pwd) {
		if (chfmList != null && chfmList.size() > 0) {
			schfmList.clear();
			for (CoHousingFundModel m : chfmList) {
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, m.getCid().toString())) {
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
				if (!cogjjid.isEmpty()) {
					if (!RegexUtil.isExists(cogjjid, m.getCohf_houseid())) {
						continue;
					}
				}
				if (pwd != null) {
					if (!RegexUtil.isExists(pwd, m.getIspwd())) {
						continue;
					}
				}
				schfmList.add(m);
			}
		}
	}
	
	
	//打开业务操作
	@Command
	@NotifyChange("schfmList")
	public void contro(@BindingParam("status") String status,
			@BindingParam("houseid") String houseid,
			@BindingParam("company") String company,
			@BindingParam("cohf_id") int cohf_id,
			@BindingParam("cid") String cid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("houseid", houseid);
		map.put("company", company);
		map.put("cohf_id", String.valueOf(cohf_id));
		map.put("cid", cid);
		String url = "/CoHousingFund/Co_House_Zb_cz.zul";
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		schfmList = cfzb.getChfzList();
	}

	/**
	 * 单位缴存信息
	 * 
	 * @param coname
	 */
	@Command
	public void CoInfo(@BindingParam("coname") String coname,
			@BindingParam("cid") int cid, @BindingParam("cohfid") int cohf_id) {
		System.out.println(cid);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cid", cid);
		map.put("cohf_id", cohf_id);
		String url = "/CoHousingFund/Co_House_Info.zul";
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
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

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getFirstMonth() {
		return firstMonth;
	}

	public void setFirstMonth(String firstMonth) {
		this.firstMonth = firstMonth;
	}

	public String getCogjjid() {
		return cogjjid;
	}

	public void setCogjjid(String cogjjid) {
		this.cogjjid = cogjjid;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public List<CoHousingFundModel> getChfmList() {
		return chfmList;
	}

	public void setChfmList(List<CoHousingFundModel> chfmList) {
		this.chfmList = chfmList;
	}

}
