/*
 * 创建人：林少斌
 * 创建时间：2013-11-29
 * 用途：公司财务信息修改页面Controller
 */
package Controller.CoBase;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoBase.CoBase_OperateBll;
import bll.CoBase.CoBase_SelectBll;
import Model.CoBaseModel;
import Util.UserInfo;

public class CoBaseCS_UpdateController {
	private CoBase_OperateBll cobaBll = new CoBase_OperateBll();
	private CoBase_SelectBll cobaSBll = new CoBase_SelectBll();
	private CoBaseModel model;
	String str = "";

	// 获取用户名
	String username = UserInfo.getUsername();
	int cid = ((CoBaseModel) Executions.getCurrent().getArg().get("model"))
			.getCid();

	public CoBaseCS_UpdateController() {
		str = " AND a.cid=" + String.valueOf(cid);

		model = cobaSBll.getCobaseeditinfo(str).get(0);
	}

	@Command("updateCoBaseCS")
	public void updateCoBaseCS(
			@BindingParam("winCoBaseCS") final Window winCoBaseCS,
			@BindingParam("coba_emfi_paydate") Combobox coba_emfi_paydate,
			@BindingParam("coba_emfi_backdate") Combobox coba_emfi_backdate,
			@BindingParam("coba_gz_paydate") Combobox coba_gz_paydate,
			@BindingParam("coba_emailgz_paydate") Combobox coba_emailgz_paydate,
			@BindingParam("coba_papergz_paydate") Combobox coba_papergz_paydate,
			@BindingParam("coba_emfics_deldate") Combobox coba_emfics_deldate,
			@BindingParam("coba_emfics_paydate") Combobox coba_emfics_paydate,
			@BindingParam("coba_emfics_backdate") Combobox coba_emfics_backdate,
			@BindingParam("coba_invoicerule") Textbox tb) {

		String[] message = cobaBll.updateCoBaseCS(cid, username,
				coba_emfi_paydate.getValue(), coba_emfi_backdate.getValue(),
				coba_gz_paydate.getValue(), coba_emailgz_paydate.getValue(),
				coba_papergz_paydate.getValue(),
				coba_emfics_deldate.getValue(), coba_emfics_paydate.getValue(),
				coba_emfics_backdate.getValue(), tb.getValue());

		// 弹出提示并跳转页面
		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// 跳转页面
						// Executions.sendRedirect(".zul");
						//
						// winCoBaseCS.detach();
						model = cobaSBll.getCobaseeditinfo(str).get(0);
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public CoBaseModel getModel() {
		return model;
	}

	public void setModel(CoBaseModel model) {
		this.model = model;
	}

}
