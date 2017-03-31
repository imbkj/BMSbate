package Controller.CoProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoProduct.cpd_ListBll;

import Model.CopCompactModel;
import Util.RegexUtil;

public class CoProduct_CompactOperateListController {

	private List<CopCompactModel> cpctList;
	private List<CopCompactModel> scpctList = new ListModelList<>();

	private String shortname = "";
	private String name = "";

	public CoProduct_CompactOperateListController() {
		try {
			init();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		cpd_ListBll bll = new cpd_ListBll();
		try {
			setCpctList(bll.getCopCompactList(""));
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列表检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "scpctList" })
	public void search() {
		try {
			scpctList.clear();
			for (CopCompactModel m : cpctList) {
				if (!shortname.isEmpty()) {
					if (!RegexUtil.isExists(shortname, m.getCpct_shortname())) {
						continue;
					}
				}
				if (!name.isEmpty()) {
					if (!RegexUtil.isExists(name, m.getCpct_name())) {
						continue;
					}
				}
				scpctList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("检索出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 修改
	 * 
	 * @param m
	 */
	@Command("openwin")
	@NotifyChange({ "scpctList" })
	public void openwin(@BindingParam("each") CopCompactModel m) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("daid", m.getCpct_id());

			Window window = (Window) Executions.createComponents(
					"/CoProduct/CoProduct_CompactMod.zul", null, map);
			window.doModal();

			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange("scpctList")
	public void update(@BindingParam("a") CopCompactModel cm,@BindingParam("b") Integer i){
		cpd_ListBll bll = new cpd_ListBll();
		Integer k =bll.updateCopcompact(cm.getCpct_id(), i);
		if(k>0){
			Messagebox.show("操作成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			setCpctList(bll.getCopCompactList(""));
			search();
		}else {
			Messagebox.show("操作失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 新增
	 * 
	 * @param m
	 */
	@Command("add")
	@NotifyChange({ "scpctList" })
	public void add() {
		try {
			Window window = (Window) Executions.createComponents(
					"/CoProduct/CoProduct_CompactAdd.zul", null, null);
			window.doModal();

			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CopCompactModel> getCpctList() {
		return cpctList;
	}

	public void setCpctList(List<CopCompactModel> cpctList) {
		this.cpctList = cpctList;
	}

	public List<CopCompactModel> getScpctList() {
		return scpctList;
	}

	public void setScpctList(List<CopCompactModel> scpctList) {
		this.scpctList = scpctList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
}
