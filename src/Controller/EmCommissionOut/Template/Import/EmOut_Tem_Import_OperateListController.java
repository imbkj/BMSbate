package Controller.EmCommissionOut.Template.Import;

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

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.EmCommissionOutPayUpdateCRTModel;
import Util.RegexUtil;

public class EmOut_Tem_Import_OperateListController {

	private List<EmCommissionOutPayUpdateCRTModel> ectList;
	private List<EmCommissionOutPayUpdateCRTModel> sectList = new ListModelList<>();

	// 检索条件
	private String ecut_name = "";
	private String statename = "";

	public EmOut_Tem_Import_OperateListController() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void init() {
		try {
			EmCommissionOutListBll bll = new EmCommissionOutListBll();

			setEctList(bll.getEmOutPayUpdateT(""));

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
	@NotifyChange({ "sectList" })
	public void search() {
		sectList.clear();

		try {
			for (EmCommissionOutPayUpdateCRTModel m : ectList) {
				if (!ecut_name.isEmpty()) {
					if (!RegexUtil.isExists(ecut_name, m.getEcut_name())) {
						continue;
					}
				}
				if (!statename.isEmpty()) {
					if (!statename.equals(m.getStatename())) {
						continue;
					}
				}

				sectList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 弹出窗口
	 * 
	 * @param url
	 * @param m
	 */
	@Command("openwin")
	@NotifyChange({ "sectList" })
	public void openwin(@BindingParam("url") String url,
			@BindingParam("each") EmCommissionOutPayUpdateCRTModel m) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (m != null) {
			map.put("daid", m.getEcut_id());
		}

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		init();
	}

	public List<EmCommissionOutPayUpdateCRTModel> getEctList() {
		return ectList;
	}

	public void setEctList(List<EmCommissionOutPayUpdateCRTModel> ectList) {
		this.ectList = ectList;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getSectList() {
		return sectList;
	}

	public void setSectList(List<EmCommissionOutPayUpdateCRTModel> sectList) {
		this.sectList = sectList;
	}

	public String getEcut_name() {
		return ecut_name;
	}

	public void setEcut_name(String ecut_name) {
		this.ecut_name = ecut_name;
	}

	public final String getStatename() {
		return statename;
	}

	public final void setStatename(String statename) {
		this.statename = statename;
	}
}
