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
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.CoHousingFundPwdChangeModel;
import Util.RegexUtil;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class CoHousingFund_ResubmitPwdController {
	private Window window;
	private List<CoHousingFundPwdChangeModel> chfmList;
	private List<CoHousingFundPwdChangeModel> schfmList = new ArrayList<CoHousingFundPwdChangeModel>();
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();

	private String coName = "";
	private String addtype = "";
	private String cid = "";

	public CoHousingFund_ResubmitPwdController() {

		chfmList = cfpb.getPwdResubmitList();
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
			for (CoHousingFundPwdChangeModel m : chfmList) {
				if (!coName.isEmpty()) {
					if (!RegexUtil.isExists(coName, m.getCfpc_cohf_company())) {
						continue;
					}
				}
				if (!addtype.isEmpty()) {
					if (!RegexUtil.isExists(addtype, m.getCfpc_addtype())) {
						continue;
					}
				}
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, m.getCid())) {
						continue;
					}
				}
				schfmList.add(m);
			}
		}
	}

	@Command
	@NotifyChange("schfmList")
	public void update(@BindingParam("a") CoHousingFundPwdChangeModel m) {
		Map<String, CoHousingFundPwdChangeModel> map = new HashMap<String, CoHousingFundPwdChangeModel>();
		map.put("cfpc", m);
		String url = "";
		if (m.getCfpc_addtype().equals("申请数字证书")) {
			url = "/CoHousingFund/CoHousingFund_UpdateYearLimit.zul";
		} else if (m.getCfpc_addtype().equals("密钥专办员变更")) {
			url = "/CoHousingFund/CoHousingFund_UpdatePwdZb.zul";
		} else if (m.getCfpc_addtype().equals("数字证书续期")) {
			url = "/CoHousingFund/CoHousingFund_UpdateYearLimit.zul";
		}
		if (!url.equals("")) {
			window = (Window) Executions.createComponents(url, null, map);
			window.doModal();
		}
		schfmList = cfpb.getPwdResubmitList();
	}

	@NotifyChange("schfmList")
	@Command
	public void del(@BindingParam("a") final CoHousingFundPwdChangeModel m) {
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					boolean flag = cfpb.delPwd(m.getCfpc_id());
					if (flag) {
						Messagebox.show("删除成功");
					}
				}
			}
		};
		Messagebox.show("确认删除？", null, new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);

		schfmList = cfpb.getPwdResubmitList();
	}

	// @Command
	// @NotifyChange("chfmList")
	// public void query(@BindingParam("status") String status) {
	// if (status == null) {
	// status = "1";
	// }
	// if (queryCondition != null && !("").equals(queryCondition)
	// && !queryCondition.equals("") && queryCondition != null) {
	// chfmList = cfpb.getPwdListChangeByCondition(queryCondition, status);
	// } else {
	// chfmList = cfpb.getPwdListChange();
	// }
	// }

	/**
	 * 重复提交
	 */
	@Command
	@NotifyChange("schfmList")
	public void resubmit(@BindingParam("efpc_id") int efpc_id) {
		Map<String, CoHousingFundPwdChangeModel> map = new HashMap<String, CoHousingFundPwdChangeModel>();
		for (int i = 0; i < chfmList.size(); i++) {
			if (chfmList.get(i).getCfpc_id() == efpc_id) {
				String url = "/CoHousingFund/CoHousingFundPwdResubmit.zul";
				map.put("info", chfmList.get(i));
				window = (Window) Executions.createComponents(url, null, map);
				window.doModal();
				schfmList = cfpb.getPwdResubmitList();
			}
		}
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<CoHousingFundPwdChangeModel> getChfmList() {
		return chfmList;
	}

	public void setChfmList(List<CoHousingFundPwdChangeModel> chfmList) {
		this.chfmList = chfmList;
	}

	public List<CoHousingFundPwdChangeModel> getSchfmList() {
		return schfmList;
	}

	public void setSchfmList(List<CoHousingFundPwdChangeModel> schfmList) {
		this.schfmList = schfmList;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

}
