package Controller.CoCompact;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.CoCompact.Compact_houseCppBll;
import bll.CoCompact.Compact_houseCppImpl;

import Model.CoCompactCppAduitModel;
import R.CM;
import Util.DateStringChange;
import Util.UserInfo;

public class Compact_houseCppAduitController {
	private List<CoCompactCppAduitModel> clist = new ListModelList<>();
	private CoCompactCppAduitModel cam = new CoCompactCppAduitModel();

	private Integer id = 0;
	private Window win;

	private Compact_houseCppBll bll = new Compact_houseCppBll();

	public Compact_houseCppAduitController() {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			id = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());
			cam.setCoca_id(id);
		}

		clist = bll.search(cam);
		if (clist.size() > 0) {
			cam = clist.get(0);
		}
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void submit() {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							CoCompactCppAduitModel m = new CoCompactCppAduitModel();
							m.setCoca_id(cam.getCoca_id());
							m.setCoca_coco_id(cam.getCoca_coco_id());
							m.setCoca_cpp(cam.getCoca_cpp());
							m.setCoca_houseid(cam.getCoca_houseid());
							m.setCoca_aduit(1);
							m.setCoca_modname(UserInfo.getUsername());
							m.setCoca_modtime(DateStringChange
									.Datestringnow("yyyy-MM-dd HH:mm:ss"));
							m.setCoca_declarename(UserInfo.getUsername());
							m.setCoca_declaretime(DateStringChange
									.Datestringnow("yyyy-MM-dd HH:mm:ss"));
							WfBusinessService wfbs = new Compact_houseCppImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "审核", m };
							String[] str = wfs.PassToNext(obj,
									cam.getCoca_tapr_id(),
									UserInfo.getUsername(), "", 0, "");
							if (str[0].equals("1")) {
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	@Command
	public void back() {
		Messagebox.show("确认退回数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							CoCompactCppAduitModel m = new CoCompactCppAduitModel();
							m.setCoca_id(cam.getCoca_id());
							m.setCoba_company(cam.getCoba_company());
							m.setCoca_backreason(cam.getCoca_backreason());
							m.setCoca_modname(UserInfo.getUsername());
							m.setCoca_modtime(DateStringChange
									.Datestringnow("yyyy-MM-dd HH:mm:ss"));
							m.setCoca_state(0);
							m.setSendName(UserInfo.getUsername());
							m.setReceName(cam.getCoca_addname());
							WfBusinessService wfbs = new Compact_houseCppImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "终止任务", m };
							String[] str = wfs.StopTask(obj,
									cam.getCoca_tapr_id(),
									UserInfo.getUsername(), "退回");
							if (str[0].equals("1")) {
								
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public CoCompactCppAduitModel getCam() {
		return cam;
	}

	public void setCam(CoCompactCppAduitModel cam) {
		this.cam = cam;
	}

}
