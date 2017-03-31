package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.Taskflow.TaskListDal;
import dal.Taskflow.Task_controlDal;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.EmHouse.EmHouseChangeGjjConfirmImpl;
import bll.EmHouse.EmHouseChangeGjjImpl;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_EditImpl;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Model.LoginModel;
import Model.TaskProcessModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmHouse_ChangeGjjConfirmController {
	private List<EmHouseChangeGJJModel> list = new ListModelList<>();
	private List<EmHouseChangeGJJModel> list2 = new ListModelList<>();
	private EmHouseChangeGJJModel ecm = new EmHouseChangeGJJModel();
	private EmHouse_EditBll bll = new EmHouse_EditBll();

	private Integer daid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer taprId = 0;

	private Window win;

	public EmHouse_ChangeGjjConfirmController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			taprId = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
		}
		setList(daid);
		if (list.size() > 0) {
			ecm = list.get(0);

		}

	}

	@Command
	public void submit() {
		Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
							Grid grid = (Grid) win.getFellow("docGrid");
							String[] message = docOC.UpsubmitDoc(grid, ecm
									.getEhcg_id().toString());
							if (message[0].equals("1")) {
								Integer i = 0;

								ecm.setEhcg_ifdeclare(0);
								Object[] obj = { "确认数据", ecm };
								WfBusinessService wfbs = new EmHouseChangeGjjConfirmImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								String[] str = new String[5];
								ecm.setEhcg_addname(UserInfo.getUsername());
								if (ecm.getEhcg_tapr_id() != null) {
									
									str = wfs.PassToNext(obj, taprId,
											UserInfo.getUsername(), "",
											ecm.getCid(), "");
								} else {

									str = wfs
											.AddTaskToNext(
													obj,
													"员工公积金交单变更",
													ecm.getOwnmonth()
															+ ecm.getEhcg_name()
															+ "("
															+ ecm.getGid()
															+ ")变更公积金信息", 56,
													UserInfo.getUsername(), ecm.getEhcg_addname()+"添加数据",
													ecm.getCid(), "");

									if (str[0].equals("1")) {
										Messagebox.show("提交成功.", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										win.detach();
									} else {
										Messagebox
												.show("操作失败.", "操作提示",
														Messagebox.OK,
														Messagebox.ERROR);
									}

								}
							} else {
								Messagebox.show("材料修改出错。", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});

	}

	@Command
	public void back() {

		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		map.put("id", ecm.getEhcg_id());// 业务表id
		map.put("tablename", "emhousechangegjj");// 业务表名

		if (ecm.getEhcg_ifdeclare().equals(0)
				|| ecm.getEhcg_ifdeclare().equals(3)
				|| ecm.getEhcg_ifdeclare().equals(4)) {
			ecm.setEhcg_ifdeclare(3);

			map.put("clazz", new EmHouse_EditBll());
			map.put("method", "returnGjj");
			map.put("pclass", EmHouseChangeGJJModel.class);
			map.put("parameter", ecm);
			map.put("checkName", "退回");
		}

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(ecm.getCoba_client());

		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		win.detach();
	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();

		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ecm.getGid() + "",
				"雇员服务中心");
	}

	@Command
	public void winC(@BindingParam("a") Window w) {
		win = w;
	}

	public List<EmHouseChangeGJJModel> getList() {
		return list;
	}

	public void setList(Integer id) {
		this.list = bll.getGjjList(id);
	}

	public List<EmHouseChangeGJJModel> getList2() {
		return list2;
	}

	public void setList2(Integer id) {
		this.list2 = bll.getGjjReList(id);
	}

	public EmHouseChangeGJJModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeGJJModel ecm) {
		this.ecm = ecm;
	}

}
