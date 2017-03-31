package Controller.EmSalary;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;

import bll.EmSalary.EmSalary_SalarySelBll;

import Model.EmSalaryDataModel;
import Model.EmSalaryHistoryDataModel;
import Util.DateStringChange;
import Util.ExcelStyleUtil;
import Util.FileOperate;
import Util.UserInfo;

public class EmSalary_CoEmInfoController {
	private String emName = "";
	private String coCompany = "";
	private List<EmSalaryDataModel> baseList = new ArrayList<EmSalaryDataModel>();
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
	private DateStringChange dsc = new DateStringChange();

	public EmSalary_CoEmInfoController() {
	}

	// 查询
	@Command("search")
	@NotifyChange("baseList")
	public void search(@BindingParam("e_ownmonth") Datebox e_ownmonth,
			@BindingParam("s_ownmonth") Datebox s_ownmonth) {
		if (searchChk(e_ownmonth, s_ownmonth)) {

			String s_s_month = "";
			String s_e_month = "";
			s_s_month = dsc.DatetoSting(s_ownmonth.getValue(), "yyyyMM");
			s_e_month = dsc.DatetoSting(e_ownmonth.getValue(), "yyyyMM");
			baseList = bll.getSalaryDataCoEm(s_s_month, s_e_month, emName,
					coCompany);
		}
	}

	// 页面检查
	public boolean searchChk(Datebox e_ownmonth, Datebox s_ownmonth) {
		boolean result = true;
		if ("".equals(emName) && "".equals(coCompany)) {
			Messagebox.show("请输入员工姓名/编号 或者 公司简称/编号！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			result = false;
		} else if (e_ownmonth.getValue() == null
				|| s_ownmonth.getValue() == null) {
			Messagebox.show("请所属区间！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			result = false;
		}
		return result;
	}

	// excel导出
	@Command("excel")
	public void excel(@BindingParam("e_ownmonth") Datebox e_ownmonth,
			@BindingParam("s_ownmonth") Datebox s_ownmonth)
			throws RowsExceededException, WriteException, IOException {
		if (searchChk(e_ownmonth, s_ownmonth)) {
			String s_s_month = "";
			String s_e_month = "";
			s_s_month = dsc.DatetoSting(s_ownmonth.getValue(), "yyyyMM");
			s_e_month = dsc.DatetoSting(e_ownmonth.getValue(), "yyyyMM");
			baseList = bll.getSalaryDataCoEm(s_s_month, s_e_month, emName,
					coCompany);

			String filename = ""; // 文件名
			String absolutePath; // 服务器地址
			String filetype = ".xls"; // 文件类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);

			filename = nowtime + "_CoEm" + filetype;
			absolutePath = FileOperate.getAbsolutePath();
			// 创建excel
			WritableWorkbook wwb = Workbook
					.createWorkbook(new File(absolutePath
							+ "EmSalary/File/Download/History/" + filename));
			// 生成工作表
			WritableSheet sheet = wwb.createSheet("sheet1", 0);
			Label label = null;
			Number num = null;

			// 设置字体格式
			ExcelStyleUtil esU = new ExcelStyleUtil();
			WritableCellFormat wcf = esU.getFormatBold();
			WritableCellFormat wcf2 = esU.getFormatBorderLine();

			int k = 0;
			if (baseList.size() > 0) {
				// 生成表头
				label = new Label(0, k, "公司编号", wcf);
				sheet.addCell(label);
				label = new Label(1, k, "公司简称", wcf);
				sheet.addCell(label);
				label = new Label(2, k, "员工编号", wcf);
				sheet.addCell(label);
				label = new Label(3, k, "员工姓名", wcf);
				sheet.addCell(label);
				label = new Label(4, k, "所属月份", wcf);
				sheet.addCell(label);
				label = new Label(5, k, "用途", wcf);
				sheet.addCell(label);
				label = new Label(6, k, "财务备注", wcf);
				sheet.addCell(label);
				label = new Label(7, k, "发放状态", wcf);
				sheet.addCell(label);
				label = new Label(8, k, "税前工资", wcf);
				sheet.addCell(label);
				label = new Label(9, k, "社保个人部分", wcf);
				sheet.addCell(label);
				label = new Label(10, k, "住房公积金个人部分", wcf);
				sheet.addCell(label);
				label = new Label(11, k, "应纳税工资", wcf);
				sheet.addCell(label);
				label = new Label(12, k, "劳务报酬", wcf);
				sheet.addCell(label);
				label = new Label(13, k, "劳务报酬个人所得税", wcf);
				sheet.addCell(label);
				label = new Label(14, k, "个人所得税", wcf);
				sheet.addCell(label);
				label = new Label(15, k, "年终奖金", wcf);
				sheet.addCell(label);
				label = new Label(16, k, "年终奖金应纳税额", wcf);
				sheet.addCell(label);
				label = new Label(17, k, "年终奖金个税", wcf);
				sheet.addCell(label);
				label = new Label(18, k, "离职补偿金", wcf);
				sheet.addCell(label);
				label = new Label(19, k, "离职补偿金个税", wcf);
				sheet.addCell(label);
				label = new Label(20, k, "股票收入", wcf);
				sheet.addCell(label);
				label = new Label(21, k, "股票税", wcf);
				sheet.addCell(label);
				label = new Label(22, k, "报销费用", wcf);
				sheet.addCell(label);
				label = new Label(23, k, "实发工资", wcf);
				sheet.addCell(label);

				for (int j = 0; j < baseList.size(); j++) {// 循环List
					k = k + 1;
					// 插入excel
					label = new Label(0, k, String.valueOf(baseList.get(j)
							.getCid()), wcf2);
					sheet.addCell(label);
					label = new Label(1, k,
							baseList.get(j).getCoba_shortname(), wcf2);
					sheet.addCell(label);
					label = new Label(2, k, String.valueOf(baseList.get(j)
							.getGid()), wcf2);
					sheet.addCell(label);
					label = new Label(3, k, baseList.get(j).getName(), wcf2);
					sheet.addCell(label);
					label = new Label(4, k, String.valueOf(baseList.get(j)
							.getOwnmonth()), wcf2);
					sheet.addCell(label);

					String usage_str = baseList.get(j).getEsda_usage_typestr();
					if (baseList.get(j).getEsda_rp_reason() != 0) {
						usage_str = usage_str + " - 重发";
					}
					label = new Label(5, k, usage_str, wcf2);

					sheet.addCell(label);
					label = new Label(6, k,
							baseList.get(j).getEsda_fd_remark(), wcf2);
					sheet.addCell(label);
					label = new Label(7, k, baseList.get(j)
							.getEsda_payment_statestr(), wcf2);
					sheet.addCell(label);
					num = new Number(8, k, baseList.get(j)
							.getEsda_total_pretax().doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(9, k, baseList.get(j).getEsda_siop()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(10, k, baseList.get(j).getEsda_hafop()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(11, k, baseList.get(j).getEsda_tax_base()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(12, k, baseList.get(j)
							.getEsda_lw_tax_base().doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(13, k, baseList.get(j).getEsda_lw_tax()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(14, k, baseList.get(j).getEsda_tax()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(15, k, baseList.get(j).getEsda_db()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(16, k, baseList.get(j).getEsda_db_tax_base()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(17, k, baseList.get(j).getEsda_db_tax()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(18, k, baseList.get(j).getEsda_dc()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(19, k, baseList.get(j).getEsda_dc_tax()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(20, k, baseList.get(j).getEsda_stock()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(21, k, baseList.get(j).getEsda_stock_tax()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(22, k, baseList.get(j).getEsda_write_off()
							.doubleValue(), wcf2);
					sheet.addCell(num);
					num = new Number(23, k, baseList.get(j).getEsda_pay()
							.doubleValue(), wcf2);
					sheet.addCell(num);
				}

			}
			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			String filePath = "EmSalary/File/Download/History/" + filename;
			FileOperate.download(filePath);
		}
	}

	public String getEmName() {
		return emName;
	}

	public void setEmName(String emName) {
		this.emName = emName;
	}

	public String getCoCompany() {
		return coCompany;
	}

	public void setCoCompany(String coCompany) {
		this.coCompany = coCompany;
	}

	public List<EmSalaryDataModel> getBaseList() {
		return baseList;
	}

	public void setBaseList(List<EmSalaryDataModel> baseList) {
		this.baseList = baseList;
	}

}
