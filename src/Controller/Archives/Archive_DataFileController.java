package Controller.Archives;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_DataFileBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmArchiveDatumModel;
import Util.UserInfo;

public class Archive_DataFileController {
	private EmArchiveDatumModel edm = new EmArchiveDatumModel();
	private Archive_DataFileBll bll = new Archive_DataFileBll();
	private String username = UserInfo.getUsername();
	private String userId = UserInfo.getUserid();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = null;
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	public Archive_DataFileController() {
		if(Executions.getCurrent().getArg()
				.get("id")!=null)
		{
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		setEdm(eadaId);
	}

	@Command("send")
	public void send() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", edm.getCid().toString());
		map.put("cn", "员工档案");
		map.put("title", "档案调入");
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_Send.zul", null, map);
		window.doModal();
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd) {
		try {
			// 调用内联页方法chkHaveTo(Grid gd)
			String[] message = docOC.UpchkHaveTo(gd);

			// 判断材料必选项是否已选
			if (message[0].equals("1")) {

				Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
						Messagebox.Button.OK, Messagebox.Button.CANCEL },
						Messagebox.QUESTION,
						new EventListener<Messagebox.ClickEvent>() {
							@Override
							public void onEvent(ClickEvent event)
									throws Exception {
								// TODO Auto-generated method stub

								if (Messagebox.Button.OK.equals(event
										.getButton())) {

									Integer i = bll.passFlow(eadaId, tapr_id,
											username);

									if (i > 0) {
										docOC.UpsubmitDoc(gd, eadaId.toString());
										Messagebox.show("提交成功!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} else {
										Messagebox
												.show("操作失败。", "操作提示",
														Messagebox.OK,
														Messagebox.ERROR);
									}

									win.detach();
								}
							}
						});
			}
		} catch (Exception e) {
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Command("modify")
	public void modify(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd) {
		// System.out.print(Path.getComponent("//taskWin"));
		try {
			// 调用内联页方法chkHaveTo(Grid gd)
			String[] message = docOC.UpchkHaveTo(gd);

			// 判断材料必选项是否已选
			if (message[0].equals("1")) {

				Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
						Messagebox.Button.OK, Messagebox.Button.CANCEL },
						Messagebox.QUESTION,
						new EventListener<Messagebox.ClickEvent>() {
							@Override
							public void onEvent(ClickEvent event)
									throws Exception {
								// TODO Auto-generated method stub

								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									// docOC.AddsubmitDoc(gd,
									// eadaId.toString());
									docOC.UpsubmitDoc(gd, eadaId.toString());
									Messagebox.show("修改成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);

									win.detach();
								}
							}
						});
			}
		} catch (Exception e) {
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", eadaId.toString());
		map.put("typeid", "2");
		map.put("gid",edm.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public EmArchiveDatumModel getEdm() {
		return edm;
	}

	public void setEdm(Integer id) {
		this.edm = bll.getemArchiveDatumInfo(id).get(0);
	}

}
