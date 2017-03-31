package bll.EmHouse;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.zkoss.zul.ListModelList;

import dal.Embase.EmbaseGdDal;

import Model.DownLoadFileModel;
import Model.EmbaseGDModel;
import Util.FileOperate;
import Util.NumberToCN;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class EmHouseDataListBll {

	// 更新状态
	public boolean modinfo(EmbaseGDModel m) {
		Integer i = 0;
		EmbaseGdDal dal = new EmbaseGdDal();

		if (m.getEmgd_id() != null) {
			m.setEmgd_modname(UserInfo.getUsername());
			i = dal.mod(m, m.getEmgd_id());
		} else {
			m.setAddname(UserInfo.getUsername());
			i = dal.add(m);
			m.setEmgd_id(i);
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 获取清册列表
	public List<EmbaseGDModel> gdList(EmbaseGDModel m) {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		if (m.getCohf_ispwd() != null) {
			m.setCohf_ispwd(m.getCohf_ispwd().equals("有") ? "1" : "0");
		}
		if (m.getContactstate() != null) {
			if (m.getContactstate().equals("(空白)")) {
				m.setContactstate("");
			} else if (m.getContactstate().equals("")) {
				m.setContactstate(null);
			}
		}
		if (m.getClstate() != null) {
			if (m.getClstate().equals("(空白)")) {
				m.setClstate("");
			} else if (m.getClstate().equals("")) {
				m.setClstate(null);
			}
		}

		list = dal.gethouseList(m);

		return list;
	}

	// 获取下拉列表
	public List<EmbaseGDModel> distinctList(String name, String order) {
		EmbaseGdDal dal = new EmbaseGdDal();
		return dal.getDisList(name, order);
	}

	// 导出清册
	public List<DownLoadFileModel> createExcel(List<EmbaseGDModel> list) {
		List<DownLoadFileModel> excellist = new ListModelList<>();
		DownLoadFileModel dm = new DownLoadFileModel();
		DownLoadFileModel dm2 = new DownLoadFileModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		String exfileName = sdf.format(trialTime) + "_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";
		String exfileName2 = sdf.format(trialTime) + "b_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";

		Workbook workBook = null;
		Workbook workBook2 = null;
		WritableWorkbook wb = null;
		WritableWorkbook wb2 = null;
		String absolutePath = FileOperate.getAbsolutePath();

		String type = list.get(0).getChange();
		String file = "";
		Integer line = 0;

		WritableCellFormat wcf = getBodyCellStyle();
		WritableCellFormat wcf2 = getCellStyle(null, null, null,
				BorderLineStyle.MEDIUM, null);
		WritableCellFormat wcf3 = getCellStyle(null, null, null,
				BorderLineStyle.MEDIUM, Alignment.LEFT);
		WritableCellFormat wcf4 = getCellStyle(BorderLineStyle.MEDIUM, null,
				null, BorderLineStyle.MEDIUM, Alignment.LEFT);
		WritableCellFormat wcf5 = getCellStyle(BorderLineStyle.MEDIUM, null,
				null, null, Alignment.LEFT);
		WritableCellFormat wcf6 = getCellStyle(BorderLineStyle.THIN, null,
				null, null, Alignment.LEFT);
		Label lbl = null;
		jxl.write.Number num = null;

		try {
			if (type.equals("新增")) {
				line = 6;
				file = "new.xls";

				workBook = Workbook.getWorkbook(new File(absolutePath
						+ "OfficeFile/Templet/Emhouse/center/" + file));

				wb = Workbook.createWorkbook(new File(absolutePath
						+ "OfficeFile/DownLoad/Emhouse/center/" + exfileName),
						workBook);

				WritableSheet sheet = wb.getSheet(0);

				sheet.addCell(new Label(3, 1, list.get(0).getCompanyid()));
				if (list.get(0).getHsingle().equals(1)) {
					sheet.addCell(new Label(3, 2, list.get(0).getCohf_company(),
							wcf3));
				} else {
					sheet.addCell(new Label(3, 2, "深圳中智经济技术合作有限公司", wcf3));
				}
				sheet.addCell(new Label(9, 13, calendar.get(Calendar.YEAR)
						+ "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DATE) + "日", getCellStyle(
						BorderLineStyle.NONE, BorderLineStyle.NONE, null,
						BorderLineStyle.MEDIUM, Alignment.LEFT)));
				for (Integer i = 0; i < list.size(); i++) {
					sheet.insertRow(i + line);
					num = new jxl.write.Number(0, i + line, i + 1, wcf);
					sheet.addCell(num);
					lbl = new jxl.write.Label(1, i + line, list.get(i)
							.getName(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(2, i + line, "01", wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(3, i + line, list.get(i)
							.getIdcard(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(4, i + line, list.get(i)
							.getComputerid(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(5, i + line, list.get(i)
							.getDegree(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(6, i + line, list.get(i)
							.getTitle(), wcf);
					sheet.addCell(lbl);

					lbl = new jxl.write.Label(7, i + line, null, wcf);
					sheet.addCell(lbl);

					num = new jxl.write.Number(8, i + line, list.get(i)
							.getRadix(), wcf);
					sheet.addCell(num);
					lbl = new jxl.write.Label(9, i + line, list.get(i).getHj(),
							wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(10, i + line, list.get(i)
							.getMobile(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(11, i + line, list.get(i)
							.getMarry(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(12, i + line, list.get(i)
							.getWifename(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(13, i + line, list.get(i)
							.getWifeidcard(), wcf);
					sheet.addCell(lbl);
					lbl = new jxl.write.Label(14, i + line, null, wcf2);
					sheet.addCell(lbl);
				}
				wb.write();
				wb.close();
				workBook.close();
				/*
				 * Filedownload.save(new File(absolutePath +
				 * "OfficeFile/DownLoad/Emhouse/center/" + exfileName), null);
				 */
				dm.setName(exfileName);
				dm.setUrl(absolutePath + "OfficeFile/DownLoad/Emhouse/center/"
						+ exfileName);
				excellist.add(dm);

			} else if (type.equals("调入")) {

				line = 4;

				workBook = Workbook.getWorkbook(new File(absolutePath
						+ "OfficeFile/Templet/Emhouse/center/m1.xls"));

				wb = Workbook.createWorkbook(new File(absolutePath
						+ "OfficeFile/DownLoad/Emhouse/center/" + exfileName),
						workBook);

				WritableSheet sheet = wb.getSheet(0);

				sheet.addCell(new Label(2, 1, list.get(0).getCompanyid(), wcf4));

				if (list.get(0).getHsingle().equals(1)) {
					sheet.addCell(new Label(2, 2, list.get(0).getCohf_company(),
							wcf3));
				} else {
					sheet.addCell(new Label(2, 2, "深圳中智经济技术合作有限公司", wcf3));
				}
				sheet.addCell(new Label(4, 9, calendar.get(Calendar.YEAR) + "年"
						+ (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DATE) + "日", getCellStyle(
						BorderLineStyle.NONE, BorderLineStyle.NONE, null,
						BorderLineStyle.MEDIUM, Alignment.LEFT)));

				for (Integer i = 0; i < list.size(); i++) {
					sheet.insertRow(i + line);
					num = new jxl.write.Number(0, i + line, i + 1, wcf);
					sheet.addCell(num);
					lbl = new Label(1, i + line, list.get(i).getHouseid(), wcf);
					sheet.addCell(lbl);
					lbl = new Label(2, i + line, list.get(i).getIdcard(), wcf);
					sheet.addCell(lbl);
					num = new jxl.write.Number(3, i + line, list.get(i)
							.getRadix(), wcf);
					sheet.addCell(num);
					lbl = new Label(4, i + line, null, wcf);
					sheet.addCell(lbl);
					lbl = new Label(5, i + line, list.get(i).getName(), wcf2);
					sheet.addCell(lbl);
				}

				wb.write();
				wb.close();
				workBook.close();

				workBook2 = Workbook.getWorkbook(new File(absolutePath
						+ "OfficeFile/Templet/Emhouse/center/m2.xls"));

				wb2 = Workbook.createWorkbook(new File(absolutePath
						+ "OfficeFile/DownLoad/Emhouse/center/" + exfileName2),
						workBook2);

				WritableSheet sheet2 = wb2.getSheet(0);
				sheet2.addCell(new Label(1, 1, list.get(0).getCompanyid(),
						getCellStyle(BorderLineStyle.MEDIUM, null, null,
								BorderLineStyle.MEDIUM, Alignment.LEFT)));

				if (list.get(0).getHsingle().equals(1)) {
					sheet2.addCell(new Label(1, 2, list.get(0).getCompany(),
							getCellStyle(null, null, null,
									BorderLineStyle.MEDIUM, Alignment.LEFT)));
				} else {
					sheet2.addCell(new Label(1, 2, "深圳中智经济技术合作有限公司",
							getCellStyle(null, null, null,
									BorderLineStyle.MEDIUM, Alignment.LEFT)));
				}

				sheet2.addCell(new Label(2, 9, calendar.get(Calendar.YEAR)
						+ "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DATE) + "日", getCellStyle(
						BorderLineStyle.NONE, BorderLineStyle.NONE, null,
						BorderLineStyle.MEDIUM, Alignment.LEFT)));
				for (Integer i = 0; i < list.size(); i++) {
					sheet2.insertRow(i + line);
					num = new jxl.write.Number(0, i + line, i + 1,
							getCellStyle(null, null, null, null,
									Alignment.CENTRE));
					sheet2.addCell(num);
					lbl = new Label(1, i + line, list.get(i).getHouseid(),
							getCellStyle(null, null, null, null,
									Alignment.CENTRE));
					sheet2.addCell(lbl);

					lbl = new Label(2, i + line, list.get(i).getName(),
							getCellStyle(null, null, null,
									BorderLineStyle.MEDIUM, null));
					sheet2.addCell(lbl);
				}
				wb2.write();
				wb2.close();
				workBook2.close();

				dm.setName(exfileName);
				dm.setUrl(absolutePath + "OfficeFile/DownLoad/Emhouse/center/"
						+ exfileName);
				excellist.add(dm);

				dm2.setName(exfileName2);
				dm2.setUrl(absolutePath + "OfficeFile/DownLoad/Emhouse/center/"
						+ exfileName2);
				excellist.add(dm2);

				/*
				 * Filedownload.save(new File(absolutePath +
				 * "OfficeFile/DownLoad/Emhouse/center/" + exfileName), null);
				 * 
				 * Filedownload.save(new File(absolutePath +
				 * "OfficeFile/DownLoad/Emhouse/center/" + exfileName2), null);
				 */
			} else if (type.equals("停交")) {
				file = "stop.xls";
				line = 4;
				workBook = Workbook.getWorkbook(new File(absolutePath
						+ "OfficeFile/Templet/Emhouse/center/" + file));

				wb = Workbook.createWorkbook(new File(absolutePath
						+ "OfficeFile/DownLoad/Emhouse/center/" + exfileName),
						workBook);

				WritableSheet sheet = wb.getSheet(0);
				sheet.addCell(new Label(1, 1, list.get(0).getCompanyid(), wcf4));
				if (list.get(0).getHsingle().equals(1)) {
					sheet.addCell(new Label(1, 2, list.get(0).getCohf_company(),
							wcf3));
				} else {
					sheet.addCell(new Label(1, 2, "深圳中智经济技术合作有限公司", wcf3));
				}

				sheet.addCell(new Label(3, 9, calendar.get(Calendar.YEAR) + "年"
						+ (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DATE) + "日", getCellStyle(
						BorderLineStyle.NONE, BorderLineStyle.NONE, null,
						BorderLineStyle.MEDIUM, Alignment.LEFT)));
				for (Integer i = 0; i < list.size(); i++) {
					sheet.insertRow(i + line);
					num = new jxl.write.Number(0, i + line, i + 1, wcf);
					sheet.addCell(num);
					lbl = new Label(1, i + line, list.get(i).getHouseid(), wcf);
					sheet.addCell(lbl);
					lbl = new Label(2, i + line, list.get(i).getIdcard(), wcf);
					sheet.addCell(lbl);

					lbl = new Label(3, i + line, list.get(i).getName(), wcf2);
					sheet.addCell(lbl);
				}
				wb.write();
				wb.close();
				workBook.close();

				/*
				 * Filedownload.save(new File(absolutePath +
				 * "OfficeFile/DownLoad/Emhouse/center/" + exfileName), null);
				 */
				dm.setName(exfileName);
				dm.setUrl(absolutePath + "OfficeFile/DownLoad/Emhouse/center/"
						+ exfileName);
				excellist.add(dm);

			} else if (type.equals("基数调整")) {
				file = "transfer.xls";
				line = 6;
				workBook = Workbook.getWorkbook(new File(absolutePath
						+ "OfficeFile/Templet/Emhouse/center/" + file));

				wb = Workbook.createWorkbook(new File(absolutePath
						+ "OfficeFile/DownLoad/Emhouse/center/" + exfileName),
						workBook);

				WritableSheet sheet = wb.getSheet(0);
				sheet.addCell(new Label(1, 1, list.get(0).getCompanyid(), wcf4));
				if (list.get(0).getHsingle().equals(1)) {
					sheet.addCell(new Label(1, 2, list.get(0).getCohf_company(),
							wcf3));
				} else {
					sheet.addCell(new Label(1, 2, "深圳中智经济技术合作有限公司", wcf3));
				}

				sheet.addCell(new Label(3, 9, calendar.get(Calendar.YEAR) + "年"
						+ (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DATE) + "日", getCellStyle(
						BorderLineStyle.NONE, BorderLineStyle.NONE, null,
						BorderLineStyle.MEDIUM, Alignment.LEFT)));

				for (Integer i = 0; i < list.size(); i++) {
					sheet.insertRow(i + line);
					num = new jxl.write.Number(0, i + line, i + 1, wcf);
					sheet.addCell(num);
					lbl = new Label(1, i + line, list.get(i).getHouseid(), wcf);
					sheet.addCell(lbl);
					num = new Number(2, i + line, list.get(i).getRadix(), wcf);
					sheet.addCell(num);

					lbl = new Label(3, i + line, list.get(i).getName(), wcf2);
					sheet.addCell(lbl);
				}
				wb.write();
				wb.close();
				workBook.close();

				/*
				 * Filedownload.save(new File(absolutePath +
				 * "OfficeFile/DownLoad/Emhouse/center/" + exfileName), null);
				 */
				dm.setName(exfileName);
				dm.setUrl(absolutePath + "OfficeFile/DownLoad/Emhouse/center/"
						+ exfileName);
				excellist.add(dm);

			} else if (type.equals("补缴")) {
				file = "bj.xls";
				line = 4;
				workBook = Workbook.getWorkbook(new File(absolutePath
						+ "OfficeFile/Templet/Emhouse/center/" + file));

				wb = Workbook.createWorkbook(new File(absolutePath
						+ "OfficeFile/DownLoad/Emhouse/center/" + exfileName),
						workBook);

				WritableSheet sheet = wb.getSheet(0);
				sheet.addCell(new Label(1, 1, list.get(0).getCompanyid(), wcf4));
				if (list.get(0).getHsingle().equals(1)) {
					sheet.addCell(new Label(1, 2, list.get(0).getCohf_company(),
							wcf3));
				} else {
					sheet.addCell(new Label(1, 2, "深圳中智经济技术合作有限公司", wcf3));
				}

				sheet.addCell(new Number(1, 5, list.size(), wcf));
				BigDecimal total = new BigDecimal(0);
				for (EmbaseGDModel m : list) {
					total = total.add(m.getTotal());
				}
				DecimalFormat df = new DecimalFormat("#.00");
				sheet.addCell(new Label(4, 5, df.format(total), wcf3));
				sheet.addCell(new Label(5, 10, calendar.get(Calendar.YEAR)
						+ "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DATE) + "日", getCellStyle(
						BorderLineStyle.NONE, BorderLineStyle.NONE, null,
						BorderLineStyle.MEDIUM, Alignment.LEFT)));

				Integer st=0;
				Integer sp=0;
				
				for (Integer i = 0; i < list.size(); i++) {
					sheet.insertRow(i + line);
					num = new jxl.write.Number(0, i + line, i + 1, wcf);
					sheet.addCell(num);
					lbl = new Label(1, i + line, list.get(i).getHouseid(), wcf);
					sheet.addCell(lbl);
					num = new Number(2, i + line, list.get(i).getTotal()
							.doubleValue(), wcf);
					sheet.addCell(num);
					lbl = new Label(3, i + line, list.get(i).getStartMonth()
							.toString(), wcf);
					sheet.addCell(lbl);
					lbl = new Label(4, i + line, list.get(i).getStopMonth()
							.toString(), wcf);
					sheet.addCell(lbl);

					lbl = new Label(5, i + line, list.get(i).getName(), wcf);
					sheet.addCell(lbl);
					
					lbl = new Label(6, i + line, "",
							wcf3);
					sheet.addCell(lbl);
					
					if (st.equals(0)) {
						st=list.get(i).getStartMonth();
					}else {
						if (st>list.get(i).getStartMonth()) {
							st=list.get(i).getStartMonth();
						}
					}
					if (sp.equals(0)) {
						sp=list.get(i).getStopMonth();
					}else {
						if (sp<list.get(i).getStopMonth()) {
							sp=list.get(i).getStopMonth();
						}
					}
					
					
				}
				wb.write();
				wb.close();
				workBook.close();

				// 补缴合计
				file = "bjTotal.xls";
				workBook2 = Workbook.getWorkbook(new File(absolutePath
						+ "OfficeFile/Templet/Emhouse/center/" + file));

				wb2 = Workbook.createWorkbook(new File(absolutePath
						+ "OfficeFile/DownLoad/Emhouse/center/" + exfileName2),
						workBook2);

				WritableSheet sheet2 = wb2.getSheet(0);
				sheet2.addCell(new Label(1, 1, list.get(0).getCompanyid(), wcf5));
				System.out.println("bj");
				if (list.get(0).getHsingle().equals(1)) {
					sheet2.addCell(new Label(3, 1, list.get(0).getCohf_company(),
							getCellStyle(BorderLineStyle.MEDIUM,
									BorderLineStyle.THIN,
									BorderLineStyle.THIN,
									BorderLineStyle.MEDIUM, Alignment.LEFT)));

				} else {
					sheet2.addCell(new Label(3, 1, "深圳中智经济技术合作有限公司",
							getCellStyle(BorderLineStyle.THIN,
									BorderLineStyle.THIN,
									BorderLineStyle.MEDIUM,
									BorderLineStyle.MEDIUM, Alignment.LEFT)));

				}
				sheet2.addCell(new Number(1, 2, list.size(), wcf6));
				BigDecimal bd = new BigDecimal(0);
				for (EmbaseGDModel m : list) {
					bd = bd.add(m.getTotal());
				}
				String money = NumberToCN.number2CNMontrayUnit(bd);

				sheet2.addCell(new Label(1, 3, money, wcf6));
				String money2 = df.format(bd);
				
				/*
				sheet2.addCell(new Label(3, 2, list.get(0).getOwnmonth()
						.toString(), wcf4));
				*/
				if (st.equals(sp)) {
					sheet2.addCell(new Label(3, 2, st.toString(), wcf4));
				}else {
					sheet2.addCell(new Label(3, 2, st.toString()+"至"+sp.toString(), wcf4));
				}
				sheet2.addCell(new Label(12, 4, money2.substring(
						money2.length() - 3, money2.length() - 2),
						getCellStyle(null, BorderLineStyle.THIN, null,
								BorderLineStyle.MEDIUM, null)));

				sheet2.addCell(new Label(7, 9, calendar.get(Calendar.YEAR)
						+ "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DATE) + "日", getCellStyle(
						BorderLineStyle.NONE, BorderLineStyle.NONE,
						BorderLineStyle.NONE, BorderLineStyle.NONE,
						Alignment.LEFT)));
				Integer k = 1;
				money2 = money2.replace(".", "");
				System.out.println(money2);
				for (int j = 0; j < money2.length(); j++) {
					sheet2.addCell(new Label(13 - k, 4, money2.substring(
							money2.length() - j - 1, money2.length() - j),
							getCellStyle(null, null, null, j==0?BorderLineStyle.MEDIUM:null, null)));
					k++;

				}

				wb2.write();
				wb2.close();
				workBook2.close();
				/*
				 * Filedownload.save(new File(absolutePath +
				 * "OfficeFile/DownLoad/Emhouse/center/" + exfileName), null);
				 * Filedownload.save(new File(absolutePath +
				 * "OfficeFile/DownLoad/Emhouse/center/" + exfileName2), null);
				 */
				dm.setName(exfileName);
				dm.setUrl(absolutePath + "OfficeFile/DownLoad/Emhouse/center/"
						+ exfileName);
				excellist.add(dm);

				dm2.setName(exfileName2);
				dm2.setUrl(absolutePath + "OfficeFile/DownLoad/Emhouse/center/"
						+ exfileName2);
				excellist.add(dm2);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return excellist;
	}

	// 单元格样式
	public WritableCellFormat getBodyCellStyle() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 12,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			bodyFormat.setAlignment(Alignment.CENTRE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat
					.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);

		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}

	public WritableCellFormat getCellStyle(BorderLineStyle top,
			BorderLineStyle left, BorderLineStyle botton,
			BorderLineStyle right, Alignment align) {
		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		BorderLineStyle b1 = top == null ? BorderLineStyle.THIN : top;
		BorderLineStyle b2 = left == null ? BorderLineStyle.THIN : left;
		BorderLineStyle b3 = botton == null ? BorderLineStyle.THIN : botton;
		BorderLineStyle b4 = right == null ? BorderLineStyle.THIN : right;
		Alignment a = align == null ? Alignment.CENTRE : align;

		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 12,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			bodyFormat.setAlignment(a);
			bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat.setBorder(Border.TOP, b1, Colour.BLACK);
			bodyFormat.setBorder(Border.LEFT, b2, Colour.BLACK);
			bodyFormat.setBorder(Border.BOTTOM, b3, Colour.BLACK);
			bodyFormat.setBorder(Border.RIGHT, b4, Colour.BLACK);

		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}
}
