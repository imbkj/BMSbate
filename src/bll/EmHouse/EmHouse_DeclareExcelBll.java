package bll.EmHouse;

import java.io.File;
import java.sql.SQLException;
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

import Model.CoHousingFundModel;
import Model.EmHouseChangeModel;
import Util.FileOperate;

import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseCompanyIdDal;

public class EmHouse_DeclareExcelBll {
	public List<EmHouseChangeModel> getListInfo(String idlist) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		try {
			list = dal.getInfoByIdList(idlist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 获取专办员号
	public String getidInfo(Integer id) {
		String s = "";
		List<CoHousingFundModel> list = new ListModelList<>();
		EmHouseCompanyIdDal dal = new EmHouseCompanyIdDal();

		list = dal.getlistById(id);
		for (int i = 0; i < list.size(); i++) {

			s = s + list.get(i).getChfz_id().toString();
		}

		return s;
	}

	public boolean createExportExcel(String className, String filename,
			String exfilename, List<EmHouseChangeModel> list) {
		boolean b = false;
		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();

		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "OfficeFile/Templet/Emhouse/" + filename));
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/Emhouse/" + exfilename), workBook);
			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			jxl.write.Number num = null;
			if (className.equals("New")) {
				sheet.removeRow(3);
				for (int i = 0; i < list.size(); i++) {
					sheet.insertRow(i);
					sheet.setRowView(i, 375, false);
					lbl = new jxl.write.Label(0, i, list.get(i).getEmhc_name(),
							wcf);
					sheet.addCell(lbl);

					lbl = new jxl.write.Label(1, i, "01", wcf);
					sheet.addCell(lbl);

					lbl = new jxl.write.Label(2, i, list.get(i)
							.getEmhc_idcard(), wcf);
					sheet.addCell(lbl);
					System.out.println(list.get(i).getEmhc_computerid());
					if (list.get(i).getEmhc_computerid() != null
							&& !list.get(i).getEmhc_computerid().equals("")) {
						lbl = new jxl.write.Label(3, i, list.get(i)
								.getEmhc_computerid(), wcf);
					} else {
						lbl = new jxl.write.Label(3, i, "000000000", wcf);

					}

					sheet.addCell(lbl);

					lbl = new jxl.write.Label(4, i, getdegreeNo(list.get(i)
							.getEmhc_degree()), wcf);
					sheet.addCell(lbl);

					lbl = new jxl.write.Label(5, i, getzcNo(list.get(i)
							.getEmhc_title()), wcf);
					sheet.addCell(lbl);

					num = new jxl.write.Number(6, i, list.get(i).getOwnmonth(),
							wcf);
					sheet.addCell(num);

					num = new jxl.write.Number(7, i, list.get(i)
							.getEmhc_radix(), wcf);
					sheet.addCell(num);
					lbl = new jxl.write.Label(8, i, gethjNo(list.get(i)
							.getEmhc_hj()), wcf);

					sheet.addCell(lbl);
					lbl = new jxl.write.Label(9, i, list.get(i)
							.getEmhc_mobile(), wcf);
					sheet.addCell(lbl);
					if (list.get(i).getEmhc_wifename() != null
							&& !list.get(i).getEmhc_wifename().equals("")) {
						lbl = new jxl.write.Label(10, i, "01", wcf); // 已婚
						sheet.addCell(lbl);
					} else {
						lbl = new jxl.write.Label(10, i, "02", wcf); // 未婚
						sheet.addCell(lbl);
					}

					lbl = new jxl.write.Label(11, i, list.get(i)
							.getEmhc_wifename(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(12, i, list.get(i)
							.getEmhc_wifeidcard(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(13, i, list.get(i).getGid()
							.toString(), wcf);
					sheet.addCell(lbl);

				}

			} else if (className.equals("Transfer")) {
				sheet.removeRow(2);
				for (int i = 0; i < list.size(); i++) {
					sheet.insertRow(i);
					sheet.setRowView(i, 375, false);
					lbl = new jxl.write.Label(0, i, list.get(i)
							.getEmhc_houseid(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(1, i, list.get(i)
							.getEmhc_idcard(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(2, i, Double.toString(list.get(i)
							.getEmhc_radix()), wcf);
					sheet.addCell(lbl);

					lbl = new jxl.write.Label(3, i, list.get(i)
							.getEmhc_companyid(), wcf);
					sheet.addCell(lbl);

					lbl = new jxl.write.Label(3, i, list.get(i).getEmhc_name(),
							wcf);
					sheet.addCell(lbl);
				}
			} else if (className.equals("Open")) {
				sheet.removeRow(2);
				for (int i = 0; i < list.size(); i++) {
					sheet.insertRow(i);
					sheet.setRowView(i, 375, false);
					lbl = new jxl.write.Label(0, i, list.get(i)
							.getEmhc_houseid(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(1, i, list.get(i).getEmhc_name(),
							wcf);
					sheet.addCell(lbl);
				}
			} else if (className.equals("Stop")) {
				sheet.removeRow(2);
				for (int i = 0; i < list.size(); i++) {
					sheet.insertRow(i);
					sheet.setRowView(i, 375, false);
					lbl = new jxl.write.Label(0, i, list.get(i)
							.getEmhc_houseid(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(1, i, list.get(i)
							.getEmhc_idcard(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(2, i, list.get(i).getEmhc_name(),
							wcf);
					sheet.addCell(lbl);
				}
			} else if (className.equals("Salay")) {
				sheet.removeRow(1);
				for (int i = 0; i < list.size(); i++) {
					sheet.insertRow(i);
					sheet.setRowView(i, 375, false);
					lbl = new jxl.write.Label(0, i, list.get(i)
							.getEmhc_houseid(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(1, i, Double.toString(list.get(i)
							.getEmhc_radix()), wcf);
					sheet.addCell(lbl);
				}
			}
			wb.write();
			wb.close();
			workBook.close();
			b = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
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

	public List<EmHouseChangeModel> emhouselist(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		list = dal
				.getChangeList(
						em,
						false,
						null,
						false,
						"emhc_id,ownmonth,a.cid,a.gid,emhc_company,emhc_name,emhc_idcard,emhc_computerid,emhc_companyid,emhc_houseid,emhc_degree,emhc_title,emhc_radix,emhc_hj,emhc_mobile,emhc_wifename,emhc_wifeidcard",
						"emhc_company,emhc_name");

		return list;
	}

	public static String gethjNo(String name) {
		String s = "0";
		if (name.equals("深户")) {
			s = "01";
		} else if (name.equals("非深户城镇")) {
			s = "02";
		} else if (name.equals("非深户农村")) {
			s = "03";
		} else {
			s = "04";
		}
		return s;
	}

	public static String getdegreeNo(String name) {
		String s = "0";
		if (name.equals("博士学位")) {
			s = "01";
		} else if (name.equals("硕士学位")) {
			s = "02";

		} else if (name.equals("学士学位")) {
			s = "03";
		} else {
			s = "04";
		}

		return s;
	}

	public static String getzcNo(String name) {
		String s = "0";
		if (name.equals("正高职称")) {
			s = "010";
		} else if (name.equals("副高职称")) {
			s = "020";

		} else if (name.equals("中级职称")) {
			s = "030";
		} else if (name.equals("初级职称")) {
			s = "040";
		} else {
			s = "050";
		}
		return s;
	}
}
