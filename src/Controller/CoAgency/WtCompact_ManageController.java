package Controller.CoAgency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoAgencies.CoAg_CompactSelectBll;
import bll.CoAgency.BaseInfo_SelBll;

import Model.CoAgencyBaseModel;
import Model.CoAgencyCompactModel;

public class WtCompact_ManageController {
	private int cabc_id = Integer.parseInt(Executions.getCurrent()
			.getParameter("cabc_id").toString());
	private List<CoAgencyCompactModel> list;
	private CoAg_CompactSelectBll bll;

	public WtCompact_ManageController() {
		bll = new CoAg_CompactSelectBll();
		list = bll.getWtCompactList(cabc_id);
	}

	// 弹出基本信息页面
	@Command
	public void info(@BindingParam("model") CoAgencyCompactModel model) {
		Map<String, CoAgencyCompactModel> map = new HashMap<String, CoAgencyCompactModel>();
		map.put("model", model);
		String url = "../CoAgencies/CoAg_stCompact.zul";
		if (model.getCoct_type().equals("委托合同")) {
			url = "../CoAgencies/CoAg_WtCompact.zul";
		}
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 打开查看合同内容页面
	@Command
	public void lookinfo(@BindingParam("model") CoAgencyCompactModel model) {
		Map<String, CoAgencyCompactModel> map = new HashMap<String, CoAgencyCompactModel>();
		map.put("model", model);
		String url = "../CoAgencies/CoAg_CompactInfo.zul";
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 弹出新增合同页面
	@Command
	@NotifyChange("list")
	public void addCompact() {
		BaseInfo_SelBll selbll = new BaseInfo_SelBll();
		CoAgencyBaseModel model = selbll.getCoAgencyBaseModelByCabcId(cabc_id);
		Map<String, CoAgencyBaseModel> map = new HashMap<String, CoAgencyBaseModel>();
		map.put("model", model);
		String url = "../CoAgencies/CoAg_WtCompactAdd.zul";
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		list = bll.getWtCompactList(cabc_id);
	}

	public List<CoAgencyCompactModel> getList() {
		return list;
	}

}
