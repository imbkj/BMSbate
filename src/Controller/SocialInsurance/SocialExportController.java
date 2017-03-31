package Controller.SocialInsurance;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Messagebox;

import Model.PubProCityModel;
import Util.FileOperate;
import bll.SocialInsurance.SocialExportBll;

public class SocialExportController {
	private SocialExportBll bll;
	private List<PubProCityModel> cityList;
	private boolean checkAll = false;
	private String checkCity = "";

	public SocialExportController() {
		bll = new SocialExportBll();
		cityList = bll.getInsuranceCityList();
	}

	// 全选
	@Command("checkAll")
	public void checkAll() {
		try {
			for (PubProCityModel m : cityList) {
				m.setCheck(checkAll);
			}
			BindUtils.postNotifyChange(null, null, this, "cityList");
		} catch (Exception e) {
			Messagebox.show("全选操作出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	// 导出数据
	@Command("exportData")
	public void exportData() {
		try {
			String cityId = setCityIdList();
			if (!"".equals(cityId)) {
				String flieName = bll.createReport(cityId);
				if (!"".equals(flieName)) {
					FileOperate.download(flieName);
				}else{
					Messagebox.show("数据处理出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请勾选需要导出的城市。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("数据导出出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();

		}
	}

	// 拼接勾选的城市编号
	private String setCityIdList() {
		String cityId = "";
		try {
			for (PubProCityModel m : cityList) {
				if (m.isCheck()) {
					cityId += "," + m.getId();
				}
			}
			if (!"".equals(cityId)) {
				cityId = cityId.substring(1, cityId.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityId;
	}

	public List<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

	public String getCheckCity() {
		return checkCity;
	}

}
