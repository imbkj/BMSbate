package Controller.EmSheBao;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
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
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoBJModel;
import Model.EmShebaoSZSIFileModel;
import Model.EmShebaoUpdateModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_SZSI_FinanceUploadController {

	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private String absolutePath;
	private Media[] media;
	private String[] uploadFlieName;
	private String uploadfolder;
	private String ownmonth;

	public Emsc_SZSI_FinanceUploadController() {
		ownmonth = dsbll.getShebaoNowmonth();
		media = new Media[2];
		uploadFlieName = new String[2];
		absolutePath = FileOperate.getAbsolutePath();
		uploadfolder = "EmSheBao/File/Upload/Finance/";
	}

	// 浏览文件
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul,
			@BindingParam("type") int type) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		Media m = upEvent.getMedia();
		if ("xls".equals(m.getFormat())) {
			if (type == 1) {
				media[0] = m;
				uploadFlieName[0] = m.getName();
			} else if (type == 2) {
				media[1] = m;
				uploadFlieName[1] = m.getName();
			}
		} else {
			if (type == 1) {
				media[0] = null;
				uploadFlieName[0] = "";
			} else if (type == 2) {
				media[1] = null;
				uploadFlieName[1] = "";
			}
			Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		String[] str1 = null; // 档案文件参数
		String[] str2 = null; // 扣款文件参数
		String[] str3 = null; // 扣款文件补交数据参数
		try {
			// 社保局档案文件
			if (this.media[0] != null) {
				// 文件上传服务器
				if (uploadFile(this.media[0], this.media[0].getName())) {
					str1 = OperateFile1(this.media[0].getName());
					if (str1.length == 0) {
						return;
					}
				} else {
					Messagebox.show("档案文件上传出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
			// 社保局扣款文件
			if (this.media[1] != null) {
				// 文件上传服务器
				if (uploadFile(this.media[1], this.media[1].getName())) {
					str2 = OperateFile2(this.media[1].getName());
					if (str2.length == 0) {
						return;
					}
				} else {
					Messagebox.show("扣款文件上传出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}

			// 操作台账后补交数据
			str3 = OperateFile3(this.media[1].getName(), checkSingle(str1[3]));

			// 更新导入系统的社保台账后的文件的数据gid,cid
			dobll.updateFinanceSP(str1[1], str2[1]);

			win.detach();
			Messagebox.show("文件导入成功。", "操作提示", Messagebox.OK, Messagebox.NONE);

			/*
			 * try {
			 * 
			 * String[] message = dobll.uploadFinance(str1[0], str2[0], str1[1],
			 * str2[1], ownmonth, UserInfo.getUsername(), str1[2], str2[2],
			 * checkSingle(str1[3]), str1[3], str1[4]);
			 * 
			 * 
			 * if ("1".equals(message[0])) { // 操作台账后补交数据 str3 =
			 * OperateFile3(this.media[1].getName(),checkSingle(str1[3])); if
			 * (str3.length != 0) { dobll.uploadFinanceSZSIBJ(str2[0], ownmonth,
			 * checkSingle(str1[3]), UserInfo.getUsername(), str2[1], str2[2]);
			 * }
			 * 
			 * Messagebox.show(message[1], "操作提示", Messagebox.OK,
			 * Messagebox.NONE); win.detach(); } else {
			 * Messagebox.show(message[1], "操作提示", Messagebox.OK,
			 * Messagebox.ERROR); } } catch (Exception e) {
			 * Messagebox.show("文件已上传，导入数据库出错。", "操作提示", Messagebox.OK,
			 * Messagebox.ERROR); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("导入出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 处理社保局档案文件
	private String[] OperateFile1(String name) {
		String[] str = new String[5];
		String filename = name;
		String fileAllname = absolutePath + uploadfolder + name;
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
			if ("单位正常在册人员名单".equals(sheetname)) {
				shebaoid = st.getCell(0, 1).getContents().replace("单位编号：", "");
				company = st.getCell(3, 1).getContents().replace("单位名称：", "");
				st.setName("sheet1"); // 修改名字为sheet1
				sheetname = st.getName();

				// 根据社保编号检查是否已有上传的台账
				if (dobll.existsSZSIByShebaoID(Integer.parseInt(shebaoid))) {
					// 移除表头以上行
					st.removeRow(0);
					st.removeRow(0);
					st.removeRow(0);
					book.write();
					// 记录文件信息
					str[0] = sheetname;
					str[1] = filename;
					str[2] = fileAllname;
					str[3] = shebaoid;
					str[4] = company;

					String[] message = new String[3];
					int i = 1;
					while (st.getCell(0, i).getContents().trim() != null
							&& !"".equals(st.getCell(0, i).getContents().trim())) {// 判断序号是否为空，为空则表示数据到最后一条
						EmShebaoUpdateModel m = new EmShebaoUpdateModel();

						m.setOwnmonth(Integer.parseInt(ownmonth));
						m.setEsiu_name(st.getCell(1, i).getContents().trim());
						m.setEsiu_computerid(st.getCell(2, i).getContents()
								.trim());
						m.setEsiu_lbid(st.getCell(3, i).getContents().trim());
						m.setSex(st.getCell(4, i).getContents().trim());
						m.setEsiu_idcard(st.getCell(5, i).getContents().trim());
						m.setEsiu_hj(st.getCell(6, i).getContents().trim());
						m.setEsiu_radix(Integer.parseInt(st.getCell(7, i)
								.getContents().trim()));
						m.setEsiu_yl(st.getCell(9, i).getContents().trim());
						m.setEsiu_yltype(st.getCell(10, i).getContents().trim());
						m.setEsiu_gs(st.getCell(11, i).getContents().trim());
						m.setEsiu_sye(st.getCell(12, i).getContents().trim());
						m.setEsiu_syu(st.getCell(13, i).getContents().trim());
						m.setEsiu_shebaoid(Integer.parseInt(shebaoid));

						// 调用方法
						message = dobll.uploadFinanceSZSIFee(m, filename);

						i = i + 1;
					}

					// 记录上传文件路径
					EmShebaoSZSIFileModel szsiFileM=new EmShebaoSZSIFileModel();
					szsiFileM.setEssf_shebaoid(shebaoid);
					szsiFileM.setEssf_type(1);
					szsiFileM.setEssf_fileurl(fileAllname);
					szsiFileM.setEssf_filename(filename);
					szsiFileM.setEssf_addname(UserInfo.getUsername());
					dobll.addShebaoSZSIFile(szsiFileM);

				} else {
					Messagebox.show("此公司台帐已经上传，不能重复上传，如需更新请删除原来台帐！", "操作提示",
							Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("档案文件格式错误，请确认文件。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
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

	// 处理社保局扣款文件
	private String[] OperateFile2(String name) {
		String[] str = new String[5];
		String filename = name;
		String fileAllname = absolutePath + uploadfolder + name;
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

			// 根据社保编号检查是否已有上传的台账
			if (dobll.existsSZSIByShebaoID(Integer.parseInt(shebaoid))) {
				// 检查工作表名称
				if ("五险合一".equals(sheetname)) {
					shebaoid = st.getCell(0, 1).getContents()
							.replace("单位编号：", "");
					company = st.getCell(9, 1).getContents()
							.replace("单位名称：", "");
					st.setName("sheet1"); // 修改名字为sheet1
					sheetname = st.getName();
					// 更改表头
					st.addCell(new Label(7, 4, "养老保险基数"));
					st.addCell(new Label(8, 4, "养老个人交"));
					st.addCell(new Label(9, 4, "养老单位交"));
					st.addCell(new Label(10, 4, "医疗保险基数"));
					st.addCell(new Label(11, 4, "医疗个人交"));
					st.addCell(new Label(12, 4, "医疗单位交"));
					st.addCell(new Label(13, 4, "工伤保险基数"));
					st.addCell(new Label(14, 4, "工伤单位交"));
					st.addCell(new Label(15, 4, "失业保险基数"));
					st.addCell(new Label(16, 4, "失业个人交"));
					st.addCell(new Label(17, 4, "失业单位交"));
					st.addCell(new Label(18, 4, "生育保险基数"));
					st.addCell(new Label(19, 4, "生育单位交"));
					// 移除表头以上行
					st.removeRow(0);
					st.removeRow(0);
					st.removeRow(0);
					st.removeRow(0);
					book.write();
					// 记录文件信息
					str[0] = sheetname;
					str[1] = filename;
					str[2] = fileAllname;
					str[3] = shebaoid;
					str[4] = company;

					String[] message = new String[3];
					int i = 1;
					while (st.getCell(13, i).getContents().trim() != null
							&& !"".equals(st.getCell(13, i).getContents()
									.trim())) {// 判断生育是否为空，为空则表示数据到最后一条
						EmShebaoUpdateModel m = new EmShebaoUpdateModel();

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
					
					//记录上传文件
					EmShebaoSZSIFileModel szsiFileM=new EmShebaoSZSIFileModel();
					szsiFileM.setEssf_shebaoid(shebaoid);
					szsiFileM.setEssf_type(2);
					szsiFileM.setEssf_fileurl(fileAllname);
					szsiFileM.setEssf_filename(filename);
					szsiFileM.setEssf_addname(UserInfo.getUsername());
					dobll.addShebaoSZSIFile(szsiFileM);
				} else {
					Messagebox.show("扣款文件格式错误，请确认文件。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("此公司台帐已经上传，不能重复上传，如需更新请删除原来台帐！", "操作提示",
						Messagebox.OK, Messagebox.INFORMATION);
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
		String fileAllname = absolutePath + uploadfolder + name;
		String sheetname = "";
		int i = 0;

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

			i = i + 8;

			// 检查是否有补交数据
			if (st.getCell(0, i).getContents().length() > 0) {

				// 移除表头以上行
				for (int j = 0; j < i; j++) {
					st.removeRow(0);
				}
				book.write();
				// 记录文件信息
				str[0] = sheetname;
				str[1] = filename;
				str[2] = fileAllname;

				String[] message = new String[3];
				int k = 1;
				while (st.getCell(0, k).getContents().trim() != null
						&& !"".equals(st.getCell(0, k).getContents().trim())) {// 判断序号是否为空，为空则表示数据到最后一条
					EmShebaoBJModel m = new EmShebaoBJModel();

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

	// 文件上传服务器
	private boolean uploadFile(Media m, String filename) {
		boolean bool = false;
		try {
			if (FileOperate.upload(m, uploadfolder + filename)) {
				bool = true;
			}
		} catch (Exception e) {

		}
		return bool;
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

	public String[] getUploadFlieName() {
		return uploadFlieName;
	}

}
