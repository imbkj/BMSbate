package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmPayBackLogModel;
import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Model.empayTaskModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmPay.EmPa_OperateBll;
import bll.EmPay.EmPa_SelectBll;
import bll.EmPay.EmPa_TaskServiceImpl;

public class EmPa_ReturnApprovalController {
	private List<EmPayModel> plist = new ListModelList<>();
	private EmPayTaskListModel model = new EmPayTaskListModel();
	private List<EmPayBackLogModel> blist = null;

	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private EmPa_OperateBll obll = new EmPa_OperateBll();
	private String paytype = "";

	private Window wd;
	private Integer step = 2;

	public EmPa_ReturnApprovalController() {
		if (Executions.getCurrent().getArg().get("model") != null) {
			model = (EmPayTaskListModel) Executions.getCurrent().getArg()
					.get("model");

			plist = bll.getEmPayList(" and empa_number='"
					+ model.getPatk_number() + "'");
			if (plist.size() > 0) {
				paytype = plist.get(0).getEmpa_paytype();
			}
			blist = bll.getEmPayBackList(model.getPatk_number());
		}
		if (UserInfo.getDepID().equals("9")) {
			step = 3;
		}
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		wd = w;
	}

	@Command
	public void checkAll(@BindingParam("a") Checkbox cb) {

		for (EmPayModel m : plist) {
			m.setChecked(cb.isChecked());
			BindUtils.postNotifyChange(null, null, m, "checked");

		}
	}

	@Command
	public void copy(@BindingParam("a") Image img,
			@BindingParam("b") String type, @BindingParam("c") EmPayModel model) {
		Grid gd = (Grid) wd.getFellow("gd");
		Integer i = plist.indexOf(model);
		Cell cell;
		switch (type) {
		case "date":

			cell = (Cell) gd.getCell(i, 3);
			Datebox db1 = (Datebox) cell.getChildren().get(0).getChildren()
					.get(0);
			Datebox db2 = (Datebox) cell.getChildren().get(0).getChildren()
					.get(2);
			for (int j = i; j < plist.size(); j++) {
				if ((j + 1) < plist.size()) {
					plist.get(j + 1).setOwnmonth2(db1.getValue());
					plist.get(j + 1).setOwnmonthend2(db2.getValue());
					BindUtils.postNotifyChange(null, null, plist.get(j + 1),
							"ownmonth2");
					BindUtils.postNotifyChange(null, null, plist.get(j + 1),
							"ownmonthend2");
				}

			}
			break;
		case "fee":
			cell = (Cell) gd.getCell(i, 7);
			Decimalbox db = (Decimalbox) cell.getChildren().get(0);
			for (int j = i; j < plist.size(); j++) {
				if ((j + 1) < plist.size()) {
					plist.get(j + 1).setEmpa_fee(db.getValue());
					BindUtils.postNotifyChange(null, null, plist.get(j + 1),
							"empa_fee");

				}

			}
			break;
		case "class":
			cell = (Cell) gd.getCell(i, 8);
			Combobox cbClass = (Combobox) cell.getChildren().get(0);
			for (int j = i; j < plist.size(); j++) {
				if ((j + 1) < plist.size()) {
					plist.get(j + 1).setEmpa_class(cbClass.getValue());
					BindUtils.postNotifyChange(null, null, plist.get(j + 1),
							"empa_class");
				}

			}
			break;
		case "payclass":
			cell = (Cell) gd.getCell(i, 9);
			Combobox cbFee = (Combobox) cell.getChildren().get(0);
			for (int j = i; j < plist.size(); j++) {
				if ((j + 1) < plist.size()) {
					plist.get(j + 1).setEmpa_payclass(cbFee.getValue());
					BindUtils.postNotifyChange(null, null, plist.get(j + 1),
							"empa_payclass");

				}

			}
			break;
		case "paytype":
			cell = (Cell) gd.getCell(i, 10);
			Combobox cbpaytype = (Combobox) cell.getChildren().get(0);
			for (int j = i; j < plist.size(); j++) {
				if ((j + 1) < plist.size()) {
					plist.get(j + 1).setEmpa_paytype(cbpaytype.getValue());
					BindUtils.postNotifyChange(null, null, plist.get(j + 1),
							"empa_paytype");

				}

			}
			break;
		case "paymenttype":
			cell = (Cell) gd.getCell(i, 11);
			Combobox cbpaymenttype = (Combobox) cell.getChildren().get(0);
			for (int j = i; j < plist.size(); j++) {
				if ((j + 1) < plist.size()) {
					plist.get(j + 1).setEmpa_paymenttype(
							cbpaymenttype.getValue());
					BindUtils.postNotifyChange(null, null, plist.get(j + 1),
							"empa_paymenttype");
				}
			}
			break;
		}

	}

	public boolean judge() {
		String str = "";

		for (EmPayModel m : plist) {

			if (m.getOwnmonth2() == null) {
				str = "账单所属期为空";
				break;
			} else {
				m.setOwnmonth(Integer.valueOf(DateStringChange.DatetoSting(
						m.getOwnmonth2(), "yyyyMM")));
			}
			if (m.getOwnmonthend2() == null) {
				str = "账单所属期为空";
				break;
			} else {
				m.setOwnmonthend(Integer.valueOf(DateStringChange.DatetoSting(
						m.getOwnmonthend2(), "yyyyMM")));
			}

			if (m.getEmpa_ba_name() == null || m.getEmpa_ba_name().equals("")) {
				str = "帐户名为空";
				break;
			}
			if (m.getEmpa_fee() == null) {
				str = "付款金额为空";
				break;
			}
			if (m.getEmpa_class() == null || m.getEmpa_class().equals("")) {
				str = "款项类别为空";
				break;
			}
			if (m.getEmpa_payclass() == null || m.getEmpa_payclass().equals("")) {
				str = "支付方式为空";
				break;
			} else {
				if (m.getEmpa_payclass().equals("银行支付")) {
					if (m.getEmpa_account() == null
							|| m.getEmpa_account().equals("")) {
						str = "收款银行账号为空";
						break;
					}
					if (m.getEmpa_bank() == null || m.getEmpa_bank().equals("")) {
						str = "收款开户行为空";
						break;
					}
				}
			}
			if (m.getEmpa_paytype() == null || m.getEmpa_paytype().equals("")) {
				str = "支付形式为空";
				break;
			} else {
				if (m.getEmpa_paytype().equals("个税发放")) {
					if (!m.getEmpa_payclass().equals("银行支付")) {
						str = "个税发放必须通过银行支付";
						break;
					}
				}
				if (!m.getEmpa_paytype().equals(paytype)) {
					str = "支付方式有变化,请终止任务单重新提交数据.";
					break;
				}
			}
			if (m.getEmpa_paymenttype() == null
					|| m.getEmpa_paymenttype().equals("")) {
				str = "收款方式为空";
				break;
			}

		}

		if (!str.equals("")) {
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		}

		return true;
	}

	@Command
	public void submit() {
		if (judge()) {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								WfBusinessService wfbs = new EmPa_TaskServiceImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								Object[] obj = { "重新提交", model.getPatk_id(),
										plist, model.getPatk_number() };
								String[] s = wfs.SkipToN(obj,
										model.getPatk_tapr_id(), step,
										UserInfo.getUsername(), "", 0, "");
								if (s[0].equals("1")) {
									Messagebox.show(s[1], "提示", Messagebox.OK,
											Messagebox.INFORMATION);
									wd.detach();
								} else {
									Messagebox.show(s[1], "提示", Messagebox.OK,
											Messagebox.ERROR);
								}
							}
						}
					});
		}
	}

	@Command
	@NotifyChange("plist")
	public void del() {
		boolean b = false;
		for (EmPayModel epm : plist) {
			if (epm.isChecked()) {
				b = true;
			}
		}
		if (b) {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								for (EmPayModel epm : plist) {
									if (epm.isChecked()) {
										obll.del(epm.getId());
									}
								}
							}
						}
					});
			plist = bll.getEmPayList(" and empa_number='"
					+ model.getPatk_number() + "'");
		} else {
			Messagebox.show("请选择需要删除的人员.", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public List<EmPayModel> getPlist() {
		return plist;
	}

	public void setPlist(List<EmPayModel> plist) {
		this.plist = plist;
	}

	public List<EmPayBackLogModel> getBlist() {
		return blist;
	}

	public void setBlist(List<EmPayBackLogModel> blist) {
		this.blist = blist;
	}

}
