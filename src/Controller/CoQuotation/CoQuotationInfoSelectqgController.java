package Controller.CoQuotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Detail;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.CoQuotation.CoQuotationInfoBll;

import Model.CoOfferListModel;
import Model.CoOfferModel;

public class CoQuotationInfoSelectqgController {

	private List<CoOfferListModel> coofferinfoList;
	private int coof_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("coofid").toString());

	@Init
	public void init() throws Exception {
		CoQuotationInfoBll bll = new CoQuotationInfoBll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				bll.getcoofferListInfo(coof_id)));
	}

	// 弹出产品费用调整页面
	@Command("co_change")
	@NotifyChange("coofferinfoList")
	public void co_change(@BindingParam("a") CoOfferListModel cocoM)
			throws Exception {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_CoChangeqg.zul", null, map);
		window.doModal();
		CoQuotationInfoBll bll = new CoQuotationInfoBll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				bll.getCoOfferlistList(coof_id)));
	}

	// 弹出查看页面
	@Command("fp")
	public void fp(@BindingParam("model") CoOfferListModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", model.getColi_id());
		Window window = (Window) Executions.createComponents(
				"/CoBase/CoBase_SelectCoOffer_Detail.zul", null, map);
		window.doModal();
	}

	// 弹出产品终止页面
	@Command("co_del")
	@NotifyChange("coofferinfoList")
	public void co_del(@BindingParam("a") CoOfferListModel cocoM)
			throws Exception {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_CoStopqg.zul", null, map);
		window.doModal();
		CoQuotationInfoBll bll = new CoQuotationInfoBll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				bll.getCoOfferlistList(coof_id)));
	}

	public List<CoOfferListModel> getCoofferinfoList() {
		return coofferinfoList;
	}

	public void setCoofferinfoList(List<CoOfferListModel> coofferinfoList) {
		this.coofferinfoList = coofferinfoList;
	}
}
