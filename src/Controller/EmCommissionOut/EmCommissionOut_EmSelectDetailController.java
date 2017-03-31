package Controller.EmCommissionOut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmCommissionOutChangeModel;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_EmSelectDetailController {
	private List<EmCommissionOutChangeModel> ecocList = new ListModelList<>();
	Integer daid = 0;

	public EmCommissionOut_EmSelectDetailController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setEcocList(bll
					.getEmCommOutChangeList(" and typename='前道状态' and ecoc_ecou_id="
							+ daid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("openwin")
	public void openwin(@BindingParam("each") EmCommissionOutChangeModel m) {
		String url = "";
		Map<String, Object> map = new HashMap<>();

		switch (m.getEcoc_addtype()) {
		case "新增":
			url = "/EmCommissionOut/EmCommissionOut_AddDetail.zul";
			break;
		case "调整":
			url = "/EmCommissionOut/EmCommissionOut_ChangeDetail.zul";
			break;
		case "离职":
			url = "/EmCommissionOut/EmCommissionOut_TerminationDetail.zul";
			break;
		case "一次性费用":
			url = "/EmCommissionOut/EmCommissionOut_OneTimeFeeDetail.zul";
			break;
		case "年调":
			url = "/EmCommissionOut/EmCommissionOut_ChangeDetail.zul";
			break;
		default:
			url = "/EmCommissionOut/EmCommissionOut_ChangeDetail.zul";
			break;
		}

		map.put("daid", m.getEcoc_id());
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	public List<EmCommissionOutChangeModel> getEcocList() {
		return ecocList;
	}

	public void setEcocList(List<EmCommissionOutChangeModel> ecocList) {
		this.ecocList = ecocList;
	}
}
