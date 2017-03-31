package Controller.EmBaseCompact;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;

import Model.EmBaseCompactListModel;
import Model.EmPicModel;
import Util.FileOperate;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmBaseCompact.EmBaseCompact_MakeListBll;
import bll.EmBaseCompact.EmBaseCompact_OperateBll;

public class EmBaseCompact_UploadController {
	private final int cid = 1;
	private String filename;
	private String vername;
	private String covername;
	private Media media;
	private EmBaseCompact_OperateBll ccsaBll = new EmBaseCompact_OperateBll();

	EmBaseCompact_MakeListBll bll = new EmBaseCompact_MakeListBll();

	private ListModelList<EmBaseCompactListModel> verlist;// 劳动合同版本列表
	
	private ListModelList<EmBaseCompactListModel> temp_down;// 劳动合同版本下载

	private ListModelList<EmBaseCompactListModel> coverlist;// 公司合同版本列表
	
	private ListModelList<EmBaseCompactListModel> emverlist;// 劳动合同版本列表

	@Init
	public void init() throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String paynum = "EM" + nowtime;
		setVername(paynum);

		String copaynum = "CO";
		setCovername(copaynum);
		String cid="";
		
		try {
			cid = Executions.getCurrent().getArg().get("cid").toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

		verlist = new ListModelList<EmBaseCompactListModel>(bll.getverlist());// 劳动合同版本列表
		
		emverlist = new ListModelList<EmBaseCompactListModel>(bll.getemverlist(cid));// 劳动合同非标列表

		coverlist = new ListModelList<EmBaseCompactListModel>(
				bll.getcoverlist("","","",""));// 公司合同版本列表
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
	
	@Command("check_list")
	@NotifyChange("coverlist")
	public void check_list(@BindingParam("ch_name") Textbox ch_name,
			@BindingParam("ch_class") Textbox ch_class,
			@BindingParam("ch_type") Combobox ch_type,
			@BindingParam("ch_state") Combobox ch_state) throws SQLException {
		
		coverlist = new ListModelList<EmBaseCompactListModel>(
				bll.getcoverlist(ch_name.getValue(),ch_class.getValue(),ch_type.getValue(),ch_state.getValue()));// 公司合同版本列表
	}
	

	@Command("upload")
	public void upload(@BindingParam("emcompact_temp") Textbox cb_name,
			@BindingParam("cid") Label cid_id,@BindingParam("w1") Window w1,@BindingParam("compact_type") Combobox compact_type) throws Exception {
		int cid;
		cid = Integer.parseInt(cid_id.getValue());
		String emcompact_temp;
		try {
			emcompact_temp = cb_name.getValue();
		} catch (Exception e) {
			Messagebox
					.show("请录入模板名称。", "操作提示", Messagebox.OK, Messagebox.NONE);
			return;
		}
		try {
			if (this.media != null) {
				// 获取用户名
				String username = UserInfo.getUsername();
				// 编辑文件名
				String path = "OfficeFile/Templet/EmBaseCompact/";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String name = path + "EmbaseCompactTemp" + nowtime + cid + "."
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
					String[] message = ccsaBll.UDocFileUpload(cid,
							emcompact_temp, name,
							FileOperate.getFileSize(media), username,compact_type.getValue());
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
						//Executions.sendRedirect("../CoBase/CoBase_ManagerList.zul");
						RedirectUtil util=new RedirectUtil();
						util.refreshCoUrl("../EmBaseCompact/EmBaseCompact_Upload.zul");//url为跳转页面连接
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

	@Command("ver_upload")
	public void ver_upload(@BindingParam("emcompact_temp") Textbox cb_ver,
			@BindingParam("cid") Label cid_id,@BindingParam("compact_type") Combobox compact_type) throws Exception {
		int cid;
		cid = Integer.parseInt(cid_id.getValue());
		String emcompact_temp;
		try {
			emcompact_temp = cb_ver.getValue();
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
				String path = "OfficeFile/Templet/EmBaseCompact/ver/";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String name = path + "EmbaseCompact" + nowtime + "."
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
					String[] message = ccsaBll.VerUpload(cid, emcompact_temp,
							name, FileOperate.getFileSize(media), username,compact_type.getValue());
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
						Executions.sendRedirect("EmBaseCompact_VerUpload.zul");
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
	
	@Command("temp_down")
	public void temp_down(@BindingParam("coco_type") Combobox coco_type) throws SQLException {
		temp_down = new ListModelList<EmBaseCompactListModel>(bll.gettempdown(coco_type.getValue()));// 劳动合同版本列表下载
		Executions.sendRedirect(temp_down.get(0).getCompany());
	}

	@Command("cover_upload")
	public void cover_upload(@BindingParam("emcompact_temp") Textbox cb_ver,
			@BindingParam("cid") Label cid_id,
			@BindingParam("coco_type") Combobox coco_type) throws Exception {
		int cid;
		cid = Integer.parseInt(cid_id.getValue());
		String emcompact_temp;
		try {
			emcompact_temp = cb_ver.getValue();
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
				String path = "OfficeFile/Templet/CoCompact/ver/";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String name = path + "CoCompact" + nowtime + "."
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
					String[] message = ccsaBll.CoVerUpload(cid, emcompact_temp,
							name, FileOperate.getFileSize(media), username,coco_type.getValue());
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
						Executions.sendRedirect("Compact_VerUpload.zul");
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

	// 查看劳动合同合同
	@Command("veremcompact_check")
	public void veremcompact_check(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("id", emco.getEbco_id());
		arg.put("ver_temp", "1");
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompact_VerCheckDoc.zul", null, arg);
		wnd.doModal();
	}
	
	// 查看劳动合同非标合同
		@Command("otemcompact_check")
		public void otemcompact_check(@BindingParam("emco") EmBaseCompactListModel emco) {
			Map arg = new HashMap();
			arg.put("id", emco.getEbco_id());
			arg.put("ver_temp", "2");
			Window wnd = (Window) Executions.createComponents(
					"../EmBaseCompact/EmBaseCompact_VerCheckDoc.zul", null, arg);
			wnd.doModal();
		}
	
	// 查看合同
		@Command("vercompact_check")
		public void vercompact_check(@BindingParam("emco") EmBaseCompactListModel emco) {
			Map arg = new HashMap();
			arg.put("id", emco.getEbco_id());
			Window wnd = (Window) Executions.createComponents(
					"Compact_VerCheckDoc.zul", null, arg);
			wnd.doModal();
		}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getVername() {
		return vername;
	}

	public void setVername(String vername) {
		this.vername = vername;
	}

	public ListModelList<EmBaseCompactListModel> getVerlist() {
		return verlist;
	}

	public void setVerlist(ListModelList<EmBaseCompactListModel> verlist) {
		this.verlist = verlist;
	}

	public ListModelList<EmBaseCompactListModel> getCoverlist() {
		return coverlist;
	}

	public void setCoverlist(ListModelList<EmBaseCompactListModel> coverlist) {
		this.coverlist = coverlist;
	}

	public String getCovername() {
		return covername;
	}

	public void setCovername(String covername) {
		this.covername = covername;
	}

	public ListModelList<EmBaseCompactListModel> getEmverlist() {
		return emverlist;
	}

	public void setEmverlist(ListModelList<EmBaseCompactListModel> emverlist) {
		this.emverlist = emverlist;
	}

	
}
