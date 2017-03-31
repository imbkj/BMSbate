package Controller.CoHousingFund;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoHousingFundZbChangeModel;
import Util.RegexUtil;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFundZbBll;

/**
 * 前到重复提交
 * 
 * @author Administrator
 * 
 */
public class CoHousingFund_ResubmitListController {

	private Window window;

	private String queryCondition; // 查询条件
	private List<CoHousingFundZbChangeModel> chfmList;
	private List<CoHousingFundZbChangeModel> schfmList = new ArrayList<CoHousingFundZbChangeModel>();
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();

	private String coName = "";
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	private String addtype = "";
	private String cid = "";

	public CoHousingFund_ResubmitListController() {
		chfmList = cfzb.getResubmitList();
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
			for (CoHousingFundZbChangeModel m : chfmList) {
				if (!coName.isEmpty()) {
					if (!RegexUtil.isExists(coName, m.getCfzc_cohf_company())) {
						continue;
					}
				}
				if (!addtype.isEmpty()) {
					if (!RegexUtil.isExists(addtype, m.getCfzc_addtype())) {
						continue;
					}
				}
				if (!cid.isEmpty()) {
					if (!!RegexUtil.isExists(cid, m.getCid())) {
						continue;
					}
				}
				schfmList.add(m);
			}
		}
	}

	@Command
	@NotifyChange("schfmList")
	public void update(@BindingParam("a") CoHousingFundZbChangeModel m) {
		Map<String, CoHousingFundZbChangeModel> map = new HashMap<String, CoHousingFundZbChangeModel>();
		map.put("cfzc", m);
		window = (Window) Executions.createComponents(
				"/CoHousingFund/CoHousingFund_updateZb.zul", null, map);
		window.doModal();
		schfmList = cfzb.getResubmitList();
	}

	@Command
	@NotifyChange("schfmList")
	public void del(@BindingParam("a") final CoHousingFundZbChangeModel m) {
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					boolean flag = cfzb.delSb(m.getCfzc_id());
					if (flag) {
						Messagebox.show("删除成功");
					}
				}
			}
		};
		Messagebox.show("确认删除？", null, new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);
		schfmList = cfzb.getResubmitList();
	}

	// @Command
	// public void query(@BindingParam("")){
	//
	// }
	// @Command
	// @NotifyChange("chfmList")
	// public void query(@BindingParam("status") String status) {
	// if (status == null) {
	// status = "1";
	// }
	// if (queryCondition != null && !("").equals(queryCondition)
	// && !queryCondition.equals("") && queryCondition != null) {
	// chfmList = cfzb
	// .getChfzListChangeByCondition(queryCondition, status);
	// } else {
	// chfmList = cfzb.getChfzListChange();
	// }
	// }

	/**
	 * 重复提交
	 */
	@Command
	@NotifyChange("schfmList")
	public void resubmit(@BindingParam("efzc_id") int efzc_id) {
		Map<String, CoHousingFundZbChangeModel> map = new HashMap<String, CoHousingFundZbChangeModel>();
		for (int i = 0; i < chfmList.size(); i++) {
			if (chfmList.get(i).getCfzc_id() == efzc_id) {
				String url = "/CoHousingFund/CoHousingFundZbResubmit.zul";
				map.put("info", chfmList.get(i));
				window = (Window) Executions.createComponents(url, null, map);
				window.doModal();
				schfmList = cfzb.getResubmitList();
			}
		}
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public List<CoHousingFundZbChangeModel> getSchfmList() {
		return schfmList;
	}

	public void setSchfmList(List<CoHousingFundZbChangeModel> schfmList) {
		this.schfmList = schfmList;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public List<CoHousingFundZbChangeModel> getChfmList() {
		return chfmList;
	}

	public void setChfmList(List<CoHousingFundZbChangeModel> chfmList) {
		this.chfmList = chfmList;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

}
