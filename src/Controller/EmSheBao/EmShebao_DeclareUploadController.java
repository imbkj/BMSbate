package Controller.EmSheBao;

import java.io.File;
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
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.A;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

import Model.EmShebaoDeclareOKModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmShebao_DeclareUploadController {
	MonthListUtil mlu = new MonthListUtil();
	private DateStringChange dsc = new DateStringChange();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private String absolutePath;
	private Media[] media;
	private final String filetype = ".xls";
	private Window win = (Window) Path.getComponent("/winUploadChange");

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	// 所属月份下拉框
	private String[] s_ownmonth = mlu.getMonthList(true, nowmonth, 2, 9);

	private String uploadFlieName;
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("ownmonth") Combobox ownmonth,
			@ContextParam(ContextType.VIEW) Component view) {
		File file = null;
		Workbook wb = null;
		String[] message = new String[3];
		EmShebaoDeclareOKModel m;
		int j = 0;
		int k = 0;

		try {

			if (ownmonth.getSelectedItem() != null) {
				if (this.media != null) {
					absolutePath = FileOperate.getAbsolutePath();
					String uploadfolder = "EmSheBao/File/Upload/Declare/";

					Grid gd = (Grid) win.getFellow("grid");
					if (gd.getRows().getChildren().size() > 3) {

						for (int z = 3; z < gd.getRows().getChildren().size(); z++) {
							Label lbl = null;

							lbl = (Label) gd.getCell(z, 0).getChildren().get(1);

							String uploadName = lbl.getValue();
							file = new File(absolutePath + uploadfolder
									+ lbl.getValue());
							
							// 上传文件至服务器
							//if (FileOperate.upload(media[z], uploadfolder+ uploadName)) {

								file = new File(absolutePath + uploadfolder
										+ uploadName);
								// 读取Excel文件
								wb = Workbook.getWorkbook(file);
								// 读取工作表
								Sheet st = wb.getSheet(0);

								// 循环读取每一行数据
								for (int i = 4; i < st.getRows(); i++) {
									k = k + 1;
									// 获取内容
									// 初始化model
									m = new EmShebaoDeclareOKModel();
									m.setOwnmonth(Integer.parseInt(ownmonth
											.getValue()));
									m.setEsdo_name(st.getCell(1, i)
											.getContents());
									m.setEsdo_computerid(st.getCell(2, i)
											.getContents());
									m.setEsdo_hj(st.getCell(3, i).getContents());
									m.setEsdo_idcard(st.getCell(4, i)
											.getContents());
									m.setEsdo_radix(Integer.parseInt(st
											.getCell(5, i).getContents()));
									m.setEsdo_yl(st.getCell(7, i).getContents());
									m.setEsdo_gs(st.getCell(8, i).getContents());
									m.setEsdo_yltype(st.getCell(9, i)
											.getContents());
									m.setEsdo_sye(st.getCell(10, i)
											.getContents());
									m.setEsdo_oktime(st.getCell(11, i)
											.getContents());
									m.setEsdo_class(st.getCell(12, i)
											.getContents());
									m.setEsdo_filename(uploadName);
									m.setEsdo_addname(username);
									// 插入数据
									message = dobll.uploadDeclareOK(m);

									if (message[0].equals("1")) {
										j = j + 1;
									}
								}

								// 关闭excel
								wb.close();

								// 更新导入数据的户籍类型和医疗类型
								dobll.updateDeclareOK(uploadName);

								// 更新数据
								String msg;
								msg = updateChangeData(uploadName);

								//Messagebox.show(msg, "操作提示", Messagebox.OK,Messagebox.NONE);

								// 关闭窗口
								// win.detach();

							//}
							/*
							 * else { Messagebox.show("文件上传出错。", "操作提示",
							 * Messagebox.OK, Messagebox.NONE); }
							 */

						}
					}

					Messagebox.show("操作成功！", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					// 关闭窗口
					win.detach();

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

	// 更新数据
	public String updateChangeData(String filename) {
		String result;
		List<EmShebaoDeclareOKModel> esdoList;
		int emsc_tapr_id;
		int cid;
		String[] message = new String[3];
		int j = 0;
		int k = 0;

		// 获取插入的EmShebaoDeclareOK数据
		esdoList = dsbll.getEsdoData(filename);

		for (int i = 0; i < esdoList.size(); i++) {
			j = j + 1;

			// 获取emsc_tapr_id
			emsc_tapr_id = dsbll.getTapr_id(esdoList.get(i).getId());
			cid = esdoList.get(i).getCid();

			if (emsc_tapr_id != 0) {
				message = dobll.declareSuccess(esdoList.get(i).getId(),
						emsc_tapr_id, username, cid);
				if (message[0].equals("1")) {
					k = k + 1;
				}
			}
		}

		// if (j == k) {
		result = "操作成功！";
		// } else {
		// result = "部分数据操作不成功，请检查！";
		// }

		return result;
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
				String uploadfolder = "EmSheBao/File/Upload/Declare/";
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
