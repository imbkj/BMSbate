package Controller.Workflow;

import impl.UserInfoImpl;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.UserInfoService;

import Model.LoginModel;
import bll.Workflow.WfNode_DisUserBll;

public class WfNode_DisUserController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final int wfno_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private WfNode_DisUserBll bll = new WfNode_DisUserBll();
	private List<String> depSeledList = bll.getDepartment(wfno_id);;
	private List<String> rolSeledeList = bll.getRole(wfno_id);;
	private List<String> loginSeledList = bll.getUser(wfno_id);;
	private ListModelList<String> depList = new ListModelList<String>(
			bll.getDepartment());
	private ListModelList<String> roleList = new ListModelList<String>(
			bll.getRole());
	private ListModelList<String> loginList = new ListModelList<String>(
			bll.getUser());
	private List<LoginModel> userList;
	@Wire
	private Window winNodeDis;
	@Wire
	private Chosenbox cbDep;
	@Wire
	private Chosenbox cbRole;
	@Wire
	private Chosenbox cbUser;
	@Wire
	private Grid gdUser;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		// 获取初始显示的用户列表
		userList = bll.getUserList(wfno_id);
		cbDep.setModel(depList);
		cbRole.setModel(roleList);
		cbUser.setModel(loginList);
		gdUser.setModel(new ListModelList(userList));
		// 设置chosebox已选内容
		selectModel(depSeledList, depList);
		selectModel(rolSeledeList, roleList);
		selectModel(loginSeledList, loginList);
	}

	// 获取listModel已选内容。
	private void selectModel(List<String> list, ListModelList<String> listModel) {
		for (String str : list) {
			if (listModel.contains(str)) {
				listModel.addToSelection(str);
			}
		}
	}

	@Listen("onSelect = #cbDep,#cbRole,#cbUser")
	public void UpGdUser() {
		Object dep = cbDep.getSelectedObjects();
		Object role = cbRole.getSelectedObjects();
		Object user = cbUser.getSelectedObjects();
		gdUser.setModel(new ListModelList(bll.getUserList(dep, role, user)));
	}

	@Listen("onClick = #btSubmit")
	public void Submit() {
		Object dep = cbDep.getSelectedObjects();
		Object role = cbRole.getSelectedObjects();
		Object user = cbUser.getSelectedObjects();

		try {
			Session session = Executions.getCurrent().getDesktop().getSession();
			UserInfoService adduser = new UserInfoImpl(session);
			String addname = adduser.getUsername();
			// 调用提交方法返回操作信息
			String[] message = bll.UpdateNodeUser(wfno_id, dep, role, user,
					addname);
			if (message[0].equals("1")) {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				winNodeDis.detach();
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("提交信息出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

}
