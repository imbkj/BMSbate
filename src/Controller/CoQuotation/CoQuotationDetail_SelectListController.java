package Controller.CoQuotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.CoQuotation.CoQuotation_List1Bll;

import Model.CoOfferModel;

public class CoQuotationDetail_SelectListController {

	private List<CoOfferModel> coofferList;
	private int cola_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cola_id").toString());

	CoQuotation_List1Bll bll = new CoQuotation_List1Bll();

	@Init
	public void init() throws Exception {
		setCoofferList(new ListModelList<CoOfferModel>(
				bll.getCoOfferList(cola_id)));
	}

	// 弹出查看页面
	@Command("chakan")
	public void chakan(@BindingParam("model") CoOfferModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coofid", model.getCoof_id());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/CoQuotationInfoSelect.zul", null, map);
		window.doModal();
	}

	public List<CoOfferModel> getCoofferList() {
		return coofferList;
	}

	public void setCoofferList(List<CoOfferModel> coofferList) {
		this.coofferList = coofferList;
	}
}
