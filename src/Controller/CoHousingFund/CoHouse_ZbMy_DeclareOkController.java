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

import Model.CoHousingFundPwdChangeModel;
import Util.RegexUtil;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class CoHouse_ZbMy_DeclareOkController {

	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private String queryCondition; // 查询条件
	private List<CoHousingFundPwdChangeModel> chfmList;
	private List<CoHousingFundPwdChangeModel> schfmList = new ArrayList<CoHousingFundPwdChangeModel>(); // 容器集合

	private Window window;
	private String cogjjid = "";
	private String cid = "";
	private String coName = "";

	public CoHouse_ZbMy_DeclareOkController() {

		chfmList = cfpb.queryPwd();
		search(null);
	}

	// @Command
	// @NotifyChange("chfmList")
	// public void query(@BindingParam("status") String status) {
	// if (status == null) {
	// status = "1";
	// }
	// if (queryCondition != null && !("").equals(queryCondition)
	// && !queryCondition.equals("") && queryCondition != null) {
	// chfmList = cfpb.queryPwdByCondition(queryCondition, status);
	// } else {
	// chfmList = cfpb.queryPwd();
	// }
	// }

	@Command
	public void minute(@BindingParam("model") CoHousingFundPwdChangeModel m) {
		Map<String, CoHousingFundPwdChangeModel> map = new HashMap<String, CoHousingFundPwdChangeModel>();
		map.put("cfpm", m);
		String url = "/CoHousingFund/CoHousingFund_PwdMinute.zul";
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	@Command
	@NotifyChange({ "schfmList" })
	public void search(@BindingParam("status") String status) {
		if (chfmList != null && chfmList.size() > 0) {
			schfmList.clear();
			for (int i = 0; i < chfmList.size(); i++) {
				if (!cogjjid.isEmpty()) {
					if (!RegexUtil.isExists(cogjjid, chfmList.get(i)
							.getCohf_houseid())) {
						continue;
					}
				}
				if (status != null) {
					if (!RegexUtil.isExists(status, chfmList.get(i)
							.getCfpc_state().toString())) {
						continue;
					}
				}
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, chfmList.get(i).getCid())) {
						continue;
					}
				}
				if (!coName.isEmpty()) {
					if (!RegexUtil.isExists(coName, chfmList.get(i)
							.getCohf_company())) {
						continue;
					}
				}
				schfmList.add(chfmList.get(i));
			}
		}
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

	public List<CoHousingFundPwdChangeModel> getSchfmList() {
		return schfmList;
	}

	public void setSchfmList(List<CoHousingFundPwdChangeModel> schfmList) {
		this.schfmList = schfmList;
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

	public List<CoHousingFundPwdChangeModel> getChfmList() {
		return chfmList;
	}

	public void setChfmList(List<CoHousingFundPwdChangeModel> chfmList) {
		this.chfmList = chfmList;
	}

}
