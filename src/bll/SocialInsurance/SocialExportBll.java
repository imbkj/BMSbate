package bll.SocialInsurance;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import Model.PubProCityModel;
import Model.SocialExportModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.FileOperate;
import dal.SocialInsurance.SocialExportDal;

public class SocialExportBll {
	private SocialExportDal dal;

	public SocialExportBll() {
		dal = new SocialExportDal();
	}

	// 获取有算法的城市
	public List<PubProCityModel> getInsuranceCityList() {
		return dal.getInsuranceCityList();
	}

	// 导出社保标准
	public String createReport(String cityIdList) {
		String fileName = FileOperate.mosaicFileName() + ".xls";
		try {
			if (writeSocialExportReport(fileName,
					dal.getSocialInsuranceByCity(cityIdList))) {
				return "SocialInsurance/Download/" + fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 写入导出数据
	private boolean writeSocialExportReport(String flieName,
			Map<Integer, SocialExportModel> map) {
		int row = 2;
		int cell = 0;
		int i = 0;
		boolean bool = false;
		String absolutePath = FileOperate.getAbsolutePath();
		String templet = "SocialInsurance/Templet/SocialCityExport.xls";
		try {
			// 读取Excel模板
			Workbook wb = Workbook
					.getWorkbook(new File(absolutePath + templet));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + "SocialInsurance/Download/" + flieName), wb);
			// 生成工作表
			WritableSheet sheet = wwb.getSheet(0);
			// 设置字体格式
			WritableCellFormat wf = getFormatBorder();

			// 插入算法名称
			Label label = null;
			// jxl.write.Number num = null;
			try {
				Collection<SocialExportModel> cityList = map.values();
				for (SocialExportModel cm : cityList) {
					try {
						Collection<SocialInsuranceAlgorithmViewModel> sialList = cm
								.getSoinMap().values();
						for (SocialInsuranceAlgorithmViewModel sm : sialList) {
							i++;
							label = new Label(0, row, String.valueOf(i), wf);
							sheet.addCell(label);
							sheet.mergeCells(0, row, 0, row + 1);

							label = new Label(1, row, cm.getCity(), wf);
							sheet.addCell(label);
							sheet.mergeCells(1, row, 1, row + 1);

							label = new Label(2, row, sm.getCoab_name(), wf);
							sheet.addCell(label);
							sheet.mergeCells(2, row, 2, row + 1);

							label = new Label(3, row, sm.getSial_execdatestr(),
									wf);
							sheet.addCell(label);
							sheet.mergeCells(3, row, 3, row + 1);

							label = new Label(4, row, sm.getSoin_title(), wf);
							sheet.addCell(label);
							sheet.mergeCells(4, row, 4, row + 1);

							label = new Label(5, row, "企业", wf);
							sheet.addCell(label);

							label = new Label(5, row + 1, "个人", wf);
							sheet.addCell(label);
							for (SocialInsuranceClassInfoViewModel im : sm
									.getClassInfoList()) {
								switch (im.getSicl_name()) {
								case "养老保险":
									cell = 6;
									break;
								case "医疗保险":
									cell = 13;
									break;
								case "大病医疗":
									cell = 20;
									break;
								case "生育保险":
									cell = 27;
									break;
								case "工伤保险":
									cell = 34;
									break;
								case "失业保险":
									cell = 41;
									break;
								case "住房公积金":
									cell = 48;
									break;
								case "补充公积金":
									cell = 55;
									break;
								case "残保金":
									cell = 62;
									break;
								}
								switch (im.getSicl_payunit()) {
								case "企业":
									label = new Label(cell, row,
											im.getSiai_basic_d(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row,
											im.getSiai_deposit_d(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row,
											im.getSiai_basic_u(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row,
											im.getSiai_deposit_u(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row,
											im.getSiai_proportion(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row,
											im.getSiai_algorithm(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row,
											im.getSiai_remark(), wf);
									sheet.addCell(label);
									cell++;
									break;
								case "个人":
									label = new Label(cell, row + 1,
											im.getSiai_basic_d(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row + 1,
											im.getSiai_deposit_d(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row + 1,
											im.getSiai_basic_u(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row + 1,
											im.getSiai_deposit_u(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row + 1,
											im.getSiai_proportion(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row + 1,
											im.getSiai_algorithm(), wf);
									sheet.addCell(label);
									cell++;

									label = new Label(cell, row + 1,
											im.getSiai_remark(), wf);
									sheet.addCell(label);
									cell++;
									break;
								}
							}
							cell = 69;
							label = new Label(cell, row,
									sm.getSial_lowest_salarystr(), wf);
							sheet.addCell(label);
							sheet.mergeCells(cell, row, cell, row + 1);
							cell++;

							label = new Label(cell, row,
									sm.getSial_avg_salarystr(), wf);
							sheet.addCell(label);
							sheet.mergeCells(cell, row, cell, row + 1);
							cell++;

							label = new Label(cell, row,
									sm.getSial_sb_remark(), wf);
							sheet.addCell(label);
							sheet.mergeCells(cell, row, cell, row + 1);
							cell++;

							label = new Label(cell, row,
									sm.getSial_gjj_remark(), wf);
							sheet.addCell(label);
							sheet.mergeCells(cell, row, cell, row + 1);
							cell++;

							label = new Label(cell, row,
									sm.getSial_cb_remark(), wf);
							sheet.addCell(label);
							sheet.mergeCells(cell, row, cell, row + 1);
							cell++;

							label = new Label(cell, row, sm.getSial_addname(),
									wf);
							sheet.addCell(label);
							sheet.mergeCells(cell, row, cell, row + 1);

							row = row + 2;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
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

	// 居中字体
	private WritableCellFormat getFormatBorder() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 11);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			// wc.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}
}
