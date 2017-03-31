package Controller.CoQuotation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoCompact.Compact_DetailBll;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;
import bll.CoQuotation.CoQuotationInfoBll;

import Model.CoOfferListModel;
import Util.FileOperate;
import Util.UserInfo;

public class CoQuotationInfoSelectController {

	private List<CoOfferListModel> coofferinfoList;
	private int coof_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("coofid").toString());
	private Compact_DetailBll cocoBll = new Compact_DetailBll();
	private Media media;
	private String filename;
	private CoCompactSA_OperateBll ccsaBll = new CoCompactSA_OperateBll();

	@Init
	public void init() throws Exception {
		CoQuotationInfoBll bll = new CoQuotationInfoBll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				bll.getcoofferListInfo(coof_id)));

	}

	// 弹出查看页面
	@Command("fp")
	public void fp(@BindingParam("model") CoOfferListModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", model.getColi_coof_id());
		Window window = (Window) Executions.createComponents(
				"/CoBase/CoBase_SelectCoOffer_Detail.zul", null, map);
		window.doModal();
	}

	// 弹出产品费用调整页面
	@Command("co_change")
	@NotifyChange("coofferinfoList")
	public void co_change(@BindingParam("a") CoOfferListModel cocoM)
			throws Exception {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_CoChange.zul", null, map);
		window.doModal();
		CoQuotationInfoBll bll = new CoQuotationInfoBll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				bll.getcoofferListInfo(coof_id)));
	}

	// 弹出产品终止页面
	@Command("co_del")
	@NotifyChange("coofferinfoList")
	public void co_del(@BindingParam("a") CoOfferListModel cocoM)
			throws Exception {
		// 专递cocoM
		Map map = new HashMap();
		map.put("cocoM", cocoM);
		Window window = (Window) Executions.createComponents(
				"../CoCompact/Compact_CoStop.zul", null, map);
		window.doModal();
		CoQuotationInfoBll bll = new CoQuotationInfoBll();
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				bll.getcoofferListInfo(coof_id)));
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
	public void upload(@BindingParam("gridco") Grid gridco,
			@BindingParam("w1") Window w1) throws Exception {
		if (this.media != null) {
			try {
				for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {

					Label coco_id = (Label) gridco.getCell(i, 11).getChildren()
							.get(0);
					System.out.println(coco_id.getValue());

					Label coli_id = (Label) gridco.getCell(i, 12).getChildren()
							.get(0);
					System.out.println(coli_id.getValue());

					Textbox coli_fee = (Textbox) gridco.getCell(i, 2)
							.getChildren().get(0);
					System.out.println(coli_fee.getValue());

					Datebox in_date = (Datebox) gridco.getCell(i, 9)
							.getChildren().get(0);
					System.out.println(in_date.getValue());

					Datebox st_date = (Datebox) gridco.getCell(i, 10)
							.getChildren().get(0);
					System.out.println(st_date.getValue());

					if (this.media != null&&(in_date.getValue()!=null||st_date.getValue()!=null)) {

						// 获取用户名
						String username = UserInfo.getUsername();
						// 编辑文件名
						String path = "OfficeFile/DownLoad/CoCompact/";

						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						Date date = new Date();
						String nowtime = sdf.format(date);
						String name = path + nowtime + coco_id.getValue() + "."
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
								inn_date = cocoBll.DatetoSting(in_date
										.getValue());
							}
							
							String ssd_date = "";
							if (st_date.getValue() != null) {
								ssd_date = cocoBll.DatetoSting(st_date
										.getValue());
							}
							
							String[] message = new String[10];
							String ch_message ="";
							
							if (!inn_date.equals("")) {
								ch_message = cocoBll.changeco(
										coli_id.getValue(),
										coli_fee.getValue(), inn_date);

								// 调用合同签回方法
								message = ccsaBll.DocFileUpload(
										Integer.parseInt(coco_id.getValue()),
										0, inn_date, name,
										FileOperate.getFileSize(media),
										username,
										Integer.parseInt(coli_id.getValue()),
										"费用调整");
							}
							
							if (!ssd_date.equals("")) {
								System.out.println("有终止");
								ch_message = cocoBll.stopco(coli_id.getValue(),
										coli_fee.getValue(), ssd_date);
								
								// 调用合同签回方法
								message = ccsaBll.DocFileUpload(Integer.parseInt(coco_id.getValue()), 0,
										ssd_date, name, FileOperate.getFileSize(media),
										username, Integer.parseInt(coli_id.getValue()),
										"产品终止");
							}
							
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
								// Messagebox
								// .show(message[1],
								// "操作提示",
								// new Messagebox.Button[] {
								// Messagebox.Button.OK },
								// Messagebox.INFORMATION,
								// clickListener);
								// Executions
								// .sendRedirect("../CoCompact/Compact_Manager.zul");
								// w1.detach();
							} else {
								// 弹出提示
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					} else {
						//Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
						//		Messagebox.NONE);
					}
				}
			} catch (Exception e) {
				// Messagebox.show("文件上传出錯。", "操作提示", Messagebox.OK,
				// Messagebox.ERROR);
			}
			Messagebox.show("操作成功！", "操作提示", Messagebox.OK, Messagebox.NONE);
			w1.detach();
		} else {
			Messagebox.show("请选择合同补充协议！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<CoOfferListModel> getCoofferinfoList() {
		return coofferinfoList;
	}

	public void setCoofferinfoList(List<CoOfferListModel> coofferinfoList) {
		this.coofferinfoList = coofferinfoList;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
