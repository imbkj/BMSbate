package Controller.CoHousingFund;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
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

import bll.CoHousingFund.CoHousingFund_hdUpdateBll;

import Model.CoHousingFundChangeModel;
import Util.FileOperate;

public class CoHousingFund_hdUploadController {
	private Media[] media;
	private String uploadFlieName;
	private String absolutePath;

	private CoHousingFund_hdUpdateBll bll = new CoHousingFund_hdUpdateBll();

	private CoHousingFundChangeModel fm = (CoHousingFundChangeModel) Executions
			.getCurrent().getArg().get("m");

	private Window win = (Window) Path.getComponent("/winUpload");

	public CoHousingFund_hdUploadController() {

	}

	@Command
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		if (media.length > 1) {

		} else {
			for (int i = 0; i < media.length; i++) {

				if ("xls".equals(media[i].getFormat())
						|| "xlsx".equals(media[i].getFormat())) {
					uploadFlieName = media[i].getName();

					absolutePath = FileOperate.getAbsolutePath();
					String uploadfolder = "./OfficeFile/UpLoad/CoHousingFund/";
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

						for (int j = 2; j < gd.getRows().getChildren().size(); j++) {

							if (media[i].getName().equals(
									((Label) gd.getCell(j, 0).getChildren()
											.get(0)).getValue())) {

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

	}

	@Command
	public void upload() {
		if (media != null) {
			File file = null;
			Label lbl = null;
			Grid gd = (Grid) win.getFellow("grid");
			if (gd.getRows().getChildren().size() == 4) {
				String uploadfolder = "./OfficeFile/UpLoad/CoHousingFund/";

				lbl = (Label) gd.getCell(3, 0).getChildren().get(1);

				file = new File(absolutePath + uploadfolder + lbl.getValue());

				Integer i = bll.addFiledata(file,fm);
				if (i>0) {
					Messagebox.show("上传成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				}else {
					Messagebox.show("上传文件出错.", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				Messagebox.show("请选择要上传的文件", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
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

	public CoHousingFundChangeModel getFm() {
		return fm;
	}

	public void setFm(CoHousingFundChangeModel fm) {
		this.fm = fm;
	}

}
