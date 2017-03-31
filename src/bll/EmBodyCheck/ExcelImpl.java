package bll.EmBodyCheck;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import service.ExcelService;

import Model.EmBcItemGroupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;

public class ExcelImpl implements ExcelService {
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<EmBodyCheckModel> bclist;// excel中的内容
	private EmBcInfo_SelectBll bll = new EmBcInfo_SelectBll();

	public ExcelImpl(File file, String sheetName, String[] title,
			List<EmBodyCheckModel> lists) {
		// TODO Auto-generated method stub
		this.file = file;
		this.sheetName = sheetName;
		this.title = title;
		this.bclist = lists;
	}

	@Override
	public void writeExcel() throws Exception {
		WritableWorkbook wwb = Workbook.createWorkbook(new FileOutputStream(
				file));
		WritableSheet sheet = wwb.createSheet(sheetName, 0);
		// 设置标题的文字格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		// 行高设置行高,参数(行数,高度)
		sheet.setRowView(0, 500);
		// 开始写入第一行(即标题栏)
		for (int i = 0; i < title.length; i++) {

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}
		// 开始写入内容

		for (int row = 1; row < bclist.size() + 1; row++) {
			// 用model获取每一行数据
			EmBodyCheckModel cop = bclist.get(row - 1);
			// 将每列数据写入工作表中

			String invaliddate = "";
			String bookdate = cop.getEbcl_bookdate();
			if (bookdate != null) {
				Date bookdat = strtodate(bookdate);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(bookdat);
				if (cop.getEbcl_type() != null) {
					if (cop.getEbcl_type().equals(0)
							|| cop.getEbcl_type().equals(2))// 单次体检\年度体检
					{
						if (cop.getEbcl_bookmode() != null
								&& cop.getEbcl_bookmode().equals(1))// 固定时间预约当天有效
						{
							invaliddate = bookdate;
						} else {
							calendar.add(calendar.DATE, 60);// 不固定时间的年度体检和单次体检有效期是60天
							Date date = calendar.getTime();
							invaliddate = datetoStr(date);
						}
					} else if (cop.getEbcl_type().equals(1))// 入职体检有效期是15天
					{
						calendar.add(calendar.DATE, 15);// 入职体检有效期是15天
						Date date = calendar.getTime();
						invaliddate = datetoStr(date);
					}
				}

			}

			Label label = null;
			label = new Label(0, row, invaliddate);
			sheet.addCell(label);
			String linkdate = "";
			if (cop.getEbcl_linkdate() != null) {
				linkdate = cop.getEbcl_bookdate();
			}
			label = new Label(1, row, linkdate);
			sheet.addCell(label);
			label = new Label(2, row, cop.getCid().toString());
			sheet.addCell(label);
			label = new Label(3, row, cop.getEmbc_client());
			sheet.addCell(label);
			label = new Label(4, row, cop.getEmbc_name());
			sheet.addCell(label);
			label = new Label(5, row, cop.getEmbc_idcard());
			sheet.addCell(label);
			label = new Label(6, row, cop.getEmbc_sex());
			String age = "";
			if (cop.getEmbc_age() != null) {
				age = cop.getEmbc_age().toString();
			}
			sheet.addCell(label);

			label = new Label(7, row, age);
			sheet.addCell(label);

			label = new Label(8, row, cop.getEmbc_marry());
			sheet.addCell(label);

			String group = "";
			String grouplist = "";

			if (cop.getEbcl_itemgroup() != null
					&& !cop.getEbcl_itemgroup().equals("")) {
				// 根据cop.getEbcl_itemgroup()查询组合
				EmBcItemGroupModel gm = bll.getEmBcItemGroupList(cop
						.getEbcl_itemgroup());

				for (EmBodyCheckItemModel m : gm.getList()) {
					if (group.equals("")) {
						group = m.getEbig_name();
					}

					grouplist += "," + m.getEbit_name();
				}
				grouplist = grouplist.substring(1);
				label = new Label(9, row, group);
				sheet.addCell(label);
				label = new Label(10, row, grouplist);
				sheet.addCell(label);
			} else {
				label = new Label(10, row, bll.getItemList(cop
						.getEbcl_itemnums()));
				sheet.addCell(label);
			}

			String limit = getLimit(cop.getEbcl_itemnums());
			label = new Label(11, row, limit);
			sheet.addCell(label);

			label = new Label(12, row, cop.getEbcl_bookdate());
			sheet.addCell(label);

			label = new Label(13, row, cop.getEbsa_address());
			sheet.addCell(label);

			String discount = "";
			if (cop.getEbcl_discount() != null) {
				discount = cop.getEbcl_discount().toString();
			} else {
				if (cop.getEbcl_charge() != null) {
					discount = cop.getEbcl_charge().toString();
				}
			}
			label = new Label(14, row, discount);
			sheet.addCell(label);
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();

	}

	@Override
	public void exportExcel() throws Exception {
		// TODO Auto-generated method stub
		WritableWorkbook wwb = Workbook.createWorkbook(new FileOutputStream(
				file));
		WritableSheet sheet = wwb.createSheet(sheetName, 0);
		// 设置标题的文字格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		// 行高设置行高,参数(行数,高度)
		sheet.setRowView(0, 500);
		// 开始写入第一行(即标题栏)
		for (int i = 0; i < title.length; i++) {

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}
		// 开始写入内容

		for (int row = 1; row < bclist.size() + 1; row++) {
			// 用model获取每一行数据
			EmBodyCheckModel cop = bclist.get(row - 1);
			// 将每列数据写入工作表中

			Label label = null;
			label = new Label(0, row, String.valueOf(row));
			sheet.addCell(label);
			label = new Label(1, row, cop.getEmbc_client());
			sheet.addCell(label);
			label = new Label(2, row, cop.getEmbc_shortname());
			sheet.addCell(label);
			label = new Label(3, row, cop.getEmbc_name());
			sheet.addCell(label);
			label = new Label(4, row, cop.getEmbc_idcard());
			sheet.addCell(label);
			label = new Label(5, row, cop.getEmbc_sex());
			sheet.addCell(label);
			String age = "";
			if (cop.getEmbc_age() != null) {
				age = cop.getEmbc_age().toString();
			}
			label = new Label(6, row, age);
			sheet.addCell(label);
			label = new Label(7, row, cop.getEbcs_hospital());
			sheet.addCell(label);

			label = new Label(8, row, cop.getEbcl_itemsstr());
			sheet.addCell(label);
			label = new Label(9, row, cop.getEbcl_charge().toString());
			sheet.addCell(label);
			label = new Label(10, row, cop.getEbsa_address());
			sheet.addCell(label);

			label = new Label(11, row, cop.getEbcl_bookdate());
			sheet.addCell(label);
			label = new Label(12, row, cop.getEmbc_statebname());
			sheet.addCell(label);

		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
	}

	private String getLimit(String itemsId) {

		EmBodyCheckItemModel m = new EmBodyCheckItemModel();
		m.setIdList(itemsId);
		List<EmBodyCheckItemModel> list = bll.getGroupItemList(m);
		String limit = "";
		for (EmBodyCheckItemModel ml : list) {
			if (ml.getSex() != null && !ml.getSex().equals("无限制")) {
				boolean flag = true;
				if (limit.length() > 0) {
					String[] limits = limit.split(",");
					for (int i = 0; i < limits.length; i++) {
						String limitname = limits[i];
						if (limitname != null && ml.getSex() != null) {
							if (ml.getSex().equals(limitname)) {
								flag = false;
								break;
							}
						}
					}
				}
				if (flag) {
					limit = limit + ml.getSex() + ",";
				}
			}
			if (ml.getMarry() != null && !ml.getMarry().equals("无限制")) {
				boolean flag = true;
				if (limit.length() > 0) {
					String[] limits = limit.split(",");
					for (int i = 0; i < limits.length; i++) {
						String limitname = limits[i];
						if (limitname != null && ml.getMarry() != null) {
							if (ml.getMarry().equals(limitname)) {
								flag = false;
								break;
							}
						}
					}
				}
				if (flag) {
					limit = limit + ml.getMarry() + ",";
				}
			}
		}
		if (limit.length() > 0) {
			limit = limit.substring(0, limit.length() - 1);
		} else {
			limit = "无限制";
		}
		return limit;
	}

	// 时间转字符串
	private String datetoStr(Date date) {
		String str = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (date != null) {
				str = format.format(date);
			}
		} catch (Exception e) {

		}
		return str;
	}

	// 字符串转时间
	private Date strtodate(String str) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (str != null) {
				date = format.parse(str);
			}
		} catch (Exception e) {

		}
		return date;
	}
}
