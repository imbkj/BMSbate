package Controller.EmPay;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.DepartmentModel;
import Model.EmPayModel;
import Util.DateStringChange;
import bll.EmPay.EmPa_SelectBll;

public class Empa_InfoListController {
	private EmPa_SelectBll bll = new EmPa_SelectBll();

	private List<EmPayModel> list = null;
	private List<DepartmentModel> lgList = new ListModelList<>();
	private EmPayModel m = new EmPayModel();

	public Empa_InfoListController() {
		m.setState_name("客户经理待审核");
		m.setEmpa_state(1);
		list = bll.getEmPayInfoList(m);
		lgList.add(null);
		lgList = bll.getDeptList();
	}

	@Command
	@NotifyChange("list")
	public void search(@BindingParam("ownmonth") Date ownmonth)
			throws NumberFormatException, ParseException {

		if (ownmonth != null) {
			m.setOwnmonth(Integer.parseInt(DateStringChange
					.Stringtoownmonth(ownmonth)));
		} else {
			m.setOwnmonth(null);
		}
		m.setEmpa_state(null);
		list = bll.getEmPayInfoList(m);
	}

	@Command
	public void detail(@BindingParam("model") EmPayModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents("EmPa_Info.zul",
				null, map);
		window.doModal();
	}

	public List<EmPayModel> getList() {
		return list;
	}

	public void setList(List<EmPayModel> list) {
		this.list = list;
	}

	public EmPayModel getM() {
		return m;
	}

	public void setM(EmPayModel m) {
		this.m = m;
	}

	public List<DepartmentModel> getLgList() {
		return lgList;
	}

	public void setLgList(List<DepartmentModel> lgList) {
		this.lgList = lgList;
	}

}
