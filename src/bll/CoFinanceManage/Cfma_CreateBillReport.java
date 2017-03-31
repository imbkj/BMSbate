package bll.CoFinanceManage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.CoFinanceManage.Cfma_BillReportOperateDal;

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

import Model.CoFinanceCompanyClientModel;
import Model.EmFinanceCommissionOutCityModel;
import Model.EmFinanceCommissionOutDetailItemModel;
import Model.EmFinanceCommissionOutModel;
import Model.EmFinanceDisposableModel;
import Model.EmFinanceHouseGjjModel;
import Model.EmFinanceProductItemListModel;
import Model.EmFinanceProductModel;
import Model.EmFinanceReportForLocalityModel;
import Model.EmFinanceSalaryModel;
import Model.EmFinanceSheBaoModel;
import Model.EmFinanceTaxModel;
import Model.EmSalaryBaseAddItemModel;
import Util.FileOperate;
import Util.UserInfo;

public class Cfma_CreateBillReport {
	private String billNo;
	// 社保
	private List<EmFinanceSheBaoModel> shebaoList;
	// 公积金
	private List<EmFinanceHouseGjjModel> gjjList;
	// 工资
	private List<EmFinanceSalaryModel> salaryList;
	// 个税
	private List<EmFinanceTaxModel> emTaxList;
	// 员工福利
	private List<EmFinanceProductModel> emProductList;
	// 委托出
	private List<EmFinanceCommissionOutCityModel> emCommissionOutList;
	// 员工非标
	private List<EmFinanceDisposableModel> emDisposableList;

	// 本地服务合并后的Map
	private Map<Integer, EmFinanceReportForLocalityModel> localityMap;
	// 外地服务费用合并Map
	private Map<Integer, EmFinanceReportForLocalityModel> wtMap;
	// 外地福利产品列表(没有本地社保公积金时产品明细转入该列表)
	private List<EmFinanceProductModel> emwtProductList;

	public Cfma_CreateBillReport() {
		
	}

	// 生成付款通知
	public boolean createReport(String billNo,
			List<EmFinanceSheBaoModel> shebaoList,
			List<EmFinanceHouseGjjModel> gjjList,
			List<EmFinanceSalaryModel> salaryList,
			List<EmFinanceProductModel> emProductList,
			List<EmFinanceCommissionOutCityModel> emCommissionOutList,
			List<EmFinanceDisposableModel> emDisposableList,
			List<EmFinanceTaxModel> emTaxList) {
		this.billNo = billNo;
		if (shebaoList != null) {
			this.shebaoList = shebaoList;
		}
		if (gjjList != null) {
			this.gjjList = gjjList;
		}
		if (salaryList != null) {
			this.salaryList = salaryList;
		}
		if (emProductList != null) {
			this.emProductList = emProductList;
		}
		if (emCommissionOutList != null) {
			this.emCommissionOutList = emCommissionOutList;
		}
		if (emDisposableList != null) {
			this.emDisposableList = emDisposableList;
		}
		if (emTaxList != null) {
			this.emTaxList = emTaxList;
		}

		getLocalityMap();
		return createExl();
	}

	// 生成Execl并写入数据
	private boolean createExl() {
		boolean bool = false;
		String absolutePath = FileOperate.getAbsolutePath();
		Cfma_BillReportOperateDal opdal = new Cfma_BillReportOperateDal();
		CoFinanceCompanyClientModel cobaModel = opdal
				.getCompanyClientByBillNo(billNo);
		try {
			// 创建Excel文件
			WritableWorkbook wb = Workbook.createWorkbook(new File(absolutePath
					+ "CoFinanceManage/File/billReport/" + billNo + ".xls"));
			wb.setProtected(true);
			// 创建Excel工作表
			WritableSheet ws = wb.createSheet(cobaModel.getCoba_shortname()
					+ cobaModel.getOwnmonth() + "payment", 0);

			// 去掉整个sheet中的网格线
			ws.getSettings().setShowGridLines(false);

			// 根据内容自动设置列宽
			/*
			 * CellView cellView = new CellView(); cellView.setAutosize(true);
			 * for (int i = 0; i < ws.getColumns(); i++) { ws.setColumnView(i,
			 * cellView); }
			 */

			// 写入公司及客服信息
			writeCompanyClient(ws, cobaModel);

			// 写入深圳本地服务收费明细
			int row = 6;
			row = writeLocality(ws, row);

			// 写入委托外地收费明细
			row = writeCommissionOut(ws, row);

			// 写入工资收费明细
			row = writeSalary(ws, row);

			// 写入个税收费明细
			row = writeTax(ws, row);

			// 写入员工非标收费明细
			row = writeEmFinanceDisposable(ws, row);

			// 写入公司账号信息
			writeFinance(ws, row, cobaModel.getReceivable());

			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			bool = true;

			// 修改账单付款通知状态
			opdal.upReportState(billNo, UserInfo.getUsername());

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

	// 写入公司及客服信息
	private void writeCompanyClient(WritableSheet ws,
			CoFinanceCompanyClientModel cobaModel) {
		Label label = null;
		jxl.write.Number num = null;
		try {
			// 设置字体9号宋体
			WritableCellFormat wf1 = getFormat();
			// 插入公司及客服信息
			label = new Label(7, 0, "公司编号：", wf1);
			ws.addCell(label);
			num = new jxl.write.Number(8, 0, cobaModel.getCid(), wf1);
			ws.addCell(num);
			label = new Label(9, 0, "公司名称：", wf1);
			ws.addCell(label);
			label = new Label(10, 0, cobaModel.getCoba_company(), wf1);
			ws.addCell(label);
			ws.mergeCells(10, 0, 13, 0);
			label = new Label(7, 1, "客服代表：", wf1);
			ws.addCell(label);
			label = new Label(8, 1, cobaModel.getCoba_client(), wf1);
			ws.addCell(label);
			label = new Label(9, 1, "联系电话：", wf1);
			ws.addCell(label);
			label = new Label(10, 1, "0755-" + cobaModel.getLog_tel(), wf1);
			ws.addCell(label);
			label = new Label(11, 1, "电子邮件：", wf1);
			ws.addCell(label);
			label = new Label(12, 1, cobaModel.getLog_email(), wf1);
			ws.addCell(label);
			ws.mergeCells(12, 1, 13, 1);
			label = new Label(7, 2, "账单编号：", wf1);
			ws.addCell(label);
			label = new Label(8, 2, billNo, wf1);
			ws.addCell(label);
			ws.mergeCells(8, 2, 13, 2);

			// 设置字体13号粗体
			WritableCellFormat wf2 = getFormatBold();
			ws.mergeCells(0, 4, 13, 4);
			label = new Label(0, 4, cobaModel.getOwnmonth().substring(0, 4)
					+ "年" + cobaModel.getOwnmonth().substring(4, 6) + "月费用明细",
					wf2);
			ws.addCell(label);

		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	// 写入深圳本地服务收费明细
	private int writeLocality(WritableSheet ws, int row) {
		Label label = null;
		jxl.write.Number num = null;
		// 设置字体9号宋体
		WritableCellFormat wf1 = getFormat();
		try {
			// 插入本地服务数据
			if (localityMap.size() > 0) {
				// 输出表头
				label = new Label(0, row, "表1：以下为贵司员工深圳本地服务收费明细:", wf1);
				ws.addCell(label);
				ws.mergeCells(0, row, 5, row);
				row++;
				int col = 0;
				int shebaoListSize = shebaoList.size();
				int gjjListSize = gjjList.size();
				int emProductListSize = emProductList.size();

				// 设置字体居中、边框
				WritableCellFormat wf3 = getFormatBorderLine();

				ws.mergeCells(col, row, col, row + 1);
				label = new Label(col, row, "ID", wf3);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 1);
				label = new Label(col, row, "员工编号", wf3);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 1);
				label = new Label(col, row, "姓名", wf3);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 1);
				label = new Label(col, row, "户籍", wf3);
				ws.addCell(label);
				col++;

				if (shebaoListSize > 0) {
					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "社保基数", wf3);
					ws.addCell(label);
					col++;
				}
				if (gjjListSize > 0) {
					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "公积金基数", wf3);
					ws.addCell(label);
					col++;
				}
				// 社保表头
				if (shebaoListSize > 0) {
					label = new Label(col, row, "养老保险", wf3);
					ws.mergeCells(col, row, col + 1, row);
					ws.addCell(label);
					label = new Label(col, row + 1, "公司支付", wf3);
					ws.addCell(label);
					col++;
					label = new Label(col, row + 1, "个人支付", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, "医疗保险", wf3);
					ws.mergeCells(col, row, col + 1, row);
					ws.addCell(label);
					label = new Label(col, row + 1, "公司支付", wf3);
					ws.addCell(label);
					col++;
					label = new Label(col, row + 1, "个人支付", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, "生育保险", wf3);
					ws.addCell(label);
					label = new Label(col, row + 1, "公司支付", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, "工伤保险", wf3);
					ws.addCell(label);
					label = new Label(col, row + 1, "公司支付", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, "失业保险", wf3);
					ws.mergeCells(col, row, col + 1, row);
					ws.addCell(label);
					label = new Label(col, row + 1, "公司支付", wf3);
					ws.addCell(label);
					col++;
					label = new Label(col, row + 1, "个人支付", wf3);
					ws.addCell(label);
					col++;
				}
				if (gjjListSize > 0) {
					label = new Label(col, row, "住房公积金", wf3);
					ws.mergeCells(col, row, col + 1, row);
					ws.addCell(label);
					label = new Label(col, row + 1, "公司支付", wf3);
					ws.addCell(label);
					col++;
					label = new Label(col, row + 1, "个人支付", wf3);
					ws.addCell(label);
					col++;
				}
				if (shebaoListSize > 0) {
					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "社保合计", wf3);
					ws.addCell(label);
					col++;
				}
				if (gjjListSize > 0) {
					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "公积金合计", wf3);
					ws.addCell(label);
					col++;
				}
				if (emProductListSize > 0) {
					for (EmFinanceProductItemListModel m : emProductList.get(0)
							.getItemList()) {
						ws.mergeCells(col, row, col, row + 1);
						label = new Label(col, row, m.getName(), wf3);
						ws.addCell(label);
						col++;
					}
				}
				ws.mergeCells(col, row, col, row + 1);
				label = new Label(col, row, "合计", wf3);
				ws.addCell(label);
				col++;
				row++;

				// 详细数据输出
				Collection<EmFinanceReportForLocalityModel> collection = localityMap
						.values();
				int r = row;
				for (EmFinanceReportForLocalityModel m : collection) {
					col = 0;
					row++;

					num = new jxl.write.Number(col, row, row - r, wf3);
					ws.addCell(num);
					col++;

					num = new jxl.write.Number(col, row, m.getGid(), wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, m.getName(), wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, m.getHj(), wf3);
					ws.addCell(label);
					col++;

					// 社保基数
					if (shebaoListSize > 0) {
						if (m.getShebaoModel() != null) {
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_radix()
									.doubleValue(), wf3);

						} else {
							num = new jxl.write.Number(col, row, 0, wf3);
						}
						ws.addCell(num);
						col++;
					}
					// 公积金基数
					if (gjjListSize > 0) {
						if (m.getGjjModel() != null) {
							num = new jxl.write.Number(col, row, m
									.getGjjModel().getEfhg_emhu_radix()
									.doubleValue(), wf3);
						} else {
							num = new jxl.write.Number(col, row, 0, wf3);
						}
						ws.addCell(num);
						col++;
					}

					// 社保
					if (shebaoListSize > 0) {
						if (m.getShebaoModel() != null) {
							// 养老
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_ylcp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_ylop()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							// 医疗
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_jlcp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_jlop()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							// 生育
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_syucp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							// 工伤
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_gscp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							// 失业
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_syecp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_syeop()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;
						} else {
							// 养老
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 医疗
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 生育
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 工伤
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 失业
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;
						}
					}

					// 公积金
					if (gjjListSize > 0) {
						if (m.getGjjModel() != null) {
							num = new jxl.write.Number(col, row, m
									.getGjjModel().getEfhg_emhu_cp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, m
									.getGjjModel().getEfhg_emhu_op()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;
						} else {
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;
						}
					}
					// 社保合计
					if (shebaoListSize > 0) {
						if (m.getShebaoModel() != null) {
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_receivable()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;
						} else {
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;
						}
					}

					// 公积金合计
					if (gjjListSize > 0) {
						if (m.getGjjModel() != null) {
							num = new jxl.write.Number(col, row, m
									.getGjjModel().getEfhg_receivable()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;
						} else {
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;
						}
					}

					// 员工福利及服务费
					if (emProductListSize > 0) {
						if (m.getEmProductModel() != null) {
							for (EmFinanceProductItemListModel epm : m
									.getEmProductModel().getItemList()) {
								num = new jxl.write.Number(col, row, epm
										.getReceivable().doubleValue(), wf3);
								ws.addCell(num);
								col++;
							}
						} else {
							for (int i = 0; i < emProductList.get(0)
									.getItemList().size(); i++) {
								num = new jxl.write.Number(col, row, 0, wf3);
								ws.addCell(num);
								col++;
							}
						}
					}

					// 行合计
					num = new jxl.write.Number(col, row, m.sumTotalReceivable()
							.doubleValue(), wf3);
					ws.addCell(num);
					col++;
				}
				// 列合计
				row++;
				int c = 3;
				if (shebaoListSize > 0)
					c++;
				if (gjjListSize > 0)
					c++;
				ws.mergeCells(0, row, c, row);
				label = new Label(0, row, "合计", wf3);
				ws.addCell(label);
				String formula = "";
				Formula f = null;
				for (int i = c + 2; i < col + 1; i++) {
					formula = "SUM(" + getColumnName(i) + (r + 2) + ":"
							+ getColumnName(i) + row + ")";
					f = new Formula(i - 1, row, formula, wf3);
					ws.addCell(f);
				}
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

	// 写入工资收费明细
	private int writeSalary(WritableSheet ws, int row) {
		try {
			if (salaryList.size() > 0) {
				Label label = null;
				jxl.write.Number num = null;
				// 设置字体9号宋体
				WritableCellFormat wf1 = getFormat();
				row = row + 2;

				// 输出表头
				label = new Label(0, row, "附件：以下为员工工资明细:", wf1);
				ws.addCell(label);
				ws.mergeCells(0, row, 5, row);
				row++;
				int col = 0;

				// 设置字体居中、边框
				WritableCellFormat wf3 = getFormatBorderLine();

				label = new Label(col, row, "ID", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "员工编号", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "姓名", wf3);
				ws.addCell(label);
				col++;

				for (EmSalaryBaseAddItemModel m : salaryList.get(0)
						.getEmsdModel().getItemList()) {
					label = new Label(col, row, m.getCsii_item_name(), wf3);
					ws.addCell(label);
					col++;
				}

				label = new Label(col, row, "合计", wf3);
				ws.addCell(label);

				// 详细数据输出
				int r = row;
				for (EmFinanceSalaryModel m : salaryList) {
					col = 0;
					row++;
					num = new jxl.write.Number(col, row, row - r, wf3);
					ws.addCell(num);
					col++;

					num = new jxl.write.Number(col, row, m.getGid(), wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, m.getEmba_name(), wf3);
					ws.addCell(label);
					col++;

					for (EmSalaryBaseAddItemModel im : m.getEmsdModel()
							.getItemList()) {
						num = new jxl.write.Number(col, row, im.getAmount()
								.doubleValue(), wf3);
						ws.addCell(num);
						col++;
					}
					// 行合计
					num = new jxl.write.Number(col, row, m.getEfsa_receivable()
							.doubleValue(), wf3);
					ws.addCell(num);
					col++;
				}
				// 列合计
				row++;
				int c = 2;
				ws.mergeCells(0, row, c, row);
				label = new Label(0, row, "合计", wf3);
				ws.addCell(label);
				String formula = "";
				Formula f = null;
				for (int i = c + 2; i < col + 1; i++) {
					formula = "SUM(" + getColumnName(i) + (r + 2) + ":"
							+ getColumnName(i) + row + ")";
					f = new Formula(i - 1, row, formula, wf3);
					ws.addCell(f);
				}
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

	// 写入个税收费明细
	private int writeTax(WritableSheet ws, int row) {
		try {
			if (emTaxList.size() > 0) {
				Label label = null;
				jxl.write.Number num = null;
				// 设置字体9号宋体
				WritableCellFormat wf1 = getFormat();
				row = row + 2;

				// 输出表头
				label = new Label(0, row, "附件：以下为员工个税明细:", wf1);
				ws.addCell(label);
				ws.mergeCells(0, row, 5, row);
				row++;
				int col = 0;

				// 设置字体居中、边框
				WritableCellFormat wf3 = getFormatBorderLine();

				label = new Label(col, row, "ID", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "员工编号", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "姓名", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "个税类型", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "税款", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "合计", wf3);
				ws.addCell(label);

				// 详细数据输出
				int r = row;
				for (EmFinanceTaxModel m : emTaxList) {
					col = 0;
					row++;
					num = new jxl.write.Number(col, row, row - r, wf3);
					ws.addCell(num);
					col++;

					num = new jxl.write.Number(col, row, m.getGid(), wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, m.getEmba_name(), wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, m.getEfta_tax_classStr(), wf3);
					ws.addCell(label);
					col++;

					num = new jxl.write.Number(col, row, m.getEfta_tax()
							.doubleValue(), wf3);
					ws.addCell(num);
					col++;

					// 行合计
					num = new jxl.write.Number(col, row, m.getEfta_receivable()
							.doubleValue(), wf3);
					ws.addCell(num);
					col++;
				}
				// 列合计
				row++;
				int c = 3;
				ws.mergeCells(0, row, c, row);
				label = new Label(0, row, "合计", wf3);
				ws.addCell(label);
				String formula = "";
				Formula f = null;
				for (int i = c + 2; i < col + 1; i++) {
					formula = "SUM(" + getColumnName(i) + (r + 2) + ":"
							+ getColumnName(i) + row + ")";
					f = new Formula(i - 1, row, formula, wf3);
					ws.addCell(f);
				}
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

	// 写入委托外地收费明细
	private int writeCommissionOut(WritableSheet ws, int row) {
		try {
			if (emCommissionOutList.size() > 0) {
				Label label = null;
				jxl.write.Number num = null;
				// 设置字体9号宋体
				WritableCellFormat wf1 = getFormat();
				// 设置字体居中、边框
				WritableCellFormat wf3 = getFormatBorderLine();

				// 循环城市
				for (EmFinanceCommissionOutCityModel m : emCommissionOutList) {
					row = row + 2;
					// 输出表头
					label = new Label(0, row, "以下为委托到" + m.getCity()
							+ "员工收费明细:", wf1);
					ws.addCell(label);
					ws.mergeCells(0, row, 5, row);
					row++;
					int col = 0;

					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "ID", wf3);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "员工编号", wf3);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "姓名", wf3);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "缴费标准", wf3);
					ws.addCell(label);
					col++;

					// 委托明细表头
					for (EmFinanceCommissionOutDetailItemModel dm : m
							.getDetailItemList()) {
						ws.mergeCells(col, row, col + 2, row);
						label = new Label(col, row, dm.getItem(), wf3);
						ws.addCell(label);

						label = new Label(col, row + 1, "基数", wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row + 1, "公司", wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row + 1, "个人", wf3);
						ws.addCell(label);
						col++;
					}

					// 委托福利项目表头
					for (EmFinanceCommissionOutDetailItemModel pm : m
							.getProductItemList()) {
						ws.mergeCells(col, row, col, row + 1);
						label = new Label(col, row, pm.getItem(), wf3);
						ws.addCell(label);
						col++;
					}
					// 合计
					ws.mergeCells(col, row, col, row + 1);
					label = new Label(col, row, "合计", wf3);
					ws.addCell(label);
					col++;
					row++;

					// 详细数据输出

					// 社保算法
					for (EmFinanceCommissionOutModel sm : m.getSoinList()) {
						col = 3;
						row++;

						label = new Label(col, row, sm.getSoin_title(), wf3);
						ws.addCell(label);
						col++;

						for (EmFinanceCommissionOutDetailItemModel im : sm
								.getDetailItemList()) {
							col++;
							label = new Label(col, row, im.getCpp(), wf3);
							ws.addCell(label);
							col++;
							label = new Label(col, row, im.getOpp(), wf3);
							ws.addCell(label);
							col++;
						}
						// 补齐改行空单元格
						for (int i = 0; i < m.getProductItemList().size() + 1; i++) {
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;
						}

					}
					// 记录数据起始行
					int r = row;
					// 委托明细收费数据
					for (EmFinanceCommissionOutModel cm : m
							.getEmFinanceCommissionOutList()) {
						col = 0;
						row++;
						num = new jxl.write.Number(col, row, row - r, wf3);
						ws.addCell(num);
						col++;

						num = new jxl.write.Number(col, row, cm.getGid(), wf3);
						ws.addCell(num);
						col++;

						label = new Label(col, row, cm.getEmba_name(), wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row, cm.getSoin_title(), wf3);
						ws.addCell(label);
						col++;

						// DetailItem
						for (EmFinanceCommissionOutDetailItemModel dm : cm
								.getDetailItemList()) {
							num = new jxl.write.Number(col, row, dm.getBase()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, dm.getCoSum()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, dm.getEmSum()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;
						}

						// ProductItem
						
						for (EmFinanceCommissionOutDetailItemModel pm : cm
								.getProductItemList()) {
							num = new jxl.write.Number(col, row, pm
									.getReceivable().doubleValue(), wf3);
						
							ws.addCell(num);
							col++;
						}
						// 行合计
						num = new jxl.write.Number(col, row, cm
								.getEfco_receivable().doubleValue(), wf3);
						ws.addCell(num);
						col++;
					}

					// 列合计
					row++;
					int c = 3;
					ws.mergeCells(0, row, c, row);
					label = new Label(0, row, "合计", wf3);
					ws.addCell(label);
					String formula = "";
					Formula f = null;
					for (int i = c + 2; i < col + 1; i++) {
						if ("基数".equals(ws.getCell(i - 1,
								r - m.getSoinList().size()).getContents())) {
							// 基数列不写入公式
							label = new Label(i - 1, row, "", wf3);
							ws.addCell(label);
						} else {
							formula = "SUM(" + getColumnName(i) + (r + 2) + ":"
									+ getColumnName(i) + row + ")";
							f = new Formula(i - 1, row, formula, wf3);
							ws.addCell(f);
						}
					}

				}

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

	// 写入员工非标收费明细
	private int writeEmFinanceDisposable(WritableSheet ws, int row) {
		try {
			if (emDisposableList.size() > 0) {
				Label label = null;
				jxl.write.Number num = null;
				// 设置字体9号宋体
				WritableCellFormat wf1 = getFormat();
				row = row + 2;

				// 输出表头
				label = new Label(0, row, "以下为本月员工收费调整:", wf1);
				ws.addCell(label);
				ws.mergeCells(0, row, 5, row);
				row++;
				int col = 0;

				// 设置字体居中、边框
				WritableCellFormat wf3 = getFormatBorderLine();

				label = new Label(col, row, "ID", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "员工编号", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "姓名", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "收费金额", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "收费原因", wf3);
				ws.addCell(label);
				ws.mergeCells(col, row, col + 10, row);

				// 详细数据输出
				int r = row;
				for (EmFinanceDisposableModel m : emDisposableList) {
					col = 0;
					row++;
					num = new jxl.write.Number(col, row, row - r, wf3);
					ws.addCell(num);
					col++;

					num = new jxl.write.Number(col, row, m.getGid(), wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, m.getEmba_name(), wf3);
					ws.addCell(label);
					col++;

					num = new jxl.write.Number(col, row, m.getEfdi_Receivable()
							.doubleValue(), wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, m.getEfdi_Reason(), wf3);
					ws.addCell(label);
					ws.mergeCells(col, row, col + 10, row);
					col++;
				}
				// 列合计
				row++;
				int c = 2;
				ws.mergeCells(0, row, c, row);
				label = new Label(0, row, "合计", wf3);
				ws.addCell(label);
				String formula = "SUM(" + getColumnName(4) + (r + 2) + ":"
						+ getColumnName(4) + row + ")";
				Formula f = new Formula(3, row, formula, wf3);
				ws.addCell(f);
				label = new Label(4, row, "", wf3);
				ws.addCell(label);
				ws.mergeCells(4, row, 4 + 10, row);
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

	// 写入公司账号信息
	private void writeFinance(WritableSheet ws, int row,
			BigDecimal billReceivable) {
		// 设置字体9号宋体
		WritableCellFormat wf1 = getFormat();
		row = row + 3;
		try {
			NumberFormat f = new DecimalFormat("#,###.##");

			ws.mergeCells(0, row, 5, row);
			Label label = new Label(0, row, "总合计:RMB "
					+ f.format(billReceivable) + " 于15号前汇入我公司帐户,谢谢!", wf1);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "我公司开户银行：平安银行长城大厦支行营业部", wf1);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "公司名称：深圳中智经济技术合作有限公司", wf1);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "帐号：11002875321101", wf1);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "深圳中智经济技术合作有限公司", wf1);
			ws.addCell(label);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

	// 拼装本地服务产品
	private void getLocalityMap() {
		localityMap = new HashMap<Integer, EmFinanceReportForLocalityModel>();
		joinSheBao();
		joinGjj();
		joinEmProduct();
	}

	// 拼装社保
	private void joinSheBao() {
		EmFinanceReportForLocalityModel eflm;
		for (EmFinanceSheBaoModel m : shebaoList) {
			if (localityMap.containsKey(m.getGid())) {
				eflm = localityMap.get(m.getGid());
				eflm.setShebaoModel(m);
			} else {
				eflm = new EmFinanceReportForLocalityModel();
				eflm.setGid(m.getGid());
				eflm.setName(m.getEmba_name());
				eflm.setHj(m.getEfsb_esiu_hj());
				eflm.setShebaoModel(m);
				localityMap.put(m.getGid(), eflm);
			}
		}
	}

	// 拼装公积金
	private void joinGjj() {
		EmFinanceReportForLocalityModel eflm;
		for (EmFinanceHouseGjjModel m : gjjList) {
			if (localityMap.containsKey(m.getGid())) {
				eflm = localityMap.get(m.getGid());
				eflm.setGjjModel(m);
			} else {
				eflm = new EmFinanceReportForLocalityModel();
				eflm.setGid(m.getGid());
				eflm.setName(m.getEmba_name());
				eflm.setHj(m.getEfhg_emhu_hj());
				eflm.setGjjModel(m);
				localityMap.put(m.getGid(), eflm);
			}
		}
	}

	// 拼装员工福利产品
	private void joinEmProduct() {
		EmFinanceReportForLocalityModel eflm;
		for (EmFinanceProductModel m : emProductList) {
			
			if (localityMap.containsKey(m.getGid())) {
				eflm = localityMap.get(m.getGid());
				eflm.setEmProductModel(m);
			} else {
				eflm = new EmFinanceReportForLocalityModel();
				eflm.setGid(m.getGid());
				eflm.setName(m.getEmba_name());
				eflm.setEmProductModel(m);
				localityMap.put(m.getGid(), eflm);
			}
		}
	}

	// 字体设置
	private WritableCellFormat getFormat() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 9);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.LEFT);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 字体居中加粗
	private WritableCellFormat getFormatBold() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 13,
				WritableFont.BOLD);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.RIGHT);
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

	// 获取一列对应的字母。例如：ColumnNum=1，则返回值为A 列号转字母
	public String getColumnName(int columnNum) {
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
