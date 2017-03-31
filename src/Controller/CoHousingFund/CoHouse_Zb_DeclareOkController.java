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

/**
 * 专办员信息查询页面
 * 
 * @author Administrator
 * 
 */
public class CoHouse_Zb_DeclareOkController {
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private String queryCondition; // 查询条件
	private List<CoHousingFundModel> chfmList;
	private Window window;
	private List<CoHousingFundModel> schfmList = new ArrayList<CoHousingFundModel>(); // 条件查询集合

	private String cid = "";
	private String coName = "";
	private String cogjjid = "";

	public CoHouse_Zb_DeclareOkController() {
		chfmList = cfzb.queryZbC();
		search(null);
	}

	// /**
	// * 按条件查询
	// */
	// @Command
	// @NotifyChange("chfmList")
	// public void query(@BindingParam("status") String status) {
	// if (status == null) {
	// status = "1";
	// }
	// if (queryCondition != null && !("").equals(queryCondition)
	// && !queryCondition.equals("") && queryCondition != null) {
	// chfmList = cfzb.queryZbByC(queryCondition, status);
	// } else {
	// chfmList = cfzb.queryZbC();
	// }
	// }

	/**
	 * 按条件查询
	 */
	@Command
	@NotifyChange("schfmList")
	public void search(@BindingParam("status") String status) {
		if (chfmList != null && chfmList.size() > 0) {
			schfmList.clear();
			for (CoHousingFundModel m : chfmList) {
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, m.getCid().toString())) {
						continue; // 不存在就跳过，否则就把这个对象加入到集合中
					}
				}
				if (!cogjjid.isEmpty()) {
					if (!RegexUtil.isExists(cogjjid, m.getCohf_houseid())) {
						continue;
					}
				}
				if (!coName.isEmpty()) {
					if (!RegexUtil.isExists(coName, m.getCohf_company())) {
						continue;
					}
				}
				if (status != null) {
					if (!RegexUtil.isExists(status,
							String.valueOf(m.getCfzc_state()))) {
						continue;
					}
				}
				schfmList.add(m);
			}
		}

	}

	/**
	 * 单位缴存信息
	 * 
	 * @param coname
	 */
	@Command
	public void CoInfo(@BindingParam("cid") int cid,
			@BindingParam("cohf_id") int cohf_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cid", cid);
		map.put("cohf_id", cohf_id);
		String url = "/CoHousingFund/Co_House_Info.zul";
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	/**
	 * 查看详情
	 */
	@Command
	public void minute(@BindingParam("model") CoHousingFundModel m) {
		Map<String, CoHousingFundModel> map = new HashMap<String, CoHousingFundModel>();
		map.put("chfm", m);
		String url = "/CoHousingFund/CoHousingFund_ZbMinute.zul";
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
