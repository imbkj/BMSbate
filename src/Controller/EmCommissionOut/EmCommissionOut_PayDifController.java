package Controller.EmCommissionOut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmCommissionOutPayDifModel;
import Model.EmFinanceZYTModel;
import Util.FileOperate;
import bll.EmCommissionOut.EmCommissionOutPayDifBll;
import bll.EmFinance.EmFinance_OperateBll;

@SuppressWarnings("serial")
public class EmCommissionOut_PayDifController extends
		SelectorComposer<Component> {
	@Wire
	private Textbox filepath;
	@Wire
	private Window impwin;
	private String filename = "";
	private String nowtime = "";
	private String filenames = "";
	private InputStream inputStream = null;
	private Media media;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Executions.getCurrent().getDesktop().getWebApp().getConfiguration()
				.setMaxUploadSize(30000000);
	}

	// 获取上传的文件
	@Listen("onUpload= #attachBtn")
	public void uploadfile(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev) {
		try {
			media = null;
			inputStream = null;
			media = ev.getMedia();
			if (media != null) {
				if (media.getFormat().equals("xls")
						|| media.getFormat() == "xls"
						|| media.getFormat().equals("xlsx")
						|| media.getFormat() == "xlsx") {
					try {
						this.filename = media.getName();
						this.inputStream = media.getStreamData();
						filepath.setValue(filename);
					} catch (Exception e) {
						System.out.println("错误:" + e.getMessage());
					}
				} else {
					Messagebox.show("选择的文件格式不正确", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick= #summit")
	public void addTakeCardInfo() throws FileNotFoundException, IOException {

		String realPath = "EmCommissionOut/File/Upload/";
		SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
		if (inputStream != null && !inputStream.equals("")) {
			if (media != null) {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyyMMddhhmmss");
				Date date = new Date();
				nowtime = formatter.format(date);
				String fname = nowtime.trim() + filename.trim();
				String name = FileOperate.getAbsolutePath() + realPath + fname;
				filenames = fname;
				boolean flag = FileOperate.upload(media, realPath + fname);
				if (flag) {
					if (media.getFormat().equals("xls")
							|| media.getFormat() == "xls") {

						Integer k = loadXls2(name);
						if (k > 0) {
							Messagebox.show("导入成功", "提示", Messagebox.OK,
									Messagebox.INFORMATION);
							// Executions.sendRedirect("/EmCommissionOut/EmCommissionOutPay_AutList.zul");
							impwin.detach();
						} else {
							Messagebox.show("导入失败", "提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else if (media.getFormat().equals("xlsx")
							|| media.getFormat() == "xlsx") {
						loadXlsx(name);
					}
				}
			}
		} else {
			Messagebox.show("请选择导入文件", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 读取office 2003 xls
	 * 
	 * @param filePath
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public Integer loadXls2(String filepath) throws FileNotFoundException,
			IOException {
		Integer k = 0;
		try {

			// 把一张xls的数据表读到wb里
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(
					filepath)));

			// 读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
			HSSFSheet sheet = wb.getSheetAt(0);

			// 循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，

			// 如果总共有3条记录，那获取到的最后记录号就为2，因为是从0开始的
			List<EmCommissionOutPayDifModel> listm = new ArrayList<EmCommissionOutPayDifModel>();
			for (int j = sheet.getFirstRowNum() + 1; j < sheet
					.getPhysicalNumberOfRows(); j++) {
				// 创建一个行对象
				HSSFRow row = sheet.getRow(j);
				EmCommissionOutPayDifModel m = new EmCommissionOutPayDifModel();
				m.setCity(row.getCell(1).getStringCellValue());
				m.setCoab_name(row.getCell(2).getStringCellValue());
				m.setGid(row.getCell(5).getStringCellValue());
				m.setEcod_name(row.getCell(4).getStringCellValue());
				m.setEcod_idcard(row.getCell(5).getStringCellValue());
				m.setOwnmonth(row.getCell(8).getStringCellValue());
				m.setEcod_yl(row.getCell(9).getStringCellValue());
				m.setEcod_sye(row.getCell(10).getStringCellValue());
				m.setEcod_gs(row.getCell(11).getStringCellValue());
				m.setEcod_syu(row.getCell(12).getStringCellValue());
				m.setEcod_jl(row.getCell(13).getStringCellValue());
				m.setEcod_house(row.getCell(14).getStringCellValue());
				m.setEcod_other(row.getCell(15).getStringCellValue());
				m.setEcod_fuwu(row.getCell(16).getStringCellValue());
				m.setEcod_file(row.getCell(17).getStringCellValue());
				if (row.getCell(19) == null) {
					m.setEcod_remark("");
				} else {
					m.setEcod_remark(row.getCell(19).getStringCellValue()
							.toString());
				}

				if (row.getCell(21) == null) {
					m.setEcod_yl_st("");
				} else {
					m.setEcod_yl_st(String.valueOf(row.getCell(21)
							.getStringCellValue()));
				}

				if (row.getCell(22) == null) {
					m.setEcod_sye_st("");
				} else {
					m.setEcod_sye_st(String.valueOf(row.getCell(22)
							.getStringCellValue()));
				}

				if (row.getCell(23) == null) {
					m.setEcod_gs_st("");
				} else {
					m.setEcod_gs_st(String.valueOf(row.getCell(23)
							.getStringCellValue()));
				}

				if (row.getCell(24) == null) {
					m.setEcod_syu_st("");
				} else {
					m.setEcod_syu_st(String.valueOf(row.getCell(24)
							.getStringCellValue()));
				}

				if (row.getCell(25) == null) {
					m.setEcod_jl_st("");
				} else {
					m.setEcod_jl_st(String.valueOf(row.getCell(25)
							.getStringCellValue()));
				}

				if (row.getCell(26) == null) {
					m.setEcod_house_st("");
				} else {
					m.setEcod_house_st(String.valueOf(row.getCell(26)
							.getStringCellValue()));
				}

				if (row.getCell(27) == null) {
					m.setEcod_other_st("");
				} else {
					m.setEcod_other_st(String.valueOf(row.getCell(27)
							.getStringCellValue()));
				}

				if (row.getCell(28) == null) {
					m.setEcod_fuwu_st("");
				} else {
					m.setEcod_fuwu_st(String.valueOf(row.getCell(28)
							.getStringCellValue()));
				}

				if (row.getCell(29) == null) {
					m.setEcod_file_st("");
				} else {
					m.setEcod_file_st(String.valueOf(row.getCell(29)
							.getStringCellValue()));
				}

				listm.add(m);
			}
			Iterator<EmCommissionOutPayDifModel> it = listm.iterator();
			while (it.hasNext()) {
				EmCommissionOutPayDifBll bll = new EmCommissionOutPayDifBll();
				EmCommissionOutPayDifModel m1 = it.next();
				String ecod_remark = "";
				if (m1.getEcod_remark() != null
						&& !m1.getEcod_remark().equals("")) {
					ecod_remark = m1.getEcod_remark();
				} else {
					ecod_remark = "  ";
				}
				k = bll.updateEmCommissionOutPayDif(m1.getOwnmonth(),
						m1.getGid(), ecod_remark, m1.getEcod_yl_st(),
						m1.getEcod_sye_st(), m1.getEcod_gs_st(),
						m1.getEcod_syu_st(), m1.getEcod_jl_st(),
						m1.getEcod_house_st(), m1.getEcod_other_st(),
						m1.getEcod_fuwu_st(), m1.getEcod_file_st());
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			return k;
		} catch (IOException e) {

			e.printStackTrace();

		}
		return k;
	}

	/**
	 * 读取office 2007 xlsx
	 * 
	 * @param filePath
	 */
	public void loadXlsx(String filepath) {

	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}

}
