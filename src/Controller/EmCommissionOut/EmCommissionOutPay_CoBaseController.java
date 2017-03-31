package Controller.EmCommissionOut;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutPayBll;
import bll.EmCommissionOut.EmCommissionOutPayDifBll;
import Model.EmCommissionOutPayModel;

public class EmCommissionOutPay_CoBaseController {
	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();
	private ListModelList<EmCommissionOutPayModel> autpaycolist;// 显示公司帐单月份明细

	@Init
	public void init() throws SQLException {
		String ownmonth = "";
		try {
			ownmonth = Executions.getCurrent().getArg().get("ownmonth")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		String cabc_id = "";
		try {
			cabc_id = Executions.getCurrent().getArg().get("cabc_id")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		autpaycolist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaycolist(ownmonth, cabc_id));// 显示公司帐单月份明细
	}

	// 委托实付明细
	@Command("empay_base")
	public void empay_base(@BindingParam("emco") EmCommissionOutPayModel emco) {

		Map arg = new HashMap();
		arg.put("cabc_id", emco.getEcop_cabc_id());
		arg.put("ownmonth", emco.getOwnmonth());
		arg.put("cid", emco.getCid());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_BaseList1.zul", null, arg);
		wnd.doModal();
	}

	// 委托公司确认
	@Command("wtco_ok")
	@NotifyChange("autpaycolist")
	public void wtco_ok(@BindingParam("gridco") Grid gridco,
			@BindingParam("emco") EmCommissionOutPayModel emco,
			@BindingParam("cabc_id") Label cabc_id,
			@BindingParam("ownmonth") Label ownmonth) throws Exception {
		String message = "0";
		String message1 = "0";

		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			try {
				Checkbox chk = (Checkbox) gridco.getCell(i, 8).getChildren()
						.get(0);

				Label cid = (Label) gridco.getCell(i, 9).getChildren().get(0);

				if (chk.isChecked()) {

					message = bll.wtco_ok(ownmonth.getValue(),
							cabc_id.getValue(), cid.getValue());
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}

		}
		Messagebox.show("确认成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		autpaycolist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaycolist(ownmonth.getValue(), cabc_id.getValue()));// 显示公司帐单月份明细
	}

	@Command("cocheckall")
	public void cocheckall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 8).getChildren()
						.get(0);
				ckb.setChecked(ck);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public ListModelList<EmCommissionOutPayModel> getAutpaycolist() {
		return autpaycolist;
	}

	public void setAutpaycolist(
			ListModelList<EmCommissionOutPayModel> autpaycolist) {
		this.autpaycolist = autpaycolist;
	}

}
