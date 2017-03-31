package Controller.CoAgency;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;

import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Util.UserInfo;
import bll.CoAgency.BaseInfo_OperateBll;
import bll.CoAgency.BaseInfo_SelBll;

public class StAgency_BaseInfoUpdateController {
	private int coab_id = (Integer) Executions.getCurrent().getArg()
			.get("coab_id");
	private BaseInfo_SelBll selBll = new BaseInfo_SelBll();
	private List<String> provincelist; // 省份
	private List<String> citylist; // 城市
	private List<String> clientList = new ArrayList<String>(); // 客服
	private CoAgencyBaseModel cabaModel;
	private CoAgencyBaseServiceModel cabsModel;

	public StAgency_BaseInfoUpdateController() throws Exception {
		// 省份下拉框列表
		provincelist = selBll.getProvinceName();
		// 客服下拉框列表
		clientList = selBll.getClient();
		// 基本信息
		cabaModel = selBll.getCoAgencyBaseModel(coab_id);
		// 服务信息
		cabsModel = selBll.getCoAgencyBaseServiceModel(coab_id, 2);
	}

	// 根据省份查询城市
	@Command("selCity")
	@NotifyChange("citylist")
	public void selCity(@BindingParam("contact") String province,
			@BindingParam("cb") Combobox cb) throws Exception {
		cb.setValue("");
		citylist = selBll.getCityName(province);
	}

	// 修改基本信息
	@Command("upBase")
	public void upBase() {
		try {
			// 检测基本信息
			String checkMessage = checkBase();
			if ("".equals(checkMessage)) {
				BaseInfo_OperateBll opBll = new BaseInfo_OperateBll();
				// 修改数据
				opBll.UpBaseInfo(cabaModel, cabsModel, UserInfo.getUsername());
				Messagebox.show("机构信息，修改成功。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show(checkMessage, "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			Messagebox.show("机构信息，修改出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 检测基本信息
	private String checkBase() {
		String message = "";
		try {
			if (cabaModel.getCoab_shortname() == null
					|| "".equals(cabaModel.getCoab_shortname())) {
				message = "请输入委托机构简称。";
			} else if (cabaModel.getCoab_province() == null
					|| "".equals(cabaModel.getCoab_province())) {
				message = "请选择委托机构所属省份。";
			} else if (cabaModel.getCoab_city() == null
					|| "".equals(cabaModel.getCoab_city())) {
				message = "请选择委托机构所属城市。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "委托机构，修改出错。";
		}
		return message;
	}

	public List<String> getProvincelist() {
		return provincelist;
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public List<String> getClientList() {
		return clientList;
	}

	public CoAgencyBaseModel getCabaModel() {
		return cabaModel;
	}

	public void setCabaModel(CoAgencyBaseModel cabaModel) {
		this.cabaModel = cabaModel;
	}

	public CoAgencyBaseServiceModel getCabsModel() {
		return cabsModel;
	}
}
