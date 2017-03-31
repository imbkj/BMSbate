package Controller.EmFinance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.CoBase.CoBase_SelectBll;
import bll.EmFinance.EmFinance_SelectBll;

import Model.CoBaseModel;
import Model.CoFinanceWtModel;
import Model.EmFinanceWtModel;
import Model.EmFinanceZYTModel;

public class Finance_EmbaseController {
	private EmFinanceZYTModel ml = (EmFinanceZYTModel) Executions.getCurrent()
			.getArg().get("model");
	private EmFinance_SelectBll bll = new EmFinance_SelectBll();
	private CoFinanceWtModel model = bll.getModel(ml.getName(),
			ml.getOwnmonth(), ml.getCid());
	
	private List<EmFinanceWtModel> fiList = bll.getWtFiList(ml.getName(),
			ml.getOwnmonth(), ml.getCid());// 系统多出员工
	
	private List<EmFinanceZYTModel> fzList = bll.getWtZYTList(ml.getName(),
			ml.getOwnmonth(), ml.getCid());// 智翼通多出的员工
	
	private Integer diffnum = 0;
	private Integer zytnum = bll.getEmbaseCount(ml.getName(), ml.getCid(),
			ml.getOwnmonth(), ml.getCoab_name());//获取智翼通人数
	
	private List<EmFinanceWtModel> efWtList = model.getEfWtList();

	public Finance_EmbaseController() {
		model.setEmFzCount(zytnum);
		diffnum = fiList.size() + fzList.size();
	}

	// 打开系统与智翼通不一致员工列表
	@Command
	public void diffient() {
		Map map = new HashMap<>();
		map.put("model", ml);
		Window window = (Window) Executions.createComponents(
				"Finance_DifferentList.zul", null, map);
		window.doModal();
	}

	// 打开补交报价单调整页面
	@Command("openImprove")
	public void openImprove(@BindingParam("gid") Integer gid,
			@BindingParam("cid") Integer cid,
			@BindingParam("ownmonth") Integer ownmonth) {
		Map map = new HashMap<>();
		map.put("gid", gid);
		map.put("cid", cid);
		map.put("ownmonth", ownmonth);
		Window window;
		window = (Window) Executions.createComponents("Finance_Improve.zul",
				null, map);
		window.doModal();
	}

	// 打开公司业务中心
	@Command("openmemulist")
	public void openmemulist() {
		CoBase_SelectBll sebll = new CoBase_SelectBll();
		List<CoBaseModel> cobaselist = new ArrayList<CoBaseModel>();
		cobaselist = new ListModelList<CoBaseModel>(
				sebll.getCobaseeditinfo(" AND a.cid="
						+ String.valueOf(ml.getCid())));

		Map map = new HashMap();
		map.put("cid", ml.getCid());
		map.put("model", cobaselist.get(0));
		Window window = (Window) Executions.createComponents(
				"../CoMenuList/CoMe_List.zul", null, map);
		window.doModal();
	}

	// 非标管理
	@Command("manualDisposable")
	public void manualDisposable() {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("cid", ml.getCid());
			Window window = (Window) Executions.createComponents(
					"../CoFinanceManage/Cfma_ManualDisposable.zul", null, map);
			window.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CoFinanceWtModel getModel() {
		return model;
	}

	public void setModel(CoFinanceWtModel model) {
		this.model = model;
	}

	public EmFinanceZYTModel getMl() {
		return ml;
	}

	public void setMl(EmFinanceZYTModel ml) {
		this.ml = ml;
	}

	public Integer getDiffnum() {
		return diffnum;
	}

	public void setDiffnum(Integer diffnum) {
		this.diffnum = diffnum;
	}
}
