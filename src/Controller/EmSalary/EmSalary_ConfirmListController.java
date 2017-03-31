package Controller.EmSalary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_ChangeDataExportBll;
import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

import Model.CoBaseModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.LoginModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;

public class EmSalary_ConfirmListController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());

	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryListSUM;// 合计
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryDataModel> salaryWinListSUM;// 合计
	private List<EmSalaryBaseAddItemModel> itemList;
	private List<EmSalaryBaseAddItemModel> itemListSUM;// 合计
	private EmSalary_SalarySelBll bll;
	private CoBaseModel cm;

	public EmSalary_ConfirmListController() {
		bll = new EmSalary_SalarySelBll();
		salaryList = bll.getSalaryDataByCidMonth(cid, ownmonth);
		itemList = bll.getItemInfoByCidMonthNoPay(cid, ownmonth);
		setEmSalaryDataModelOfItemList(salaryList, itemList);
		salaryWinList = salaryList;

		// 合计
		salaryListSUM = bll.getSalaryDataSUM(
				" AND ed.cid=" + String.valueOf(cid) + " AND ed.ownmonth="
						+ String.valueOf(ownmonth), null);
		itemListSUM = itemList;
		setEmSalaryDataModelOfItemListSUM(salaryListSUM, itemListSUM);
		salaryWinListSUM = salaryListSUM;

		cm = bll.getCobaseByCid(cid);
	}

	// 导出工资变动数据
	@Command("exportChangeData")
	public void exportChangeData() {
		try {
			EmSalary_ChangeDataExportBll exBll = new EmSalary_ChangeDataExportBll();
			String fileName = "cid" + cid + "_" + ownmonth + "工资变动数据";
			String path = "EmSalary/File/Upload/SalaryOwnmonthChangeData/"
					+ fileName + ".xls";
			if (exBll.createExcel(path, fileName, ownmonth,
					exBll.getChangeData(cid, ownmonth, 0))) {
				FileOperate.download(path);
			} else {
				Messagebox.show("数据导出失败。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据导出出错。", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
	}

	// 判断用户是否为薪酬负责人
	private boolean checkGzaddname(String gzaddname) {
		String username = UserInfo.getUsername();
		if ("林少斌".equals(username))
			return true;
		if (username.equals(gzaddname)) {
			return true;
		} else {
			Messagebox.show("您不是该公司薪酬负责人，没有权限操作该步骤!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		return false;
	}

	// 批量发放工资
	@Command("confirmSalary")
	@NotifyChange("salaryWinList")
	public void confirmSalary(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		EmSalary_SalaryOperateBll opBll = new EmSalary_SalaryOperateBll();
		try {
			// 判断用户是否为薪酬负责人
			if (checkGzaddname(cm.getCoba_gzaddname())) {
				List<Integer> list = getGdId(gdSalary);
				if (list.size() != 0) {
					String[] message = opBll.confirmSalary(list,
							UserInfo.getUsername(), cid, ownmonth);
					if (message[0].equals("1")) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						salaryList = bll.getSalaryDataByCidMonth(cid, ownmonth);
						setEmSalaryDataModelOfItemList(salaryList, itemList);
						salaryWinList = salaryList;

						// 刷新父窗口
						Binder bind = (Binder) view.getParent().getAttribute(
								"binder");
						bind.postCommand("refreshWin", null);
						// win.detach();
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("请勾选您需要确认的数据。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("确认工资出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
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
				if (((Checkbox) row.getChildren().get(1).getChildren().get(0))
						.isChecked()) {
					list.add(((EmSalaryDataModel) row.getValue()).getEsda_id());
				}
			} catch (Exception e) {

			}
		}
		return list;
	}

	// 检索
	@Command("changeList")
	@NotifyChange("salaryWinList")
	public void changeList(@BindingParam("ibGid") Intbox ibGid,
			@BindingParam("txtName") Textbox txtName,
			@BindingParam("cbUsageType") Combobox cbUsageType) {
		if (!"".equals(txtName.getValue()) || ibGid.getValue() != null
				|| cbUsageType.getValue() != null) {
			if (ibGid.getValue() == null) {
				salaryWinList = getNewList(0, txtName.getValue(),
						cbUsageType.getValue());
			} else {
				salaryWinList = getNewList(ibGid.getValue(),
						txtName.getValue(), cbUsageType.getValue());
			}
		} else {
			salaryWinList = salaryList;
		}
	}

	// 检索数据
	private List<EmSalaryDataModel> getNewList(int gid, String name,
			String usageType) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		for (EmSalaryDataModel m : salaryList) {
			if (gid != 0) {
				if (gid != m.getGid())
					continue;
			}
			if (name != null && !"".equals(name)) {
				try {
					if (!RegexUtil.isExists(name, m.getName()))
						continue;
				} catch (Exception e) {
					continue;
				}
			}
			if (usageType != null && !"".equals(usageType)) {
				if (!usageType.equals(m.getEsda_usage_typestr()))
					continue;
			}
			list.add(m);
		}
		return list;
	}

	// 点击姓名查看工资的个人信息
	@Command("SelEmbase")
	public void SelEmbase(@BindingParam("lbl") Label lbl) {
		int esda_id = ((EmSalaryDataModel) ((Row) lbl.getParent()).getValue())
				.getEsda_id();
		int esda_payment_state = ((EmSalaryDataModel) ((Row) lbl.getParent())
				.getValue()).getEsda_payment_state();

		String url = "";
		if (esda_payment_state != 2) {
			url = "EmSalary_EmbaseUpdate.zul";
		} else {
			url = "EmSalary_Embase.zul";
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
					Checkbox ck = (Checkbox) row.get(i).getChildren().get(1)
							.getChildren().get(0);
					if (((EmSalaryDataModel) ((Row) row.get(i)).getValue())
							.getEsda_payment_state() == 3) {
						ck.setChecked(ifCheck);
					}
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
	}

	// 打开修改工资页面
	@Command("upSalary")
	public void upSalary(@BindingParam("win") Window win,@ContextParam(ContextType.VIEW) Component view)
			throws InterruptedException {
		// 判断用户是否为薪酬负责人
		if (checkGzaddname(UserInfo.getUsername())) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cid", String.valueOf(cid));
			map.put("ownmonth", String.valueOf(ownmonth));
			Window window = (Window) Executions.createComponents(
					"EmSalaryBase_Update.zul", view, map);
			window.doModal();
			win.detach();
		} else {
			Messagebox.show("必须是薪酬负责人才能打开修改工资页面！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 系统短信提醒
	@Command("sendRemind")
	public void sendRemind(@BindingParam("m") EmSalaryDataModel m) {
		try {
			String message = "(" + m.getGid() + ")" + m.getName()
					+ "的工资银行信息不全。" + m.getIfBankCon()
					+ "请进入该员工的业务中心，薪酬服务->薪酬信息新增处，补充完整。";
			String title = "(" + m.getGid() + ")" + m.getName() + "的工资银行信息不全。";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "业务类型");// 业务类型:来自WfClass的wfcl_name
			map.put("id", m.getEsda_id());// 业务表id
			map.put("tablename", "EmSalaryData");// 业务表名

			List<LoginModel> mlist = new ArrayList<LoginModel>();
			LoginModel lm = new LoginModel();
			lm.setLog_name(cm.getCoba_client());
			mlist.add(lm);
			map.put("list", mlist);// 默认收件人list
			map.put("content", message);
			map.put("title", title);
			Window window = (Window) Executions.createComponents(
					"../SysMessage/Message_Add.zul", null, map);
			window.doModal();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("系统短信，出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 导出数据
	@Command("exportData")
	public void exportData() {
		try {
			if (salaryList.size() > 0) {
				String filePath = "EmSalary/File/Download/" + "cid" + cid + "_"
						+ ownmonth + "Audting_" + UserInfo.getUsername()
						+ ".xls";
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
		final int dataCell = 6;
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
			WritableSheet sheet = wwb.createSheet("cid" + cid + "_" + ownmonth
					+ "工资待确认数据", 0);
			// 设置字体格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体
			WritableCellFormat wcf = new WritableCellFormat(wf);
			// 获取公司信息
			EmSalaryDataModel m = salaryList.get(0);
			// 插入第一行
			jxl.write.Label label = null;
			jxl.write.Number num = null;
			label = new jxl.write.Label(1, 0, String.valueOf(m.getOwnmonth()),
					wcf);
			sheet.addCell(label);
			label = new jxl.write.Label(4, 0, bll.getCoShortName(m.getCid())
					+ "工资待确认", wcf);
			sheet.addCell(label);
			label = new jxl.write.Label(0, 1, "", wcf);
			sheet.addCell(label);
			// 插入表头
			try {
				label = new jxl.write.Label(0, dataRow - 1, "员工编号", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(1, dataRow - 1, "员工姓名", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(2, dataRow - 1, "用途", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(3, dataRow - 1, "财务备注", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(4, dataRow - 1, "状态", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(5, dataRow - 1, "实发工资", wcf);
				sheet.addCell(label);
				for (int i = 0; i < itemList.size(); i++) {
					if (itemList.get(i).getCsii_fd_state() == 1) {
						label = new jxl.write.Label(i + dataCell, dataRow - 1,
								itemList.get(i).getCsii_item_name(), wcf);
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
					label = new jxl.write.Label(1, dataRow + i, salaryList.get(
							i).getName());
					sheet.addCell(label);
					label = new jxl.write.Label(2, dataRow + i, salaryList.get(
							i).getEsda_usage_typestr());
					sheet.addCell(label);
					label = new jxl.write.Label(3, dataRow + i, salaryList.get(
							i).getEsda_remark());
					sheet.addCell(label);
					label = new jxl.write.Label(4, dataRow + i, salaryList.get(
							i).getEsda_payment_statestr());
					sheet.addCell(label);
					num = new jxl.write.Number(5, dataRow + i, salaryList
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

	public List<EmSalaryDataModel> getSalaryWinList() {
		return salaryWinList;
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}

	public CoBaseModel getCm() {
		return cm;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public List<EmSalaryDataModel> getSalaryWinListSUM() {
		return salaryWinListSUM;
	}

	public List<EmSalaryBaseAddItemModel> getItemListSUM() {
		return itemListSUM;
	}

}
