package Controller.Batchprocessing;

import impl.WorkflowCore.WfOperateImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.Batchprocessing.EmHouse_BathOperateBll;
import bll.Batchprocessing.EmHouse_BathSelectBll;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditImpl;

import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmHouseUploadModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.FileOperate;
import Util.SocialInsuranceCalculator;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class BatP_HouseController {
	private List<EmHouseChangeModel> changelist = new ArrayList<EmHouseChangeModel>();
	private List<EmHouseUploadModel> list = new ListModelList<>();
	private String type = "";
	private String uploadFlieName = "";
	private String uploadfolder = "/OfficeFile/UpLoad/EmHouse/";
	private String absolutePath = "";
	private Media[] media;
	private InputStream inputStream = null;
	private EmHouse_BathSelectBll bll = new EmHouse_BathSelectBll();
	private String sql = "";
	private List<EmHouseUploadModel> seList = new ListModelList<>();
	private String hsup_batpNumber = "";
	private String searchstate;
	private Integer ownmonth;
	private String m_cid = "";
	private String m_gid = "";
	private String m_company = "";
	private String m_name = "";
	private String m_idcard = "";
	private String m_radix = "";
	private String m_cpp = "";

	public BatP_HouseController() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String ownstr = "";
		if (month < 10) {
			ownstr = year + "0" + month;
		} else {
			ownstr = year + "" + month;
		}
		ownmonth = Integer.parseInt(ownstr);
	}

	@Command
	@NotifyChange("list")
	public void changestate() {
		String sqls = "";
		if (searchstate != null && !searchstate.equals("")) {

			if (searchstate.equals("未提交")) {
				sqls = " and hsup_state=0";
			} else if (searchstate.equals("已提交")) {
				sqls = " and hsup_state=1";
			}
		}

		if (!m_cid.equals("")) {
			sqls += " and a.cid =" + m_cid;
		}
		if (!m_gid.equals("")) {
			sqls += " and gid =" + m_gid;
		}
		if (!m_company.equals("")) {
			sqls += " and coba_company like '%" + m_company + "%'";
		}
		if (!m_name.equals("")) {
			sqls += " and hsup_name like '%" + m_name + "%'";
		}
		if (!m_idcard.equals("")) {
			sqls += " and hsup_idcard like '%" + m_idcard + "%'";
		}

		if (!m_radix.equals("")) {
			sqls += " and hsup_radix=" + m_radix;
		}
		if (!m_cpp.equals("")) {
			sqls += " and hsup_cpp=" + m_cpp;
		}
		if (!type.equals("")) {
			sqls += " and hsup_type="
					+ (type.equals("新增") ? 1 : type.equals("调入") ? 2 : type
							.equals("年度调基") ? 3 : type.equals("独立户接管") ? 4 : 0);
		}
		seList.clear();
		list = bll.getEmHouseUploadList(sqls + sql);

	}

	@Command
	@NotifyChange("list")
	public void yearChange() {
		hsup_batpNumber = bll.getLastBatchNumber(type);
		list = bll.getEmHouseUploadList(" and hsup_batchnumber='"
				+ hsup_batpNumber + "' and hsup_change='" + type + "'");
	}

	// 文件检查
	@Command("browse")
	@NotifyChange({ "uploadFlieName", "list" })
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		if (type == null || type.equals("")) {
			Messagebox.show("请选择操作类型", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
			if (changelist.size() > 0) {
				changelist.clear();
			}
			media = upEvent.getMedias();
			inputStream = null;
			for (int i = 0; i < media.length; i++) {
				if ("xls".equals(media[i].getFormat())
						|| "xlsx".equals(media[i].getFormat())) {
					uploadFlieName = media[i].getName();
					absolutePath = FileOperate.getAbsolutePath();
					String uploadName = mosaicFileName();
					if (FileOperate.upload(media[i], uploadfolder + uploadName)) {
						this.inputStream = media[i].getStreamData();
						if (type.equals("新增")) {
							UpLoadNewAdd();
						} else if (type.equals("调入")) {
							UpLoadMovein();
						} else if (type.equals("年度调基")) {
							UpLoadSalery();
							/*
							 * inputStream = null; Messagebox.show("该功能还没有开放",
							 * "提示", Messagebox.OK, Messagebox.ERROR);
							 */
						} else if (type.equals("独立户接管")) {
							UpLoadTakeOver();
						}
					}
				} else {
					media = null;
					uploadFlieName = "";
					Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					return;
				}
			}
		}
	}

	@Command
	public void checkall(@BindingParam("cb") Checkbox cb,
			@BindingParam("gd") Grid gd) {
		/*
		 * int num = gd.getPageSize(); if (gd.getRows().getChildren().size() <
		 * num) { num = gd.getRows().getChildren().size(); } for (int i = 0; i <
		 * num; i++) { if (gd.getCell(i, 0) != null) { org.zkoss.zul.Cell cell =
		 * (org.zkoss.zul.Cell) gd.getCell(i, 0); if (cell.getChildren().size()
		 * > 0 && cell.getChildren().get(0) != null) { Checkbox ck = (Checkbox)
		 * cell.getChildren().get(0); ck.setChecked(cb.isChecked()); } } }
		 */
		Integer num = gd.getPageSize();
		Integer size = list.size();
		if (size < num) {
			for (EmHouseUploadModel m : list) {
				if (m.getHsup_state().equals(0)) {
					m.setChecked(cb.isChecked());
					BindUtils.postNotifyChange(null, null, m, "checked");
				}

			}
		} else {
			Integer start = gd.getActivePage() * gd.getPageSize() + 1;
			Integer end = (gd.getActivePage() + 1) * gd.getPageSize();
			if (size < end) {
				end = size;
			}
			for (int i = start; i < end; i++) {
				if (list.get(i).getHsup_state().equals(0)) {
					list.get(i).setChecked(cb.isChecked());
					BindUtils.postNotifyChange(null, null, list.get(i),
							"checked");
				}
			}
		}
	}

	private void UpLoadNewAdd() {
		if (inputStream != null) {

			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook;
			try {
				workbook = new HSSFWorkbook(inputStream);
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 其语句为：
				// 读取左上端单元
				HSSFRow row;
				for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					if (row != null) {
						String gid = "";
						String ownmonthstr = "";
						String name = "";
						String idcard = "";
						String radix = "";
						String cp = "";
						String op = "";
						String hj = "";
						String degree = "";
						String title = "";
						String mobile = "";
						String wifename = "";
						String wifeIdcard = "";
						if (row.getCell(0) != null) {
							row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(0).getStringCellValue() != null) {
								ownmonthstr = row.getCell(0)
										.getStringCellValue().toString();
							}
						}
						if (row.getCell(1) != null) {
							row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(1).getStringCellValue() != null) {
								gid = row.getCell(1).getStringCellValue()
										.toString();
							}
						}

						if (row.getCell(2) != null) {
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(2).getStringCellValue() != null) {
								name = row.getCell(2).getStringCellValue()
										.toString();
							}
						}

						if (row.getCell(3) != null) {
							row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(3).getStringCellValue() != null) {
								idcard = row.getCell(3).getStringCellValue()
										.toString();
							}
						}

						if (row.getCell(4) != null) {
							row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(4).getStringCellValue() != null) {
								radix = row.getCell(4).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(5) != null) {
							row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(5).getStringCellValue() != null) {
								cp = row.getCell(5).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(6) != null) {
							row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(6).getStringCellValue() != null) {
								op = row.getCell(6).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(7) != null) {
							row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(7).getStringCellValue() != null) {
								hj = row.getCell(7).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(8) != null) {
							row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(8).getStringCellValue() != null) {
								degree = row.getCell(8).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(9) != null) {
							row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(9).getStringCellValue() != null) {
								title = row.getCell(9).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(10) != null) {
							row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(10).getStringCellValue() != null) {
								mobile = row.getCell(10).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(11) != null) {
							row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(11).getStringCellValue() != null) {
								wifename = row.getCell(11).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(12) != null) {
							row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
							if (row.getCell(12).getStringCellValue() != null) {
								wifeIdcard = row.getCell(12)
										.getStringCellValue().toString();
							}
						}
						if (gid != null && !gid.equals("")) {
							EmHouseChangeModel m = new EmHouseChangeModel();
							m.setGid(Integer.parseInt(gid));
							if (radix != null && !radix.equals("")) {
								m.setEmhc_trueradix(Double.valueOf(radix));
								if (cp != null) {
									Integer cpp = Integer.parseInt(cp);
									Double emhc_cpp = Math.round(cpp) / 100.0;
									BigDecimal emhc_radix = getEmhu_radix(
											radix, emhc_cpp.toString());
									m.setEmhc_radix(emhc_radix.doubleValue());
								}
							}
							if (cp != null && !cp.equals("")) {
								Integer cpp = Integer.parseInt(cp);
								Double emhc_cpp = Math.round(cpp) / 100.0;
								m.setEmhc_cpp(emhc_cpp);
							}
							if (op != null && !op.equals("")) {
								Integer opp = Integer.parseInt(op);
								Double emhc_opp = Math.round(opp) / 100.0;
								m.setEmhc_opp(Double.valueOf(emhc_opp));
							}
							if (hj != null) {
								String emhc_hj = "";
								if (hj.equals("01")) {
									emhc_hj = "深户";
								} else if (hj.equals("02")) {
									emhc_hj = "非深户城镇";
								} else if (hj.equals("03")) {
									emhc_hj = "非深户农村";
								} else if (hj.equals("04")) {
									emhc_hj = "其他";
								} else {
									m.setErrorMsg("户籍不能为空");
								}
								m.setEmhc_hj(emhc_hj);
							} else {
								m.setErrorMsg("户籍不能为空");
							}
							if (degree != null) {
								String emhc_degree = "";
								if (degree.equals("01")) {
									emhc_degree = "博士学位";
								} else if (degree.equals("02")) {
									emhc_degree = "硕士学位";
								} else if (degree.equals("03")) {
									emhc_degree = "学士学位";
								} else if (degree.equals("04")) {
									emhc_degree = "其他";
								} else {
									m.setErrorMsg("学历不能为空");
								}
								m.setEmhc_degree(emhc_degree);
							} else {
								m.setErrorMsg("学历不能为空");
							}
							if (title != null) {
								String emhc_title = "";
								if (title.equals("010")) {
									emhc_title = "正高职称";
								} else if (title.equals("020")) {
									emhc_title = "副高职称";
								} else if (title.equals("030")) {
									emhc_title = "中级职称";
								} else if (title.equals("040")) {
									emhc_title = "初级职称";
								} else if (title.equals("050")) {
									emhc_title = "无";
								} else {
									m.setErrorMsg("职称不能为空");
								}
								m.setEmhc_title(emhc_title);
							} else {
								m.setErrorMsg("职称不能为空");
							}
							if (mobile == null || mobile.equals("")) {
								m.setErrorMsg("电话号码不能为空");
							}
							if (name == null || name.equals("")) {
								m.setErrorMsg("姓名不能为空");
							}

							if (idcard == null || idcard.equals("")) {
								m.setErrorMsg("身份证号码不能为空");
							}

							if (radix == null || radix.equals("")) {
								m.setErrorMsg("基数不能为空");
							}

							if (wifename != null && !wifename.equals("")) {
								if (wifeIdcard == null || wifeIdcard.equals("")) {
									m.setErrorMsg("配偶身份证不能为空");
								}
							}
							m.setEmhc_name(name);
							m.setEmhc_idcard(idcard);
							m.setEmhc_mobile(mobile);
							m.setEmhc_wifename(wifename);
							m.setEmhc_wifeidcard(wifeIdcard);
							if (ownmonthstr != null && !ownmonthstr.equals("")) {
								m.setOwnmonth(Integer.parseInt(ownmonthstr));
							} else {
								m.setErrorMsg("所属月份不能为空");
							}

							m = bll.getEmbaInfo(m, 1);

							m.setEmhc_change(type);
							if (type.equals("新增")) {
								m.setEmhc_ifprogress(11);
								m.setEmhc_type(1);
							} else if (type.equals("调入")) {
								m.setEmhc_ifprogress(21);
								m.setEmhc_type(2);
							} else if (type.equals("年度调基")) {
								m.setEmhc_ifprogress(41);
								m.setEmhc_type(3);
							}
							if (m.getOwnmonth() < ownmonth) {
								m.setErrorMsg("所属月份不能小于当前月");
							} else if (m.getOwnmonth().equals(ownmonth)) {
								if (ifStop(m.getGid(), m.getCid(),
										m.getOwnmonth())) {
									if (m.getErrorMsg() == null
											|| m.getErrorMsg().equals("")) {
										m.setErrorMsg("已过截单日，请修改所属月份");
									}
								}
							}
							changelist.add(m);
						}
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void UpLoadMovein() {
		if (inputStream != null) {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook;
			try {
				workbook = new HSSFWorkbook(inputStream);
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 其语句为：
				// 读取左上端单元
				HSSFRow row;
				for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					String gid = "";
					String ownmonthstr = "";
					String name = "";
					String idcard = "";
					String radix = "";
					String cp = "";
					String op = "";
					String houseid = "";
					String excompanyid = "";
					String excompany = "";
					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(0).getStringCellValue() != null) {
							ownmonthstr = row.getCell(0).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(1).getStringCellValue() != null) {
							gid = row.getCell(1).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(2) != null) {
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(2).getStringCellValue() != null) {
							name = row.getCell(2).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(3) != null) {
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(3).getStringCellValue() != null) {
							idcard = row.getCell(3).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(4) != null) {
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(4).getStringCellValue() != null) {
							radix = row.getCell(4).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(5) != null) {
						row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(5).getStringCellValue() != null) {
							cp = row.getCell(5).getStringCellValue().toString();
						}
					}
					if (row.getCell(6) != null) {
						row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(6).getStringCellValue() != null) {
							op = row.getCell(6).getStringCellValue().toString();
						}
					}
					if (row.getCell(7) != null) {
						row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(7).getStringCellValue() != null) {
							houseid = row.getCell(7).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(8) != null) {
						row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(8).getStringCellValue() != null) {
							excompanyid = row.getCell(8).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(9) != null) {
						row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(9).getStringCellValue() != null) {
							excompany = row.getCell(9).getStringCellValue()
									.toString();
						}
					}

					if (gid != null && !gid.equals("")) {
						EmHouseChangeModel m = new EmHouseChangeModel();
						m.setGid(Integer.parseInt(gid));
						if (radix != null && !radix.equals("")) {
							m.setEmhc_trueradix(Double.valueOf(radix));
							if (cp != null) {
								Integer cpp = Integer.parseInt(cp);
								Double emhc_cpp = Math.round(cpp) / 100.0;
								BigDecimal emhc_radix = getEmhu_radix(radix,
										emhc_cpp.toString());
								m.setEmhc_radix(emhc_radix.doubleValue());
							}
						}
						if (cp != null && !cp.equals("")) {
							Integer cpp = Integer.parseInt(cp);
							Double emhc_cpp = Math.round(cpp) / 100.0;
							m.setEmhc_cpp(emhc_cpp);
						}
						if (op != null && !op.equals("")) {
							Integer opp = Integer.parseInt(op);
							Double emhc_opp = Math.round(opp) / 100.0;
							m.setEmhc_opp(Double.valueOf(emhc_opp));
						}
						m.setEmhc_name(name);
						m.setEmhc_idcard(idcard);
						if (ownmonthstr != null && !ownmonthstr.equals("")) {
							m.setOwnmonth(Integer.parseInt(ownmonthstr));
						} else {
							changelist.add(m);
							m.setErrorMsg("所属月份不能为空");
							continue;
						}
						m = bll.getEmbaInfo(m, 2);

						m.setEmhc_change(type);
						if (type.equals("新增")) {

							m.setEmhc_ifprogress(11);
							m.setEmhc_type(1);
						} else if (type.equals("调入")) {
							m.setEmhc_ifprogress(21);
							m.setEmhc_type(2);
						} else if (type.equals("年度调基")) {
							m.setEmhc_ifprogress(41);
							m.setEmhc_type(3);
						}
						m.setEmhc_houseid(houseid);
						if (excompanyid == null) {
							excompanyid = "";
						}
						if (excompany == null) {
							excompany = "";
						}
						m.setEmhc_excompanyid(excompanyid);
						m.setEmhc_excompany(excompany);
						if (m.getEmhc_houseid() != null
								&& !m.getEmhc_houseid().equals("")) {
							m.setEmhc_houseid(m.getEmhc_houseid().trim());
							if (m.getEmhc_houseid().length() != 11) {
								m.setErrorMsg("公积金号异常");
							}
						} else {
							m.setErrorMsg("个人公积金号不能为空");
						}
						if (m.getEmhc_trueradix() == null) {
							m.setErrorMsg("缴存基数不能为空");
						}
						if (m.getEmhc_opp() == null || m.getEmhc_cpp() == null) {
							m.setErrorMsg("缴存比例不能为空");
						}
						if (m.getOwnmonth() < ownmonth) {
							m.setErrorMsg("所属月份不能小于当前月");
						} else if (m.getOwnmonth().equals(ownmonth)) {
							if (ifStop(m.getGid(), m.getCid(), m.getOwnmonth())) {
								if (m.getErrorMsg() == null
										|| m.getErrorMsg().equals("")) {
									m.setErrorMsg("已过截单日，请修改所属月份");
								}
							}
						}
						changelist.add(m);
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void UpLoadSalery() {
		if (inputStream != null) {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook;
			try {
				workbook = new HSSFWorkbook(inputStream);
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 其语句为：
				// 读取左上端单元
				HSSFRow row;
				for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					String gid = "";
					String name = "";
					String idcard = "";
					String radix = "";
					String cp = "";

					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(0).getStringCellValue() != null) {
							gid = row.getCell(0).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(1).getStringCellValue() != null) {
							name = row.getCell(1).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(2) != null) {
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(2).getStringCellValue() != null) {
							idcard = row.getCell(2).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(3) != null) {
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(3).getStringCellValue() != null) {
							radix = row.getCell(3).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(4) != null) {
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(4).getStringCellValue() != null) {
							cp = row.getCell(4).getStringCellValue().toString();
						}
					}

					if (gid != null && !gid.equals("")) {
						EmHouseChangeModel m = new EmHouseChangeModel();
						m.setGid(Integer.parseInt(gid));
						if (name != null && !name.equals("")) {
							m.setEmhc_name(name);
						} else {
							if (!UserInfo.getDepID().equals("6")) {
								m.setErrorMsg("姓名不能为空");
								continue;
							}
						}
						if (idcard != null && !idcard.equals("")) {
							m.setEmhc_idcard(idcard);
						} else {
							if (!UserInfo.getDepID().equals("6")) {
								m.setErrorMsg("身份证不能为空");
								continue;
							}
						}
						if (radix != null && !radix.equals("")) {
							m.setEmhc_trueradix(Double.valueOf(radix));
							if (cp != null && !cp.equals("")) {
								Integer cpp = Integer.parseInt(cp);
								Double emhc_cpp = Math.round(cpp) / 100.0;
								BigDecimal emhc_radix = getEmhu_radix(radix,
										emhc_cpp.toString());
								m.setEmhc_radix(emhc_radix.doubleValue());
							}
						}
						if (cp != null && !cp.equals("")) {
							Integer cpp = Integer.parseInt(cp);
							Double emhc_cpp = Math.round(cpp) / 100.0;
							m.setEmhc_cpp(emhc_cpp);
							m.setEmhc_opp(emhc_cpp);
						} else {
							m.setErrorMsg("比例不能为空");
							continue;
						}

						m.setOwnmonth(ownmonth);
						m.setEmhc_change(type);
						m.setEmhc_type(3);
						m.setEmhc_ifprogress(41);
						m = bll.getgjjinfo(m);

						if (m.getEmhc_houseid() != null
								&& !m.getEmhc_houseid().equals("")) {
							m.setEmhc_houseid(m.getEmhc_houseid().trim());
							if (m.getEmhc_houseid().length() != 11) {
								m.setErrorMsg("公积金号异常");
							}
						} else {
							m.setErrorMsg("个人公积金号不能为空");
						}
						if (m.getEmhc_trueradix() == null) {
							m.setErrorMsg("缴存基数不能为空");
						}
						if (m.getEmhc_opp() == null || m.getEmhc_cpp() == null) {
							m.setErrorMsg("缴存比例不能为空");
						}
						if (m.getOwnmonth() == null
								|| m.getOwnmonth().equals("")) {
							changelist.add(m);
							m.setErrorMsg("所属月份不能为空");
							// continue;
						}

						if (m.getOwnmonth() == null || m.getCid() == null) {
							m.setErrorMsg("未找到在册数据.");
							// continue;
						} else if (ifStop(m.getGid(), m.getCid(),
								m.getOwnmonth())) {
							if (m.getErrorMsg() == null
									|| m.getErrorMsg().equals("")) {
								m.setErrorMsg("已过截单日，请修改所属月份");
							}
						}

						changelist.add(m);
					}
				}
				list = null;
				// System.out.print(changelist.size());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void UpLoadTakeOver() {
		if (inputStream != null) {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook;
			try {
				workbook = new HSSFWorkbook(inputStream);
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 其语句为：
				// 读取左上端单元
				HSSFRow row;
				for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					String gid = "";
					String ownmonthstr = "";
					String name = "";
					String idcard = "";
					String radix = "";
					String cp = "";
					String op = "";
					String houseid = "";
					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(0).getStringCellValue() != null) {
							ownmonthstr = row.getCell(0).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(1).getStringCellValue() != null) {
							gid = row.getCell(1).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(2) != null) {
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(2).getStringCellValue() != null) {
							name = row.getCell(2).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(3) != null) {
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(3).getStringCellValue() != null) {
							idcard = row.getCell(3).getStringCellValue()
									.toString();
						}
					}

					if (row.getCell(4) != null) {
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(4).getStringCellValue() != null) {
							radix = row.getCell(4).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(5) != null) {
						row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(5).getStringCellValue() != null) {
							cp = row.getCell(5).getStringCellValue().toString();
						}
					}
					if (row.getCell(6) != null) {
						row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(6).getStringCellValue() != null) {
							op = row.getCell(6).getStringCellValue().toString();
						}
					}
					if (row.getCell(7) != null) {
						row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(7).getStringCellValue() != null) {
							houseid = row.getCell(7).getStringCellValue()
									.toString();
						}
					}

					if (gid != null && !gid.equals("")) {
						EmHouseChangeModel m = new EmHouseChangeModel();
						m.setGid(Integer.parseInt(gid));
						if (radix != null && !radix.equals("")) {
							m.setEmhc_trueradix(Double.valueOf(radix));
							if (cp != null) {
								Integer cpp = Integer.parseInt(cp);
								Double emhc_cpp = Math.round(cpp) / 100.0;
								BigDecimal emhc_radix = getEmhu_radix(radix,
										emhc_cpp.toString());
								m.setEmhc_radix(emhc_radix.doubleValue());
							}
						}
						if (cp != null && !cp.equals("")) {
							Integer cpp = Integer.parseInt(cp);
							Double emhc_cpp = Math.round(cpp) / 100.0;
							m.setEmhc_cpp(emhc_cpp);
						}
						if (op != null && !op.equals("")) {
							Integer opp = Integer.parseInt(op);
							Double emhc_opp = Math.round(opp) / 100.0;
							m.setEmhc_opp(Double.valueOf(emhc_opp));
						}
						m.setEmhc_name(name);
						m.setEmhc_idcard(idcard);
						if (ownmonthstr != null && !ownmonthstr.equals("")) {
							m.setOwnmonth(Integer.parseInt(ownmonthstr));
						} else {
							changelist.add(m);
							m.setErrorMsg("所属月份不能为空");
							continue;
						}
						m = bll.getEmbaInfo(m, 2);

						m.setEmhc_change(type);

						if (type.equals("新增")) {
							m.setEmhc_ifprogress(11);
							m.setEmhc_type(1);
						} else if (type.equals("调入")) {
							m.setEmhc_ifprogress(21);
							m.setEmhc_type(2);
						} else if (type.equals("年度调基")) {
							m.setEmhc_ifprogress(41);
							m.setEmhc_type(3);
						} else if (type.equals("独立户接管")) {
							m.setEmhc_ifprogress(21);
							m.setEmhc_type(4);
						}
						m.setEmhc_houseid(houseid);
						if (m.getEmhc_houseid() != null
								&& !m.getEmhc_houseid().equals("")) {
							m.setEmhc_houseid(m.getEmhc_houseid().trim());
							if (m.getEmhc_houseid().length() != 11) {
								m.setErrorMsg("公积金号异常");
							}
						} else {
							m.setErrorMsg("个人公积金号不能为空");
						}
						if (m.getEmhc_trueradix() == null) {
							m.setErrorMsg("缴存基数不能为空");
						}
						if (m.getEmhc_opp() == null || m.getEmhc_cpp() == null) {
							m.setErrorMsg("缴存比例不能为空");
						}
						if (m.getOwnmonth() < ownmonth) {
							m.setErrorMsg("所属月份不能小于当前月");
						} else if (m.getOwnmonth().equals(ownmonth)) {
							if (ifStop(m.getGid(), m.getCid(), m.getOwnmonth())) {
								if (m.getErrorMsg() == null
										|| m.getErrorMsg().equals("")) {
									m.setErrorMsg("已过截单日，请修改所属月份");
								}
							}
						}
						changelist.add(m);
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + "(" + UserInfo.getUsername() + ")"
				+ uploadFlieName;
		return name;
	}

	// 下载模版
	@Command
	public void downloadTemplet() {
		if (type != null && !type.equals("")) {
			String filename = "";
			if (type.equals("新增")) {
				filename = "House.xls";
			} else if (type.equals("调入")) {
				filename = "House_Movein.xls";
			} else if (type.equals("年度调基")) {
				filename = "GJJ_Salary.xls";
			} else if (type.equals("独立户接管")) {
				filename = "House_takeover.xls";
			} else {
				Messagebox.show("没有该业务的模板", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (filename != null && !filename.equals("")) {
				String absolutePath = FileOperate.getAbsolutePath();
				String path = absolutePath + "OfficeFile/Templet/EmHouse/"
						+ filename;
				File file = new File(path);
				try {
					Filedownload.save(file, "xlsx");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Messagebox.show("请选择操作类型", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 导入数据
	@Command
	@NotifyChange("list")
	public void summitUpload() {
		if (inputStream == null) {
			Messagebox.show("请选择文件", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else if (changelist.size() <= 0) {
			Messagebox.show("没有找到有效数据.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		List<EmHouseChangeModel> failList = new ArrayList<EmHouseChangeModel>();
		EmHouse_BathOperateBll obll = new EmHouse_BathOperateBll();
		Integer k = 0;
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 生成批量号
		hsup_batpNumber = sdf.format(date);
		for (EmHouseChangeModel m : changelist) {
			m.setHsup_batchnumber(hsup_batpNumber);
			Integer kl = obll.EmHouseUpload(m);
			if (kl > 0) {
				k = k + 1;
				m.setMessage(true);
				m.setErrorMsg("导入成功");
			} else {
				m.setErrorMsg("导入失败");
				m.setMessage(false);
			}
		}
		if (k == changelist.size()) {
			Messagebox
					.show("导入成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else if (k == 0) {
			Messagebox.show("导入失败", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show("部分数据导入成功", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		if (changelist.size() > 0) {
			changelist.clear();
		}
		sql = " and hsup_batchnumber='" + hsup_batpNumber
				+ "' and hsup_change='" + type + "'";
		list = bll.getEmHouseUploadList(sql+" order by hsup_state");
	}

	// 提交数据到数据表
	@Command
	@NotifyChange("list")
	public void summit(@BindingParam("gd") Grid gd) {

		if (seList.size() > 0) {
			seList.clear();
		}
		for (EmHouseUploadModel m : list) {
			if (m.isChecked()) {
				if ((m.getHsup_errormsg() == null || m.getHsup_errormsg()
						.equals("")) && m.getHsup_state().equals(0)) {
					seList.add(m);
				} else {
					if (m.getHsup_state().equals(0)) {
						String s = "";
						s = m.getGid().toString();
						s += "," + m.getHsup_name();
						s += "数据异常,不允许提交.";
						Messagebox.show(s, "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
		}
		if (seList.size() <= 0) {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (type.equals("新增")) {
			List<EmHouseChangeModel> uplist = new ArrayList<EmHouseChangeModel>();
			for (EmHouseUploadModel model : seList) {
				EmHouseChangeModel m = new EmHouseChangeModel();
				if (model.getGid() != null) {
					m.setGid(model.getGid());
				}
				m.setEmhc_radix(model.getHsup_radix());

				m.setEmhc_trueradix(model.getHsup_trueradix());
				if (model.getHsup_cpp() != null) {
					m.setEmhc_cpp(model.getHsup_cpp());
				}
				if (model.getHsup_opp() != null) {
					m.setEmhc_opp(model.getHsup_opp());
				}
				m.setEmhc_hj(model.getHsup_hj());
				m.setEmhc_degree(model.getHsup_degree());
				m.setEmhc_title(model.getHsup_title());
				m.setEmhc_mobile(model.getHsup_mobile());
				m.setEmhc_wifename(model.getHsup_wifename());
				m.setEmhc_wifeidcard(model.getHsup_wifeidcard());
				m.setEmhc_name(model.getHsup_name());
				m.setEmhc_idcard(model.getHsup_idcard());
				m = bll.getEmbaInfo(m, 1);
				m.setOwnmonth(model.getOwnmonth());

				m.setEmhc_change(model.getHsup_change());
				m.setEmhc_ifprogress(model.getHsup_ifprogress());
				m.setEmhc_type(model.getHsup_type());

				m.setCid(model.getCid());
				m.setCoba_company(model.getCoba_company());

				m.setEmhc_companyid(model.getHsup_companyid());
				m.setEmhc_single(model.getHsup_single());
				m.setHsup_id(model.getHsup_id());
				if (model.getHsup_errormsg() == null
						|| model.getHsup_errormsg().equals("")) {
					uplist.add(m);
				}
			}

			EmHouse_BathOperateBll obll = new EmHouse_BathOperateBll();
			Integer k = 0;
			for (EmHouseChangeModel m : uplist) {
				if (m.getEmhc_companyid() != null && m.getEmhc_single() >= 0) {
					Integer kl = obll.EmHouseAdd(m);
					if (kl > 0) {
						obll.UpdateEmHouseUpload(m.getHsup_id());
						k = k + 1;
						m.setMessage(true);
						m.setErrorMsg("导入成功");
					} else {
						m.setErrorMsg("导入失败");
						m.setMessage(false);
					}
				}
			}
			if (k == seList.size()) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else if (k == 0) {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			} else {
				Messagebox.show("部分数据提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} else if (type.equals("调入")) {
			// Messagebox.show("该功能还没有开放", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			List<EmHouseChangeModel> uplist = new ArrayList<EmHouseChangeModel>();
			for (EmHouseUploadModel model : seList) {
				EmHouseChangeModel m = new EmHouseChangeModel();
				if (model.getGid() != null) {
					m.setGid(model.getGid());
				}
				m.setEmhc_radix(model.getHsup_radix());
				m.setEmhc_trueradix(model.getHsup_trueradix());

				if (model.getHsup_cpp() != null) {
					m.setEmhc_cpp(model.getHsup_cpp());
				}
				if (model.getHsup_opp() != null) {
					m.setEmhc_opp(model.getHsup_opp());
				}
				m.setEmhc_hj(model.getHsup_hj());
				m.setEmhc_degree(model.getHsup_degree());
				m.setEmhc_title(model.getHsup_title());
				m.setEmhc_mobile(model.getHsup_mobile());
				m.setEmhc_wifename(model.getHsup_wifename());
				m.setEmhc_wifeidcard(model.getHsup_wifeidcard());
				m.setOwnmonth(model.getOwnmonth());

				m.setEmhc_change(model.getHsup_change());
				m.setEmhc_ifprogress(model.getHsup_ifprogress());
				m.setEmhc_type(model.getHsup_type());

				m.setCid(model.getCid());
				m.setCoba_company(model.getCoba_company());
				m.setEmhc_name(model.getHsup_name());
				m.setEmhc_companyid(model.getHsup_companyid());
				m.setEmhc_single(model.getHsup_single());
				m.setHsup_id(model.getHsup_id());
				m.setEmhc_houseid(model.getHsup_houseid());

				if (model.getHsup_errormsg() == null
						|| model.getHsup_errormsg().equals("")) {
					uplist.add(m);
				}
			}

			EmHouse_BathOperateBll obll = new EmHouse_BathOperateBll();
			Integer k = 0;
			for (EmHouseChangeModel m : uplist) {
				if (m.getEmhc_houseid() != null
						&& !m.getEmhc_houseid().equals("")
						&& m.getEmhc_companyid() != null
						&& m.getEmhc_single() >= 0) {
					Integer kl = obll.EmHouseMoveIn(m);
					if (kl > 0) {
						obll.UpdateEmHouseUpload(m.getHsup_id());
						k = k + 1;
						m.setMessage(true);
						m.setErrorMsg("导入成功");
					} else {
						m.setErrorMsg("导入失败");
						m.setMessage(false);
					}
				}
			}
			if (k == seList.size()) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else if (k == 0) {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			} else {
				Messagebox.show("部分数据提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} else if (type.equals("年度调基")) {
			List<EmHouseChangeModel> uplist = new ArrayList<EmHouseChangeModel>();
			for (EmHouseUploadModel model : seList) {
				EmHouseChangeModel m = new EmHouseChangeModel();
				if (model.getGid() != null) {
					m.setGid(model.getGid());
				}
				m.setEmhc_radix(model.getHsup_radix());
				m.setEmhc_trueradix(model.getHsup_trueradix());

				if (model.getHsup_cpp() != null) {
					m.setEmhc_cpp(model.getHsup_cpp());
				}
				if (model.getHsup_opp() != null) {
					m.setEmhc_opp(model.getHsup_opp());
				}
				m.setEmhc_hj(model.getHsup_hj());
				m.setEmhc_degree(model.getHsup_degree());
				m.setEmhc_title(model.getHsup_title());
				m.setEmhc_mobile(model.getHsup_mobile());
				m.setEmhc_wifename(model.getHsup_wifename());
				m.setEmhc_wifeidcard(model.getHsup_wifeidcard());
				m.setOwnmonth(model.getOwnmonth());

				m.setEmhc_change(model.getHsup_change());
				m.setEmhc_ifprogress(model.getHsup_ifprogress());
				m.setEmhc_type(model.getHsup_type());

				m.setCid(model.getCid());
				m.setCoba_company(model.getCoba_company());
				m.setEmhc_name(model.getHsup_name());
				m.setEmhc_companyid(model.getHsup_companyid());
				m.setEmhc_single(model.getHsup_single());
				m.setHsup_id(model.getHsup_id());
				m.setEmhc_houseid(model.getHsup_houseid());

				if (model.getHsup_errormsg() == null
						|| model.getHsup_errormsg().equals("")) {
					uplist.add(m);
				}
			}

			EmHouse_BathOperateBll obll = new EmHouse_BathOperateBll();
			Integer k = 0;
			for (EmHouseChangeModel m : uplist) {
				if (m.getEmhc_houseid() != null
						&& !m.getEmhc_houseid().equals("")
						&& m.getEmhc_companyid() != null
						&& m.getEmhc_single() >= 0) {
					WfBusinessService wfbs = new EmHouse_EditImpl();
					WfOperateService wfs = new WfOperateImpl(wfbs);
					EmHouseUpdateModel em = new EmHouseUpdateModel();
					em.setOwnmonth(m.getOwnmonth());
					em.setEmhu_name(m.getEmhc_name());
					em.setCid(m.getCid());
					em.setGid(m.getGid());
					em.setEmhu_cpp(m.getEmhc_cpp());
					em.setEmhu_radix(m.getEmhc_radix());
					em.setReason("年度调基");
					Object[] obj = { "年度调基", em };
					String[] str = wfs.AddTaskToNext(
							obj,
							"员工公积金年调",
							em.getOwnmonth() + em.getEmhu_name() + "("
									+ em.getGid() + ")公积金年度调基", 122,
							UserInfo.getUsername(), "", em.getCid(), "");

					if (str[0].equals("1")) {
						obll.UpdateEmHouseUpload(m.getHsup_id());
						k = k + 1;
						m.setMessage(true);
						m.setErrorMsg("导入成功");
					} else {
						m.setErrorMsg("导入失败");
						m.setMessage(false);
					}

					// Integer kl = obll.EmHouseMoveIn(m);
					/*
					 * if (kl > 0) { obll.UpdateEmHouseUpload(m.getHsup_id()); k
					 * = k + 1; m.setMessage(true); m.setErrorMsg("导入成功"); }
					 * else { m.setErrorMsg("导入失败"); m.setMessage(false); }
					 */
				}
			}
			if (k == seList.size()) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else if (k == 0) {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			} else {
				Messagebox.show("部分数据提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} else if (type.equals("独立户接管")) {
			List<EmHouseChangeModel> uplist = new ArrayList<EmHouseChangeModel>();
			for (EmHouseUploadModel model : seList) {
				EmHouseChangeModel m = new EmHouseChangeModel();
				if (model.getGid() != null) {
					m.setGid(model.getGid());
				}
				m.setEmhc_radix(model.getHsup_radix());
				m.setEmhc_trueradix(model.getHsup_trueradix());
				if (model.getHsup_cpp() != null) {
					m.setEmhc_cpp(model.getHsup_cpp());
				}
				if (model.getHsup_opp() != null) {
					m.setEmhc_opp(model.getHsup_opp());
				}
				m.setEmhc_hj(model.getHsup_hj());
				m.setEmhc_degree(model.getHsup_degree());
				m.setEmhc_title(model.getHsup_title());
				m.setEmhc_mobile(model.getHsup_mobile());
				m.setEmhc_wifename(model.getHsup_wifename());
				m.setEmhc_wifeidcard(model.getHsup_wifeidcard());
				m.setOwnmonth(model.getOwnmonth());

				m.setEmhc_change(model.getHsup_change());
				m.setEmhc_ifprogress(model.getHsup_ifprogress());
				m.setEmhc_type(model.getHsup_type());

				m.setCid(model.getCid());
				m.setCoba_company(model.getCoba_company());
				m.setEmhc_name(model.getHsup_name());
				m.setEmhc_companyid(model.getHsup_companyid());
				m.setEmhc_single(model.getHsup_single());
				m.setHsup_id(model.getHsup_id());
				m.setEmhc_houseid(model.getHsup_houseid());

				if (model.getHsup_errormsg() == null
						|| model.getHsup_errormsg().equals("")) {
					uplist.add(m);
				}
			}

			EmHouse_BathOperateBll obll = new EmHouse_BathOperateBll();
			Integer k = 0;
			for (EmHouseChangeModel m : uplist) {
				if (m.getEmhc_houseid() != null
						&& !m.getEmhc_houseid().equals("")
						&& m.getEmhc_companyid() != null
						&& m.getEmhc_single() >= 0) {
					Integer kl = obll.EmHouseTakeOver(m);
					if (kl > 0) {
						obll.UpdateEmHouseUpload(m.getHsup_id());
						k = k + 1;
						m.setMessage(true);
						m.setErrorMsg("导入成功");
					} else {
						m.setErrorMsg("导入失败");
						m.setMessage(false);
					}
				}
			}
			if (k == seList.size()) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else if (k == 0) {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			} else {
				Messagebox.show("部分数据提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		}
		String sqls = " and hsup_batchnumber='" + hsup_batpNumber
				+ "' and hsup_change='" + type + "' and hsup_name=" + m_name;

		if (!m_cid.equals("")) {
			sqls += " and a.cid =" + m_cid;
		}
		if (!m_gid.equals("")) {
			sqls += " and gid =" + m_gid;
		}
		if (!m_company.equals("")) {
			sqls += " and coba_company like '%" + m_company + "%'";
		}
		if (!m_name.equals("")) {
			sqls += " and hsup_name like '%" + m_name + "%'";
		}
		if (!m_idcard.equals("")) {
			sqls += " and hsup_idcard like '%" + m_idcard + "%'";
		}

		if (!m_radix.equals("")) {
			sqls += " and hsup_radix=" + m_radix;
		}
		if (!m_cpp.equals("")) {
			sqls += " and hsup_cpp=" + m_cpp;
		}
		if (!type.equals("")) {
			sqls += " and hsup_type="
					+ (type.equals("新增") ? 1 : type.equals("调入") ? 2 : type
							.equals("年度调基") ? 3 : type.equals("独立户接管") ? 4 : 0);
		}
		sqls+=" order by hsup_state";
		list = bll.getEmHouseUploadList(sql + sqls);
	}

	@Command
	@NotifyChange("list")
	public void del(@BindingParam("gd") Grid gd) {
		getCheckList(gd);
		if (seList.size() <= 0) {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		EmHouse_BathOperateBll obll = new EmHouse_BathOperateBll();
		Integer k = 0;
		for (EmHouseUploadModel m : seList) {
			k = k + obll.delEmHouseUpload(m.getHsup_id());
		}
		if (k > 0) {
			Messagebox.show("删除成功.", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else {
			Messagebox.show("删除失败.", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		String sqls="";
		if (searchstate != null && !searchstate.equals("")) {

			if (searchstate.equals("未提交")) {
				sqls = " and hsup_state=0";
			} else if (searchstate.equals("已提交")) {
				sqls = " and hsup_state=1";
			}
		}
		if (!m_cid.equals("")) {
			sqls += " and a.cid =" + m_cid;
		}
		if (!m_gid.equals("")) {
			sqls += " and gid =" + m_gid;
		}
		if (!m_company.equals("")) {
			sqls += " and coba_company like '%" + m_company + "%'";
		}
		if (!m_name.equals("")) {
			sqls += " and hsup_name like '%" + m_name + "%'";
		}
		if (!m_idcard.equals("")) {
			sqls += " and hsup_idcard like '%" + m_idcard + "%'";
		}

		if (!m_radix.equals("")) {
			sqls += " and hsup_radix=" + m_radix;
		}
		if (!m_cpp.equals("")) {
			sqls += " and hsup_cpp=" + m_cpp;
		}
		if (!type.equals("")) {
			sqls += " and hsup_type="
					+ (type.equals("新增") ? 1 : type.equals("调入") ? 2 : type
							.equals("年度调基") ? 3 : type.equals("独立户接管") ? 4 : 0);
		}
		sqls+=" order by hsup_state";
		list = bll.getEmHouseUploadList(sql+sqls);
	}

	private void getCheckList(Grid gd) {
		if (seList.size() > 0) {
			seList.clear();
		}
		/*
		 * int num = gd.getPageSize(); if (gd.getRows().getChildren().size() <
		 * num) { num = gd.getRows().getChildren().size(); } for (int i = 0; i <
		 * num; i++) { if (gd.getCell(i, 0) != null) { org.zkoss.zul.Cell cell =
		 * (org.zkoss.zul.Cell) gd.getCell(i, 0); if (cell.getChildren().size()
		 * > 0 && cell.getChildren().get(0) != null) { Checkbox ck = (Checkbox)
		 * cell.getChildren().get(0); if (ck.isChecked()) { EmHouseUploadModel m
		 * = ck.getValue(); seList.add(m); } } } }
		 */
		for (EmHouseUploadModel m : list) {
			if (m.isChecked()) {
				seList.add(m);
			}
		}
	}

	public List<EmHouseUploadModel> getList() {
		return list;
	}

	public void setList(List<EmHouseUploadModel> list) {
		this.list = list;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSearchstate() {
		return searchstate;
	}

	public void setSearchstate(String searchstate) {
		this.searchstate = searchstate;
	}

	// 判断是否已过了截单日
	private boolean ifStop(int gid, int cid, Integer gjjtzownmont) {
		EmHouseSetBll sbll = new EmHouseSetBll();
		boolean ifStop = sbll.gjjOnair(cid, gid, gjjtzownmont, null); // 公积金接单日
		return ifStop;
	}

	// 通过真是基数计算emhu_radix
	private BigDecimal getEmhu_radix(String emhu_trueradix, String cpp) {
		SocialInsuranceCalculator sm = new SocialInsuranceCalculator();
		Integer id = sm.getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司");
		Date d = new Date();
		sm.setBcgjj(false);
		List<SocialInsuranceClassInfoViewModel> list = sm.getGjjItemFee(id,
				new BigDecimal(emhu_trueradix), new BigDecimal(cpp), d);
		BigDecimal radix = list.get(0).getRadix();
		radix = radix.setScale(2, BigDecimal.ROUND_HALF_UP);
		return radix;
	}

	public String getM_cid() {
		return m_cid;
	}

	public void setM_cid(String m_cid) {
		this.m_cid = m_cid;
	}

	public String getM_gid() {
		return m_gid;
	}

	public void setM_gid(String m_gid) {
		this.m_gid = m_gid;
	}

	public String getM_company() {
		return m_company;
	}

	public void setM_company(String m_company) {
		this.m_company = m_company;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_idcard() {
		return m_idcard;
	}

	public void setM_idcard(String m_idcard) {
		this.m_idcard = m_idcard;
	}

	public String getM_radix() {
		return m_radix;
	}

	public void setM_radix(String m_radix) {
		this.m_radix = m_radix;
	}

	public String getM_cpp() {
		return m_cpp;
	}

	public void setM_cpp(String m_cpp) {
		this.m_cpp = m_cpp;
	}

}
