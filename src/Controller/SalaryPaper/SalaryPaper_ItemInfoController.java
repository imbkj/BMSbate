package Controller.SalaryPaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoSalaryItemInfoModel;
import Model.SalaryPaperCoModel;
import bll.SalaryPaper.SalaryPaperBll;

public class SalaryPaper_ItemInfoController {
	private List<CoSalaryItemInfoModel> waitSitemList = new LinkedList<CoSalaryItemInfoModel>();
	private List<CoSalaryItemInfoModel> ietmList = new LinkedList<CoSalaryItemInfoModel>();
	private Window window;
	private SalaryPaperCoModel m = (SalaryPaperCoModel) Executions.getCurrent()
			.getArg().get("m");
	private SalaryPaperBll spb = new SalaryPaperBll();

	public SalaryPaper_ItemInfoController() {
		init();
	}

	// 数据初始化
	public void init() {
		waitSitemList = spb.getWaitSitemList(m.getCid());
		ietmList = spb.getIetmList(m.getCid());
	}

	@Command
	@NotifyChange({ "waitSitemList", "ietmList" })
	public void rightMove(@BindingParam("hehe") CoSalaryItemInfoModel hehe) {
		waitSitemList.remove(hehe);
		ietmList.add(0, hehe);
	}

	@Command
	@NotifyChange({ "waitSitemList", "ietmList" })
	public void leftMove(@BindingParam("hehe") CoSalaryItemInfoModel hehe) {
		ietmList.remove(hehe);
		waitSitemList.add(0, hehe);
	}

	@Command
	@NotifyChange({ "waitSitemList", "ietmList" })
	public void allRight() {
		for (int i = 0; i < waitSitemList.size(); i++) {
			ietmList.add(waitSitemList.get(i));
		}
		waitSitemList.clear();

	}

	@Command
	@NotifyChange({ "waitSitemList", "ietmList" })
	public void allLeft() {
		for (int i = 0; i < ietmList.size(); i++) {
			waitSitemList.add(ietmList.get(i));
		}
		ietmList.clear();
	}

	// 修改工资项目
	@Command
	public void updateSalaryIetm() {
		List<CoSalaryItemInfoModel> allList = new ArrayList<CoSalaryItemInfoModel>();
		Map<String, List<CoSalaryItemInfoModel>> map = new HashMap<String, List<CoSalaryItemInfoModel>>();
		for (int i = 0; i < waitSitemList.size(); i++) {
			allList.add(waitSitemList.get(i));
		}
		for (int i = 0; i < ietmList.size(); i++) {
			allList.add(ietmList.get(i));
		}
		map.put("allList", allList);
		window = (Window) Executions.createComponents(
				"/SalaryPaper/SalaryPaper_updateSalaryIetm.zul", null, map);
		window.doModal();
	}

	// 保存设置
	@Command
	@NotifyChange({ "waitSitemList", "ietmList" })
	public void save() {
		boolean flag = spb.itemMoveSave(waitSitemList, ietmList);
		if (flag) {
			Messagebox.show("设置成功", "操作提示", Messagebox.OK, Messagebox.NONE);
			init();
		} else {
			Messagebox.show("设置失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<CoSalaryItemInfoModel> getWaitSitemList() {
		return waitSitemList;
	}

	public void setWaitSitemList(List<CoSalaryItemInfoModel> waitSitemList) {
		this.waitSitemList = waitSitemList;
	}

	public List<CoSalaryItemInfoModel> getIetmList() {
		return ietmList;
	}

	public void setIetmList(List<CoSalaryItemInfoModel> ietmList) {
		this.ietmList = ietmList;
	}

	public SalaryPaperCoModel getM() {
		return m;
	}

	public void setM(SalaryPaperCoModel m) {
		this.m = m;
	}

}
