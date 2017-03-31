package Controller.SalaryPaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmSalaryInfoModel;
import Model.SalaryPaperCoModel;
import bll.SalaryPaper.SalaryPaperBll;

/**
 * 员工设置
 * @author gxj
 *
 */
public class SalaryPaper_setCoSpController {
	private Window window;
	private List<SalaryPaperCoModel> spCoList = (List<SalaryPaperCoModel>) Executions
			.getCurrent().getArg().get("spCoList");
	private List<EmSalaryInfoModel> esinfoList;
	private SalaryPaperBll spb = new SalaryPaperBll();

	// 构造器
	public SalaryPaper_setCoSpController() {
		init();
	}

	// 初始化
	public void init() {

		esinfoList = spb.getPlEsInfoList(spCoList);
	}

	// 全选
	@Command
	public void allcheck(@BindingParam("grid") Grid grid,
			@BindingParam("allcheck") Checkbox c) {
		for (int i = 0; i < esinfoList.size(); i++) {
			Checkbox checkbox = (Checkbox) grid.getCell(i, 0).getChildren()
					.get(0);
			if (c.isChecked() == true) {
				checkbox.setChecked(true);
			} else {
				checkbox.setChecked(false);
			}
		}
	}

	// 设置发送方式窗口
	@Command
	public void setSend(@BindingParam("m") EmSalaryInfoModel m,
			@ContextParam(ContextType.VIEW) Component view) {

		Map<String, EmSalaryInfoModel> map = new HashMap<String, EmSalaryInfoModel>();
		map.put("m", m);
		window = (Window) Executions.createComponents(
				"/SalaryPaper/SalaryPaper_setSp.zul", view, map);
		window.doModal();
	}

	// 批量设置发送方式窗口
	@Command
	public void setPlSend(@BindingParam("grid") Grid grid,
			@ContextParam(ContextType.VIEW) Component view) {
		List<EmSalaryInfoModel> l = new ArrayList<EmSalaryInfoModel>();
		for (int i = 0; i < esinfoList.size(); i++) {
			Checkbox checkbox = (Checkbox) grid.getCell(i, 0).getChildren()
					.get(0);
			if (checkbox.isChecked() == true) {
				l.add((EmSalaryInfoModel) checkbox.getValue());
			}
		}
		if (l.size() > 0) {
			Map<String, List<EmSalaryInfoModel>> map = new HashMap<String, List<EmSalaryInfoModel>>();
			map.put("l", l);
			window = (Window) Executions.createComponents(
					"/SalaryPaper/SalaryPaper_PlSetSp.zul", view, map);
			window.doModal();
		} else {
			Messagebox.show("请选择要设置的员工", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 设置模版窗口
	@Command
	public void setModel(@BindingParam("m") EmSalaryInfoModel m,
			@ContextParam(ContextType.VIEW) Component view) {

		Map<String, EmSalaryInfoModel> map = new HashMap<String, EmSalaryInfoModel>();
		map.put("m", m);
		window = (Window) Executions.createComponents(
				"/SalaryPaper/SalaryPaper_setModel.zul", view, map);
		window.doModal();
	}

	// 批量设置模版
	@Command
	public void setPlModel(@BindingParam("grid") Grid grid,
			@ContextParam(ContextType.VIEW) Component view) {
		List<EmSalaryInfoModel> l = new ArrayList<EmSalaryInfoModel>();
		for (int i = 0; i < esinfoList.size(); i++) {
			Checkbox checkbox = (Checkbox) grid.getCell(i, 0).getChildren()
					.get(0);
			if (checkbox.isChecked() == true) {
				l.add((EmSalaryInfoModel) checkbox.getValue());
			}
		}
		if (l.size() > 0) {
			Map<String, List<EmSalaryInfoModel>> map = new HashMap<String, List<EmSalaryInfoModel>>();
			map.put("l", l);
			window = (Window) Executions.createComponents(
					"/SalaryPaper/SalaryPaper_PlSetModel.zul", view, map);
			window.doModal();
		} else {
			Messagebox.show("请选择要设置的员工", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 刷新
	@Command
	@NotifyChange("esinfoList")
	public void esinfoList() {
		init();
	}

	public List<EmSalaryInfoModel> getEsinfoList() {
		return esinfoList;
	}

	public void setEsinfoList(List<EmSalaryInfoModel> esinfoList) {
		this.esinfoList = esinfoList;
	}

}
