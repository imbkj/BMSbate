package Controller.EmZYT;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;

public class EmZYT_FeedbackUploadController {
	private DateStringChange dsc = new DateStringChange();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();

	private String absolutePath;
	private Media media;
	private final String filetype = ".xls";

	private String uploadFlieName;
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	/*
	 * // 提交
	 * 
	 * @Command("submit") public void submit(@BindingParam("win") Window win,
	 * 
	 * @ContextParam(ContextType.VIEW) Component view) { File file = null;
	 * Workbook wb = null; String[] message = new String[3];
	 * 
	 * try { if (this.media != null) { absolutePath =
	 * FileOperate.getAbsolutePath(); String uploadfolder =
	 * "EmZYT/File/Upload/fbExcel/"; String uploadName = mosaicFileName();
	 * String sheetname = ""; // 上传文件至服务器 if (FileOperate.upload(media,
	 * uploadfolder + uploadName)) {
	 * 
	 * file = new File(absolutePath + uploadfolder + uploadName); // 读取Excel文件
	 * wb = Workbook.getWorkbook(file);
	 * 
	 * Sheet st = wb.getSheet(0); sheetname = st.getName(); // 关闭excel
	 * wb.close();
	 * 
	 * String filename = uploadName; String fileAllname = absolutePath +
	 * uploadfolder + uploadName;
	 * 
	 * // 插入数据 message = obll.uploadFeedbackExcel(sheetname, filename, username,
	 * fileAllname);
	 * 
	 * if (message[0].equals("1")) { // 更新数据
	 * 
	 * Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.NONE);
	 * 
	 * } else { Messagebox.show("文件数据导入系统出错。", "操作提示", Messagebox.OK,
	 * Messagebox.NONE); }
	 * 
	 * } else { Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
	 * Messagebox.NONE); } } else { Messagebox.show("请选择需要上传的文件。", "操作提示",
	 * Messagebox.OK, Messagebox.NONE); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); Messagebox.show("数据导入出錯。",
	 * "操作提示", Messagebox.OK, Messagebox.ERROR); } }
	 */

	// 提交
	@Command("submit")
	// java读取excel单条数据循环插入
	public void submit(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		File file = null;
		Workbook wb = null;

		try {
			if (this.media != null) {
				absolutePath = FileOperate.getAbsolutePath();
				String uploadfolder = "EmZYT/File/Upload/fbExcel/";
				String uploadName = mosaicFileName();
				String sheetname = "";
				// 上传文件至服务器
				if (FileOperate.upload(media, uploadfolder + uploadName)) {

					file = new File(absolutePath + uploadfolder + uploadName);
					String filename = uploadName; 
					// 读取Excel文件
					wb = Workbook.getWorkbook(file);

					Sheet st = wb.getSheet(0);
					sheetname = st.getName();

					int i = 1;
					int count = 0;
					String msg = "";
					EmZYTFeedbackModel m = null;
					while (i < st.getRows()) {

						m = new EmZYTFeedbackModel();
						m.setEzfb_billmonth(st.getCell(0, i).getContents()
								.trim());//[账单月份]
						m.setEzfb_zytid(Integer.parseInt(st.getCell(1, i).getContents()
								.trim()));//[序号]
						m.setEzfb_listid(Integer.parseInt(st.getCell(2, i).getContents()
								.trim()));//[委托任务单序号]
						m.setEzfb_gid(st.getCell(3, i).getContents()
								.trim());//[雇员编号]
						m.setEzfb_name(st.getCell(4, i).getContents()
								.trim());//[雇员姓名]
						m.setEzfb_cid(st.getCell(5, i).getContents()
								.trim());//[公司编号]
						m.setEzfb_company(st.getCell(6, i).getContents()
								.trim());//[公司名称]
						m.setEzfb_idcardclass(st.getCell(7, i).getContents()
								.trim());//[证件类型]
						m.setEzfb_idcard(st.getCell(8, i).getContents()
								.trim());//[证件号码]
						m.setEzfb_class(st.getCell(9, i).getContents()
								.trim());//[委托类型]
						m.setEzfb_hbstart(st.getCell(11, i).getContents()
								.trim());//[合并执行年月]
						m.setEzfb_hbstop(st.getCell(12, i).getContents()
								.trim());//[合并结束年月]
						m.setEzfb_hbclass(st.getCell(13, i).getContents()
								.trim());//[合并险种]
						m.setEzfb_yl_cradix(st.getCell(15, i).getContents()
								.trim());//[执行养老企业基数]
						m.setEzfb_yl_oradix(st.getCell(16, i).getContents()
								.trim());//[执行养老个人基数]
						m.setEzfb_ylstart(st.getCell(17, i).getContents()
								.trim());//[执行养老保险执行年月]
						m.setEzfb_ylstop(st.getCell(18, i).getContents()
								.trim());//[执行养老保险结束年月]
						m.setEzfb_ylcity(st.getCell(19, i).getContents()
								.trim());//[执行养老执行城市序号]
						m.setEzfb_house_cradix(st.getCell(23, i).getContents()
								.trim());//[公积金企业基数]
						m.setEzfb_house_oradix(st.getCell(24, i).getContents()
								.trim());//[公积金个人基数]
						m.setEzfb_housecpp(st.getCell(25, i).getContents()
								.trim());//[公积金企业比例]
						m.setEzfb_houseopp(st.getCell(26, i).getContents()
								.trim());//[公积金个人比例]
						m.setEzfb_housestart(st.getCell(27, i).getContents()
								.trim());//[公积金执行年月]
						m.setEzfb_housestop(st.getCell(28, i).getContents()
								.trim());//[公积金结束年月]
						m.setEzfb_housecity(st.getCell(29, i).getContents()
								.trim());//[执行公积金执行城市序号]
						m.setEzfb_compactstart(st.getCell(33, i).getContents()
								.trim());//[执行服务产品执行年月]
						m.setEzfb_compactstop(st.getCell(34, i).getContents()
								.trim());//[执行服务产品结束年月]
						m.setEzfb_feestart(st.getCell(36, i).getContents()
								.trim());//[执行服务费执行年月]
						m.setEzfb_feestop(st.getCell(37, i).getContents()
								.trim());//[执行服务费结束年月]
						m.setEzfb_filestart(st.getCell(39, i).getContents()
								.trim());//[执行档案保管费执行年月]
						m.setEzfb_filestop(st.getCell(40, i).getContents()
								.trim());//[执行档案保管费结束年月]
						m.setEzfb_elsestart(st.getCell(42, i).getContents()
								.trim());//[执行额外费用执行年月]
						m.setEzfb_elsestop(st.getCell(43, i).getContents()
								.trim());//[执行额外费用结束年月]
						m.setEzfb_cremark(st.getCell(44, i).getContents()
								.trim());//[受托方备注]
						m.setEzfb_cremark_class(st.getCell(45, i).getContents()
								.trim());//[受托方备注类型]
						m.setEzfb_cremark_content(st.getCell(46, i).getContents()
								.trim());//[受托方备注内容]
						m.setEzfb_zytgid(st.getCell(47, i).getContents()
								.trim());//[智翼通雇员编号]
						m.setEzfb_zytcid(st.getCell(48, i).getContents()
								.trim());//[智翼通公司编号]
						m.setEzfb_ct_gid(st.getCell(49, i).getContents()
								.trim());//[委托方雇员编号]
						m.setEzfb_ct_cid(st.getCell(50, i).getContents()
								.trim());//[委托方公司编号]
						m.setEzfb_city(st.getCell(51, i).getContents()
								.trim());//[执行城市]
						m.setEzfb_submittime(st.getCell(52, i).getContents()
								.trim());//[委托方提交日期]
						m.setEzfb_ylop_pm(st.getCell(20, i).getContents()
								.trim());//[执行养老个人收款方式]
						m.setEzfb_ylcp_pm(st.getCell(21, i).getContents()
								.trim());//[执行养老企业收款方式]
						m.setEzfb_houseop_pm(st.getCell(30, i).getContents()
								.trim());//[执行公积金个人收款方式]
						m.setEzfb_housecp_pm(st.getCell(31, i).getContents()
								.trim());//[执行公积金企业收款方式]
						m.setEzfb_feetotal(st.getCell(53, i).getContents()
								.trim());//[执行管理费总计]
						m.setEzfb_sb_remark(st.getCell(54, i).getContents()
								.trim());//[社保标准备注]
						m.setEzfb_house_remark(st.getCell(55, i).getContents()
								.trim());	//[公积金标准备注]
						m.setEzfb_addname(UserInfo.getUsername());
						m.setEzfb_addfilename(uploadName);
						m.setEzfb_qz_fee(st.getCell(56, i).getContents()
								.trim());//其中服务费
						m.setEzfb_qz_housecp(st.getCell(57, i).getContents()
								.trim());//其中公积金企业缴费
						m.setEzfb_qz_houseop(st.getCell(58, i).getContents()
								.trim());//其中公积金个人缴费
						m.setEzfb_qz_sbcp(st.getCell(59, i).getContents()
								.trim());//其中社保企业缴费
						m.setEzfb_qz_sbop(st.getCell(60, i).getContents()
								.trim());//其中社保个人缴费
						m.setEzfb_qz_file(st.getCell(61, i).getContents()
								.trim());//其中档案费
						m.setEzfb_qz_product(st.getCell(62, i).getContents()
								.trim());//其中服务产品费
						m.setEzfb_wt_agency(st.getCell(63, i).getContents()
								.trim());//委托机构
						m.setEzfb_wtremark(st.getCell(64, i).getContents()
								.trim());//委托方备注


						
						// 插入数据
						String[] message = obll.uploadFeedbackByModel(m);

						if (message[0].equals("1")) {
							count = count + 1;
						}

						i = i + 1;
					}
					// 关闭excel
					wb.close();

					Messagebox.show("操作成功，导入"+count+"条数据！", "操作提示",
							Messagebox.OK, Messagebox.NONE);

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
