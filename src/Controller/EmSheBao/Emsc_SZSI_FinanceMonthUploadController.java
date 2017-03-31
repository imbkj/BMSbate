package Controller.EmSheBao;

import java.io.File;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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

import Model.EmShebaoUpdateModel;
import Util.FileOperate;
import Util.UserInfo;

public class Emsc_SZSI_FinanceMonthUploadController {
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private String absolutePath;
	private Media[] media;
	private String uploadFlieName;
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private Window win = (Window) Path.getComponent("/winUploadChange");

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {

		try {
			if (this.media != null) {

				Grid gd = (Grid) win.getFellow("grid");
				if (gd.getRows().getChildren().size() > 3) {

					absolutePath = FileOperate.getAbsolutePath();
					String uploadfolder = "EmSheBao/File/Upload/FinanceMonth/";
					String uploadName = "";
					String sheetname = "";

					for (int k = 3; k < gd.getRows().getChildren().size(); k++) {

						File file = null;
						Label lbl = null;
						Workbook book = null;
						WritableWorkbook wb = null;

						lbl = (Label) gd.getCell(k, 0).getChildren().get(1);
						file = new File(absolutePath + uploadfolder
								+ lbl.getValue());

						// 读取Excel文件
						book = Workbook.getWorkbook(file);
						wb = Workbook.createWorkbook(file, book);

						// 获得第一个工作表对象
						WritableSheet st = (WritableSheet) wb.getSheet(0);
						st.setName("sheet1"); // 修改名字为sheet1
						sheetname = st.getName();

						uploadName = lbl.getValue();
						String filename = uploadName;
						String fileAllname = absolutePath + uploadfolder
								+ uploadName;

						// 插入数据
						String ownmonth;
						String ifsingle;
						String shebaoid;
						String company;
						int chkfm = 0;

						shebaoid = st.getCell(0, 1).getContents().trim()
								.substring(5);
						company = st.getCell(3, 1).getContents().trim()
								.substring(5);

						ownmonth = dsbll.getShebaoNowmonth();
						ifsingle = dsbll.getSingle(shebaoid);

						chkfm = dsbll.getFinanceMonthCount(shebaoid);

						/*
						 * st.removeRow(0); st.removeRow(0); st.removeRow(0);
						 */

						wb.write();

						if (chkfm > 0 && !shebaoid.equals("167120")) {
							/*
							 * Messagebox.show("此公司台帐已经上传，不能重复上传，如需更新请删除原来台帐！",
							 * "操作提示", Messagebox.OK, Messagebox.ERROR);
							 */
						} else {
							String[] message = new String[3];
							/*
							 * message = dobll.uploadFinanceMonth(sheetname,
							 * filename, ownmonth, username, fileAllname,
							 * ifsingle, shebaoid, company);
							 */

							int i = 4;
							while (st.getCell(0, i).getContents().trim() != null
									&& !"".equals(st.getCell(0, i)
											.getContents().trim())) {// 判断序号是否为空，为空则表示数据到最后一条
								EmShebaoUpdateModel m = null;
								m = new EmShebaoUpdateModel();

								m.setOwnmonth(Integer.parseInt(ownmonth));
								m.setEsiu_name(st.getCell(1, i).getContents()
										.trim());
								m.setEsiu_computerid(st.getCell(2, i)
										.getContents().trim());
								m.setEsiu_lbid(st.getCell(3, i).getContents()
										.trim());
								m.setSex(st.getCell(4, i).getContents().trim());
								m.setEsiu_idcard(st.getCell(5, i).getContents()
										.trim());
								m.setEsiu_hj(st.getCell(6, i).getContents()
										.trim());
								m.setEsiu_radix(Integer.parseInt(st
										.getCell(7, i).getContents().trim()));
								m.setEsiu_yl(st.getCell(9, i).getContents()
										.trim());
								m.setEsiu_yltype(st.getCell(10, i)
										.getContents().trim());
								m.setEsiu_gs(st.getCell(11, i).getContents()
										.trim());
								m.setEsiu_sye(st.getCell(12, i).getContents()
										.trim());
								m.setEsiu_syu(st.getCell(13, i).getContents()
										.trim());
								m.setEsiu_single(Integer.parseInt(ifsingle));
								m.setEsiu_shebaoid(Integer.parseInt(shebaoid));
								m.setEsiu_company(company);

								// 调用方法
								message = dobll.uploadFinanceMonth(m, filename);

								i = i + 1;
							}

							// 更新导入系统的社保台账前的文件的数据的医疗类型
							dobll.updateFinanceMonth(filename);
							// 更新导入系统的社保台账前的文件的数据gid,cid
							dobll.updateFinanceMonthSP(filename);

						}

						// 关闭excel
						book.close();
						wb.close();

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
				String uploadfolder = "EmSheBao/File/Upload/FinanceMonth/";
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

	public String getUploadFlieName() {
		return uploadFlieName;
	}
}
