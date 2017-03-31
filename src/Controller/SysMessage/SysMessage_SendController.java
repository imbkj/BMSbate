package Controller.SysMessage;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SysMessage.SysMessageTem_ManageBll;
import bll.SysMessage.SysMessage_AddBll;

import Model.SysMessageModel;

public class SysMessage_SendController {
	private ListModelList<SysMessageModel> temList = new ListModelList<SysMessageModel>();
	private SysMessageModel temModel = new SysMessageModel();

	int cid = 0;
	int gid = 0;
	private String className = Executions.getCurrent().getArg().get("cn")
			.toString();
	private String title = Executions.getCurrent().getArg().get("title")
			.toString();

	public SysMessage_SendController() {

		SysMessageTem_ManageBll tbll = new SysMessageTem_ManageBll();

		setTemList(tbll.gettemListByClass(className));
		temList.add(0, new SysMessageModel());
		try {
			gid = Integer.valueOf(Executions.getCurrent().getArg().get("gid")
					.toString());
		} catch (Exception e) {
			cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
					.toString());
		}

	}

	// 选择模板
	@Command("temselect")
	@NotifyChange("temModel")
	public void temselect() {

		temModel.setSyme_title(title);
		temModel.setSyme_content(temModel.getPmte_content());

	}

	// 发送
	@Command("send")
	public void send(@BindingParam("win") Window win) throws Exception {
		if (temModel.getSyme_content() == null
				&& temModel.getSyme_content().equals("")) {
			Messagebox
					.show("内容不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {

			int issuccess = submit(1);
			if (issuccess == 1) {
				if (Messagebox.show("发送成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					win.detach();
				}
			} else {
				Messagebox.show("发送失败,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
		}
	}

	public int submit(int state) throws Exception {
		int issuccess = 0;
		SysMessage_AddBll bll = new SysMessage_AddBll();
		List<SysMessageModel> list = new ListModelList<SysMessageModel>();

		if (gid > 0) {
			temModel.setSymr_log_id(bll.getInfoByGid(gid).get(0).getLog_id());
			temModel.setSymr_name(bll.getInfoByGid(gid).get(0).getCoba_client()
					.toString());
		} else if (cid > 0) {
			temModel.setSymr_log_id(bll.getInfoByCid(cid).get(0).getLog_id());
			temModel.setSymr_name(bll.getInfoByCid(cid).get(0).getCoba_client()
					.toString());
		}

		temModel.setSyme_state(state);
		list.add(temModel);

		issuccess = bll.SysMessageAdd(temModel, list);
		return issuccess;
	}

	public ListModelList<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(ListModelList<SysMessageModel> temList) {
		this.temList = temList;
	}

	public SysMessageModel getTemModel() {
		return temModel;
	}

	public void setTemModel(SysMessageModel temModel) {
		this.temModel = temModel;
	}

}
