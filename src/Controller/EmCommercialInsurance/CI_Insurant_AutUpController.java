package Controller.EmCommercialInsurance;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;

import Model.CI_Insurant_ListModel;

public class CI_Insurant_AutUpController {
	CI_Insurant_ListBll bll = new CI_Insurant_ListBll();

	private CI_Insurant_OperateBll ccsaBll = new CI_Insurant_OperateBll();

	private ListModelList<CI_Insurant_ListModel> castsortlist;// 显示保险类型

	private ListModelList<CI_Insurant_ListModel> castsortdatelist;// 显示保险审核时间

	private ListModelList<CI_Insurant_ListModel> ci_insurant_autlist;// 显示商保已审核数据

	@Init
	public void init() throws SQLException {
		castsortlist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortlist());// 显示保险类型

		castsortdatelist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortdatelist());// 显示保险类型

		ci_insurant_autlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_autlist("", ""));// 显示已审核列表
	}

	// 商保申报
	@Command("ci_insurant_autup")
	public void ci_insurant_autup(@BindingParam("gridco") Grid gridco)
			throws Exception {

		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			try {
				Checkbox chk = (Checkbox) gridco.getCell(i, 9).getChildren()
						.get(0);

				Label tapr_id = (Label) gridco.getCell(i, 10).getChildren()
						.get(0);

				if (chk.isChecked()) {
					String[] message = ccsaBll.autup_insurant(
							(int) chk.getValue(),
							Integer.parseInt(tapr_id.getValue()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_Insurant_AutUp.zul");
	}

	// 审核支付通知
	@Command("ci_insurant_upload")
	public void ci_insurant_upload(
			@BindingParam("emco") CI_Insurant_ListModel emco) {
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("cid", 1);
		arg.put("ownmonth", 1);
		arg.put("type", 1);
		Window wnd = (Window) Executions.createComponents(
				"CI_Insurant_AutUpload.zul", null, arg);
		wnd.doModal();
	}

	// 商保申报查询
	@Command("search_up")
	@NotifyChange("ci_insurant_autlist")
	public void search_up(@BindingParam("castsort") Combobox castsort,
			@BindingParam("de_date") Combobox de_date) throws Exception {
		ci_insurant_autlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_autlist(castsort.getValue(), de_date.getValue()));// 显示审核列表
	}

	@Command("checkall")
	public void checkall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {

				Checkbox ckb = (Checkbox) gd.getCell(i, 9).getChildren().get(0);
				ckb.setChecked(ck);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public ListModelList<CI_Insurant_ListModel> getCastsortlist() {
		return castsortlist;
	}

	public void setCastsortlist(
			ListModelList<CI_Insurant_ListModel> castsortlist) {
		this.castsortlist = castsortlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCastsortdatelist() {
		return castsortdatelist;
	}

	public void setCastsortdatelist(
			ListModelList<CI_Insurant_ListModel> castsortdatelist) {
		this.castsortdatelist = castsortdatelist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_autlist() {
		return ci_insurant_autlist;
	}

	public void setCi_insurant_autlist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_autlist) {
		this.ci_insurant_autlist = ci_insurant_autlist;
	}

}
