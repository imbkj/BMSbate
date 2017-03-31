package Controller.CoAgency;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Include;

import bll.CoAgency.BaseInfo_SelBll;

import Model.CoAgencyBaseCityRelModel;

public class WtAgency_ManageController {
	private int cabc_id = (Integer) Executions.getCurrent().getArg()
			.get("cabc_id");

	private CoAgencyBaseCityRelModel coabModel;
	private String icSrc;
	private Include icPage;

	public WtAgency_ManageController() {
		BaseInfo_SelBll bll = new BaseInfo_SelBll();
		coabModel = bll.getCoabModel(cabc_id);
		icSrc = "../CoAgency/WtAgency_BaseInfoUpdate.zul?cabc_id=" + cabc_id;
	}

	// 切换页面
	@Command("changePage")
	public void changePage(@BindingParam("lbl") String lbl) {
		try {
			switch (lbl) {
			case "机构基本信息":
				icSrc = "../CoAgency/WtAgency_BaseInfoUpdate.zul?cabc_id="
						+ cabc_id;
				break;
			case "联系人信息":
				icSrc = "../CoAgency/WtLinkMan_Manage.zul?cabc_id=" + cabc_id;
				break;
			case "机构合同信息":
				icSrc = "../CoAgency/WtCompact_Manage.zul?cabc_id=" + cabc_id;
				break;
			case "机构产品":
				icSrc = "../CoAgency/WtProduct_Manage.zul?cabc_id=" + cabc_id;
				break;
			case "政策信息":
				icSrc = "../CoAgency/WtPolicy_Manage.zul?cabc_id=" + cabc_id;
				break;
			case "社保字典库":
				icSrc = "../CoAgency/WtAlgorithm_Manage.zul?cabc_id=" + cabc_id;
				break;
			default:
				break;
			}
			BindUtils.postNotifyChange(null, null, this, "icSrc");
			icPage = (Include) Path.getComponent("/winAgManage/icPage");
			icPage.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CoAgencyBaseCityRelModel getCoabModel() {
		return coabModel;
	}

	public String getIcSrc() {
		return icSrc;
	}

}
