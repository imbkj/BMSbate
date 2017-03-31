package Controller.EmHouseCard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
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
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoPolicyNoticeFileModel;
import Model.EmHouseTakeCardInfoModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class HuCard_TakeInfoAddController {
	// private String filename = "";
	private String cgjjno = "";
	private Date modate;
	private List<CoPolicyNoticeFileModel> flist = new ArrayList<CoPolicyNoticeFileModel>();
	private Media[] media;
	private String uploadfolder="EmHouseCard/uploadfile/";

	public HuCard_TakeInfoAddController() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		/*
		 * String monthstr = ""; if (month < 10) { monthstr = "0" + month; }
		 * else { monthstr = "" + month; }
		 */
		String om = year + "" + month;
		modate = strchangdate(om);
	}

	// 获取上传的政策文件
	@Command
	@NotifyChange("flist")
	public void upfile(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		// Media media = null;
		// media = ev.getMedia();
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		if (media != null) {
			for (int i = 0; i < media.length; i++) {
				if (media[i].getFormat().equals("xls")
						|| media[i].getFormat() == "xls"
						|| media[i].getFormat().equals("xlsx")
						|| media[i].getFormat() == "xlsx") {
					try {
						/*
						this.filename = media[i].getName();
						
						String filena = media[i].getName().substring(0,
								media[i].getName().length() - 4);
						String RelativePath = "EmHouseCard/uploadfile/";
						String name = filena
								+ "_"
								+ UserInfo.getUserid()
								+ "_"
								+ DateStringChange.DatetoSting(new Date(),
										"yyyyMMddhhmmss") + "."
								+ media[i].getFormat();
						*/
						String filename =mosaicFileName(media[i].getName());
						boolean flag = FileOperate.upload(media[i],
								uploadfolder + filename);
						if (flag) {
							// filepath.setValue(filename);
							CoPolicyNoticeFileModel m = new CoPolicyNoticeFileModel();
							m.setFile_title(media[i].getName());
							checkData(filename, m);
							m.setMedia(media[i]);
							// 检查数据
							flist.add(m);
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
	}

	@Command
	public void addTakeCardInfo(@BindingParam("win") Window addwins) {
		if (flist.size() <= 0) {
			Messagebox.show("请选择文件", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (modate == null) {
			Messagebox.show("请选择所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			int k = 0;
			for (CoPolicyNoticeFileModel m : flist) {
				if (m.getErrorMsg() == null || m.getErrorMsg().equals("")) {
					List<EmHouseTakeCardInfoModel> tlist = m.getTlist();
					k = k + UpLoadData(tlist);
				}
			}
			if (k > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				addwins.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	private int UpLoadData(List<EmHouseTakeCardInfoModel> tlist) {
		int k = 0;
		for (EmHouseTakeCardInfoModel m : tlist) {
			String[] s = new String[5];
			s = EmHuTakeCardAdd(m);
			if (s[0] == "1") {
				k = k + 1;
			}
		}
		return k;
	}

	@Command
	@NotifyChange("flist")
	public void delfile(@BindingParam("model") CoPolicyNoticeFileModel model) {
		flist.remove(model);
	}

	/**
	 * 读取office 2003 xls
	 * 
	 * @param filePath
	 */
	public void checkData(String filename, CoPolicyNoticeFileModel fm) {
		String own = changedate(modate);
		//String realPath = "EmHouseCard/uploadfile/";
		String name = FileOperate.getAbsolutePath() + uploadfolder + filename;
		EmHouse_TakeCardInfoSelectBll bl = new EmHouse_TakeCardInfoSelectBll();
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(name));
			// 创建对工作表的引用。
			// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
			HSSFSheet sheet = workbook.getSheet("p0");
			// 也可用getSheetAt(int index)按索引引用，
			// 在Excel文档中，第一张工作表的缺省索引是0，
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			// 读取左上端单元
			HSSFRow row;
			int u = 0;
			List<EmHouseTakeCardInfoModel> slist = new ArrayList<EmHouseTakeCardInfoModel>();
			String namestr = "";
			for (int i = sheet.getFirstRowNum(); i < sheet
					.getPhysicalNumberOfRows(); i++) {
				EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
				row = sheet.getRow(i);
				// 输出单元内容，cell.getStringCellValue()就是取所在单元的值
				if (i == 1) {
					String con[] = row.getCell(0).getStringCellValue()
							.split("：", 2);
					cgjjno = con[1];
					if (bl.ifExist(own, cgjjno)) {
						// 当领卡信息表已经有了填入的所属月份和单位公积金号的信息的时候就退出循环，因为一个单位公积金号一个月只能导入一次数据
						u = 1;
						break;// 退出对表格的循环
					}
				}

				else if (i > 5) {
					if (cgjjno != null && !cgjjno.equals("")) {
						String idcard = "", falseInfo = "", emhc_houseid = "", username = "", stateinfo = "", cardid = "", appinfo = "";
						if (row.getCell(0) != null) {
							row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
							emhc_houseid = row.getCell(0).getStringCellValue();
						}
						if (row.getCell(1) != null) {
							row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							username = row.getCell(1).getStringCellValue();
						}
						if (row.getCell(2) != null) {
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							idcard = row.getCell(2).getStringCellValue();
						}
						if (row.getCell(3) != null) {
							row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
							cardid = row.getCell(3).getStringCellValue();
						}
						if (row.getCell(4) != null) {
							row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							appinfo = row.getCell(4).getStringCellValue();
						}
						if (row.getCell(5) != null) {
							row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							stateinfo = row.getCell(5).getStringCellValue();
						}
						if (row.getCell(6) != null) {
							row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
							falseInfo = row.getCell(6).getStringCellValue();
						}

						m = getInfo(idcard, own, falseInfo, emhc_houseid,
								username, stateinfo, cardid, appinfo, cgjjno);
						m.setRe_cgjjno(cgjjno);
						if (m.getGid() != null && m.getGid() > 0) {
							slist.add(m);

						} else {
							if (namestr == "") {
								namestr = row.getCell(1).getStringCellValue();
							} else {
								namestr = namestr + "、"
										+ row.getCell(1).getStringCellValue();
							}
						}
					}
				}
			}
			if (u == 1) {
				fm.setErrorMsg("该公司这个月已经导入了领卡资料！");
			} else if (namestr == "") {
				fm.setTlist(slist);
			} else {
				fm.setErrorMsg(namestr + "等员工找不到公积金信息，该Excel所有数据都不能导入");
			}
			workbook.close();
		} catch (FileNotFoundException e) {
			fm.setErrorMsg("读取文件失败，请检查文件格式是否正确.");
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
			fm.setErrorMsg("读取文件失败，请检查文件格式是否正确.");
		}
	}

	private EmHouseTakeCardInfoModel getInfo(String idcard, String ownmonth,
			String falseInfo, String emhc_houseid, String username,
			String stateinfo, String cardid, String appinfo, String cgjjno) {
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		EmHouse_TakeCardInfoSelectBll bl2 = new EmHouse_TakeCardInfoSelectBll();
		// 判断制卡表当月是否有该员工的制卡信息
		m = bl2.getInfo(idcard, ownmonth, " and emhc_companyid='" + cgjjno
				+ "'");
		// 有制卡信息
		if (m.getGid() == null) {
			String sql = "";
			String ownmonthstr = "";
			// 先判断失败原因
			if (falseInfo.equals("注销不取") && falseInfo == "注销不取"
					&& falseInfo.equals("销卡不取") && falseInfo == "销卡不取") {
				ownmonthstr = ownmonthstr + " and ownmonth=" + ownmonth;
			}
			// 查询不包括停交的数据
			sql = "  and emhc_companyid='"
					+ cgjjno
					+ "'"
					+ " and Emhc_declareTime<>'' and (emhc_houseid='"
					+ emhc_houseid
					+ "' or emhc_idcard='"
					+ idcard
					+ "') and emhc_ifdeclare<>4 and emhc_ifdeclare<>5 and emhc_change<>'停交'"
					+ ownmonthstr;
			m = bl2.getEmhouseCahneInfo(sql);
			if (m.getGid() == null) {
				// 查询包括停交的数据
				sql = " and emhc_companyid='" + cgjjno
						+ "' and Emhc_declareTime<>'' and (emhc_houseid='"
						+ emhc_houseid + "' or emhc_idcard='" + idcard
						+ "') and emhc_ifdeclare<>4 and emhc_ifdeclare<>5 "
						+ ownmonthstr;
				m = bl2.getEmhouseCahneInfo(sql);
				if (m.getGid() == null) {
					sql = "  and emhc_companyid='" + cgjjno
							+ "' and (emhc_houseid='" + emhc_houseid
							+ "' or emhc_idcard='" + idcard
							+ "') and emhc_ifdeclare<>4 and emhc_ifdeclare<>5 "
							+ ownmonthstr;
					m = bl2.getEmhouseCahneInfo(sql);
				}
			}
		}
		m.setAddname(UserInfo.getUsername());
		m.setAddtime(datechange(new Date()));
		m.setUsername(username);
		m.setIdcard(idcard);
		m.setRe_type(getType(stateinfo));
		m.setRe_gjjno(emhc_houseid);
		m.setRe_cardid(cardid);
		m.setRe_applycase(appinfo);
		m.setRe_cardstate(stateinfo);
		m.setRe_failcase(falseInfo);
		EmHouseTakeCardInfoModel l = bl2
				.getStateId(" and state_type=2 and state_name='"
						+ m.getRe_type() + "'");
		if (l.getState_Id() != null) {
			m.setRe_state(l.getState_Id());
		} else {
			m.setRe_state(19);
		}
		m.setOwnmonth(ownmonth);
		return m;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date = time.format(d);
		return date;
	}

	// 判断领卡类型
	private String getType(String info) {
		String s = "";
		if (info.indexOf("失败") >= 0) {
			s = "失败不取";
		} else if (info.indexOf("销户") >= 0) {
			s = "注销不取";
		} else if (info.indexOf("销卡") >= 0 || info.indexOf("卡注销") >= 0) {
			s = "销卡不取";
		} else {
			s = "我司待领";
		}
		return s;
	}

	// 新增领卡信息方法
	private String[] EmHuTakeCardAdd(EmHouseTakeCardInfoModel model) {
		String[] s = new String[5];
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		s = obll.EmHuTakeCardAdd(model);
		return s;
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}

	private Date strchangdate(String d) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if (d != null && !d.equals("")) {
			try {
				if (d != null) {
					date = formatter.parse(d);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}

	// 拼接上传文件的名称
	private String mosaicFileName(String filename) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + filename;
		return name;
	}

	public Date getModate() {
		return modate;
	}

	public void setModate(Date modate) {
		this.modate = modate;
	}

	public List<CoPolicyNoticeFileModel> getFlist() {
		return flist;
	}

	public void setFlist(List<CoPolicyNoticeFileModel> flist) {
		this.flist = flist;
	}

}
