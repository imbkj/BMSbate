package Controller.Taskflow;

import impl.UserInfoImpl;
import impl.WorkflowCore.WfOperateImpl;
import impl.WorkflowCore.Core.WfFlowControlImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import service.UserInfoService;
import bll.Taskflow.TaskProcess_ViewBll;
import bll.Taskflow.Task_controlBll;
import Model.TaskProcessLogViewModel;
import Model.TaskProcessModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;

public class TaskProcess_ViewController {
	private List<TaskProcessViewModel> listView;
	private List<TaskProcessLogViewModel> listTP;
	private List<TaskProcessLogViewModel> listLog;
	private Window win = (Window) Path.getComponent("/taskWin");
	private Integer id;
	// private Integer
	// id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());
	TaskProcess_ViewBll bll = new TaskProcess_ViewBll();
	private boolean visflag = true;

	public TaskProcess_ViewController() {
		try {
			id = Integer.parseInt(Executions.getCurrent().getParameter("id")
					.toString());
			if (id != null) {
				Task_controlBll tbll = new Task_controlBll();
				TaskProcessModel tmodel = tbll.getNowProcess(id);
				if (tmodel != null) {
					WfFlowControlImpl impl = new WfFlowControlImpl();
					boolean flag = impl.CheckCompetence(tmodel.getTapr_id(),
							UserInfo.getUsername());
					if (!flag) {
						visflag = false;
					}
				} else {
					visflag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (visflag) {
			listView = bll.getViewList(id);
			listTP = bll.geTaskProcessLogViewByTaliId(id);
			listLog = bll.geTaskProcessLogById(id);
		}
	}

	@Command("getInfo")
	@NotifyChange({ "listView", "listTP", "listLog" })
	@SuppressWarnings("java.lang.InterruptedException")
	public void getLink(@BindingParam("model") TaskProcessViewModel tpv,
			@BindingParam("win") Window win) throws SQLException {

		String url = tpv.getWfno_url();

		if (url != null) {
			if (!url.equals("")) {
				if (tpv.getTapr_id() != null) {
					String i1 = tpv.getTapr_id().toString();
					String i2 = tpv.getTapr_dataid().toString();
					TaskProcess_ViewBll tpvbll = new TaskProcess_ViewBll();

					if (tpv.getTapr_state() == 1
							|| (tpv.getWfno_id()
									.toString()
									.equals(tpvbll.getLastId(tpv.getTali_id())
											.toString()) && tpv.getTapr_state() == 2)) {

						Map map = new HashMap<>();
						map.put("id", i1);
						map.put("daid", i2);
						map.put("win", win);

						Window window = (Window) Executions.createComponents(
								url, null, map);
						window.doModal();
						win.detach();
						//listView = bll.getViewList(id);
						//listTP = bll.geTaskProcessLogViewByTaliId(id);
						//listLog = bll.geTaskProcessLogById(id);
						BindUtils.postGlobalCommand(null, null, "refreshWin",
								null);
					}

				}
			}

		}
	}

	@Command("recokeData")
	@NotifyChange("listTP")
	public void recokeData(@BindingParam("a") TaskProcessLogViewModel ltp) {
		Integer i = 0;
		// 获取操作用户
		Session session = Executions.getCurrent().getDesktop().getSession();
		UserInfoService user = new UserInfoImpl(session);

		if (ltp.getWfno_ifrevoke() == 1) {
			if (ltp.getWfno_runprocedure().toString().equals("")) {
				Messagebox.show("未录入撤销步骤存储过程!", "INFORMATION", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (Messagebox.show("是否撤销?", "INFORMATION", Messagebox.YES
						| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
					WfOperateImpl wfcs = new WfOperateImpl(null);
					i = wfcs.revokeToPrev(ltp.getTapr_id(), user.getUsername(),
							null);
					System.out.println(ltp.getTapr_id());
					System.out.println(user.getUsername());

					if (i > 0) {
						Messagebox.show("撤销成功!", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
					} else {
						Messagebox.show("撤销失败!", "INFORMATION", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		} else {
			Messagebox.show("该步骤不允许撤销!", "INFORMATION", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 暂缓处理
	@Command("delay")
	public void delay(@BindingParam("tapr_id") int tapr_id,
			@BindingParam("tali_name") String tali_name,
			@BindingParam("wfno_name") String wfno_name,
			@ContextParam(ContextType.VIEW) Component view) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tapr_id", String.valueOf(tapr_id));
		map.put("tali_name", tali_name);
		map.put("wfno_name", wfno_name);
		Window window = (Window) Executions.createComponents(
				"Task_DelayOp.zul", view, map);
		window.doModal();
	}

	// 刷新列表
	@Command("refreshWin")
	public void refreshWin() {
		BindUtils.postGlobalCommand(null, null, "refreshWin", null);
	}

	public List<TaskProcessViewModel> getListView() {
		return listView;
	}

	public void setListView(List<TaskProcessViewModel> listView) {
		this.listView = listView;
	}

	public List<TaskProcessLogViewModel> getListTP() {
		return listTP;
	}

	public void setListTP(List<TaskProcessLogViewModel> listTP) {
		this.listTP = listTP;
	}

	public boolean isVisflag() {
		return visflag;
	}

	public void setVisflag(boolean visflag) {
		this.visflag = visflag;
	}

	public List<TaskProcessLogViewModel> getListLog() {
		return listLog;
	}

}
