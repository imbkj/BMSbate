package bll.EmHouse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.zul.ListModelList;

import dal.EmHouse.Emhouse_BjDal;

import Model.EmArchivePaymentModel;
import Model.EmHouseBJModel;
import Util.FileOperate;

public class EmHouse_BjSearchBll {

	public List<EmHouseBJModel> houselist(EmHouseBJModel em) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		list = dal.housebjList(em, false, null, false, null,
				"ownmonth,emhb_company,emhb_name", false);

		return list;
	}

	public String export(EmHouseBJModel em) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		list = dal.housebjList(em, false, null, false, null,
				"ownmonth,emhb_company,emhb_name", false);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);
		String exfileName = sdf.format(trialTime) + "bj";

		if (list.size() > 0) {
			String absolutePath = FileOperate.getAbsolutePath();
			// 创建Excel文件
			WritableWorkbook wb;
			try {
				wb = Workbook
						.createWorkbook(new File(absolutePath
								+ "OfficeFile/DownLoad/EmHouse/" + exfileName
								+ ".xls"));

				// 创建Excel工作表
				WritableSheet ws = wb.createSheet("sheet1", 0);

				jxl.write.Label lbl = null;
				WritableCellFormat wcf = getBodyCellStyle();
				String[] str = { "序号", "公司简称", "所属月份", "缴费月份", "姓名", "单位公积金",
						"个人公积金", "身份证", "基数", "比例", "补缴起始月", "补缴终止月", "总额",
						"客服", "状态" };
				ws.insertRow(0);
				ws.setRowView(0, 375, false);
				Integer i = 0;
				for (String s : str) {
					lbl = new jxl.write.Label(i, 0, s, wcf);
					ws.setColumnView(i, 13);
					ws.addCell(lbl);
					i++;
				}
				i = 1;
				for (EmHouseBJModel ebm : list) {
					lbl = new jxl.write.Label(0, i, i.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(1, i, ebm.getEmhb_company(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(2, i, ebm.getOwnmonth()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(3, i, ebm.getEmhb_feemonth()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(4, i, ebm.getEmhb_name()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(5, i, ebm.getEmhb_companyid()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(6, i, ebm.getEmhb_houseid()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(7, i, ebm.getEmhb_idcard()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(8, i, String.valueOf(ebm
							.getEmhb_radix()), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(9, i, String.valueOf(ebm
							.getEmhb_cpp()), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(10, i, ebm.getEmhb_startmonth()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(11, i, ebm.getEmhb_stopmonth()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(12, i, ebm.getEmhb_total()
							.toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(13, i,
							ebm.getClient().toString(), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(14, i,
							ebm.getStates().toString(), wcf);
					ws.addCell(lbl);
					i++;
				}
				// 写入数据
				wb.write();
				// 关闭文件
				wb.close();

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return exfileName;
	}

	public WritableCellFormat getBodyCellStyle() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat
					.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);

		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}

	public List<EmHouseBJModel> combolist(EmHouseBJModel em, String comboItem) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		String order = "";
		boolean distinct = false;
		boolean top = false;
		Integer topNums = 0;
		String columns = null;
		if (comboItem.equals("company")) {
			top = true;
			topNums = 10;
			distinct = true;
			columns = "a.cid,emhb_company";
			order = "emhb_company";
		} else if (comboItem.equals("emp")) {
			top = true;
			topNums = 10;
			distinct = true;
			columns = "a.gid,emhb_name";
			order = "emhb_name";
		} else if (comboItem.equals("ownmonth")) {
			distinct = true;
			columns = "ownmonth";
			order = "ownmonth desc";
		} else if (comboItem.equals("feemonth")) {
			distinct = true;
			columns = "emhb_feemonth";
			order = "emhb_feemonth desc";
		}
		list = dal.housebjList(em, top, topNums, distinct, columns, order,
				false);

		return list;
	}
}
