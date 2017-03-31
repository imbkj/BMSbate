package Controller.Archives;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class Archive_FileOutCliDunController {
	String id = (String) Executions.getCurrent().getArg().get("daid");
	EmArchive_SelectBll bll = new EmArchive_SelectBll();
	EmArchiveDatumModel model = bll.getEmArchiveDatumInfo(" and eada_id=" + id)
			.get(0);

	EmArchiveModel amodel = new EmArchiveModel();
	List<EmArchiveModel> archivelist = bll.getEmArchiveInfo(" and a.gid="
			+ model.getGid());

	public Archive_FileOutCliDunController() {
		if (!archivelist.isEmpty()) {
			amodel = archivelist.get(0);
			setAmodel(amodel);
		}
	}

	// 提交更新
	@Command
	public void summitupdate(@BindingParam("detail") final Window detail) {
		final String sql = "";
		EmArchiveDatum_OperateBll bllo = new EmArchiveDatum_OperateBll();
		EmArchiveDatumModel models = new EmArchiveDatumModel();
		models.setEada_id(model.getEada_id());
		models.setEada_addname(UserInfo.getUsername());
		models.setEada_tapr_id(model.getEada_tapr_id());
		models.setEada_type("客服催缴欠款");
		String[] str = bllo.Accepted(models, sql);

		if (str[0] == "1" || str[0].equals("1")) {
			Messagebox
					.show(str[1], "提示", Messagebox.OK, Messagebox.INFORMATION);
			detail.detach();
		} else {
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "2");
		map.put("gid",model.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public EmArchiveDatumModel getModel() {
		return model;
	}

	public void setModel(EmArchiveDatumModel model) {
		this.model = model;
	}

	public EmArchiveModel getAmodel() {
		return amodel;
	}

	public void setAmodel(EmArchiveModel amodel) {
		this.amodel = amodel;
	}
}
