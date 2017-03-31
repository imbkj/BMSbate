package Controller.EmHouse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import bll.EmHouse.EmHouse_UpLoadBll;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmHouse_UpLoadController {

	private Media[] media;
	private String uploadFlieName;
	private String absolutePath;
	private String username = UserInfo.getUsername();

	private EmHouse_UpLoadBll bll = new EmHouse_UpLoadBll();
	private MonthListUtil mlu = new MonthListUtil();

	private DateStringChange dsc = new DateStringChange();
	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	// 所属月份下拉框
	private String[] s_ownmonth = mlu.getMonthList(true, nowmonth, 2, 9);

	// 获取台帐(更新前:1/更新后)参数
	private Integer m = Integer.valueOf(Executions.getCurrent().getArg()
			.get("m").toString());

	private Window win = (Window) Path.getComponent("/winUpload");

	public EmHouse_UpLoadController() {

	}

	// 文件检查
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		for (int i = 0; i < media.length; i++) {

			if ("xls".equals(media[i].getFormat()) || "xlsx".equals(media[i].getFormat())) {
				uploadFlieName = media[i].getName();

				absolutePath = FileOperate.getAbsolutePath();
				String uploadfolder = m.equals(1) ? "EmHouse/File/UpLoad/FinanceMonth/"
						: "EmHouse/File/UpLoad/Finance/";
				String uploadName = mosaicFileName();

				if (FileOperate.upload(media[i], uploadfolder + uploadName)) {

					Grid gd = (Grid) win.getFellow("grid");
					final Row rw = new Row();
					Cell cell1 = new Cell();
					Cell cell2 = new Cell();
					Cell cell3 = new Cell();
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
					Progressmeter p = new Progressmeter();
					cell3.appendChild(p);
					rw.appendChild(cell1);
					rw.appendChild(cell2);
					rw.appendChild(cell3);
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

	// 提交并导入数据
	@Command("upload")
	public void upload() {
		File file = null;
		Label lbl = null;
		Integer n = 0;
		Combobox ownmonth = (Combobox) win.getFellow("ownmonth");
		try {
			if (ownmonth.getSelectedItem() != null) {
				if (media != null) {
					Grid gd = (Grid) win.getFellow("grid");
					if (gd.getRows().getChildren().size() > 2) {
						String uploadfolder = m.equals(1) ? "EmHouse/File/UpLoad/FinanceMonth/"
								: "EmHouse/File/UpLoad/Finance/";
						for (int i = 2; i < gd.getRows().getChildren().size(); i++) {
							lbl = (Label) gd.getCell(i, 0).getChildren().get(1);
							Progressmeter p = (Progressmeter) gd.getCell(i, 2).getChildren().get(0);
							file = new File(absolutePath + uploadfolder
									+ lbl.getValue());
							p.setValue(0);
							for (int j = 0; j < 90; j++) {
								p.setValue(j);
							}
							n = bll.addFiledata(file, Integer.valueOf(ownmonth
									.getSelectedItem().getLabel()), m, username,p);
							
							
							if (n == 0) {
								// Messagebox.show("数据录入异常", "操作提示",
								// Messagebox.OK, Messagebox.ERROR);
								return;
							}else {
								p.setValue(100);
								
							}
						}
						bll.updateData(m);
					} else {
						Messagebox.show("请选择要上传的文件", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}

			} else {
				Messagebox.show("请选择所属月份。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据导入出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		if (n > 0) {
			Messagebox.show("导入成功", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		}

	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + uploadFlieName;
		return name;
	}

	public String getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(String nowmonth) {
		this.nowmonth = nowmonth;
	}

	public String[] getS_ownmonth() {
		return s_ownmonth;
	}

	public void setS_ownmonth(String[] s_ownmonth) {
		this.s_ownmonth = s_ownmonth;
	}

	public Media[] getMedia() {
		return media;
	}

	public void setMedia(Media[] media) {
		this.media = media;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}

}
