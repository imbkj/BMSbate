package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmbaseModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmPay.EmPa_OperateBll;
import bll.EmPay.EmPa_TaskService;
import bll.EmPay.EmPa_TaskServiceImpl;

public class EmPa_BatchAddController {
	private List<EmbaseModel> lists = new ListModelList<EmbaseModel>();
	private String nextrole;

	private List<EmbaseModel> bxList = new ArrayList<EmbaseModel>();// 存放报销的数据
	private List<EmbaseModel> taxList = new ArrayList<EmbaseModel>();// 存放个税的数据
	private EmPa_OperateBll bll = new EmPa_OperateBll();
	private Integer step = 2;

	@SuppressWarnings("unchecked")
	public EmPa_BatchAddController() {
		if (Executions.getCurrent().getArg().get("l") != null) {
			lists = (List<EmbaseModel>) Executions.getCurrent().getArg()
					.get("l");
		}
		if (UserInfo.getDepID().equals("9")) {
			step = 3;
			nextrole="部门经理";
		}else {
			nextrole="客户经理";
		}
		
	}

	@Command
	public void submit(@BindingParam("win") Window win) {
		String error = isComplete();
		if (error != "") {
			Messagebox.show(error, "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (nextrole == null || nextrole.equals("")) {
			Messagebox.show("请选择下一步角色", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		difference();
		int k = 0;

		
		String number = DateStringChange.DatetoSting(new Date(),
				"yyyyMMddhhmmss");
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);


		if (bxList.size() > 0) {
			
			Object[] obj = {"新增",bxList,"报销" + number};
			String[] str = wfs.AddTaskToNext(obj, "个人支付", "报销" + number, 124,
					UserInfo.getUsername(), "", 0, "");
			if (str[0].equals("1")) {
				k=1;
				if (step.equals(3)) {
					Object[] o = {"跳转部门经理","报销" + number,str[3]};
					wfs.SkipToN(o, Integer.valueOf(str[2]), step, "系统", "", 0, "");	
				}
			}
		}
		if (taxList.size() > 0) {
		
			Object[] obj = {"新增",taxList,"个税" + number};
			String[] str = wfs.AddTaskToNext(obj, "个人支付", "个税" + number, 125,
					UserInfo.getUsername(), "", 0, "");
			if (str[0].equals("1")) {
				k++;
				if (step.equals(3)) {
					Object[] o = {"跳转部门经理","个税" + number,str[3]};
					wfs.SkipToN(o, Integer.valueOf(str[2]), step, "系统", "", 0, "");	
				}
			}
		}
		if (k > 0) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 把个税和报销的数据分开
	private void difference() {
		if (bxList.size() > 0) {
			bxList.clear();
		}
		if (taxList.size() > 0) {
			taxList.clear();
		}
		for (EmbaseModel m : lists) {
			if (m.getEmba_paytype().equals("报销")) {
				bxList.add(m);
			} else {
				taxList.add(m);
			}
		}
	}

	// 检查列表的数据是否完整
	private String isComplete() {
		String str = "";
		for (EmbaseModel m : lists) {
			if (m.getOwnmonth() == null) {
				str = "账单所属期为空";
				break;
			}
			if (m.getOwnmonthend() == null) {
				str = "账单所属期为空";
				break;
			}
			
			if (m.getEmba_ba_name() == null || m.getEmba_ba_name().equals("")) {
				str = "帐户名为空";
				break;
			}
			if (m.getEmba_fee() == null) {
				str = "付款金额为空";
				break;
			}
			if (m.getEmba_class() == null || m.getEmba_class().equals("")) {
				str = "款项类别为空";
				break;
			}
			if (m.getEmba_payclass() == null || m.getEmba_payclass().equals("")) {
				str = "支付方式为空";
				break;
			}else {
				if (m.getEmba_payclass().equals("银行支付")) {
					if (m.getEmba_gz_account() == null
							|| m.getEmba_gz_account().equals("")) {
						str = "收款银行账号为空";
						break;
					}
					if (m.getEmba_gz_bank() == null || m.getEmba_gz_bank().equals("")) {
						str = "收款开户行为空";
						break;
					}
				}
			}
			if (m.getEmba_paytype() == null || m.getEmba_paytype().equals("")) {
				str = "支付形式为空";
				break;
			} else {
				if (m.getEmba_paytype().equals("个税发放")) {
					if (!m.getEmba_payclass().equals("银行支付")) {
						str = "个税发放必须通过银行支付";
						break;
					}
				}
			}
			if (m.getEmba_paymenttype() == null
					|| m.getEmba_paymenttype().equals("")) {
				str = "收款方式为空";
				break;
			}
			if (bll.empayRepeat(m)) {
				str = m.getCoba_shortname() + "," + m.getEmba_name()
						+ ",已有支付信息";
				break;
			}

		}
		return str;
	}

	// 复制所属期
	@Command("copyown")
	public void copyown(@BindingParam("grid") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Integer cellNum = 3;
		Hlayout hlayout = (Hlayout) gd.getCell(num, cellNum).getChildren()
				.get(0);
		Datebox dbx1 = (Datebox) hlayout.getChildren().get(0);
		Datebox dbx1end = (Datebox) hlayout.getChildren().get(2);
		if (dbx1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, cellNum) != null) {
					Hlayout ht = (Hlayout) gd.getCell(i, cellNum).getChildren()
							.get(0);
					Datebox dbx2 = (Datebox) ht.getChildren().get(0);
					Datebox dbx2end = (Datebox) ht.getChildren().get(2);
					dbx2.setValue(dbx1.getValue());
					dbx2end.setValue(dbx1end.getValue());
					Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren()
							.get(0);
					EmbaseModel m = ck.getValue();
					m.setOwnmonth(dbx1.getValue());
					m.setOwnmonthend(dbx2end.getValue());
				}
			}
		}
	}

	// 复制款项类别
	@Command("copyclass")
	public void copyclass(@BindingParam("grid") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Integer cellNum = 8;
		Combobox dbx1 = (Combobox) gd.getCell(num, cellNum).getChildren()
				.get(0);
		if (dbx1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, cellNum) != null) {
					Combobox dbx2 = (Combobox) gd.getCell(i, cellNum)
							.getChildren().get(0);
					dbx2.setValue(dbx1.getValue());
					Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren()
							.get(0);
					EmbaseModel m = ck.getValue();
					m.setEmba_class(dbx1.getValue());
				}
			}
		}
	}

	// 复制付款金额
	@Command("copyfee")
	public void copyfee(@BindingParam("grid") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Integer cellNum = 7;
		Decimalbox dbx1 = (Decimalbox) gd.getCell(num, cellNum).getChildren()
				.get(0);
		if (dbx1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, cellNum) != null) {
					Decimalbox dbx2 = (Decimalbox) gd.getCell(i, cellNum)
							.getChildren().get(0);
					dbx2.setValue(dbx1.getValue());
					Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren()
							.get(0);
					EmbaseModel m = ck.getValue();
					m.setEmba_fee(dbx1.getValue());
				}
			}
		}
	}

	// 复制支付方式
	@Command("copypayclass")
	public void copypayclass(@BindingParam("grid") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Integer cellNum = 9;
		Combobox dbx1 = (Combobox) gd.getCell(num, cellNum).getChildren()
				.get(0);
		if (dbx1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, cellNum) != null) {
					Combobox dbx2 = (Combobox) gd.getCell(i, cellNum)
							.getChildren().get(0);
					dbx2.setValue(dbx1.getValue());
					Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren()
							.get(0);
					EmbaseModel m = ck.getValue();
					m.setEmba_payclass(dbx1.getValue());
				}
			}
		}
	}

	// 复制支付形式
	@Command("copypaytype")
	public void copypaytype(@BindingParam("grid") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Integer cellNum = 10;
		Combobox dbx1 = (Combobox) gd.getCell(num, cellNum).getChildren()
				.get(0);
		if (dbx1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, cellNum) != null) {
					Combobox dbx2 = (Combobox) gd.getCell(i, cellNum)
							.getChildren().get(0);
					dbx2.setValue(dbx1.getValue());
					Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren()
							.get(0);
					EmbaseModel m = ck.getValue();
					m.setEmba_paytype(dbx1.getValue());
				}
			}
		}
	}

	// 复制收款方式
	@Command("copypaymenttype")
	public void copypaymenttype(@BindingParam("grid") Grid gd,
			@BindingParam("rowIndex") Integer num) {
		Integer cellNum = 11;
		Combobox dbx1 = (Combobox) gd.getCell(num, cellNum).getChildren()
				.get(0);
		if (dbx1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, cellNum) != null) {
					Combobox dbx2 = (Combobox) gd.getCell(i, cellNum)
							.getChildren().get(0);
					dbx2.setValue(dbx1.getValue());
					Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren()
							.get(0);
					EmbaseModel m = ck.getValue();
					m.setEmba_paymenttype(dbx1.getValue());
				}
			}
		}
	}

	// 取消
	@Command
	@NotifyChange("lists")
	public void cancelpay(@BindingParam("model") EmbaseModel model) {
		lists.remove(model);
	}

	public List<EmbaseModel> getLists() {
		return lists;
	}

	public void setLists(List<EmbaseModel> list) {
		this.lists = list;
	}

	public String getNextrole() {
		return nextrole;
	}

	public void setNextrole(String nextrole) {
		this.nextrole = nextrole;
	}
}
