package Controller.CoProduct;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoProduct.cpd_addBll;

import Model.CoPAccountModel;
import Model.CoProductModel;
import Util.UserInfo;

public class CoProduct_AddOperateController {

	private CoProductModel m = new CoProductModel();
	// 财务科目
	private List<CoPAccountModel> accountList = new ListModelList<>();
	private CoPAccountModel accountModel = new CoPAccountModel();

	Integer daid = 0;

	public CoProduct_AddOperateController() {
		try {

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		try {
			cpd_addBll bll = new cpd_addBll();

			m = bll.getCoproductInfo(daid);
			accountList = bll.getaccount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		cpd_addBll bll = new cpd_addBll();
		if (accountModel == null) {
			Messagebox.show("请选择财务科目!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			m.setCopr_id(daid);
			m.setCopr_cpac_id(accountModel.getCpac_id());
			m.setCopr_state(m.getCopr_state() + 1);
			m.setCopr_addname(UserInfo.getUsername());
			m.setCopr_remark("");

			try {
				String[] strings = bll.updatestate(m);

				if (strings[0].equals("1")) {
					Messagebox.show(strings[1], "INFORMATION", Messagebox.OK
							| Messagebox.CANCEL, Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(strings[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public List<CoPAccountModel> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<CoPAccountModel> accountList) {
		this.accountList = accountList;
	}

	public CoPAccountModel getAccountModel() {
		return accountModel;
	}

	public void setAccountModel(CoPAccountModel accountModel) {
		this.accountModel = accountModel;
	}

	public CoProductModel getM() {
		return m;
	}

	public void setM(CoProductModel m) {
		this.m = m;
	}
}
