package Controller.EmCAFFeeInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmCAFFeeInfoModel;
import Model.HandoverPeopleModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_OperateBll;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_SelectBll;

public class EmCAFFeeInfo_UpdateAllStateController {
	private EmCAFFeeInfo_SelectBll ecfiSBll = new EmCAFFeeInfo_SelectBll();
	private EmCAFFeeInfo_OperateBll ecfiOBll = new EmCAFFeeInfo_OperateBll();
	private EmCAFFeeInfoModel ecfiM = new EmCAFFeeInfoModel();
	private DateStringChange dsc = new DateStringChange();

	private List<Integer> list_ecfi_id = (List<Integer>) Executions
			.getCurrent().getArg().get("list_ecfi_id");

	// 获取用户名
	String username = UserInfo.getUsername();
	private List<HandoverPeopleModel> wdaList = new ArrayList<HandoverPeopleModel>();
	private List<HandoverPeopleModel> csdaList = new ArrayList<HandoverPeopleModel>();

	public EmCAFFeeInfo_UpdateAllStateController() throws SQLException {
		wdaList = ecfiSBll
				.getHP(" AND hape_type='员工费用管理' AND hape_state=1 AND hape_channel like '%后道%'");// 福利部申请人下拉框数据
		csdaList = ecfiSBll
				.getHP(" AND hape_type='员工费用管理' AND hape_state=1 AND hape_channel like '%前道%'");// 客服部申请人下拉框数据
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {

		if (checkPage(win)) {

			Combobox ecfi_payment_state = (Combobox) win
					.getFellow("ecfi_payment_state");
			Combobox ecfi_loanstate = (Combobox) win
					.getFellow("ecfi_loanstate");
			Combobox ecfi_wd_applicant = (Combobox) win
					.getFellow("ecfi_wd_applicant");
			Combobox ecfi_csd_applicant = (Combobox) win
					.getFellow("ecfi_csd_applicant");
			Datebox ecfi_wd_loan_date = (Datebox) win
					.getFellow("ecfi_wd_loan_date");
			Datebox ecfi_csd_loan_date = (Datebox) win
					.getFellow("ecfi_csd_loan_date");
			Datebox ecfi_ri_date = (Datebox) win.getFellow("ecfi_ri_date");
			Datebox ecfi_csd_clearing_date = (Datebox) win
					.getFellow("ecfi_csd_clearing_date");

			// 赋值到model
			if (ecfi_payment_state.getSelectedItem() != null) {
				ecfiM.setEcfi_payment_state(String.valueOf(ecfi_payment_state
						.getSelectedItem().getLabel()));
			}
			if (ecfi_loanstate.getSelectedItem() != null) {
				ecfiM.setEcfi_loanstate(Integer.parseInt(ecfi_loanstate
						.getSelectedItem().getValue().toString()));
			}
			if (ecfi_wd_applicant.getSelectedItem() != null) {
				ecfiM.setEcfi_wd_applicant(String.valueOf(ecfi_wd_applicant
						.getSelectedItem().getLabel()));
			}
			if (ecfi_csd_applicant.getSelectedItem() != null) {
				ecfiM.setEcfi_csd_applicant(String.valueOf(ecfi_csd_applicant
						.getSelectedItem().getLabel()));
			}
			if (ecfi_wd_loan_date.getValue() != null) {
				ecfiM.setEcfi_wd_loan_date(dsc.DatetoSting(
						ecfi_wd_loan_date.getValue(), "yyyy-MM-dd"));
			}
			if (ecfi_csd_loan_date.getValue() != null) {
				ecfiM.setEcfi_csd_loan_date(dsc.DatetoSting(
						ecfi_csd_loan_date.getValue(), "yyyy-MM-dd"));
			}
			if (ecfi_ri_date.getValue() != null) {
				ecfiM.setEcfi_ri_date(dsc.DatetoSting(ecfi_ri_date.getValue(),
						"yyyy-MM-dd"));
			}
			if (ecfi_csd_clearing_date.getValue() != null) {
				ecfiM.setEcfi_csd_clearing_date(dsc.DatetoSting(
						ecfi_csd_clearing_date.getValue(), "yyyy-MM-dd"));
			}

			ecfiM.setEcfi_addname(username);

			String[] message;
			String msg;
			int j = 0;
			// 调用更新方法
			for (int i = 0; i < list_ecfi_id.size(); i++) {
				ecfiM.setEcfi_id(list_ecfi_id.get(i));
				message = ecfiOBll.ecfiUpdateState(ecfiM, 1);
				if (message[0].equals("1")) {
					j = j + 1;
				}
			}

			// 判断是否成功
			if (list_ecfi_id.size() == j) {
				msg = "批量操作成功!";
			} else {
				msg = "部分数据操作不成功!";
			}

			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		}
	}

	// 检测表单
	private boolean checkPage(Window w1) {
		boolean b = true;
		Datebox ecfi_wd_loan_date = (Datebox) w1.getFellow("ecfi_wd_loan_date");
		Datebox ecfi_csd_loan_date = (Datebox) w1
				.getFellow("ecfi_csd_loan_date");
		Datebox ecfi_ri_date = (Datebox) w1.getFellow("ecfi_ri_date");
		Datebox ecfi_csd_clearing_date = (Datebox) w1
				.getFellow("ecfi_csd_clearing_date");
		Combobox ecfi_payment_state = (Combobox) w1
				.getFellow("ecfi_payment_state");

		String payment_state = "";
		if (ecfi_payment_state.getSelectedItem() != null) {
			payment_state = ecfi_payment_state.getSelectedItem().getLabel();
		}

		if (ecfi_csd_clearing_date.getValue() != null
				&& !"已付".equals(payment_state)) {
			b = false;
			Messagebox.show("请将 付款状态 选为 已付 !", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}

		if (ecfi_wd_loan_date.getValue() != null
				&& ecfi_ri_date.getValue() != null) {
			if (ecfi_ri_date.getValue().before(ecfi_wd_loan_date.getValue())) {
				b = false;
				Messagebox.show("请将 收发票日期 必须大于 福利部借款日期!", "操作提示",
						Messagebox.OK, Messagebox.ERROR);
				return b;
			}
		}

		if (ecfi_csd_loan_date.getValue() != null
				&& ecfi_csd_clearing_date.getValue() != null) {
			if (ecfi_csd_clearing_date.getValue().before(
					ecfi_csd_loan_date.getValue())) {
				b = false;
				Messagebox.show("请将 客服部清账日期 必须大于 客服部借款日期!", "操作提示",
						Messagebox.OK, Messagebox.ERROR);
				return b;
			}
		}

		return b;
	}

	public List<HandoverPeopleModel> getWdaList() {
		return wdaList;
	}

	public void setWdaList(List<HandoverPeopleModel> wdaList) {
		this.wdaList = wdaList;
	}

	public List<HandoverPeopleModel> getCsdaList() {
		return csdaList;
	}

	public void setCsdaList(List<HandoverPeopleModel> csdaList) {
		this.csdaList = csdaList;
	}

}
