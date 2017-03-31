package Controller.CoAgency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import bll.CoAgency.CoAgencyBaseListBll;

public class BaseInfo_ListController {
	private List cobaBaseList;
	private CoAgencyBaseListBll coabBll = new CoAgencyBaseListBll();
	private final String coab_city = Executions.getCurrent().getArg()
			.get("city").toString();

	public BaseInfo_ListController() {
		cobaBaseList = coabBll.getCoAgBaseByCity(coab_city);
	}

	// 弹出委托机构详细信息页面
	@Command("allinone")
	public void allinone(@BindingParam("coabM") CoAgencyBaseModel coabM) {
		// 专递Model
		Map coabMap = new HashMap();
		coabMap.put("coabM", coabM);
		Window window = (Window) Executions.createComponents(
				"BaseInfo_AllInOne.zul", null, coabMap);
		window.doModal();
	}

	public List getCobaBaseList() {
		return cobaBaseList;
	}

	public void setCobaBaseList(List cobaBaseList) {
		this.cobaBaseList = cobaBaseList;
	}

}
