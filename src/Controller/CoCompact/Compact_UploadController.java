package Controller.CoCompact;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import com.sun.java.swing.plaf.windows.resources.windows;

import Model.DocumentsInfoModel;
import Model.DocumentsInfoPubPicModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoCompact.Compact_UploadBll;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;

public class Compact_UploadController {
	private final int cid = 1;
	private String filename;
	private List<DocumentsInfoModel> classList;
	private List<DocumentsInfoPubPicModel> outcont;
	private List<DocumentsInfoPubPicModel> picList;
	private Compact_UploadBll bll = new Compact_UploadBll();
	private Media media;
	private CoCompactSA_OperateBll ccsaBll = new CoCompactSA_OperateBll();
	String coco_id = Executions.getCurrent().getArg().get("daid").toString();
	private String conname;

	public Compact_UploadController() throws Exception {
		outcont = bll.getoutcont(coco_id);

		setConname(outcont.get(0).getDoin_content());
		setClassList();
		setPicList();
		setOutcont();
	}

	@Command("browse")
	@NotifyChange("filename")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul,@BindingParam("in_a") Include in_a) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		System.out.println(media.getFormat());
		if (media.getFormat().equals("txt")||media.getFormat().equals("jpeg")||media.getFormat().equals("pdf")||media.getFormat().equals("zip")||media.getFormat().equals("rar")||media.getFormat().equals("xls")||media.getFormat().equals("xlsx")) {
			this.media = null;
			in_a.setVisible(false);
			Messagebox.show("无法上传其它类型文件，只能上传word文档。", "操作提示", Messagebox.OK,
					Messagebox.NONE);

		} else {
			setFilename(media.getName());
		}
	}

	@Command("upload")
	//@NotifyChange("picList")
	public void upload(@BindingParam("class") Radiogroup cb,
			@BindingParam("cocoid") Label coid,@BindingParam("tarp_id") Label tarp_id,
			@BindingParam("w1") Window w1,@BindingParam("in_a") Include in_a) throws Exception {
		System.out.print(Integer.parseInt(coid.getValue()));
		int coco_id;
		coco_id = Integer.parseInt(coid.getValue());
		String doin_id;
		try {
			doin_id = cb.getSelectedItem().getValue();
		} catch (Exception e) {
			Messagebox
					.show("请选择文件的类型。", "操作提示", Messagebox.OK, Messagebox.NONE);
			return;
		}
		try {
			if (this.media != null) {
				// 获取用户名
				String username = UserInfo.getUsername();
				// 编辑文件名
				String path = "OfficeFile/DownLoad/CoCompact/";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String name = path + nowtime + doin_id + coco_id + "."
						+ media.getFormat();
				// 上传文件
				if (FileOperate.upload(media, name)) {
					/*
					if (bll.DocFileUpload(coco_id, 0, doin_id, name,
							FileOperate.getFileSize(media), username)) {
						Messagebox.show("文件上传成功。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
						setPicList();
					}*/
					
					// 调用合同签回方法
					String[] message = ccsaBll.UDocFileUpload(coco_id, tarp_id.getValue(),
							doin_id, nowtime + doin_id + coco_id + "."+ media.getFormat(), FileOperate.getFileSize(media),
							username);
					// 弹出提示并跳转页面
					if (message[0].equals("1")) {
						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									// Executions.sendRedirect("Compact_SginList.zul");
									// //跳转页面
									// w1.detach();
								}
							}
						};
						// 弹出提示
						//w1.detach();
						in_a.setVisible(false);
						Messagebox
								.show(message[1],
										"操作提示",
										new Messagebox.Button[] { Messagebox.Button.OK },
										Messagebox.INFORMATION, clickListener);
						//Executions.sendRedirect("Compact_AddSelect.zul");
						w1.detach();
					} else {
						// 弹出提示
						
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}

		} catch (Exception e) {
			Messagebox.show("文件上传出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<DocumentsInfoModel> getClassList() {
		return classList;
	}

	public void setClassList() {
		try {
			classList = bll.getDocClassByType(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<DocumentsInfoPubPicModel> getPicList() {
		return picList;
	}

	public void setPicList() {
		try {
			picList = bll.getPicById(cid, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<DocumentsInfoPubPicModel> getOutcont() {
		return outcont;
	}

	public void setOutcont() {
		try {
			outcont = bll.getoutcont(coco_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}
	
}
