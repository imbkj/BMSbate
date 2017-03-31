package Controller.EmZYT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoCompactCoofferModel;
import Model.CoOfferListModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Util.UserInfo;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.CoCompactComparatorBll;
import bll.Embase.CoCompactGroupingBll;
import bll.Embase.EmBase_OnBoardBll;

public class EmZYT_AddNewController {
	private List<Integer> ccm = new ArrayList<Integer>();
	private List<CoOfferListModel> cflList = new ListModelList<CoOfferListModel>();
	private List<CoCompactCoofferModel> cklist = new ListModelList<CoCompactCoofferModel>();
	private EmBase_OnBoardBll bll = new EmBase_OnBoardBll();
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	private boolean showGroup = true;
	private boolean b = false;
	private String username = UserInfo.getUsername();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	private Window win = (Window) Path.getComponent("/win");

	private int emzt_id = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getId();
	private List<String> contacttypeList;
	private int cid = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getCid();

	private String id = "";
	private String sd1 = "";
	private String sd2 = "";
	private String intime = null;

	public EmZYT_AddNewController() {
		// 联系方式下拉框
		contacttypeList = sbll.getContacttype();
	}

	// 勾选报价单事件
	@Command("ckbcoof")
	@NotifyChange({ "cflList", "cklist" })
	public void ckbcoof(@BindingParam("a") CoCompactCoofferModel clm,
			@BindingParam("b") Checkbox ckb) {
		if (ckb.isChecked()) {

			for (Integer i = 0; i <= ccm.size(); i++) {

				if (i == ccm.size()) {
					ccm.add(i, clm.getCoof_id());
					break;
				} else {
					if (ccm.get(i) == null) {
						ccm.add(i, clm.getCoof_id());
						break;
					}
				}

			}

		} else {
			if (ccm.size() > 0) {
				for (Integer i = 0; i < ccm.size(); i++) {
					if (ccm.get(i).toString()
							.equals(clm.getCoof_id().toString())) {
						ccm.remove(ccm.get(i));

						break;
					}
				}
			}
		}
		setCflList(ccm);
		setCklist(ccm);

	}

	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox chb,
			@BindingParam("b") Grid gdck, @BindingParam("c") Grid gd) {

		Label lb = (Label) chb.getParent().getChildren().get(1);
		if (lb.getValue().toString().equals("全选")) {

			Checkbox cka = (Checkbox) gd.getColumns().getChildren().get(5)
					.getChildren().get(0);

			cka.setChecked(chb.isChecked());
			for (int i = 1; i < gdck.getRows().getChildren().size(); i++) {

				Checkbox cb = (Checkbox) gdck.getCell(i, 0).getChildren()
						.get(0);
				cb.setChecked(chb.isChecked());

			}

			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, 5) != null) {

					Checkbox cb = (Checkbox) gd.getCell(i, 5).getChildren()
							.get(0);
					cb.setChecked(chb.isChecked());
					cancel(gd, cb);
				}
			}
		} else {
			String pclass = lb.getValue().toString();
			Checkbox cka = (Checkbox) gd.getColumns().getChildren().get(5)
					.getChildren().get(0);
			boolean check = true;
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, 5) != null) {
					Label lbl = (Label) gd.getCell(i, 1).getChildren().get(0);
					Checkbox cb = (Checkbox) gd.getCell(i, 5).getChildren()
							.get(0);
					if (!cb.isChecked()) {
						check = false;
					}
					if (lbl.getValue().toString().equals(pclass)) {
						cb.setChecked(chb.isChecked());
						cancel(gd, cb);

					}
				}

			}
			cka.setChecked(check);
		}
	}

	@Command("checklist")
	public void checklist(@BindingParam("a") Checkbox cb) {
		Grid gd = (Grid) win.getFellow("coofferlist");

		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 5) != null) {
				Checkbox chb = (Checkbox) gd.getCell(i, 5).getChildren().get(0);
				chb.setChecked(cb.isChecked());
				cancel(gd, chb);
			}
		}
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

					if (db2.getValue() == null) {
						Checkbox ck = (Checkbox) gd.getCell(i, 5).getChildren()
								.get(0);
						if (ck.isChecked())
							db2.setValue(db1.getValue());
					}

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
			Checkbox ck = (Checkbox) gd.getCell(row.getIndex(), 5)
					.getChildren().get(0);
			ck.setChecked(true);
		}
	}

	// 取消项目选择并清空时间
	@Command("setDateValue")
	public void setcheck(@BindingParam("a") Grid gd,
			@BindingParam("b") Checkbox ck) {

		cancel(gd, ck);

	}

	public void cancel(Grid gd, Checkbox ck) {
		if (!ck.isChecked()) {
			/*Row row = (Row) ck.getParent().getParent();
			Datebox db1 = (Datebox) gd.getCell(row.getIndex(), 5).getChildren()
					.get(0).getChildren().get(0);
			Datebox db2 = (Datebox) gd.getCell(row.getIndex(), 6).getChildren()
					.get(0).getChildren().get(0);
			db1.setValue(null);
			db2.setValue(null);*/
		}
	}

	// 判断date控件的值是否为空
	public boolean DateEmpty(Datebox db) {
		boolean a = false;
		if (db.getValue() != null) {
			if (db.getValue().equals("")) {
				a = true;
			}
		} else {
			a = true;
		}
		return a;
	}

	// 重置全局变量
	public void ClearPama() {
		intime = null;
		id = "";
		sd1 = "";
		sd2 = "";
	}

	// 提交页面
	@Command("btnSubmit")
	public void btnSubmit(@BindingParam("a") Datebox db,
			@BindingParam("b") Grid gd,
			@BindingParam("emzt_contacttype") final Combobox emzt_contacttype) {

		Boolean j = false;
		// 初始化判断属性
		ClearPama();
		// 判断入职日期
		if (DateEmpty(db)) {
			Messagebox.show("请录入入职时间", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			intime = DateFormat.getDateInstance().format(db.getValue());
		}

		// 判断联系方式
		if (emzt_contacttype.getSelectedItem() == null) {
			Messagebox.show("请选择联系方式", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// 判断是否勾选项目
		if (gd.getRows().getChildren().size() > 0) {
			for (Integer i = 0; i < gd.getRows().getChildren().size(); i++) {

				if (gd.getCell(i, 5) != null) {

					Checkbox ck = (Checkbox) gd.getCell(i, 5).getChildren()
							.get(0);

					if (ck.isChecked()) {
						j = true;
						/*
						 * Datebox db1 = (Datebox) gd.getCell(i,
						 * 5).getChildren() .get(0).getChildren().get(0);
						 * Datebox db2 = (Datebox) gd.getCell(i,
						 * 6).getChildren() .get(0).getChildren().get(0);
						 * 
						 * 
						 * if (DateEmpty(db1)) {
						 * 
						 * Messagebox.show("请录入 [" + ((Label) gd.getCell(i,
						 * 3).getChildren() .get(0)).getValue() + "] 服务起始日",
						 * "操作提示", Messagebox.OK, Messagebox.ERROR); return;
						 * 
						 * }
						 * 
						 * if (DateEmpty(db2)) { Messagebox.show("请录入 [" +
						 * ((Label) gd.getCell(i, 3).getChildren()
						 * .get(0)).getValue() + "] 收费起始日", "操作提示",
						 * Messagebox.OK, Messagebox.ERROR); return; }
						 */
						id = id + "," + ck.getValue();
						// sd1 = sd1 + "," + sdf.format(db1.getValue());
						// sd2 = sd2 + "," + sdf.format(db2.getValue());
					}
				}
			}

			if (id.length() > 0)
				id = id.substring(1);
			/*
			 * if (sd1.length() > 0) sd1 = sd1.substring(1); if (sd2.length() >
			 * 0) sd2 = sd2.substring(1);
			 */
		}

		if (!j) {
			Messagebox.show("请选择员工的报价单项目", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {

						String[] message = obll.addEmZYTCoGlist(emzt_id,
								intime, id, emzt_contacttype.getValue(),
								username);
						if (message[0].equals("1")) {
							EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
								public void onEvent(ClickEvent event)
										throws Exception {
									if (Messagebox.Button.OK.equals(event
											.getButton())) {
										// 关闭页面
										win.detach();
									}
								}
							};
							// 弹出提示
							Messagebox
									.show(message[1],
											"操作提示",
											new Messagebox.Button[] { Messagebox.Button.OK },
											Messagebox.INFORMATION,
											clickListener);
						} else {
							// 弹出提示
							Messagebox.show(message[1], "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				}
			};

			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION, clickListener);

		}

	}

	public List<CoCompactCoofferModel> getCklist() {
		return cklist;
	}

	public void setCklist(List<Integer> list) {
		this.cklist = bll.getcoofferMenu(list);
	}

	public CoCompactGroupingBll getCompactModel() {
		return new CoCompactGroupingBll(bll.getCompactCoofferByCid(cid),
				new CoCompactComparatorBll(), this.showGroup);
	}

	public List<CoOfferListModel> getCflList() {
		return cflList;
	}

	public void setCflList(List<Integer> list) {
		this.cflList = bll.getCoofferlistByCofId(list);
	}

	public List<String> getContacttypeList() {
		return contacttypeList;
	}

	public void setContacttypeList(List<String> contacttypeList) {
		this.contacttypeList = contacttypeList;
	}

}
