package Controller.EmPay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Util.DateStringChange;
import bll.EmPay.EmPa_SelectBll;
import bll.SmsMessage.SmsGroupBll;

public class EmPa_FinanceListController {
	private final EmPa_SelectBll bll = new EmPa_SelectBll();
	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private EmPayModel m = new EmPayModel();
	private String sql = " and ((empa_number like '报销%' and empa_state =5) or (empa_number like '个税%' and empa_state in(41,42)))";

	public EmPa_FinanceListController() {
		m.setEmpa_state(5);
		list = bll.getEmPayInfoList(sql);
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
		m.setState_name("财务待审核");
		list = bll.getEmPayInfoList(m);
	}

	// 财务审批
	@Command
	@NotifyChange("list")
	public void singleUpdate(@BindingParam("model") EmPayModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmPa_FinanceBatchApproval.zul", null, map);
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
