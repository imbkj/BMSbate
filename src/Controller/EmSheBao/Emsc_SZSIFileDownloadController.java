package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.EmShebaoChangeSZSIModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_SZSIFileDownloadController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private EmShebaoChangeSZSIModel sbData;
	private String id = Executions.getCurrent().getArg().get("daid").toString();

	private String reason;

	public Emsc_SZSIFileDownloadController() {
		// 获取页面数据
		sbData = dsbll.getEmSCSZSIData(" AND escs_id=" + id, "").get(0);
		reason = "";
	}

	// 下载
	@Command("download")
	public void download() {
		String allPath = "EmSheBao/File/Upload/Declare/"
				+ sbData.getEscs_uploadfile();

		try {
			FileOperate.download(allPath);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 转下一步
	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {
		// 更新数据
		String[] message;
		message = dobll.declareSZSIDownload(sbData);

		// 判断是否成功
		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);

		}
	}

	// 退回
	@Command("szsiReturn")
	public void szsiReturn(@BindingParam("win") Window win) {
		try {
			if (reason != null && !"".equals(reason)) {
				EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
				
				String[] message;
				message = dobll.declareSZSIChangeState(sbData.getEscs_id(),
						sbData.getEmsc_tapr_id(), 3, "",
						UserInfo.getUsername(), sbData.getCid(), reason);
				
				if ("1".equals(message[0])) {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请填写退回原因。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("退回出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmShebaoChangeSZSIModel getSbData() {
		return sbData;
	}

	public void setSbData(EmShebaoChangeSZSIModel sbData) {
		this.sbData = sbData;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
