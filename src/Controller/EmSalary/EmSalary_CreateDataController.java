package Controller.EmSalary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import Model.EmSalaryBaseSel_viewModel;
import Util.UserInfo;

public class EmSalary_CreateDataController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());

	private List<String> ownmonthList;
	private List<String> createmonthList;
	private String shortname;
	private List<EmSalaryBaseSel_viewModel> emList;
	private List<EmSalaryBaseSel_viewModel> winEmList;
	private List<EmSalaryBaseSel_viewModel> createList;

	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();

	public EmSalary_CreateDataController() {
		createList = new LinkedList<EmSalaryBaseSel_viewModel>();
		shortname = bll.getCoShortName(cid);
		ownmonthList = bll.getOwnmonth(cid);
		createmonthList = bll.getOwnmonthByNow();
	}

	// 生成工资
	@Command("createData")
	public void createData(@BindingParam("ownmonth") String ownmonth,
			@BindingParam("createmonth") String createmonth,
			@BindingParam("ifZero") String ifZero,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (createList.size() > 0) {
				if (!"".equals(createmonth) && createmonth != null) {
					EmSalary_SalaryOperateBll opBll = new EmSalary_SalaryOperateBll();
					String[] message = opBll.createSalary(createList, Integer
							.parseInt(ownmonth), Integer.parseInt(createmonth),
							cid, UserInfo.getUsername(),
							("1".equals(ifZero) ? true : false));
					if (message[0].equals("1")) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						// 刷新父窗口
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("ownmonth", createmonth);
						map.put("cid", cid);
						Binder bind = (Binder) view.getParent().getAttribute(
								"binder");
						bind.postCommand("refreshWinByOwnmonth", map);
						win.detach();
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				} else {
					Messagebox.show("请选择需要生成工资的月份。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("请选择需要生成的数据。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("工资生成出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 所属月份检索
	@Command("ownmonthChange")
	@NotifyChange({ "emList", "createList", "winEmList" })
	public void ownmonthChange(@BindingParam("ownmonth") String ownmonth,
			@BindingParam("createmonth") String createmonth) {
		if (!"".equals(ownmonth) && ownmonth != null && !"".equals(createmonth)
				&& createmonth != null) {
			winEmList = new ArrayList<EmSalaryBaseSel_viewModel>();
			emList = bll.getEmbaseByCidOwnmonth(cid,
					Integer.parseInt(ownmonth), Integer.parseInt(createmonth));
			winEmList.addAll(emList);
			createList.clear();
		}
	}

	// 全选
	@Command("chooseAll")
	@NotifyChange({ "emList", "createList", "winEmList" })
	public void chooseAll() {
		try {
			for (EmSalaryBaseSel_viewModel m : winEmList) {
				createList.add(m);
				emList.remove(m);
			}
			winEmList.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 选择
	@Command("choose")
	@NotifyChange({ "emList", "createList", "winEmList" })
	public void choose(@BindingParam("select") Listbox select) {
		Set<Listitem> listCheck = select.getSelectedItems();
		try {
			if (!listCheck.isEmpty()) {
				for (Listitem c : listCheck) {
					EmSalaryBaseSel_viewModel m = (EmSalaryBaseSel_viewModel) c
							.getValue();
					if (createList.add(m)) {
						emList.remove(m);
						winEmList.remove(m);
					}
				}
			} else {
				if (listCheck.isEmpty()) {
					// 弹出提示
					Messagebox.show("请选择员工！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			}
		} catch (Exception e) {
			Messagebox.show("操作时请将数据加载完整", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 单个移除
	@Command("remove")
	@NotifyChange({ "emList", "createList", "winEmList" })
	public void remove(@BindingParam("select") Listbox select) {
		Set<Listitem> listCheck = select.getSelectedItems();
		try {
			if (!listCheck.isEmpty()) {
				for (Listitem c : listCheck) {
					EmSalaryBaseSel_viewModel m = (EmSalaryBaseSel_viewModel) c
							.getValue();
					if (emList.add(m)) {
						winEmList.add(m);
						createList.remove(m);
					}
				}
			} else {
				if (listCheck.isEmpty()) {
					// 弹出提示
					Messagebox.show("请选择员工！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			}
		} catch (Exception e) {
			Messagebox.show("操作时请将数据加载完整", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 全移除
	@Command("removeAll")
	@NotifyChange({ "emList", "createList", "winEmList" })
	public void removeAll() {
		try {
			emList.addAll(createList);
			winEmList.addAll(createList);
			createList.clear();
		} catch (Exception e) {

		}
	}

	// 用途筛选
	@Command("searchUsage")
	public void searchUsage(@BindingParam("usage") String usage) {
		winEmList = new ArrayList<EmSalaryBaseSel_viewModel>();
		try {
			if (usage == null || "".equals(usage)) {
				winEmList.addAll(emList);
			} else {
				for (EmSalaryBaseSel_viewModel m : emList) {
					try {
						if (usage.equals(m.getEsda_usage_typestr()))
							winEmList.add(m);
					} catch (Exception e) {

					}
				}
			}
			BindUtils.postNotifyChange(null, null, this, "winEmList");
		} catch (Exception e) {

		}
	}

	public List<String> getCreatemonthList() {
		return createmonthList;
	}

	public String getShortname() {
		return shortname;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public List<EmSalaryBaseSel_viewModel> getEmList() {
		return emList;
	}

	public List<EmSalaryBaseSel_viewModel> getCreateList() {
		return createList;
	}

	public List<EmSalaryBaseSel_viewModel> getWinEmList() {
		return winEmList;
	}

}
