package Controller.Archives;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmArchiveModel;
import bll.Archives.EmArchive_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class Archive_InfoUpdateController {
	private String gid = Executions.getCurrent().getArg().get("gid").toString();
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveModel> archivelist = bll.getEmArchiveInfo(" and a.gid="
			+ gid);

	// 选择档案事件
	@Command
	@NotifyChange("model")
	public void changefid(@BindingParam("cb") Combobox cb,
			@BindingParam("win") Window win) {
		if (cb.getValue() != null) {
			if (cb.getSelectedItem() != null) {
				model = cb.getSelectedItem().getValue();
			}
		}
	}

	// 修改提交
	@Command
	public void summit(@BindingParam("win") Window win) {
		if (model.getEmar_fid() != null && !model.getEmar_fid().equals("")) {
			EmArchive_OperateBll obll = new EmArchive_OperateBll();
			Integer k = obll.EmArchiveEdit(model);
			if (k > 0) {
				Messagebox.show("修改成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择档案", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	private EmArchiveModel model = new EmArchiveModel();

	public List<EmArchiveModel> getArchivelist() {
		return archivelist;
	}

	public void setArchivelist(List<EmArchiveModel> archivelist) {
		this.archivelist = archivelist;
	}

	public EmArchiveModel getModel() {
		return model;
	}

	public void setModel(EmArchiveModel model) {
		this.model = model;
	}

}
