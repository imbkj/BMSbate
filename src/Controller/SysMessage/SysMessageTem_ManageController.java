package Controller.SysMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SysMessage.SysMessageTem_ManageBll;

import Model.SysMessageModel;

public class SysMessageTem_ManageController {

	private List<SysMessageModel> temList = new ListModelList<SysMessageModel>();

	// 初始化
	public SysMessageTem_ManageController() {
		SysMessageTem_ManageBll bll = new SysMessageTem_ManageBll();
		setTemList(bll.gettemList(""));
	}
	
	//删除
	@Command("delete")
	@NotifyChange("temList")
	public void delete(@BindingParam("id") int pmte_id){
		if (Messagebox.show("是否确定删除!", "CONFIRM", Messagebox.YES|Messagebox.NO,
				Messagebox.QUESTION) == Messagebox.YES) {
			SysMessageTem_ManageBll bll = new SysMessageTem_ManageBll();
			int row = bll.deleteTem(pmte_id);
			if(row == 1){
				if (Messagebox.show("删除成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES){
					setTemList(bll.gettemList(""));
				}
			} else {
				Messagebox.show("删除失败,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
		}
	}

	// 弹窗
	@Command("mod")
	@NotifyChange("temList")
	public void mod(@BindingParam("each") SysMessageModel model) {
		Map map = new HashMap();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessageTem_Mod.zul", null, map);
		window.doModal();
		SysMessageTem_ManageBll bll = new SysMessageTem_ManageBll();
		setTemList(bll.gettemList(""));
	}

	public List<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(List<SysMessageModel> temList) {
		this.temList = temList;
	}

}
