package Controller.EmSheBao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.EmSheBao.Emsi_OperateDal;

import Model.EmShebaoBJModel;
import Util.FileOperate;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsc_BJFileUploadController {
	private Emsc_DeclareSelBll dsbll = new Emsc_DeclareSelBll();
	private Emsc_DeclareOperateBll dobll = new Emsc_DeclareOperateBll();
	private EmShebaoBJModel bjModel;
	private String id = Executions.getCurrent().getArg().get("daid").toString();

	private String absolutePath;
	private Media media;
	private String uploadFlieName;
	private String filetype;

	public Emsc_BJFileUploadController() {
		bjModel = dsbll.getBjInfoById(Integer.parseInt(id));
	}

	// 浏览文件
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		uploadFlieName = media.getName();
		filetype = media.getFormat();
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		if (this.media != null) {
			absolutePath = FileOperate.getAbsolutePath();
			String uploadfolder = "EmSheBao/File/Upload/Declare/";
			String uploadName = mosaicFileName();

			// 上传文件至服务器
			if (FileOperate.upload(media, uploadfolder + uploadName)) {
				// 更新数据
				bjModel.setEmsb_uploadfile(uploadName);
				String[] message;
				String msg="";
				message = dobll.BjDeclareUpload(bjModel);

				// 判断是否成功
				if (message[0].equals("1")) {
					
					//更新相应的医疗补交数据状态
					Emsi_OperateDal eDal = new Emsi_OperateDal();
					boolean ifJLBJ = eDal.getShebaoBJJL(bjModel.getGid(),
							bjModel.getOwnmonth(), bjModel.getEmsb_startmonth());
					if (ifJLBJ == true) {
						// 通过养老补交获取医疗补交数据
						EmShebaoBJModel jlM = new EmShebaoBJModel();
						Emsi_SelBll bll=new Emsi_SelBll() ;
						jlM = bll.getBjJLListByBJid(bjModel.getId());
						jlM.setEmsb_uploadfile(uploadName);
						
						String[] message2=dobll.BjJLDeclareUpload(jlM);
						if (!"1".equals(message2[0])) {
							msg = "，但是社保医疗数据操作失败，请联系IT部！";
						}
					}
					
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								win.detach();
							}
						}
					};
					// 弹出提示
					Messagebox.show(message[1]+msg, "操作提示",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION, clickListener);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);

				}
			} else {
				Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + "." + filetype;
		return name;
	}

	public EmShebaoBJModel getBjModel() {
		return bjModel;
	}

	public void setBjModel(EmShebaoBJModel bjModel) {
		this.bjModel = bjModel;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}
}
