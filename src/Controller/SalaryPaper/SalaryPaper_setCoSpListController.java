package Controller.SalaryPaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import bll.SalaryPaper.SalaryPaperBll;

import Model.CoFinanceCollectModel;
import Model.EmSalaryInfoModel;
import Model.SalaryPaperCoModel;
import Util.RegexUtil;

/**
 * 公司工资单设置
 * 
 * @author Administrator
 * 
 */
public class SalaryPaper_setCoSpListController {

	private Window window;
	private String Coinfo;
	private List<SalaryPaperCoModel> coSetList;
	private SalaryPaperBll spb = new SalaryPaperBll();
	private List<SalaryPaperCoModel> sCoSetList = new ArrayList<SalaryPaperCoModel>();

	// 构造器
	public SalaryPaper_setCoSpListController() {
		init();
	}

	// 初始化数据
	public void init() {
		coSetList = spb.getCoSetList();
		serach();
	}

	// 查询
	@Command
	@NotifyChange("sCoSetList")
	public void serach() {
		if (coSetList != null && coSetList.size() != 0) {
			sCoSetList.clear();
			for (SalaryPaperCoModel m : coSetList) {
				if (Coinfo != null) {
					try {
						Integer.valueOf(Coinfo);
						if (!RegexUtil.isExists(Coinfo,
								String.valueOf(m.getCid()))) {
							continue;
						}
					} catch (Exception e) {
						if (!RegexUtil.isExists(Coinfo, m.getCoba_company())) {
							continue;
						}
					}
				}
				sCoSetList.add(m);
			}
		}
	}

	/**
	 * 全选
	 * 
	 * @param gridId
	 * @param checkall
	 */
	@Command
	public void allcheck(@BindingParam("gridid") Grid gridId,
			@BindingParam("c") Checkbox c) {
		boolean isCheck = c.isChecked();
		int page = gridId.getPageCount();
		int size = gridId.getPageSize();
		int num = page * size;
		try {
			for (int i = 0; i <= num; i++) {
				try {
					Checkbox check = (Checkbox) gridId.getCell(i, 5)
							.getChildren().get(0);
					check.setChecked(isCheck);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
	}

	// 员工设置
	@Command
	public void setSalary(@BindingParam("m") SalaryPaperCoModel m) {
		List<SalaryPaperCoModel> l = new ArrayList<SalaryPaperCoModel>();
		l.add(m);
		Map<String, List<SalaryPaperCoModel>> map = new HashMap<String, List<SalaryPaperCoModel>>();
		map.put("spCoList", l);
		window = (Window) Executions.createComponents(
				"/SalaryPaper/SalaryPaper_setCoSp.zul", null, map);
		window.doModal();
	}

	// 批量员工设置
	@Command
	public void plSetSalary(@BindingParam("gridid") Grid grid) {
		int page = grid.getPageCount();
		int size = grid.getPageSize();
		int num = page * size;
		List<SalaryPaperCoModel> l = new ArrayList<SalaryPaperCoModel>();
		try {
			for (int i = 0; i <= num; i++) {
				try {
					Checkbox check = (Checkbox) grid.getCell(i, 5)
							.getChildren().get(0);
					if (check.isChecked() == true) {
						l.add((SalaryPaperCoModel) check.getValue());
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}

		if (l.size() > 0) {
			Map<String, List<SalaryPaperCoModel>> map = new HashMap<String, List<SalaryPaperCoModel>>();
			map.put("spCoList", l);
			window = (Window) Executions.createComponents(
					"/SalaryPaper/SalaryPaper_setCoSp.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("请选择要设置的公司", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 公司设置
	@Command
	public void setCo(@BindingParam("m") SalaryPaperCoModel m,
			@ContextParam(ContextType.VIEW) Component view) {
		Map<String, SalaryPaperCoModel> map = new HashMap<String, SalaryPaperCoModel>();
		map.put("m", m);

		window = (Window) Executions.createComponents(
				"/SalaryPaper/SalaryPaper_CoSetInfo.zul", view, map);
		window.doModal();
	}

	// 设置工资项目\
	@Command
	public void setSp(@BindingParam("m") SalaryPaperCoModel m) {
		Map<String, SalaryPaperCoModel> map = new HashMap<String, SalaryPaperCoModel>();
		map.put("m", m);
		window = (Window) Executions.createComponents(
				"/SalaryPaper/SalaryPaper_ItemInfo.zul", null, map);
		window.doModal();
	}

	// 生成附件密码
	@Command
	public void producePassword() {

		// 根据公司的查询员工
		List<EmSalaryInfoModel> esinfoList = spb.getPlEsInfoList(coSetList);
		// 产生cosetlist.size()个随机数
		for (int i = 0; i < esinfoList.size(); i++) {
			// 生成一个8位数的随机数
			int number = (int) (89999999 * Math.random() + 10000000);
			esinfoList.get(i).setEsin_attachpassword(String.valueOf(number));
		}
		// 保存
		boolean flag = spb.producePwd(esinfoList);
		if (flag) {
			Messagebox.show("生成成功", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else {
			Messagebox.show("生成失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 刷新
	@Command
	@NotifyChange({ "sCoSetList", "coSetList" })
	public void sCoSetList() {
		init();
	}

	public List<SalaryPaperCoModel> getsCoSetList() {
		return sCoSetList;
	}

	public void setsCoSetList(List<SalaryPaperCoModel> sCoSetList) {
		this.sCoSetList = sCoSetList;
	}

	public String getCoinfo() {
		return Coinfo;
	}

	public void setCoinfo(String coinfo) {
		Coinfo = coinfo;
	}

}
