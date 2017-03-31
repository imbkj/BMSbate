package Controller.EmBodyCheck;

import impl.WorkflowCore.WfOperateImpl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmBodyCheckModel;
import Model.TaskProcessViewModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBodyCheck.EmBcInfoImpl;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;

public class Embc_InsertEmbcIdController {
	private List<EmBodyCheckModel> list = new ArrayList<EmBodyCheckModel>();
	private String flags = (String) Executions.getCurrent().getArg()
			.get("flag");
	private String filename = "", name = "";
	private InputStream inputStream = null;
	private Media media;
	private String username = UserInfo.getUsername();
	private boolean visbtn = false;
	private EmBcInfo_SelectBll bll = new EmBcInfo_SelectBll();
	private EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();

	public Embc_InsertEmbcIdController() {
		if (username != null && username.equals("陈耀家")) {
			visbtn = true;
		}
	}

	// 获取上传的政策文件
	@Command
	@NotifyChange("list")
	public void uploadfile(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev,
			@BindingParam("win") Window win,
			@BindingParam("tb") Textbox filepath) {
		media = null;
		inputStream = null;
		media = ev.getMedia();
		name = "";
		if (list.size() > 0) {
			list.clear();
		}
		if (media != null) {
			if (media.getFormat().equals("xls") || media.getFormat() == "xls"
					|| media.getFormat().equals("xlsx")
					|| media.getFormat() == "xlsx") {
				try {
					this.filename = media.getName();
					this.inputStream = media.getStreamData();
					filepath.setValue(filename);
					String fname = UserInfo.getUserid()
							+ DateStringChange.DatetoSting(new Date(),
									"yyyyMMddhhmmss") + "." + media.getFormat();
					String realPath = "EmBodyCheck/uploadfile/";
					boolean flag = FileOperate.upload(media, realPath + fname);
					if (flag) {
						if (media.getFormat().equals("xls")
								|| media.getFormat() == "xls") {
							name = FileOperate.getAbsolutePath() + realPath
									+ fname;
							if (flags.equals("1"))// 导入保健号
							{
								loadXlsList(name);
							} else if (flags.equals("2"))// 导入签收名单
							{
								loadXlsList2(name);
							} else if (flags.equals("3"))// 导入结算费用数据
							{
								loadXlsList3(name);
							}
						} else {
							Messagebox.show("选择的文件格式不正确", "提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				} catch (Exception e) {
					System.out.println("错误:" + e.getMessage());
				}
			} else {
				Messagebox.show("选择的文件格式不正确", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	@Command
	@NotifyChange("list")
	public void summit(@BindingParam("win") Window win,
			@BindingParam("flag") String flag) {
		if (media.getFormat().equals("xls") || media.getFormat() == "xls") {
			if (flags != null) {
				if (flags.equals("1"))// 导入保健号
				{
					loadXls1(win, flag);
				} else if (flags.equals("2"))// 导入签收名单
				{
					loadXls2(name, win);
				} else if (flags.equals("3"))// 导入结算费用数据
				{
					loadXls3(win);
				}
			}
		}
	}

	/**
	 * 读取office 2003 xls //导入保健号
	 * 
	 * @param filePath
	 */
	public void loadXls1(Window win, String flag) {
		String[] s = new String[5];
		for (EmBodyCheckModel m : list) {
			if (m.getEmbc_id() != null && !m.getEmbc_id().equals("")
					&& m.getEbcl_id() != null && !m.getEbcl_id().equals("")) {
				s = obll.importBcId(m);
				if (s[0].equals("1")) {
					m.setEmbc_statebname("更新成功");
				} else {
					m.setEmbc_statebname("更新失败");
				}
			} else {
				m.setEmbc_statebname("无法匹配更新数据");
			}

		}
	}

	private void loadXlsList(String filepath) {
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					filepath));
			// 创建对工作表的引用。
			// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
			HSSFSheet sheet = workbook.getSheet("sheet1");
			// 也可用getSheetAt(int index)按索引引用，
			// 在Excel文档中，第一张工作表的缺省索引是0，
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			// 读取左上端单元
			HSSFRow row;
			List<EmBodyCheckModel> clist = new ListModelList<>();
			for (int i = sheet.getFirstRowNum(); i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if (i > 0) {
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						if (!row.getCell(1).getStringCellValue().equals("")) {

							String cid = "", embcid = "", embc_name = "", idcard = "";
							if (row.getCell(2) != null) {
								row.getCell(2).setCellType(
										Cell.CELL_TYPE_STRING);
								cid = row.getCell(2).getStringCellValue();
							}
							if (row.getCell(1) != null) {

								embcid = row.getCell(1).getStringCellValue();
							}
							if (row.getCell(3) != null) {
								row.getCell(3).setCellType(
										Cell.CELL_TYPE_STRING);
								embc_name = row.getCell(3).getStringCellValue();
							}
							if (row.getCell(4) != null) {
								row.getCell(4).setCellType(
										Cell.CELL_TYPE_STRING);
								idcard = row.getCell(4).getStringCellValue();
							}
							EmBodyCheckModel ml = new EmBodyCheckModel();
							ml.setEmbc_idcard(idcard);
							if (cid != null && !cid.equals("")) {
								ml.setCid(Integer.parseInt(cid));
							}
							ml.setEmbc_name(embc_name);
							ml.setEmbc_bcid(embcid);
							ml.setEmbc_statebname("未导入");
							clist = bll.getBCList(ml.getEmbc_idcard(),
									ml.getEmbc_bcid());
							if (clist.size() > 0) {
								ml.setEmbc_id(clist.get(0).getEmbc_id());
								ml.setEbcl_id(clist.get(0).getEbcl_id());
								ml.setEbcl_plandate(clist.get(0)
										.getEbcl_plandate());
								ml.setEmbc_tapr_id(clist.get(0)
										.getEmbc_tapr_id());
								if (ml.getEmbc_bcid().equals(
										clist.get(0).getEmbc_bcid())) {
									ml.setEmbc_statebname("保健号已存在");
								}
							} else {
								ml.setEmbc_statebname("未能匹配有效数据");
							}
							list.add(ml);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadXlsList2(String filepath) {
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					filepath));
			// 创建对工作表的引用。
			// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
			HSSFSheet sheet = workbook.getSheetAt(0);
			String sheetname = sheet.getSheetName();
			HSSFRow row;
			for (int i = sheet.getFirstRowNum(); i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if (i > 0) {
					if (row.getCell(4) != null) {
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						String idcard = row.getCell(4).getStringCellValue();
						String cid = "", embcid = "", embc_name = "";

						if (row.getCell(1) != null) {
							row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							embcid = row.getCell(1).getStringCellValue();
						}

						if (row.getCell(2) != null) {
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							cid = row.getCell(2).getStringCellValue();
						}

						if (row.getCell(3) != null) {
							row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
							embc_name = row.getCell(3).getStringCellValue();
						}

						Date showreportdate = null;
						if (row.getCell(5) != null) {
							// row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
							showreportdate = row.getCell(5).getDateCellValue();
						}

						EmBodyCheckModel ml = new EmBodyCheckModel();

						ml.setEmbc_idcard(idcard);
						if (cid != null && !cid.equals("")) {
							ml.setCid(Integer.parseInt(cid));
						}
						ml.setEbcl_showreportdate(changedate(showreportdate));
						ml.setEmbc_name(embc_name);
						ml.setEmbc_bcid(embcid);
						ml.setEmbc_statebname("未导入");
						if (idcard != null && !idcard.equals("")) {
							list.add(ml);
						}
					}

				}
			}
		} catch (Exception e) {

		}
	}

	private void loadXlsList3(String filepath) {
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					filepath));
			// 创建对工作表的引用。
			// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
			HSSFSheet sheet = workbook.getSheetAt(0);
			String sheetname = sheet.getSheetName();
			HSSFRow row;
			for (int i = sheet.getFirstRowNum(); i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				System.out.println(i);
				if (i > 0) {
					if (sheetname != null && sheetname.contains("入职")) {
						if (row.getCell(2) != null) {
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							String idcard = row.getCell(2).getStringCellValue();
							String completedate = "";
							String fee = null;
							String cid = "", embc_name = "";

							if (row.getCell(6) != null) {
								row.getCell(6).setCellType(
										Cell.CELL_TYPE_STRING);
								cid = row.getCell(6).getStringCellValue();
							}

							if (row.getCell(1) != null) {
								row.getCell(1).setCellType(
										Cell.CELL_TYPE_STRING);
								embc_name = row.getCell(1).getStringCellValue();
							}

							if (row.getCell(4) != null) {
								// row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
								completedate = row.getCell(4)// 体检时间
										.getStringCellValue();
							}
							if (row.getCell(5) != null) {
								row.getCell(5).setCellType(
										Cell.CELL_TYPE_STRING);
								fee = row.getCell(5)// 体检费用
										.getStringCellValue();
							}
							EmBodyCheckModel ml = new EmBodyCheckModel();
							ml.setEmbc_idcard(idcard);
							if (cid != null && !cid.equals("")) {
								ml.setCid(Integer.parseInt(cid));
							}
							ml.setEbcl_completedate(completedate);
							ml.setEbcl_charge(new BigDecimal(fee));
							ml.setEmbc_name(embc_name);
							ml.setEmbc_statebname("未导入");
							ml.setEbcl_typename("入职体检");
							list.add(ml);
						}
					} else if (sheetname != null && sheetname.contains("年度")) {
						if (row.getCell(4) != null) {
							row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							String embcid = row.getCell(4).getStringCellValue();
							String completedate = "";
							String cid = "", embc_name = "";
							String fee = "";
							if (row.getCell(7) != null) {
								row.getCell(7).setCellType(
										Cell.CELL_TYPE_STRING);
								cid = row.getCell(7).getStringCellValue();
							}

							if (row.getCell(1) != null) {
								row.getCell(1).setCellType(
										Cell.CELL_TYPE_STRING);
								embc_name = row.getCell(1).getStringCellValue();
							}
							EmBodyCheckModel ml = new EmBodyCheckModel();
							ml.setEmbc_bcid(embcid);
							if (cid != null && !cid.equals("")) {
								ml.setCid(Integer.parseInt(cid));
							}

							if (row.getCell(5) != null) {
								row.getCell(5).setCellType(
										Cell.CELL_TYPE_STRING);
								fee = row.getCell(5).getStringCellValue();
							}

							if (row.getCell(6) != null) {
								// row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
								completedate = row.getCell(6)// 体检时间
										.getStringCellValue();
							}

							ml.setEbcl_completedate(completedate);
							ml.setEbcl_charge(new BigDecimal(fee));
							ml.setEmbc_name(embc_name);
							ml.setEmbc_statebname("未导入");
							ml.setEbcl_typename("年度体检");
							list.add(ml);
						}

					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 读取office 2003 xls //导入签收名单
	 * 
	 * @param filePath
	 */
	public void loadXls2(String filepath, Window win) {
		try {

			int u = 0;
			EmArchive_SelectBll abll = new EmArchive_SelectBll();
			Integer k = 0;
			List<EmBodyCheckModel> faillist = new ArrayList<EmBodyCheckModel>();
			for (int i = 0; i < list.size(); i++) {
				EmBodyCheckModel ml = list.get(i);

				String idcard = ml.getEmbc_idcard();
				String cid = ml.getCid().toString();
				String showreportdate = ml.getEbcl_showreportdate();

				String sqlstr = " and embc_idcard='" + idcard
						+ "' and ebcl_showreportdate is null and ebcl_state=3";
				EmBodyCheckModel m = bll.isHasEmbcId(sqlstr);
				if (m.getEbcl_id() != null) {
					String sql = ",ebcl_showreportdate='" + showreportdate
							+ "',ebcl_showreportpeople='"
							+ UserInfo.getUsername() + "',ebcl_state=4";
					// k=k+obll.updateEmbodyChecklist(m.getEbcl_id(),sql);
					String[] str = obll.EmBodyCheckEdit(m, sql, "3");
					if (str[0].equals("1")) {
						k = k + 1;
						if (k > 0) {
							if (m.getEmbc_tapr_id() != null
									&& !m.getEmbc_tapr_id().equals(0)) {
								String strs[] = obll.EmBodyCheckEditAll(m, "",
										"2");
								TaskProcessViewModel tmodel = new TaskProcessViewModel();
								List<TaskProcessViewModel> tlist = abll
										.getLastId(m.getEmbc_tapr_id()
												.toString());
								if (tlist.size() > 0) {
									tmodel = tlist.get(0);
								}
								if (str[0].equals("1")) {
									if (tmodel.getWfno_step() == 5)// 跳过下一步（后道确认取消预约）
									{
										Integer tarpid = Integer
												.parseInt(str[2]);
										obll.EmBodyCheckSkip(m, tarpid);
									}
								}
							}
						}
					} else {
						ml.setEmbc_statebname("更新失败");
						faillist.add(ml);
						System.out.println("更新失败");
					}
				} else {
					String newsqlstr = " and embc_idcard='" + idcard
							+ "' and ebcl_showreportdate is not null";
					EmBodyCheckModel mm = bll.isHasEmbcId(newsqlstr);
					if (mm.getEbcl_id() == null) {
						ml.setEmbc_statebname("没有找到数据");
						faillist.add(ml);
					}
				}
			}
			Integer ok = 0;
			if (faillist.size() <= 0) {
				ok = 1;
			} else if (faillist.size() == list.size()) {
				ok = -1;
			}
			if (ok > 0) {
				Messagebox.show("导入成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else if (ok == -1) {
				if (list.size() > 0) {
					list.clear();
				}
				list = faillist;
				Messagebox.show("导入失败", "提示", Messagebox.OK, Messagebox.ERROR);
			} else {
				if (list.size() > 0) {
					list.clear();
				}
				list = faillist;
				Messagebox.show("部分数据导入成功", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 读取office 2003 xls //导入结算费用数据
	 * 
	 * @param filePath
	 */
	public void loadXls3(Window win) {
		try {
			int u = 0;

			EmArchive_SelectBll abll = new EmArchive_SelectBll();
			Integer k = 0;
			List<EmBodyCheckModel> faillist = new ArrayList<EmBodyCheckModel>();
			for (int i = 0; i < list.size(); i++) {
				EmBodyCheckModel ml = list.get(i);
				if (ml.getEbcl_typename() != null
						&& ml.getEbcl_typename().contains("入职")) {
					if (ml.getEmbc_idcard() != null) {
						String idcard = ml.getEmbc_idcard();
						String completedate = ml.getEbcl_completedate();
						String fee = ml.getEbcl_charge().toString();

						String sqlstr = " and embc_idcard='"
								+ idcard
								+ "' and '"
								+ completedate
								+ "' Between isnull(ebcl_plandate,ebcl_bookdate) And getdate()"
								+ " and ebcl_state=12 "
								+ " and ebcl_balancedate is null ";
						EmBodyCheckModel m = bll.isHasEmbcId(sqlstr);
						if (m.getEbcl_id() != null) {
							String sql = ",ebcl_state=11,ebcl_balancedate=getdate(),"
									+ "ebcl_finalcharge='" + fee + "'";
							String[] str = new String[5];
							if (m.getEmbc_tapr_id() != null
									&& !m.getEmbc_tapr_id().equals(0)) {
								str = obll.EmBodyCheckEdit(m, sql, "3");
							} else {
								Integer mlk = obll.updateEmbodyChecklist(
										m.getEbcl_id(), sql);
								if (mlk > 0) {
									str[0] = "1";
								}
							}
							if (str[0].equals("1")) {

								if (m.getEmbc_tapr_id() != null
										&& !m.getEmbc_tapr_id().equals(0)) {
									Object[] obj = {"6", m, sql };
									WfBusinessService wfbs = new EmBcInfoImpl();
									WfOperateService wfs = new WfOperateImpl(
											wfbs);
									wfs.SkipToN(obj, m.getEmbc_tapr_id(), 8,
											UserInfo.getUsername(), "", 0, "");

								}
							} else {
								ml.setEmbc_statebname("更新失败");
								faillist.add(ml);
								System.out.println("更新失败");
							}
						} else {
							String newsqlstr = " and ebcl_finalcharge is not null and embc_idcard='"
									+ idcard + "'";
							EmBodyCheckModel mm = bll.isHasEmbcId(newsqlstr);
							if (mm.getEbcl_id() == null) {
								ml.setEmbc_statebname("没有找到数据");
								faillist.add(ml);
							} else {
								ml.setEmbc_statebname("无法匹配");
								faillist.add(ml);
							}
						}
					}
				} else if (ml.getEbcl_typename().contains("年度")) {
					if (ml.getEmbc_bcid() != null) {
						String bcid = ml.getEmbc_bcid();
						String completedate = ml.getEbcl_completedate();
						String fee = ml.getEbcl_charge().toString();

						String sqlstr = " and ebcl_bcid='"
								+ bcid
								+ "' and '"
								+ completedate
								+ "' Between isnull(ebcl_plandate,ebcl_bookdate) And getdate()"
								+ " and ebcl_balancedate is null";
						EmBodyCheckModel m = bll.isHasEmbcId(sqlstr);
						if (m.getEbcl_id() != null) {
							String sql = ",ebcl_state=11,ebcl_balancedate=getdate(),"
									+ "ebcl_finalcharge='" + fee + "'";
							k = k
									+ obll.updateEmbodyChecklist(
											m.getEbcl_id(), sql);
							String[] str = obll.EmBodyCheckEdit(m, sql, "3");
							if (str[0].equals("1")) {

								if (m.getEmbc_tapr_id() != null
										&& !m.getEmbc_tapr_id().equals(0)) {
									Object[] obj = { m, sql };
									WfBusinessService wfbs = new EmBcInfoImpl();
									WfOperateService wfs = new WfOperateImpl(
											wfbs);
									wfs.SkipToN(obj, m.getEmbc_tapr_id(), 8,
											UserInfo.getUsername(), "", 0, "");

								}

							} else {
								ml.setEmbc_statebname("更新失败");
								faillist.add(ml);
								System.out.println("更新失败");
							}
						} else {
							String newsqlstr = " and ebcl_finalcharge is not null and ebcl_bcid='"
									+ bcid + "'";
							EmBodyCheckModel mm = bll.isHasEmbcId(newsqlstr);
							if (mm.getEbcl_id() == null) {
								ml.setEmbc_statebname("没有找到数据");
								faillist.add(ml);
							} else {
								ml.setEmbc_statebname("无法匹配");
								faillist.add(ml);
							}
						}
					}
				} else {
					Messagebox.show("导入文件的工作表名称不对，请把工作表名称改为年度体检或者入职", "提示",
							Messagebox.OK, Messagebox.INFORMATION);
				}
			}

			Integer ok = 0;
			if (faillist.size() <= 0) {
				ok = 1;
			} else if (faillist.size() == list.size()) {
				ok = -1;
			}
			if (ok > 0) {
				Messagebox.show("导入成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else if (ok == -1) {
				if (list.size() > 0) {
					list.clear();
				}
				list = faillist;
				Messagebox.show("导入失败", "提示", Messagebox.OK, Messagebox.ERROR);
			} else {
				if (list.size() > 0) {
					list.clear();
				}
				list = faillist;
				Messagebox.show("部分数据导入成功", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("list")
	public void cancel(@BindingParam("model") EmBodyCheckModel model) {
		if (model != null) {
			list.remove(model);
		}
	}

	public List<EmBodyCheckModel> getList() {
		return list;
	}

	public void setList(List<EmBodyCheckModel> list) {
		this.list = list;
	}

	public boolean isVisbtn() {
		return visbtn;
	}

	public void setVisbtn(boolean visbtn) {
		this.visbtn = visbtn;
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}
}
