package Controller.EmHouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_SearchListBll;
import Model.EmHouseChangeModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class EmHouse_SearchListController {
	private List<EmHouseChangeModel> eclist = new ListModelList<>();
	private EmHouse_SearchListBll bll = new EmHouse_SearchListBll();

	private String username = UserInfo.getUsername();
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private boolean qc = false;

	private Window win = (Window) Path.getComponent("/winSearchList");

	public EmHouse_SearchListController() {
		ecm = (EmHouseChangeModel) Executions.getCurrent().getArg().get("ecm");
		ecm.setDataState(6);
		setEclist(ecm);
		if (eclist.size() > 0) {
			if (ecm.getEmhc_change() != null
					&& !ecm.getEmhc_change().equals("")
					&& ecm.getEmhc_ifprogress2() != null
					&& !ecm.getEmhc_ifprogress2().equals("")
					&& ecm.getEmhc_single() != null
					&& !ecm.getEmhc_single().equals("")) {
				qc = true;
			} else {
				qc = false;
			}

			List<SysMessageModel> mList = bll.getMsgList("emhousechange",
					UserInfo.getUsername(),
					Integer.valueOf(UserInfo.getUserid()));
			for (EmHouseChangeModel em : eclist) {
				for (SysMessageModel sm : mList) {
					if (em.getEmhc_id().equals(sm.getSmwr_tid())) {
						em.setMessage(true);
						if (sm.getSymr_readstate() != null) {
							em.setReadState(sm.getSymr_readstate().equals(1) ? true
									: false);
						}

					}
				}
			}
		}
	}

	@Command("search")
	@NotifyChange("eclist")
	public void search() {
		Textbox tbcompany = (Textbox) win.getFellow("company");
		Textbox tbemp = (Textbox) win.getFellow("emp");
		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setEmhc_company(tbcompany.getValue());
		em.setEmhc_name(tbemp.getValue());
		em.setDataState(6);
		if ((tbcompany.getValue() != null && !tbcompany.getValue().equals(""))
				|| (tbemp.getValue() != null && !tbemp.getValue().equals(""))) {
		} else {
			em = ecm;
		}
		setEclist(em);

		if (eclist.size() > 0) {
			List<SysMessageModel> mList = bll.getMsgList("emhousechange",
					UserInfo.getUsername(),
					Integer.valueOf(UserInfo.getUserid()));
			for (EmHouseChangeModel ecm : eclist) {
				for (SysMessageModel sm : mList) {
					if (ecm.getEmhc_id().equals(sm.getSmwr_tid())) {
						ecm.setMessage(true);
						if (sm.getSymr_readstate() != null) {
							ecm.setReadState(sm.getSymr_readstate().equals(1) ? true
									: false);
						}

					}
				}
			}
		}
	}

	@Command("returnIndex")
	public void returnIndex() {
		win.detach();
		/*
		 * Window window = (Window) Executions.createComponents(
		 * "EmHouse_Search.zul", null, null); window.doModal();
		 */
	}

	@Command
	public void export() {
		bll.exportExcel(eclist);
	}

	@Command
	@NotifyChange("eclist")
	public void errorfile() {

		Window window = (Window) Executions.createComponents(
				"EmHouse_UploadErr.zul", null, null);
		window.doModal();
		search();
	}

	@Command("checkInfo")
	public void checkInfo(@BindingParam("a") EmHouseChangeModel ehm) {
		Map map = new HashMap();
		map.put("daid", ehm.getEmhc_id());
		map.put("id", ehm.getEmhc_tapr_id());
		Window window = (Window) Executions.createComponents(
				"EmHouse_Info.zul", null, map);
		window.doModal();
		// ecm.setDataState(null);
		// setEclist(ecm);
	}

	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka,
			@BindingParam("b") Checkbox cks) {
		if (cks.getId().equals("cka")) {

			Grid gd = (Grid) win.getFellow("gdList");

			Integer pageSize = gd.getPageSize();
			Integer activePage = gd.getActivePage() + 1;
			Integer page = gd.getPageCount();
			Integer start = 0;
			Integer size = 0;

			if (eclist.size() < pageSize) {
				size = eclist.size();
			} else {
				start = gd.getActivePage() * pageSize;
				if (activePage.equals(page)) {
					if (eclist.size() < activePage * pageSize) {
						size = eclist.size();
					} else {
						size = activePage * pageSize;
					}
				} else {
					size = activePage * pageSize;
				}

			}

			for (int i = start; i < size; i++) {
				if (eclist.get(i).getEmhc_ifdeclare().equals(0)
						|| eclist.get(i).getEmhc_ifdeclare().equals(2)) {
					eclist.get(i).setEmhc_ischecked(cka.isChecked());
					BindUtils.postNotifyChange(null, null, eclist.get(i),
							"emhc_ischecked");
				}
			}
		} else {
			if (!cks.isChecked()) {
				cka.setChecked(false);
			}
		}
	}

	// 独立申报
	@Command("declareInfo")
	@NotifyChange({ "eclist", "qc" })
	public void declareInfo() {
		boolean b = false;
		for (EmHouseChangeModel em : eclist) {
			if (em.isEmhc_ischecked()) {
				b = true;
			}
		}
		if (!b) {
			Messagebox.show("请选择需要申报的员工.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							declareList();
							EmHouseChangeModel m = new EmHouseChangeModel();
							for (EmHouseChangeModel em : eclist) {
								if (em.isEmhc_ischecked()) {

									if (m.getIdState() != null
											&& !m.getIdState().equals("")) {
										m.setIdState(m.getIdState() + ","
												+ em.getEmhc_id().toString());
									} else {
										m.setIdState(em.getEmhc_id().toString());
									}

								}
							}

							setEclist(m);
							qc = false;
						}
					}
				});

	}

	// 生成清册
	@Command("declareExcel")
	@NotifyChange({ "eclist", "qc" })
	public void declareExcel() {
		boolean b = false;
		for (EmHouseChangeModel em : eclist) {
			if (em.isEmhc_ischecked()) {
				b = true;
			}
		}
		if (!b) {
			Messagebox.show("请选择员工.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							exportExcel();
							EmHouseChangeModel m = new EmHouseChangeModel();
							for (EmHouseChangeModel em : eclist) {
								if (m.getIdState() != null
										&& !m.getIdState().equals("")) {
									m.setIdState(m.getIdState() + ","
											+ em.getEmhc_id().toString());
								} else {
									m.setIdState(em.getEmhc_id().toString());
								}
							}
							setEclist(m);
							qc = false;
						}
					}
				});
	}

	// 生成清册
	@NotifyChange("eclist")
	public void exportExcel() {
		Grid gd = (Grid) win.getFellow("gdList");
		boolean b = false;
		String className = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();

		switch (ecm.getEmhc_change()) {
		case "新增":
			className = "New";
			break;
		case "调入":
			if (ecm.getEmhc_ifprogress2().equals("调入(等待转移)")) {
				className = "Transfer";
			} else if (ecm.getEmhc_ifprogress2().equals("调入(等待启封)")) {
				className = "Open";
			}
			break;
		case "停交":
			className = "Stop";
			break;
		default:
			className = "Salay";
			break;

		}

		Map<Integer, Integer> map = new HashMap();
		for (int i = 0, j = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox cb = (Checkbox) gd.getCell(i, 20).getChildren().get(0);
				if (cb.isChecked()) {
					b = true;
					map.put(j, Integer.valueOf(cb.getValue().toString()));
					j++;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (b) {
			String exfileName = sdf.format(trialTime) + "_" + className + "_"
					+ pinyin4jUtil.getPinYinHeadChar(username) + "_"
					+ map.size() + ".xls";

			String[] s = new String[5];
			String idlist = "";

			for (int i = 0, j = 0; i < map.size(); i++) {

				idlist = idlist.equals("") ? map.get(i).toString() : idlist
						+ "," + map.get(i).toString();

				EmHouseChangeModel ehm = new EmHouseChangeModel();
				ehm = bll.getInfoById(map.get(i)).get(0);
				if (className.equals("New")) {
					if (ehm.getEmhc_title() == null
							|| ehm.getEmhc_title().equals("")) {
						Messagebox.show(ehm.getEmhc_name() + "职称为空!", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
					if (ehm.getEmhc_hj() == null || ehm.getEmhc_hj().equals("")) {
						Messagebox.show(ehm.getEmhc_name() + "户籍为空!", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
					if (ehm.getEmhc_degree() == null
							|| ehm.getEmhc_degree().equals("")) {
						Messagebox.show(ehm.getEmhc_name() + "学历为空!", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				if (ehm.getEmhc_ifdeclare() == 0) {
					j = bll.updateExcel(ehm.getEmhc_id(), exfileName,
							className, map.size(), username);
					ehm.setEmhc_declarename(username);

					if (j > 0) {
						s = bll.declareSingle(ehm);

					} else {
						s[1] = "发生错误";
						break;
					}

				} else {
					j = bll.updateExcel(ehm.getEmhc_id(), exfileName,
							className, map.size(), username);
					if (j > 0) {
						s[1] = "提交成功";
					} else {
						s[1] = "发生错误";
						break;
					}
				}
			}
			Map m = new HashMap();
			m.put("id", idlist);
			m.put("className", className);
			m.put("exfileName", exfileName);

			Window win = (Window) Executions.createComponents(
					"EmHouse_DeclareExcel.zul", null, m);
			win.doModal();

		} else {
			Messagebox.show("请选择需要申报的数据!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 独立申报
	@NotifyChange("eclist")
	public void declareList() {

		boolean b = false;
		String str = "";

		Grid gd = (Grid) win.getFellow("gdList");
		Integer pageSize = gd.getPageSize();
		Integer activePage = gd.getActivePage() + 1;
		Integer page = gd.getPageCount();
		Integer start = 0;
		Integer size = 0;

		if (eclist.size() < pageSize) {
			size = eclist.size();
		} else {
			start = gd.getActivePage() * pageSize;
			if (activePage.equals(page)) {
				if (eclist.size() < activePage * pageSize) {
					size = eclist.size();
				} else {
					size = activePage * pageSize;
				}
			} else {
				size = activePage * pageSize;
			}

		}
		EmHouseChangeModel ehm;
		for (int i = start; i < size; i++) {
			try {

				if (eclist.get(i).isEmhc_ischecked()) {
					b = true;
					ehm = new EmHouseChangeModel();
					ehm = bll.getInfoById(eclist.get(i).getEmhc_id()).get(0);
					ehm.setEmhc_declarename(username);
					String[] s = bll.declareSingle(ehm);
					if (s[0].equals("0")) {
						b = false;
						str = s[1] + ",业务ID:"
								+ eclist.get(i).getEmhc_id().toString()
								+ ",流程ID:" + eclist.get(i).getEmhc_tapr_id();
						break;
					}
					ehm = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		if (b) {
			Messagebox.show("申报成功!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else {
			if (str.equals("")) {
				Messagebox.show("请选择需要申报的数据!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				Messagebox.show(str, "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	@Command
	public void message(@BindingParam("a") EmHouseChangeModel em) {
		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		map.put("id", em.getEmhc_id());// 业务表id
		map.put("tablename", "emhousechange");// 业务表名
		map.put("title", em.getEmhc_company() + "," + em.getEmhc_name());

		if (!em.isError()) {

			List<EmHouseChangeModel> list = new ListModelList<>();
			EmHouseChangeModel model = new EmHouseChangeModel();
			model.setEmhc_id(em.getEmhc_id());
			list = bll.getList(model);
			if (list.size() > 0) {
				if (!em.getEmhc_statename().equals(
						list.get(0).getEmhc_statename())) {

					em.setError(true);
				}
			}
		}

		if (!em.isError()
				&& (em.getEmhc_ifdeclare().equals(0) || em.getEmhc_ifdeclare()
						.equals(2))) {

			map.put("clazz", new EmHouse_EditBll());
			map.put("method", "returnBack");
			map.put("pclass", EmHouseChangeModel.class);
			map.put("parameter", em);
			map.put("checkName", "退回");
		}

		EmbaseModel emp = new EmbaseModel();
		emp.setCoba_company(em.getEmhc_company());
		emp.setCid(em.getCid());
		emp.setGid(em.getGid());
		emp.setEmba_name(em.getEmhc_name());
		map.put("embase", emp);

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		if (em.getEmhc_confirmname() != null
				&& !em.getEmhc_confirmname().equals("")) {
			m.setLog_name(em.getEmhc_confirmname());
		} else {
			m.setLog_name(em.getEmhc_addname());
		}

		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	@Command
	public void checkerr(@BindingParam("a") final EmHouseChangeModel em) {
		Messagebox.show("确认修改" + em.getEmhc_name() + "的信息?", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK,
						Messagebox.Button.CANCEL }, Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

						}
					}
				});

	}

	public List<EmHouseChangeModel> getEclist() {
		return eclist;
	}

	public void setEclist(EmHouseChangeModel em) {
		this.eclist = bll.getList(em);
	}

	public EmHouseChangeModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeModel ecm) {
		this.ecm = ecm;
	}

	public boolean isQc() {
		return qc;
	}

	public void setQc(boolean qc) {
		this.qc = qc;
	}

}
