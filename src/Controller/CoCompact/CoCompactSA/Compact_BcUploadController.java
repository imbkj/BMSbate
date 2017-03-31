package Controller.CoCompact.CoCompactSA;

import java.sql.SQLException;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.DocumentsInfoModel;
import Model.DocumentsInfoPubPicModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;
import bll.CoCompact.CoCompactSA.Compact_BcUploadBll;
import bll.CoQuotation.CoQuotationInfoBll;

public class Compact_BcUploadController {
	private final int cid = 1;
	private String filename;
	private List<DocumentsInfoModel> classList;
	private List<DocumentsInfoPubPicModel> picList;
	private Compact_BcUploadBll bll = new Compact_BcUploadBll();
	private Media media;
	private CoCompactSA_OperateBll ccsaBll = new CoCompactSA_OperateBll();
	private List<CoOfferListModel> coofferinfoList;
	int coco_id = ((CoOfferListModel) Executions.getCurrent().getArg()
			.get("cocoM")).getColi_coco_id();

	public Compact_BcUploadController() throws SQLException {
		setClassList();
		setPicList();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				new CoQuotationInfoBll().getCoOfferlist(coco_id + "")));// 报价单详情
	}

	@Command("browse")
	@NotifyChange("filename")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if (media.getFormat().equals("txt")) {
			this.media = null;
			Messagebox.show("无法上传txt文件。", "操作提示", Messagebox.OK,
					Messagebox.NONE);

		} else {
			setFilename(media.getName());
		}
	}

	@Command("upload")
	@NotifyChange("picList")
	public void upload(@BindingParam("class") Datebox cb,
			@BindingParam("cocoid") Label coid,
			@BindingParam("coli_id") Combobox coli_id,
			@BindingParam("co_type") Combobox co_type) throws Exception {

		int coco_id;
		coco_id = Integer.parseInt(coid.getValue());
		String doin_id;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String Date = sdf.format(cb.getValue());
			doin_id = Date;
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
				String name = path + nowtime + coco_id + "."
						+ media.getFormat();
				

				// 上传文件
				if (FileOperate.upload(media, name)) {
					/*
					 * if (bll.DocFileUpload(coco_id, 0, doin_id, name,
					 * FileOperate.getFileSize(media), username)) {
					 * Messagebox.show("文件上传成功。", "操作提示", Messagebox.OK,
					 * Messagebox.NONE); setPicList(); }
					 */

					// 调用合同签回方法
					String[] message = ccsaBll.DocFileUpload(coco_id, 0,
							doin_id, name, FileOperate.getFileSize(media),
							username,Integer.parseInt(coli_id.getSelectedItem().getValue().toString()),co_type.getValue());
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
						Messagebox
								.show(message[1],
										"操作提示",
										new Messagebox.Button[] { Messagebox.Button.OK },
										Messagebox.INFORMATION, clickListener);
						Executions.sendRedirect("../CoCompact/Compact_Manager.zul");
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

	public List<CoOfferListModel> getCoofferinfoList() {
		return coofferinfoList;
	}

	public void setCoofferinfoList(List<CoOfferListModel> coofferinfoList) {
		this.coofferinfoList = coofferinfoList;
	}

}
