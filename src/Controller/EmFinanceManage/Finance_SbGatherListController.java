package Controller.EmFinanceManage;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;

import bll.EmFinanceManage.SbGatherBll;
import Model.SbGatherModel;
import Util.plyUtil;

public class Finance_SbGatherListController {
	private SbGatherBll bll = new SbGatherBll();
	private List<SbGatherModel> list = new ArrayList<SbGatherModel>();
	private String cid = "", company = "", client = "", single = "";
	String ownmonths = "", ifsingle = "";
	private String zztotal = "0.00", wttotal = "0.00", wbtotal = "0.00",
			pqtotal = "0.00";
	private String sql = "";

	public Finance_SbGatherListController() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String monthstr = "";
		if (month < 10) {
			monthstr = "0" + month;
		} else {
			monthstr = "" + month;
		}
		ownmonths = year + "" + monthstr;
		list = bll.getSbGatherList(sql, ownmonths, ifsingle);
		getTotalInfo(ownmonths);
	}

	// 计算中智户和委托户的扣款总额
	private void getTotalInfo(String ownmonth) {
		BigDecimal s[] = bll.getSbTotalInfo(ownmonth);
		BigDecimal zztotaldf = s[0];
		BigDecimal wttotaldf = s[1];
		BigDecimal wbtotaldf = s[2];
		BigDecimal pqtotaldf = s[3];
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		zztotal = numberFormat.format(zztotaldf);
		wttotal = numberFormat.format(wttotaldf);
		wbtotal = numberFormat.format(wbtotaldf);
		pqtotal = numberFormat.format(pqtotaldf);
	}

	@Command
	@NotifyChange({ "list", "zztotal", "wttotal", "wbtotal", "pqtotal" })
	public void search(@BindingParam("ownmonth") Date ownmonth) {
		if (ownmonth == null) {
			Messagebox.show("请选择所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		ifsingle = "";
		sql = "";
		if (cid != null && !cid.equals("")) {
			sql = sql + " and a.cid=" + cid;
		}
		if (company != null && !company.equals("")) {
			sql = sql + " and coba_company like '%" + company + "%'";
		}
		if (client != null && !client.equals("")) {
			sql = sql + " and coba_client like '%" + client + "%'";
		}
		if (ownmonth != null) {
			ownmonths = dateToStr(ownmonth);
			sql = sql + " and z.ownmonth=" + ownmonths;
		}
		if (single != null && !single.equals("")) {
			if (single.equals("中智户")) {
				ifsingle = "0";
			} else if (single.equals("委托户")) {
				ifsingle = "2";
			} else if (single.equals("外包户")) {
				ifsingle = "3";
			} else if (single.equals("派遣户")) {
				ifsingle = "4";
			}
		}
		getTotalInfo(ownmonths);
		list = bll.getSbGatherList(sql, ownmonths, ifsingle);
	}

	// 弹出修改页面
	@Command
	@NotifyChange("list")
	public void edit(@BindingParam("model") SbGatherModel model) {
		Map map = new HashMap<>();
		map.put("cid", model.getCid());
		map.put("type", "2");
		Window window = (Window) Executions.createComponents(
				"Finance_GatherInfoEdit.zul", null, map);
		window.doModal();
		list = bll.getSbGatherList(sql, ownmonths, ifsingle);
	}

	// 生成报盘
	@Command
	public void addoffer() {
		Map map = new HashMap<>();
		map.put("single", "2");
		map.put("ownmonth", ownmonths);
		Window window = (Window) Executions.createComponents(
				"Finance_SbCreateTxt.zul", null, map);
		window.doModal();
	}

	@Command
	public void OutExcel() throws Exception {

		plyUtil ply = new plyUtil();
		String path = "/../../EmFinanceManage/downfile/";// 导出保存路径
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "社保分录" + sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName = "社保台账";// Excel表格名
		// 定义表头
		String[] title = { "序号", "委托机构", "所属月份", "用友编号", "客户类型", "科目代码",
				"公司编号", "公司名称", "社保局扣款总额", "其中补缴金额", "社保收款金额", "差额", "客服",
				"到账情况" };
		try {
			// 使用自己写的Excel导出实现类把数据写入Excel
			ExcelService exl = new Finance_SbOutExcelImpl(file, sheetName,
					title, list);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Filedownload.save(file, "xls");// 导出Excel
		// file.delete();
	}

	public List<SbGatherModel> getList() {
		return list;
	}

	public void setList(List<SbGatherModel> list) {
		this.list = list;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getZztotal() {
		return zztotal;
	}

	public void setZztotal(String zztotal) {
		this.zztotal = zztotal;
	}

	public String getWttotal() {
		return wttotal;
	}

	public void setWttotal(String wttotal) {
		this.wttotal = wttotal;
	}

	public String getWbtotal() {
		return wbtotal;
	}

	public void setWbtotal(String wbtotal) {
		this.wbtotal = wbtotal;
	}

	public String getPqtotal() {
		return pqtotal;
	}

	public void setPqtotal(String pqtotal) {
		this.pqtotal = pqtotal;
	}

	private String dateToStr(Date date) {
		String datestr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		if (date != null) {
			try {
				datestr = format.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datestr;
	}
}
