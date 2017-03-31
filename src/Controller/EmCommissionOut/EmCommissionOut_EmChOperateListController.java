package Controller.EmCommissionOut;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.PubStateModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_EmChOperateListController {

	private List<EmCommissionOutChangeModel> ecocList;
	private List<EmCommissionOutChangeModel> secocList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> cityList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coabList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> clientList = new ListModelList<>();
	private List<PubStateModel> stateList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> countList = new ListModelList<>();

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String gid = "";
	private String gid1 = "";
	private String name = "";
	private String idcard = "";
	private String city = "";
	private String coabname = "";
	private String client = "";
	private String statename = "";
	private String addtype = "";

	public EmCommissionOut_EmChOperateListController() {
		try {
			bind();
			search();
			
			System.out.println("xxxxxxx");
			System.out.println(gid1);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化绑定
	 * 
	 */
	public void bind() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		
		try {
			gid1 = Executions.getCurrent().getArg().get("gid").toString();
			setEcocList(bll.getEmCommOutChangeOutList(" and typename='前道状态' and a.gid="+gid1));
			setCityList(bll.getCityList("",""));
			cityList.add(0, null);
			setCoabList(bll.getCoagencyList());
			coabList.add(0, null);
			setClientList(bll.getClientList());
			clientList.add(0, null);
			statelistbind();

			setCountList(bll.getCountList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 状态列表绑定
	 * 
	 */
	@Command("statelistbind")
	@NotifyChange({ "stateList", "statename", "secocList" })
	public void statelistbind() {
		try {
			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			String type = statehandle();

			if (!type.isEmpty()) {
				setStateList(bll.getStateList(" and typename='前道状态' and type='"
						+ type + "'"));
			} else {
				setStateList(bll
						.getStateList(" and typename='前道状态' and type='ecocall'"));
			}
			stateList.add(0, null);
			statename = "";

			search();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	/**
	 * 列表检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "secocList" })
	public void search() {
		secocList.clear();

		for (EmCommissionOutChangeModel m : ecocList) {
			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!gid.isEmpty()) {
				if (!RegexUtil.isExists(gid, m.getGid() + "")) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getEmba_name())) {
					continue;
				}
			}
			if (!idcard.isEmpty()) {
				if (!idcard.equals(m.getEcoc_idcard())) {
					continue;
				}
			}
			if (!city.isEmpty()) {
				if (!city.equals(m.getCity())) {
					continue;
				}
			}
			if (!coabname.isEmpty()) {
				if (!coabname.equals(m.getCoab_name())) {
					continue;
				}
			}
			if (!client.isEmpty()) {
				if (!client.equals(m.getEcoc_client())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!statename.equals(m.getStatename())) {
					continue;
				}
			}
			if (!addtype.isEmpty()) {
				if (!addtype.equals(m.getEcoc_addtype())) {
					continue;
				}
			}
			secocList.add(m);
		}
	}

	/**
	 * 综合检索
	 * 
	 */
	@Command("multsearch")
	@NotifyChange({ "secocList", "statename", "addtype", "stateList" })
	public void multsearch(@BindingParam("ecoc_addtype") String ecoc_addtype,
			@BindingParam("ecoc_state") String ecoc_state) {

		addtype = ecoc_addtype;
		statelistbind();
		statename = ecoc_state;

		search();
	}

	@Command("updatestate")
	@NotifyChange({ "secocList", "countList" })
	public void updatestate(@BindingParam("each") EmCommissionOutChangeModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getEcoc_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		bind();
		search();
	}

	@Command("detail")
	@NotifyChange({ "secocList", "countList" })
	public void detail(@BindingParam("each") EmCommissionOutChangeModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getEcoc_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		bind();
		search();
	}

	public String statehandle() {
		String type = "";
		if (addtype.equals("新增")) {
			type = "ecocadd";
		} else if (addtype.equals("调整")) {
			type = "ecocchange";
		} else if (addtype.equals("离职")) {
			type = "ecocter";
		} else if (addtype.equals("一次性费用")) {
			type = "ecocotf";
		}
		return type;
	}

	/**
	 * 导出Excel(单条、批量)
	 * 
	 * @param set
	 */
	@Command("ExportExcel")
	public void ExportExcel(@BindingParam("set") Set<Listitem> set) {
		if (set.size() <= 0) {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			// 获取set中第一项委托单类型
			String addtypeString = ((EmCommissionOutChangeModel) set.iterator()
					.next().getValue()).getEcoc_addtype();

			// 按照委托单类型筛选数据，并装入excelList中
			List<EmCommissionOutChangeModel> excelList = new ListModelList<>();
			for (Listitem lt : set) {
				EmCommissionOutChangeModel m1 = lt.getValue();
				if (m1.getEcoc_addtype().equals(addtypeString)) {
					excelList.add(m1);
				}
			}

			// 按照set中第一个类型导出
			if (addtypeString.equals("新增")) {
				Excel_Add(excelList);
			}
		}
	}

	/**
	 * 新增导出Excel
	 * 
	 */
	public void Excel_Add(List<EmCommissionOutChangeModel> excelList) {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		excel: {

			if (excelList.size() == 1) {

				EmCommissionOutChangeModel m = excelList.get(0);
				try {
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_excel.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						break excel;
					}

					// 读取Excel模板
					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_out_excel.xls", this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					// 开始写入内容
					try {
						Label label = null;
						CellFormat cf = null;
						WritableCellFormat wcf = null;

						// 获取字体样式
						cf = sheet.getCell(0, 1).getCellFormat();
						wcf = new WritableCellFormat(cf);
						// 委托单类型
						label = new Label(0, 1, "深圳中智委托单("
								+ m.getEcoc_addtype() + ")", wcf);
						sheet.addCell(label);

						// 获取字体样式
						cf = sheet.getCell(0, 2).getCellFormat();
						wcf = new WritableCellFormat(cf);
						// 委托出日期
						label = new Label(1, 2, m.getEcoc_title_date(), wcf);
						sheet.addCell(label);
						// 算法
						label = new Label(5, 2, m.getSoin_title(), wcf);
						sheet.addCell(label);
						// 受托单位名称
						label = new Label(5, 3, m.getCoab_name(), wcf);
						sheet.addCell(label);
						// 委托单位项目负责人
						label = new Label(2, 4, m.getEcoc_client(), wcf);
						sheet.addCell(label);
						// 项目负责人联系电话
						label = new Label(2, 5, m.getEcoc_com_phone(), wcf);
						sheet.addCell(label);

						List<CoAgencyLinkmanModel> calList = new ListModelList<>();
						calList = bll.getLinkManList(m.getEcos_cabc_id());
						// 受托单位联系人
						label = new Label(5, 4, calList.get(0).getCali_name(),
								wcf);
						sheet.addCell(label);
						// 受托单位联系人电话
						label = new Label(5, 5, calList.get(0).getCali_tel(),
								wcf);
						sheet.addCell(label);
						// 受托单位传真号码
						label = new Label(5, 6, calList.get(0).getCali_fax(),
								wcf);
						sheet.addCell(label);

						// 雇员姓名
						label = new Label(1, 8, m.getEmba_name(), wcf);
						sheet.addCell(label);
						// 户籍
						label = new Label(3, 8, m.getEcoc_domicile(), wcf);
						sheet.addCell(label);
						// 身份证号
						label = new Label(5, 8, m.getEcoc_idcard(), wcf);
						sheet.addCell(label);
						// 工作单位
						label = new Label(1, 9, m.getCoba_company(), wcf);
						sheet.addCell(label);
						// 社保缴纳地
						label = new Label(5, 9, m.getCity(), wcf);
						sheet.addCell(label);
						// 工作电话
						label = new Label(1, 10, m.getEcoc_phone(), wcf);
						sheet.addCell(label);
						// 个人手机
						label = new Label(5, 10, m.getEcoc_mobile(), wcf);
						sheet.addCell(label);
						// Email
						label = new Label(1, 11, m.getEcoc_email(), wcf);
						sheet.addCell(label);
						// 社保基数
						label = new Label(4, 11, m.getEcoc_sb_base() + "", wcf);
						sheet.addCell(label);
						// 公积金基数
						label = new Label(6, 11, m.getEcoc_house_base() + "",
								wcf);
						sheet.addCell(label);

						// 获取字体样式
						cf = sheet.getCell(2, 13).getCellFormat();
						wcf = new WritableCellFormat(cf);
						// 服务项目小计
						BigDecimal fwsum = new BigDecimal(0);
						int row = 0;
						int row1 = 23;
						for (int i = 0; i < m.getFeeList().size(); i++) {
							EmCommissionOutFeeDetailChangeModel m1 = m
									.getFeeList().get(i);

							if (m1.getSicl_class().equals("社会保险")
									|| m1.getSicl_class().equals("公积金")) {
								switch (m1.getEofc_name()) {
								case "养老保险":
									row = 13;
									break;
								case "失业保险":
									row = 14;
									break;
								case "工伤保险":
									row = 15;
									break;
								case "医疗保险":
									row = 16;
									break;
								case "生育保险":
									row = 17;
									break;
								case "住房公积金":
									row = 18;
									break;
								case "补充公积金":
									row = 19;
									break;

								default:
									break;
								}

								// 缴费基数
								label = new Label(2, row, m1.getEofc_em_base()
										+ "", wcf);
								sheet.addCell(label);
								if (m1.getEofc_name().equals("补充公积金")) {
									// 企业比例、个人比例
									label = new Label(3, row, m1.getEofc_op(),
											wcf);
									sheet.addCell(label);
								} else {
									// 企业比例
									label = new Label(3, row, m1.getEofc_op(),
											wcf);
									sheet.addCell(label);
									// 个人比例
									label = new Label(4, row, m1.getEofc_cp(),
											wcf);
									sheet.addCell(label);
								}
								// 月缴费
								label = new Label(5, row,
										m1.getEofc_month_sum() + "", wcf);
								sheet.addCell(label);
								// 委托起始日
								label = new Label(6, row,
										m1.getEofc_start_date(), wcf);
								sheet.addCell(label);

								fwsum = fwsum.add(m1.getEofc_month_sum());
							} else if (m1.getSicl_class().equals("服务费")) {
								// 服务费
								label = new Label(6, 22, m1.getEofc_month_sum()
										+ "", wcf);
								sheet.addCell(label);
							} else if (m1.getSicl_class().equals("档案费")) {
							} else {
								sheet.insertRow(row1);
								sheet.mergeCells(1, row1, 5, row1);
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
										m1.getEofc_month_sum() + "", wcf);
								sheet.addCell(label);

								row1++;
							}
						}

						row1 += 2;

						// 小计
						label = new Label(2, 20, fwsum + "", wcf);
						sheet.addCell(label);

						// 社保合计
						label = new Label(0, row1, m.getEcoc_sb_sum() + "", wcf);
						sheet.addCell(label);
						// 档案费
						label = new Label(1, row1, m.getEcoc_file_fee() + "",
								wcf);
						sheet.addCell(label);
						// 福利合计
						label = new Label(3, row1,
								m.getEcoc_welfare_sum() + "", wcf);
						sheet.addCell(label);
						// 合计
						label = new Label(5, row1, m.getEcoc_sum() + "", wcf);
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
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
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
			} else if (excelList.size() > 1) {
				try {
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_all_excel.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						break excel;
					}

					// 读取Excel模板
					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_out_all_excel.xls",
													this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					WritableCellFormat wcf = null;
					Label label = null;
					int row = 12;
					int row1 = 35;

					for (int i = 0; i < excelList.size(); i++) {
						EmCommissionOutChangeModel m = excelList.get(i);

						if (m.getEcoc_addtype().equals("新增")) {

							// 插入一行
							sheet.insertRow(row);

							// 序号
							wcf = new WritableCellFormat(sheet.getCell(0,
									row + 1).getCellFormat());
							label = new Label(0, row, i + 1 + "", wcf);
							sheet.addCell(label);

							// 员工姓名
							wcf = new WritableCellFormat(sheet.getCell(1,
									row + 1).getCellFormat());
							label = new Label(1, row, m.getEmba_name(), wcf);
							sheet.addCell(label);

							// 身份证号码
							sheet.mergeCells(2, row, 3, row);
							wcf = new WritableCellFormat(sheet.getCell(2,
									row + 1).getCellFormat());
							label = new Label(2, row, m.getEcoc_idcard(), wcf);
							sheet.addCell(label);

							// 联系电话
							sheet.mergeCells(4, row, 5, row);
							wcf = new WritableCellFormat(sheet.getCell(4,
									row + 1).getCellFormat());
							label = new Label(4, row, m.getEcoc_phone(), wcf);
							sheet.addCell(label);

							// 户籍
							sheet.mergeCells(6, row, 7, row);
							wcf = new WritableCellFormat(sheet.getCell(6,
									row + 1).getCellFormat());
							label = new Label(6, row, m.getEcoc_domicile(), wcf);
							sheet.addCell(label);

							// 受托地
							wcf = new WritableCellFormat(sheet.getCell(8,
									row + 1).getCellFormat());
							label = new Label(8, row, m.getCity(), wcf);
							sheet.addCell(label);

							// 档案状态
							wcf = new WritableCellFormat(sheet.getCell(9,
									row + 1).getCellFormat());
							label = new Label(9, row, m.getEcoc_file_fee()
									.compareTo(BigDecimal.ZERO) == 0 ? "否"
									: "是", wcf);
							sheet.addCell(label);

							String sb_incept_date = "";
							String house_incept_date = "";
							for (EmCommissionOutFeeDetailChangeModel m1 : m
									.getFeeList()) {
								if (m1.getEofc_start_date() != null) {
									if (m1.getSicl_class().equals("社会保险")
											&& sb_incept_date.isEmpty()) {
										sb_incept_date = m1
												.getEofc_start_date();
									} else if (m1.getSicl_class().equals("公积金")
											&& house_incept_date.isEmpty()) {
										house_incept_date = m1
												.getEofc_start_date();
									}
								}
							}

							// 社保委托起始日
							sheet.mergeCells(10, row, 11, row);
							wcf = new WritableCellFormat(sheet.getCell(10,
									row + 1).getCellFormat());
							label = new Label(10, row, sb_incept_date, wcf);
							sheet.addCell(label);

							// 住房委托起始日
							sheet.mergeCells(12, row, 13, row);
							wcf = new WritableCellFormat(sheet.getCell(12,
									row + 1).getCellFormat());
							label = new Label(12, row, house_incept_date, wcf);
							sheet.addCell(label);

							// 插入一行
							sheet.insertRow(row1);

							// 序号
							wcf = new WritableCellFormat(sheet.getCell(0,
									row1 + 1).getCellFormat());
							label = new Label(0, row1, i + 1 + "", wcf);
							sheet.addCell(label);

							// 员工姓名
							wcf = new WritableCellFormat(sheet.getCell(1,
									row1 + 1).getCellFormat());
							label = new Label(1, row1, m.getEmba_name(), wcf);
							sheet.addCell(label);

							wcf = new WritableCellFormat(sheet.getCell(2,
									row1 + 1).getCellFormat());

							for (EmCommissionOutFeeDetailChangeModel m1 : m
									.getFeeList()) {
								switch (m1.getEofc_name()) {
								case "养老保险":
									label = new Label(2, row1,
											m1.getEofc_em_base() + "", wcf);
									sheet.addCell(label);
									break;
								case "失业保险":
									label = new Label(3, row1,
											m1.getEofc_em_base() + "", wcf);
									sheet.addCell(label);
									break;
								case "工伤保险":
									label = new Label(4, row1,
											m1.getEofc_em_base() + "", wcf);
									sheet.addCell(label);
									break;
								case "医疗保险":
									label = new Label(5, row1,
											m1.getEofc_em_base() + "", wcf);
									sheet.addCell(label);
									break;
								case "生育保险":
									label = new Label(6, row1,
											m1.getEofc_em_base() + "", wcf);
									sheet.addCell(label);
									break;
								case "住房公积金":
									label = new Label(7, row1,
											m1.getEofc_em_base() + "", wcf);
									sheet.addCell(label);
									label = new Label(8, row1, m1.getEofc_op(),
											wcf);
									sheet.addCell(label);
									label = new Label(9, row1, m1.getEofc_cp(),
											wcf);
									sheet.addCell(label);
									label = new Label(10, row1,
											m.getEcoc_gjj_sum() + "", wcf);
									sheet.addCell(label);
									break;
								default:
									break;
								}
							}
							// 补充福利
							label = new Label(11, row1, m.getEcoc_welfare_sum()
									+ "", wcf);
							sheet.addCell(label);
							// 服务费
							label = new Label(12, row1, m.getEcoc_service_fee()
									+ "", wcf);
							sheet.addCell(label);
							// 档案费
							label = new Label(13, row1, m.getEcoc_file_fee()
									+ "", wcf);
							sheet.addCell(label);
							// 其他
							label = new Label(14, row1, "", wcf);
							sheet.addCell(label);
							// 备注
							label = new Label(15, row1, m.getEcoc_remark(), wcf);
							sheet.addCell(label);

							row++;
							row1 += 2;
						}
					}
					sheet.removeRow(row);
					sheet.removeRow(row1 - 2);

					EmCommissionOutChangeModel m = excelList.get(0);

					// 获取委托机构联系人信息
					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(m.getEcos_cabc_id());

					// 委托机构
					wcf = new WritableCellFormat(sheet.getCell(1, 5)
							.getCellFormat());
					label = new Label(1, 6, m.getCoab_name(), wcf);
					sheet.addCell(label);

					// 联系人姓名
					label = new Label(6, 6, calList.get(0).getCali_name(), wcf);
					sheet.addCell(label);

					// 传真
					wcf = new WritableCellFormat(sheet.getCell(9, 5)
							.getCellFormat());
					label = new Label(9, 6, calList.get(0).getCali_fax(), wcf);
					sheet.addCell(label);

					// 公司名称
					wcf = new WritableCellFormat(sheet.getCell(0, 10)
							.getCellFormat());
					label = new Label(1, 10, m.getCoba_company(), wcf);
					sheet.addCell(label);

					// 委托出日期
					wcf = new WritableCellFormat(sheet.getCell(7, 10)
							.getCellFormat());
					label = new Label(8, 10, m.getEcoc_title_date(), wcf);
					sheet.addCell(label);

					row = row + 14;

					// 委托机构
					wcf = new WritableCellFormat(sheet.getCell(1, row - 1)
							.getCellFormat());
					label = new Label(1, row, m.getCoab_name(), wcf);
					sheet.addCell(label);

					// 联系人姓名
					label = new Label(6, row, calList.get(0).getCali_name(),
							wcf);
					sheet.addCell(label);

					// 传真
					wcf = new WritableCellFormat(sheet.getCell(9, row - 1)
							.getCellFormat());
					label = new Label(9, row, calList.get(0).getCali_fax(), wcf);
					sheet.addCell(label);

					// 公司名称
					wcf = new WritableCellFormat(sheet.getCell(0, row + 4)
							.getCellFormat());
					label = new Label(1, row + 4, m.getCoba_company(), wcf);
					sheet.addCell(label);

					row1 = row1 - 1;

					// 委托单位联系人
					wcf = new WritableCellFormat(sheet.getCell(0, row1)
							.getCellFormat());
					label = new Label(2, row1, calList.get(0).getCali_name(),
							wcf);
					sheet.addCell(label);

					// 电话
					wcf = new WritableCellFormat(sheet.getCell(9, row - 1)
							.getCellFormat());
					label = new Label(9, row1, calList.get(0).getCali_tel(),
							wcf);
					sheet.addCell(label);

					// 传真
					label = new Label(9, row1 + 1,
							calList.get(0).getCali_fax(), wcf);
					sheet.addCell(label);

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();

					try {
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
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
	}

	public List<EmCommissionOutChangeModel> getEcocList() {
		return ecocList;
	}

	public void setEcocList(List<EmCommissionOutChangeModel> ecocList) {
		this.ecocList = ecocList;
	}

	public List<EmCommissionOutChangeModel> getSecocList() {
		return secocList;
	}

	public void setSecocList(List<EmCommissionOutChangeModel> secocList) {
		this.secocList = secocList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CoAgencyBaseCityRelViewModel> cityList) {
		this.cityList = cityList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public List<EmCommissionOutChangeModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<EmCommissionOutChangeModel> clientList) {
		this.clientList = clientList;
	}

	public List<PubStateModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<PubStateModel> stateList) {
		this.stateList = stateList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoabname() {
		return coabname;
	}

	public void setCoabname(String coabname) {
		this.coabname = coabname;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public List<EmCommissionOutChangeModel> getCountList() {
		return countList;
	}

	public void setCountList(List<EmCommissionOutChangeModel> countList) {
		this.countList = countList;
	}
}
