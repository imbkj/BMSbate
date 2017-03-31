/*
 * 创建人：林少斌
 * 创建时间：2013-9-24
 * 用途：委托机构联系人管理页面Controller
 */
package Controller.CoAgency;

import impl.UserInfoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import service.UserInfoService;

import Model.CoAgencyBaseModel;
import Model.CoAgencyLinkmanModel;
import bll.CoAgency.CoAgencyBaseListBll;

public class LinkMan_UpdateListController {

	private CoAgencyBaseListBll cablBll = new CoAgencyBaseListBll();
	int coab_id = (Integer) Executions.getCurrent().getArg().get("coab_id");
	private List caliBaseList; // 联系人信息
	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	// 构造函数
	public LinkMan_UpdateListController() {
		// 加载联系人列表
		caliBaseList = cablBll.getAgLinkman(coab_id);
	}

	// 弹出修改联系人信息页面
	@Command("update")
	@NotifyChange("caliBaseList")
	public void update(@BindingParam("caliM") CoAgencyLinkmanModel caliM) {
		// 专递Model
		Map caliMap = new HashMap();
		caliMap.put("caliM", caliM);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Update.zul", null, caliMap);
		window.doModal();
		caliBaseList = cablBll.getAgLinkman(coab_id);
	}

	// 弹出删除联系人信息页面
	@Command("delete")
	@NotifyChange("caliBaseList")
	public void delete(@BindingParam("cali_id") int cali_id) {
		// 专递参数
		Map caliMap = new HashMap();
		caliMap.put("cali_id", cali_id);
		caliMap.put("coab_id", coab_id);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Delete.zul", null, caliMap);
		window.doModal();
		caliBaseList = cablBll.getAgLinkman(coab_id);
	}

	// 弹出委托机构详细信息页面
	@Command("detail")
	public void detail(@BindingParam("caliM") CoAgencyLinkmanModel caliM) {
		// 专递Model
		Map caliMap = new HashMap();
		caliMap.put("caliM", caliM);
		Window window = (Window) Executions.createComponents(
				"LinkMan_Detail.zul", null, caliMap);
		window.doModal();
	}

	public List getCaliBaseList() {
		return caliBaseList;
	}

	public void setCaliBaseList(List caliBaseList) {
		this.caliBaseList = caliBaseList;
	}

}
