package Controller.EmHouse;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmHouseBJModel;
import Model.SysMessageModel;
import bll.SysMessage.SysMessageTem_ManageBll;
import bll.SysMessage.SysMessage_AddBll;

public class EmHouse_BjReturnController {
	private ListModelList<SysMessageModel> temList = new ListModelList<SysMessageModel>();
	private SysMessageModel temModel = new SysMessageModel();
	private String title = "公积金补缴数据退回";
	private String temClass = "公积金";
	private boolean ck = false;
	private boolean dis = true;

	private EmHouseBJModel ejm = (EmHouseBJModel) Executions.getCurrent()
			.getArg().get("em");
	private Integer gid;
	private Window win = (Window) Path.getComponent("winME");

	public EmHouse_BjReturnController() {

		SysMessageTem_ManageBll tbll = new SysMessageTem_ManageBll();
		setTemList(tbll.gettemListByClass(temClass));
		temList.add(0, new SysMessageModel());
		
	}

	@Command
	public void winName(@BindingParam("a") Window winc) {
		if (win == null) {
			win = winc;

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
	public void send() throws Exception {
		if (temModel.getSyme_content() == null
				&& temModel.getSyme_content().equals("")) {
			Messagebox
					.show("内容不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								int issuccess = submit(1);
								if (issuccess == 1) {
									if (ck) {
										// goback();
									}

									if (Messagebox.show("发送成功!", "INFORMATION",
											Messagebox.YES,
											Messagebox.INFORMATION) == Messagebox.YES) {
										win.detach();

									}
								} else {
									Messagebox.show("发送失败,请联系IT部门!", "ERROR",
											Messagebox.YES, Messagebox.ERROR);
								}
							}
						}
					});
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

	public boolean isCk() {
		return ck;
	}

	public void setCk(boolean ck) {
		this.ck = ck;
	}

	public boolean isDis() {
		return dis;
	}

	public void setDis(boolean dis) {
		this.dis = dis;
	}

}
