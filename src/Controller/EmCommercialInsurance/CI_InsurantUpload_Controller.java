package Controller.EmCommercialInsurance;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBaseCompactListModel;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.EmCommercialInsurance.CI_InsurantUpload_Bll;

public class CI_InsurantUpload_Controller {
	MonthListUtil mlu = new MonthListUtil();
	private CI_InsurantUpload_Bll dobll = new CI_InsurantUpload_Bll();
	private String absolutePath;
	private Media media;
	private final String filetype = ".xls";

	private String uploadFlieName;
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		File file = null;
		Workbook wb = null;
		String[] message = new String[3];

		try {
				if (this.media != null) {
					absolutePath = FileOperate.getAbsolutePath();
					String uploadfolder = "EmCommercialInsurance/upload/Em_In/";
					String uploadName = mosaicFileName();
					String sheetname = "";
					// 上传文件至服务器
					if (FileOperate.upload(media, uploadfolder + uploadName)) {

						file = new File(absolutePath + uploadfolder
								+ uploadName);
						// 读取Excel文件
						wb = Workbook.getWorkbook(file);

						Sheet st = wb.getSheet(0);
						sheetname = st.getName();
						// 关闭excel
						wb.close();

						String filename = uploadName;
						String fileAllname = absolutePath + uploadfolder
								+ uploadName;
						
						//filename = "20140509144747.xls";
						//fileAllname = "d:/upload_test/20140509144747.xls";
						// 插入数据
						message = dobll.uploadCIOK(sheetname, filename, username, fileAllname);

						if (message[0].equals("1")) {
							// 更新数据

							Messagebox.show("文件上传成功", "操作提示", Messagebox.OK,
									Messagebox.NONE);
							
							Executions.sendRedirect("CI_Insurant_AutUp.zul");

						} else {
							Messagebox.show("文件数据导入系统出错。", "操作提示",
									Messagebox.OK, Messagebox.NONE);
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
	
	// 提交理赔明细
		@Command("claim_submit")
		public void claim_submit(@BindingParam("win") Window win,
				@ContextParam(ContextType.VIEW) Component view,
				@BindingParam("email_state") Checkbox email_state) {
			File file = null;
			Workbook wb = null;
			String[] message = new String[3];

			try {
					if (this.media != null) {
						absolutePath = FileOperate.getAbsolutePath();
						String uploadfolder = "EmCommercialInsurance/upload/Em_InCl/";
						String uploadName = mosaicFileName();
						String sheetname = "";
						// 上传文件至服务器
						if (FileOperate.upload(media, uploadfolder + uploadName)) {

							file = new File(absolutePath + uploadfolder
									+ uploadName);
							// 读取Excel文件
							wb = Workbook.getWorkbook(file);

							Sheet st = wb.getSheet(0);
							sheetname = st.getName();
							// 关闭excel
							wb.close();

							String filename = uploadName;
							String fileAllname = absolutePath + uploadfolder
									+ uploadName;
							
							//filename = "ci_bq_up.xls";
							//fileAllname = "d:/upload_test/ci_bq_up.xls";
							// 插入数据
							message = dobll.uploadCIclaimOK(sheetname, filename, username, fileAllname,email_state.getValue().toString());

							if (message[0].equals("1")) {
								// 更新数据

								Messagebox.show("文件上传成功", "操作提示", Messagebox.OK,
										Messagebox.NONE);
								
								Executions.sendRedirect("CI_InsurantClaim_Auting.zul");

							} else {
								Messagebox.show("文件数据导入系统出错。", "操作提示",
										Messagebox.OK, Messagebox.NONE);
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
	
	@Command("downloadTemplet")
	public void downloadTemplet() throws SQLException {
		FileOperate.download("/EmCommercialInsurance/File/Templet/ci_bq_up.xls");
	}
	
	@Command("downloadclaimTemplet")
	public void downloadclaimTemplet() throws SQLException {
		FileOperate.download("/EmCommercialInsurance/File/Templet/claim_pf_email.xls");
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + filetype;
		return name;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}
}
