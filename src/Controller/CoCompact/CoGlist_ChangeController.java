package Controller.CoCompact;

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
import org.zkoss.zk.ui.Executions;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.sun.java.swing.plaf.windows.resources.windows;

import Controller.systemWindowController;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoCompact.Compact_DetailBll;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;

public class CoGlist_ChangeController {
	private Compact_DetailBll cocoBll = new Compact_DetailBll();
	private Media media;
	private String filename;
	private CoCompactSA_OperateBll ccsaBll = new CoCompactSA_OperateBll();

	// 变更产品费用
	@Command("co_changeok")
	public void co_changeok(@BindingParam("coli_id") Label coli_id,
			@BindingParam("coli_fee") Textbox coli_fee,
			@BindingParam("in_date") Datebox in_date) throws Exception {
		String inn_date = "";
		if (in_date.getValue() != null) {
			inn_date = cocoBll.DatetoSting(in_date.getValue());
		}

		String ch_message = cocoBll.changeco(coli_id.getValue(),
				coli_fee.getValue(), inn_date);

		if (!ch_message.equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// Executions.sendRedirect("Compact_SginList.zul");
						// //跳转页面
						// w1.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show("操作成功", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox.show("操作失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 变更产品费用
	@Command("co_stopok1")
	public void co_stopok1(@BindingParam("coli_id") Label coli_id,
			@BindingParam("coli_fee") Textbox coli_fee,
			@BindingParam("st_date") Datebox in_date) throws Exception {
		String inn_date = "";
		if (in_date.getValue() != null) {
			inn_date = cocoBll.DatetoSting(in_date.getValue());
		}

		String st_message = cocoBll.stopco(coli_id.getValue(),
				coli_fee.getValue(), inn_date);

		if (!st_message.equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// Executions.sendRedirect("Compact_SginList.zul");
						// //跳转页面
						// w1.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show("操作成功", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox.show("操作失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("browse")
	@NotifyChange("filename")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if (media.getFormat().equals("txt")||media.getFormat().equals("xls")||media.getFormat().equals("xlsx")||media.getFormat().equals("jpg")||media.getFormat().equals("gid")||media.getFormat().equals("pdf")) {
			this.media = null;
			Messagebox.show("上传文件出错，只能上传doc后缀的WORD文档。", "操作提示", Messagebox.OK,
					Messagebox.NONE);

		} else {
			setFilename(media.getName());
		}
	}

	@Command("upload")
	@NotifyChange("picList")
	public void upload(@BindingParam("cocoid") Label coid,
			@BindingParam("coli_id") Label coli_id,
			@BindingParam("coli_fee") Textbox coli_fee,
			@BindingParam("w1") final Window w1,
			@BindingParam("in_date") Datebox in_date) throws Exception {

		try {
			int coco_id;
			coco_id = Integer.parseInt(coid.getValue());
			String doin_id;
			
			
			
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

					String inn_date = "";
					if (in_date.getValue() != null) {
						inn_date = cocoBll.DatetoSting(in_date.getValue());
					}

					String ch_message = cocoBll.changeco(coli_id.getValue(),
							coli_fee.getValue(), inn_date);

					// 调用合同签回方法
					String[] message = ccsaBll.DocFileUpload(coco_id, 0,
							inn_date, name, FileOperate.getFileSize(media),
							username, Integer.parseInt(coli_id.getValue()),
							"费用调整");
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
						//Executions
						//		.sendRedirect("../CoCompact/Compact_Manager.zul");
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

	// 产品终止
	@Command("co_stopok")
	@NotifyChange("picList")
	public void co_stopok(@BindingParam("cocoid") Label coid,
			@BindingParam("coli_id") Label coli_id,
			@BindingParam("coli_fee") Label coli_fee,
			@BindingParam("w1") final Window w1,
			@BindingParam("st_date") Datebox st_date) throws Exception {
		
		int coco_id;
		coco_id = Integer.parseInt(coid.getValue());
		String doin_id;

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

					String inn_date = "";
					if (st_date.getValue() != null) {
						inn_date = cocoBll.DatetoSting(st_date.getValue());
					}

					String ch_message = cocoBll.stopco(coli_id.getValue(),
							coli_fee.getValue(), inn_date);
					
					// 调用合同签回方法
					String[] message = ccsaBll.DocFileUpload(coco_id, 0,
							inn_date, name, FileOperate.getFileSize(media),
							username, Integer.parseInt(coli_id.getValue()),
							"产品终止");
					// 弹出提示并跳转页面
					if (message[0].equals("1")) {
						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									// Executions.sendRedirect("Compact_SginList.zul");
									// //跳转页面
									w1.detach();
								}
							}
						};
						// 弹出提示
						Messagebox
								.show(message[1],
										"操作提示",
										new Messagebox.Button[] { Messagebox.Button.OK },
										Messagebox.INFORMATION, clickListener);
						//Executions
						//		.sendRedirect("../CoCompact/Compact_Manager.zul");
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
}
