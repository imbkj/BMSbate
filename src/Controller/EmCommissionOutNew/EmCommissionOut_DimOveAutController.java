package Controller.EmCommissionOutNew;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sun.mail.dsn.message_deliverystatus;

import bll.EmCommissionOutNew.EmCommissionOut_AddAutBll;
import bll.EmCommissionOutNew.EmCommissionOut_AddOveAutBll;
import bll.EmCommissionOutNew.EmCommissionOut_DimOveAutBll;
import bll.EmCommissionOutNew.EmCommissionOut_AutOperateBll;
import bll.Taskflow.Task_controlBll;

import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutZYTModel;

import Model.EmCommissionOutFeeDetailChangeModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;

public class EmCommissionOut_DimOveAutController {
	private ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_feedetail;// 显示委托社保费用明细

	private ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_flfeedetail;// 显示委托福利费用明细

	private EmCommissionOutChangeModel wt_list;// 显示委托主表明细

	private ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_qd_feedetail;// 前道显示委托社保费用明细

	private ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_qd_flfeedetail;// 前道显示委托福利费用明细

	private EmCommissionOutChangeModel wt_qd_list;// 前道显示委托主表明细

	private ListModelList<EmCommissionOutZYTModel> ecoc_list;// excel显示主表委托明细

	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();// 社保费用明细

	private List<EmCommissionOutChangeModel> gettaprid = new ListModelList<>();// 获取cid,taprid

	private List<EmCommissionOutChangeModel> wtout_change = new ListModelList<>();// 获取历史记录

	private List<EmCommissionOutChangeModel> wtout_content = new ListModelList<>();// 获取历史操作记录

	private List<EmCommissionOutChangeModel> wtout_remarklist = new ListModelList<>();// 获取历史记录

	EmCommissionOut_AddAutBll rebll = new EmCommissionOut_AddAutBll();

	EmCommissionOut_DimOveAutBll bll = new EmCommissionOut_DimOveAutBll();

	String ecoc_id = "";

	@Init
	public void init() throws SQLException {
		ecoc_id = Executions.getCurrent().getArg().get("ecoc_id").toString();

		wtout_add_feedetail = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getfeedetail(ecoc_id));// 显示委托费用明细

		wt_list = bll.getwt_list(ecoc_id);// 显示委托主表明细

		wtout_add_flfeedetail = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getflfeedetail(ecoc_id));// 显示委托福利费用明细

		wtout_add_qd_feedetail = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getqdfeedetail(ecoc_id));// 显示委托费用明细

		wt_qd_list = bll.getqdwt_list(ecoc_id);// 显示委托主表明细

		wtout_add_qd_flfeedetail = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getqdflfeedetail(ecoc_id));// 显示委托福利费用明细

		wtout_change = new ListModelList<EmCommissionOutChangeModel>(
				bll.getchange(ecoc_id));// 获取历史记录

		wtout_content = new ListModelList<EmCommissionOutChangeModel>(
				bll.getcontent(ecoc_id));// 获取历史操作记录

		wtout_remarklist = new ListModelList<EmCommissionOutChangeModel>(
				rebll.wtout_remarklist(ecoc_id));// 获取委托备注
	}

	// 导出Excel
	@Command("ExportExcel")
	public void ExportExcel() throws SQLException {
		EmCommissionOut_DimOveAutBll bll = new EmCommissionOut_DimOveAutBll();

		setFeeList(bll.getFeeDetailChangeList(" and eofc_ecoc_id=" + ecoc_id));// 社保费用明细

		ecoc_list = new ListModelList<EmCommissionOutZYTModel>(
				bll.ci_excel(ecoc_id));// 显示主表委托明细

		excel: {
			try {
				String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
						.format(new Date())
						+ UserInfo.getUserid()
						+ "wt_out_excel.xls";
				File file = new File(new plyUtil().getAbsolutePath(
						"/../../OfficeFile/DownLoad/EmCommissionOut", filename,
						this));
				try {
					file.createNewFile();
				} catch (Exception e) {
					Messagebox.show("生成文件出错,请再次点击生成!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					break excel;
				}

				// 读取Excel模板
				Workbook wb = Workbook.getWorkbook(new File(new plyUtil()
						.getAbsolutePath(
								"/../../OfficeFile/Templet/EmCommissionOut",
								"wt_add.xls", this)));

				// 使用模板创建
				WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

				// 生成工作表,(name:First Sheet,参数0表示这是第一页)
				WritableSheet sheet = wwb.getSheet(0);

				WritableCellFormat wfCo = getFormatBorder();

				// 开始写入内容
				try {
					Label label = null;
					CellFormat cf = null;
					WritableCellFormat wcf = null;

					// 获取字体样式
					cf = sheet.getCell(0, 4).getCellFormat();
					wcf = new WritableCellFormat(cf);
					// 委托单类型
					label = new Label(0, 4, "委托合同(" + ecoc_list.get(0).getT9()
							+ ")", wcf);
					sheet.addCell(label);

					// 获取字体样式
					cf = sheet.getCell(0, 10).getCellFormat();
					wcf = new WritableCellFormat(cf);
					// 受托单位名称..
					label = new Label(1, 5, ecoc_list.get(0).getT110(), wcf);
					sheet.addCell(label);

					// 委托单位项目负责人..
					label = new Label(5, 6, UserInfo.getUsername(), wcf);
					sheet.addCell(label);

					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(ecoc_list.get(0).getT200());
					// 受托单位联系人..
					label = new Label(1, 6, calList.get(0).getCali_name(), wcf);
					sheet.addCell(label);

					// 受托单位联系人电话..
					label = new Label(1, 7, calList.get(0).getCali_tel(), wcf);
					sheet.addCell(label);
					// 受托单位传真号码..
					label = new Label(1, 8, calList.get(0).getCali_fax(), wcf);
					sheet.addCell(label);

					// 合同起始时间
					label = new Label(1, 15, ecoc_list.get(0).getT18(), wcf);
					sheet.addCell(label);

					// 合同结束时间
					label = new Label(4, 15, ecoc_list.get(0).getT19(), wcf);
					sheet.addCell(label);

					// 公司编号..
					label = new Label(4, 10, ecoc_list.get(0).getT6(), wcf);
					sheet.addCell(label);
					// 员工编号..
					label = new Label(4, 11, ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);
					// 雇员姓名..
					label = new Label(1, 11, ecoc_list.get(0).getT2(), wcf);
					sheet.addCell(label);
					// 身份证号..
					label = new Label(6, 11, ecoc_list.get(0).getT11(), wcf);
					sheet.addCell(label);
					// 工作单位..
					label = new Label(1, 10, ecoc_list.get(0).getT7(), wcf);
					sheet.addCell(label);
					// 社保缴纳地
					label = new Label(6, 10, ecoc_list.get(0).getT24(), wcf);
					sheet.addCell(label);
					// 工作电话..
					label = new Label(1, 12, ecoc_list.get(0).getT5(), wcf);
					sheet.addCell(label);
					// 个人手机..
					label = new Label(6, 12, ecoc_list.get(0).getT4(), wcf);
					sheet.addCell(label);
					// Email..
					label = new Label(1, 13, ecoc_list.get(0).getT14(), wcf);
					sheet.addCell(label);
					// 社保基数..
					label = new Label(1, 14, ecoc_list.get(0).getT199() + "",
							wcf);
					sheet.addCell(label);
					// 公积金基数
					label = new Label(4, 14, ecoc_list.get(0).getT198() + "",
							wcf);
					sheet.addCell(label);

					// 档案情况..
					label = new Label(6, 15, ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);
					// 社保情况..
					label = new Label(4, 13, ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);
					// 住房情况..
					label = new Label(6, 13, ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);
					// 用工情况..
					label = new Label(6, 14, ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);
					// 劳动合同版本..
					label = new Label(1, 16, ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);
					// 劳动合同签订方..
					label = new Label(4, 16, ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);

					// 获取字体样式
					cf = sheet.getCell(2, 17).getCellFormat();
					wcf = new WritableCellFormat(cf);
					// 服务项目小计
					BigDecimal fwsum = new BigDecimal(0);
					int row = 0;
					int row1 = 28;
					String fuwu_fee = "";
					BigDecimal jl_sum = new BigDecimal(0);
					String jl_cp = "";
					String jl_op = "";
					for (int i = 0; i < feeList.size(); i++) {
						EmCommissionOutFeeDetailChangeModel m1 = feeList.get(i);

						if (m1.getSicl_class().equals("社会保险")
								|| m1.getSicl_class().equals("公积金")) {
							switch (m1.getEofc_name()) {
							case "养老保险":
								row = 18;
								break;
							case "失业保险":
								row = 19;
								break;
							case "工伤保险":
								row = 20;
								break;
							case "医疗保险":
								row = 21;
								break;
							case "生育保险":
								row = 22;
								break;
							case "住房公积金":
								row = 23;
								break;
							case "补充公积金":
								row = 24;
								break;

							default:
								break;
							}

							// 企业缴费基数
							label = new Label(2, row,
									m1.getEofc_co_base() + "", wfCo);
							sheet.addCell(label);
							// 个人缴费基数
							label = new Label(3, row,
									m1.getEofc_em_base() + "", wfCo);
							sheet.addCell(label);
							if (m1.getEofc_name().equals("补充公积金")) {
								// 企业比例、个人比例
								label = new Label(4, row, m1.getEofc_op(), wfCo);
								sheet.addCell(label);
							} else {
								// 企业比例
								label = new Label(4, row, m1.getEofc_op(), wfCo);
								sheet.addCell(label);
								// 个人比例
								label = new Label(5, row, m1.getEofc_cp(), wfCo);
								sheet.addCell(label);
							}
							// 月缴费
							label = new Label(6, row, m1.getEofc_month_sum()
									+ "", wfCo);
							sheet.addCell(label);
							// 委托起始日
							label = new Label(7, row, m1.getEofc_start_date(),
									wfCo);
							sheet.addCell(label);

							if (m1.getEofc_name().equals("医疗保险")) {
								jl_sum = m1.getEofc_month_sum();
								jl_cp = m1.getEofc_cp();
								jl_op = m1.getEofc_op();
							}

							if (m1.getEofc_name().equals("大病医疗")) {
								jl_sum = jl_sum.add(m1.getEofc_month_sum());
								// 月缴费
								label = new Label(6, row, jl_sum.toString(),
										wfCo);
								sheet.addCell(label);
								label = new Label(4, row, jl_op, wfCo);
								sheet.addCell(label);
								label = new Label(5, row, jl_cp, wfCo);
								sheet.addCell(label);
							}

							fwsum = fwsum.add(m1.getEofc_month_sum());
						} else if (m1.getSicl_class().equals("服务费")) {
							// 服务费
							// label = new Label(5, 30, m1.getEofc_month_sum()
							// + "", wfCo);
							// sheet.addCell(label);
							fuwu_fee = m1.getEofc_month_sum().toString();
						} else if (m1.getSicl_class().equals("档案费")) {
						} else {
							if (m1.getEofc_start_date() != null) {
								sheet.insertRow(row1);
								sheet.mergeCells(1, row1, 5, row1);

								if (m1.getEofc_name().equals("服务费")) {
									fuwu_fee = m1.getEofc_month_sum()
											.toString();
								}

								// 福利项目
								label = new Label(0, row1, m1.getEofc_name(),
										sheet.getCell(0, 22).getCellFormat());
								sheet.addCell(label);
								// 福利内容
								label = new Label(1, row1,
										m1.getEofc_content(),
										new WritableCellFormat(sheet.getCell(1,
												22).getCellFormat()));
								sheet.addCell(label);
								// 福利费用
								label = new Label(6, row1,
										m1.getEofc_month_sum() + "", wfCo);
								sheet.addCell(label);
								// 福利时间
								label = new Label(7, row1,
										m1.getEofc_start_date() + "", wfCo);
								sheet.addCell(label);

								row1++;
							}
						}
					}

					row1 += 2;

					// 公司社保费
					label = new Label(0, 25, "公司社保费："
							+ ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);

					// 个人社保费
					label = new Label(2, 25, "个人社保费："
							+ ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);

					// 公司公积金费
					label = new Label(4, 25, "公司公积金费："
							+ ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);

					// 个人公积金费
					label = new Label(6, 25, "个人公积金费："
							+ ecoc_list.get(0).getT1(), wcf);
					sheet.addCell(label);

					// 小计
					label = new Label(5, 24, fwsum + "", wcf);
					sheet.addCell(label);

					// 社保合计
					label = new Label(0, row1, ecoc_list.get(0).getT1() + "",
							wcf);
					sheet.addCell(label);
					// 公积金合计
					label = new Label(1, row1, ecoc_list.get(0).getT1() + "",
							wcf);
					sheet.addCell(label);
					// 档案费
					label = new Label(2, row1, ecoc_list.get(0).getT1() + "",
							wcf);
					sheet.addCell(label);
					// 福利合计
					label = new Label(3, row1, ecoc_list.get(0).getT1() + "",
							wcf);
					sheet.addCell(label);
					// 服务费
					label = new Label(5, row1, fuwu_fee, wcf);
					sheet.addCell(label);
					// 合计
					label = new Label(6, row1, ecoc_list.get(0).getT1() + "",
							wcf);
					sheet.addCell(label);
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("写入内容失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					break excel;
				}

				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();

				try {
					FileOperate.download("OfficeFile/DownLoad/EmCommissionOut/"
							+ filename);
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					break excel;
				}

			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 二次确认
	@Command("yc_aut")
	public void yc_aut(@BindingParam("g2") Grid gridco,
			@BindingParam("g3") Grid gridco2, @BindingParam("win") Window win)
			throws NumberFormatException, Exception {
		EmCommissionOut_AutOperateBll ccsaBll = new EmCommissionOut_AutOperateBll();
		setGettaprid(bll.gettaprid(ecoc_id));// 获取tapr_id
		int rsult = 0;
		int j = 0;
		int k = 0;
		String wt_name = "";
		// 更新社保时间
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			org.zkoss.zul.Label ecof_id = (org.zkoss.zul.Label) gridco
					.getCell(i, 11).getChildren().get(0);
			j = j + 1;
				Datebox ecof_co_fstart = (Datebox) gridco.getCell(i, 7)
						.getChildren().get(0);
				Datebox ecof_em_fstart = (Datebox) gridco.getCell(i, 9)
						.getChildren().get(0);

				String formatStr = "";
				String formatStr2 = "";
				
				if (ecof_co_fstart.getValue() != null) {
					formatStr = ecof_co_fstart.getValue().toString();
				}
				if (ecof_em_fstart.getValue() != null) {
					formatStr2 = ecof_em_fstart.getValue().toString();
				}

				try {
					// java.util.Date对象
					if (!ecof_co_fstart.getValue().equals("")
							&& ecof_co_fstart.getValue() != null) {
						formatStr = bll.DatetoSting(ecof_co_fstart.getValue());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					if (!ecof_em_fstart.getValue().equals("")
							&& ecof_em_fstart.getValue() != null) {
						formatStr2 = bll.DatetoSting(ecof_em_fstart.getValue());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				if (!formatStr.equals("") || !formatStr2.equals("")) {
					k = k + 1;
					wt_name = wt_name + ecof_id.getValue().toString() + ","
							+ formatStr + "," + formatStr2 + "|";
				}
				//rsult = bll.getstateover(ecof_id.getValue().toString(),
				//		formatStr, formatStr2);// 更新二次确认时间
			
		}

		// 更新福利时间
		for (int i = 0; i < gridco2.getRows().getChildren().size(); i++) {
			org.zkoss.zul.Label ecof_id = (org.zkoss.zul.Label) gridco2
					.getCell(i, 6).getChildren().get(0);
			
			j = j + 1;
				Datebox ecof_co_fstart = (Datebox) gridco2.getCell(i, 3)
						.getChildren().get(0);
				Datebox ecof_em_fstart = (Datebox) gridco2.getCell(i, 5)
						.getChildren().get(0);

				String formatStr = "";
				String formatStr2 = "";
				
				if (ecof_co_fstart.getValue() != null) {
					formatStr = ecof_co_fstart.getValue().toString();
				}
				if (ecof_em_fstart.getValue() != null) {
					formatStr2 = ecof_em_fstart.getValue().toString();
				}

				try {
					// java.util.Date对象
					if (!ecof_co_fstart.getValue().equals("")
							&& ecof_co_fstart.getValue() != null) {
						formatStr = bll.DatetoSting(ecof_co_fstart.getValue());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					if (!ecof_em_fstart.getValue().equals("")
							&& ecof_em_fstart.getValue() != null) {
						formatStr2 = bll.DatetoSting(ecof_em_fstart.getValue());
					}
			} catch (Exception e) { 
				// TODO: handle exception
			}
				
					rsult = bll.getstateover(ecof_id.getValue().toString(),
							formatStr, formatStr2);// 更新二次确认时间
				if (!formatStr.equals("") || !formatStr2.equals("")) {
					k = k + 1;
					wt_name = wt_name + ecof_id.getValue().toString() + ","
							+ formatStr + "," + formatStr2 + "|";
				}
		}
		
		rsult = bll.getstateovernew(wt_name);// 更新二次确认时间

		// 弹出提示
		if (rsult != 0) {

			// 弹出提示
			//if (j == k) {
			
			String[] message = ccsaBll.ec_aut(
					ecoc_id,
					Integer.parseInt(gettaprid.get(0).getEcoc_tapr_id()
							.toString()));

			Messagebox.show("委托单已完成！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
		} else {
			Messagebox.show("委托单部分完成！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
		}
		win.detach();
	//} else {
		//Messagebox
		//		.show("委托确认失败！", "ERROR", Messagebox.OK, Messagebox.ERROR);
	//}
	}

	// 委托处理
	@Command("remark_add")
	@NotifyChange("wtout_remarklist")
	public void remark_add() throws SQLException {

		Map arg = new HashMap();
		arg.put("ecoc_id", ecoc_id);

		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOut_Remark.zul", null, arg);
		wnd.doModal();
		wtout_remarklist = new ListModelList<EmCommissionOutChangeModel>(
				rebll.wtout_remarklist(ecoc_id));// 获取委托备注
	}

	// 退回
	@Command("Back")
	public void Back(@BindingParam("win") Window win,
			@BindingParam("outcontent") Textbox outcontent) throws SQLException {
		if (outcontent.getValue().equals("")) {
			Messagebox.show("请录入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			EmCommissionOut_AutOperateBll ccsaBll = new EmCommissionOut_AutOperateBll();
			setGettaprid(bll.gettaprid(ecoc_id));// 社保费用明细
			try {
				String[] message = ccsaBll.back(
						ecoc_id,
						Integer.parseInt(gettaprid.get(0).getEcoc_tapr_id()
								.toString()), outcontent.getValue());

				// 弹出提示
				if (message[0] != "0") {
					Task_controlBll tbll =new Task_controlBll();
					 if (tbll.setOpName(Integer.parseInt(message[2].toString()),gettaprid.get(0).getCoba_client())==1)
			            {			            

			            Messagebox.show(message[1], "操作提示", Messagebox.OK,
			                   Messagebox.INFORMATION);
			            win.detach();
			            }
			            else
			            {
			                Messagebox
			                .show("退回失败，请联系IT部门!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			            } 
				} else {
					Messagebox.show(message[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				// TODO: handle exception
				Messagebox.show("操作出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 企业日期控制
	@Command("dateAll1")
	public void dateAll1(@BindingParam("g2") Grid gridco) throws SQLException,
			ParseException {
		try {
			Datebox cp_date1 = (Datebox) gridco.getCell(0, 7).getChildren()
					.get(0);

			String dateStr = cp_date1.getValue().toString();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

			// java.util.Date对象
			Date date = (Date) sdf.parse(dateStr);

			String formatStr = new SimpleDateFormat("yyyy-MM-DD").format(date);

			System.out.println(formatStr);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-DD");
			Date date1 = sdf1.parse(formatStr);

			for (int i = 1; i < gridco.getRows().getChildren().size(); i++) {
				Datebox dateot = (Datebox) gridco.getCell(i, 7).getChildren()
						.get(0);

				dateot.setValue(date1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 个人日期控制
	@Command("dateAll2")
	public void dateAll2(@BindingParam("g2") Grid gridco) throws SQLException,
			ParseException {
		try {
			Datebox cp_date1 = (Datebox) gridco.getCell(0, 9).getChildren()
					.get(0);

			String dateStr = cp_date1.getValue().toString();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

			// java.util.Date对象
			Date date = (Date) sdf.parse(dateStr);

			String formatStr = new SimpleDateFormat("yyyy-MM-DD").format(date);

			System.out.println(formatStr);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-DD");
			Date date1 = sdf1.parse(formatStr);

			for (int i = 1; i < gridco.getRows().getChildren().size(); i++) {
				Datebox dateot = (Datebox) gridco.getCell(i, 9).getChildren()
						.get(0);

				dateot.setValue(date1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 福利企业日期控制
	@Command("dateflAll1")
	public void dateflAll1(@BindingParam("g3") Grid gridco)
			throws SQLException, ParseException {
		try {
			Datebox cp_date1 = (Datebox) gridco.getCell(0, 3).getChildren()
					.get(0);

			String dateStr = cp_date1.getValue().toString();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

			// java.util.Date对象
			Date date = (Date) sdf.parse(dateStr);

			String formatStr = new SimpleDateFormat("yyyy-MM-DD").format(date);

			System.out.println(formatStr);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-DD");
			Date date1 = sdf1.parse(formatStr);

			for (int i = 1; i < gridco.getRows().getChildren().size(); i++) {
				Datebox dateot = (Datebox) gridco.getCell(i, 3).getChildren()
						.get(0);

				dateot.setValue(date1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 个人日期控制
	@Command("dateflAll2")
	public void dateflAll2(@BindingParam("g3") Grid gridco)
			throws SQLException, ParseException {
		Datebox cp_date1 = (Datebox) gridco.getCell(0, 5).getChildren().get(0);

		String dateStr = cp_date1.getValue().toString();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

		// java.util.Date对象
		Date date = (Date) sdf.parse(dateStr);

		String formatStr = new SimpleDateFormat("yyyy-MM-DD").format(date);

		System.out.println(formatStr);

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-DD");
		Date date1 = sdf1.parse(formatStr);

		try {
			for (int i = 1; i < gridco.getRows().getChildren().size(); i++) {
				Datebox dateot = (Datebox) gridco.getCell(i, 5).getChildren()
						.get(0);

				dateot.setValue(date1);
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	public ListModelList<EmCommissionOutFeeDetailChangeModel> getWtout_add_feedetail() {
		return wtout_add_feedetail;
	}

	public void setWtout_add_feedetail(
			ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_feedetail) {
		this.wtout_add_feedetail = wtout_add_feedetail;
	}

	public ListModelList<EmCommissionOutZYTModel> getEcoc_list() {
		return ecoc_list;
	}

	public void setEcoc_list(ListModelList<EmCommissionOutZYTModel> ecoc_list) {
		this.ecoc_list = ecoc_list;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public EmCommissionOutChangeModel getWt_list() {
		return wt_list;
	}

	public void setWt_list(EmCommissionOutChangeModel wt_list) {
		this.wt_list = wt_list;
	}

	public List<EmCommissionOutChangeModel> getGettaprid() {
		return gettaprid;
	}

	public void setGettaprid(List<EmCommissionOutChangeModel> gettaprid) {
		this.gettaprid = gettaprid;
	}

	public ListModelList<EmCommissionOutFeeDetailChangeModel> getWtout_add_flfeedetail() {
		return wtout_add_flfeedetail;
	}

	public void setWtout_add_flfeedetail(
			ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_flfeedetail) {
		this.wtout_add_flfeedetail = wtout_add_flfeedetail;
	}

	public ListModelList<EmCommissionOutFeeDetailChangeModel> getWtout_add_qd_feedetail() {
		return wtout_add_qd_feedetail;
	}

	public void setWtout_add_qd_feedetail(
			ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_qd_feedetail) {
		this.wtout_add_qd_feedetail = wtout_add_qd_feedetail;
	}

	public ListModelList<EmCommissionOutFeeDetailChangeModel> getWtout_add_qd_flfeedetail() {
		return wtout_add_qd_flfeedetail;
	}

	public void setWtout_add_qd_flfeedetail(
			ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_add_qd_flfeedetail) {
		this.wtout_add_qd_flfeedetail = wtout_add_qd_flfeedetail;
	}

	public EmCommissionOutChangeModel getWt_qd_list() {
		return wt_qd_list;
	}

	public void setWt_qd_list(EmCommissionOutChangeModel wt_qd_list) {
		this.wt_qd_list = wt_qd_list;
	}

	public List<EmCommissionOutChangeModel> getWtout_change() {
		return wtout_change;
	}

	public void setWtout_change(List<EmCommissionOutChangeModel> wtout_change) {
		this.wtout_change = wtout_change;
	}

	public List<EmCommissionOutChangeModel> getWtout_content() {
		return wtout_content;
	}

	public void setWtout_content(List<EmCommissionOutChangeModel> wtout_content) {
		this.wtout_content = wtout_content;
	}

	public List<EmCommissionOutChangeModel> getWtout_remarklist() {
		return wtout_remarklist;
	}

	public void setWtout_remarklist(
			List<EmCommissionOutChangeModel> wtout_remarklist) {
		this.wtout_remarklist = wtout_remarklist;
	}

}
