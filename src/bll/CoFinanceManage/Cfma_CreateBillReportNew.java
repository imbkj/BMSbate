package bll.CoFinanceManage;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.zkoss.zul.ListModelList;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
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
import Model.CoFinanceDisposableModel;
import Model.CoFinanceProductModel;
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
import Model.EmFinanceValueAddTaxModel;
import Model.EmSalaryBaseAddItemModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.pinyin4jUtil;
import dal.CoFinanceManage.Cfma_BillReportOperateDal;

public class Cfma_CreateBillReportNew {
	private WritableWorkbook wb;
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
	// 公司福利
	private List<CoFinanceProductModel> coProductList;
	// 公司非标
	private List<CoFinanceDisposableModel> coDisposableList;
	// 增值税金
	private List<EmFinanceValueAddTaxModel> vtaList;

	// 本地服务合并后的Map
	private Map<String, EmFinanceReportForLocalityModel> localityMap;

	// 表号
	private int tableNo = 0;

	public Cfma_CreateBillReportNew() {

	}

	// 生成付款通知
	public boolean createReport(String billNo,
			List<EmFinanceSheBaoModel> shebaoList,
			List<EmFinanceHouseGjjModel> gjjList,
			List<EmFinanceSalaryModel> salaryList,
			List<EmFinanceProductModel> emProductList,
			List<EmFinanceCommissionOutCityModel> emCommissionOutList,
			List<EmFinanceDisposableModel> emDisposableList,
			List<EmFinanceTaxModel> emTaxList,
			List<CoFinanceProductModel> coProductList,
			List<CoFinanceDisposableModel> coDisposableList,
			List<EmFinanceValueAddTaxModel> vtaList) {
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
		if (coProductList != null) {
			this.coProductList = coProductList;
		}
		if (coDisposableList != null) {
			this.coDisposableList = coDisposableList;
		}
		if (vtaList != null) {
			this.vtaList = vtaList;
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
			this.wb = Workbook.createWorkbook(new File(absolutePath
					+ "CoFinanceManage/File/billReport/" + billNo + ".xls"));
			wb.setProtected(true);
			// 创建Excel工作表
			WritableSheet ws = wb.createSheet(cobaModel.getCoba_shortname()
					+ cobaModel.getOwnmonth() + "payment", 0);

			// 去掉整个sheet中的网格线
			// ws.getSettings().setShowGridLines(false);

			// 根据内容自动设置列宽
			// CellView cellView = new CellView();
			// cellView.setAutosize(true);
			// for (int i = 0; i < 10; i++) {
			// ws.setColumnView(i, cellView);
			// }

			// 设置列的默认列宽 （所有单元格）
			ws.getSettings().setDefaultColumnWidth(9);
			// 设置列的默认行高（所有单元格）
			ws.getSettings().setDefaultRowHeight(400);

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

			// 写入公司收费明细
			row = writeCoFinance(ws, row);

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
			// 设置字体
			WritableCellFormat wf1 = getFormat();
			// 插入公司及客服信息
			label = new Label(0, 0, "Company NO.：", wf1);
			ws.addCell(label);
			num = new jxl.write.Number(1, 0, cobaModel.getCid(), wf1);
			ws.addCell(num);
			label = new Label(2, 0, "Company Name：", wf1);
			ws.addCell(label);
			label = new Label(3, 0, cobaModel.getCoba_company(), wf1);
			ws.addCell(label);
			ws.mergeCells(3, 0, 7, 0);
			label = new Label(0, 1, "Customer Servicer：", wf1);
			ws.addCell(label);
			label = new Label(1, 1, cobaModel.getCoba_client(), wf1);
			ws.addCell(label);
			label = new Label(2, 1, "Contact Number：", wf1);
			ws.addCell(label);
			label = new Label(3, 1, "0755-" + cobaModel.getLog_tel(), wf1);
			ws.addCell(label);
			ws.mergeCells(3, 1, 4, 1);
			label = new Label(5, 1, "Email Adress：", wf1);
			ws.addCell(label);
			label = new Label(6, 1, cobaModel.getLog_email(), wf1);
			ws.addCell(label);
			ws.mergeCells(6, 1, 7, 1);
			label = new Label(0, 2, "Account Manager：", wf1);
			ws.addCell(label);
			label = new Label(1, 2, cobaModel.getCoba_clientmanager(), wf1);
			ws.addCell(label);
			label = new Label(2, 2, "Contact Number：", wf1);
			ws.addCell(label);
			label = new Label(3, 2, "0755-" + cobaModel.getLm_tel(), wf1);
			ws.addCell(label);
			ws.mergeCells(3, 2, 4, 2);
			label = new Label(5, 2, "Email Adress：", wf1);
			ws.addCell(label);
			label = new Label(6, 2, cobaModel.getLm_email(), wf1);
			ws.addCell(label);
			ws.mergeCells(6, 2, 7, 2);

			// 设置字体18号粗体
			WritableCellFormat wf2 = getFormatBold();
			ws.mergeCells(0, 3, 25, 5);
			label = new Label(0, 3, cobaModel.getOwnmonth().substring(0, 4)
					+ "年" + cobaModel.getOwnmonth().substring(4, 6) + "月付款通知",
					wf2);
			ws.addCell(label);

		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: writeLocality
	 * @Description: TODO(写入深圳本地服务收费明细)
	 * @param ws
	 * @param row
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	private int writeLocality(WritableSheet ws, int row) {
		Label label = null;
		jxl.write.Number num = null;
		// 设置副标题字体
		WritableCellFormat sf = getSubtitleFormat();
		try {
			// 插入本地服务数据
			if (localityMap.size() > 0) {
				// 输出表头
				tableNo++;
				label = new Label(0, row,
						"表" + tableNo + "：以下为贵司员工深圳本地服务收费明细:", sf);
				ws.addCell(label);
				ws.mergeCells(0, row, 10, row);
				row++;
				int col = 0;
				int shebaoListSize = shebaoList.size();
				int gjjListSize = gjjList.size();
				int emProductListSize = emProductList.size();

				// 设置字体
				WritableCellFormat wfh1 = getHeaderFormat1(Colour.AQUA,
						"#B2A1C7");
				WritableCellFormat wfh2 = getHeaderFormat1(Colour.CORAL,
						"#D7E4BC");
				WritableCellFormat wfh3 = getHeaderFormat1(null, null);

				WritableCellFormat wf3 = getFormatBorderLine();

				// 设置表头行高
				ws.setRowView(row, 750);
				ws.setRowView(row + 1, 600);
				ws.setRowView(row + 2, 650);

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "No." + "\r\n" + "序号", wfh1);
				ws.addCell(label);
				col++;
				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "GID." + "\r\n" + "员工编号", wfh1);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "IDCARD" + "\r\n" + "身份证", wfh1);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "Chinese Name" + "\r\n" + "中文名",
						wfh1);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "English Name" + "\r\n" + "英文名",
						wfh2);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "Dept" + "\r\n" + "部门", wfh2);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "Title" + "\r\n" + "职位", wfh2);
				ws.addCell(label);
				col++;

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "Residence" + "\r\n" + "户籍", wfh2);
				ws.addCell(label);
				col++;

				// 社保表头
				if (shebaoListSize > 0) {
					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Standard" + "\r\n" + "缴费标准",
							wfh1);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Social Insurance Base"
							+ "\r\n" + "社保基数", wfh1);
					ws.addCell(label);
					col++;

					// 养老
					ws.mergeCells(col, row, col + 2, row);
					label = new Label(col, row, "Pension" + "\r\n" + "养老保险",
							wfh1);
					ws.addCell(label);
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "Pension Base" + "\r\n"
							+ "养老基数", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1,
							"company" + "\r\n" + "公司支付", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "staff" + "\r\n" + "个人支付",
							wfh1);
					ws.addCell(label);
					col++;
					// 医疗
					ws.mergeCells(col, row, col + 2, row);
					label = new Label(col, row, "Medical Insurace" + "\r\n"
							+ "医疗保险", wfh1);
					ws.addCell(label);
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "Medical Base" + "\r\n"
							+ "医疗基数", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1,
							"company" + "\r\n" + "公司支付", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "staff" + "\r\n" + "个人支付",
							wfh1);
					ws.addCell(label);
					col++;

					// 生育
					ws.mergeCells(col, row, col + 1, row);
					label = new Label(col, row, "Parturition Insurance"
							+ "\r\n" + "生育保险", wfh1);
					ws.addCell(label);
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "Parturition Base" + "\r\n"
							+ "生育基数", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1,
							"company" + "\r\n" + "公司支付", wfh1);
					ws.addCell(label);
					col++;

					// 工伤
					ws.mergeCells(col, row, col + 1, row);
					label = new Label(col, row, "Work-related Injury Insurance"
							+ "\r\n" + "工伤保险", wfh1);
					ws.addCell(label);
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "Work-related Injury Base"
							+ "\r\n" + "工伤基数", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1,
							"company" + "\r\n" + "公司支付", wfh1);
					ws.addCell(label);
					col++;

					// 失业
					ws.mergeCells(col, row, col + 2, row);
					label = new Label(col, row, "Unemployment Insurance"
							+ "\r\n" + "失业保险", wfh1);
					ws.addCell(label);
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "Unemployment Base"
							+ "\r\n" + "失业基数", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1,
							"company" + "\r\n" + "公司支付", wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "staff" + "\r\n" + "个人支付",
							wfh1);
					ws.addCell(label);
					col++;

					// 社保公司合计
					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Social Insurance for company"
							+ "\r\n" + "社保公司合计", wfh1);
					ws.addCell(label);
					col++;

					// 社保个人合计
					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Social Insurance for staff"
							+ "\r\n" + "社保个人合计", wfh1);
					ws.addCell(label);
					col++;

				}
				// 公积金
				if (gjjListSize > 0) {
					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Housing Fund Base" + "\r\n"
							+ "公积金基数", wfh1);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col + 1, row);
					label = new Label(col, row, "Housing Fund" + "\r\n"
							+ "住房公积金", wfh1);
					ws.addCell(label);
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "company" + "\r\n" + "公司",
							wfh1);
					ws.addCell(label);
					col++;
					ws.mergeCells(col, row + 1, col, row + 2);
					label = new Label(col, row + 1, "staff" + "\r\n" + "个人",
							wfh1);
					ws.addCell(label);
					col++;

				}
				if (emProductListSize > 0) {
					for (EmFinanceProductItemListModel m : emProductList.get(0)
							.getItemList()) {
						ws.mergeCells(col, row, col, row + 2);
						label = new Label(col, row, m.getName(), wfh3);
						ws.addCell(label);
						col++;
					}
				}
				if (vtaList.size() > 0) {
					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "税金", wfh3);
					ws.addCell(label);
					col++;
				}

				ws.mergeCells(col, row, col, row + 2);
				label = new Label(col, row, "CIIC Cost Total" + "\r\n"
						+ "中智费用总计", wfh1);
				ws.addCell(label);
				col++;
				row = row + 2;

				// 详细数据输出

				List<Map.Entry<String, EmFinanceReportForLocalityModel>> mappinglist = new ArrayList<Map.Entry<String, EmFinanceReportForLocalityModel>>(
						localityMap.entrySet());

				Collections
						.sort(mappinglist,
								new Comparator<Map.Entry<String, EmFinanceReportForLocalityModel>>() {
									public int compare(
											Map.Entry<String, EmFinanceReportForLocalityModel> m1,
											Map.Entry<String, EmFinanceReportForLocalityModel> m2) {
										EmFinanceReportForLocalityModel mf1 = m1
												.getValue();
										EmFinanceReportForLocalityModel mf2 = m2
												.getValue();

										return pinyin4jUtil.getPinYinHeadChar(
												mf1.getName()).compareTo(
												pinyin4jUtil
														.getPinYinHeadChar(mf2
																.getName()));
									}
								});

				Collection<EmFinanceReportForLocalityModel> collection = new ListModelList<>();
				for (Map.Entry<String, EmFinanceReportForLocalityModel> m : mappinglist) {
					collection.add(m.getValue());
				}

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

					label = new Label(col, row, m.getIdcard(), wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, m.getName(), wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, "", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, "", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, "", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, m.getHj(), wf3);
					ws.addCell(label);
					col++;

					// 社保基数
					if (shebaoListSize > 0) {
						label = new Label(col, row, "", wf3);
						ws.addCell(label);
						col++;
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

					// 社保
					if (shebaoListSize > 0) {
						if (m.getShebaoModel() != null) {
							// 养老
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

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
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

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
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_syucp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							// 工伤
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_gscp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							// 失业
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

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

							// 社保合计
							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_totalcp()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, m
									.getShebaoModel().getEfsb_esiu_totalop()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;
						} else {
							// 养老
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 医疗
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 生育
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 工伤
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 失业
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

							// 社保合计
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
									.getGjjModel().getEfhg_emhu_radix()
									.doubleValue(), wf3);
							ws.addCell(num);
							col++;

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
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;

							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;

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

					BigDecimal vta = new BigDecimal(0);
					if (vtaList.size() > 0) {
						boolean b = false;
						for (EmFinanceValueAddTaxModel vm : vtaList) {

							if (vm.getGid().equals(m.getGid()) && !vm.isWrite()) {
								if (vm.getTypes().equals(1)) {
									b = true;
									vm.setWrite(true);
									num = new jxl.write.Number(col, row,
											vm.getEfvt_Receivable()
													.doubleValue(), wf3);
									vta=vta.add(vm.getEfvt_Receivable());
									ws.addCell(num);
									col++;
									break;
								}
							}
						}
						if (!b) {
							num = new jxl.write.Number(col, row, 0, wf3);
							ws.addCell(num);
							col++;
						}
					}

					// 行合计
					num = new jxl.write.Number(col, row, m.sumTotalReceivable().add(vta)
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
				ws.mergeCells(0, row, 5, row);
				label = new Label(0, row, "小计", wf3);
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

	/**
	 * @Title: writeCommissionOut
	 * @Description: TODO(写入委托外地收费明细)
	 * @param ws
	 * @param row
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	private int writeCommissionOut(WritableSheet ws, int row) {
		try {
			if (emCommissionOutList.size() > 0) {
				Label label = null;
				jxl.write.Number num = null;
				// 设置副标题字体
				WritableCellFormat sf = getSubtitleFormat();
				// 设置字体
				WritableCellFormat wfh1 = getHeaderFormat1(Colour.AQUA,
						"#B2A1C7");
				WritableCellFormat wfh2 = getHeaderFormat1(Colour.CORAL,
						"#D7E4BC");
				WritableCellFormat wfh3 = getHeaderFormat1(null, null);

				WritableCellFormat wf3 = getFormatBorderLine();

				// 循环城市
				for (EmFinanceCommissionOutCityModel m : emCommissionOutList) {
					row = row + 2;
					// 输出表头
					tableNo++;
					label = new Label(0, row, "表" + tableNo + "：以下为贵司"
							+ m.getCity() + "员工收费明细:", sf);
					ws.addCell(label);
					ws.mergeCells(0, row, 10, row);
					row++;
					int col = 0;

					// 设置表头行高
					ws.setRowView(row, 750);
					ws.setRowView(row + 1, 600);
					ws.setRowView(row + 2, 650);

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "No." + "\r\n" + "序号", wfh1);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "GID." + "\r\n" + "员工编号", wfh1);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "IDCARD" + "\r\n" + "身份证", wfh1);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row,
							"Chinese Name" + "\r\n" + "中文名", wfh1);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row,
							"English Name" + "\r\n" + "英文名", wfh2);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Dept" + "\r\n" + "部门", wfh2);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Title" + "\r\n" + "职位", wfh2);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Residence" + "\r\n" + "户籍",
							wfh2);
					ws.addCell(label);
					col++;

					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "Standard" + "\r\n" + "缴费标准",
							wfh1);
					ws.addCell(label);
					col++;

					// 委托明细表头
					for (EmFinanceCommissionOutDetailItemModel dm : m
							.getDetailItemList()) {
						ws.mergeCells(col, row, col + 2, row);
						label = new Label(col, row, dm.getItem(), wfh1);
						ws.addCell(label);

						ws.mergeCells(col, row + 1, col, row + 2);
						label = new Label(col, row + 1, "Base" + "\r\n" + "基数",
								wfh1);
						ws.addCell(label);
						col++;

						ws.mergeCells(col, row + 1, col, row + 2);
						label = new Label(col, row + 1, "company" + "\r\n"
								+ "公司", wfh1);
						ws.addCell(label);
						col++;

						ws.mergeCells(col, row + 1, col, row + 2);
						label = new Label(col, row + 1,
								"staff" + "\r\n" + "个人", wfh1);
						ws.addCell(label);
						col++;
					}

					// 委托福利项目表头
					if (m.getProductItemList() != null) {

						for (EmFinanceCommissionOutDetailItemModel pm : m
								.getProductItemList()) {
							ws.mergeCells(col, row, col, row + 2);
							label = new Label(col, row, pm.getItem(), wfh3);
							ws.addCell(label);
							col++;
						}
					}

					// 税金
					if (vtaList.size() > 0) {
						ws.mergeCells(col, row, col, row + 2);
						label = new Label(col, row, "税金", wfh3);
						ws.addCell(label);
						col++;
					}

					// 合计
					ws.mergeCells(col, row, col, row + 2);
					label = new Label(col, row, "CIIC Cost Total" + "\r\n"
							+ "中智费用总计", wfh1);
					ws.addCell(label);
					col++;
					row = row + 2;

					// 详细数据输出

					// 社保算法
					for (EmFinanceCommissionOutModel sm : m.getSoinList()) {
						col = 8;
						row++;

						label = new Label(col, row, sm.getSoin_title(), wf3);
						ws.addCell(label);
						col++;

						for (EmFinanceCommissionOutDetailItemModel im : sm
								.getDetailItemList()) {
							label = new Label(col, row, "", wf3);
							ws.addCell(label);
							col++;
							label = new Label(col, row, im.getCpp(), wf3);
							ws.addCell(label);
							col++;
							label = new Label(col, row, im.getOpp(), wf3);
							ws.addCell(label);
							col++;
						}
						// 补齐改行空单元格
						if (m.getProductItemList() != null) {

							for (int i = 0; i < m.getProductItemList().size() + 1; i++) {
								label = new Label(col, row, "", wf3);
								ws.addCell(label);
								col++;
							}
						}
					}
					// 记录数据起始行
					int r = row;
					// 委托明细收费数据
					for (EmFinanceCommissionOutModel cm : m
							.getEmFinanceCommissionOutList()) {
						col = 0;
						row++;
						if (cm.getEfco_ecou_addtype() != null
								&& cm.getEfco_ecou_addtype().equals("一次性费用")) {
							cm.setEfco_receivable(new BigDecimal(0));
						}
						num = new jxl.write.Number(col, row, row - r, wf3);
						ws.addCell(num);
						col++;

						num = new jxl.write.Number(col, row, cm.getGid(), wf3);
						ws.addCell(num);
						col++;

						label = new Label(col, row, cm.getEmba_idcard(), wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row, cm.getEmba_name(), wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row, "", wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row, "", wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row, "", wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row, "", wf3);
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
						/**
						 * TODO:
						 */
						// ProductItem

						BigDecimal bm = new BigDecimal(0);
						if (cm.getProductItemList() != null) {

							for (EmFinanceCommissionOutDetailItemModel pm : cm
									.getProductItemList()) {
								num = new jxl.write.Number(col, row, pm
										.getReceivable().doubleValue(), wf3);
								ws.addCell(num);
								bm = bm.add(pm.getReceivable());
								col++;
							}
						}

						if (vtaList.size() > 0) {
							boolean b = false;
							for (EmFinanceValueAddTaxModel vm : vtaList) {
								if (vm.getGid().equals(cm.getGid())) {
									if (vm.getTypes().equals(2) && !vm.isWrite()) {
										b = true;
										vm.setWrite(true);
										num = new jxl.write.Number(col, row, vm
												.getEfvt_Receivable()
												.doubleValue(), wf3);
										ws.addCell(num);
										bm = bm.add(vm.getEfvt_Receivable());
										col++;
										break;
									}
								}
							}
							if (!b) {
								num = new jxl.write.Number(col, row, 0, wf3);
								ws.addCell(num);
								col++;
							}
						}

						if (cm.getEfco_ecou_welfare_sum() != null) {
							cm.setEfco_receivable(cm.getEfco_receivable()
									.subtract(cm.getEfco_ecou_welfare_sum()));
						}

						// 行合计
						num = new jxl.write.Number(
								col,
								row,
								(cm.getEfco_receivable().add(bm)).doubleValue(),
								wf3);

						ws.addCell(num);
						col++;
					}

					// 列合计
					row++;
					int c = 8;
					ws.mergeCells(0, row, c, row);
					label = new Label(0, row, "小计", wf3);
					ws.addCell(label);
					String formula = "";
					Formula f = null;
					for (int i = c + 2; i < col + 1; i++) {
						if (ws.getCell(i - 1, r - m.getSoinList().size() - 1)
								.getContents().contains("基数")) {
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

	// 写入公司收费明细
	private int writeCoFinance(WritableSheet ws, int row) {
		try {
			if (coDisposableList.size() > 0 || coProductList.size() > 0) {
				Label label = null;
				jxl.write.Number num = null;
				// 设置字体
				WritableCellFormat sf = getSubtitleFormat();
				row = row + 2;

				// 输出表头
				tableNo++;
				label = new Label(0, row, "表" + tableNo + "：其他费用:", sf);
				ws.addCell(label);
				ws.mergeCells(0, row, 10, row);
				row++;
				int col = 0;

				// 设置字体居中、边框
				WritableCellFormat wf3 = getFormatBorderLine();

				label = new Label(col, row, "序号", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "收费对象", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "名称", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "收费金额", wf3);
				ws.addCell(label);
				col++;

				label = new Label(col, row, "收费原因", wf3);
				ws.addCell(label);
				ws.mergeCells(col, row, col + 4, row);

				// 详细数据输出
				int r = row;
				// 公司费用
				for (CoFinanceProductModel m : coProductList) {
					col = 0;
					row++;
					num = new jxl.write.Number(col, row, row - r, wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, "公司", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, m.getCfpr_copr_name(), wf3);
					ws.addCell(label);
					col++;

					num = new jxl.write.Number(col, row, m.getCfpr_Receivable()
							.doubleValue(), wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, "", wf3);
					ws.addCell(label);
					ws.mergeCells(col, row, col + 4, row);
					col++;
				}
				// 公司非标
				for (CoFinanceDisposableModel m : coDisposableList) {
					col = 0;
					row++;
					num = new jxl.write.Number(col, row, row - r, wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, "公司", wf3);
					ws.addCell(label);
					col++;

					label = new Label(col, row, m.getCfdi_copr_name(), wf3);
					ws.addCell(label);
					col++;

					num = new jxl.write.Number(col, row, m.getCfdi_Receivable()
							.doubleValue(), wf3);
					ws.addCell(num);
					col++;

					label = new Label(col, row, m.getCfdi_Reason(), wf3);
					ws.addCell(label);
					ws.mergeCells(col, row, col + 4, row);
					col++;
				}
				// 公司非标
				for (EmFinanceValueAddTaxModel m : vtaList) {
					if (m.getGid().equals(0)) {

						col = 0;
						row++;
						num = new jxl.write.Number(col, row, row - r, wf3);
						ws.addCell(num);
						col++;

						label = new Label(col, row, "公司", wf3);
						ws.addCell(label);
						col++;

						label = new Label(col, row, "税金", wf3);
						ws.addCell(label);
						col++;

						num = new jxl.write.Number(col, row, m
								.getEfvt_Receivable().doubleValue(), wf3);
						ws.addCell(num);
						col++;

						label = new Label(col, row, "", wf3);
						ws.addCell(label);
						ws.mergeCells(col, row, col + 4, row);
						col++;
					}
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
				ws.mergeCells(4, row, 4 + 4, row);
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
		// 设置字体
		WritableCellFormat wftBlack = getTailFormat(Colour.BLACK);
		WritableCellFormat wftBlue = getTailFormat(Colour.BLUE);
		row = row + 3;
		try {
			NumberFormat f = new DecimalFormat("#,###.##");

			ws.mergeCells(0, row, 10, row);
			Label label = new Label(
					0,
					row,
					"Please transfer the total of all cost to the following account before 10th of each month. ",
					wftBlack);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 10, row);
			label = new Label(0, row, "请在每月10日前将以下款项汇至我司以下帐户", wftBlack);
			ws.addCell(label);
			row++;

			row++;
			label = new Label(0, row, "Total：", wftBlack);
			ws.addCell(label);
			ws.mergeCells(1, row, 3, row);
			label = new Label(1, row, f.format(billReceivable), wftBlack);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "Bank Account name: SZCIIC. CO., LTD",
					wftBlack);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "帐户名：深圳中智经济技术合作有限公司", wftBlue);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 10, row);
			label = new Label(
					0,
					row,
					"Bank Name:PING AN BANK CO., LTD (FORMERLY SHENZHEN DEVELOPMENT BANK CO., LTD)",
					wftBlack);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "银行名称：平安银行深圳长城支行", wftBlue);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "Bank Account Number: 11002875321101",
					wftBlack);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "帐号：11002875321101", wftBlue);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "Swift Code: SZDBCNBS", wftBlack);
			ws.addCell(label);
			row++;

			ws.mergeCells(0, row, 5, row);
			label = new Label(0, row, "Thanks for your cooperation.", wftBlack);
			ws.addCell(label);
			row++;
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

	// 拼装本地服务产品
	private void getLocalityMap() {
		localityMap = new TreeMap<String, EmFinanceReportForLocalityModel>();
		joinSheBao();
		joinGjj();
		joinEmProduct();
		joinTax();
	}

	private void joinTax() {
		for (EmFinanceValueAddTaxModel m : vtaList) {
			for (EmFinanceCommissionOutCityModel m2 : emCommissionOutList) {
				for (EmFinanceCommissionOutModel m3 : m2
						.getEmFinanceCommissionOutList()) {
					if (m.getGid().equals(m3.getGid())) {
						m.setTypes(2);
					}
				}
			}
			m.setTypes(m.getTypes() == null ? 1 : m.getTypes());
		}

	}

	// 拼装社保
	private void joinSheBao() {
		EmFinanceReportForLocalityModel eflm;
		for (EmFinanceSheBaoModel m : shebaoList) {

			if (localityMap.containsKey(String.valueOf(m.getGid())
					+ String.valueOf(m.getEfsb_esiu_ownmonth()))) {

				eflm = localityMap.get(String.valueOf(m.getGid())
						+ String.valueOf(m.getEfsb_esiu_ownmonth()));
				eflm.setShebaoModel(m);
			} else {
				eflm = new EmFinanceReportForLocalityModel();
				eflm.setGid(m.getGid());
				eflm.setName(m.getEmba_name());
				eflm.setHj(m.getEfsb_esiu_hj());
				eflm.setIdcard(m.getIdcard());
				eflm.setShebaoModel(m);
				localityMap.put(
						String.valueOf(m.getGid())
								+ String.valueOf(m.getEfsb_esiu_ownmonth()),
						eflm);
			}
		}
	}

	// 拼装公积金
	private void joinGjj() {
		EmFinanceReportForLocalityModel eflm;
		for (EmFinanceHouseGjjModel m : gjjList) {

			if (localityMap.containsKey(String.valueOf(m.getGid())
					+ String.valueOf(m.getEfhg_emhu_ownmonth()))) {

				eflm = localityMap.get(String.valueOf(m.getGid())
						+ String.valueOf(m.getEfhg_emhu_ownmonth()));
				eflm.setGjjModel(m);
			} else {
				eflm = new EmFinanceReportForLocalityModel();
				eflm.setGid(m.getGid());
				eflm.setName(m.getEmba_name());
				eflm.setHj(m.getEfhg_emhu_hj());
				eflm.setIdcard(m.getIdcard());
				eflm.setGjjModel(m);
				localityMap.put(
						String.valueOf(m.getGid())
								+ String.valueOf(m.getEfhg_emhu_ownmonth()),
						eflm);
			}
		}
	}

	/**
	 * @Title: joinEmProduct
	 * @Description: TODO(拼装员工福利产品)
	 * @return void 返回类型
	 * @throws
	 */
	private void joinEmProduct() {
		EmFinanceReportForLocalityModel eflm;
		System.out.println("");
		p: for (int i = 0; i < emProductList.size(); i++) {

			if (i < emProductList.size()) {

				for (EmFinanceCommissionOutCityModel m2 : emCommissionOutList) {

					for (EmFinanceCommissionOutModel m3 : m2
							.getEmFinanceCommissionOutList()) {

						if (Integer.valueOf(emProductList.get(i).getGid())
								.equals(m3.getGid())) {

							// 获取员工本地数据源
							EmFinanceReportForLocalityModel lm = new EmFinanceReportForLocalityModel();
							if (localityMap.containsKey(String
									.valueOf(emProductList.get(i).getGid())
									+ String.valueOf(emProductList.get(i)
											.getOwnmonth()))) {

								lm = localityMap.get(String
										.valueOf(emProductList.get(i).getGid())
										+ String.valueOf(emProductList.get(i)
												.getOwnmonth()));
							}

							// 当员工没有社保公积金服务时将数据转到委托出列表
							if (lm.getShebaoModel() == null
									&& lm.getGjjModel() == null) {

								for (EmFinanceProductItemListModel pm : emProductList
										.get(i).getItemList()) {
									// System.out.println(pm.getName());
									boolean b = false;
									if (m2.getProductItemList() != null) {
										for (int j = 0; j < m2
												.getProductItemList().size(); j++) {
											if (m2.getProductItemList().get(j)
													.getItem()
													.equals(pm.getName())) {
												b = true;
												break;
											}
										}
									}
									if (!b) {
										EmFinanceCommissionOutDetailItemModel m = new EmFinanceCommissionOutDetailItemModel();
										m.setItem(pm.getName());
										m2.getProductItemList().add(m);
										// System.out.println(pm.getName());
									}
									EmFinanceCommissionOutDetailItemModel dm = new EmFinanceCommissionOutDetailItemModel();
									dm.setItem(pm.getName());
									dm.setReceivable(pm.getReceivable());
									
									if (m3.getProductItemList().size() > 0) {
										boolean b1 = false;
										for (EmFinanceCommissionOutDetailItemModel ppm : m3
												.getProductItemList()) {

											if (ppm.getItem().equals(
													pm.getName())) {
												b1 = true;
												//if (m3.getGid()==220981 && ppm.getItem().equals("补充医疗服务 方案一")) {
												//System.out.println(ppm.getItem());
												//System.out.println(ppm.getReceivable());
												//}
												ppm.setReceivable(ppm
														.getReceivable()
														.add(pm.getReceivable()));
											}
											//if (m3.getGid()==220981 && ppm.getItem().equals("补充医疗服务 方案一")) {
											//	System.out.println("*****");
											//System.out.println(ppm.getItem());
											//System.out.println(ppm.getReceivable());
											//}
										}
										if (!b1) {
											m3.getProductItemList().add(dm);
										}
									} else {
										m3.getProductItemList().add(dm);
									}

								}

								// 移除本地福利数据
								emProductList.remove(i);
								i = i - 1;
								continue p;

							}
						}
					}
				}

			}

		}

		for (EmFinanceCommissionOutCityModel m2 : emCommissionOutList) {
			if (m2.getProductItemList() != null) {

				if (m2.getProductItemList().size() > 0) {

					for (EmFinanceCommissionOutModel m3 : m2
							.getEmFinanceCommissionOutList()) {

						if (m2.getProductItemList().size() != m3
								.getProductItemList().size()) {

							for (EmFinanceCommissionOutDetailItemModel dm : m2
									.getProductItemList()) {
								boolean bp = false;
								for (int j = 0; j < m3.getProductItemList()
										.size(); j++) {
									if (dm.getItem().equals(
											m3.getProductItemList().get(j)
													.getItem())) {
										bp = true;
									}
								}
								if (!bp) {
									EmFinanceCommissionOutDetailItemModel m = new EmFinanceCommissionOutDetailItemModel();
									m.setReceivable(new BigDecimal(0));
									m3.getProductItemList().add(m);
								}
							}
						}

					}
				}
			}
		}
		for (int i = 0; i < emProductList.size(); i++) {
			if (i < emProductList.size()) {

				if (localityMap.containsKey(String.valueOf(emProductList.get(i)
						.getGid())
						+ String.valueOf(emProductList.get(i).getOwnmonth()))) {

					eflm = localityMap
							.get(String.valueOf(emProductList.get(i).getGid())
									+ String.valueOf(emProductList.get(i)
											.getOwnmonth()));
					eflm.setEmProductModel(emProductList.get(i));
				} else {
					eflm = new EmFinanceReportForLocalityModel();
					eflm.setGid(emProductList.get(i).getGid());
					eflm.setName(emProductList.get(i).getEmba_name());
					eflm.setIdcard(emProductList.get(i).getIdcard());
					eflm.setEmProductModel(emProductList.get(i));
					localityMap.put(
							String.valueOf(emProductList.get(i).getGid())
									+ String.valueOf(emProductList.get(i)
											.getOwnmonth()), eflm);
				}
			}

		}

	}

	// 字体设置
	private WritableCellFormat getFormat() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 9);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.LEFT);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 自动换行
			wc.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 副标题字体设置
	private WritableCellFormat getSubtitleFormat() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 14,
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

	// 表头字体(带入背景色)
	private WritableCellFormat getHeaderFormat1(Colour replacColor,
			String background) {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			if (background != null) {
				Color color = Color.decode(background); // 自定义的颜色
				wb.setColourRGB(replacColor, color.getRed(), color.getGreen(),
						color.getBlue());
				wc.setBackground(replacColor);
			}

			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 自动换行
			wc.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 尾部字体
	private WritableCellFormat getTailFormat(Colour colour) {
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 11,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, colour);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.LEFT);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 字体居中加粗
	private WritableCellFormat getFormatBold() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 18,
				WritableFont.BOLD);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
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
