package Controller.CoAgency;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import bll.CoAgency.BaseInfo_OperateBll;
import bll.CoAgency.WtAgency_DisCitySelBll;
import Model.CoAgencyBaseModel;
import Util.CheckString;
import Util.UserInfo;

public class WtAgency_DisOperateController {
	private List<CoAgencyBaseModel> baseList;
	private List<CoAgencyBaseModel> winBaseList;
	private final String city = Executions.getCurrent().getArg().get("city")
			.toString();

	public WtAgency_DisOperateController() {
		WtAgency_DisCitySelBll bll = new WtAgency_DisCitySelBll();
		winBaseList = baseList = bll.getCoAgBaseListNotCity(city);
	}

	// 查询机构
	@Command("searchAgency")
	@NotifyChange("winBaseList")
	public void searchAgency(@BindingParam("agency") String agency) {
		if ("".equals(agency) || agency == null) {
			winBaseList = baseList;
		} else {
			winBaseList = searchAgencyInfo(agency);
		}

	}

	// 检索机构信息
	private List<CoAgencyBaseModel> searchAgencyInfo(String agency) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		if (CheckString.isNum(agency)) {
			for (CoAgencyBaseModel m : baseList) {
				if (m.getCoab_id() == Integer.parseInt(agency)) {
					list.add(m);
					return list;
				}
			}
		} else if (CheckString.isLetter(agency)) {
			for (CoAgencyBaseModel m : baseList) {
				try {
					if (m.getCoab_namespell().contains(agency)
							|| m.getCoab_name().contains(agency)) {
						list.add(m);
					}
				} catch (Exception e) {

				}
			}
		} else {
			for (CoAgencyBaseModel m : baseList) {
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

	// 提交分配事件
	@Command("Submit")
	public void Submit(@BindingParam("lb") Listbox lb,
			@BindingParam("win") final Window win,
			@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Set<Listitem> check = lb.getSelectedItems();
		if (!check.isEmpty()) {
			try {
				List<Integer> list = new ArrayList<Integer>();
				// 遍历勾选项
				for (Listitem c : check) {
					list.add(((CoAgencyBaseModel) c.getValue()).getCoab_id());
				}
				// 提交操作
				BaseInfo_OperateBll opBll = new BaseInfo_OperateBll();
				opBll.DisBase(city, list, UserInfo.getUsername());
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("refreshWin", null);
				Messagebox.show("设置合作机构，操作成功。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("设置合作机构，出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请先选择相应的机构。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	public List<CoAgencyBaseModel> getWinBaseList() {
		return winBaseList;
	}

}
