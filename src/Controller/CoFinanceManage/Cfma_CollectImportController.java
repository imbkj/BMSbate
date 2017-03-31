package Controller.CoFinanceManage;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceCollectImportModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_CollectImportBll;

public class Cfma_CollectImportController {
	private Cfma_CollectImportBll bll;
	private Media media;
	private String uploadFlieName;
	private final int dataRow = 1;

	public Cfma_CollectImportController() {
		bll = new Cfma_CollectImportBll();
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

	// 提交并导入数据
	@Command("upload")
	public void upload(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (this.media != null) {
				String absolutePath = FileOperate.getAbsolutePath();
				String uploadfolder = "CoFinanceManage/File/Upload/collect";
				String uploadName = mosaicFileName();
				// 上传文件至服务器
				if (FileOperate.upload(media, uploadfolder + uploadName)) {
					// 获取上传Excel的内容,并更新至数据库
					String[] message = getExcel(absolutePath + uploadfolder
							+ uploadName);
					if ("1".equals(message[0]) || "0".equals(message[0])) {
						Binder bind = (Binder) view.getParent().getAttribute(
								"binder");
						bind.postCommand("refreshWin", null);
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();
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
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据导入出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取上传Excel的内容,并更新至数据库
	private String[] getExcel(String filepath) {
		String[] message = new String[3];
		File file = null;
		Workbook wb = null;
		try {
			file = new File(filepath);
			// 读取Excel文件
			wb = Workbook.getWorkbook(file);
			// 读取工作表
			Sheet st = wb.getSheet(0);
			int rows = st.getRows();
			if (rows > 1) {
				// 核对Excel数据格式
				if (checkTh(st)) {
					// 存放数据至LIST
					List<CoFinanceCollectImportModel> ciList = getExcelData(st);
					if (ciList.size() > 0) {
						// 更新至数据库
						message = bll.addImportToCompany(ciList,
								UserInfo.getUsername());
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
				message[1] = "导入的Excel中并未找到导入数据。";
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

	// 核对数据格式
	private boolean checkTh(Sheet st) {
		try {
			if (!"交易日期".equals(
					st.getCell(0, 0).getContents().trim())) 
				return false;
			if (!"借".equals(
					st.getCell(1, 0).getContents().trim())) 
				return false;
			if (!"贷".equals(
					st.getCell(2, 0).getContents().trim())) 
				return false;
			if (!"交易流水号".equals(
					st.getCell(3, 0).getContents().trim())) 
				return false;
			if (!"摘要".equals(
					st.getCell(4, 0).getContents().trim())) 
				return false;
			if (!"用途".equals(
					st.getCell(5, 0).getContents().trim())) 
				return false;
			if (!"对方账户".equals(
					st.getCell(6, 0).getContents().trim())) 
				return false;
			if (!"对方账户名称".equals(
					st.getCell(7, 0).getContents().trim())) 
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	// 存放数据至LIST
	private List<CoFinanceCollectImportModel> getExcelData(Sheet st) {
		List<CoFinanceCollectImportModel> list = new ArrayList<CoFinanceCollectImportModel>();
		CoFinanceCollectImportModel m = null;
		try {
			// 遍历数据行
			for (int r = dataRow; r < st.getRows(); r++) {
				try {
					if (st.getCell(0, r).getContents() == null
							|| "".equals(st.getCell(0, r).getContents())) {
						break;
					}
					m = new CoFinanceCollectImportModel();
					m.setCfci_transactionTime(st.getCell(0, r).getContents()
							.trim());
					m.setCfci_amount(new BigDecimal(st.getCell(2, r)
							.getContents().trim()));
					m.setCfci_transactionNo(st.getCell(3, r).getContents()
							.trim());
					m.setCfci_remark(st.getCell(4, r).getContents().trim());
					m.setCfci_usage(st.getCell(5, r).getContents().trim());
					m.setCfci_account(st.getCell(6, r).getContents().trim());
					m.setCfci_company(st.getCell(7, r).getContents().trim());
				} catch (Exception e) {

				}
				list.add(m);
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

	public String getUploadFlieName() {
		return uploadFlieName;
	}

}
