package Controller.EmPay;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Util.DateStringChange;
import bll.EmPay.EmPa_SelectBll;

public class Empa_InvoiceListController {
	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private EmPayModel m = new EmPayModel();
	private String sql = " and empa_state in(41,42)";

	public Empa_InvoiceListController() {
		m.setState_name("票据待审核");
		list = bll.getEmPayInfoList(sql);
	}

	@Command
	@NotifyChange("list")
	public void search(@BindingParam("ownmonth") Date ownmonth)
			throws NumberFormatException, ParseException {
		if (ownmonth != null) {
			m.setOwnmonth(Integer.parseInt(DateStringChange
					.Stringtoownmonth(ownmonth)));
		}else {
			m.setOwnmonth(null);
		}
		list = bll.getEmPayInfoList(m);
	}

	// 票据审批
	@Command
	@NotifyChange("list")
	public void singleUpdate(@BindingParam("model") EmPayModel model) {

		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"Empa_InvoiceApproval.zul", null, map);
		window.doModal();
		list = bll.getEmPayInfoList(sql);
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
}
