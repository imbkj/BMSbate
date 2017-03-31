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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;

import Model.EmZYTModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmZYT_UploadController {
	private MonthListUtil mlu = new MonthListUtil();
	private DateStringChange dsc = new DateStringChange();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private EmSheBao_DSelectBll sbBll = new EmSheBao_DSelectBll();

	private String absolutePath;
	private Media media;
	private final String filetype = ".xls";

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	// 所属月份下拉框
	private String[] s_ownmonth = mlu.getMonthList(true, nowmonth, 2, 9);
	// 社保当前月份
	private String sbOwnmonth;

	private String uploadFlieName;
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public EmZYT_UploadController() {
		// 获取上传月份默认值
		sbOwnmonth = sbBll.getShebaoNowmonth();
		if (Integer.parseInt(dsc.DatetoSting(nowDate, "dd")) > 15
				&& !sbOwnmonth.equals(nowmonth)) {
			nowmonth = dsc.ownmonthAddoneMonth(nowmonth);
		}
	}

	/*
	 * // 提交
	 * 
	 * @Command("submit")//sql语句直接获取excel数据插入 public void
	 * submit(@BindingParam("win") Window win,
	 * 
	 * @BindingParam("ownmonth") Combobox ownmonth,
	 * 
	 * @ContextParam(ContextType.VIEW) Component view) { File file = null;
	 * Workbook wb = null; String[] message = new String[3];
	 * 
	 * try { if (ownmonth.getSelectedItem() != null) { if (this.media != null) {
	 * absolutePath = FileOperate.getAbsolutePath(); String uploadfolder =
	 * "EmZYT/File/Upload/wtdExcel/"; String uploadName = mosaicFileName();
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
	 * uploadfolder + uploadName; // 插入数据
	 * 
	 * message = obll.uploadZYT(sheetname, filename, ownmonth.getValue(),
	 * username, fileAllname);
	 * 
	 * if (message[0].equals("1")) { // 更新数据 //String msg = ""; //msg =
	 * updateData(filename);
	 * 
	 * //Messagebox.show(msg, "操作提示", Messagebox.OK,Messagebox.NONE);
	 * 
	 * Integer count=sbll.getUploadCount(filename);
	 * 
	 * Messagebox.show(message[1]+"；成功导入条"+count+"数据", "操作提示",
	 * Messagebox.OK,Messagebox.NONE);
	 * 
	 * } else { Messagebox.show("文件数据导入系统出错。", "操作提示", Messagebox.OK,
	 * Messagebox.NONE); }
	 * 
	 * } else { Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
	 * Messagebox.NONE); } } else { Messagebox.show("请选择需要上传的文件。", "操作提示",
	 * Messagebox.OK, Messagebox.NONE); } } else { Messagebox.show("请选择所属月份。",
	 * "操作提示", Messagebox.OK, Messagebox.NONE); } } catch (Exception e) {
	 * e.printStackTrace(); Messagebox.show("数据导入出錯。", "操作提示", Messagebox.OK,
	 * Messagebox.ERROR); } }
	 */

	// 提交
	@Command("submit")
	// java读取excel单条数据循环插入
	public void submit(@BindingParam("win") Window win,
			@BindingParam("ownmonth") Combobox ownmonth,
			@ContextParam(ContextType.VIEW) Component view) {
		File file = null;
		Workbook wb = null;
		try {
			if (ownmonth.getSelectedItem() != null) {
				if (this.media != null) {
					absolutePath = FileOperate.getAbsolutePath();
					String uploadfolder = "EmZYT/File/Upload/wtdExcel/";
					String uploadName = mosaicFileName();
					String filename = uploadName;
					Integer count = 0;
					// 上传文件至服务器
					if (FileOperate.upload(media, uploadfolder + uploadName)) {

						file = new File(absolutePath + uploadfolder
								+ uploadName);
						// 读取Excel文件
						wb = Workbook.getWorkbook(file);
						Sheet st = wb.getSheet(0);

						int i = 1;
						String msg = "";
						EmZYTModel m = null;
						while (i < st.getRows()) {
							if (chk(st.getCell(4, i).getContents().trim(), st
									.getCell(2, i).getContents().trim())) {

								m = new EmZYTModel();
								m.setOwnmonth(Integer.parseInt(ownmonth
										.getValue()));
								m.setEmzt_zytid(st.getCell(0, i).getContents()
										.trim());
								m.setEmzt_company(st.getCell(4, i)
										.getContents().trim());

								m.setEmzt_name(st.getCell(6, i).getContents()
										.trim());
								m.setEmzt_class(st.getCell(2, i).getContents()
										.trim());
								m.setEmzt_uptime(st.getCell(83, i)
										.getContents().trim());
								m.setEmzt_mobile(st.getCell(10, i)
										.getContents().trim());
								m.setEmzt_fee(new BigDecimal(st.getCell(59, i)
										.getContents().trim()));
								m.setEmzt_filefee(new BigDecimal(st
										.getCell(62, i).getContents().trim()));
								m.setEmzt_idcard(st.getCell(8, i).getContents()
										.trim());
								m.setEmzt_sname(st.getCell(77, i).getContents()
										.trim());
								String scity = st.getCell(1, i).getContents()
										.trim();
								m.setEmzt_scity(scity.substring(
										scity.length() - 2, scity.length()));
								m.setEmzt_scompany(scity);
								m.setEmzt_total(new BigDecimal(st
										.getCell(69, i).getContents().trim()));
								m.setEmzt_sbtotal(new BigDecimal(st
										.getCell(67, i).getContents().trim()));
								m.setEmzt_housetotal(new BigDecimal(st
										.getCell(100, i).getContents().trim())
										.add(new BigDecimal(st.getCell(101, i)
												.getContents().trim())));
								m.setEmzt_filename(filename);
								m.setEmzt_addname(UserInfo.getUsername());
								m.setEmzt_ylradix(st.getCell(24, i)
										.getContents().trim());
								m.setEmzt_jlradix(st.getCell(36, i)
										.getContents().trim());
								m.setEmzt_gsradix(st.getCell(28, i)
										.getContents().trim());
								m.setEmzt_syeradix(st.getCell(40, i)
										.getContents().trim());
								m.setEmzt_syuradix(st.getCell(32, i)
										.getContents().trim());
								m.setEmzt_houseradix(st.getCell(44, i)
										.getContents().trim());
								m.setEmzt_remark(st.getCell(70, i)
										.getContents().trim());
								m.setEmzt_ylstart(st.getCell(22, i)
										.getContents().trim());
								m.setEmzt_ylstop(st.getCell(23, i)
										.getContents().trim());
								m.setEmzt_jlstart(st.getCell(34, i)
										.getContents().trim());
								m.setEmzt_jlstop(st.getCell(35, i)
										.getContents().trim());
								m.setEmzt_gsstart(st.getCell(26, i)
										.getContents().trim());
								m.setEmzt_gsstop(st.getCell(27, i)
										.getContents().trim());
								m.setEmzt_syestart(st.getCell(38, i)
										.getContents().trim());
								m.setEmzt_syestop(st.getCell(39, i)
										.getContents().trim());
								m.setEmzt_syustart(st.getCell(30, i)
										.getContents().trim());
								m.setEmzt_syustop(st.getCell(31, i)
										.getContents().trim());
								m.setEmzt_housestart(st.getCell(42, i)
										.getContents().trim());
								m.setEmzt_housestop(st.getCell(43, i)
										.getContents().trim());
								m.setEmzt_sbtitle(st.getCell(72, i)
										.getContents().trim());
								m.setEmzt_housetitle(st.getCell(73, i)
										.getContents().trim());
								m.setEmzt_compactstart(st.getCell(19, i)
										.getContents().trim());
								m.setEmzt_compactstop(st.getCell(20, i)
										.getContents().trim());
								m.setEmzt_zcid(st.getCell(79, i).getContents()
										.trim());
								m.setEmzt_zgid(st.getCell(78, i).getContents()
										.trim());
								m.setEmzt_flfee(new BigDecimal(st
										.getCell(54, i).getContents().trim()));
								m.setEmzt_flcontent(st.getCell(52, i)
										.getContents().trim());
								m.setEmzt_phone(st.getCell(5, i).getContents()
										.trim());
								m.setEmzt_idcardclass(st.getCell(12, i)
										.getContents().trim());
								m.setEmzt_bchousestart(st.getCell(46, i)
										.getContents().trim());
								m.setEmzt_bchousestop(st.getCell(47, i)
										.getContents().trim());
								m.setEmzt_bchouseradix(st.getCell(48, i)
										.getContents().trim());
								m.setEmzt_flstart(st.getCell(50, i)
										.getContents().trim());
								m.setEmzt_flstop(st.getCell(51, i)
										.getContents().trim());
								m.setEmzt_flfeeinfo(st.getCell(53, i)
										.getContents().trim());
								m.setEmzt_elsefee(new BigDecimal(st
										.getCell(56, i).getContents().trim()));
								m.setEmzt_elsefeestart(st.getCell(57, i)
										.getContents().trim());
								m.setEmzt_elsefeestop(st.getCell(58, i)
										.getContents().trim());
								m.setEmzt_feestart(st.getCell(60, i)
										.getContents().trim());
								m.setEmzt_feestop(st.getCell(61, i)
										.getContents().trim());
								m.setEmzt_filefeestart(st.getCell(63, i)
										.getContents().trim());
								m.setEmzt_filefeestop(st.getCell(64, i)
										.getContents().trim());
								m.setEmzt_managefee(new BigDecimal(st
										.getCell(66, i).getContents().trim()));
								m.setEmzt_cityremark(st.getCell(74, i)
										.getContents().trim());
								m.setEmzt_agreement(st.getCell(75, i)
										.getContents().trim());
								m.setEmzt_iffile(st.getCell(76, i)
										.getContents().trim());
								m.setEmzt_rdate(st.getCell(18, i).getContents()
										.trim());
								m.setEmzt_ifsingle(st.getCell(82, i)
										.getContents().trim());
								m.setEmzt_ifunlimited(st.getCell(21, i)
										.getContents().trim());
								m.setEmzt_outdate(st.getCell(106, i)
										.getContents().trim());
								m.setEmzt_ylcpp(st.getCell(85, i).getContents()
										.trim());
								m.setEmzt_ylopp(st.getCell(84, i).getContents()
										.trim());
								m.setEmzt_ylcp(new BigDecimal(st.getCell(87, i)
										.getContents().trim()));
								m.setEmzt_ylop(new BigDecimal(st.getCell(86, i)
										.getContents().trim()));
								m.setEmzt_jlcpp(st.getCell(89, i).getContents()
										.trim());
								m.setEmzt_jlopp(st.getCell(88, i).getContents()
										.trim());
								m.setEmzt_jlcp(new BigDecimal(st.getCell(91, i)
										.getContents().trim()));
								m.setEmzt_jlop(new BigDecimal(st.getCell(90, i)
										.getContents().trim()));
								m.setEmzt_gscpp(st.getCell(96, i).getContents()
										.trim());
								m.setEmzt_gsopp(null);
								m.setEmzt_gscp(new BigDecimal(st.getCell(97, i)
										.getContents().trim()));
								m.setEmzt_gsop(new BigDecimal(0));
								m.setEmzt_syecpp(st.getCell(92, i)
										.getContents().trim());
								m.setEmzt_syeopp(st.getCell(117, i)
										.getContents().trim());
								m.setEmzt_syecp(new BigDecimal(st
										.getCell(93, i).getContents().trim()));
								m.setEmzt_syeop(new BigDecimal(st
										.getCell(118, i).getContents().trim()));
								m.setEmzt_syucpp(st.getCell(94, i)
										.getContents().trim());
								m.setEmzt_syuopp(null);
								m.setEmzt_syucp(new BigDecimal(st
										.getCell(95, i).getContents().trim()));
								m.setEmzt_syuop(new BigDecimal(0));
								m.setEmzt_housecpp(st.getCell(99, i)
										.getContents().trim());
								m.setEmzt_houseopp(st.getCell(98, i)
										.getContents().trim());
								m.setEmzt_housecp(new BigDecimal(st
										.getCell(101, i).getContents().trim()));
								m.setEmzt_houseop(new BigDecimal(st
										.getCell(100, i).getContents().trim()));
								m.setEmzt_sbsingle(st.getCell(112, i)
										.getContents().trim());
								m.setEmzt_housesingle(st.getCell(113, i)
										.getContents().trim());
								m.setEmzt_filesingle(st.getCell(111, i)
										.getContents().trim());
								m.setEmzt_rsingle(st.getCell(110, i)
										.getContents().trim());
								m.setEmzt_yltotal(new BigDecimal(st
										.getCell(87, i).getContents().trim())
										.add(new BigDecimal(st.getCell(86, i)
												.getContents().trim())));
								m.setEmzt_syetotal(new BigDecimal(st
										.getCell(93, i).getContents().trim())
										.add(new BigDecimal(st.getCell(118, i)
												.getContents().trim())));
								m.setEmzt_gstotal(new BigDecimal(st
										.getCell(97, i).getContents().trim()));
								m.setEmzt_jltotal(new BigDecimal(st
										.getCell(91, i).getContents().trim())
										.add(new BigDecimal(st.getCell(90, i)
												.getContents().trim())));
								m.setEmzt_syutotal(new BigDecimal(st
										.getCell(95, i).getContents().trim()));
								m.setEmzt_wtgid(st.getCell(80, i).getContents()
										.trim());
								m.setEmzt_tel(st.getCell(9, i).getContents()
										.trim());
								m.setEmzt_wtcid(st.getCell(81, i).getContents()
										.trim());
								m.setEmzt_outreason(st.getCell(114, i)
										.getContents().trim());
								m.setEmzt_sex(st.getCell(7, i).getContents()
										.trim());
								m.setEmzt_iffeefile(st.getCell(65, i)
										.getContents().trim());

								// 插入数据
								String[] message = obll.uploadZYTbyModel(m);

								if (message[0].equals("1")) {
									count = count + 1;
								} else {
									// 不成功记录姓名，类型，公司
									msg = msg + " -- " + m.getEmzt_company()
											+ " -- " + m.getEmzt_name()
											+ " -- " + m.getEmzt_class()
											+ " -- ；";
								}

							} else {
								// 不成功记录姓名，类型，公司
								msg = msg + " -- "
										+ st.getCell(4, i).getContents().trim()
										+ " -- "
										+ st.getCell(6, i).getContents().trim()
										+ " -- "
										+ st.getCell(2, i).getContents().trim()
										+ " -- ；";
							}

							i = i + 1;

						}

						// 关闭excel
						wb.close();

						// 调用上传文件后的更新方法
						if (m != null) {
							obll.updateUploadFile(m);
							obll.updateUploadFileBJ(m);
						}

						Messagebox.show(
								"委托单成功导入条" + count + "数据，导入失败名单：" + msg,
								"操作提示", Messagebox.OK, Messagebox.NONE);

					} else {
						Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
					}
				} else {
					Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			} else {
				Messagebox.show("请选择所属月份。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据导入出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public boolean chk(String company, String emzt_class) {
		boolean result = true;
		// 检查公司名称是否为空
		if (company == null || "".equals(company)) {
			result = false;
		}
		// 检查委托事件是否合法
		if (!"新增".equals(emzt_class) && !"社保基数调整".equals(emzt_class)
				&& !"户籍调整".equals(emzt_class) && !"特殊调整".equals(emzt_class)
				&& !"服务项目调整".equals(emzt_class) && !"服务费调整".equals(emzt_class)
				&& !"年度调整".equals(emzt_class) && !"终止".equals(emzt_class)
				&& !"一次性费用".equals(emzt_class)) {
			result = false;
		}
		return result;
	}

	public String updateData(String filename) {
		String result;
		List<EmZYTModel> dataList;
		String[] message = new String[3];
		int j = 0;
		int k = 0;

		// 获取当月数据
		dataList = sbll.getEmZYTList(" AND emzt_filename='" + filename + "'");

		for (int i = 0; i < dataList.size(); i++) {
			j = j + 1;
			// 更新数据
			message = obll.msiEmZYTStartTask(dataList.get(i));

			if (message[0].equals("1")) {
				k = k + 1;
			}
		}

		if (j == k) {
			result = "操作成功！";
		} else {
			result = "部分数据操作不成功，请检查！";
		}

		return result;
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

	public String[] getS_ownmonth() {
		return s_ownmonth;
	}

	public void setS_ownmonth(String[] s_ownmonth) {
		this.s_ownmonth = s_ownmonth;
	}

	public String getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(String nowmonth) {
		this.nowmonth = nowmonth;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}
}
