/**
 * @Classname BaseInfo_AddController
 * @ClassInfo 委托机构新增控制类
 * @author 李文洁
 * @Date 2014-9-29
 */
package Controller.CoAgency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Model.CoAgencyLinkmanModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.CoAgency.BaseInfo_OperateBll;
import bll.CoAgency.BaseInfo_SelBll;

public class WtAgency_BaseInfoAddController {
	private BaseInfo_SelBll selBll = new BaseInfo_SelBll();
	private List<String> provincelist; // 省份
	private List<String> citylist; // 城市
	private List<String> winProvincelist; // 省份
	private List<String> clientList = new ArrayList<String>(); // 客服
	private List<String> winCityDisList; // 可操作城市
	private CoAgencyBaseModel cabaModel;
	private CoAgencyBaseServiceModel cabsModel;

	private CoAgencyBaseModel winCabaModel;
	private String username;
	private Map<String, CoAgencyBaseCityRelModel> cityMap;
	private int citySum;
	private boolean readVis;
	private String existCity;

	public WtAgency_BaseInfoAddController() throws Exception {
		// 省份下拉框列表
		winProvincelist = provincelist = selBll.getProvinceName();
		// 客服下拉框列表
		clientList = selBll.getClient();
		// 基本信息
		winCabaModel = cabaModel = new CoAgencyBaseModel();
		// 服务信息
		cabsModel = new CoAgencyBaseServiceModel();
		// 可操作城市列表
		getCityDisList();
		username = UserInfo.getUsername();
		cabsModel.setCoas_client(username);
		cityMap = new HashMap<String, CoAgencyBaseCityRelModel>();
		citySum = 0;

		readVis = false;
	}

	// 查询可操作城市列表
	private void getCityDisList() {
		try {
			winCityDisList = selBll.getCityName();
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
				winProvincelist = provincelist;
				getCityDisList();
				BindUtils.postNotifyChange(null, null, this, "winCityDisList");
			} else {
				winCabaModel = m;
				selExistCity(m.getCoab_id(), cbSerCity);
				readVis = true;
				if (m.getCoab_city() != null && !"".equals(m.getCoab_city())) {
					winProvincelist = null;
					citylist = null;
					BindUtils.postNotifyChange(null, null, this, "citylist");
				} else {
					winProvincelist = provincelist;
				}
			}
			BindUtils.postNotifyChange(null, null, this, "winCabaModel");
			BindUtils.postNotifyChange(null, null, this, "readVis");
			BindUtils.postNotifyChange(null, null, this, "winProvincelist");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询已添加的服务城市
	private void selExistCity(int coab_id, Chosenbox cbSerCity) {
		try {
			List<String> existCityList = selBll.getCityDisList(coab_id);
			existCity = "";
			getCityDisList();
			if (existCityList.size() > 0) {
				for (String city : existCityList) {
					existCity += " " + city;
					winCityDisList.remove(city);
				}
				cityMap.clear();
				cbSerCity.clearSelection();
				BindUtils.postNotifyChange(null, null, this, "existCity");
				BindUtils.postNotifyChange(null, null, this, "cityMap");
			}
			BindUtils.postNotifyChange(null, null, this, "winCityDisList");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				String[] message = opBll.AddBase(winCabaModel, cabsModel,
						cityMap, linkList, username);
				if ("1".equals(message[0])) {
					// win.detach();
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					Executions
							.sendRedirect("/CoAgency/WtAgency_BaseInfoAdd.zul");
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show(checkMessage, "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			Messagebox.show("新增委托机构出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 选择服务城市
	@Command("addCity")
	@NotifyChange("cityMap")
	public void addCity(@BindingParam("city") Set<String> city) {
		try {
			int s = city.size();
			if (s > citySum) {
				for (String str : city) {
					if (!cityMap.containsKey(str)) {
						CoAgencyBaseCityRelModel m = new CoAgencyBaseCityRelModel();
						cityMap.put(str, m);
					}
				}
			} else if (s < citySum) {
				List<String> list = new ArrayList<String>();
				// 遍历Map找出需删除的Key,用List记录。
				for (String str : cityMap.keySet()) {
					if (!city.contains(str)) {
						list.add(str);
					}
				}
				// 根据List中的记录，移除Map中的Key。(在遍历Map中移除会报错，以List记录后，在此处做移除。)
				for (String str : list) {
					cityMap.remove(str);
				}
			}
			citySum = s;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检测基本信息
	private String checkBase() {
		String message = "";
		try {
			if (winCabaModel.getCoab_name() == null
					|| "".equals(winCabaModel.getCoab_name())) {
				message = "请输入委托机构名称。";
			} else if (winCabaModel.getCoab_shortname() == null
					|| "".equals(winCabaModel.getCoab_shortname())) {
				message = "请输入委托机构简称。";
			}else if (winCabaModel.getCoab_province() == null
					|| "".equals(winCabaModel.getCoab_province())) {
				message = "请选择委托机构所属省份。";
			} else if (winCabaModel.getCoab_city() == null
					|| "".equals(winCabaModel.getCoab_city())) {
				message = "请选择委托机构所属城市。";
			} else if (cityMap.size() == 0) {
				message = "请选择服务城市。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "委托机构，新增出错。";
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

	public List<String> getWinProvincelist() {
		return winProvincelist;
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

	public List<String> getWinCityDisList() {
		return winCityDisList;
	}

	public CoAgencyBaseModel getWinCabaModel() {
		return winCabaModel;
	}

	public void setWinCabaModel(CoAgencyBaseModel winCabaModel) {
		this.winCabaModel = winCabaModel;
	}

	public boolean isReadVis() {
		return readVis;
	}

	public Map<String, CoAgencyBaseCityRelModel> getCityMap() {
		return cityMap;
	}

	public String getExistCity() {
		return existCity;
	}

}
