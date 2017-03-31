package Controller.CoAgency;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Model.CoAgencyLinkmanModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.CoAgency.BaseInfo_OperateBll;
import bll.CoAgency.BaseInfo_SelBll;

public class StAgency_BaseInfoAddController {
	private BaseInfo_SelBll selBll = new BaseInfo_SelBll();
	private List<String> provincelist; // 省份
	private List<String> citylist; // 城市
	private List<String> clientList = new ArrayList<String>(); // 客服
	private List<CoAgencyBaseModel> agencyList;// 机构列表
	private CoAgencyBaseModel cabaModel;
	private CoAgencyBaseServiceModel cabsModel;

	private List<CoAgencyBaseModel> winAgencyList;
	private CoAgencyBaseModel winCabaModel;
	private String username;
	private boolean readVis;

	public StAgency_BaseInfoAddController() throws Exception {
		// 省份下拉框列表
		provincelist = selBll.getProvinceName();
		// 客服下拉框列表
		clientList = selBll.getClient();
		// 基本信息
		winCabaModel = cabaModel = new CoAgencyBaseModel();
		// 服务信息
		cabsModel = new CoAgencyBaseServiceModel();
		// 机构列表
		winAgencyList = agencyList = selBll.getAgency(2);

		username = UserInfo.getUsername();
		cabsModel.setCoas_client(username);
		readVis = false;
	}

	// 查询机构基本信息
	@Command("selAgency")
	public void selAgencyList(@BindingParam("coab_name") String coab_name,
			@BindingParam("cbSerCity") Chosenbox cbSerCity) {
		try {
			CoAgencyBaseModel m = selBll.getCoAgencyBaseModel(coab_name);
			if (m == null) {
				winCabaModel = cabaModel;
				winCabaModel.setCoab_name(coab_name);
				readVis = false;
				BindUtils.postNotifyChange(null, null, this, "winCityDisList");
			} else {
				winCabaModel = m;
				readVis = true;
				if (m.getCoab_city() != null && !"".equals(m.getCoab_city())) {
					citylist = null;
					BindUtils.postNotifyChange(null, null, this, "citylist");
				}
			}
			BindUtils.postNotifyChange(null, null, this, "winCabaModel");
			BindUtils.postNotifyChange(null, null, this, "readVis");
			BindUtils.postNotifyChange(null, null, this, "winProvincelist");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据省份查询城市
	@Command("selCity")
	@NotifyChange("citylist")
	public void selCity(@BindingParam("contact") String province,
			@BindingParam("cb") Combobox cb) throws Exception {
		cb.setValue("");
		citylist = selBll.getCityName(province);
	}

	// 查询是否存在该机构
	@Command("checkAg")
	public void checkAg(@BindingParam("coab_name") String coab_name) {
		if (coab_name == null || "".equals(coab_name)) {
			return;
		} else {
			for (CoAgencyBaseModel m : agencyList) {
				if (coab_name.equals(m.getCoab_name())) {
					if (m.getCoas_id() == 0) {
						winCabaModel = m;
						BindUtils.postNotifyChange(null, null, this,
								"winCabaModel");
					} else {
						Messagebox.show("已存在该委托机构，无需新增。", "操作提示",
								Messagebox.OK, Messagebox.INFORMATION);
					}
				}
			}
		}
	}

	// 查询机构列表
	@Command("selAgencyList")
	public void selAgencyList(@BindingParam("coab_name") String coab_name) {
		winAgencyList = new ArrayList<CoAgencyBaseModel>();
		winAgencyList.add(new CoAgencyBaseModel());
		if (coab_name == null || "".equals(coab_name)) {
			return;
		} else {
			for (CoAgencyBaseModel m : agencyList) {
				if (m.getCoab_name().contains(coab_name)) {
					winAgencyList.add(m);
				}
			}
		}
		BindUtils.postNotifyChange(null, null, this, "winAgencyList");
	}

	// 新增基本信息
	@Command("addBase")
	public void addBase(@BindingParam("cbSerCity") Chosenbox cbSerCity,
			@BindingParam("gdLinkman") Grid gdLinkman,
			@BindingParam("win") Window win) {
		try {
			// 检测基本信息
			String checkMessage = checkBase();
			if ("".equals(checkMessage)) {
				BaseInfo_OperateBll opBll = new BaseInfo_OperateBll();
				// 获取联系人
				List<CoAgencyLinkmanModel> linkList = getLinkman(gdLinkman);
				// 添加数据
				String[] message = opBll.AddStBase(winCabaModel, cabsModel,
						linkList, username);
				if ("1".equals(message[0])) {
					// win.detach();
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					Executions
							.sendRedirect("/CoAgency/StAgency_BaseInfoAdd.zul");
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show(checkMessage, "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			Messagebox.show("新增受托机构出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 检测基本信息
	private String checkBase() {
		String message = "";
		try {
			if (winCabaModel.getCoab_name() == null
					|| "".equals(winCabaModel.getCoab_name())) {
				message = "请输入受托机构名称。";
			} else if (winCabaModel.getCoab_shortname() == null
					|| "".equals(winCabaModel.getCoab_shortname())) {
				message = "请输入委托机构简称。";
			} else if (winCabaModel.getCoab_province() == null
					|| "".equals(winCabaModel.getCoab_province())) {
				message = "请选择受托机构所属省份。";
			} else if (winCabaModel.getCoab_city() == null
					|| "".equals(winCabaModel.getCoab_city())) {
				message = "请选择受托机构所属城市。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "受托机构，新增出错。";
		}
		return message;
	}

	// 获取联系人列表
	private List<CoAgencyLinkmanModel> getLinkman(Grid gdLinkman) {
		List<CoAgencyLinkmanModel> list = new ArrayList<CoAgencyLinkmanModel>();
		List<Component> rows = gdLinkman.getRows().getChildren();
		Grid gd;
		String name;
		for (int i = 1; i < rows.size(); i++) {
			try {
				gd = (Grid) rows.get(i).getChildren().get(0).getChildren()
						.get(0);
				name = ((Textbox) gd.getRows().getChildren().get(1)
						.getChildren().get(1).getChildren().get(0)).getValue();
				if (name == null || "".equals(name)) {
					// 未写名称的不录入
					break;
				} else {
					list.add(setLinkmanModel(gd, name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 写入联系人信息
	private CoAgencyLinkmanModel setLinkmanModel(Grid gd, String name) {
		// 获取联系人数据
		CoAgencyLinkmanModel m = new CoAgencyLinkmanModel();
		try {
			// 联系人组名
			m.setCali_linkman(((Textbox) gd.getRows().getChildren().get(0)
					.getChildren().get(2).getChildren().get(0)).getValue());

			// 是否重要联系人
			if (((Checkbox) gd.getRows().getChildren().get(0).getChildren()
					.get(4).getChildren().get(0)).isChecked()) {
				m.setCali_vip(1);
			} else {
				m.setCali_vip(0);
			}

			// 雇员姓名
			m.setCali_name(name);

			// 英文名
			m.setCali_ename(((Textbox) gd.getRows().getChildren().get(1)
					.getChildren().get(3).getChildren().get(0)).getValue());

			// 手机
			m.setCali_mobile(((Textbox) gd.getRows().getChildren().get(2)
					.getChildren().get(1).getChildren().get(0)).getValue());

			// 座机
			m.setCali_tel(((Textbox) gd.getRows().getChildren().get(2)
					.getChildren().get(3).getChildren().get(0)).getValue());

			// 职位
			m.setCali_job(((Textbox) gd.getRows().getChildren().get(3)
					.getChildren().get(1).getChildren().get(0)).getValue());

			// 传真
			m.setCali_fax(((Textbox) gd.getRows().getChildren().get(3)
					.getChildren().get(3).getChildren().get(0)).getValue());

			// 生日
			if (((Datebox) gd.getRows().getChildren().get(4).getChildren()
					.get(1).getChildren().get(0)).getValue() == null) {
				m.setCali_birth("");
			} else {
				m.setCali_birth(DateStringChange.DatetoSting(((Datebox) gd
						.getRows().getChildren().get(4).getChildren().get(1)
						.getChildren().get(0)).getValue(), "yyyy-MM-dd"));
			}

			// 兴趣爱好
			m.setCali_hobby(((Textbox) gd.getRows().getChildren().get(4)
					.getChildren().get(3).getChildren().get(0)).getValue());

			// Email
			m.setCali_email(((Textbox) gd.getRows().getChildren().get(5)
					.getChildren().get(1).getChildren().get(0)).getValue());

			// 联系地址
			m.setCali_address(((Textbox) gd.getRows().getChildren().get(5)
					.getChildren().get(3).getChildren().get(0)).getValue());

			// 个性描述
			m.setCali_personality(((Textbox) gd.getRows().getChildren().get(6)
					.getChildren().get(1).getChildren().get(0)).getValue());

			// 备注
			m.setCali_remark(((Textbox) gd.getRows().getChildren().get(6)
					.getChildren().get(3).getChildren().get(0)).getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
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

	public CoAgencyBaseModel getWinCabaModel() {
		return winCabaModel;
	}

	public void setWinCabaModel(CoAgencyBaseModel winCabaModel) {
		this.winCabaModel = winCabaModel;
	}

	public List<CoAgencyBaseModel> getWinAgencyList() {
		return winAgencyList;
	}

	public boolean isReadVis() {
		return readVis;
	}

}
