package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.EmHouse.EmHouseChangeGjjImpl;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_EditImpl;

import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmHouse_ChangeGjjSubController {

	private List<EmHouseChangeGJJModel> gjjlist = new ListModelList<>();

	private EmHouse_EditBll bll = new EmHouse_EditBll();

	private Integer daid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());

	private Window winC;

	public EmHouse_ChangeGjjSubController() {
		EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
		em.setEhcg_id(daid);
		gjjlist = bll.getgjjChangeList(em);
		if (gjjlist.size() > 0) {
			List<SysMessageModel> mList = new ListModelList<>();
			mList = bll.getMsgList("emhousechangegjj", UserInfo.getUsername(),
					Integer.valueOf(UserInfo.getUserid()));
			for (EmHouseChangeGJJModel m : gjjlist) {
				for (SysMessageModel sm : mList) {
					if (m.getEhcg_id().equals(sm.getSmwr_tid())) {
						m.setMessage(true);
						if (sm.getSymr_readstate() != null) {
							m.setReadState(sm.getSymr_readstate().equals(1) ? true
									: false);
						}

					}
				}

			}
		}
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;

	}

	@Command
	@NotifyChange("clist")
	public void message(@BindingParam("a") EmHouseChangeGJJModel em) {

		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name

		map.put("id", em.getEhcg_id());// 业务表id
		map.put("tablename", "emhousechangegjj");// 业务表名

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();
		msglist = bll.getuserlist("39,49,50");
		for (loginroleModel m : msglist) {
			LoginModel lm = new LoginModel();
			lm.setLog_id(m.getLog_id());
			lm.setLog_name(m.getLog_name());
			mlist.add(lm);
		}
		// 收件人姓名和收件人id至少要填一个

		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	@Command
	public void info(@BindingParam("a") EmHouseChangeGJJModel em) {
		Map map = new HashMap<>();
		map.put("em", em);

		Window window = (Window) Executions.createComponents(
				"../EmHouse/Emhouse_ChangeGjjInfo.zul", null, map);
		window.doModal();
		gjjlist = bll.getgjjChangeList(em);
	}

	@Command
	public void send(@BindingParam("a") final EmHouseChangeGJJModel em) {
		em.setEhcg_ifdeclare(0);
		em.setEhcg_modname(UserInfo.getUsername());
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i=bll.modGjjInfo(em);
							if (i>0) {
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								winC.detach();
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	@Command
	@NotifyChange("gjjlist")
	public void gjjDel(@BindingParam("a") final EmHouseChangeGJJModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfBusinessService wfbs = new EmHouseChangeGjjImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "删除交单数据", em };
							String[] str = null;
							if (em.getEhcg_tapr_id() != null) {
								str = wfs.StopTask(obj, em.getEhcg_tapr_id(),
										UserInfo.getUsername(), "");
							} else {
								Integer i = bll.delGjj(em.getEhcg_id());
								str = new String[5];
								str[0] = i > 0 ? "1" : "0";
							}

							if (str[0].equals("1")) {
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								winC.detach();
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<EmHouseChangeGJJModel> getGjjlist() {
		return gjjlist;
	}

	public void setGjjlist(List<EmHouseChangeGJJModel> gjjlist) {
		this.gjjlist = gjjlist;
	}

}
