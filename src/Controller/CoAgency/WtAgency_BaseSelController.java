package Controller.CoAgency;

import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import Model.CoAgencyBaseModel;
import Util.CheckString;
import bll.CoAgency.BaseInfo_SelBll;

public class WtAgency_BaseSelController {
	private List<CoAgencyBaseModel> cabaList;
	private List<CoAgencyBaseModel> wincabaList;

	public WtAgency_BaseSelController() {
		BaseInfo_SelBll bll = new BaseInfo_SelBll();
		wincabaList = cabaList = bll.getWtAgencyList();
	}

	// 机构检索
	@Command("searchAgency")
	@NotifyChange("wincabaList")
	public void searchAgency(@BindingParam("agency") String agency) {
		if ("".equals(agency) || agency == null) {
			wincabaList = cabaList;
		} else {
			wincabaList = searchAgencyInfo(agency);
		}

	}

	// 检索机构信息
	private List<CoAgencyBaseModel> searchAgencyInfo(String agency) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		if (CheckString.isNum(agency)) {
			for (CoAgencyBaseModel m : cabaList) {
				if (m.getCoab_id() == Integer.parseInt(agency)) {
					list.add(m);
					return list;
				}
			}
		} else if (CheckString.isLetter(agency)) {
			for (CoAgencyBaseModel m : cabaList) {
				try {
					if (m.getCoab_namespell().contains(agency)
							|| m.getCoab_name().contains(agency)) {
						list.add(m);
					}
				} catch (Exception e) {

				}
			}
		} else {
			for (CoAgencyBaseModel m : cabaList) {
				try {
					if (m.getCoab_name().contains(agency)
							|| m.getCabsModel().getCoas_client()
									.contains(agency)) {
						list.add(m);
					}
				} catch (Exception e) {

				}
			}
		}
		return list;
	}

	public List<CoAgencyBaseModel> getWincabaList() {
		return wincabaList;
	}

}
