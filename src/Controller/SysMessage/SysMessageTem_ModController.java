package Controller.SysMessage;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SysMessage.SysMessageTem_AddBll;
import bll.SysMessage.SysMessageTem_ManageBll;

import Model.SysMessageModel;
import Model.WfClassModel;

public class SysMessageTem_ModController {

	private String pmte_model = "";
	private String pmte_class = "";
	private String pmte_content = "";
	private String pmte_type = "";
	private List<WfClassModel> wfclassList = new ListModelList<WfClassModel>();

	SysMessageModel getarg = (SysMessageModel) Executions.getCurrent().getArg()
			.get("model");

	// 初始化
	public SysMessageTem_ModController() {
		SysMessageTem_AddBll bll = new SysMessageTem_AddBll();
		setWfclassList(bll.getwfclassList());
		pmte_model = getarg.getPmte_model();
		pmte_class = getarg.getPmte_class();
		pmte_content = getarg.getPmte_content();
		pmte_type = getarg.getPmte_type();
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win) throws Exception {
		if (pmte_model.isEmpty()) {
			Messagebox
					.show("名称不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {

			SysMessageModel model = new SysMessageModel();
			SysMessageTem_ManageBll bll = new SysMessageTem_ManageBll();
			model.setPmte_model(pmte_model);
			model.setPmte_class(pmte_class);
			model.setPmte_content(pmte_content);
			model.setPmte_id(getarg.getPmte_id());
			model.setPmte_type(pmte_type);

			int row = bll.updateTem(model);

			if (row == 1) {
				if (Messagebox.show("更新成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					win.detach();
				}
			} else {
				Messagebox.show("更新失败,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
		}
	}

	public String getPmte_model() {
		return pmte_model;
	}

	public void setPmte_model(String pmte_model) {
		this.pmte_model = pmte_model;
	}

	public String getPmte_class() {
		return pmte_class;
	}

	public void setPmte_class(String pmte_class) {
		this.pmte_class = pmte_class;
	}

	public String getPmte_content() {
		return pmte_content;
	}

	public void setPmte_content(String pmte_content) {
		this.pmte_content = pmte_content;
	}

	public List<WfClassModel> getWfclassList() {
		return wfclassList;
	}

	public void setWfclassList(List<WfClassModel> wfclassList) {
		this.wfclassList = wfclassList;
	}

	public String getPmte_type() {
		return pmte_type;
	}

	public void setPmte_type(String pmte_type) {
		this.pmte_type = pmte_type;
	}
}
