package Controller.EmBodyCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmbodyCheckFileModel;
import Util.FileOperate;
import bll.EmBodyCheck.EmBcFile_selectBll;

public class Embc_FileListController {
	private EmBcFile_selectBll bll = new EmBcFile_selectBll();

	private List<EmbodyCheckFileModel> list = bll
			.getEmbodyCheckFileList(" and file_state=1");

	@Command
	public void downloadfile(@BindingParam("model") EmbodyCheckFileModel model) {
		FileOperate.download(model.getFile_url());
	}

	@Command
	@NotifyChange("list")
	public void edit(@BindingParam("model") EmbodyCheckFileModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"Embc_FileEdit.zul", null, map);
		window.doModal();
		list = bll.getEmbodyCheckFileList(" and file_state=1");
	}

	public List<EmbodyCheckFileModel> getList() {
		return list;
	}

	public void setList(List<EmbodyCheckFileModel> list) {
		this.list = list;
	}

}
