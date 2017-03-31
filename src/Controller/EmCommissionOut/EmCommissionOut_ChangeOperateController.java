package Controller.EmCommissionOut;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmCommercialInsurance.CI_Insurant_ListDal;

import Model.CI_Insurant_ListModel;
import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutStandardModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;
import bll.Taskflow.Task_controlBll;

public class EmCommissionOut_ChangeOperateController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	Integer daid = 0;

	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private EmCommissionOutChangeModel titleModel;// 获取标准详细信息
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();
	private EmCommissionOutChangeModel mm = new EmCommissionOutChangeModel();
	
	public EmCommissionOut_ChangeOperateController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			mm = (EmCommissionOutChangeModel) Executions.getCurrent().getArg()
					.get("cm");
			init();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setM(bll.getEmCommOutChangeInfo(daid, " and f.typename='后道状态' "));
			setStardModel(bll.getStardInfo(m.getEcoc_ecos_id()));
			setTitleList(bll.getTitleList(m.getEcoc_ecos_id()));
			for (EmCommissionOutChangeModel m1 : titleList) {
				if (m1.getSoin_id().equals(m.getEcoc_soin_id())) {
					setTitleModel(m1);
					break;
				}
			}
			setFeeList(bll.getFeeDetailChangeList(" and eofc_ecoc_id=" + daid));
			for (EmCommissionOutFeeDetailChangeModel tm : feeList) {
				if (tm.getEofc_co_fstart_date() != null) {
					tm.setTempDate(DateStringChange.StringtoDate(
							tm.getEofc_co_fstart_date(), "yyyy-MM"));
				}
				if (tm.getEofc_em_fstart_date() != null) {
					tm.setTempDate1(DateStringChange.StringtoDate(
							tm.getEofc_em_fstart_date(), "yyyy-MM"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出Excel
	 * 
	 */
	@Command("ExportExcel")
	public void ExportExcel() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

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
					label = new Label(0, 4, "委托合同(" + m.getEcoc_addtype()
							+ ")", wcf);
					sheet.addCell(label);

					// 获取字体样式
					cf = sheet.getCell(0, 10).getCellFormat();
					wcf = new WritableCellFormat(cf);
					// 受托单位名称..
					label = new Label(1, 5, m.getCoab_name(), wcf);
					sheet.addCell(label);

					// 委托单位项目负责人..
					label = new Label(5, 6, UserInfo.getUsername(), wcf);
					sheet.addCell(label);

					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(m.getEcos_cabc_id());
					// 受托单位联系人..
					label = new Label(1, 6, calList.get(0).getCali_name(), wcf);
					sheet.addCell(label);

					// 受托单位联系人电话..
					label = new Label(1, 7, calList.get(0).getCali_tel(), wcf);
					sheet.addCell(label);
					// 受托单位传真号码..
					label = new Label(1, 8, calList.get(0).getCali_fax(), wcf);
					sheet.addCell(label);

					// 公司编号..
					label = new Label(4, 10, m.getCid().toString(), wcf);
					sheet.addCell(label);
					// 员工编号..
					label = new Label(4, 11, m.getGid().toString(), wcf);
					sheet.addCell(label);
					// 雇员姓名..
					label = new Label(1, 11, m.getEmba_name(), wcf);
					sheet.addCell(label);
					// 户籍
					// label = new Label(3, 8, m.getEcoc_domicile(), wcf);
					// sheet.addCell(label);
					// 身份证号..
					label = new Label(6, 11, m.getEcoc_idcard(), wcf);
					sheet.addCell(label);
					// 工作单位..
					label = new Label(1, 10, m.getCoba_company(), wcf);
					sheet.addCell(label);
					// 社保缴纳地
					 label = new Label(6, 10, m.getCity(), wcf);
					 sheet.addCell(label);
					// 工作电话..
					label = new Label(1, 12, m.getEcoc_phone(), wcf);
					sheet.addCell(label);
					// 个人手机..
					label = new Label(6, 12, m.getEcoc_mobile(), wcf);
					sheet.addCell(label);
					// Email..
					label = new Label(1, 13, m.getEcoc_email(), wcf);
					sheet.addCell(label);
					// 社保基数..
					label = new Label(1, 14, m.getEcoc_sb_base() + "", wcf);
					sheet.addCell(label);
					// 公积金基数
					label = new Label(4, 14, m.getEcoc_house_base() + "", wcf);
					sheet.addCell(label);

					// 档案情况..
					label = new Label(6, 15, m.getWtss_archives(), wcf);
					sheet.addCell(label);
					// 社保情况..
					label = new Label(4, 13, m.getWtss_shebaoco(), wcf);
					sheet.addCell(label);
					// 住房情况..
					label = new Label(6, 13, m.getWtss_gjjco(), wcf);
					sheet.addCell(label);
					// 用工情况..
					label = new Label(6, 14, m.getWtss_employment(), wcf);
					sheet.addCell(label);
					// 劳动合同版本..
					label = new Label(1, 16, m.getWtss_laborcontractbb(), wcf);
					sheet.addCell(label);
					// 劳动合同签订方..
					label = new Label(4, 16, m.getWtss_laborcontract(), wcf);
					sheet.addCell(label);

					// 获取字体样式
					cf = sheet.getCell(2, 17).getCellFormat();
					wcf = new WritableCellFormat(cf);
					// 服务项目小计
					BigDecimal fwsum = new BigDecimal(0);
					int row = 0;
					int row1 = 28;
					String fuwu_fee="";
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
								jl_sum=m1.getEofc_month_sum();
								jl_cp=m1.getEofc_cp();
								jl_op=m1.getEofc_op();
							}
							
							if (m1.getEofc_name().equals("大病医疗")) {
								jl_sum=jl_sum.add(m1.getEofc_month_sum());
								// 月缴费
								label = new Label(6, row, jl_sum.toString(), wfCo);
								sheet.addCell(label);
								label = new Label(4, row, jl_op, wfCo);
								sheet.addCell(label);
								label = new Label(5, row, jl_cp, wfCo);
								sheet.addCell(label);
							}

							fwsum = fwsum.add(m1.getEofc_month_sum());
						} else if (m1.getSicl_class().equals("服务费")) {
							// 服务费
							//label = new Label(5, 30, m1.getEofc_month_sum()
							//		+ "", wfCo);
							//sheet.addCell(label);
							fuwu_fee=m1.getEofc_month_sum().toString();
						} else if (m1.getSicl_class().equals("档案费")) {
						} else {
							if(m1.getEofc_start_date()!=null){
							sheet.insertRow(row1);
							sheet.mergeCells(1, row1, 5, row1);
							
							if (m1.getEofc_name().equals("服务费")) {
								fuwu_fee=m1.getEofc_month_sum().toString();
							}
							
							// 福利项目
							label = new Label(0, row1, m1.getEofc_name(), sheet
									.getCell(0, 22).getCellFormat());
							sheet.addCell(label);
							// 福利内容
							label = new Label(1, row1, m1.getEofc_content(),
									new WritableCellFormat(sheet.getCell(1, 22)
											.getCellFormat()));
							sheet.addCell(label);
							// 福利费用
							label = new Label(6, row1, m1.getEofc_month_sum()
									+ "", wfCo);
							sheet.addCell(label);
							//福利时间
							label = new Label(7, row1, m1.getEofc_start_date()
									+ "", wfCo);
							sheet.addCell(label);

							row1++;
							}
						}
					}

					row1 += 2;

					// 小计
					label = new Label(5, 24, fwsum + "", wcf);
					sheet.addCell(label);

					// 社保合计
					label = new Label(0, row1, m.getEcoc_sb_sum() + "", wcf);
					sheet.addCell(label);
					// 公积金合计
					label = new Label(1, row1, m.getEcoc_gjj_sum() + "", wcf);
					sheet.addCell(label);
					// 档案费
					label = new Label(2, row1, m.getEcoc_file_fee() + "", wcf);
					sheet.addCell(label);
					// 福利合计
					label = new Label(3, row1, m.getEcoc_welfare_sum() + "",
							wcf);
					sheet.addCell(label);
					//服务费
					label = new Label(5, row1, fuwu_fee,
							wcf);
					sheet.addCell(label);
					// 合计
					label = new Label(6, row1, m.getEcoc_sum() + "", wcf);
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

	/**
	 * 时间批量修改
	 * 
	 * @param date
	 */
	@Command("dateAll")
	@NotifyChange({ "feeList" })
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		if (sicl_class.equals("all")) {
			date = feeList.get(0).getTempDate();
			index = 0;
		}
		for (int i = index + 1; i < feeList.size(); i++) {
			if (feeList.get(i).getSicl_class().equals(sicl_class)
					|| sicl_class.equals("all")) {
				feeList.get(i).setTempDate(date);
			}
		}
	}

	@Command("dateAll1")
	@NotifyChange({ "feeList" })
	public void dateAll1(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		if (sicl_class.equals("all")) {
			date = feeList.get(0).getTempDate1();
			index = 0;
		}
		for (int i = index + 1; i < feeList.size(); i++) {
			if (feeList.get(i).getSicl_class().equals(sicl_class)
					|| sicl_class.equals("all")) {
				feeList.get(i).setTempDate1(date);
			}
		}
	}

	/**
	 * 退回
	 * 
	 * @param win
	 */
	@Command("Back")
	public void Back(@BindingParam("win") Window win) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
		if (m.getEcoc_remark1() == null || m.getEcoc_remark1().isEmpty()) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			if (Messagebox.show("是否确认退回?!", "CONFIRM", Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				try {
					m.setEcoc_addname(UserInfo.getUsername());
					m.setEcoc_state(4);
					m.setEcoc_remark(m.getEcoc_remark1());
					m.setRemark(m.getEcoc_remark1());

					String[] str = bll.back(m);

					if (str[0].equals("1")) {
						mm.setUpdateState(true);
						CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
						List<CI_Insurant_ListModel> listname = dal.getallclient(m.getGid()); 
						Task_controlBll tbll =new Task_controlBll();
						System.out.println("xx");
						System.out.println(Integer.parseInt(str[2].toString()));
						System.out.println(listname.get(0).getCoba_client());
						System.out.println("xx");
						 if (tbll.setOpName(Integer.parseInt(str[2].toString()),listname.get(0).getCoba_client())==1)
				            {			            

				            Messagebox.show(str[1], "操作提示", Messagebox.OK,
				                   Messagebox.INFORMATION);
				            win.detach();
				            }
				            else
				            {
				                Messagebox
				                .show("退回失败，请联系IT部门!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				            } 
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();

		m.setEcoc_addname(UserInfo.getUsername());
		if (!m.getEcoc_state().equals(3)) {
			m.setEcoc_state(m.getEcoc_state() + 1);
		}
		m.setType(m.getEcoc_type());
		m.setEcoc_remark(m.getEcoc_remark1());
		m.setRemark(m.getEcoc_remark1());

		try {
			if (m.getEcoc_state().equals(3)) {
				for (EmCommissionOutFeeDetailChangeModel m : feeList) {
					if (m.getTempDate() != null) {
						m.setEofc_co_fstart_date(DateStringChange.DatetoSting(
								m.getTempDate(), "yyyy-MM-01"));
					}
					if (m.getTempDate1() != null) {
						m.setEofc_em_fstart_date(DateStringChange.DatetoSting(
								m.getTempDate1(), "yyyy-MM-01"));
					}
				}
			}

			statehandle();

			if (m.getEcoc_state().equals(6)) {
				if (Messagebox.show("所有实际起始日已填写,是否完成此单?", "CONFIRM",
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {

					String[] str = bll.updatestate(m, feeList);

					if (str[0].equals("1")) {
						mm.setUpdateState(true);
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				String[] str = bll.updatestate(m, feeList);

				if (str[0].equals("1")) {
					mm.setUpdateState(true);
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 处理状态(如果所有项目的实际起始日不为空，状态为6(已完成),否则状态不变)
	 * 
	 */
	public void statehandle() {
		try {
			Boolean bol = true;
			for (EmCommissionOutFeeDetailChangeModel tm : feeList) {
				if (tm.getEofc_start_date() != null) {
					if (tm.getEofc_em_fstart_date() == null
							|| tm.getEofc_co_fstart_date() == null) {
						bol = false;
						break;
					}
				}
			}

			if (bol) {
				m.setEcoc_state(6);
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

	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public final EmCommissionOutChangeModel getM() {
		return m;
	}

	public final List<EmCommissionOutChangeModel> getTitleList() {
		return titleList;
	}

	public final EmCommissionOutChangeModel getTitleModel() {
		return titleModel;
	}

	public final List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public final void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}

	public final void setTitleList(List<EmCommissionOutChangeModel> titleList) {
		this.titleList = titleList;
	}

	public final void setTitleModel(EmCommissionOutChangeModel titleModel) {
		this.titleModel = titleModel;
	}

	public final void setFeeList(
			List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}
}
