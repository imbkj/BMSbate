package Controller.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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

import service.WorkflowCore.WfOperateService;

import bll.EmBenefit.EmActy_compactAddImpl;
import bll.EmBenefit.EmActy_compactBll;
import bll.EmHouse.EmHouse_UpLoadBll;
import Model.EmActyCompactModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmActy_UpLoadController {

	private Media[] media;
	private String uploadFlieName;
	private String absolutePath;

	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private EmActy_compactBll bll = new EmActy_compactBll();
	private Window win = (Window) Path.getComponent("/winUpload");

	public EmActy_UpLoadController() {

	}

	// 文件检查
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		for (int i = 0; i < media.length; i++) {
			if ("doc".equals(media[i].getFormat())) {
				uploadFlieName = media[i].getName();

				absolutePath = FileOperate.getAbsolutePath();
				String uploadfolder = "OfficeFile/UpLoad/EmBenefit/";
				String uploadName = mosaicFileName();

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

					for (int j = 1; j < gd.getRows().getChildren().size(); j++) {

						if (media[i].getName().equals(
								((Label) gd.getCell(j, 0).getChildren().get(0))
										.getValue())) {

							gd.getRows().removeChild(
									gd.getRows().getChildren().get(j));
						}

					}

					gd.getRows().appendChild(rw);
				}
			}else {
				Messagebox.show("上传文件格式有误,后缀名应为doc.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 提交并导入数据
	@Command("upload")
	public void upload(@ContextParam(ContextType.VIEW) Component view) {
		File file = null;
		Label lbl = null;
		Integer n = 0;
		try {

			if (media != null) {
				Grid gd = (Grid) win.getFellow("grid");
				if (gd.getRows().getChildren().size() > 1) {
					String uploadfolder = "OfficeFile/UpLoad/EmBenefit";
					for (int i = 1; i < gd.getRows().getChildren().size(); i++) {
						lbl = (Label) gd.getCell(i, 0).getChildren().get(1);

						file = new File(absolutePath + uploadfolder
								+ lbl.getValue());
						bll.writeDoc(id, lbl.getValue());
						EmActyCompactModel eacm = new EmActyCompactModel();
						eacm.setEaco_id(id);
						eacm.setEaco_state(3);
						Object[] obj = { 2, eacm };
						// 执行工作流
						
						WfOperateService wf = new WfOperateImpl(
								new EmActy_compactAddImpl());
						EmActyCompactModel em = new EmActyCompactModel();
						em.setEaco_id(id);
						List<EmActyCompactModel> list = bll.getList(em, false);
			
						wf.AddTaskToNext(obj, "供应商合同", list.get(0).getEaco_name() + "("
								+ list.get(0).getEaco_compactid() + ")", 76,
								UserInfo.getUsername(), "", 0, "");
						list = bll.getList(em, false);
						wf.PassToNext(obj, list.get(0).getEaco_tapr_id(), UserInfo.getUsername(), "", 0, "");
						n = 1;
					}
				} else {
					Messagebox.show("请选择要上传的文件", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}else {
				Messagebox.show("请选择要上传的文件", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
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
		String name = nowtime+".doc";
		return name;
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
