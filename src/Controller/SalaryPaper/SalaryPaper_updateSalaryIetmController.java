package Controller.SalaryPaper;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import bll.SalaryPaper.SalaryPaperBll;

import Model.CoSalaryItemInfoModel;
import Util.RegexUtil;

//工资项目修改
public class SalaryPaper_updateSalaryIetmController {

	private List<CoSalaryItemInfoModel> allList = (List<CoSalaryItemInfoModel>) Executions
			.getCurrent().getArg().get("allList");

	List<CoSalaryItemInfoModel> sallList = new ArrayList<CoSalaryItemInfoModel>();
	private String itemname;
	private SalaryPaperBll spb = new SalaryPaperBll();

	// 构造器
	public SalaryPaper_updateSalaryIetmController() {
		init();
	}

	public void init() {
		search();
	}

	@Command
	@NotifyChange("sallList")
	public void search() {
		if (allList != null && allList.size() != 0) {
			sallList.clear();
			for (CoSalaryItemInfoModel m : allList) {
				if (itemname != null) {
					if (!RegexUtil.isExists(itemname, m.getCsii_item_name())) {
						continue;
					}
				}
				sallList.add(m);
			}
		}
	}

	// 保存修改
	@Command
	public void save(@BindingParam("sign") String sign,
			@BindingParam("m") CoSalaryItemInfoModel m) {
		spb.saveUpdateItemname(sign, m);
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public List<CoSalaryItemInfoModel> getSallList() {
		return sallList;
	}

	public void setSallList(List<CoSalaryItemInfoModel> sallList) {
		this.sallList = sallList;
	}

	public List<CoSalaryItemInfoModel> getAllList() {
		return allList;
	}

	public void setAllList(List<CoSalaryItemInfoModel> allList) {
		this.allList = allList;
	}

}
