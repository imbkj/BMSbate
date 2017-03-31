package Controller.EmHouse;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmHouseChangeModel;
import Model.SysMessageModel;
import bll.EmHouse.EmHouse_EditBll;
import bll.SysMessage.SysMessageTem_ManageBll;
import bll.SysMessage.SysMessage_AddBll;

public class EmHouse_returnController {
	private ListModelList<SysMessageModel> temList = new ListModelList<SysMessageModel>();
	private SysMessageModel temModel = new SysMessageModel();

	private Integer daid = Integer.valueOf(Executions.getCurrent().getArg().get("id")
			.toString());
	private Integer gid = Integer.valueOf(Executions.getCurrent().getArg().get("gid")
			.toString());
	private EmHouseChangeModel ecm = (EmHouseChangeModel) Executions.getCurrent().getArg().get("em");
	private String className = Executions.getCurrent().getArg().get("cn")
			.toString();
	private String title = Executions.getCurrent().getArg().get("title")
			.toString();
	private boolean ck = false;
	private boolean dis = false;
	private Window win = (Window) Path.getComponent("winME");

	public EmHouse_returnController() {

		SysMessageTem_ManageBll tbll = new SysMessageTem_ManageBll();

		setTemList(tbll.gettemListByClass(className));
		temList.add(0, new SysMessageModel());

		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setEmhc_id(daid);
		em.setDataState(3);
		EmHouse_EditBll bll = new EmHouse_EditBll();
		dis = bll.gobackstate(em);

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

			int issuccess = submit(1);
			if (issuccess == 1) {
				if (ck) {
					//goback();
				}

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

	/*
	public String[] goback() {
		EmHouseChangeModel em = new EmHouseChangeModel();
		EmHouse_EditBll bll = new EmHouse_EditBll();
		em.setEmhc_id(daid);
		em.setEmhc_ifdeclare(3);
		em.setEmhc_tapr_id(ecm.getEmhc_tapr_id());
		
		
		//return bll.returnBack(em);

	}*/

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
