package bll.EmSalary;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import dal.EmSalary.EmSalary_ChangeDataDal;

import Model.EmSalaryDataModel;
import Util.FileOperate;

public class EmSalary_ChangeDataExportBll {

	/*
	 * // 根据Cid导出变动数据 public void exportChangeDataByCid(int cid, int ownmonth) {
	 * try { String fileName = "cid" + cid + "_" + ownmonth + "工资变动数据"; fileName
	 * = "EmSalary/File/Upload/SalaryOwnmonthChangeData/" + fileName + ".xls";
	 * map = getChangeData(cid, ownmonth, 0); if (createExcel(fileName,
	 * ownmonth)) { FileOperate.download(fileName); } else {
	 * Messagebox.show("数据导出失败。", "操作提示", Messagebox.OK, Messagebox.NONE); } }
	 * catch (Exception e) { e.printStackTrace(); Messagebox.show("数据导出出错。",
	 * "操作提示", Messagebox.OK, Messagebox.NONE); } }
	 */

	// 获取变动数据
	public Map<String, List<EmSalaryDataModel>> getChangeData(int cid,
			int ownmonth, int taba_id) {
		EmSalary_ChangeDataDal dal = new EmSalary_ChangeDataDal();
		return dal.getChangeData(cid, ownmonth, taba_id);
	}

	// 生成变动数据excel
	public boolean createExcel(String path, String fileName, int ownmonth,
			Map<String, List<EmSalaryDataModel>> dataMap) {
		Map<String, List<EmSalaryDataModel>> map = dataMap;
		boolean bool = false;
		String absolutePath = FileOperate.getAbsolutePath();
		try {
			// 创建Excel文件
			WritableWorkbook wb = Workbook.createWorkbook(new File(absolutePath
					+ path));
			// 创建Excel工作表
			WritableSheet ws = wb.createSheet(fileName, 0);

			// 去掉整个sheet中的网格线
			ws.getSettings().setShowGridLines(false);

			// 写入新增数据
			int addRow = 0;
			addRow = writeHead(ws, ownmonth + "新增数据名单", addRow);
			try {
				List<EmSalaryDataModel> addList = map.get("add");
				addRow++;
				if (addList.size() > 0) {
					int row = writeData(ws, addRow, addList);
					addRow = writeSum(ws, row, addRow + 1);
					addRow++;
				}
				addList = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 写入减少数据
			int lessRow = addRow + 1;
			lessRow = writeHead(ws, ownmonth + "减少数据名单", lessRow);
			try {
				List<EmSalaryDataModel> lessList = map.get("less");
				lessRow++;
				if (lessList.size() > 0) {
					int row = writeData(ws, lessRow, lessList);
					lessRow = writeSum(ws, row, lessRow + 1);
					lessRow++;
				}
				lessList = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 写入变动数据
			int changeRow = lessRow + 1;
			changeRow = writeHead(ws, ownmonth + "与上月比对变动数据名单", changeRow);
			try {
				List<EmSalaryDataModel> changeList = map.get("change");
				changeRow++;
				if (changeList.size() > 0) {
					int row = writeData(ws, changeRow, changeList);
					changeRow = writeSum(ws, row, changeRow + 1);
					changeRow++;
				}
				changeList = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 写入Excel
			wb.write();
			// 关闭文件
			wb.close();
			bool = true;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 写入表头
	private int writeHead(WritableSheet ws, String headCon, int BeginRow) {
		Label label = null;
		int row = BeginRow;
		try {
			// 字体居中带蓝色边框
			WritableCellFormat wf1 = getFormatBorderLineBlue();

			// 输出表头
			label = new Label(0, row, headCon, getFormat());
			ws.addCell(label);
			ws.mergeCells(0, row, 5, row);
			row++;

			int col = 0;

			label = new Label(col, row, "员工编号", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "员工姓名", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "用    途", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "实发工资", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "社保个人部分", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "住房公积金个人部分", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "税前工资", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "应税工资", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "个人所得税", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "年终奖金", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "年终奖金应纳税额", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "年终奖金税", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "离职补偿金", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "离职补偿金税", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "股票收入", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "股票税", wf1);
			ws.addCell(label);
			col++;
			label = new Label(col, row, "报销费用", wf1);
			ws.addCell(label);
			col++;

		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	// 写入变动数据
	private int writeData(WritableSheet ws, int BeginRow,
			List<EmSalaryDataModel> list) {
		Label label = null;
		jxl.write.Number num = null;
		int row = BeginRow;
		try {
			// 字体居中带边框
			WritableCellFormat wf1 = getFormatBorderLine();
			int col = 0;

			for (EmSalaryDataModel m : list) {
				col = 0;
				num = new jxl.write.Number(col, row, m.getGid(), wf1);
				ws.addCell(num);
				col++;
				label = new Label(col, row, m.getName(), wf1);
				ws.addCell(label);
				col++;
				label = new Label(col, row, m.getEsda_usage_typestr(), wf1);
				ws.addCell(label);
				col++;
				// 实发工资
				num = new jxl.write.Number(col, row, m.getEsda_pay()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 社保个人部分
				num = new jxl.write.Number(col, row, m.getEsda_siop()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 住房公积金个人部分
				num = new jxl.write.Number(col, row, m.getEsda_hafop()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 税前工资
				num = new jxl.write.Number(col, row, m.getEsda_total_pretax()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 应税工资
				num = new jxl.write.Number(col, row, m.getEsda_tax_base()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 个人所得税
				num = new jxl.write.Number(col, row, m.getEsda_tax()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 年终奖金
				num = new jxl.write.Number(col, row, m.getEsda_db()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 年终奖金应纳税额
				num = new jxl.write.Number(col, row, m.getEsda_db_tax_base()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 年终奖金税
				num = new jxl.write.Number(col, row, m.getEsda_db_tax()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 离职补偿金
				num = new jxl.write.Number(col, row, m.getEsda_dc()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 离职补偿金税
				num = new jxl.write.Number(col, row, m.getEsda_dc_tax()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 股票收入
				num = new jxl.write.Number(col, row, m.getEsda_stock()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 股票税
				num = new jxl.write.Number(col, row, m.getEsda_stock_tax()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;
				// 报销费用
				num = new jxl.write.Number(col, row, m.getEsda_write_off()
						.doubleValue(), wf1);
				ws.addCell(num);
				col++;

				row++;
			}
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	// 写入合计行
	private int writeSum(WritableSheet ws, int BeginRow, int dataRow) {
		Label label = null;
		int row = BeginRow;
		try {
			// 字体居中带黄色边框
			WritableCellFormat wf1 = getFormatBorderLineYellow();
			ws.mergeCells(0, row, 2, row);
			label = new Label(0, row, "合计", wf1);
			ws.addCell(label);
			String formula = "";
			Formula f = null;
			for (int i = 3; i < 18; i++) {
				formula = "SUM(" + getColumnName(i) + dataRow + ":"
						+ getColumnName(i) + row + ")";
				f = new Formula(i - 1, row, formula, wf1);
				ws.addCell(f);
			}
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	// 字体设置
	private WritableCellFormat getFormat() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 11);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.LEFT);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 字体居中带边框
	private WritableCellFormat getFormatBorderLine() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 9);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 字体居中带蓝色边框
	private WritableCellFormat getFormatBorderLineBlue() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 9);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
			wc.setBackground(jxl.format.Colour.SKY_BLUE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 字体居中带黄色边框
	private WritableCellFormat getFormatBorderLineYellow() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 9);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
			wc.setBackground(jxl.format.Colour.YELLOW);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 获取一列对应的字母。例如：ColumnNum=1，则返回值为A 列号转字母
	private String getColumnName(int columnNum) {
		if (columnNum <= 0) {
			try {
				throw new Exception("Invalid parameter");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		columnNum--;
		String column = "";
		do {
			if (column.length() > 0) {
				columnNum--;
			}
			column = ((char) (columnNum % 26 + (int) 'A')) + column;
			columnNum = (int) ((columnNum - columnNum % 26) / 26);
		} while (columnNum > 0);
		return column;
	}
}
