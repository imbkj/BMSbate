package Controller.EmTax;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.EmTaxInfoModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.CoAgency.WtAgency_DisCitySelBll;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_SelectBll;
import bll.EmTax.EmTax_SelectBll;

public class EmTax_ExcelCreateController {
	private WtAgency_DisCitySelBll cBll = new WtAgency_DisCitySelBll();
	private EmTax_SelectBll sBll = new EmTax_SelectBll();
	private DateStringChange dsc = new DateStringChange();
	private EmCAFFeeInfo_SelectBll ecffsBll = new EmCAFFeeInfo_SelectBll();

	private MonthListUtil mlu = new MonthListUtil();
	private DecimalFormat df = new DecimalFormat("#.00");

	private List<String> citylist; // 个税申报地
	private List<CoBaseModel> cobaList; // 公司列表
	private boolean chk_p = false;// 申报权限

	public EmTax_ExcelCreateController() throws Exception {
		citylist = cBll.getCityName(); // 获取个税申报地
		cobaList = sBll.getCoBase();// 获取公司列表

		if (ecffsBll.getHP(
				" and hape_type='个税' and hape_username='"
						+ UserInfo.getUsername() + "'").size() > 0) {// 判断是否有申报权限
			chk_p = true;
		}
	}

	// 个人明细数据
	@Command("emSubmit")
	public void emSubmit(@BindingParam("win") Window win,
			@BindingParam("gtstate") Combobox gtstate,
			@BindingParam("taxplace") Combobox taxplace,
			@BindingParam("ownmonth") Datebox ownmonth,
			@BindingParam("company") Combobox company,
			@BindingParam("state") Combobox state) throws IOException {
		try {
			String ownmonthString = "";
			String l_month; // 上一月份
			String sql = "";
			String sql2 = "";
			/*
			 * String co_sql1 = ""; String co_sql2 = "";
			 */

			try {
				if (ownmonth.getValue() != null) {
					ownmonthString = dsc.DatetoSting(ownmonth.getValue(),
							"yyyyMM"); // 月份
				}
			} catch (Exception e) {
				ownmonthString = "";
			}

			if (!"".equals(ownmonthString)) {

				l_month = mlu.getLastMonth(ownmonthString);// 获取上一月份

				sql = sql + " AND (a.ownmonth=" + ownmonthString + ")";
				sql2 = sql2 + " AND a.etin_state=0 AND a.ownmonth=" + l_month;

				// co_sql1 = co_sql1 + " AND (h.ownmonth=" + ownmonthString +
				// ")";
				// co_sql2 = co_sql2 + " AND h.ownmonth=" + l_month;

				// 开户状态
				if (gtstate.getSelectedItem() != null
						&& !"".equals(gtstate.getSelectedItem().getValue())) {
					if (!"委托出".equals(gtstate.getSelectedItem().getValue())) {

						if ("中智开户".equals(gtstate.getSelectedItem().getValue())) {// 支付模块的数据也算中智开户
							sql = sql + " AND (f.coco_gs='"
									+ gtstate.getSelectedItem().getValue()
									+ "' or etin_tax_class=6)";
							sql2 = sql2 + " AND (f.coco_gs='"
									+ gtstate.getSelectedItem().getValue()
									+ "' or etin_tax_class=6)";
						} else {
							sql = sql + " AND f.coco_gs='"
									+ gtstate.getSelectedItem().getValue()
									+ "'";
							sql2 = sql2 + " AND f.coco_gs='"
									+ gtstate.getSelectedItem().getValue()
									+ "'";
						}

					} else {
						sql = sql
								+ " AND e.esin_taxplace is not null AND e.esin_taxplace!='深圳'";
						sql2 = sql2
								+ " AND e.esin_taxplace is not null AND e.esin_taxplace!='深圳'";
					}
				}

				// 公司
				if (company.getSelectedItem() != null) {
					sql = sql + " AND a.cid="
							+ company.getSelectedItem().getValue();
					sql2 = sql2 + " AND a.cid="
							+ company.getSelectedItem().getValue();

					/*
					 * co_sql1 = co_sql1 + " AND h.cid=" +
					 * company.getSelectedItem().getValue(); co_sql2 = co_sql2 +
					 * " AND h.cid=" + company.getSelectedItem().getValue();
					 */
				}

				// 个税缴交地
				if (taxplace.getSelectedItem() != null) {
					if (taxplace.getValue().equals("深圳")) {
						sql = sql
								+ " AND (e.esin_taxplace IS NULL OR e.esin_taxplace='' OR e.esin_taxplace='深圳')";
						sql2 = sql2
								+ " AND (e.esin_taxplace IS NULL OR e.esin_taxplace='' OR e.esin_taxplace='深圳')";

						/*
						 * co_sql1 = co_sql1 +
						 * " AND (h.esin_taxplace IS NULL OR h.esin_taxplace='' OR h.esin_taxplace='深圳')"
						 * ; co_sql2 = co_sql2 +
						 * " AND (h.esin_taxplace IS NULL OR h.esin_taxplace='' OR h.esin_taxplace='深圳')"
						 * ;
						 */
					} else {
						sql = sql + " AND e.esin_taxplace='"
								+ taxplace.getSelectedItem().getValue() + "'";
						sql2 = sql2 + " AND e.esin_taxplace='"
								+ taxplace.getSelectedItem().getValue() + "'";

						/*
						 * co_sql1 = co_sql1 + " AND h.esin_taxplace='" +
						 * taxplace.getSelectedItem().getValue() + "'"; co_sql2
						 * = co_sql2 + " AND h.esin_taxplace='" +
						 * taxplace.getSelectedItem().getValue() + "'";
						 */
					}
				}

				// 个税状态
				if (state.getSelectedItem() != null
						&& !"".equals(state.getSelectedItem().getValue())) {
					sql = sql + " AND a.etin_state="
							+ state.getSelectedItem().getValue();

					/*
					 * co_sql1 = co_sql1 + " AND h.etin_state=" +
					 * state.getSelectedItem().getValue();
					 */
				}

				String filename = emTaxOut(sql, sql2, ownmonthString, l_month);
				String path = ""; // 文件路径
				path = "EmTax/File/Download/EmExcel/";
				// 弹出页面
				Map map = new HashMap();
				map.put("filename", filename);
				map.put("path", path);
				Window window = (Window) Executions.createComponents(
						"EmTax_ExcelOut.zul", null, map);
				window.doModal();
			} else {
				// 弹出提示
				Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 个税申报
	@Command("emUpdate")
	public void emUpdate(@BindingParam("win") Window win,
			@BindingParam("gtstate") Combobox gtstate,
			@BindingParam("taxplace") Combobox taxplace,
			@BindingParam("ownmonth") Datebox ownmonth,
			@BindingParam("company") Combobox company,
			@BindingParam("state") Combobox state) throws IOException {
		try {
			if (Messagebox.show("是否确认申报当前条件下的个税数据？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				String ownmonthString = "";
				String l_month; // 上一月份
				String up_sql = "";
				String up_sql2 = "";
				String sql = "";
				String sql2 = "";

				try {
					if (ownmonth.getValue() != null) {
						ownmonthString = dsc.DatetoSting(ownmonth.getValue(),
								"yyyyMM"); // 月份
					}
				} catch (Exception e) {
					ownmonthString = "";
				}

				if (!"".equals(ownmonthString)) {

					l_month = mlu.getLastMonth(ownmonthString);// 获取上一月份

					up_sql = up_sql + " (a.ownmonth=" + ownmonthString + ")";
					up_sql2 = up_sql2 + " (a.ownmonth=" + l_month + ")";

					sql = sql + " AND (a.ownmonth=" + ownmonthString + ")";
					sql2 = sql2 + " AND a.etin_state=0 AND a.ownmonth="
							+ l_month;

					// co_sql1 = co_sql1 + " AND (h.ownmonth=" + ownmonthString
					// +
					// ")";
					// co_sql2 = co_sql2 + " AND h.ownmonth=" + l_month;

					// 开户状态
					if (gtstate.getSelectedItem() != null
							&& !"".equals(gtstate.getSelectedItem().getValue())) {
						if (!"委托出".equals(gtstate.getSelectedItem().getValue())) {
							if ("中智开户".equals(gtstate.getSelectedItem().getValue())) {//支付模块数据都是属于大户
								up_sql = up_sql + " AND (f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "' or etin_tax_class=6)";
								up_sql2 = up_sql2 + " AND (f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "' or etin_tax_class=6)";

								sql = sql + " AND (f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "' or etin_tax_class=6)";
								sql2 = sql2 + " AND (f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "' or etin_tax_class=6)";
							}else {
								up_sql = up_sql + " AND f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "'";
								up_sql2 = up_sql2 + " AND f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "'";

								sql = sql + " AND f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "'";
								sql2 = sql2 + " AND f.coco_gs='"
										+ gtstate.getSelectedItem().getValue()
										+ "'";
							}
							
						} else {
							up_sql = up_sql
									+ " AND b.esin_taxplace is not null AND b.esin_taxplace!='深圳'";
							up_sql2 = up_sql2
									+ " AND b.esin_taxplace is not null AND b.esin_taxplace!='深圳'";

							sql = sql
									+ " AND e.esin_taxplace is not null AND e.esin_taxplace!='深圳'";
							sql2 = sql2
									+ " AND e.esin_taxplace is not null AND e.esin_taxplace!='深圳'";
						}
					}

					// 公司
					if (company.getSelectedItem() != null) {
						up_sql = up_sql + " AND a.cid="
								+ company.getSelectedItem().getValue();
						up_sql2 = up_sql2 + " AND a.cid="
								+ company.getSelectedItem().getValue();

						sql = sql + " AND a.cid="
								+ company.getSelectedItem().getValue();
						sql2 = sql2 + " AND a.cid="
								+ company.getSelectedItem().getValue();
					}

					// 个税缴交地
					if (taxplace.getSelectedItem() != null) {
						if (taxplace.getValue().equals("深圳")) {
							up_sql = up_sql
									+ " AND (b.esin_taxplace IS NULL OR b.esin_taxplace='' OR b.esin_taxplace='深圳')";
							up_sql2 = up_sql2
									+ " AND (b.esin_taxplace IS NULL OR b.esin_taxplace='' OR b.esin_taxplace='深圳')";

							sql = sql
									+ " AND (e.esin_taxplace IS NULL OR e.esin_taxplace='' OR e.esin_taxplace='深圳')";
							sql2 = sql2
									+ " AND (e.esin_taxplace IS NULL OR e.esin_taxplace='' OR e.esin_taxplace='深圳')";
						} else {
							up_sql = up_sql + " AND b.esin_taxplace='"
									+ taxplace.getSelectedItem().getValue()
									+ "'";
							up_sql2 = up_sql2 + " AND b.esin_taxplace='"
									+ taxplace.getSelectedItem().getValue()
									+ "'";

							sql = sql + " AND e.esin_taxplace='"
									+ taxplace.getSelectedItem().getValue()
									+ "'";
							sql2 = sql2 + " AND e.esin_taxplace='"
									+ taxplace.getSelectedItem().getValue()
									+ "'";
						}
					}

					// 生成文件
					String filename = emTaxOut(sql, sql2, ownmonthString,
							l_month);

					// 更新数据
					String[] message = sBll.upEmTax(up_sql, ownmonthString,
							filename);
					sBll.upEmTax(up_sql2, l_month, filename);
					if (message[0].equals("1")) {

						String path = ""; // 文件路径
						path = "EmTax/File/Download/EmExcel/";
						// 弹出页面
						Map map = new HashMap();
						map.put("filename", filename);
						map.put("path", path);
						Window window = (Window) Executions.createComponents(
								"EmTax_ExcelOut.zul", null, map);
						window.doModal();

						// 弹出提示
						Messagebox.show("操作成功！", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}

				} else {
					// 弹出提示
					Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 导出个人明细
	public String emTaxOut(String sql1, String sql2, String ownmonthString,
			String l_month) {
		String filename = "";
		try {
			// 创建excel
			String absolutePath; // 服务器地址
			String path = ""; // 文件路径
			String filetype = ".xls"; // 文件类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);
			int dataRow = 4;

			filename = "em_" + nowtime + filetype;
			path = "EmTax/File/Download/EmExcel/";
			absolutePath = FileOperate.getAbsolutePath();

			// 创建excel
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + path + filename));
			// 生成工作表
			WritableSheet sheet = wwb.createSheet("sheet1", 0);
			Label label = null;
			Number num = null;

			// 生成表头
			label = new Label(0, 0, ownmonthString + "个人明细数据");
			sheet.addCell(label);
			label = new Label(0, 1, "公司编号");
			sheet.addCell(label);
			label = new Label(1, 1, "所属公司");
			sheet.addCell(label);
			label = new Label(2, 1, "税款所属月份");
			sheet.addCell(label);
			label = new Label(3, 1, "个税缴交地");
			sheet.addCell(label);
			label = new Label(4, 1, "姓名");
			sheet.addCell(label);
			label = new Label(5, 1, "委托机构");
			sheet.addCell(label);
			label = new Label(6, 1, "工资劳务费月份");
			sheet.addCell(label);
			label = new Label(7, 1, "税金所属月份");
			sheet.addCell(label);
			label = new Label(8, 1, "证照类型");
			sheet.addCell(label);
			label = new Label(9, 1, "证照号码");
			sheet.addCell(label);
			label = new Label(10, 1, "所得项目");
			sheet.addCell(label);
			label = new Label(11, 1, "应发工资");
			sheet.addCell(label);
			label = new Label(12, 1, "费用扣除标准");
			sheet.addCell(label);
			label = new Label(13, 1, "应纳税所得额");
			sheet.addCell(label);
			label = new Label(14, 1, "税率");
			sheet.addCell(label);
			label = new Label(15, 1, "速算扣除数");
			sheet.addCell(label);
			label = new Label(16, 1, "实际应纳税额");
			sheet.addCell(label);
			label = new Label(17, 1, "是否新增（本月）");
			sheet.addCell(label);
			label = new Label(18, 1, "雇员编号");
			sheet.addCell(label);
			label = new Label(19, 1, "国籍");
			sheet.addCell(label);
			label = new Label(20, 1, "出生年月");
			sheet.addCell(label);
			label = new Label(21, 1, "性别");
			sheet.addCell(label);
			label = new Label(22, 1, "个税开户状态");
			sheet.addCell(label);
			label = new Label(23, 1, "联系电话");
			sheet.addCell(label);
			label = new Label(24, 1, "备注");
			sheet.addCell(label);
			label = new Label(25, 1, "有无选择个税报价单项目");
			sheet.addCell(label);

			// 获取数据
			int k = 2;
			BigDecimal noTax;
			String ifNew;

			List<EmTaxInfoModel> emTaxList;
			emTaxList = sBll.getEmTaxList(sql1, ownmonthString);
			if (emTaxList.size() > 0) {
				for (int i = 0; i < emTaxList.size(); i++) {
					noTax = BigDecimal.ZERO;
					label = new Label(0, k, String.valueOf(emTaxList.get(i)
							.getCid()));// 公司编号
					sheet.addCell(label);
					label = new Label(1, k, emTaxList.get(i).getCompany());// 所属公司
					sheet.addCell(label);
					label = new Label(2, k, ownmonthString); // 税款所属月份
					sheet.addCell(label);
					label = new Label(3, k, emTaxList.get(i).getEsin_taxplace()); // 个税缴交地
					sheet.addCell(label);
					label = new Label(4, k, emTaxList.get(i).getEtin_name()); // 姓名
					sheet.addCell(label);

					label = new Label(5, k, emTaxList.get(i)
							.getCoab_shortname()); // 委托机构
					sheet.addCell(label);
					label = new Label(6, k, String.valueOf(
							emTaxList.get(i).getGz_ownmonth()).substring(0, 4)
							+ "-"
							+ String.valueOf(emTaxList.get(i).getGz_ownmonth())
									.substring(4)); // 工资劳务费月份
					sheet.addCell(label);
					label = new Label(7, k, ownmonthString.substring(0, 4)
							+ "-" + ownmonthString.substring(4)); // 税金所属月份
					sheet.addCell(label);

					label = new Label(8, k, emTaxList.get(i).getIdcardclass()); // 证照类型
					sheet.addCell(label);
					label = new Label(9, k, emTaxList.get(i).getEtin_idcard());// 证照号码
					sheet.addCell(label);
					label = new Label(10, k, emTaxList.get(i).getTax_class());// 所得项目
					sheet.addCell(label);

					if ("中国".equals(emTaxList.get(i).getNationality())
							|| "".equals(emTaxList.get(i).getNationality())
							|| "NULL".equals(emTaxList.get(i).getNationality())
							|| emTaxList.get(i).getNationality() == null) {
						noTax = new BigDecimal("3500");
					} else {
						noTax = new BigDecimal("4800");
					}
					num = new Number(11, k, Double.parseDouble(df
							.format(emTaxList.get(i).getEtin_tax_base()
									.add(noTax))));// 应发工资
					sheet.addCell(num);
					label = new Label(12, k, String.valueOf(noTax));// 费用扣除标准
					sheet.addCell(label);
					num = new Number(13, k, emTaxList.get(i).getEtin_tax_base()
							.doubleValue());// 应纳税所得额
					sheet.addCell(num);
					label = new Label(14, k, sBll.GetTaxS(emTaxList.get(i)
							.getEtin_tax_base(), emTaxList.get(i)
							.getEtin_tax_class()));// 税率
					sheet.addCell(label);
					label = new Label(15, k, sBll.GetTaxM(emTaxList.get(i)
							.getEtin_tax_base(), emTaxList.get(i)
							.getEtin_tax_class()));// 速算扣除数
					sheet.addCell(label);
					num = new Number(16, k, emTaxList.get(i).getEtin_tax()
							.doubleValue());// 实际应纳税额
					sheet.addCell(num);
					if (emTaxList.get(i).getIfNew() == 0) {
						ifNew = "否";
					} else {
						ifNew = "是";
					}
					label = new Label(17, k, ifNew);// 是否新增（本月）
					sheet.addCell(label);
					label = new Label(18, k, String.valueOf(emTaxList.get(i)
							.getGid()));// 雇员编号
					sheet.addCell(label);
					label = new Label(19, k, emTaxList.get(i).getNationality());// 国籍
					sheet.addCell(label);
					label = new Label(20, k, emTaxList.get(i).getBirth());// 出生年月
					sheet.addCell(label);
					label = new Label(21, k, emTaxList.get(i).getSex());// 性别
					sheet.addCell(label);
					label = new Label(22, k, emTaxList.get(i).getGtstate());// 个税开户状态
					sheet.addCell(label);
					
					if (!"".equals(emTaxList.get(i).getMobile())) {
						label = new Label(23, k, emTaxList.get(i)
								.getMobile());// 联系电话
					}else {
						label = new Label(23, k, "0755-83323640");// 联系电话
					}
					
					sheet.addCell(label);
					label = new Label(25, k, emTaxList.get(i).getChk_cgli());// 是否选择个税报价单项目
					sheet.addCell(label);
					k++;
				}
			}

			// 上月未导出数据
			emTaxList = sBll.getEmTaxList(sql2, l_month);
			if (emTaxList.size() > 0) {
				k = k + 3;
				label = new Label(0, k, l_month + "未导出数据");
				sheet.addCell(label);

				k = k + 1;
				for (int i = 0; i < emTaxList.size(); i++) {
					noTax = BigDecimal.ZERO;

					label = new Label(0, k, String.valueOf(emTaxList.get(i)
							.getCid()));// 公司编号
					sheet.addCell(label);
					label = new Label(1, k, emTaxList.get(i).getCompany());// 所属公司
					sheet.addCell(label);
					label = new Label(2, k, l_month); // 税款所属月份
					sheet.addCell(label);
					label = new Label(3, k, emTaxList.get(i).getEsin_taxplace()); // 个税缴交地
					sheet.addCell(label);
					label = new Label(4, k, emTaxList.get(i).getEtin_name()); // 姓名
					sheet.addCell(label);

					label = new Label(5, k, emTaxList.get(i)
							.getCoab_shortname()); // 委托机构
					sheet.addCell(label);
					label = new Label(6, k, String.valueOf(
							emTaxList.get(i).getGz_ownmonth()).substring(0, 4)
							+ "-"
							+ String.valueOf(emTaxList.get(i).getGz_ownmonth())
									.substring(4)); // 工资劳务费月份
					sheet.addCell(label);
					label = new Label(7, k, l_month.substring(0, 4) + "-"
							+ l_month.substring(4)); // 税金所属月份
					sheet.addCell(label);

					label = new Label(8, k, emTaxList.get(i).getIdcardclass()); // 证照类型
					sheet.addCell(label);
					label = new Label(9, k, emTaxList.get(i).getEtin_idcard());// 证照号码
					sheet.addCell(label);
					label = new Label(10, k, emTaxList.get(i).getTax_class());// 所得项目
					sheet.addCell(label);

					if (emTaxList.get(i).getNationality().equals("中国")
							|| "".equals(emTaxList.get(i).getNationality())
							|| emTaxList.get(i).getNationality() == null) {
						noTax = new BigDecimal("3500");
					} else {
						noTax = new BigDecimal("4800");
					}
					num = new Number(11, k, Double.parseDouble(df
							.format(emTaxList.get(i).getEtin_tax_base()
									.add(noTax))));// 应发工资
					sheet.addCell(num);
					label = new Label(12, k, String.valueOf(noTax));// 费用扣除标准
					sheet.addCell(label);
					num = new Number(13, k, emTaxList.get(i).getEtin_tax_base()
							.doubleValue());// 应纳税所得额
					sheet.addCell(num);
					label = new Label(14, k, sBll.GetTaxS(emTaxList.get(i)
							.getEtin_tax_base(), emTaxList.get(i)
							.getEtin_tax_class()));// 税率
					sheet.addCell(label);
					label = new Label(15, k, sBll.GetTaxM(emTaxList.get(i)
							.getEtin_tax_base(), emTaxList.get(i)
							.getEtin_tax_class()));// 速算扣除数
					sheet.addCell(label);
					num = new Number(16, k, emTaxList.get(i).getEtin_tax()
							.doubleValue());// 实际应纳税额
					sheet.addCell(num);
					if (emTaxList.get(i).getIfNew() == 0) {
						ifNew = "否";
					} else {
						ifNew = "是";
					}
					label = new Label(17, k, ifNew);// 是否新增（本月）
					sheet.addCell(label);
					label = new Label(18, k, String.valueOf(emTaxList.get(i)
							.getGid()));// 雇员编号
					sheet.addCell(label);

					label = new Label(19, k, emTaxList.get(i).getNationality());// 国籍
					sheet.addCell(label);
					label = new Label(20, k, emTaxList.get(i).getBirth());// 出生年月
					sheet.addCell(label);
					label = new Label(21, k, emTaxList.get(i).getSex());// 性别
					sheet.addCell(label);
					label = new Label(22, k, emTaxList.get(i).getGtstate());// 个税开户状态
					sheet.addCell(label);

					if (!"".equals(emTaxList.get(i).getMobile())) {
						label = new Label(23, k, emTaxList.get(i)
								.getMobile());// 联系电话
					}else {
						label = new Label(23, k, "0755-83323640");// 联系电话
					}
					sheet.addCell(label);
					label = new Label(25, k, emTaxList.get(i).getChk_cgli());// 是否选择个税报价单项目
					sheet.addCell(label);
					k++;
				}
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return filename;
	}

	// 公司汇总数据
	public String coTaxOut(String sql1, String sql2, String ownmonthString,
			String l_month) {

		String filename = "";
		try {
			String absolutePath; // 服务器地址
			String path = ""; // 文件路径
			String filetype = ".xls"; // 文件类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);
			int dataRow = 4;

			filename = "co_" + nowtime + filetype;
			path = "EmTax/File/Download/CoExcel/";
			absolutePath = FileOperate.getAbsolutePath();

			// 创建excel
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + path + filename));
			// 生成工作表
			WritableSheet sheet = wwb.createSheet("sheet1", 0);
			Label label = null;
			Number num = null;

			// 生成表头
			label = new Label(0, 0, ownmonthString + "月份个税汇总");
			sheet.addCell(label);
			label = new Label(0, 1, "税款所属月份");
			sheet.addCell(label);
			label = new Label(1, 1, "个税缴交地");
			sheet.addCell(label);
			label = new Label(2, 1, "委托机构");
			sheet.addCell(label);
			label = new Label(3, 1, "公司编号");
			sheet.addCell(label);
			label = new Label(4, 1, "公司名称");
			sheet.addCell(label);
			label = new Label(5, 1, "员工总数");
			sheet.addCell(label);
			label = new Label(6, 1, "个人所得税汇总");
			sheet.addCell(label);
			label = new Label(7, 1, "年终奖金税汇总");
			sheet.addCell(label);
			label = new Label(8, 1, "股票税汇总");
			sheet.addCell(label);
			label = new Label(9, 1, "离职补偿金税汇总");
			sheet.addCell(label);
			label = new Label(10, 1, "劳务报酬个人所得税汇总");
			sheet.addCell(label);
			label = new Label(11, 1, "个税实际收款");
			sheet.addCell(label);
			label = new Label(12, 1, "相差对比");
			sheet.addCell(label);
			label = new Label(13, 1, "所有税金汇总");
			sheet.addCell(label);

			// 获取数据
			int k = 2;
			BigDecimal allTax = new BigDecimal("0");

			List<EmTaxInfoModel> emTaxList;
			emTaxList = sBll.getCoTaxList(sql1, 0, ownmonthString);
			if (emTaxList.size() > 0) {
				for (int i = 0; i < emTaxList.size(); i++) {

					label = new Label(0, k, String.valueOf(emTaxList.get(i)
							.getOwnmonth())); // 所属月份
					sheet.addCell(label);
					label = new Label(1, k, emTaxList.get(i).getEsin_taxplace()); // 个税缴交地
					sheet.addCell(label);
					label = new Label(2, k, emTaxList.get(i)
							.getCoab_shortname()); // 委托机构
					sheet.addCell(label);
					label = new Label(3, k, String.valueOf(emTaxList.get(i)
							.getCid())); // 公司编号
					sheet.addCell(label);
					label = new Label(4, k, emTaxList.get(i).getCompany()); // 公司名称
					sheet.addCell(label);
					label = new Label(5, k, String.valueOf(emTaxList.get(i)
							.getS_cou())); // 员工总数
					sheet.addCell(label);
					num = new Number(6, k, emTaxList.get(i).getEtin_tax1()
							.doubleValue());// 个人所得税汇总
					sheet.addCell(num);
					num = new Number(7, k, emTaxList.get(i).getEtin_tax2()
							.doubleValue());// 年终奖金税汇总
					sheet.addCell(num);
					num = new Number(8, k, emTaxList.get(i).getEtin_tax3()
							.doubleValue());// 股票税汇总
					sheet.addCell(num);
					num = new Number(9, k, emTaxList.get(i).getEtin_tax4()
							.doubleValue());// 离职补偿金税汇总
					sheet.addCell(num);
					num = new Number(10, k, emTaxList.get(i).getEtin_tax5()
							.doubleValue());// 劳务报酬个人所得税汇总
					sheet.addCell(num);
					num = new Number(11, k, emTaxList.get(i).getCoga_t6()
							.doubleValue());// 个税实际收款
					sheet.addCell(num);
					num = new Number(12, k, emTaxList.get(i).getBalance()
							.doubleValue());// 相差对比
					sheet.addCell(num);

					// 将cid相同的所有税款相加
					if (i == emTaxList.size() - 1) {// 判断是否为最后一条数据，如果是，直接插入总税款

						if (i != 0) {
							// 相加所有税款(上一条记录)
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax1());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax2());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax3());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax4());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax5());

							if (emTaxList.get(i).getCid() != emTaxList.get(
									i - 1).getCid()) {
								num = new Number(13, k - 1,
										allTax.doubleValue());// 所有税金汇总
								sheet.addCell(num);

								// 清空税款总额
								allTax = new BigDecimal("0");
							}
						}

						// 相加所有税款
						allTax = allTax.add(emTaxList.get(i).getEtin_tax1());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax2());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax3());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax4());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax5());

						num = new Number(13, k, allTax.doubleValue());// 所有税金汇总
						sheet.addCell(num);
						// 清空税款总额
						allTax = new BigDecimal("0");
					} else {
						// 如果不是最后一条数据，则判断公司编号是否与上一条一致，如果不是，则在上一行单元格插入税款总额
						if (i > 0) {// 第一条数据不用判断

							// 相加所有税款(上一条记录)
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax1());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax2());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax3());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax4());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax5());

							if (emTaxList.get(i).getCid() != emTaxList.get(
									i - 1).getCid()) {
								num = new Number(13, k - 1,
										allTax.doubleValue());// 所有税金汇总
								sheet.addCell(num);

								// 清空税款总额
								allTax = new BigDecimal("0");
							}
						}
					}

					k++;
				}
			}

			allTax = new BigDecimal("0");
			// 上月未导出数据
			emTaxList = sBll.getCoTaxList(sql2, 1, l_month);
			if (emTaxList.size() > 0) {
				k = k + 3;
				label = new Label(0, k, l_month + "未导出数据");
				sheet.addCell(label);

				k = k + 1;
				for (int i = 0; i < emTaxList.size(); i++) {

					label = new Label(0, k, String.valueOf(emTaxList.get(i)
							.getOwnmonth())); // 所属月份
					sheet.addCell(label);
					label = new Label(1, k, emTaxList.get(i).getEsin_taxplace()); // 个税缴交地
					sheet.addCell(label);
					label = new Label(2, k, emTaxList.get(i)
							.getCoab_shortname()); // 委托机构
					sheet.addCell(label);
					label = new Label(3, k, String.valueOf(emTaxList.get(i)
							.getCid())); // 公司编号
					sheet.addCell(label);
					label = new Label(4, k, emTaxList.get(i).getCompany()); // 公司名称
					sheet.addCell(label);
					label = new Label(5, k, String.valueOf(emTaxList.get(i)
							.getS_cou())); // 员工总数
					sheet.addCell(label);
					num = new Number(6, k, emTaxList.get(i).getEtin_tax1()
							.doubleValue());// 个人所得税汇总
					sheet.addCell(num);
					num = new Number(7, k, emTaxList.get(i).getEtin_tax2()
							.doubleValue());// 年终奖金税汇总
					sheet.addCell(num);
					num = new Number(8, k, emTaxList.get(i).getEtin_tax3()
							.doubleValue());// 股票税汇总
					sheet.addCell(num);
					num = new Number(9, k, emTaxList.get(i).getEtin_tax4()
							.doubleValue());// 离职补偿金税汇总
					sheet.addCell(num);
					num = new Number(10, k, emTaxList.get(i).getEtin_tax5()
							.doubleValue());// 劳务报酬个人所得税汇总
					sheet.addCell(num);
					num = new Number(11, k, emTaxList.get(i).getCoga_t6()
							.doubleValue());// 个税实际收款
					sheet.addCell(num);
					num = new Number(12, k, emTaxList.get(i).getBalance()
							.doubleValue());// 相差对比
					sheet.addCell(num);

					// 将cid相同的所有税款相加
					if (i == emTaxList.size() - 1) {// 判断是否为最后一条数据，如果是，直接插入总税款

						if (i != 0) {
							// 相加所有税款(上一条记录)
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax1());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax2());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax3());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax4());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax5());

							if (emTaxList.get(i).getCid() != emTaxList.get(
									i - 1).getCid()) {
								num = new Number(13, k - 1,
										allTax.doubleValue());// 所有税金汇总
								sheet.addCell(num);

								// 清空税款总额
								allTax = new BigDecimal("0");
							}
						}

						// 相加所有税款
						allTax = allTax.add(emTaxList.get(i).getEtin_tax1());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax2());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax3());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax4());
						allTax = allTax.add(emTaxList.get(i).getEtin_tax5());

						num = new Number(13, k, allTax.doubleValue());// 所有税金汇总
						sheet.addCell(num);
						// 清空税款总额
						allTax = new BigDecimal("0");
					} else {
						// 如果不是最后一条数据，则判断公司编号是否与上一条一致，如果不是，则在上一行单元格插入税款总额
						if (i > 0) {// 第一条数据不用判断

							// 相加所有税款(上一条记录)
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax1());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax2());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax3());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax4());
							allTax = allTax.add(emTaxList.get(i - 1)
									.getEtin_tax5());

							if (emTaxList.get(i).getCid() != emTaxList.get(
									i - 1).getCid()) {
								num = new Number(13, k - 1,
										allTax.doubleValue());// 所有税金汇总
								sheet.addCell(num);

								// 清空税款总额
								allTax = new BigDecimal("0");
							}
						}
					}

					k++;
				}
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return filename;
	}

	// 公司汇总数据
	@Command("coSubmit")
	public void coSubmit(@BindingParam("win") Window win,
			@BindingParam("gtstate") Combobox gtstate,
			@BindingParam("taxplace") Combobox taxplace,
			@BindingParam("ownmonth") Datebox ownmonth,
			@BindingParam("company") Combobox company,
			@BindingParam("state") Combobox state) {

		try {
			String ownmonthString = "";
			String l_month; // 上一月份
			String sql = "";
			String sql2 = "";
			String co_sql1 = "";
			String co_sql2 = "";

			try {
				if (ownmonth.getValue() != null) {
					ownmonthString = dsc.DatetoSting(ownmonth.getValue(),
							"yyyyMM"); // 月份
				}
			} catch (Exception e) {
				ownmonthString = "";
			}

			if (!"".equals(ownmonthString)) {

				l_month = mlu.getLastMonth(ownmonthString);// 获取上一月份

				/*
				 * sql = sql + " AND (a.ownmonth=" + ownmonthString + ")"; sql2
				 * = sql2 + " AND a.etin_state=0 AND a.ownmonth=" + l_month;
				 */
				co_sql1 = co_sql1 + " AND (h.ownmonth=" + ownmonthString + ")";
				co_sql2 = co_sql2 + " AND h.etin_state=0 AND h.ownmonth="
						+ l_month;

				// 开户状态
				if (gtstate.getSelectedItem() != null
						&& !"".equals(gtstate.getSelectedItem().getValue())) {
					if (!"委托出".equals(gtstate.getSelectedItem().getValue())) {
						co_sql1 = co_sql1 + " AND h.coco_gs='"
								+ gtstate.getSelectedItem().getValue() + "'";
						co_sql2 = co_sql2 + " AND h.coco_gs='"
								+ gtstate.getSelectedItem().getValue() + "'";
					} else {
						co_sql1 = co_sql1
								+ " AND h.esin_taxplace is not null AND h.esin_taxplace!='深圳'";
						co_sql2 = co_sql2
								+ " AND h.esin_taxplace is not null AND h.esin_taxplace!='深圳'";
					}
				}

				// 公司
				if (company.getSelectedItem() != null) {
					co_sql1 = co_sql1 + " AND h.cid="
							+ company.getSelectedItem().getValue();
					co_sql2 = co_sql2 + " AND h.cid="
							+ company.getSelectedItem().getValue();
				}

				// 个税缴交地
				if (taxplace.getSelectedItem() != null) {
					if (taxplace.getValue().equals("深圳")) {
						co_sql1 = co_sql1
								+ " AND (h.esin_taxplace IS NULL OR h.esin_taxplace='' OR h.esin_taxplace='深圳')";
						co_sql2 = co_sql2
								+ " AND (h.esin_taxplace IS NULL OR h.esin_taxplace='' OR h.esin_taxplace='深圳')";
					} else {
						co_sql1 = co_sql1 + " AND h.esin_taxplace='"
								+ taxplace.getSelectedItem().getValue() + "'";
						co_sql2 = co_sql2 + " AND h.esin_taxplace='"
								+ taxplace.getSelectedItem().getValue() + "'";
					}
				}

				// 个税状态
				if (state.getSelectedItem() != null
						&& !"".equals(state.getSelectedItem().getValue())) {
					co_sql1 = co_sql1 + " AND h.etin_state="
							+ state.getSelectedItem().getValue();
				}

				String filename = "";
				String path = "EmTax/File/Download/CoExcel/";
				filename = coTaxOut(co_sql1, co_sql2, ownmonthString, l_month);

				// 弹出页面
				Map map = new HashMap();
				map.put("filename", filename);
				map.put("path", path);
				Window window = (Window) Executions.createComponents(
						"EmTax_ExcelOut.zul", null, map);
				window.doModal();
			} else {
				// 弹出提示
				Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public List<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String> citylist) {
		this.citylist = citylist;
	}

	public List<CoBaseModel> getCobaList() {
		return cobaList;
	}

	public void setCobaList(List<CoBaseModel> cobaList) {
		this.cobaList = cobaList;
	}

	public boolean isChk_p() {
		return chk_p;
	}

	public void setChk_p(boolean chk_p) {
		this.chk_p = chk_p;
	}

}
