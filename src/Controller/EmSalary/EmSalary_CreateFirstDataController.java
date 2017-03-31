package Controller.EmSalary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmSalaryDataModel;
import Util.UserInfo;
import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_CreateFirstDataController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());

	private List<String> createmonthList;
	private String shortname;
	private List<EmSalaryDataModel> emList;
	private List<EmSalaryDataModel> createList;

	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();

	public EmSalary_CreateFirstDataController() {
		createList = new LinkedList<EmSalaryDataModel>();
		shortname = bll.getCoShortName(cid);
		createmonthList = bll.getOwnmonthByNow();
		emList = bll.getEmSalaryDateBase(cid);
	}

	// 生成工资
	@Command("createData")
	public void createData(@BindingParam("createmonth") String createmonth,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (createList.size() > 0) {
				if (!"".equals(createmonth) && createmonth != null) {
					EmSalary_SalaryOperateBll opBll = new EmSalary_SalaryOperateBll();
					String[] message = opBll.createSalaryFirst(createList,
							Integer.parseInt(createmonth),
							UserInfo.getUsername());
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

	// 全选
	@Command("chooseAll")
	@NotifyChange({ "emList", "createList" })
	public void chooseAll() {
		try {
			createList.addAll(emList);
			emList.clear();
		} catch (Exception e) {

		}
	}

	// 选择
	@Command("choose")
	@NotifyChange({ "emList", "createList" })
	public void choose(@BindingParam("select") Listitem select) {
		try {
			EmSalaryDataModel m = (EmSalaryDataModel) select.getValue();
			if (createList.add(m)) {
				emList.remove(m);
			}
		} catch (Exception e) {

		}
	}

	// 单个移除
	@Command("remove")
	@NotifyChange({ "emList", "createList" })
	public void remove(@BindingParam("select") Listitem select) {
		try {
			EmSalaryDataModel m = (EmSalaryDataModel) select.getValue();
			if (emList.add(m)) {
				createList.remove(m);
			}
		} catch (Exception e) {

		}
	}

	// 全移除
	@Command("removeAll")
	@NotifyChange({ "emList", "createList" })
	public void removeAll() {
		try {
			emList.addAll(createList);
			createList.clear();
		} catch (Exception e) {

		}
	}

	public List<String> getCreatemonthList() {
		return createmonthList;
	}

	public String getShortname() {
		return shortname;
	}

	public List<EmSalaryDataModel> getEmList() {
		return emList;
	}

	public List<EmSalaryDataModel> getCreateList() {
		return createList;
	}
}
