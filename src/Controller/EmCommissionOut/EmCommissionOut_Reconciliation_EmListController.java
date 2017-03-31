package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.EmCommissionOutHistoryModel;
import Util.RegexUtil;

public class EmCommissionOut_Reconciliation_EmListController {

	private List<EmCommissionOutHistoryModel> emList;
	private List<EmCommissionOutHistoryModel> semList = new ListModelList<>();

	String ownmonth = "";
	Integer cid;

	private String gid = "";
	private String name = "";
	private String idcard = "";

	public EmCommissionOut_Reconciliation_EmListController() {
		try {
			ownmonth = Executions.getCurrent().getArg().get("ownmonth")
					.toString();
			cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
					.toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("页面加载出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setEmList(bll.getEmOutEmCompareList(ownmonth, cid, ""));
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "semList" })
	public void search() {
		try {
			if (emList != null && emList.size() > 0) {
				semList.clear();
				for (EmCommissionOutHistoryModel ehm : emList) {
					if (!gid.isEmpty()) {
						if (!RegexUtil.isExists(gid, ehm.getGid().toString())) {
							continue;
						}
					}
					if (!name.isEmpty()) {
						if (!RegexUtil.isExists(name, ehm.getEmba_name())) {
							continue;
						}
					}
					if (!idcard.isEmpty()) {
						if (!idcard.equals(ehm.getEcoh_idcard())) {
							continue;
						}
					}

					semList.add(ehm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<EmCommissionOutHistoryModel> getEmList() {
		return emList;
	}

	public void setEmList(List<EmCommissionOutHistoryModel> emList) {
		this.emList = emList;
	}

	public List<EmCommissionOutHistoryModel> getSemList() {
		return semList;
	}

	public void setSemList(List<EmCommissionOutHistoryModel> semList) {
		this.semList = semList;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
}
