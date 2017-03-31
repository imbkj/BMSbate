package Controller.SalaryPaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Util.FileOperate;
import bll.SalaryPaper.SalaryPaperBll;

public class SalaryPaper_EmSalaryDataIncludeController {

	private String cid = Executions.getCurrent().getParameter("cid");
	private String ownmonth = Executions.getCurrent().getParameter("ownmonth");
	private SalaryPaperBll spb = new SalaryPaperBll();
	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryBaseAddItemModel> itemList;

	public SalaryPaper_EmSalaryDataIncludeController() {
		init();
	}

	public void init() {
		if (cid != null && ownmonth != null) {
			salaryList = spb.getSalaryList(Integer.valueOf(cid), ownmonth); // 工资单数据
			itemList = spb.getItemList(Integer.valueOf(cid), ownmonth); // 工资单项目
			setEmSalaryDataModelOfItemList(salaryList, itemList);
			salaryWinList = salaryList;
			for (int i = 0; i < salaryWinList.size(); i++) {
				List<String> sendType = new ArrayList<String>();
				String type = salaryWinList.get(i).getEsin_cost_type();
				// 如果为空，默认是emailhtml
				if ("".equals(type) || type == null) {
					type = "emailhtml,";
				}
				String[] sendTypes = type.split(",");
				for (int j = 0; j < sendTypes.length; j++) {
					sendType.add(sendTypes[j]);
				}
				salaryWinList.get(i).setSendType(sendType);
			}
		}

	}

	// 发送工资单
	@Command
	@NotifyChange({ "salaryWinList", "salaryList", "itemList" })
	public void send(@BindingParam("m") EmSalaryDataModel m,
			@BindingParam("st") String st, @BindingParam("resend") String resend) {
		if (resend != null) {
			for (int i = 0; i < m.getSendType().size(); i++) {
				if (!m.getSendType().get(i).equals("网上中智")
						|| !m.getSendType().get(i).equals("纸质")) {
					st = m.getSendType().get(i);
				}
			}
		}
		Window window = null;
		// System.out.println(st);
		switch (st) {
		case "网上中智":

			break;
		case "email附件pdf":
			Map<String, EmSalaryDataModel> mapPDF = new HashMap<String, EmSalaryDataModel>();
			mapPDF.put("m", m);
			window = (Window) Executions.createComponents(
					"/SalaryPaper/SalaryPaper_SendOfPDF.zul", null, mapPDF);
			//System.out.println(FileOperate.getAbsolutePath());
			window.doModal();
			break;
		case "emailText":
			Map<String, EmSalaryDataModel> mapText = new HashMap<String, EmSalaryDataModel>();
			mapText.put("m", m);
			window = (Window) Executions.createComponents(
					"/SalaryPaper/SalaryPaper_SendOfText.zul", null, mapText);
			//System.out.println(FileOperate.getAbsolutePath());
			window.doModal();
			break;
		case "emailhtml":
			Map<String, EmSalaryDataModel> map = new HashMap<String, EmSalaryDataModel>();
			map.put("m", m);
			window = (Window) Executions.createComponents(
					"/SalaryPaper/SalaryPaper_SendOfHtml.zul", null, map);
			//System.out.println(FileOperate.getAbsolutePath());
			window.doModal();
			break;
		case "email附件excel":
			Map<String, EmSalaryDataModel> mapExcel = new HashMap<String, EmSalaryDataModel>();
			mapExcel.put("m", m);
			window = (Window) Executions.createComponents(
					"/SalaryPaper/SalaryPaper_SendOfExcel.zul", null, mapExcel);
			//System.out.println(FileOperate.getAbsolutePath());
			window.doModal();
			break;
		default:
			break;
		}
		init();
	}

	// 发送工资单
	@Command
	@NotifyChange({ "salaryWinList", "salaryList", "itemList" })
	public void sendAll(@BindingParam("gdSalary") Grid gdSalary) {
		List<Component> row = gdSalary.getRows().getChildren();
		int page = gdSalary.getActivePage();
		int size = gdSalary.getPageSize();
		int min = page * size;
		int max = (page + 1) * size;
		try {
			for (int i = min; i < max; i++) {
				try {
					Checkbox ck = (Checkbox) row.get(i).getChildren().get(0)
							.getChildren().get(0);
					if (ck.isChecked()) {
						EmSalaryDataModel sm = new EmSalaryDataModel();
						sm = (EmSalaryDataModel) ((Row) row.get(i)).getValue();

						for (int j = 0; j < sm.getSendType().size(); j++) {// 一个员工有两种工资单格式
							String[] msg;
							switch (sm.getSendType().get(j)) {
							case "email附件pdf":
								msg = spb.sendPDFEmail(sm);
								if ("1".equals(msg[0])) {

								} else {

								}
								break;
							case "emailText":
								msg = spb.sendTextEmail(sm);
								if ("1".equals(msg[0])) {

								} else {

								}
								break;
							case "emailhtml":
								msg = spb.sendHTMLEmail(sm);
								if ("1".equals(msg[0])) {
								} else {
								}
								break;
							case "email附件excel":
								msg = spb.sendExcelEmail(sm);
								if ("1".equals(msg[0])) {
								} else {
								}
								break;
							default:
								break;
							}
						}
					}

				} catch (Exception e) {
				}
			}

			Messagebox.show("发送成功", "操作提示", Messagebox.OK, Messagebox.NONE);
		} catch (Exception e) {
			Messagebox.show("发送出错", "error", Messagebox.OK, Messagebox.ERROR);
		}

		init();
	}

	// 初始化EmSalaryDataModel的itemList
	private void setEmSalaryDataModelOfItemList(List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setGzdItemList(itList);
			} catch (Exception e) {
			}
		}
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
					Checkbox ck = (Checkbox) row.get(i).getChildren().get(0)
							.getChildren().get(0);
					ck.setChecked(ifCheck);

				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<EmSalaryBaseAddItemModel> itemList) {
		this.itemList = itemList;
	}

	public List<EmSalaryDataModel> getSalaryWinList() {
		return salaryWinList;
	}

	public void setSalaryWinList(List<EmSalaryDataModel> salaryWinList) {
		this.salaryWinList = salaryWinList;
	}

}
