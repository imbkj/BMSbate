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
import Util.DateUtil;
import Util.FileOperate;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;
import bll.Taskflow.Task_controlBll;

public class EmCommissionOut_TerminationOperateController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	Integer daid = 0;

	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private EmCommissionOutChangeModel titleModel;// 获取标准详细信息
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();
	private EmCommissionOutChangeModel mm = new EmCommissionOutChangeModel();

	public EmCommissionOut_TerminationOperateController() {
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
			setM(bll.getEmCommOutChangeInfo(daid, ""));
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
				if (tm.getEofc_co_fstop_date() != null) {
					tm.setTempDate(DateStringChange.StringtoDate(
							tm.getEofc_co_fstop_date(), "yyyy-MM"));
				}
				if (tm.getEofc_em_fstop_date() != null) {
					tm.setTempDate1(DateStringChange.StringtoDate(
							tm.getEofc_em_fstop_date(), "yyyy-MM"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		m.setType("ecocter");
		m.setEcoc_remark(m.getEcoc_remark1());

		try {
			if (m.getEcoc_state().equals(3)) {
				for (EmCommissionOutFeeDetailChangeModel m : feeList) {
					if (m.getTempDate() != null) {
						m.setEofc_co_fstop_date(DateStringChange.DatetoSting(
								DateUtil.getLastDay(m.getTempDate()),
								"yyyy-MM-dd"));
					}
					if (m.getTempDate1() != null) {
						m.setEofc_em_fstop_date(DateStringChange.DatetoSting(
								DateUtil.getLastDay(m.getTempDate1()),
								"yyyy-MM-dd"));
					}
				}
			}

			statehandle();

			if (m.getEcoc_state().equals(6)) {
				if (Messagebox.show("所有实际停缴日已填写,是否完成此单?", "CONFIRM",
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
	 * 处理状态(如果所有项目的实际停缴日不为空，状态为6(已完成),否则状态不变)
	 * 
	 */
	public void statehandle() {
		try {
			Boolean bol = true;
			for (EmCommissionOutFeeDetailChangeModel tm : feeList) {
				if (tm.getEofc_start_date() != null) {
					if (tm.getEofc_em_fstop_date() == null
							|| tm.getEofc_co_fstop_date() == null) {
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
						+ "wt_out_excelter.xls";
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
								"wt_dis.xls", this)));

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
					cf = sheet.getCell(0, 5).getCellFormat();

					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(m.getEcos_cabc_id());

					// 获取字体样式
					wfCo = new WritableCellFormat(cf);
					// 受托单位名称..
					label = new Label(1, 5, m.getCoab_name(), wfCo);
					sheet.addCell(label);
					// 受托单位传真号码..
					label = new Label(1, 8, calList.get(0).getCali_fax(), wfCo);
					sheet.addCell(label);
					// 受托单位经办人..
					label = new Label(1, 6, calList.get(0).getCali_name(), wfCo);
					sheet.addCell(label);
					
					// 离职时间
					label = new Label(2, 14, m.getEcoc_stop_date().toString(), wfCo);
					sheet.addCell(label);
					
					// 离职原因
					label = new Label(5, 14, m.getEcoc_stop_cause(), wfCo);
					sheet.addCell(label);
					
					// 委托单位项目负责人..
					label = new Label(4, 6, UserInfo.getUsername(), wfCo);
					sheet.addCell(label);
					
					// 委托日期..
					// label = new Label(3, 5, new
					// SimpleDateFormat("yyyy-MM-dd").format(new Date()), wcf);
					// sheet.addCell(label);
					// 工作单位..
					
					// 获取字体样式
					cf = sheet.getCell(0, 12).getCellFormat();
					wfCo = new WritableCellFormat(cf);
					
					// 受托城市..
					label = new Label(6, 12, m.getCity(), wfCo);
					sheet.addCell(label);
					
					label = new Label(2, 12, m.getCoba_company(), wfCo);
					sheet.addCell(label);
					// 雇员姓名..
					label = new Label(2, 13, m.getEmba_name(), wfCo);
					sheet.addCell(label);
					// 身份证号..
					label = new Label(5, 13, m.getEcoc_idcard(), wfCo);
					sheet.addCell(label);

					for (int i = 0; i < feeList.size(); i++) {
						if (feeList.get(i).getEofc_name().equals("养老保险")) {
							// 养老终止时间..
							label = new Label(2, 16, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}

						if (feeList.get(i).getEofc_name().equals("医疗保险")) {
							// 养老终止时间..
							label = new Label(2, 17, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}
						
						if (feeList.get(i).getEofc_name().equals("生育保险")) {
							// 养老终止时间..
							label = new Label(2, 18, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}
						
						if (feeList.get(i).getEofc_name().equals("工伤保险")) {
							// 养老终止时间..
							label = new Label(5, 17, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}
						
						if (feeList.get(i).getEofc_name().equals("失业保险")) {
							// 养老终止时间..
							label = new Label(5, 16, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}
						
						if (feeList.get(i).getEofc_name().equals("住房公积金")) {
							// 养老终止时间..
							label = new Label(2, 19, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}
						
						if (feeList.get(i).getEofc_name().equals("补充公积金")) {
							// 养老终止时间..
							label = new Label(5, 19, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}
						
						if (feeList.get(i).getEofc_name().equals("服务费")) {
							// 养老终止时间..
							label = new Label(2,20, feeList.get(i)
									.getEofc_stop_date(), wfCo);
							sheet.addCell(label);
						}
						
					}

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
