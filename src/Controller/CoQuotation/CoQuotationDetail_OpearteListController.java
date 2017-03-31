package Controller.CoQuotation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoQuotation.CoQuotation_List1Bll;
import bll.CoQuotation.CoofferOperateBll;

import Model.CoOfferModel;

public class CoQuotationDetail_OpearteListController {

	private List<CoOfferModel> coofferList;
	private int cola_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cola_id").toString());

	CoQuotation_List1Bll bll = new CoQuotation_List1Bll();

	@Init
	public void init() throws Exception {
		setCoofferList(new ListModelList<CoOfferModel>(
				bll.getCoOfferList(cola_id)));
	}

	// 弹出预览页面
	@Command("yulan")
	public void yulan(@BindingParam("model") CoOfferModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/Quotation_ExcelAdd.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("coofferList")
	public void del(@BindingParam("model") final CoOfferModel cm) {
		Messagebox.show("确认删除报价?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.delQutation(cm.getCoof_id());

							if (i > 0) {
								setCoofferList(new ListModelList<CoOfferModel>(
										bll.getCoOfferList(cola_id)));
								Messagebox.show("删除成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show("删除失败", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}
						}
					}
				});
	}

	@Command("coquotation_edit")
	@NotifyChange("coofferList")
	public void coquotation_edit(@BindingParam("model") final CoOfferModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("colaid", model.getCoof_id());
		map.put("colacompany", model.getCoof_name());
		Window window = (Window) Executions.createComponents(
				"CoQuotation_edit.zul", null, map);
		window.doModal();
	}

	// 提交审核
	@Command("compactautding_add")
	@NotifyChange("coofferList")
	public void compactautding_add(
			@BindingParam("model") final CoOfferModel model) {
		// 添加审核任务单
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					CoofferOperateBll cbll = new CoofferOperateBll();
					String[] msg = cbll.CoofferAuditAdd(model);
					if (msg[0] == "1") {
						Messagebox.show("提交成功", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						try {
							coofferList = bll.getCoOfferList(cola_id);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						Messagebox.show(msg[1], "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		};
		Messagebox.show("是否确认提交审核?", "提示", new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);

	}

	// 重新提交审核
	@Command("readd")
	@NotifyChange("coofferList")
	public void readd(@BindingParam("model") final CoOfferModel model) {
		Map map = new HashMap<>();
		map.put("daid", model.getCoof_id() + "");
		map.put("id", model.getCoof_tarp_id() + "");
		Window window = (Window) Executions.createComponents(
				"../CoQuotation/CoQuotation_AuditReAdd.zul", null, map);
		window.doModal();
		try {
			coofferList = bll.getCoOfferList(cola_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 弹出生成模板页面
	@Command("compactTemp_add")
	@NotifyChange("coofferList")
	public void compactTemp_add(@BindingParam("gd") Grid gd,
			@BindingParam("model") CoOfferModel model) {
		// 获取所有选项报价单
		String coof_id = "0";
		boolean b = false;
		Checkbox ckb = null;
		String cType = "";
		String dType = "";
		boolean chkType = true;

		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {

			try {
				ckb = (Checkbox) gd.getRows().getChildren().get(i)
						.getChildren().get(11).getChildren().get(0);
				// ckb = (Checkbox) gd.getCell(i, 11).getChildren().get(0); //
				// 获取checkbox
				b = ckb.isChecked();

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (b) {// 判断是否选中
				b = false;
				dType = "";
				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行

				// 判断是否都选择相同的合同类型
				if ("".equals(cType)) {
					// 第一行无需判断
					cType = String.valueOf(((CoOfferModel) row.getValue())
							.getCpct_shortname());
				} else {
					// 判断合同类型
					dType = String.valueOf(((CoOfferModel) row.getValue())
							.getCpct_shortname());

					if (!cType.equals(dType)) {
						chkType = false;
						break;
					}
				}
				// 拼接报价单id
				coof_id = coof_id
						+ ","
						+ String.valueOf(((CoOfferModel) row.getValue())
								.getCoof_id());
			}
		}

		if (coof_id.equals("0")) {
			Messagebox.show("请选择报价单。", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else {
			if (chkType) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("model", model);
				map.put("coof_id", coof_id);
				Window window = (Window) Executions.createComponents(
						"/CoCompact/CompactTemp_Add.zul", null, map);
				window.doModal();

				try {
					coofferList = bll.getCoOfferList(cola_id);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				Messagebox.show("请选择需相同的合同类型。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}

	}

	// 弹出查看页面
	@Command("chakan")
	public void chakan(@BindingParam("model") CoOfferModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coofid", model.getCoof_id());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/CoQuotationInfoOperate.zul", null, map);
		window.doModal();
	}

	// 弹出合同查看页面
	@Command("compactTemp_chk")
	public void compactTemp_chk(@BindingParam("model") CoOfferModel model) {
		// 调用方法
		Map map = new HashMap();

		map.put("id", model.getCoco_house());
		Window window = (Window) Executions.createComponents(
				"/CoCompact/Compact_CheckDoc.zul", null, map);
		window.doModal();
	}

	public List<CoOfferModel> getCoofferList() {
		return coofferList;
	}

	public void setCoofferList(List<CoOfferModel> coofferList) {
		this.coofferList = coofferList;
	}

}
