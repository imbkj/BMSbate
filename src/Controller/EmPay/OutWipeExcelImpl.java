package Controller.EmPay;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import service.ExcelService;
import Model.EmPayModel;
import bll.EmPay.EmPa_SelectBll;

public class OutWipeExcelImpl implements ExcelService {
	private String file;// excel文件对象
	private String filename;// sheet名称
	private EmPayModel model;
	private final EmPa_SelectBll bll = new EmPa_SelectBll();
	private List<EmPayModel> list = new ArrayList<EmPayModel>();;

	public OutWipeExcelImpl(String file, String filename, EmPayModel model) {
		this.file = file;
		this.filename = filename;
		this.model = model;
		list = bll.getEmPayGroupList(" and empa_number='" + model.getEmpa_number()
				+ "'");
	}

	@Override
	public void writeExcel() throws Exception {

		Workbook wk = Workbook.getWorkbook(new File(file));
		// 使用模板创建
		WritableWorkbook wbook = Workbook
				.createWorkbook(new File(filename), wk);
		WritableSheet sheet = wbook.getSheet(0);

		// 开始写入内容
		Label firstlabel = null;
		// firstlabel = new Label(1, 1, "朗新科技股份",// 第二行第二列
		// getHeader2());
		// sheet.addCell(firstlabel);

		Label twolabel = null;
		twolabel = new Label(1, 2, list.get(0).getEmpa_paymenttype(),// 第三行第二列
				getHeader());
		sheet.addCell(twolabel);

		Label dateLabel = null;
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		String date = "日期：" + calendar.get(Calendar.YEAR) + "年"
				+ (calendar.get(Calendar.MONTH)+1) + "月"
				+ calendar.get(Calendar.DATE) + "日";
		dateLabel = new Label(6, 1, date, getHeader());
		sheet.addCell(dateLabel);

		int row = 4;
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal taxtotal = BigDecimal.ZERO;
		BigDecimal attotal = BigDecimal.ZERO;
		String cm = "";
		String dm = "";
		String am = "";
		String feetype = "";
		for (int i = 0; i < list.size(); i++) {

			// 用model获取每一行数据
			EmPayModel m = list.get(i);
			if (m.getEmpa_manager_checkname() != null) {
				cm = m.getEmpa_manager_checkname();
			}
			if (m.getEmpa_depmanager_checkname() != null) {
				dm = m.getEmpa_depmanager_checkname();
			}
			if (m.getEmpa_finance_checkname() != null) {
				am = m.getEmpa_finance_checkname();
			}
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, m.getNum().toString(), getHeader());
			sheet.addCell(label);

			switch (m.getEmpa_class()) {
			case "活动":
			case "商保":
			case "社保":
			case "公积金":
				feetype = "交通费";
				break;
			case "体检":
			case "书报":
			case "档案费":
				feetype = m.getEmpa_class();
				break;
			default:
				break;
			}
			label = new Label(1, row, feetype, getHeader());
			sheet.mergeCells(1, row, 2, row);
			sheet.addCell(label);

			String company = "";
			company = m.getCoba_shortname();
			label = new Label(3, row, company, getHeader());
			sheet.addCell(label);

			String ownmonth = "";
			ownmonth = m.getOwnmonth() + "-" + m.getOwnmonthend();
			label = new Label(4, row, ownmonth, getHeader());
			sheet.addCell(label);

			BigDecimal aftertax = BigDecimal.ZERO;
			if (m.getEmpa_aftertax() != null) {
				aftertax = m.getEmpa_aftertax();
			}
			label = new Label(5, row, aftertax.toString(), getHeader());
			sheet.addCell(label);
			total = total.add(aftertax);
			label = new Label(6, row, m.getEmpa_payclass(), getHeader());
			sheet.addCell(label);

			label = new Label(7, row, m.getEmpa_remark(), getHeader());
			sheet.addCell(label);

			// sheet.mergeCells(6, row, 7, row);
			sheet.setRowView(row, 350);

			row++;
		}

		if (row <= 8) {
			int num = 9 - row;
			for (int k = 0; k < num; k++) {
				Label label = null;

				label = new Label(0, row, "", getHeader());
				sheet.addCell(label);
				label = new Label(1, row, "", getHeader());
				sheet.addCell(label);
				sheet.mergeCells(1, row, 2, row);
				label = new Label(3, row, "", getHeader());
				sheet.addCell(label);
				label = new Label(4, row, "", getHeader());
				sheet.addCell(label);
				label = new Label(5, row, "", getHeader());
				sheet.addCell(label);
				label = new Label(6, row, "", getHeader());
				sheet.addCell(label);
				label = new Label(7, row, "", getHeader());
				sheet.addCell(label);

				// sheet.mergeCells(6, row, 7, row);
				sheet.setRowView(row, 350);
				row++;
			}
		}

		Label bolowlabel = null;
		bolowlabel = new Label(0, row, "金额合计", getBottom());
		sheet.addCell(bolowlabel);

		sheet.mergeCells(0, row, 4, row);
		sheet.setRowView(row, 250);

		bolowlabel = new Label(5, row, total.toString(), getBottom());
		sheet.addCell(bolowlabel);
		sheet.setRowView(row, 250);
		bolowlabel = new Label(2, row + 1, "总经理经理:" + cm, getBottom2());
		sheet.addCell(bolowlabel);
		sheet.mergeCells(2, row + 1, 3, row + 1);
		sheet.setRowView(row + 1, 250);

		bolowlabel = new Label(4, row + 1, "部门经理:" + dm, getBottom2());
		sheet.addCell(bolowlabel);
		sheet.mergeCells(4, row + 1, 5, row + 1);
		sheet.setRowView(row + 1, 250);
		// bolowlabel = new Label(5, row + 1, "", getBottom2());
		// sheet.addCell(bolowlabel);
		// bolowlabel = new Label(6, row + 1, "", getBottom2());
		// sheet.addCell(bolowlabel);

		// sheet.mergeCells(5, row + 1, 6, row + 1);
		// sheet.setRowView(row + 1, 250);

		bolowlabel = new Label(6, row + 1, "经办人:" + am, getBottom2());
		sheet.addCell(bolowlabel);
		sheet.mergeCells(6, row + 1, 7, row + 1);
		sheet.setRowView(row + 1, 250);

		bolowlabel = new Label(7, row, "", getBottom2());
		sheet.addCell(bolowlabel);
		bolowlabel = new Label(0, row + 1, "", getBottom2());
		sheet.addCell(bolowlabel);
		bolowlabel = new Label(1, row + 1, "", getBottom2());
		sheet.addCell(bolowlabel);

		// 写入数据
		wbook.write();
		// 关闭文件
		wbook.close();
	}

	/**
	 * 设置头的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getHeader() {
		WritableCellFormat format = new WritableCellFormat();
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置头的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getHeader2() {
		WritableCellFormat format = new WritableCellFormat();
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置底部的样式；边框：左上右
	 * 
	 * @return
	 */
	public static WritableCellFormat getBottom() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框

		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置底部的样式；边框左右
	 * 
	 * @return
	 */
	public static WritableCellFormat getBottom2() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置底部的样式；边框左右下
	 * 
	 * @return
	 */
	public static WritableCellFormat getBottom4() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setAlignment(jxl.format.Alignment.LEFT);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.BOTTOM);// 上下居中
			format.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框

		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置底部的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getFirstHeader() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 14,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框

		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	@Override
	public void exportExcel() throws Exception {
		// TODO Auto-generated method stub

	}
}
