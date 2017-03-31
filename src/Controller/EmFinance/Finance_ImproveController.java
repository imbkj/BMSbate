package Controller.EmFinance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoOfferListModel;
import Model.EmHouseBJModel;
import Model.EmShebaoBJModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.EmFinance.EmFinance_OperateBll;
import bll.EmHouse.Emhouse_BjBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.Embase.EmbaseListBll;
import bll.Embase.Embase_DimissionBll;

public class Finance_ImproveController {
	private EmbaseListBll eBll = new EmbaseListBll();
	private Embase_DimissionBll dBll = new Embase_DimissionBll();
	private EmFinance_OperateBll oBll = new EmFinance_OperateBll();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	private DateStringChange dsc = new DateStringChange();

	private List<CoOfferListModel> cflList = new ListModelList<CoOfferListModel>();
	private boolean ifCflsit = false;// 报价单列表显示状态
	private EmbaseModel emM;

	private MonthListUtil mlu = new MonthListUtil();
	private String[] monthList;// 月份下拉列表

	private Integer fmonth; // 最新已确认台账月份

	// 社保补缴(养老)
	private Emsc_DeclareSelBll sbbjBll = new Emsc_DeclareSelBll();
	private List<EmShebaoBJModel> sbbjList = new ListModelList<EmShebaoBJModel>();
	private boolean ifsbbj = false;

	// 社保补缴(医疗)
	private List<EmShebaoBJModel> sbbjJLList = new ListModelList<EmShebaoBJModel>();
	private boolean ifsbbjjl = false;

	// 公积金补缴
	private Emhouse_BjBll gjjbjBll = new Emhouse_BjBll();
	private List<EmHouseBJModel> gjjbjList = new ListModelList<EmHouseBJModel>();
	private boolean ifgjjbj = false;

	// 获取用户名
	String username = UserInfo.getUsername();
	private Integer gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Integer ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());
	private Integer cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());

	private List<Integer> cgliId = dBll.getCgliId2(gid);// 获取报价单数据

	public Finance_ImproveController() {
		// 获取员工基本信息
		emM = eBll.getEmbaseInfo(" and a.gid=" + gid);

		fmonth = oBll.getFinanceOwnmonth(cid);// 最新已确认台账月份

		// 获取报价单信息
		setCflList(cgliId);
		if (cgliId.size() > 0) {
			ifCflsit = true;
		}

		// 获取月份下拉列表数据
		monthList = mlu.getMonthList(true, sdf.format(new Date()), 2, 3);

		// 获取社保补缴数据(养老)
		sbbjList = sbbjBll
				.getBjInfoByStr(" AND bj.emsb_ifdeclare<>3 AND bj.gid="
						+ String.valueOf(emM.getGid()));
		if (sbbjList.size() > 0) {
			ifsbbj = true;
		}

		// 获取社保补缴数据(医疗)
		sbbjJLList = sbbjBll
				.getBjJLInfoByStr(" AND bj.esbj_ifdeclare<>3 AND bj.gid="
						+ String.valueOf(emM.getGid()));
		if (sbbjJLList.size() > 0) {
			ifsbbjjl = true;
		}

		// 获取公积金补缴数据
		gjjbjList = gjjbjBll
				.getEmhouseBjInfoZYT(" AND emhb_ifdeclare<>3 AND a.gid="
						+ String.valueOf(emM.getGid()));
		if (gjjbjList.size() > 0) {
			ifgjjbj = true;
		}
	}

	// 提交事件
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("coofferlist") Grid coofferlist,
			@BindingParam("gdShebaobj") Grid gdShebaobj,
			@BindingParam("gdGjjbj") Grid gdGjjbj,
			@BindingParam("gdShebaobjjl") Grid gdShebaobjjl) throws Exception {

		// 检查表单
		if (checkPage(coofferlist, gdShebaobj, gdGjjbj, gdShebaobjjl)) {

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

			// 社保补缴收费月份调整
			if (ifsbbj == true) {// 判断是否有社保补缴项目
				String emsb_id;
				String sbFeemonth;
				String[] sbMsg;

				for (Integer i = 0; i < gdShebaobj.getRows().getChildren()
						.size(); i++) {
					emsb_id = ((Textbox) gdShebaobj.getCell(i, 0).getChildren()
							.get(0)).getValue();
					sbFeemonth = ((Combobox) gdShebaobj.getCell(i, 4)
							.getChildren().get(0)).getValue();

					sbMsg = oBll.changeSBBJFeemonth(emsb_id, sbFeemonth,
							username);

				}
			}

			// 社保补缴收费月份调整(医疗)
			if (ifsbbjjl == true) {// 判断是否有社保补缴项目
				String emsb_id;
				String sbFeemonth;
				String[] sbMsg;

				for (Integer i = 0; i < gdShebaobjjl.getRows().getChildren()
						.size(); i++) {
					emsb_id = ((Textbox) gdShebaobjjl.getCell(i, 0)
							.getChildren().get(0)).getValue();
					sbFeemonth = ((Combobox) gdShebaobjjl.getCell(i, 4)
							.getChildren().get(0)).getValue();

					// 医疗补交
					sbMsg = oBll.changeSBBJJLFeemonth(emsb_id, sbFeemonth,
							username);
				}
			}

			// 公积金补缴收费调整
			if (ifgjjbj == true) {// 判断是否有公积金补缴项目
				String emhb_id;
				String gjjFeemonth;
				String[] gjjMsg;

				for (Integer i = 0; i < gdGjjbj.getRows().getChildren().size(); i++) {
					emhb_id = ((Textbox) gdGjjbj.getCell(i, 0).getChildren()
							.get(0)).getValue();
					gjjFeemonth = ((Combobox) gdGjjbj.getCell(i, 5)
							.getChildren().get(0)).getValue();

					gjjMsg = oBll.changeGJJBJFeemonth(emhb_id, gjjFeemonth,
							username);
				}
			}

			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						win.detach();
					}
				}
			}; // 弹出提示
			Messagebox.show("操作成功，请重新同步台账！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);

		}
	}

	// 检测表单
	private boolean checkPage(Grid coofferlist, Grid gdShebaobj, Grid gdGjjbj,
			Grid gdShebaobjjl) {
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

			// 社保补缴收费月份调整(养老)
			if (ifsbbj == true) {// 判断是否有社保补缴项目
				Integer sbFeemonth;

				for (Integer i = 0; i < gdShebaobj.getRows().getChildren()
						.size(); i++) {
					sbFeemonth = Integer.parseInt(((Combobox) gdShebaobj
							.getCell(i, 4).getChildren().get(0)).getValue());
					if (sbFeemonth <= fmonth) {
						b = false;
						Messagebox.show(
								"该公司" + fmonth + "台账数据已确认，请取消确认再操作此步骤!",
								"操作提示", Messagebox.OK, Messagebox.ERROR);
						return b;
					}
				}
			}

			// 社保补缴收费月份调整(医疗)
			if (ifsbbjjl == true) {// 判断是否有社保补缴项目
				Integer sbFeemonth;

				for (Integer i = 0; i < gdShebaobjjl.getRows().getChildren()
						.size(); i++) {
					sbFeemonth = Integer.parseInt(((Combobox) gdShebaobjjl
							.getCell(i, 4).getChildren().get(0)).getValue());
					if (sbFeemonth <= fmonth) {
						b = false;
						Messagebox.show(
								"该公司" + fmonth + "台账数据已确认，请取消确认再操作此步骤!",
								"操作提示", Messagebox.OK, Messagebox.ERROR);
						return b;
					}
				}
			}

			// 公积金补缴收费调整
			if (ifgjjbj == true) {// 判断是否有公积金补缴项目
				Integer gjjFeemonth;

				for (Integer i = 0; i < gdGjjbj.getRows().getChildren().size(); i++) {
					gjjFeemonth = Integer.parseInt(((Combobox) gdGjjbj
							.getCell(i, 5).getChildren().get(0)).getValue());
					if (gjjFeemonth <= fmonth) {
						b = false;
						Messagebox.show(
								"该公司" + fmonth + "台账数据已确认，请取消确认再操作此步骤!",
								"操作提示", Messagebox.OK, Messagebox.ERROR);
						return b;
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

	// 打开业务中心
	@Command
	public void openbucenter() {
		EmbaseListBll emBll = new EmbaseListBll();
		Integer emba_id = 0;
		emba_id = emBll.getEmbaseInfo(" AND a.gid=" + String.valueOf(gid))
				.getEmba_id();

		Map map = new HashMap<>();
		map.put("daid", gid);
		map.put("embaId", emba_id);
		Window window;
		window = (Window) Executions.createComponents(
				"../SystemControl/EmBuCenterInfoList.zul", null, map);
		window.doModal();
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

	public boolean isIfsbbj() {
		return ifsbbj;
	}

	public void setIfsbbj(boolean ifsbbj) {
		this.ifsbbj = ifsbbj;
	}

	public boolean isIfgjjbj() {
		return ifgjjbj;
	}

	public void setIfgjjbj(boolean ifgjjbj) {
		this.ifgjjbj = ifgjjbj;
	}

	public List<EmShebaoBJModel> getSbbjList() {
		return sbbjList;
	}

	public void setSbbjList(List<EmShebaoBJModel> sbbjList) {
		this.sbbjList = sbbjList;
	}

	public List<EmHouseBJModel> getGjjbjList() {
		return gjjbjList;
	}

	public void setGjjbjList(List<EmHouseBJModel> gjjbjList) {
		this.gjjbjList = gjjbjList;
	}

	public String[] getMonthList() {
		return monthList;
	}

	public void setMonthList(String[] monthList) {
		this.monthList = monthList;
	}

	public List<EmShebaoBJModel> getSbbjJLList() {
		return sbbjJLList;
	}

	public void setSbbjJLList(List<EmShebaoBJModel> sbbjJLList) {
		this.sbbjJLList = sbbjJLList;
	}

	public boolean isIfsbbjjl() {
		return ifsbbjjl;
	}

	public void setIfsbbjjl(boolean ifsbbjjl) {
		this.ifsbbjjl = ifsbbjjl;
	}

}
