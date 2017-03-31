package bll.SocialInsurance;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import Model.SocialEstimatesModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.FileOperate;
import Util.SocialInsuranceCalculator;

import dal.SocialInsurance.SocialEstimatesDal;

public class SocialEstimatesBll {
	private SocialEstimatesDal dal;

	public SocialEstimatesBll() {
		dal = new SocialEstimatesDal();
	}

	// 生成测算文件
	public boolean createEstimatesReport(List<String[]> dataList, int soin_id,
			String ownmonth, String flieName, String gjjCpp, String insurance) {
		boolean bool = false;
		try {
			SocialEstimatesModel m = getInsuranceModel(soin_id);
			m.setCo_gjjPro(gjjCpp);
			m.setEm_gjjPro(gjjCpp);
			if (writeEstimatesReport(dataList, m, ownmonth, flieName, insurance)) {
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 写入测算数据
	private boolean writeEstimatesReport(List<String[]> dataList,
			SocialEstimatesModel m, String ownmonth, String flieName,
			String insurance) {
		int row = 4;
		int cell;
		boolean bool = false;
		String absolutePath = FileOperate.getAbsolutePath();
		String templet = "SocialInsurance/Templet/ExportSocialEstimatesTemplet.xls";
		try {
			// 读取Excel模板
			Workbook wb = Workbook
					.getWorkbook(new File(absolutePath + templet));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + "SocialInsurance/Download/" + flieName), wb);
			// 生成工作表
			WritableSheet sheet = wwb.getSheet(0);
			sheet.setName(ownmonth);
			// 设置字体格式
			// 企业用字体
			WritableCellFormat wfCo = getFormatBorder();
			// 个人用字体
			WritableCellFormat wfEm = getFormatBorderRed();

			// 插入算法名称
			Label label = null;
			jxl.write.Number num = null;
			label = new Label(0, 0, insurance, getFormatTitle());
			sheet.addCell(label);
			// 插入比例及基数上限
			try {
				// 比例
				cell = 2;
				label = new Label(cell, row, m.getCo_ylPro(), wfCo);
				sheet.addCell(label);
				cell++;

				label = new Label(cell, row, m.getEm_ylPro(), wfEm);
				sheet.addCell(label);
				cell++;

				label = new Label(cell, row, m.getCo_jlPro(), wfCo);
				sheet.addCell(label);
				cell++;
				label = new Label(cell, row, m.getEm_jlPro(), wfEm);
				sheet.addCell(label);
				cell++;

				label = new Label(cell, row, m.getCo_syuPro(), wfCo);
				sheet.addCell(label);
				cell++;

				label = new Label(cell, row, m.getCo_gsPro(), wfCo);
				sheet.addCell(label);
				cell++;

				label = new Label(cell, row, m.getCo_syePro(), wfCo);
				sheet.addCell(label);
				cell++;
				label = new Label(cell, row, m.getEm_syePro(), wfEm);
				sheet.addCell(label);
				cell++;

				label = new Label(cell, row, m.getCo_gjjPro(), wfCo);
				sheet.addCell(label);
				cell++;
				label = new Label(cell, row, m.getEm_gjjPro(), wfEm);
				sheet.addCell(label);

				// 基数上下限
				row++;
				int lastCell;
				lastCell = cell = 2;
				String lastRange = "";
				String nowRange = "";

				label = new Label(0, row, "基数上下限", wfCo);
				sheet.addCell(label);
				sheet.mergeCells(0, row, 1, row);

				// 公司养老
				if ("0.00".equals(m.getCo_ylD())
						&& "0.00".equals(m.getCo_ylU())) {
					nowRange = lastRange = "";
				} else {
					nowRange = lastRange = m.getCo_ylD() + "-" + m.getCo_ylU();
				}
				label = new Label(cell, row, nowRange, wfCo);
				sheet.addCell(label);
				cell++;

				// 个人养老
				if ("0.00".equals(m.getEm_ylD())
						&& "0.00".equals(m.getEm_ylU())) {
					nowRange = "";
				} else {
					nowRange = m.getEm_ylD() + "-" + m.getEm_ylU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 公司医疗
				if ("0.00".equals(m.getCo_jlD())
						&& "0.00".equals(m.getCo_jlU())) {
					nowRange = "";
				} else {
					nowRange = m.getCo_jlD() + "-" + m.getCo_jlU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					if (lastCell != cell - 1)
						sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 个人医疗
				if ("0.00".equals(m.getEm_jlD())
						&& "0.00".equals(m.getEm_jlU())) {
					nowRange = "";
				} else {
					nowRange = m.getEm_jlD() + "-" + m.getEm_jlU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					if (lastCell != cell - 1)
						sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 公司生育
				if ("0.00".equals(m.getCo_syuD())
						&& "0.00".equals(m.getCo_syuU())) {
					nowRange = "";
				} else {
					nowRange = m.getCo_syuD() + "-" + m.getCo_syuU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					if (lastCell != cell - 1)
						sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 公司工伤
				if ("0.00".equals(m.getCo_gsD())
						&& "0.00".equals(m.getCo_gsU())) {
					nowRange = "";
				} else {
					nowRange = m.getCo_gsD() + "-" + m.getCo_gsU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					if (lastCell != cell - 1)
						sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 公司失业
				if ("0.00".equals(m.getCo_syeD())
						&& "0.00".equals(m.getCo_syeU())) {
					nowRange = "";
				} else {
					nowRange = m.getCo_syeD() + "-" + m.getCo_syeU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					if (lastCell != cell - 1)
						sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 个人失业
				if ("0.00".equals(m.getEm_syeD())
						&& "0.00".equals(m.getEm_syeU())) {
					nowRange = "";
				} else {
					nowRange = m.getEm_syeD() + "-" + m.getEm_syeU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					if (lastCell != cell - 1)
						sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 公司公积金
				if ("0.00".equals(m.getCo_gjjD())
						&& "0.00".equals(m.getCo_gjjU())) {
					nowRange = "";
				} else {
					nowRange = m.getCo_gjjD() + "-" + m.getCo_gjjU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					if (lastCell != cell - 1)
						sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				}
				cell++;

				// 个人公积金
				if ("0.00".equals(m.getEm_gjjD())
						&& "0.00".equals(m.getEm_gjjU())) {
					nowRange = "";
				} else {
					nowRange = m.getEm_gjjD() + "-" + m.getEm_gjjU();
				}
				if (!nowRange.equals(lastRange)) {
					lastRange = nowRange;
					sheet.mergeCells(lastCell, row, cell - 1, row);
					label = new Label(cell, row, nowRange, wfCo);
					sheet.addCell(label);
					lastCell = cell;
				} else {
					sheet.mergeCells(lastCell, row, cell, row);
				}
				cell++;

				// 补充空白格
				for (int i = 0; i < 8; i++) {
					label = new Label(cell, row, "", wfCo);
					sheet.addCell(label);
					cell++;
				}
				row++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 插入数据
			try {
				String formula = "";
				Formula f = null;
				String salary = "";
				String less = "";
				int sumRow;
				for (String[] str : dataList) {
					salary = "B" + (row + 1);
					cell = 0;
					label = new Label(cell, row, str[0], wfCo);
					sheet.addCell(label);
					cell++;

					num = new jxl.write.Number(cell, row,
							Double.valueOf(str[1]), wfCo);
					sheet.addCell(num);
					cell++;

					// 公司养老
					if ("0.00".equals(m.getCo_ylD())) {
						if ("0.00".equals(m.getCo_ylU())) {
							formula = "ROUND(" + salary + "*C5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getCo_ylU() + "," + m.getCo_ylU() + ","
									+ salary + ")*C5,2)";
						}
					} else {
						if ("0.00".equals(m.getCo_ylU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getCo_ylD() + "," + m.getCo_ylD() + ","
									+ salary + ")*C5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getCo_ylD() + ","
									+ m.getCo_ylD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getCo_ylU()
									+ "," + m.getCo_ylU() + "," + less
									+ ")*C5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);
					cell++;

					// 个人养老
					if ("0.00".equals(m.getEm_ylD())) {
						if ("0.00".equals(m.getEm_ylU())) {
							formula = "ROUND(" + salary + "*D5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getEm_ylU() + "," + m.getEm_ylU() + ","
									+ salary + ")*D5,2)";
						}
					} else {
						if ("0.00".equals(m.getEm_ylU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getEm_ylD() + "," + m.getEm_ylD() + ","
									+ salary + ")*D5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getEm_ylD() + ","
									+ m.getEm_ylD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getEm_ylU()
									+ "," + m.getEm_ylU() + "," + less
									+ ")*D5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfEm);
					sheet.addCell(f);
					cell++;

					// 公司医疗
					if ("0.00".equals(m.getCo_jlD())) {
						if ("0.00".equals(m.getCo_jlU())) {
							formula = "ROUND(" + salary + "*E5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getCo_jlU() + "," + m.getCo_jlU() + ","
									+ salary + ")*E5,2)";
						}
					} else {
						if ("0.00".equals(m.getCo_jlU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getCo_jlD() + "," + m.getCo_jlD() + ","
									+ salary + ")*E5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getCo_jlD() + ","
									+ m.getCo_jlD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getCo_jlU()
									+ "," + m.getCo_jlU() + "," + less
									+ ")*E5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);
					cell++;

					// 个人医疗
					if ("0.00".equals(m.getEm_jlD())) {
						if ("0.00".equals(m.getEm_jlU())) {
							formula = "ROUND(" + salary + "*F5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getEm_jlU() + "," + m.getEm_jlU() + ","
									+ salary + ")*F5,2)";
						}
					} else {
						if ("0.00".equals(m.getEm_jlU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getEm_jlD() + "," + m.getEm_jlD() + ","
									+ salary + ")*F5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getEm_jlD() + ","
									+ m.getEm_jlD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getEm_jlU()
									+ "," + m.getEm_jlU() + "," + less
									+ ")*F5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfEm);
					sheet.addCell(f);
					cell++;

					// 公司生育
					if ("0.00".equals(m.getCo_syuD())) {
						if ("0.00".equals(m.getCo_syuU())) {
							formula = "ROUND(" + salary + "*G5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getCo_syuU() + "," + m.getCo_syuU()
									+ "," + salary + ")*G5,2)";
						}
					} else {
						if ("0.00".equals(m.getCo_syuU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getCo_syuD() + "," + m.getCo_syuD()
									+ "," + salary + ")*G5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getCo_syuD() + ","
									+ m.getCo_syuD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getCo_syuU()
									+ "," + m.getCo_syuU() + "," + less
									+ ")*G5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);
					cell++;

					// 公司工伤
					if ("0.00".equals(m.getCo_gsD())) {
						if ("0.00".equals(m.getCo_gsU())) {
							formula = "ROUND(" + salary + "*" + m.getCo_gsPro()
									+ ",2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getCo_gsU() + "," + m.getCo_gsU() + ","
									+ salary + ")*H5,2)";
						}
					} else {
						if ("0.00".equals(m.getCo_gsU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getCo_gsD() + "," + m.getCo_gsD() + ","
									+ salary + ")*H5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getCo_gsD() + ","
									+ m.getCo_gsD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getCo_gsU()
									+ "," + m.getCo_gsU() + "," + less
									+ ")*H5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);
					cell++;

					// 公司失业
					if ("0.00".equals(m.getCo_syeD())) {
						if ("0.00".equals(m.getCo_syeU())) {
							formula = "ROUND(" + salary + "*I5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getCo_syeU() + "," + m.getCo_syeU()
									+ "," + salary + ")*I5,2)";
						}
					} else {
						if ("0.00".equals(m.getCo_syeU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getCo_syeD() + "," + m.getCo_syeD()
									+ "," + salary + ")*I5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getCo_syeD() + ","
									+ m.getCo_syeD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getCo_syeU()
									+ "," + m.getCo_syeU() + "," + less
									+ ")*I5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);
					cell++;

					// 个人失业
					if ("0.00".equals(m.getEm_syeD())) {
						if ("0.00".equals(m.getEm_syeU())) {
							formula = "ROUND(" + salary + "*J5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getEm_syeU() + "," + m.getEm_syeU()
									+ "," + salary + ")*J5,2)";
						}
					} else {
						if ("0.00".equals(m.getEm_syeU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getEm_syeD() + "," + m.getEm_syeD()
									+ "," + salary + ")*J5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getEm_syeD() + ","
									+ m.getEm_syeD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getEm_syeU()
									+ "," + m.getEm_syeU() + "," + less
									+ ")*J5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfEm);
					sheet.addCell(f);
					cell++;

					// 公司公积金
					if ("0.00".equals(m.getCo_gjjD())) {
						if ("0.00".equals(m.getCo_gjjU())) {
							formula = "ROUND(" + salary + "*K5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getCo_gjjU() + "," + m.getCo_gjjU()
									+ "," + salary + ")*K5,2)";
						}
					} else {
						if ("0.00".equals(m.getCo_gjjU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getCo_gjjD() + "," + m.getCo_gjjD()
									+ "," + salary + ")*K5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getCo_gjjD() + ","
									+ m.getCo_gjjD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getCo_gjjU()
									+ "," + m.getCo_gjjU() + "," + less
									+ ")*K5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);
					cell++;

					// 个人公积金
					if ("0.00".equals(m.getEm_gjjD())) {
						if ("0.00".equals(m.getEm_gjjU())) {
							formula = "ROUND(" + salary + "*L5,2)";
						} else {
							formula = "ROUND(IF(" + salary + ">"
									+ m.getEm_gjjU() + "," + m.getEm_gjjU()
									+ "," + salary + ")*L5,2)";
						}
					} else {
						if ("0.00".equals(m.getEm_gjjU())) {
							formula = "ROUND(IF(" + salary + "<"
									+ m.getEm_gjjD() + "," + m.getEm_gjjD()
									+ "," + salary + ")*L5,2)";
						} else {
							less = "IF(" + salary + "<" + m.getEm_gjjD() + ","
									+ m.getEm_gjjD() + "," + salary + ")";
							formula = "ROUND(IF(" + less + ">" + m.getEm_gjjU()
									+ "," + m.getEm_gjjU() + "," + less
									+ ")*L5,2)";
						}
					}
					f = new Formula(cell, row, formula, wfEm);
					sheet.addCell(f);
					cell++;
					sumRow = row + 1;
					// 公司合计
					formula = "C" + sumRow + "+E" + sumRow + "+G" + sumRow
							+ "+H" + sumRow + "+I" + sumRow + "+K" + sumRow;
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);
					cell++;

					// 个人合计
					formula = "D" + sumRow + "+F" + sumRow + "+J" + sumRow
							+ "+L" + sumRow;
					f = new Formula(cell, row, formula, wfEm);
					sheet.addCell(f);
					cell++;

					num = new jxl.write.Number(cell, row,
							Double.valueOf(str[2]), wfCo);
					sheet.addCell(num);
					cell++;
					
					num = new jxl.write.Number(cell, row,
							Double.valueOf(str[3]), wfCo);
					sheet.addCell(num);
					cell++;
					
					num = new jxl.write.Number(cell, row,
							Double.valueOf(str[4]), wfCo);
					sheet.addCell(num);
					cell++;
					
					num = new jxl.write.Number(cell, row,
							Double.valueOf(str[5]), wfCo);
					sheet.addCell(num);
					cell++;
					
					num = new jxl.write.Number(cell, row,
							Double.valueOf(str[6]), wfCo);
					sheet.addCell(num);
					cell++;

					// 合计
					formula = "M" + sumRow + "+N" + sumRow + "+O" + sumRow
							+ "+P" + sumRow + "+Q" + sumRow + "+R" + sumRow
							+ "+S" + sumRow;
					f = new Formula(cell, row, formula, wfCo);
					sheet.addCell(f);

					row++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			bool = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 获取有算法的城市
	public List<String> getInsuranceCityList() {
		return dal.getInsuranceCityList();
	}

	// 根据城市获取算法
	public List<SocialInsuranceAlgorithmViewModel> getInsuranceList(String city) {
		return dal.getInsuranceList(city);
	}

	// 获取算法Model
	public SocialEstimatesModel getInsuranceModel(int soin_id) {
		SocialEstimatesModel m = new SocialEstimatesModel();
		try {
			SocialInsuranceCalculator si = new SocialInsuranceCalculator();
			List<SocialInsuranceClassInfoViewModel> list = si.getSiAlInfo(
					soin_id, new Date(), "社会保险公积金");
			for (SocialInsuranceClassInfoViewModel sm : list) {
				setSocialEstimatesModel(sm, m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 赋值SocialEstimatesModel
	private void setSocialEstimatesModel(SocialInsuranceClassInfoViewModel sm,
			SocialEstimatesModel m) {
		try {
			String sicl_payunit = sm.getSicl_payunit();
			switch (sm.getSicl_name()) {
			case "养老保险":
				switch (sicl_payunit) {
				case "企业":
					m.setCo_ylU(sm.getSiai_basic_ud().toString());
					m.setCo_ylD(sm.getSiai_basic_dd().toString());
					m.setCo_ylPro(sm.getSiai_proportion());
					break;
				case "个人":
					m.setEm_ylU(sm.getSiai_basic_ud().toString());
					m.setEm_ylD(sm.getSiai_basic_dd().toString());
					m.setEm_ylPro(sm.getSiai_proportion());
					break;
				}
				break;
			case "医疗保险":
				switch (sicl_payunit) {
				case "企业":
					m.setCo_jlU(sm.getSiai_basic_ud().toString());
					m.setCo_jlD(sm.getSiai_basic_dd().toString());
					m.setCo_jlPro(sm.getSiai_proportion());
					break;
				case "个人":
					m.setEm_jlU(sm.getSiai_basic_ud().toString());
					m.setEm_jlD(sm.getSiai_basic_dd().toString());
					m.setEm_jlPro(sm.getSiai_proportion());
					break;
				}
				break;
			case "生育保险":
				switch (sicl_payunit) {
				case "企业":
					m.setCo_syuU(sm.getSiai_basic_ud().toString());
					m.setCo_syuD(sm.getSiai_basic_dd().toString());
					m.setCo_syuPro(sm.getSiai_proportion());
					break;
				case "个人":
					m.setEm_syuU(sm.getSiai_basic_ud().toString());
					m.setEm_syuD(sm.getSiai_basic_dd().toString());
					m.setEm_syuPro(sm.getSiai_proportion());
					break;
				}
				break;
			case "工伤保险":
				switch (sicl_payunit) {
				case "企业":
					m.setCo_gsU(sm.getSiai_basic_ud().toString());
					m.setCo_gsD(sm.getSiai_basic_dd().toString());
					m.setCo_gsPro(sm.getSiai_proportion());
					break;
				case "个人":
					m.setEm_gsU(sm.getSiai_basic_ud().toString());
					m.setEm_gsD(sm.getSiai_basic_dd().toString());
					m.setEm_gsPro(sm.getSiai_proportion());
					break;
				}
				break;
			case "失业保险":
				switch (sicl_payunit) {
				case "企业":
					m.setCo_syeU(sm.getSiai_basic_ud().toString());
					m.setCo_syeD(sm.getSiai_basic_dd().toString());
					m.setCo_syePro(sm.getSiai_proportion());
					break;
				case "个人":
					m.setEm_syeU(sm.getSiai_basic_ud().toString());
					m.setEm_syeD(sm.getSiai_basic_dd().toString());
					m.setEm_syePro(sm.getSiai_proportion());
					break;
				}
				break;
			case "住房公积金":
				switch (sicl_payunit) {
				case "企业":
					m.setCo_gjjU(sm.getSiai_basic_ud().toString());
					m.setCo_gjjD(sm.getSiai_basic_dd().toString());
					break;
				case "个人":
					m.setEm_gjjU(sm.getSiai_basic_ud().toString());
					m.setEm_gjjD(sm.getSiai_basic_dd().toString());
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 居中带边框
	private WritableCellFormat getFormatBorder() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10);
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

	// 红色字体居中带边框
	private WritableCellFormat getFormatBorderRed() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.RED);
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

	// 加粗
	private WritableCellFormat getFormatTitle() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.BOLD);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.LEFT);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}
}
