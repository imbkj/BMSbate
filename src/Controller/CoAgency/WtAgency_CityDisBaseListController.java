package Controller.CoAgency;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import Util.UserInfo;
import bll.CoAgency.BaseInfo_OperateBll;
import bll.CoAgency.WtAgency_DisCitySelBll;

public class WtAgency_CityDisBaseListController {
	private List<CoAgencyBaseModel> baselist;
	private final String city = Executions.getCurrent().getArg().get("city")
			.toString();
	private WtAgency_DisCitySelBll bll;

	public WtAgency_CityDisBaseListController() {
		bll = new WtAgency_DisCitySelBll();
		baselist = bll.getCoAgBaseListByCity(city);
		
	}

	// 设置为默认机构
	@Command("defaultAg")
	public void defaultAg(@BindingParam("id") final int id) throws Exception {
		if (Messagebox.show("确认设置该机构为" + city + "默认机构吗？", "操作提示",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			try {
				BaseInfo_OperateBll opBll = new BaseInfo_OperateBll();
				if (opBll.SetDefaultAgency(id) == 1) {
					referenceList();
					Messagebox.show("设置默认机构，成功。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);

				} else {
					Messagebox.show("设置默认机构，失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);

				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("设置默认机构，出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 取消合作机构
	@Command("delDis")
	@NotifyChange("baselist")
	public void delDis(@BindingParam("id") final int id) throws Exception {
		if (Messagebox.show("确认取消该机构与城市的关联？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			try {
				BaseInfo_OperateBll opBll = new BaseInfo_OperateBll();
				if (opBll.DelDisBasefromCity(id, city, UserInfo.getUsername()) == 1) {
					referenceList();
					Messagebox.show("取消合作机构，成功。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);

				} else {
					Messagebox.show("取消合作机构，失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);

				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("取消合作机构，出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	//设置最低服务费
	@Command("setlowfee")
	@NotifyChange("baselist")
	public void setlowfee(@BindingParam("id") final int id)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cabc_id", id);
		//map.put("cid", cid);
		Window window = (Window) Executions.createComponents("../CoAgency/CoAgencyFeeManage.zul", null, map);
		window.doModal();
		baselist = bll.getCoAgBaseListByCity(city);
	}
	//添加产品
	@Command("addprd")
	public void addprd(@BindingParam("id") final int id)
	{
		System.out.println(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cabc_id", id);
//		map.put("cid", cid);
		Window window = (Window) Executions.createComponents("../CoProduct/Agcpd_add.zul", null, map);
		window.doModal();

	}
	
	
	private void referenceList() {
		baselist = bll.getCoAgBaseListByCity(city);
		BindUtils.postNotifyChange(null, null, this, "baselist");
	}

	public List<CoAgencyBaseModel> getBaselist() {
		return baselist;
	}

}
