package Controller.EmTax;

import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmTax.EmTax_OperateBll;
import bll.EmTax.EmTax_SelectBll;

import Model.EmSalaryInfoModel;
import Model.EmTaxInfoModel;
import Util.DateStringChange;

public class EmTax_TaxPlaceAssignController {
	private EmTax_SelectBll tBll = new EmTax_SelectBll();
	private EmTax_OperateBll oBll = new EmTax_OperateBll();
	private EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
	private DateStringChange dsc = new DateStringChange();

	private List<EmTaxInfoModel> emList;
	private List<String> placeList;
	private String state = "";
	private int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());

	public EmTax_TaxPlaceAssignController() throws Exception {
		refresh();
		placeList = esBll.getTaxCityByCid(cid);
	}

	@Command("search")
	@NotifyChange({ "emList" })
	public void search(@BindingParam("state") String s) {
		state = s;
		refresh();
	}

	public void refresh() {
		String sql = " AND cid=" + String.valueOf(cid);
		if ("未分配".equals(state)) {
			sql = sql + " AND (esin_taxplace='' OR esin_taxplace is null)";
		} else if ("已分配".equals(state)) {
			sql = sql + " AND esin_taxplace<>'' AND esin_taxplace is NOT null";
		}
		emList = tBll.getTPEmList(sql);
	}

	@Command("submit")
	@NotifyChange({ "emList" })
	public void submit(@BindingParam("emlist") Listbox emlist,
			@BindingParam("place") Listbox place,
			@BindingParam("n_ownmonth") Datebox n_ownmonth) {
		Set<Listitem> emCheck = emlist.getSelectedItems();

		String n_ownmonth_s = "";
		try {
			if (n_ownmonth.getValue() != null) {
				n_ownmonth_s = dsc.DatetoSting(n_ownmonth.getValue(), "yyyyMM"); // 月份
			}
		} catch (Exception e) {
			n_ownmonth_s = "";
		}

		if (!emCheck.isEmpty() && place.getSelectedItem() != null
				&& !"".equals(n_ownmonth_s)) {
			int chk_i = 0;
			int chk_j = 0;

			for (Listitem c : emCheck) {
				chk_i = chk_i + 1;

				EmSalaryInfoModel m = null;
				m = new EmSalaryInfoModel();
				
				m.setCid(cid);
				m.setGid(((EmTaxInfoModel) c.getValue()).getGid());
				m.setEsin_taxplace((String) place.getSelectedItem().getValue());
				m.setEsin_nexttaxplace_smonth(Integer.parseInt(n_ownmonth_s));
				// 调用方法
				String[] message = oBll.assginTaxPlace(m);

				if (message[0].equals("1")) {
					chk_j = chk_j + 1;
				}

			}

			if (chk_i == chk_j) {
				// 弹出提示
				Messagebox.show("分配成功！", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);

			} else {
				// 弹出提示
				Messagebox.show("分配出错，请重新操作！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

			// 刷新
			refresh();

		} else {
			if (emCheck.isEmpty()) {
				// 弹出提示
				Messagebox.show("请选择员工！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (place.getSelectedItem() == null) {
				// 弹出提示
				Messagebox.show("请选择地区！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if ("".equals(n_ownmonth_s)) {
				// 弹出提示
				Messagebox.show("请选择生效月份！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	public List<EmTaxInfoModel> getEmList() {
		return emList;
	}

	public void setEmList(List<EmTaxInfoModel> emList) {
		this.emList = emList;
	}

	public List<String> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<String> placeList) {
		this.placeList = placeList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
