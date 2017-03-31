package Controller.EmSheBao;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.A;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

import Model.EmShebaoBJModel;
import Model.EmShebaoSZSIFileModel;
import Model.EmShebaoUpdateModel;
import Util.FileOperate;
import Util.UserInfo;

public class Emsc_SZSI_FinanceUploadFeeController {

	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private String absolutePath;
	private Media[] media;
	private String uploadFlieName;

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private Window win = (Window) Path.getComponent("/winUploadChange");

	String ownmonth;

	public Emsc_SZSI_FinanceUploadFeeController() {
		ownmonth = dsbll.getShebaoNowmonth();
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {

		try {
			if (this.media != null) {

				Grid gd = (Grid) win.getFellow("grid");
				if (gd.getRows().getChildren().size() > 3) {

					String uploadName = "";

					for (int k = 3; k < gd.getRows().getChildren().size(); k++) {

						Label lbl = null;

						lbl = (Label) gd.getCell(k, 0).getChildren().get(1);

						uploadName = lbl.getValue();

						// 插入数据
						String[] str2 = OperateFile2(uploadName);
						/*
						 * if (str1.length == 0) { return; } else {
						 * Messagebox.show("扣款文件上传出错。", "操作提示", Messagebox.OK,
						 * Messagebox.ERROR); return; }
						 */

						String szsiFileName = dsbll.getSZSIFeeFileName(Integer
								.parseInt(str2[3]));

						// 操作台账后补交数据
						// String[] str3 =
						// OperateFile3(uploadName,checkSingle(str2[3]));

						// 更新导入系统的社保台账后的文件的数据gid,cid
						dobll.updateFinanceSP(szsiFileName, uploadName);

					}

					Messagebox.show("操作成功", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					// 关闭窗口
					win.detach();
				} else {
					Messagebox.show("请选择要上传的文件", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("文件上传出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 处理社保局扣款文件
	private String[] OperateFile2(String name) {
		String[] str = new String[5];
		String filename = name;
		String fileAllname = absolutePath + "EmSheBao/File/Upload/Finance/"
				+ name;
		String sheetname = "";
		String shebaoid = "";
		String company = "";
		try {
			File file = new File(fileAllname);
			// Excel获得文件
			Workbook wb = Workbook.getWorkbook(file);
			// 打开一个文件的副本，并且指定数据写回到原文件
			WritableWorkbook book = Workbook.createWorkbook(file, wb);
			WritableSheet st = book.getSheet(0);
			sheetname = st.getName();

			// 检查工作表名称
			if ("五险合一".equals(sheetname)) {
				shebaoid = st.getCell(0, 1).getContents().replace("单位编号：", "");
				company = st.getCell(9, 1).getContents().replace("单位名称：", "");
				st.setName("sheet1"); // 修改名字为sheet1
				sheetname = st.getName();
				/*
				 * // 更改表头 st.addCell(new jxl.write.Label(7, 4, "养老保险基数"));
				 * st.addCell(new jxl.write.Label(8, 4, "养老个人交"));
				 * st.addCell(new jxl.write.Label(9, 4, "养老单位交"));
				 * st.addCell(new jxl.write.Label(10, 4, "医疗保险基数"));
				 * st.addCell(new jxl.write.Label(11, 4, "医疗个人交"));
				 * st.addCell(new jxl.write.Label(12, 4, "医疗单位交"));
				 * st.addCell(new jxl.write.Label(13, 4, "工伤保险基数"));
				 * st.addCell(new jxl.write.Label(14, 4, "工伤单位交"));
				 * st.addCell(new jxl.write.Label(15, 4, "失业保险基数"));
				 * st.addCell(new jxl.write.Label(16, 4, "失业个人交"));
				 * st.addCell(new jxl.write.Label(17, 4, "失业单位交"));
				 * st.addCell(new jxl.write.Label(18, 4, "生育保险基数"));
				 * st.addCell(new jxl.write.Label(19, 4, "生育单位交")); // 移除表头以上行
				 * st.removeRow(0); st.removeRow(0); st.removeRow(0);
				 * st.removeRow(0); book.write();
				 */
				// 记录文件信息
				str[0] = sheetname;
				str[1] = filename;
				str[2] = fileAllname;
				str[3] = shebaoid;
				str[4] = company;

				// 根据社保编号检查是否已有上传的台账
				if (dobll.existsSZSIByShebaoID(Integer.parseInt(shebaoid))) {
					String[] message = new String[3];
					int i = 5;
					while (st.getCell(13, i).getContents().trim() != null
							&& !"".equals(st.getCell(13, i).getContents()
									.trim())) {// 判断生育是否为空，为空则表示数据到最后一条
						EmShebaoUpdateModel m = null;
						m = new EmShebaoUpdateModel();

						m.setOwnmonth(Integer.parseInt(ownmonth));
						m.setEsiu_name(st.getCell(2, i).getContents().trim());
						m.setEsiu_computerid(st.getCell(1, i).getContents()
								.trim());
						m.setEsiu_total(new BigDecimal(st.getCell(4, i)
								.getContents().trim()));
						m.setEsiu_totalop(new BigDecimal(st.getCell(5, i)
								.getContents().trim()));
						m.setEsiu_totalcp(new BigDecimal(st.getCell(6, i)
								.getContents().trim()));
						m.setEsiu_radix(Integer.parseInt(st.getCell(7, i)
								.getContents().trim()));
						m.setEsiu_ylop(new BigDecimal(st.getCell(8, i)
								.getContents().trim()));
						m.setEsiu_ylcp(new BigDecimal(st.getCell(9, i)
								.getContents().trim()));
						m.setEsiu_jlop(new BigDecimal(st.getCell(11, i)
								.getContents().trim()));
						m.setEsiu_jlcp(new BigDecimal(st.getCell(12, i)
								.getContents().trim()));
						m.setEsiu_gscp(new BigDecimal(st.getCell(14, i)
								.getContents().trim()));
						m.setEsiu_syeop(new BigDecimal(st.getCell(16, i)
								.getContents().trim()));
						m.setEsiu_syecp(new BigDecimal(st.getCell(17, i)
								.getContents().trim()));
						m.setEsiu_syucp(new BigDecimal(st.getCell(19, i)
								.getContents().trim()));
						m.setEsiu_single(Integer
								.parseInt(checkSingle(shebaoid)));
						m.setEsiu_shebaoid(Integer.parseInt(shebaoid));
						m.setEsiu_company(company);

						// 调用方法
						message = dobll.uploadFinanceSZSI(m, filename);

						i = i + 1;
					}

					// 记录上传文件路径
					EmShebaoSZSIFileModel szsiFileM = null;
					szsiFileM = new EmShebaoSZSIFileModel();

					szsiFileM.setEssf_shebaoid(shebaoid);
					szsiFileM.setEssf_type(2);
					szsiFileM.setEssf_fileurl("EmSheBao/File/Upload/Finance/"
							+ name);
					szsiFileM.setEssf_filename(filename);
					szsiFileM.setEssf_addname(UserInfo.getUsername());
					dobll.addShebaoSZSIFile(szsiFileM);

					// 补交数据
					int k = 10 + i;

					while (st.getCell(0, k).getContents().trim() != null
							&& !"".equals(st.getCell(0, k).getContents().trim())) {// 判断序号是否为空，为空则表示数据到最后一条
						EmShebaoBJModel m = null;
						m = new EmShebaoBJModel();
						
						m.setOwnmonth(Integer.parseInt(ownmonth));
						m.setEmsb_name(st.getCell(2, k).getContents().trim());
						m.setEmsb_computerid(st.getCell(1, k).getContents()
								.trim());
						m.setEmsb_m1(st.getCell(3, k).getContents().trim());
						m.setEmsb_total(new BigDecimal(st.getCell(4, k)
								.getContents().trim()));
						m.setEssb_zh(new BigDecimal(st.getCell(5, k)
								.getContents().trim()));
						m.setEssb_gj(new BigDecimal(st.getCell(6, k)
								.getContents().trim()));
						m.setEmsb_totalop(new BigDecimal(st.getCell(7, k)
								.getContents().trim()));
						m.setEmsb_totalcp(new BigDecimal(st.getCell(8, k)
								.getContents().trim()));
						m.setEmsb_single(Integer
								.parseInt(checkSingle(shebaoid)));

						// 调用方法
						message = dobll.uploadFinanceSZSIBJ(m, filename);

						k = k + 1;
					}
				}
			} else {
				/*
				 * Messagebox.show("扣款文件格式错误，请确认文件。", "操作提示", Messagebox.OK,
				 * Messagebox.ERROR);
				 */
			}
			book.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 处理台账后补交数据
	private String[] OperateFile3(String name, String single) {
		String[] str = new String[3];
		String filename = name;
		String fileAllname = absolutePath + "EmSheBao/File/Upload/Finance/"
				+ name;
		String sheetname = "";
		int i = 5;

		try {
			File file = new File(fileAllname);
			// Excel获得文件
			Workbook wb = Workbook.getWorkbook(file);
			// 打开一个文件的副本，并且指定数据写回到原文件
			WritableWorkbook book = Workbook.createWorkbook(file, wb);
			WritableSheet st = book.getSheet(0);
			sheetname = st.getName();

			do {
				i++;
			} while (st.getCell(0, i - 1).getContents().length() > 0);

			i = i + 10;

			// 检查是否有补交数据
			if (st.getCell(0, i).getContents().length() > 0) {

				/*
				 * // 移除表头以上行 for (int j = 0; j < i; j++) { st.removeRow(0); }
				 * book.write();
				 */
				// 记录文件信息
				str[0] = sheetname;
				str[1] = filename;
				str[2] = fileAllname;

				String[] message = new String[3];
				int k = 1 + i;

				while (st.getCell(0, k).getContents().trim() != null
						&& !"".equals(st.getCell(0, k).getContents().trim())) {// 判断序号是否为空，为空则表示数据到最后一条
					EmShebaoBJModel m = null;
					 m = new EmShebaoBJModel();

					m.setOwnmonth(Integer.parseInt(ownmonth));
					m.setEmsb_name(st.getCell(2, k).getContents().trim());
					m.setEmsb_computerid(st.getCell(1, k).getContents().trim());
					m.setEmsb_m1(st.getCell(3, k).getContents().trim());
					m.setEmsb_total(new BigDecimal(st.getCell(4, k)
							.getContents().trim()));
					m.setEssb_zh(new BigDecimal(st.getCell(5, k).getContents()
							.trim()));
					m.setEssb_gj(new BigDecimal(st.getCell(6, k).getContents()
							.trim()));
					m.setEmsb_totalop(new BigDecimal(st.getCell(7, k)
							.getContents().trim()));
					m.setEmsb_totalcp(new BigDecimal(st.getCell(8, k)
							.getContents().trim()));
					m.setEmsb_single(Integer.parseInt(single));

					// 调用方法
					message = dobll.uploadFinanceSZSIBJ(m, filename);

					k = k + 1;
				}

			}
			book.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 文件检查
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		for (int i = 0; i < media.length; i++) {

			if ("xls".equals(media[i].getFormat())
					|| "xlsx".equals(media[i].getFormat())) {
				uploadFlieName = media[i].getName();

				absolutePath = FileOperate.getAbsolutePath();
				String uploadfolder = "EmSheBao/File/Upload/Finance/";
				String uploadName = uploadFlieName;

				if (FileOperate.upload(media[i], uploadfolder + uploadName)) {

					Grid gd = (Grid) win.getFellow("grid");
					final Row rw = new Row();
					Cell cell1 = new Cell();
					Cell cell2 = new Cell();
					cell1.appendChild(new Label(media[i].getName()));
					Label lb = new Label();
					lb.setValue(uploadName);
					lb.setStyle("display:none");
					cell1.appendChild(lb);
					cell1.setColspan(2);
					A rm = new A("删除");
					rm.addEventListener(Events.ON_CLICK,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event event)
										throws Exception {
									rw.detach();
								}
							});
					cell2.appendChild(rm);
					rw.appendChild(cell1);
					rw.appendChild(cell2);
					for (int j = 2; j < gd.getRows().getChildren().size(); j++) {

						if (media[i].getName().equals(
								((Label) gd.getCell(j, 0).getChildren().get(0))
										.getValue())) {

							gd.getRows().removeChild(
									gd.getRows().getChildren().get(j));
						}

					}

					gd.getRows().appendChild(rw);
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

	// 判断开户状态
	private String checkSingle(String shebaoid) {
		String ifSingle = "1";
		switch (shebaoid) {
		case "167120":
			ifSingle = "2";
			break;
		case "391055":
			ifSingle = "0";
			break;
		case "464780":
			ifSingle = "3";
			break;
		case "464781":
			ifSingle = "4";
			break;
		default:
			ifSingle = "1";
			break;
		}
		return ifSingle;
	}
}
