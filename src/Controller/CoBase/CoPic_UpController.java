package Controller.CoBase;

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
import org.zkoss.zul.Window;

import Model.CI_Insurant_ListModel;
import Model.EmPicModel;
import Util.FileOperate;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.Embase.EmPic_CheckBll;

public class CoPic_UpController {
	private List<EmPicModel> copiclist = new ListModelList<EmPicModel>();
	EmPic_CheckBll bll = new EmPic_CheckBll();
	String cid = "";
	private String rwd = null;
	private String filename;
	private Media media;
	private EmPic_CheckBll ccsaBll = new EmPic_CheckBll();
	
	private ListModelList<EmPicModel> copicclasslist;// 显示所选商保类型

	public CoPic_UpController() throws SQLException {
		cid = Executions.getCurrent().getArg().get("cid").toString();
		if (Executions.getCurrent().getArg().get("rwd") != null) {
			rwd = Executions.getCurrent().getArg().get("rwd").toString();
		}
		copiclist = new ListModelList<EmPicModel>(bll.getcopiclist(cid));// 获取图像内容
		
		copicclasslist = new ListModelList<EmPicModel>(bll.getempicclasslist("公司"));// 获图像类型
	}

	@Command("openurl")
	public void openurl(@BindingParam("a") EmPicModel em) {
		//Executions.sendRedirect("http://192.168.1.8/empic/"
		//		+ em.getEmpic_name());
		
		RedirectUtil u = new RedirectUtil();

        u.indexAddTab("http://192.168.1.8/"+em.getEmpic_url() + "", "图像信息");

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
	@NotifyChange("copiclist")
	public void upload(@BindingParam("pic_class") Combobox pic_class,
			@BindingParam("picWin") Window win) throws Exception {
		try {
			if (this.media != null) {
				// 获取用户名
				String username = UserInfo.getUsername();
				// 编辑文件名
				String path = "PicUp/CoPic/";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String name = path + nowtime + "." + media.getFormat();

				if (pic_class.getValue().equals("")) {
					Messagebox.show("请选择图片类型。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}

				// 图片文件
				if (FileOperate.upload(media, name)) {

					// 记录上传明细
					String[] message = ccsaBll.copic_add(cid,
							pic_class.getValue(),
							nowtime + "." + media.getFormat(), username);
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
						if (rwd != null && rwd.equals("rwd")) {
							win.detach();
						} else {
//							Executions
//									.sendRedirect("../EmBase/Embase_editlist.zul");
							
							copiclist = new ListModelList<EmPicModel>(bll.getcopiclist(cid));// 获取图像内容
						}
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

	public List<EmPicModel> getCopiclist() {
		return copiclist;
	}

	public void setCopiclist(List<EmPicModel> copiclist) {
		this.copiclist = copiclist;
	}

	public ListModelList<EmPicModel> getCopicclasslist() {
		return copicclasslist;
	}

	public void setCopicclasslist(ListModelList<EmPicModel> copicclasslist) {
		this.copicclasslist = copicclasslist;
	}


}
