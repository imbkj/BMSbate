package Controller.EmSalary;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_HisSelBll;

import Model.EmFinanceReportForLocalityModel;
import Model.EmPayModel;
import Model.EmSalaryHistoryDataModel;
import Model.EmTXTFileInfoModel;
import Util.DateStringChange;
import Util.ExcelStyleUtil;
import Util.FileOperate;
import Util.UserInfo;

public class EmSalary_HistoryListController {
	private String icSrc;
	private Include icBase;

	public EmSalary_HistoryListController() {

	}

	@Command("search")
	public void search(@BindingParam("db") Datebox db,
			@BindingParam("cb") Combobox cb) {
		try {
			if (db.getValue() != null) {
				int batch = 0;
				if ("第一批".equals(cb.getValue())) {
					batch = 1;
				} else if ("第二批".equals(cb.getValue())) {
					batch = 2;
				}

				icSrc = "../EmSalary/EmSalary_HistoryList_Base.zul?date="
						+ DateStringChange.DatetoSting(db.getValue(),
								"yyyy-MM-dd") + "&batch=" + batch;
				BindUtils.postNotifyChange(null, null, this, "icSrc");
				icBase.invalidate();
			} else {
				db.focus();
				Messagebox.show("请选择生成历史记录日期。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取include组件
	@Command("setInclude")
	public void setInclude(@BindingParam("com") Include com) {
		icBase = com;
	}

	// 导出报表
	@Command("excel")
	public void excel(@BindingParam("db") Datebox db,
			@BindingParam("cb") Combobox cb) {
		try {
			// 检查必选项
			if (db.getValue() != null) {
				// 获取查询条件
				String h_date = DateStringChange.DatetoSting(db.getValue(),
						"yyyy-MM-dd");
				int batch = 0;
				if ("第一批".equals(cb.getValue())) {
					batch = 1;
				} else if ("第二批".equals(cb.getValue())) {
					batch = 2;
				}

				Map<Integer, EmSalaryHistoryDataModel> map;
				EmSalary_HisSelBll bll;
				int sumData = 0;
				BigDecimal totalEx = BigDecimal.ZERO;

				List<EmPayModel> empaList;
				int empa_count = 0;
				BigDecimal empa_total = BigDecimal.ZERO;

				// 历史记录数据
				bll = new EmSalary_HisSelBll();
				map = bll.getHisData(h_date, batch);
				if (map.size() > 0) {

					Object s[] = map.keySet().toArray();
					for (int i = 0; i < map.size(); i++) {
						sumData += map.get(s[i]).getEsdaListSize();
						totalEx = totalEx.add(map.get(s[i]).getSumModel()
								.getEsda_pay());
					}

				}
				// 支付模块数据
				empaList = bll.getEmPayList(h_date);
				if (empaList.size() > 0) {
					empa_count = empaList.size();
				}
				empa_total = bll.getEmPayTotal(h_date).getEmpa_fee();

				String filename = ""; // 文件名
				String absolutePath; // 服务器地址
				String filetype = ".xls"; // 文件类型
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				int dataCell = 0;
				int dataRow = 1;
				int dataRow2 = 0;

				filename = nowtime + filetype;
				absolutePath = FileOperate.getAbsolutePath();
				// 创建excel
				WritableWorkbook wwb = Workbook.createWorkbook(new File(
						absolutePath + "EmSalary/File/Download/History/"
								+ filename));
				// 生成工作表
				WritableSheet sheet = wwb.createSheet("sheet1", 0);
				Label label = null;
				Number num = null;

				// 设置字体格式
				ExcelStyleUtil esU = new ExcelStyleUtil();
				WritableCellFormat wcf = esU.getFormatBold();
				WritableCellFormat wcf2 = esU.getFormatBorderLine();

				label = new Label(0, 0, "共"
						+ String.valueOf(sumData + empa_count) + "条记录，总金额"
						+ String.valueOf(totalEx.add(empa_total)) + "元");
				sheet.addCell(label);
				int k = 1;
				if (map.size() > 0) {
					Collection<EmSalaryHistoryDataModel> collection = map
							.values();

					for (EmSalaryHistoryDataModel m : collection) {// 循环map
						// 生成表头
						label = new Label(0, k, "公司编号：" + m.getCid()
								+ "    公司简称：" + m.getCoba_shortname()
								+ "    所属月份：" + m.getOwnmonth() + "    "
								+ m.getEsdaListSize() + "条记录，总发放金额"
								+ m.getSumModel().getEsda_pay() + "元");
						sheet.addCell(label);
						k = k + 1;
						label = new Label(0, k, "员工姓名", wcf);
						sheet.addCell(label);
						label = new Label(1, k, "员工编号", wcf);
						sheet.addCell(label);
						label = new Label(2, k, "用途", wcf);
						sheet.addCell(label);
						label = new Label(3, k, "财务备注", wcf);
						sheet.addCell(label);
						label = new Label(4, k, "税前工资", wcf);
						sheet.addCell(label);
						label = new Label(5, k, "社保个人部分", wcf);
						sheet.addCell(label);
						label = new Label(6, k, "住房公积金个人部分", wcf);
						sheet.addCell(label);
						label = new Label(7, k, "应纳税工资", wcf);
						sheet.addCell(label);
						label = new Label(8, k, "劳务报酬", wcf);
						sheet.addCell(label);
						label = new Label(9, k, "劳务报酬个人所得税", wcf);
						sheet.addCell(label);
						label = new Label(10, k, "个人所得税", wcf);
						sheet.addCell(label);
						label = new Label(11, k, "年终奖金", wcf);
						sheet.addCell(label);
						label = new Label(12, k, "年终奖金个税", wcf);
						sheet.addCell(label);
						label = new Label(13, k, "离职补偿金", wcf);
						sheet.addCell(label);
						label = new Label(14, k, "离职补偿金个税", wcf);
						sheet.addCell(label);
						label = new Label(15, k, "股票收入", wcf);
						sheet.addCell(label);
						label = new Label(16, k, "股票税", wcf);
						sheet.addCell(label);
						label = new Label(17, k, "报销费用", wcf);
						sheet.addCell(label);
						label = new Label(18, k, "住房返还", wcf);
						sheet.addCell(label);
						label = new Label(19, k, "实发工资", wcf);
						sheet.addCell(label);

						for (int j = 0; j < m.getEsdaListSize(); j++) {// 循环map
							k = k + 1;
							// 插入excel
							label = new Label(0, k, m.getEsdaList().get(j)
									.getName(), wcf2);
							sheet.addCell(label);
							label = new Label(1, k, String.valueOf(m
									.getEsdaList().get(j).getGid()), wcf2);
							sheet.addCell(label);

							String usage_str = m.getEsdaList().get(j)
									.getEsda_usage_typestr();
							if (m.getEsdaList().get(j).getEsda_rp_reason() != 0) {
								usage_str=usage_str+" - 重发";
							}
							label = new Label(2, k, usage_str, wcf2);

							sheet.addCell(label);
							label = new Label(3, k, m.getEsdaList().get(j)
									.getEsda_fd_remark(), wcf2);
							sheet.addCell(label);
							num = new Number(4, k, m.getEsdaList().get(j)
									.getEsda_total_pretax().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(5, k, m.getEsdaList().get(j)
									.getEsda_siop().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(6, k, m.getEsdaList().get(j)
									.getEsda_hafop().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(7, k, m.getEsdaList().get(j)
									.getEsda_tax_base().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(8, k, m.getEsdaList().get(j)
									.getEsda_lw_tax_base().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(9, k, m.getEsdaList().get(j)
									.getEsda_lw_tax().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(10, k, m.getEsdaList().get(j)
									.getEsda_tax().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(11, k, m.getEsdaList().get(j)
									.getEsda_db().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(12, k, m.getEsdaList().get(j)
									.getEsda_db_tax().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(13, k, m.getEsdaList().get(j)
									.getEsda_dc().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(14, k, m.getEsdaList().get(j)
									.getEsda_dc_tax().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(15, k, m.getEsdaList().get(j)
									.getEsda_stock().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(16, k, m.getEsdaList().get(j)
									.getEsda_stock_tax().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(17, k, m.getEsdaList().get(j)
									.getEsda_write_off().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(18, k, m.getEsdaList().get(j)
									.getEsda_house_bf().doubleValue(), wcf2);
							sheet.addCell(num);
							num = new Number(19, k, m.getEsdaList().get(j)
									.getEsda_pay().doubleValue(), wcf2);
							sheet.addCell(num);
						}
						k = k + 1;
						// 合计金额
						label = new Label(0, k, "金额合计", wcf2);
						sheet.addCell(label);
						label = new Label(1, k, "共" + m.getEsdaListSize()
								+ "条记录", wcf2);
						sheet.addCell(label);
						num = new Number(4, k, m.getSumModel()
								.getEsda_total_pretax().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(5, k, m.getSumModel().getEsda_siop()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(6, k, m.getSumModel().getEsda_hafop()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(7, k, m.getSumModel()
								.getEsda_tax_base().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(8, k, m.getSumModel()
								.getEsda_lw_tax_base().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(9, k, m.getSumModel().getEsda_lw_tax()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(10, k, m.getSumModel().getEsda_tax()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(11, k, m.getSumModel().getEsda_db()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(12, k, m.getSumModel()
								.getEsda_db_tax().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(13, k, m.getSumModel().getEsda_dc()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(14, k, m.getSumModel()
								.getEsda_dc_tax().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(15, k, m.getSumModel().getEsda_stock()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(16, k, m.getSumModel()
								.getEsda_stock_tax().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(17, k, m.getSumModel()
								.getEsda_write_off().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(18, k, m.getSumModel()
								.getEsda_house_bf().doubleValue(), wcf2);
						sheet.addCell(num);
						num = new Number(19, k, m.getSumModel().getEsda_pay()
								.doubleValue(), wcf2);
						sheet.addCell(num);

						// 财务备注 和 个税扣缴方式
						k = k + 1;
						label = new Label(0, k, "财务备注", wcf2);
						sheet.addCell(label);
						label = new Label(1, k, m.getSumModel()
								.getEsda_fd_remark(), wcf2);
						sheet.addCell(label);

						// 客服备注
						k = k + 1;
						label = new Label(0, k, "客服备注", wcf2);
						sheet.addCell(label);
						label = new Label(1, k, m.getSumModel()
								.getEsda_remark(), wcf2);
						sheet.addCell(label);

						k = k + 2;
					}

				}

				// 支付模块数据
				if (empaList.size() > 0) {
					k = k + 2;
					label = new Label(0, k, "通过支付模块发放的数据  共" + empa_count
							+ "条记录，总发放金额" + empa_total); // 流水序号
					sheet.addCell(label);

					k = k + 1; // 表头
					label = new Label(0, k, "公司编号", wcf);
					sheet.addCell(label);
					label = new Label(1, k, "公司简称", wcf);
					sheet.addCell(label);
					label = new Label(2, k, "所属月份", wcf);
					sheet.addCell(label);
					label = new Label(3, k, "员工编号", wcf);
					sheet.addCell(label);
					label = new Label(4, k, "员工姓名", wcf);
					sheet.addCell(label);
					label = new Label(5, k, "款项类别", wcf);
					sheet.addCell(label);
					label = new Label(6, k, "状态", wcf);
					sheet.addCell(label);
					label = new Label(7, k, "金额", wcf);
					sheet.addCell(label);
					label = new Label(8, k, "备注", wcf);
					sheet.addCell(label);

					for (int i = 0; i < empaList.size(); i++) {
						k = k + 1;
						label = new Label(0, k, String.valueOf(empaList.get(i)
								.getCid()), wcf2);
						sheet.addCell(label);
						label = new Label(1, k, empaList.get(i)
								.getCoba_shortname(), wcf2);
						sheet.addCell(label);
						label = new Label(2, k, String.valueOf(empaList.get(i)
								.getOwnmonth()), wcf2);
						sheet.addCell(label);
						label = new Label(3, k, String.valueOf(empaList.get(i)
								.getGid()), wcf2);
						sheet.addCell(label);
						label = new Label(4, k, empaList.get(i)
								.getEmpa_ba_name(), wcf2);
						sheet.addCell(label);
						label = new Label(5, k,
								empaList.get(i).getEmpa_class(), wcf2);
						sheet.addCell(label);
						if (empaList.get(i).getEmpa_ifpay() == 0) {
							label = new Label(6, k, "未发", wcf2);
						} else if (empaList.get(i).getEmpa_ifpay() == 1) {
							label = new Label(6, k, "已发", wcf2);
						}
						sheet.addCell(label);
						num = new Number(7, k, empaList.get(i).getEmpa_fee()
								.doubleValue(), wcf2);
						sheet.addCell(num);
						label = new Label(8, k, empaList.get(i).getRemark(),
								wcf2);
						sheet.addCell(label);
					}
				}
				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();
				String filePath = "EmSalary/File/Download/History/" + filename;
				FileOperate.download(filePath);
			} else {

				// 弹出提示
				Messagebox.show("请选择“生成历史记录日期”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 导出用友
	@Command("ufOut")
	public void ufOut(@BindingParam("db") Datebox db) {
		// 检查必选项
		if (db.getValue() != null) {

			Map map = new HashMap();
			map.put("historyDate",
					DateStringChange.DatetoSting(db.getValue(), "yyyy-MM-dd"));
			Window window = (Window) Executions.createComponents(
					"EmSalary_HistoryUFtxt.zul", null, map);
			window.doModal();

		} else {

			// 弹出提示
			Messagebox.show("请选择“生成历史记录日期”！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public String getIcSrc() {
		return icSrc;
	}

}
