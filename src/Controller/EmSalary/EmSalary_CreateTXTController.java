package Controller.EmSalary;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmPayModel;
import Model.EmTXTFileInfoModel;
import Model.EmTXTFileSetModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmSalary.EmSalary_HisSelBll;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_CreateTXTController {
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private EmSalary_HisSelBll hisSbll = new EmSalary_HisSelBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int total_count = 0;
	private BigDecimal total_pay = new BigDecimal(0);
	private int total_ep_count = 0;
	private BigDecimal total_empay = new BigDecimal(0);
	private int all_total_count = 0;
	private BigDecimal all_total_pay = new BigDecimal(0);

	private String etfi_txt_date = Executions.getCurrent().getArg()
			.get("txt_date").toString();

	private List<EmPayModel> empaList;

	private EmTXTFileSetModel etfsM = new EmTXTFileSetModel();

	public EmSalary_CreateTXTController() {
		String[] pay = ifSBll.getTXTCountPay(etfi_txt_date);
		total_pay = new BigDecimal(pay[0])
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		total_count = Integer.parseInt(pay[1]);

		// 支付模块数据
		empaList = hisSbll.getEmPayList(etfi_txt_date);
		if (empaList.size() > 0) {
			total_ep_count = empaList.size();
		}
		total_empay = hisSbll.getEmPayTotal(etfi_txt_date).getEmpa_fee();

		all_total_count = total_count + total_ep_count;
		all_total_pay = total_pay.add(total_empay).setScale(2,
				BigDecimal.ROUND_HALF_UP);

		etfsM = ifSBll.getTXTBalance();
	}

	@Command("submit")
	public void submit(@BindingParam("winTXTCreate") final Window winTXTCreate,
			@BindingParam("txt_batch") Combobox txt_batch)
			throws BiffException, IOException {
		try {

			// 检查必选项
			if (txt_batch.getSelectedItem() != null) {

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
				// 获取txt数据
				List<EmTXTFileInfoModel> etfiList = ifSBll
						.getTXTEmPayDataList(etfi_txt_date);
				//if (etfiList.size() > 0 || empaList.size() > 0) {
				if (etfiList.size() > 0) {
					// 创建excel
					// 读取Excel模板
					Workbook wb = Workbook.getWorkbook(new File(absolutePath
							+ "EmSalary/File/Templet/esda_templet.xls"));
					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(new File(
							absolutePath + "EmSalary/File/Download/TXTData/"
									+ filename), wb);
					// 生成工作表
					WritableSheet sheet = wwb.getSheet(0);
					Label label = null;
					Number num = null;

					int j = 0;// 流水号
					String remit_ba = "821104266908091001";// 付方账号
					String remit_company = "深圳中智经济技术合作有限公司"; // 付方名称
					/*if (etfiList.size() > 0) {
						remit_ba = etfiList.get(0).getEtfi_remit_ba();// 付方账号
						remit_company = etfiList.get(0).getEtfi_remit_company(); // 付方名称
					}*/

					for (int i = 0; i < etfiList.size(); i++) {
						// 生成历史记录
						String[] message;
						if (etfiList.get(i).getEsda_id()>0) {
							message = ifOBll.addHistoryData(etfiList
								.get(i).getEsda_id(), txt_batch
								.getSelectedItem().getValue().toString(),
								username);
						}else {
							message = new String[2];
							message[0]="0";
						}
						
						// 插入excel
						if (message[0].equals("0")) {
							j = j + 1;
							label = new Label(0, i + dataRow, String.valueOf(j)); // 流水序号
							sheet.addCell(label);
							label = new Label(1, i + dataRow, remit_ba); // 付方账号
							sheet.addCell(label);
							label = new Label(2, i + dataRow, remit_company); // 付方名称
							sheet.addCell(label);
							label = new Label(3, i + dataRow, etfiList.get(i)
									.getEtfi_bank_account()); // 收方账号
							sheet.addCell(label);
							label = new Label(4, i + dataRow, etfiList.get(i)
									.getEtfi_ba_name()); // 收方名称
							sheet.addCell(label);
							num = new Number(5, i + dataRow, etfiList.get(i)
									.getEtfi_pay().doubleValue()); // 金额
							sheet.addCell(num);
							label = new Label(6, i + dataRow, etfiList.get(i)
									.getEtfi_bank()); // 收款行名称
							sheet.addCell(label);
							label = new Label(7, i + dataRow, etfiList.get(i)
									.getUsage_type()); // 款项用途
							sheet.addCell(label);

						}
					}
					/*dataRow2 = etfiList.size() + 1;
					// 支付模块数据
					for (int i = 0; i < empaList.size(); i++) {
						j = j + 1;
						label = new Label(0, i + dataRow2, String.valueOf(j)); // 流水序号
						sheet.addCell(label);
						label = new Label(1, i + dataRow2, remit_ba); // 付方账号
						sheet.addCell(label);
						label = new Label(2, i + dataRow2, remit_company); // 付方名称
						sheet.addCell(label);
						label = new Label(3, i + dataRow2, empaList.get(i)
								.getEmpa_account()); // 收方账号
						sheet.addCell(label);
						label = new Label(4, i + dataRow2, empaList.get(i)
								.getEmpa_ba_name()); // 收方名称
						sheet.addCell(label);
						num = new Number(5, i + dataRow2, empaList.get(i)
								.getEmpa_fee().doubleValue()); // 金额
						sheet.addCell(num);
						label = new Label(6, i + dataRow2, empaList.get(i)
								.getEmpa_bank()); // 收款行名称
						sheet.addCell(label);
						label = new Label(7, i + dataRow2, "报销"); // 款项用途
						sheet.addCell(label);
					}*/
					// 更新支付模块数据
					hisSbll.upEmPay(etfi_txt_date);

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();
					// 更新txt状态
					String[] message = ifOBll.updateTXTState(etfi_txt_date,
							txt_batch.getSelectedItem().getValue().toString(),
							username);

					// 弹出提示并跳转页面
					if (message[0].equals("0")) {
						/*
						 * // 跳转页面 winTXTCreate.getDesktop().getExecution()
						 * .sendRedirect("EmSalary_ExcelOut.zul?filename=" +
						 * filename);
						 */

						// 更新银行余额剩余金额
						etfsM.setEtfs_remaining(etfsM.getEtfs_balance()
								.subtract(total_pay)
								.setScale(2, BigDecimal.ROUND_HALF_UP));
						ifOBll.updateTXTremaining(etfsM);

						// 关闭本窗口
						winTXTCreate.detach();

						Map map = new HashMap();
						map.put("filename", filename);
						Window window = (Window) Executions.createComponents(
								"EmSalary_ExcelOut.zul", null, map);
						window.doModal();

					}

				}
			} else {
				if (txt_batch.getSelectedItem() == null) {
					// 弹出提示
					Messagebox.show("请选择“发放批次”！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public BigDecimal getTotal_pay() {
		return total_pay;
	}

	public void setTotal_pay(BigDecimal total_pay) {
		this.total_pay = total_pay;
	}

	public int getTotal_ep_count() {
		return total_ep_count;
	}

	public void setTotal_ep_count(int total_ep_count) {
		this.total_ep_count = total_ep_count;
	}

	public BigDecimal getTotal_empay() {
		return total_empay;
	}

	public void setTotal_empay(BigDecimal total_empay) {
		this.total_empay = total_empay;
	}

	public int getAll_total_count() {
		return all_total_count;
	}

	public void setAll_total_count(int all_total_count) {
		this.all_total_count = all_total_count;
	}

	public BigDecimal getAll_total_pay() {
		return all_total_pay;
	}

	public void setAll_total_pay(BigDecimal all_total_pay) {
		this.all_total_pay = all_total_pay;
	}

	public EmTXTFileSetModel getEtfsM() {
		return etfsM;
	}

	public void setEtfsM(EmTXTFileSetModel etfsM) {
		this.etfsM = etfsM;
	}

}
