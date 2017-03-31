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

import bll.EmPay.EmPa_SelectBll;
import bll.SmsMessage.SmsGroupBll;

import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Util.DateStringChange;
import Util.UserInfo;

public class Empa_DMListController {
	private final EmPa_SelectBll bll = new EmPa_SelectBll();
	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private EmPayModel m = new EmPayModel();
	private String sql = "";

	public Empa_DMListController() {
		// 部门经理只能查询自己部门添加的数据
		sql = "and empa_state=2 and empa_addname in(select log_name from Login "
				+ "where dep_id=" + UserInfo.getDepID() + ")";
		m.setEmpa_state(2);
		m.setDep_id(Integer.parseInt(UserInfo.getDepID()));
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

	// 部门经理审批
	@Command
	@NotifyChange("list")
	public void singleUpdate(@BindingParam("model") EmPayModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmPa_DMBatchApproval.zul", null, map);
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
