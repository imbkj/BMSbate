package Controller.Batchprocessing;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;

import bll.Batchprocessing.EmSheBao_BatchBll;

import Model.EmShebaoChangeUploadModel;
import Util.FileOperate;
import Util.UserInfo;

public class EmSheBao_BatchController {
	private Media media;
	private String uploadFlieName;
	private final int dataRow = 6;
	private final String downfolder = "Batchprocessing/Templet/EmSheBao/";
	private String templetType;
	private EmSheBao_BatchBll bll;
	private boolean importOpen;

	public EmSheBao_BatchController() {
		bll = new EmSheBao_BatchBll();
		templetType = "";
		importOpen = true;
	}

	// 下载模板
	@Command("downloadTemplet")
	public void downloadTemplet() {
		String path = downfolder;
		try {
			String filename = getTempletFilename();
			if (!"".equals(filename) && filename != null) {
				FileOperate.download(path + filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取模板文件名
	private String getTempletFilename() {
		String fliename = "";
		try {
			if (!"".equals(templetType) && templetType != null) {
				switch (templetType) {
				case "修改工资":
					fliename = "EmSheBao_SalaryUpdate.xls";
					break;
				case "新增调入":
					fliename = "EmSheBao_BatchAdd.xls";
					break;
				case "独立户接管":
					fliename = "EmSheBao_TakeOver.xls";
					break;
				default:
					Messagebox.show("模板类型有误。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					break;
				}
			} else {
				Messagebox.show("请先选择模板类型。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fliename;
	}

	// 文件检查
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if ("xls".equals(media.getFormat())) {
			uploadFlieName = media.getName();
		} else {
			this.media = null;
			uploadFlieName = "";
			Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 导入数据
	@Command("upload")
	public void upload() {
		try {
			if (!"".equals(templetType) && templetType != null) {
				if (this.media != null) {
					String absolutePath = FileOperate.getAbsolutePath();
					String uploadfolder = "Batchprocessing/Upload/EmSheBao/";
					String uploadName = mosaicFileName();
					// 上传文件至服务器
					if (FileOperate.upload(media, uploadfolder + uploadName)) {
						// 获取上传Excel的内容,并更新至数据库
						String[] message = getExcel(
								absolutePath + uploadfolder, uploadName);
						if ("1".equals(message[0]) || "0".equals(message[0])) {
							// 弹出提示
							Messagebox.show(message[1], "操作提示", Messagebox.OK,
									Messagebox.NONE);
							refreshList();
						} else {
							// 弹出提示
							Messagebox.show(message[1], "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
					}
				} else {
					Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			} else {
				Messagebox.show("请先选择模板类型。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据导入出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取上传Excel的内容,并更新至数据库
	private String[] getExcel(String path, String fileName) {
		String[] message = new String[3];
		File file = null;
		Workbook wb = null;
		try {
			file = new File(path + fileName);
			// 读取Excel文件
			wb = Workbook.getWorkbook(file);
			// 读取工作表
			Sheet st = wb.getSheet(0);
			int rows = st.getRows();
			if (rows > 1) {
				// 核对Excel数据格式
				if (checkTh(st)) {
					// 存放数据至LIST
					List<EmShebaoChangeUploadModel> ciList = getExcelData(st);
					if (ciList != null && ciList.size() > 0) {
						// 更新至数据库
						message = bll.addDataToDb(ciList,
								UserInfo.getUsername(), templetType, fileName);
					} else {
						message[0] = "2";
						message[1] = "未找到可导入的数据。";
					}
				} else {
					message[0] = "2";
					message[1] = "导入的Excel与模板的数据格式不一致。";
				}
			} else {
				message[0] = "2";
				message[1] = "导入的Excel中并未找到有效数据。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "数据获取出错。";
		} finally {
			wb.close();
		}
		return message;
	}

	// 核对修改工资数据格式
	private boolean checkTh(Sheet st) {
		try {
			switch (templetType) {
			case "修改工资":
				if (!"员工编号".equals(st.getCell(0, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"姓名".equals(st.getCell(1, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"身份证号".equals(st.getCell(2, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"所属月份".equals(st.getCell(3, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"工资(整数)".equals(st.getCell(4, dataRow - 1).getContents()
						.trim()))
					return false;
				break;
			case "新增调入":
				if (!"员工编号".equals(st.getCell(0, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"姓名".equals(st.getCell(1, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"身份证号".equals(st.getCell(2, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"所属月份".equals(st.getCell(3, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"电脑号".equals(st.getCell(4, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"工资(整数)".equals(st.getCell(5, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"户籍(市内城镇、市外城镇)".equals(st.getCell(6, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"职工性质(合同制、劳务)".equals(st.getCell(7, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"利手(左、右)".equals(st.getCell(8, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"民族(汉族、白族...)".equals(st.getCell(9, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"养老(参加、不参加)".equals(st.getCell(10, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"医疗(一档、二档、三档、不参加)".equals(st.getCell(11, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"工伤(参加、不参加)".equals(st.getCell(12, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"失业(参加、不参加)".equals(st.getCell(13, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"生育(参加、不参加)".equals(st.getCell(14, dataRow - 1)
						.getContents().trim()))
					return false;
				break;
			case "独立户接管":
				if (!"员工编号".equals(st.getCell(0, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"姓名".equals(st.getCell(1, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"身份证号".equals(st.getCell(2, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"所属月份".equals(st.getCell(3, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"电脑号".equals(st.getCell(4, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"工资(整数)".equals(st.getCell(5, dataRow - 1).getContents()
						.trim()))
					return false;
				if (!"户籍(市内城镇、市外城镇)".equals(st.getCell(6, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"养老(参加、不参加)".equals(st.getCell(7, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"医疗(一档、二档、三档、不参加)".equals(st.getCell(8, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"工伤(参加、不参加)".equals(st.getCell(9, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"失业(参加、不参加)".equals(st.getCell(10, dataRow - 1)
						.getContents().trim()))
					return false;
				if (!"生育(参加、不参加)".equals(st.getCell(11, dataRow - 1)
						.getContents().trim()))
					return false;
				break;
			default:
				return false;
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	// 获取导入数据
	private List<EmShebaoChangeUploadModel> getExcelData(Sheet st) {
		List<EmShebaoChangeUploadModel> list = null;
		try {
			switch (templetType) {
			case "修改工资":
				list = getUpSalaryData(st);
				break;
			case "新增调入":
				list = getAddData(st);
				break;
			case "独立户接管":
				list = getTakeOverData(st);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 存放修改工资数据至LIST
	private List<EmShebaoChangeUploadModel> getUpSalaryData(Sheet st) {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		EmShebaoChangeUploadModel m = null;
		BigDecimal radix;
		try {
			// 遍历数据行
			for (int r = dataRow; r < st.getRows(); r++) {
				try {
					if (st.getCell(0, r).getContents() == null
							|| "".equals(st.getCell(0, r).getContents())) {
						break;
					}
					m = new EmShebaoChangeUploadModel();
					m.setGid(Integer.parseInt(st.getCell(0, r).getContents().trim()));
					m.setEmsu_name(st.getCell(1, r).getContents().trim());
					m.setEmsu_idcard(st.getCell(2, r).getContents().trim());
					m.setOwnmonth(Integer.parseInt(st.getCell(3, r)
							.getContents().trim()));
					radix = (new BigDecimal(st.getCell(4, r).getContents()
							.trim())).setScale(0, BigDecimal.ROUND_DOWN);
					m.setEmsu_radix(Integer.parseInt(radix.toString()));
					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 存放新增调入数据至LIST
	private List<EmShebaoChangeUploadModel> getAddData(Sheet st) {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		EmShebaoChangeUploadModel m = null;
		BigDecimal radix;
		try {
			// 遍历数据行
			for (int r = dataRow; r < st.getRows(); r++) {
				try {
					if (st.getCell(0, r).getContents() == null
							|| "".equals(st.getCell(0, r).getContents())) {
						break;
					}
					m = new EmShebaoChangeUploadModel();
					m.setGid(Integer.parseInt(st.getCell(0, r).getContents().trim()));
					m.setEmsu_name(st.getCell(1, r).getContents().trim());
					m.setEmsu_idcard(st.getCell(2, r).getContents().trim());
					m.setOwnmonth(Integer.parseInt(st.getCell(3, r)
							.getContents().trim()));
					m.setEmsu_computerid(st.getCell(4, r).getContents().trim());
					radix = (new BigDecimal(st.getCell(5, r).getContents()
							.trim())).setScale(0, BigDecimal.ROUND_DOWN);
					m.setEmsu_radix(Integer.parseInt(radix.toString()));
					m.setEmsu_hj(st.getCell(6, r).getContents().trim());
					m.setEmsu_Worker(st.getCell(7, r).getContents().trim());
					m.setEmsu_Hand(st.getCell(8, r).getContents().trim());
					m.setEmsu_Folk(st.getCell(9, r).getContents().trim());
					m.setEmsu_YL(st.getCell(10, r).getContents().trim());
					m.setEmsu_YLType(st.getCell(11, r).getContents().trim());
					m.setEmsu_GS(st.getCell(12, r).getContents().trim());
					m.setEmsu_Sye(st.getCell(13, r).getContents().trim());
					m.setEmsu_Syu(st.getCell(14, r).getContents().trim());
					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 存放独立户接管数据至LIST
	private List<EmShebaoChangeUploadModel> getTakeOverData(Sheet st) {
		List<EmShebaoChangeUploadModel> list = new ArrayList<EmShebaoChangeUploadModel>();
		EmShebaoChangeUploadModel m = null;
		BigDecimal radix;
		try {
			// 遍历数据行
			for (int r = dataRow; r < st.getRows(); r++) {
				try {
					if (st.getCell(0, r).getContents() == null
							|| "".equals(st.getCell(0, r).getContents())) {
						break;
					}
					m = new EmShebaoChangeUploadModel();
					m.setGid(Integer.parseInt(st.getCell(0, r).getContents().trim()));
					m.setEmsu_name(st.getCell(1, r).getContents().trim());
					m.setEmsu_idcard(st.getCell(2, r).getContents().trim());
					m.setOwnmonth(Integer.parseInt(st.getCell(3, r)
							.getContents().trim()));
					m.setEmsu_computerid(st.getCell(4, r).getContents().trim());
					radix = (new BigDecimal(st.getCell(5, r).getContents()
							.trim())).setScale(0, BigDecimal.ROUND_DOWN);
					m.setEmsu_radix(Integer.parseInt(radix.toString()));
					m.setEmsu_hj(st.getCell(6, r).getContents().trim());
					m.setEmsu_YL(st.getCell(7, r).getContents().trim());
					m.setEmsu_YLType(st.getCell(8, r).getContents().trim());
					m.setEmsu_GS(st.getCell(9, r).getContents().trim());
					m.setEmsu_Sye(st.getCell(10, r).getContents().trim());
					m.setEmsu_Syu(st.getCell(11, r).getContents().trim());
					list.add(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + "_" + UserInfo.getUserid() + ".xls";
		return name;
	}

	// 刷新列表
	private void refreshList() {
		try {
			Include ic = null;
			Tab tb = null;
			switch (templetType) {
			case "修改工资":
				ic = (Include) Path.getComponent("/winEmSBBatch/icSalUp");
				tb = (Tab) Path.getComponent("/winEmSBBatch/tbSalUp");
				break;
			case "新增调入":
			case "独立户接管":
				ic = (Include) Path.getComponent("/winEmSBBatch/icAdd");
				tb = (Tab) Path.getComponent("/winEmSBBatch/tbAdd");
				break;
			}
			tb.setSelected(true);
			ic.invalidate();
			importOpen = false;
			BindUtils.postNotifyChange(null, null, this, "importOpen");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public String getTempletType() {
		return templetType;
	}

	public void setTempletType(String templetType) {
		this.templetType = templetType;
	}

	public boolean isImportOpen() {
		return importOpen;
	}

	public void setImportOpen(boolean importOpen) {
		this.importOpen = importOpen;
	}

}
