package Controller.EmSalary;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import Model.EmPayModel;
import Model.EmSalaryDataModel;
import Util.DateStringChange;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmSalary.EmSalary_HisSelBll;

public class EmSalary_HistoryUFtxtController {
	private String historyDate = (String) Executions.getCurrent().getArg()
			.get("historyDate");
	private String number = "";
	private EmSalary_HisSelBll bll = new EmSalary_HisSelBll();

	@Command
	public void submit(@BindingParam("makedate") Date makedate) {
		if (makedate == null) {
			Messagebox.show("填制时间不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (number == null || number.equals("")) {
			Messagebox.show("凭证号不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {

			// 总计
			EmPayModel totalM = new EmPayModel();
			totalM = bll.getEmGZandPayTotal(historyDate);

			// 工资
			List<EmSalaryDataModel> gzList = new ArrayList<EmSalaryDataModel>();
			gzList = bll.getHistoryGZUftxt(historyDate);

			// 支付模块
			List<EmPayModel> epList = new ArrayList<EmPayModel>();
			epList = bll.getEmPayList(historyDate);

			// 个税
			List<EmSalaryDataModel> taxList = new ArrayList<EmSalaryDataModel>();
			taxList = bll.getHistoryTaxUftxt(historyDate);

			if (gzList.size() > 0 || epList.size() > 0) {
				String ownmonth = "";
				if (gzList.size() > 0) {
					ownmonth = String.valueOf(gzList.get(0).getOwnmonth());
				} else {
					ownmonth = String.valueOf(epList.get(0).getOwnmonth());
				}

				String makedateString = "";
				makedateString = DateStringChange.DatetoSting(makedate,
						"yyyy-MM-dd");

				// 创建txt
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String fileName = nowtime + "_uf";// 文件名

				String filePath = "/../../EmSalary/File/Download/UF";
				plyUtil ply = new plyUtil();
				String path = ply.getAbsolutePath(filePath, "", this);
				File file = new File(path);

				// 定义文件名格式并创建
				File txtFile = null;
				try {
					// System.out.print("fileName="+fileName);
					txtFile = File.createTempFile(fileName, ".txt", new File(
							path));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 先读取原有文件内容，然后进行写入操作
				FileWriter writer = null;
				FileOutputStream outSTr = null;
				BufferedOutputStream Buff = null;
				try {
					writer = new FileWriter(txtFile, true);
					outSTr = new FileOutputStream(txtFile);

					Buff = new BufferedOutputStream(outSTr);
					StringBuffer write = new StringBuffer();
					String newline = "\r\n";

					/********************* 每个账单的第一行 *********************************/
					String firstline = "";
					firstline = "填制凭证,V800";
					String username = UserInfo.getUsername();
					String tline = makedateString + ",记," + number + ",0,付雇员"
							+ ownmonth.substring(4, 6) + "月工资,10020105,0.00,"
							+ totalM.getEmpa_fee() + ",,,," + username
							+ ",,,,,,,,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
					/******************************************************/
					write.append(firstline);
					write.append(newline);// 换行
					write.append(tline);
					write.append(newline);// 换行

					// 工资
					if (gzList.size() > 0) {
						for (int i = 0; i < gzList.size(); i++) {
							String line = makedateString
									+ ",记,"
									+ number
									+ ",0,付"
									+ gzList.get(i).getCoba_shortname()
									+ String.valueOf(
											gzList.get(i).getOwnmonth())
											.substring(4, 6)
									+ "月工资,"
									+ ifAF(gzList.get(i).getCoba_ufclass())
									+ ","
									+ gzList.get(i).getEsda_pay()
									+ ",0.00,0,0,,"
									+ username
									+ ",,,,,,"
									+ gzList.get(i).getCoba_ufid()
									+ ",,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
							write.append(line);
							write.append(newline);// 换行
						}
					}

					// 支付模块
					if (epList.size() > 0) {
						for (int i = 0; i < epList.size(); i++) {
							String line = makedateString
									+ ",记,"
									+ number
									+ ",0,付"
									+ epList.get(i).getCoba_shortname()
									+ "-"
									+ epList.get(i).getEmba_name()
									+ DateStringChange.DatetoSting(
											DateStringChange.StringtoDate(
													historyDate, "yyyy-MM-dd"),
											"yyyy-MM-dd").substring(5, 7)
									+ "月"
									+ epList.get(i).getEmpa_remark()
									+ epList.get(i).getEmpa_class()
									+ ","
									+ ifAF(epList.get(i).getCoba_ufclass())
									+ ","
									+ epList.get(i).getEmpa_fee()
									+ ",0.00,0,0,,"
									+ username
									+ ",,,,,,"
									+ epList.get(i).getCoba_ufid()
									+ ",,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
							write.append(line);
							write.append(newline);// 换行
						}
					}

					// 个税
					if (taxList.size() > 0) {
						String zeroString = "0";
						BigDecimal zero = new BigDecimal(zeroString);
						for (int i = 0; i < taxList.size(); i++) {
							if (taxList.get(i).getEsda_tax() != zero) {
								String line = makedateString
										+ ",记,"
										+ number
										+ ",0,计提雇员"
										+ String.valueOf(
												taxList.get(i).getOwnmonth())
												.substring(4, 6)
										+ "月个税,"
										+ "22212202,0.00,"
										+ taxList.get(i).getEsda_tax()
										+ ",,,,"
										+ username
										+ ",,,,,,,,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
							}
						}
					}

					Buff.write(write.toString().getBytes("GBK"));
					/************************* 对txt文件写入内容End ************************************/
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							Buff.flush();
							Buff.close();
							outSTr.close();
							writer.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}
				try {
					Filedownload.save(txtFile, "txt");
					/*
					 * for (GatherInfoModel m : list) {
					 * bll.updateYyOutState(m.getCfsa_id()); }
					 */
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 导出txt

			} else {
				Messagebox.show("没有可导出的数据", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 判断是否属于AF客户
	public String ifAF(String ufclass) {
		String ufid = "";
		if ("224105".equals(ufclass)) {
			ufid = "224103";
		} else {
			ufid = "224104";
		}
		return ufid;
	}

	public String getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
