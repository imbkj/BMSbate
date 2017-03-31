package Controller.EmPay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;
import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmPay.EmPa_SelectBll;

public class EmPa_CashierListController {
	private final EmPa_SelectBll bll = new EmPa_SelectBll();
	private String sql = " and empa_state in(6,8)";
	private List<EmPayModel> list = new ListModelList<>();
	private EmPayModel m = new EmPayModel();

	public EmPa_CashierListController() {
		list = bll.getEmPayInfoList(sql);
	}

	@Command
	@NotifyChange("list")
	public void search(@BindingParam("ownmonth") Date ownmonth)
			throws NumberFormatException, ParseException {
		if (ownmonth != null) {
			m.setOwnmonth(Integer.parseInt(DateStringChange
					.Stringtoownmonth(ownmonth)));
		} else {
			m.setOwnmonth(null);
		}
		list = bll.getEmPayInfoList(m);
	}

	// 打开单个修改页面——出纳
	@Command
	@NotifyChange("list")
	public void singleUpdate(@BindingParam("model") EmPayModel model) {
		// 判断时间是否在当天三点前
		/*
		 * if (!ifExceedTime()) { Messagebox.show("今天已过下午三点，请明天再操作", "提示",
		 * Messagebox.OK, Messagebox.ERROR); return; }
		 */
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmPa_CashierBatchApproval.zul", null, map);
		window.doModal();
		list = bll.getEmPayInfoList(sql);

	}

	// 导出报销单
	@Command
	public void OutWipeExcel(@BindingParam("model") EmPayModel model) {
		plyUtil ply = new plyUtil();
		String path = "/../../EmPay/file/";
		String paths = "EmPay/downfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "wipe.xls";
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "雇员费用报销单" + sdf.format(date) + "_"
				+ UserInfo.getUsername() + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		try {
			ExcelService exl = new OutWipeExcelImpl(solpath, absolutePath
					+ paths + newfilename, model);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		FileOperate.download(paths + newfilename);
	}

	// 导出支付单
	@Command
	public void OutPayExcel(@BindingParam("model") EmPayModel model) {
		plyUtil ply = new plyUtil();
		String path = "/../../EmPay/file/";
		String paths = "EmPay/downfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "pay.xls";
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "雇员费用支付单" + sdf.format(date) + "_"
				+ UserInfo.getUsername() + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		try {
			ExcelService exl = new OutPayExcelImpl(solpath, absolutePath
					+ paths + newfilename, model);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		FileOperate.download(paths + newfilename);
	}

	private boolean ifExceedTime() {
		boolean flag = false;
		Date today = new Date();
		if (today.getHours() <= 14)
			return true;
		return flag;
	}

	public List<EmPayModel> getList() {
		return list;
	}

	public void setList(List<EmPayModel> list) {
		this.list = list;
	}

	public EmPayModel getM() {
		return m;
	}

	public void setM(EmPayModel m) {
		this.m = m;
	}
}
