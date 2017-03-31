package Controller.EmSalary;

import impl.MessageImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import Model.CoBaseModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;
import bll.EmSalary.EmSalaryInfoListBll;
import bll.EmSalary.EmSalary_AudtingOperateBll;
import bll.EmSalary.EmSalary_EditBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_PayController {

	private final int taba_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("daid").toString());
	private final int taba_tapr_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryBaseAddItemModel> itemList;
	private List<EmSalaryDataModel> salaryListSUM;// 合计
	private List<EmSalaryDataModel> salaryWinListSUM;// 合计
	private List<EmSalaryBaseAddItemModel> itemListSUM;// 合计
	private EmSalary_SalarySelBll bll;

	private EmSalaryInfoListBll emsbll = new EmSalaryInfoListBll();

	private EmSalary_EditBll ebe = new EmSalary_EditBll();

	public EmSalary_PayController() {

		bll = new EmSalary_SalarySelBll();
		salaryList = bll.getSalaryDataByTabaId(taba_id);
		if (salaryList.size() > 0) {
			EmSalaryDataModel m = (EmSalaryDataModel) salaryList.get(0);
			itemList = bll.getItemInfoByCidMonthNoPay(m.getCid(),
					m.getOwnmonth());
			setEmSalaryDataModelOfItemList(salaryList, itemList);
			salaryWinList = salaryList;

			// 合计
			salaryListSUM = bll.getSalaryDataSUM("", taba_id);
			itemListSUM = itemList;
			setEmSalaryDataModelOfItemListSUM(salaryListSUM, itemListSUM);
			salaryWinListSUM = salaryListSUM;
		}

	}

	// 发放工资数据
	@Command("audtingSalary")
	public void audtingSalary(@BindingParam("gd") Grid gdSalary,
			@BindingParam("win") Window win, @BindingParam("type") Integer type) {
		if (Messagebox.show("确认发放选中的工资数据吗？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			try {
				auditing(gdSalary, win, type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 导出数据
	@Command("exportData")
	public void exportData() {
		try {
			if (salaryList.size() > 0) {
				String filePath = "EmSalary/File/Download/" + taba_tapr_id
						+ "Audting_" + UserInfo.getUsername() + ".xls";
				int i = createTemplet(filePath);
				if (i == 1) {
					FileOperate.download(filePath);
				} else {
					Messagebox.show("数据生成失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("未找到有效数据。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("导出数据出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 生成导出文件
	private int createTemplet(String filePath) {
		final String absolutePath = FileOperate.getAbsolutePath();
		final String templet = "EmSalary/File/Templet/SalaryChange.xls";
		final int dataCell = 7;
		final int dataRow = 3;
		int success = 0;
		try {
			// 读取Excel模板
			Workbook wb = Workbook
					.getWorkbook(new File(absolutePath + templet));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + filePath), wb);
			// 生成工作表
			WritableSheet sheet = wwb.createSheet(taba_id + "工资发放数据", 0);
			// 设置字体格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体
			WritableCellFormat wcf = new WritableCellFormat(wf);
			// 获取公司信息
			EmSalaryDataModel m = salaryList.get(0);
			// 插入第一行
			Label label = null;
			jxl.write.Number num = null;
			label = new Label(1, 0, String.valueOf(m.getOwnmonth()), wcf);
			sheet.addCell(label);
			label = new Label(4, 0, bll.getCoShortName(m.getCid()) + "工资发放",
					wcf);
			sheet.addCell(label);
			label = new Label(0, 1, "", wcf);
			sheet.addCell(label);
			// 插入表头
			try {
				label = new Label(0, dataRow - 1, "员工编号", wcf);
				sheet.addCell(label);
				label = new Label(1, dataRow - 1, "员工姓名", wcf);
				sheet.addCell(label);
				label = new Label(2, dataRow - 1, "用途", wcf);
				sheet.addCell(label);
				label = new Label(3, dataRow - 1, "财务备注", wcf);
				sheet.addCell(label);
				label = new Label(4, dataRow - 1, "是否台账外", wcf);
				sheet.addCell(label);
				label = new Label(5, dataRow - 1, "状态", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(6, dataRow - 1, "实发工资", wcf);
				sheet.addCell(label);
				for (int i = 0; i < itemList.size(); i++) {
					if (itemList.get(i).getCsii_fd_state() == 1) {
						label = new Label(i + dataCell, dataRow - 1, itemList
								.get(i).getCsii_item_name(), wcf);
						sheet.addCell(label);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 插入数据
			try {
				for (int i = 0; i < salaryList.size(); i++) {
					num = new jxl.write.Number(0, dataRow + i, salaryList
							.get(i).getGid());
					sheet.addCell(num);
					label = new Label(1, dataRow + i, salaryList.get(i)
							.getName());
					sheet.addCell(label);
					label = new Label(2, dataRow + i, salaryList.get(i)
							.getEsda_usage_typestr());
					sheet.addCell(label);
					label = new Label(3, dataRow + i, salaryList.get(i)
							.getEsda_remark());
					sheet.addCell(label);
					label = new Label(4, dataRow + i, salaryList.get(i)
							.getEsda_oof_statestr());
					sheet.addCell(label);
					if (salaryList.get(i).getEsda_payment_state() == 2) {
						label = new Label(5, dataRow + i, "已发放");
					} else {
						label = new Label(5, dataRow + i, "未发放");
					}
					sheet.addCell(label);
					num = new jxl.write.Number(6, dataRow + i, salaryList
							.get(i).getEsda_pay().doubleValue());
					sheet.addCell(num);
					for (int j = 0; j < salaryList.get(i).getItemList().size(); j++) {
						if (salaryList.get(i).getItemList().get(j)
								.getCsii_fd_state() == 1) {
							num = new jxl.write.Number(j + dataCell, dataRow
									+ i, salaryList.get(i).getItemList().get(j)
									.getAmount().doubleValue());
							sheet.addCell(num);
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			success = 1;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return success;
	}

	private void refreshSalaryWinList() {
		salaryList = bll.getSalaryDataByTabaId(taba_id);
		setEmSalaryDataModelOfItemList(salaryList, itemList);
		salaryWinList = salaryList;
	}

	// 暂停发放
	@Command("holdSalary")
	public void holdSalary(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("win") Window win) {
		try {
			if (Messagebox.show("确认暂停发放选中的工资数据吗？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				List<EmSalaryDataModel> list = getGdModel(gdSalary);
				if (list.size() != 0) {
					EmSalary_AudtingOperateBll opBll = new EmSalary_AudtingOperateBll();
					String[] message = opBll.payHoldEmSalary(list);

					if ("1".equals(message[0])) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						// if ("1".equals(message[2])) {
						win.detach();
						// } else {
						// refreshSalaryWinList();
						// }
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}

			} else {
				Messagebox.show("请勾选您需要确认的数据。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("工资暂停发放出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 获取Grid的勾选的model
	private List<EmSalaryDataModel> getGdModel(Grid gdSalary) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		List<Component> rows = gdSalary.getRows().getChildren();
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(1)).isChecked()) {
					EmSalaryDataModel m = new EmSalaryDataModel();
					m = (EmSalaryDataModel) row.getValue();
					list.add(m);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 获取Grid的勾选的ID
	private List<Integer> getGdId(Grid gdSalary) {
		List<Integer> list = new ArrayList<Integer>();
		List<Component> rows = gdSalary.getRows().getChildren();
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(1)).isChecked()) {
					list.add(((EmSalaryDataModel) row.getValue()).getTbrb_id());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 检索
	@Command("changeList")
	@NotifyChange("salaryWinList")
	public void changeList(@BindingParam("ibGid") Intbox ibGid,
			@BindingParam("txtName") Textbox txtName) {
		if (!"".equals(txtName.getValue()) || ibGid.getValue() != null) {
			if (ibGid.getValue() == null) {
				salaryWinList = getNewList(0, txtName.getValue());
			} else {
				salaryWinList = getNewList(ibGid.getValue(), txtName.getValue());
			}
		} else {
			salaryWinList = salaryList;
		}
	}

	// 检索数据
	private List<EmSalaryDataModel> getNewList(int gid, String name) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		int i = 2;
		for (EmSalaryDataModel m : salaryList) {
			i = 2;
			if (gid == 0) {
				i = i - 1;
			} else if (gid == m.getGid()) {
				i = i - 1;
			}
			try {
				if (name == null || "".equals(name)) {
					i = i - 1;
				} else if (RegexUtil.isExists(name, m.getName())) {
					i = i - 1;
				}
			} catch (Exception e) {

			}
			if (i == 0) {
				list.add(m);
			}
		}
		return list;
	}

	// 点击姓名查看工资的个人信息
	@Command("SelEmbase")
	public void SelEmbase(@BindingParam("lbl") org.zkoss.zul.Label lbl) {
		int esda_id = ((EmSalaryDataModel) ((Row) lbl.getParent()).getValue())
				.getEsda_id();
		int esda_payment_state = ((EmSalaryDataModel) ((Row) lbl.getParent())
				.getValue()).getEsda_payment_state();

		String url = "";
		if (esda_payment_state != 2) {
			url = "../EmSalary/EmSalary_EmbaseUpdate.zul";
		} else {
			url = "../EmSalary/EmSalary_Embase.zul";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("esda_id", String.valueOf(esda_id));
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 全选
	@Command("checkAll")
	public void checkAll(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("cbAll") Checkbox cbAll) {
		List<Component> row = gdSalary.getRows().getChildren();
		boolean ifCheck = cbAll.isChecked();
		int page = gdSalary.getActivePage();
		int size = gdSalary.getPageSize();
		int min = page * size;
		int max = (page + 1) * size;
		try {
			for (int i = min; i < max; i++) {
				try {
					Checkbox ck = (Checkbox) row.get(i).getChildren().get(1);
					if (!ck.isDisabled()) {
						ck.setChecked(ifCheck);
					}
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
	}

	// 初始化EmSalaryDataModel的itemList
	private void setEmSalaryDataModelOfItemList(List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemList(itList);
			} catch (Exception e) {

			}
		}
	}

	// 初始化EmSalaryDataModel的itemListSUM
	private void setEmSalaryDataModelOfItemListSUM(
			List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemListSUM(itList);
			} catch (Exception e) {

			}
		}
	}

	// email通知客服
	public void email() {
		// 只发送给客服部和项目外包部
		if (salaryList.size() > 0) {
			LoginModel m;
			m = emsbll.getLoginByCid(salaryList.get(0).getCid());
			if (m.getDep_id() != 6) {
				LoginDal d = new LoginDal();
				DateStringChange dsc = new DateStringChange();
				// 参数解释，业务表名：tablename；业务表id:id
				MessageService msgservice = new MessageImpl("EmSalaryData", 0);
				CoBaseModel coM = new CoBaseModel();
				coM = bll.getCobaseByCid(salaryList.get(0).getCid());

				String msgstr = "(" + String.valueOf(coM.getCid()) + ")"
						+ coM.getCoba_shortname()
						+ String.valueOf(salaryList.get(0).getOwnmonth())
						+ "月份的" + salaryList.get(0).getEsda_usage_typestr()
						+ "已于 " + dsc.Datestringnow("yyyy-MM-dd HH:mm")
						+ "分 发放";
				String eTitle = "(" + String.valueOf(coM.getCid()) + ")"
						+ coM.getCoba_shortname()
						+ String.valueOf(salaryList.get(0).getOwnmonth())
						+ "月份的" + salaryList.get(0).getEsda_usage_typestr()
						+ "已发放";

				try {
					// 调用添加短信息方法
					SysMessageModel sysm = new SysMessageModel();
					sysm.setSyme_content(msgstr);// 短信内容
					sysm.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
					sysm.setSymr_name(m.getLog_email());// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname((m.getLog_name())));// 收件人姓名id
					sysm.setEmail(1);
					sysm.setEmailtitle(eTitle);
					msgservice.Add(sysm);
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}

		}
	}

	// 工资发放
	@Command("auditing")
	@NotifyChange({ "salaryWinList", "itemList" })
	public void auditing(@BindingParam("gd") Grid gd,
			@BindingParam("win") Window winEmSalarypay,
			@BindingParam("type") Integer type) throws Exception {
		int msg = 0;
		String[] msg1 = null;
		int i = 0;
		int x = 0;

		try {

			String nowtime = "";
			if (type == 1) {// 当日发放，获取今天日期

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:00");
				Date date = new Date();
				nowtime = sdf.format(date);

			} else if (type == 2) {// 预发放，获取下个工作日(只能判断星期一至五)

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 09:00:00");
				SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");

				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if ("星期五".equals(sdf2.format(calendar.getTime()))) {
					calendar.add(Calendar.DATE, 3);
				} else if ("星期六".equals(sdf2.format(calendar.getTime()))) {
					calendar.add(Calendar.DATE, 2);
				} else {
					calendar.add(Calendar.DATE, 1);
				}

				nowtime = sdf.format(calendar.getTime());

			}

			List<Component> row = gd.getRows().getChildren();
			for (Object obj : row) {
				Row comp = (Row) obj;
				try {
					Checkbox ck = (Checkbox) comp.getChildren().get(1);

					if (ck.isChecked()) {

						if (!"".equals(nowtime)) {
							msg = ebe.pay(
									Integer.parseInt(ck.getValue().toString()),
									nowtime);

							if (msg == 1) {
								i++;
							}
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (i > 0) {

				x = emsbll.checkstate(taba_id, 4);

				if (x == 0) {
					// email通知客服
					email();

					msg1 = ebe.passtonext("2", taba_tapr_id,
							UserInfo.getUsername(), taba_id);
					Messagebox.show(msg1[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					winEmSalarypay.detach();
				} else {
					Messagebox.show("操作成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					winEmSalarypay.detach();
				}

			}

		} catch (Exception e) {
			Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	// 退回
	@Command("returnSalary")
	@NotifyChange({ "salaryWinList", "itemList" })
	public void returnSalary(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("win") Window win) throws Exception {
		try {
			if (Messagebox.show("确认退回选中的工资数据吗？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				List<Integer> list = getGdId(gdSalary);
				if (list.size() != 0) {
					EmSalary_AudtingOperateBll opBll = new EmSalary_AudtingOperateBll();
					String[] message = opBll.payReturnSalary(list,
							UserInfo.getUsername(), taba_id, taba_tapr_id);

					if ("1".equals(message[0])) {
						String[] msg1 = null;
						int x = 0;
						x = emsbll.checkstate(taba_id, 4);

						if (x == 0) {
							// email通知客服
							email();

							msg1 = ebe.passtonext("2", taba_tapr_id,
									UserInfo.getUsername(), taba_id);
							Messagebox.show("操作成功!", "INFORMATION",
									Messagebox.OK, Messagebox.INFORMATION);
							win.detach();
						} else {
							Messagebox.show("操作成功!", "INFORMATION",
									Messagebox.OK, Messagebox.INFORMATION);
							win.detach();
						}
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}

			} else {
				Messagebox.show("请勾选您需要退回的数据。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("工资退回出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 公司收款
	@Command("companyCollect")
	public void companyCollect() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(salaryList.get(0).getCid()));
		Window window = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_CollectMain.zul", null, map);
		window.doModal();
	}

	public List<EmSalaryDataModel> getSalaryWinList() {
		return salaryWinList;
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}

	public List<EmSalaryDataModel> getSalaryWinListSUM() {
		return salaryWinListSUM;
	}

	public List<EmSalaryBaseAddItemModel> getItemListSUM() {
		return itemListSUM;
	}
}
