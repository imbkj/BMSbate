package Controller.Workflow;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.WfDefinationModel;
import Util.UserInfo;
import bll.Workflow.WfDeploymentListBll;

public class WfDeployment_AddController {
	private final int wfbu_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("wfbu_id").toString());

	private final String wfbu_name = Executions.getCurrent().getArg()
			.get("wfbu_name").toString();

	private List<WfDefinationModel> deList;
	private WfDeploymentListBll bll = new WfDeploymentListBll();

	public WfDeployment_AddController() {
		deList = bll.getDefinationExBid(wfbu_id);
	}

	@Command("addBusiness")
	public void addBusiness(@BindingParam("name") Combobox cbName,
			@BindingParam("remark") String remark,
			@BindingParam("win") Window win) {
		if (cbName.getSelectedItem().getValue() != null) {
			try {
				int i = bll.AddWfDeployment(
						wfbu_id,
						Integer.parseInt(cbName.getSelectedItem().getValue()
								.toString()), remark, UserInfo.getUsername());
				if (i == 1) {
					Messagebox.show("流程部署成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else if (i == 2) {
					Messagebox.show("流程重复无法提交。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Messagebox.show("流程部署失败。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} catch (Exception e) {
				Messagebox.show("流程部署出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请先选择任务流程。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}

	}

	public String getWfbu_name() {
		return wfbu_name;
	}

	public List<WfDefinationModel> getDeList() {
		return deList;
	}

}
