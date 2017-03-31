package Controller.Embase;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoOfferListModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmFinance.EmFinance_OperateBll;
import bll.Embase.EmbaseListBll;
import bll.Embase.Embase_DimissionBll;

public class EmBase_CoGlistUpController {
	private EmbaseListBll eBll = new EmbaseListBll();
	private Embase_DimissionBll dBll = new Embase_DimissionBll();
	private EmFinance_OperateBll oBll = new EmFinance_OperateBll();
	private DateStringChange dsc = new DateStringChange();

	private List<CoOfferListModel> cflList = new ListModelList<CoOfferListModel>();
	private boolean ifCflsit = false;// 报价单列表显示状态
	private EmbaseModel emM;

	// 获取用户名
	String username = UserInfo.getUsername();
	private Integer gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());

	private List<Integer> cgliId;// 获取报价单数据

	private Integer fmonth; // 最新已确认台账月份

	public EmBase_CoGlistUpController() {
		// 获取员工基本信息
		emM = eBll.getEmbaseInfo(" and a.gid=" + gid);

		fmonth = oBll.getFinanceOwnmonth(emM.getCid());// 最新已确认台账月份

		String str = "";
		if (!"3".equals(UserInfo.getDepID())) {
			if ("13".equals(UserInfo.getDepID())) {
				str = " AND b.coli_pclass like '%财%'";
			} else {
				str = " AND b.coli_pclass not like '%财%'";
			}
		}

		cgliId = dBll.getCgliId3(gid, str);// 获取报价单数据
		// 获取报价单信息
		setCflList(cgliId);
		if (cgliId.size() > 0) {
			ifCflsit = true;
		}
	}

	// 提交事件
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("coofferlist") Grid coofferlist) throws Exception {

		// 检查表单
		if (checkPage(coofferlist)) {

			// 报价单项目调整
			if (ifCflsit == true) {// 判断是否有报价单项目
				String cgli_id = "";
				String sd1 = "";
				String sd2 = "";
				String sd3 = "";
				String[] cgliMsg;

				// 判断是否勾选项目
				if (coofferlist.getRows().getChildren().size() > 0) {
					for (Integer i = 0; i < coofferlist.getRows().getChildren()
							.size(); i++) {

						if (coofferlist.getCell(i, 8) != null) {

							Checkbox ck = (Checkbox) coofferlist.getCell(i, 8)
									.getChildren().get(0);

							if (ck.isChecked()) {

								Datebox db1 = (Datebox) coofferlist
										.getCell(i, 6).getChildren().get(0)
										.getChildren().get(0);
								Datebox db2 = (Datebox) coofferlist
										.getCell(i, 5).getChildren().get(0)
										.getChildren().get(0);
								Datebox db3 = (Datebox) coofferlist
										.getCell(i, 7).getChildren().get(0)
										.getChildren().get(0);

								cgli_id = String.valueOf(ck.getValue());
								if (db1.getValue() != null
										&& !"".equals(db1.getValue())) {
									sd1 = dsc.DatetoSting(db1.getValue(),
											"yyyyMM");
								} else {
									sd1 = "";
								}

								if (db2.getValue() != null
										&& !"".equals(db2.getValue())) {
									sd2 = dsc.DatetoSting(db2.getValue(),
											"yyyyMM");
								} else {
									sd2 = "";
								}

								if (db3.getValue() != null
										&& !"".equals(db3.getValue())) {
									sd3 = dsc.DatetoSting(db3.getValue(),
											"yyyyMM");
								} else {
									sd3 = "";
								}

								cgliMsg = oBll.changeCoGlist(cgli_id, sd1, sd2,
										sd3, username);
								if (!cgliMsg[0].equals("1")) {
									// fMsg = fMsg + "存在报价单项目更新不成功；";
								}

							}
						}
					}
				}
			}

			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						win.detach();
					}
				}
			}; // 弹出提示
			Messagebox.show("操作成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);

		}
	}

	// 检测表单
	private boolean checkPage(Grid coofferlist) {
		boolean b = true;

		if (fmonth != 0) {

			// 获取最新已确认台账月份
			if (ifCflsit == true) {// 判断是否有报价单项目
				Integer sd1 = 999999;
				Integer sd2 = 999999;
				Integer sd3 = 999999;

				// 判断是否勾选项目
				if (coofferlist.getRows().getChildren().size() > 0) {
					for (Integer i = 0; i < coofferlist.getRows().getChildren()
							.size(); i++) {

						if (coofferlist.getCell(i, 8) != null) {

							Checkbox ck = (Checkbox) coofferlist.getCell(i, 8)
									.getChildren().get(0);

							if (ck.isChecked()) {

								Datebox db1 = (Datebox) coofferlist
										.getCell(i, 6).getChildren().get(0)
										.getChildren().get(0);
								Datebox db2 = (Datebox) coofferlist
										.getCell(i, 5).getChildren().get(0)
										.getChildren().get(0);
								Datebox db3 = (Datebox) coofferlist
										.getCell(i, 7).getChildren().get(0)
										.getChildren().get(0);

								if (db1.getValue() != null
										&& !"".equals(db1.getValue())) {
									sd1 = Integer.parseInt(dsc.DatetoSting(
											db1.getValue(), "yyyyMM"));
								}
								if (db2.getValue() != null
										&& !"".equals(db2.getValue())) {
									sd2 = Integer.parseInt(dsc.DatetoSting(
											db2.getValue(), "yyyyMM"));
								}
								if (db3.getValue() != null
										&& !"".equals(db3.getValue())) {
									sd3 = Integer.parseInt(dsc.DatetoSting(
											db3.getValue(), "yyyyMM"));
								}

								if (sd1 <= fmonth) {
									// 取消限制
									/*
									 * b = false; Messagebox.show("该公司" + fmonth
									 * + "台账数据已确认，请取消确认再操作此步骤!", "操作提示",
									 * Messagebox.OK, Messagebox.ERROR); return
									 * b;
									 */
								}
								if (sd2 <= fmonth) {
									// 取消限制
									/*
									 * b = false; Messagebox.show("该公司" + fmonth
									 * + "台账数据已确认，请取消确认再操作此步骤!", "操作提示",
									 * Messagebox.OK, Messagebox.ERROR); return
									 * b;
									 */
								}
								if (sd3 <= fmonth) {
									// 取消限制
									/*
									 * b = false; Messagebox.show("该公司" + fmonth
									 * + "台账数据已确认，请取消确认再操作此步骤!", "操作提示",
									 * Messagebox.OK, Messagebox.ERROR); return
									 * b;
									 */
								}
							}
						}
					}
				}
			}
		}

		return b;
	}

	// 复制起始日
	@Command("copysd")
	public void copysd(@BindingParam("a") Grid gd,
			@BindingParam("b") Integer cellIndex,
			@BindingParam("rowIndex") Integer num) {
		Datebox db1 = (Datebox) gd.getCell(num, cellIndex).getChildren().get(0)
				.getChildren().get(0);

		if (db1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {

				if (gd.getCell(i, cellIndex) != null) {

					Datebox db2 = (Datebox) gd.getCell(i, cellIndex)
							.getChildren().get(0).getChildren().get(0);

					// if (db2.getValue() == null) {
					Checkbox ck = (Checkbox) gd.getCell(i, 8).getChildren()
							.get(0);
					if (ck.isChecked())
						db2.setValue(db1.getValue());
					// }

				}
			}
		} else {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {

			}
		}
	}

	// 填入起始日同时勾选
	@Command("setcheck")
	public void setcheck(@BindingParam("a") Grid gd,
			@BindingParam("b") Datebox db) {
		if (db.getValue() != null) {
			Row row = (Row) db.getParent().getParent().getParent();
			Checkbox ck = (Checkbox) gd.getCell(row.getIndex(), 8)
					.getChildren().get(0);
			ck.setChecked(true);
		}
	}

	public EmbaseModel getEmM() {
		return emM;
	}

	public void setEmM(EmbaseModel emM) {
		this.emM = emM;
	}

	public List<CoOfferListModel> getCflList() {
		return cflList;
	}

	public void setCflList(List<Integer> list) {
		this.cflList = dBll.getCoofferlistByColiId(list);
	}

	public boolean isIfCflsit() {
		return ifCflsit;
	}

	public void setIfCflsit(boolean ifCflsit) {
		this.ifCflsit = ifCflsit;
	}
}
