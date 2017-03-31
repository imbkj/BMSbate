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
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmHouseChangeGJJModel;
import Model.LoginModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmHouse.EmHouseChangeGjjConfirmImpl;
import bll.EmHouse.EmHouse_ChangeGjjBll;
import bll.EmHouse.EmHouse_EditBll;

public class EmHouse_ChangeListController {
	private EmHouse_ChangeGjjBll bll = new EmHouse_ChangeGjjBll();
	List<EmHouseChangeGJJModel> list = new ArrayList<EmHouseChangeGJJModel>();
	private List<EmHouseChangeGJJModel> ownmonthlist = new ListModelList<>();
	private EmHouseChangeGJJModel ecgm = new EmHouseChangeGJJModel();

	private Window win = (Window) Path.getComponent("/winGj");

	public EmHouse_ChangeListController() {
		ecgm.setOwnmonth(Integer.valueOf(DateStringChange
				.Datestringnow("yyyyMM")));
		ownmonthlist = bll.getOwnmonthList();
		list = bll.getEmHouse_ChangeGjj(null, null, null, ecgm.getOwnmonth(),
				0, 2);

	}

	@Command
	public void stateInfo(@BindingParam("a") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			System.out.println(cb.getSelectedItem().getValue());
			if (!cb.getSelectedItem().getValue().equals("")) {
				ecgm.setEhcg_ifdeclare(Integer.valueOf(cb.getSelectedItem()
						.getValue().toString()));
			} else {
				ecgm.setEhcg_ifdeclare(null);
			}

		}
	}

	// 公积金交单申报
	@Command
	@NotifyChange("list")
	public void declare(@BindingParam("a") EmHouseChangeGJJModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmHouse_ChangeDeclare.zul", null, map);
		window.doModal();
		list = bll.getEmHouse_ChangeGjj(model.getEhcg_id(), null, null, null,
				null, null);
	}

	@Command
	@NotifyChange("list")
	public void returnInfo(@BindingParam("a") EmHouseChangeGJJModel em) {
		em.setEhcg_modname(UserInfo.getUsername());
		em.setEhcg_ifdeclare(3);
		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		map.put("id", em.getEhcg_id());// 业务表id
		map.put("title", em.getEhcg_company()+","+em.getEhcg_name());
		map.put("tablename", "emhousechangegjj");// 业务表名
		map.put("clazz", new EmHouse_EditBll());
		map.put("method", "returnGjjFlow");
		map.put("pclass", EmHouseChangeGJJModel.class);
		map.put("parameter", em);
		map.put("checkName", "退回");

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(em.getEhcg_addname());

		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		list = bll.getEmHouse_ChangeGjj(em.getEhcg_id(), null, null, null,
				null, null);
	}

	// 公积金交单申报信息查询
	@Command
	@NotifyChange("list")
	public void search() {

		list = bll.getEmHouse_ChangeGjj(null, ecgm.getEhcg_company(),
				ecgm.getEhcg_name(), ecgm.getOwnmonth(),
				ecgm.getEhcg_ifdeclare(), 2);
	}

	// 全选
	@Command("checkall")
	@NotifyChange("list")
	public void checkall() {
		Checkbox ck = (Checkbox) win.getFellow("cka");
		Grid gd = (Grid) win.getFellow("gg");
		Integer n = gd.getActivePage() * gd.getPageSize();
		Integer s = (list.size() / gd.getPageCount()) < gd.getPageSize() ? (list
				.size() / gd.getPageCount()) : gd.getPageSize();
		for (int i = n; i < s; i++) {
			if (list.get(i).getEhcg_ifdeclare().equals(0)
					|| list.get(i).getEhcg_ifdeclare().equals(2)) {
				list.get(i).setChecked(ck.isChecked());
			}
		}
	}

	// 弹出批量申报页面
	@Command
	@NotifyChange("list")
	public void declareall() {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Checkbox ck = (Checkbox) win.getFellow("cka");
							Grid gd = (Grid) win.getFellow("gg");
							List<EmHouseChangeGJJModel> listmodel = new ArrayList<EmHouseChangeGJJModel>();
							Integer n = gd.getActivePage() * gd.getPageSize();
							Integer s = (list.size() / gd.getPageCount()) < gd
									.getPageSize() ? (list.size() / gd
									.getPageCount()) : gd.getPageSize();
							for (int i = n; i < s; i++) {
								if (list.get(i).isChecked()) {
									EmHouseChangeGJJModel ml = list.get(i);
									listmodel.add(ml);
								}
							}

							if (listmodel.size() < 1) {
								Messagebox.show("请选择数据", "提示", Messagebox.OK,
										Messagebox.ERROR);
								return;
							} else {
								String sql = "0";
								WfBusinessService wfbs = new EmHouseChangeGjjConfirmImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								for (EmHouseChangeGJJModel em : listmodel) {

									if (em.getEhcg_ifdeclare().equals(0)) {
										em.setEhcg_ifdeclare(2);
									} else if (em.getEhcg_ifdeclare().equals(2)) {
										em.setEhcg_ifdeclare(1);
									}
									if (em.getEhcg_tapr_id() != null) {

										Object[] obj = { "申报数据", em };
										String[] str = wfs.PassToNext(obj,
												em.getEhcg_tapr_id(),
												UserInfo.getUsername(), "", 0,
												"");
										if (!str[0].equals("1")) {
											Messagebox.show(
													em.getEhcg_company() + ","
															+ em.getEhcg_name()
															+ "提交数据失败.", "提示",
													Messagebox.OK,
													Messagebox.ERROR);
											return;
										}
									} else {
										Integer i = bll.modInfo(em,
												em.getEhcg_id());
										if (i <= 0) {
											Messagebox.show(
													em.getEhcg_company() + ","
															+ em.getEhcg_name()
															+ "提交数据失败.", "提示",
													Messagebox.OK,
													Messagebox.ERROR);
											return;
										}
									}
									sql += "," + em.getEhcg_id();
								}
								Messagebox.show("提交成功.", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								list = bll
										.getEmHouse_ChangeGjjInfo(" and ehcg_id in ("
												+ sql + ")");
								ck.setChecked(false);
							}
						}
					}
				});
	}

	public List<EmHouseChangeGJJModel> getList() {
		return list;
	}

	public void setList(List<EmHouseChangeGJJModel> list) {
		this.list = list;
	}

	public List<EmHouseChangeGJJModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<EmHouseChangeGJJModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public EmHouseChangeGJJModel getEcgm() {
		return ecgm;
	}

	public void setEcgm(EmHouseChangeGJJModel ecgm) {
		this.ecgm = ecgm;
	}

}
