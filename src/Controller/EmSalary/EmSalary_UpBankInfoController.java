package Controller.EmSalary;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmSalaryBaseAdd_viewModel;
import Model.EmSalaryBaseSel_viewModel;
import Util.CheckString;
import Util.UserInfo;
import bll.EmSalary.EmSalary_DataOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_UpBankInfoController {
	private EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
	private EmSalary_DataOperateBll eoBll = new EmSalary_DataOperateBll();

	private EmSalaryBaseSel_viewModel esdaM; // 员工工资数据
	private EmSalaryBaseAdd_viewModel embaM; // 员工银行信息
	private int esda_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString()); // 工资数据id

	// 获取用户名
	String username = UserInfo.getUsername();

	public EmSalary_UpBankInfoController() {
		esdaM = esBll.getSalaryEmBase(esda_id);
		embaM = esBll.getEmSalaryDateAdd(esdaM.getGid(),esdaM.getCid());
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("gzbank") Textbox gzbank,
			@BindingParam("gzbankacc") Textbox gzbankacc,
			@BindingParam("wfbank") Textbox wfbank,
			@BindingParam("wfbankacc") Textbox wfbankacc,
			@BindingParam("baname") Textbox baname) {

		int chk_acc = 0;

		// 清除空格
		String gzAcc = gzbankacc.getValue().replaceAll("\\s*", "");
		String wfAcc = wfbankacc.getValue().replaceAll("\\s*", "");

		// 检查银行账号是否存在非数字
		if (!"".equals(gzAcc)) {
			if (CheckString.isNum(gzAcc) == false) {
				chk_acc = 1;
			}
		}
		if (!"".equals(wfAcc)) {
			if (CheckString.isNum(wfAcc) == false) {
				chk_acc = 1;
			}
		}

		if ((!"".equals(gzAcc) && !"".equals(gzbank.getValue()))
				|| (!"".equals(wfAcc) && !"".equals(wfbank.getValue()))) {

			if (chk_acc == 0) {
				// 将银行数据插入model
				embaM.setEmba_gz_bank(gzbank.getValue());
				embaM.setEmba_gz_account(gzAcc);
				embaM.setEmba_writeoff_bank(wfbank.getValue());
				embaM.setEmba_writeoff_account(wfAcc);
				esdaM.setEsda_ba_name(baname.getValue());

				// 调用方法
				String[] message = eoBll.upBankInfo(esdaM, embaM, username);

				// 弹出提示并跳转页面
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// //跳转页面
								win.detach();
							}
						}
					};
					// 弹出提示
					Messagebox.show(message[1], "操作提示",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION, clickListener);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				// 弹出提示
				Messagebox.show("银行账号格式有误，请检查！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			// 弹出提示
			Messagebox.show("请正确录入相应的银行信息！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public EmSalaryBaseSel_viewModel getEsdaM() {
		return esdaM;
	}

	public void setEsdaM(EmSalaryBaseSel_viewModel esdaM) {
		this.esdaM = esdaM;
	}

	public EmSalaryBaseAdd_viewModel getEmbaM() {
		return embaM;
	}

	public void setEmbaM(EmSalaryBaseAdd_viewModel embaM) {
		this.embaM = embaM;
	}

}
