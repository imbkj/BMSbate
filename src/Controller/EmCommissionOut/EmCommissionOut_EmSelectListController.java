package Controller.EmCommissionOut;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.EmCommissionOutModel;
import Util.SocialInsuranceEmCommissionOut;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_EmSelectListController {
	private List<EmCommissionOutModel> emoutList = new ListModelList<>();
	Integer gid = 0;

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOut_EmSelectListController() {
		try {
			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());

			init();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setEmoutList(bll.getEmCommOutList(" and a.gid=" + gid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Include控件初始化
	 * 
	 * @param include
	 */
	@Command("includeinit")
	public void includeinit(@BindingParam("include") Include include) {
		if (emoutList != null) {
			if (emoutList.size() > 0) {
				detail("/EmCommissionOut/EmCommissionOut_Detail1.zul",
						emoutList.get(0), include);
			}
		}
	}

	/**
	 * 点击操作打开详情页面
	 * 
	 * @param src
	 */
	@Command("detail")
	public void detail(@BindingParam("src") String src,
			@BindingParam("each") EmCommissionOutModel m,
			@BindingParam("include") Include include) {
		include.setDynamicProperty("daid", m.getEcou_id());
		include.setSrc(src + "?" + new Date().getTime());
	}

	public List<EmCommissionOutModel> getEmoutList() {
		return emoutList;
	}

	public void setEmoutList(List<EmCommissionOutModel> emoutList) {
		this.emoutList = emoutList;
	}
}
