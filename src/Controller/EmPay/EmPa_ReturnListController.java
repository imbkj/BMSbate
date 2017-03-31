package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

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
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmPay.EmPa_SelectBll;
import bll.EmPay.EmPa_TaskServiceImpl;

public class EmPa_ReturnListController {

	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private List<EmPayModel> plist = new ArrayList<EmPayModel>();
	private EmPayModel mo = new EmPayModel();
	private EmPayTaskListModel tlm;
	private String sql = " and empa_state =9";

	private Window win;

	public EmPa_ReturnListController() {
		mo.setEmpa_state(9);
		plist = bll.getEmPayInfoList(sql);
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	@NotifyChange("plist")
	public void search() throws WrongValueException, NumberFormatException,
			ParseException {
		Datebox db = (Datebox) win.getFellow("ownmonth");
		if (db.getValue() != null) {
			mo.setOwnmonth(Integer.parseInt(DateStringChange
					.Stringtoownmonth(db.getValue())));
		} else {
			mo.setOwnmonth(null);
		}
		plist = bll.getEmPayInfoList(mo);
	}

	// 财务修改支付信息
	@Command
	@NotifyChange("plist")
	public void submit(@BindingParam("model") EmPayModel model) {
		EmPayTaskListModel tm = bll.getEmPayTaskListByNumber(model
				.getEmpa_number());

		Map map = new HashMap<>();
		map.put("model", tm);
		Window window = (Window) Executions.createComponents(
				"EmPa_ReturnApproval.zul", null, map);
		window.doModal();
		plist = bll.getEmPayInfoList(sql);
	}

	// 终止任务
	@Command
	@NotifyChange("plist")
	public void stop(@BindingParam("model") EmPayModel model) {
		tlm = bll.getEmPayTaskListByNumber(model.getEmpa_number());
		Messagebox.show("确认终止任务?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {

							WfBusinessService wfbs = new EmPa_TaskServiceImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "1", tlm.getPatk_id(),
									tlm.getPatk_number() };
							String[] s = wfs.StopTask(obj,
									tlm.getPatk_tapr_id(),
									UserInfo.getUsername(), "");
							if (s[0].equals("1")) {
								Messagebox.show(s[1], "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								plist = bll.getEmPayInfoList(mo);
							} else {
								Messagebox.show(s[1], "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<EmPayModel> getPlist() {
		return plist;
	}

	public void setPlist(List<EmPayModel> plist) {
		this.plist = plist;
	}

	public EmPayModel getMo() {
		return mo;
	}

	public void setMo(EmPayModel mo) {
		this.mo = mo;
	}

}
