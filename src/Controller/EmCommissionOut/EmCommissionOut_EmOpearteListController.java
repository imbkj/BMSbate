package Controller.EmCommissionOut;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;
import Model.EmCommissionOutModel;
import Util.SocialInsuranceEmCommissionOut;

public class EmCommissionOut_EmOpearteListController {
	private List<EmCommissionOutModel> emoutList = new ListModelList<>();
	Integer gid = 0;

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOut_EmOpearteListController() {
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
	@GlobalCommand
	@NotifyChange({ "emoutList" })
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setEmoutList(bll.getEmCommOutList(" and ecou_state=1 and a.gid="
					+ gid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 点击操作打开调整、终止页面
	 * 
	 * @param src
	 */
	@Command("operate")
	@NotifyChange({ "cm", "cfeeList" })
	public void operate(@BindingParam("src") String src,
			@BindingParam("each") EmCommissionOutModel m,
			@BindingParam("include") Include include) {
		include.setDynamicProperty("m", m);
		include.setSrc(src + "?" + new Date().getTime());
	}

	public List<EmCommissionOutModel> getEmoutList() {
		return emoutList;
	}

	public void setEmoutList(List<EmCommissionOutModel> emoutList) {
		this.emoutList = emoutList;
	}
}
