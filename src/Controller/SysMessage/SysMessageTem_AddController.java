package Controller.SysMessage;

import java.util.List;

import impl.UserInfoImpl;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import service.UserInfoService;

import bll.SysMessage.SysMessageTem_AddBll;
import Model.SysMessageModel;
import Model.WfClassModel;

public class SysMessageTem_AddController {

	private String pmte_model = "";
	private String pmte_class = "";
	private String pmte_content = "";
	private String pmte_type = "";
	private List<WfClassModel> wfclassList = new ListModelList<WfClassModel>();

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	// 初始化
	public SysMessageTem_AddController() {
		SysMessageTem_AddBll bll = new SysMessageTem_AddBll();
		setWfclassList(bll.getwfclassList());
	}

	// 提交
	@Command("submit")
	@NotifyChange({ "pmte_class", "pmte_content", "pmte_model", "pmte_type" })
	public void submit() throws Exception {
		if (pmte_model.isEmpty()) {
			Messagebox
					.show("名称不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else if (pmte_content.isEmpty()) {
			Messagebox
					.show("内容不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {

			SysMessageModel model = new SysMessageModel();
			SysMessageTem_AddBll bll = new SysMessageTem_AddBll();
			model.setPmte_model(pmte_model);
			model.setPmte_class(pmte_class);
			model.setPmte_content(pmte_content);
			model.setPmte_addname(user.getUsername());
			model.setPmte_type(pmte_type);

			model = bll.PubMessageTemAdd(model);
			int row = model.getRow();

			if (row == 1) {
				if (Messagebox.show("新增成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					pmte_class = "";
					pmte_content = "";
					pmte_model = "";
					pmte_type = "";
				}
			} else {
				Messagebox.show("新增失败,请联系IT部门!", "ERROR", Messagebox.YES,
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
